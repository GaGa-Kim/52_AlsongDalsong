package AlsongDalsong_backend.AlsongDalsong.service.post;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.PhotoRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Category;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Decision;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Todo;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.VoteRepository;
import AlsongDalsong_backend.AlsongDalsong.exception.UnauthorizedPostEditException;
import AlsongDalsong_backend.AlsongDalsong.service.photo.AwsS3ServiceImpl;
import AlsongDalsong_backend.AlsongDalsong.service.photo.PhotoServiceImpl;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoIdResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostUpdateRequestDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 게시글을 위한 비즈니스 로직 구현 클래스
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private static final Long VOTE_INIT = 0L;
    private static final int POINTS_PER_POST = 1;
    private static final int POINTS_PER_DECISION = 5;

    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;
    private final UserService userService;
    private final AwsS3ServiceImpl awsS3ServiceImpl;
    private final PhotoServiceImpl photoServiceImpl;
    private final VoteRepository voteRepository;

    /**
     * 게시글을 작성한다.
     *
     * @param postSaveRequestDto (게시글 저장 정보 DTO), photos (게시글 사진)
     * @return PostResponseDto (저장된 게시글 정보 DTO)
     */
    @Override
    public PostResponseDto addPostWithPhotos(PostSaveRequestDto postSaveRequestDto, List<MultipartFile> photos) {
        User user = userService.findUserByEmail(postSaveRequestDto.getEmail());
        Post post = savePost(user, postSaveRequestDto);
        savePostPhotos(post, photos);
        return new PostResponseDto(post, findPhotoIdByPost(post.getId()), Pair.of(VOTE_INIT, VOTE_INIT));
    }

    /**
     * 게시글 아이디로 게시글을 상세 조회한다.
     *
     * @param postId (게시글 아이디)
     * @return PostResponseDto (게시글 정보 DTO)
     */
    @Override
    public PostResponseDto findPostByPostId(Long postId) {
        Post post = findPostById(postId);
        return new PostResponseDto(post, findPhotoIdByPost(postId), countVote(post));
    }

    /**
     * 분류별 최신글 리스트를 조회한다.
     *
     * @param todo (분류)
     * @return List<PostResponseDto> (분류별 최신 게시글 DTO 리스트)
     */
    public List<PostResponseDto> findLatestPosts(String todo) {
        return convertToDtos(postRepository.findByTodo(Todo.ofTodo(todo)));
    }

    /**
     * 분류별 인기글 리스트를 조회한다. 이때 결정이 완료된 글은 제외된다.
     *
     * @param todo (분류)
     * @return List<PostResponseDto> (분류별 인기 게시글 DTO 리스트)
     */
    @Override
    public List<PostResponseDto> findPopularPosts(String todo) {
        return convertToDtos(
                postRepository.findByTodoAndDecisionOrderByVoteListDesc(Todo.ofTodo(todo), Decision.UNDECIDED));
    }

    /**
     * 분류 및 카테고리별 게시글 리스트를 조회한다.
     *
     * @param todo (분류), category (카테고리)
     * @return List<PostResponseDto> (분류 및 카테고리별 게시글 DTO 리스트)
     */
    @Override
    public List<PostResponseDto> findPostsByCategory(String todo, String category) {
        return convertToDtos(postRepository.findByTodoAndCategory(Todo.ofTodo(todo), Category.ofCategory(category)));
    }

    /**
     * 회원 아이디에 따른 게시글 리스트를 조회한다.
     *
     * @param email (회원 이메일)
     * @return List<PostResponseDto> (회원 게시글 DTO 리스트)
     */
    @Override
    public List<PostResponseDto> findUserPosts(String email) {
        User user = userService.findUserByEmail(email);
        return convertToDtos(postRepository.findByUserId(user));
    }

    /**
     * 게시글을 수정한다.
     *
     * @param postUpdateRequestDto (게시글 수정 정보 DTO), photos (새롭게 저장할 사진), deletePhotoIds (삭제할 사진 아이디)
     * @return PostResponseDto (수정된 게시글 정보 DTO)
     */
    @Override
    public PostResponseDto modifyPost(PostUpdateRequestDto postUpdateRequestDto, List<MultipartFile> photos,
                                      List<Long> deletePhotoIds) {
        User user = userService.findUserByEmail(postUpdateRequestDto.getEmail());
        Post post = findPostById(postUpdateRequestDto.getId());
        if (isSameUser(user, post)) {
            removePostPhotos(deletePhotoIds);
            savePostPhotos(post, photos);
            updatePost(post, postUpdateRequestDto);
            return new PostResponseDto(post, findPhotoIdByPost(post.getId()), countVote(post));
        }
        throw new UnauthorizedPostEditException();
    }

    /**
     * 게시글을 삭제한다.
     *
     * @param postId (게시글 아이디), email (회원 이메일)
     * @return Boolean (게시글 삭제 여부)
     */
    @Override
    public Boolean removePost(Long postId, String email) {
        User user = userService.findUserByEmail(email);
        Post post = findPostById(postId);
        if (isSameUser(user, post)) {
            removeAllPostPhotos(post);
            postRepository.delete(post);
            return true;
        }
        throw new UnauthorizedPostEditException();
    }

    /**
     * 게시글을 확정한다.
     *
     * @param postId (게시글 아이디), email (회원 이메일), decision (결정 여부), reason (결정 이유)
     * @return PostResponseDto (게시글 정보 DTO)
     */
    @Override
    public PostResponseDto modifyPostDecision(Long postId, String email, String decision, String reason) {
        User user = userService.findUserByEmail(email);
        Post post = findPostById(postId);
        if (isSameUser(user, post) && isNotDecided(post)) {
            post.setDecision(decision, reason);
            increasePoint(user, POINTS_PER_DECISION);
            return new PostResponseDto(post, findPhotoIdByPost(post.getId()), countVote(post));
        }
        throw new UnauthorizedPostEditException();
    }

    /**
     * 게시글 아이디로 게시글을 조회한다.
     *
     * @param postId (게시글 아이디)
     * @return Post (게시글)
     */
    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
    }

    /**
     * 게시글 별 사진 아이디 리스트를 조회한다.
     *
     * @param postId (게시글 아이디)
     * @return List<Long> 사진 아이디 리스트
     */
    private List<Long> findPhotoIdByPost(Long postId) {
        return photoServiceImpl.findAllByPost(postId)
                .stream()
                .map(PhotoIdResponseDto::getPhotoId)
                .collect(Collectors.toList());
    }

    /**
     * 게시글 내용을 저장한다.
     *
     * @param user (회원), postSaveRequestDto (게시글 저장 정보 DTO)
     * @return Post (게시글)
     */
    private Post savePost(User user, PostSaveRequestDto postSaveRequestDto) {
        Post post = postRepository.save(postSaveRequestDto.toEntity());
        post.setUser(user);
        user.addPostList(post);
        increasePoint(user, POINTS_PER_POST);
        return post;
    }

    /**
     * 활동에 따른 포인트가 증가한다.
     *
     * @param user (회원), @param point (증가 포인트)
     */
    private void increasePoint(User user, int point) {
        user.updatePoint(user.getPoint() + point);
    }

    /**
     * 게시글 사진을 저장한다.
     *
     * @param post (게시글), photos (사진)
     */
    private void savePostPhotos(Post post, List<MultipartFile> photos) {
        Optional.ofNullable(photos)
                .map(awsS3ServiceImpl::uploadPhoto)
                .ifPresent(photo -> photo.forEach(p -> post.addPhotoList(photoRepository.save(p))));
    }

    /**
     * 게시글 작성자와 게시글 편집자가 같은지 확인한다.
     *
     * @param user (회원), post (게시글)
     * @return boolean (게시글 작성자와 편집자 동일 여부)
     */
    private boolean isSameUser(User user, Post post) {
        return user.equals(post.getUserId());
    }

    /**
     * 사진 아이디에 따른 게시글 사진을 삭제한다.
     *
     * @param deletePhotoIds (삭제할 사진 아이디)
     */
    private void removePostPhotos(List<Long> deletePhotoIds) {
        if (!deletePhotoIds.isEmpty()) {
            for (Long deleteFileId : deletePhotoIds) {
                awsS3ServiceImpl.deleteS3(photoServiceImpl.findByPhotoId(deleteFileId).getPhotoName());
                photoServiceImpl.delete(deleteFileId);
            }
        }
    }

    /**
     * 게시글 내용을 수정한다.
     *
     * @param post (게시글), postUpdateRequestDto (수정할 게시글 DTO)
     */
    private void updatePost(Post post, PostUpdateRequestDto postUpdateRequestDto) {
        post.update(postUpdateRequestDto.getTodo(),
                postUpdateRequestDto.getCategory(),
                postUpdateRequestDto.getWho(),
                postUpdateRequestDto.getOld(),
                postUpdateRequestDto.getDate(),
                postUpdateRequestDto.getWhat(),
                postUpdateRequestDto.getContent(),
                postUpdateRequestDto.getLink(),
                postUpdateRequestDto.getImportance());
    }

    /**
     * 게시글에 따른 투표 수를 조회한다.
     *
     * @param post (게시글)
     * @return Pair<Long, Long> (게시글 동의, 비동의 투표 수)
     */
    private Pair<Long, Long> countVote(Post post) {
        Long agree = voteRepository.countByPostIdAndVote(post, true);
        Long disagree = voteRepository.countByPostIdAndVote(post, false);
        return Pair.of(agree, disagree);
    }

    /**
     * 게시글 별 사진을 전체 조회한다.
     *
     * @param post (게시글)
     * @return List<Photo> (게시글 별 사진 리스트)
     */
    private List<Photo> findPhotoByPost(Post post) {
        return photoRepository.findAllByPostId(post);
    }

    /**
     * 게시글 별 사진을 전체 삭제한다.
     *
     * @param post (게시글)
     */
    private void removeAllPostPhotos(Post post) {
        for (Photo photo : findPhotoByPost(post)) {
            awsS3ServiceImpl.deleteS3(photo.getPhotoName());
        }
    }

    /**
     * 게시글 확정 여부를 확인한다.
     *
     * @param post (게시글)
     * @return
     */
    private boolean isNotDecided(Post post) {
        return post.getDecision().equals(Decision.UNDECIDED);
    }

    /**
     * 게시글 리스트을 PostResponseDto 리스트로 변환한다.
     *
     * @param posts (게시글 리스트)
     * @return List<PostResponseDto> (게시글 DTO 리스트)
     */
    private List<PostResponseDto> convertToDtos(List<Post> posts) {
        return posts.stream()
                .map(post -> new PostResponseDto(post, findPhotoIdByPost(post.getId()), countVote(post)))
                .collect(Collectors.toList());
    }
}
