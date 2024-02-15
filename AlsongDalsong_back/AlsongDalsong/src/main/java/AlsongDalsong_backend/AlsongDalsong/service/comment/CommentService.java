package AlsongDalsong_backend.AlsongDalsong.service.comment;

import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentUpdateRequestDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글을 위한 비즈니스 로직 인터페이스
 */
@Transactional
public interface CommentService {
    // 게시글에 댓글을 작성한다.
    List<CommentResponseDto> addComment(CommentSaveRequestDto commentSaveRequestDto);

    // 댓글 아이디로 댓글을 조회한다.
    @Transactional(readOnly = true)
    Comment findCommentByCommentId(Long commentId);

    // 게시글 아이디로 게시글 별 댓글을 조회한다. 이때 좋아요가 많은 댓글 순으로 정렬된다.
    @Transactional(readOnly = true)
    List<CommentResponseDto> findPostCommentsByLikes(Long postId);

    // 댓글을 수정한다.
    List<CommentResponseDto> modifyComment(CommentUpdateRequestDto commentUpdateRequestDto);

    // 댓글을 삭제한다.
    Boolean removeComment(Long commentId, String email);
}
