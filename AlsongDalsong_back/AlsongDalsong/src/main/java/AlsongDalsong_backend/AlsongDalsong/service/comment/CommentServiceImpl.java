package AlsongDalsong_backend.AlsongDalsong.service.comment;

import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.CommentRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.except.NotFoundException;
import AlsongDalsong_backend.AlsongDalsong.except.UnauthorizedEditException;
import AlsongDalsong_backend.AlsongDalsong.service.post.PostService;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentUpdateRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 댓글을 위한 비즈니스 로직 구현 클래스
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private static final int POINTS_PER_COMMENT = 1;

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    /**
     * 게시글에 댓글을 작성한다.
     *
     * @param commentSaveRequestDto (댓글 저장 정보 DTO)
     * @return List<CommentResponseDto> (게시글 댓글 DTO 리스트)
     */
    @Override
    public List<CommentResponseDto> addComment(CommentSaveRequestDto commentSaveRequestDto) {
        User user = userService.findUserByEmail(commentSaveRequestDto.getEmail());
        Post post = postService.findPostByPostId(commentSaveRequestDto.getPostId());
        createComment(user, post, commentSaveRequestDto);
        return findPostCommentsByLikes(post.getId());
    }

    /**
     * 댓글 아이디로 댓글을 조회한다.
     *
     * @param commentId (댓글 아이디)
     * @return Comment (댓글)
     */
    @Override
    public Comment findCommentByCommentId(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(NotFoundException::new);
    }

    /**
     * 게시글 아이디로 게시글 별 댓글을 조회한다. 이때 좋아요가 많은 댓글 순으로 정렬된다.
     *
     * @param postId (게시글 아이디)
     * @return List<CommentResponseDto> (게시글 댓글 DTO 리스트)
     */
    public List<CommentResponseDto> findPostCommentsByLikes(Long postId) {
        Post post = postService.findPostByPostId(postId);
        return commentRepository.findAllByPostIdOrderByLikeListDesc(post)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 댓글을 수정한다.
     *
     * @param commentUpdateRequestDto (댓글 수정 정보 DTO)
     * @return List<CommentResponseDto> (게시글 댓글 DTO 리스트)
     */
    @Override
    public List<CommentResponseDto> modifyComment(CommentUpdateRequestDto commentUpdateRequestDto) {
        User user = userService.findUserByEmail(commentUpdateRequestDto.getEmail());
        Comment comment = findCommentByCommentId(commentUpdateRequestDto.getId());
        if (isSameUser(user, comment)) {
            comment.update(commentUpdateRequestDto.getContent());
            return findPostCommentsByLikes(comment.getPostId().getId());
        }
        throw new UnauthorizedEditException();
    }

    /**
     * 댓글을 삭제한다.
     *
     * @param commentId (댓글 아이디), email (회원 이메일)
     * @return Boolean (댓글 삭제 여부)
     */
    @Override
    public Boolean removeComment(Long commentId, String email) {
        User user = userService.findUserByEmail(email);
        Comment comment = findCommentByCommentId(commentId);
        if (isSameUser(user, comment)) {
            commentRepository.delete(comment);
            return true;
        }
        throw new UnauthorizedEditException();
    }

    /**
     * 댓글 내용을 저장한다.
     *
     * @param user (회원), post (게시글), commentSaveRequestDto (댓글 저장 정보 DTO)
     */
    private void createComment(User user, Post post, CommentSaveRequestDto commentSaveRequestDto) {
        Comment comment = commentRepository.save(commentSaveRequestDto.toEntity());
        comment.setUser(user);
        comment.setPost(post);
        post.addCommentList(comment);
        increasePoint(user, POINTS_PER_COMMENT);
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
     * 댓글 작성자와 댓글 편집자가 같은지 확인한다.
     *
     * @param user (회원), comment (댓글)
     * @return boolean (댓글 작성자와 편집자 동일 여부)
     */
    private boolean isSameUser(User user, Comment comment) {
        return user.equals(comment.getUserId());
    }
}