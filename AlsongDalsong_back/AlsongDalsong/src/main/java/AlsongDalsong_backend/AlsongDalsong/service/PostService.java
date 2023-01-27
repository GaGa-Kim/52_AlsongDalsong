package AlsongDalsong_backend.AlsongDalsong.service;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.PhotoRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.VoteRepository;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoIdResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 게시글 서비스
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final AwsS3Service awsS3Service;
    private final PhotoService photoService;
    private final VoteRepository voteRepository;

    // 게시글 작성
    @Transactional
    public PostResponseDto save(PostSaveRequestDto postSaveRequestDto, List<MultipartFile> multipartFiles) {
        User user = userRepository.findByEmail(postSaveRequestDto.getEmail());

        Post post = postSaveRequestDto.toEntity();
        // 연관관계 설정
        post.setUser(user);
        user.addPostList(postRepository.save(post));

        // 글 작성 시 + 1점
        user.updatePointAndSticker(user.getPoint() + 1, user.getSticker());

        // 게시글에 사진이 있을 경우
        if(multipartFiles != null) {
            // 사진 저장
            List<Photo> photoList = awsS3Service.uploadPhoto(multipartFiles);
            if(!photoList.isEmpty()) {
                for(Photo photo: photoList) {
                    // 연관관계 설정
                    post.addPhotoList(photoRepository.save(photo));
                }
            }
        }

        return new PostResponseDto(post, findPostId(post.getId()), 0L, 0L);
    }

    // 게시글 상세 조회
    @Transactional
    public PostResponseDto inquire(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));

        return new PostResponseDto(post, findPostId(id), voteRepository.countByPostIdAndVote(post, true), voteRepository.countByPostIdAndVote(post, false));
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto update(PostUpdateRequestDto postUpdateRequestDto, List<MultipartFile> multipartFiles, List<Long> deleteId) {
        User user = userRepository.findByEmail(postUpdateRequestDto.getEmail());
        Post post = postRepository.findById(postUpdateRequestDto.getId()).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));

        // 게시글 작성자와 게시글 수정하는 자가 같을 경우에만 수정 가능
        if(user.equals(post.getUserId())) {
            // 삭제할 파일이 있다면 파일 삭제
            if (!CollectionUtils.isEmpty(deleteId)) {
                for(Long deleteFileId: deleteId) {
                    awsS3Service.deleteS3(photoService.findByPhotoId(deleteFileId).getPhotoName());
                    photoService.delete(deleteFileId);
                }
            }
            // 추가할 파일이 있다면 파일 추가
            if(!CollectionUtils.isEmpty(multipartFiles)) {
                List<Photo> photoList = awsS3Service.uploadPhoto(multipartFiles);
                for(Photo photo: photoList) {
                    post.addPhotoList(photoRepository.save(photo));
                }
            }

            post.update(postUpdateRequestDto.getTodo(),
                        postUpdateRequestDto.getCategory(),
                        postUpdateRequestDto.getWho(),
                        postUpdateRequestDto.getOld(),
                        postUpdateRequestDto.getDate(),
                        postUpdateRequestDto.getWhat(),
                        postUpdateRequestDto.getContent(),
                        postUpdateRequestDto.getLink(),
                        postUpdateRequestDto.getImportance());

            return new PostResponseDto(post, findPostId(post.getId()), voteRepository.countByPostIdAndVote(post, true), voteRepository.countByPostIdAndVote(post, false));
        }
        else {
            throw new RuntimeException("게시글 수정에 실패했습니다.");
        }
    }

    // 게시글 삭제
    @Transactional
    public Boolean delete(Long id, String email) {
        User user = userRepository.findByEmail(email);
        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));
        List<Photo> photoList = photoRepository.findAllByPostId(post);

        // 게시글 작성자와 게시글 삭제하는 자가 같을 경우에만 삭제 가능
        if(user.equals(post.getUserId())) {
            // 게시글과 연관된 사진 모두 삭제
            for(Photo photo: photoList) {
                awsS3Service.deleteS3(photo.getPhotoName());
            }
            postRepository.delete(post);

            return true;
        }
        else {
            throw new RuntimeException("게시글 삭제에 실패했습니다.");
        }
    }

    // 게시글 확정
    @Transactional
    public PostResponseDto updateDecision(Long id, String email, String decision, String reason) {
        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));

        // 게시글 작성자와 게시글 확정하는 자가 같을 경우에만 확정 가능
        // 이미 게시글을 확정했다면 변경할 수 없음
        if((post.getUserId().getEmail().equals(email)) && (post.getDecision().equals("미정"))) {
            post.setDecision(decision, reason);
            // 글 확정 시 + 5점
            post.getUserId().updatePointAndSticker(post.getUserId().getPoint() + 5, post.getUserId().getSticker());

            return new PostResponseDto(post, findPostId(post.getId()), voteRepository.countByPostIdAndVote(post, true), voteRepository.countByPostIdAndVote(post, false));
        }

        else {
            throw new RuntimeException("게시글 확정에 실패했습니다.");
        }
    }

    // 살까 말까 / 할까 말까 / 갈까 말까로 분류별 최신글 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> inquireLatest(String todo) {
        // 분류별 조회
        List<Post> postList = postRepository.findByTodo(todo);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post: postList) {
            postResponseDtoList.add(new PostResponseDto(post, findPostId(post.getId()), voteRepository.countByPostIdAndVote(post, true), voteRepository.countByPostIdAndVote(post, false)));
        }

        return postResponseDtoList;
    }

    // 분류별 인기글 조회 (결정이 된 글은 인기순에서 제외)
    @Transactional(readOnly = true)
    public List<PostResponseDto> inquirePopular(String todo) {
        // 분류별이면서 결정이 '미정'일 경우에만 조회하여 투표순으로 정렬
        List<Post> postList = postRepository.findByTodoAndDecisionOrderByVoteListDesc(todo, "미정");
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post: postList) {
            postResponseDtoList.add(new PostResponseDto(post, findPostId(post.getId()), voteRepository.countByPostIdAndVote(post, true), voteRepository.countByPostIdAndVote(post, false)));
        }

        return postResponseDtoList;
    }

    // 분류의 카테고리별 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> inquireCategory(String todo, String category) {
        // 분류와 카테고리별 조회
        List<Post> postList = postRepository.findByTodoAndCategory(todo, category);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post: postList) {
            postResponseDtoList.add(new PostResponseDto(post, findPostId(post.getId()), voteRepository.countByPostIdAndVote(post, true), voteRepository.countByPostIdAndVote(post, false)));
        }

        return postResponseDtoList;
    }

    // 사용자별 쓴 글 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> my(String email) {
        User user = userRepository.findByEmail(email);
        List<Post> postList = postRepository.findByUserId(user);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post: postList) {
            postResponseDtoList.add(new PostResponseDto(post, findPostId(post.getId()), voteRepository.countByPostIdAndVote(post, true), voteRepository.countByPostIdAndVote(post, false)));
        }

        return postResponseDtoList;
    }

    // 게시글 별 사진 아이디 전체 조회
    @Transactional(readOnly = true)
    public List<Long> findPostId(Long id){

        List<PhotoIdResponseDto> photoIdResponseDtoList = photoService.findAllByPost(id);
        List<Long> photoId = new ArrayList<>();

        for (PhotoIdResponseDto photoIdResponseDto : photoIdResponseDtoList) {
            photoId.add(photoIdResponseDto.getPhotoId());
        }

        return photoId;
    }
}
