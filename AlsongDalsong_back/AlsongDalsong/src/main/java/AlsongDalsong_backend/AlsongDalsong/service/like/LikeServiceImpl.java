package AlsongDalsong_backend.AlsongDalsong.service.like;

import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.like.Like;
import AlsongDalsong_backend.AlsongDalsong.domain.like.LikeRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.except.UnauthorizedEditException;
import AlsongDalsong_backend.AlsongDalsong.service.comment.CommentService;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.like.LikeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글 좋아요를 위한 비즈니스 로직 구현 클래스
 */
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private static final int POINTS_PER_LIKE = 1;
    private static final int POINTS_PER_CANCEL = -1;

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final CommentService commentService;

    /**
     * 댓글에 좋아요를 하거나 좋아요를 취소한다.
     *
     * @param likeSaveRequestDto (좋아요 저장 정보 DTO)
     * @return Boolean (좋아요 또는 좋아요 취소 여부)
     */
    @Override
    @Transactional
    public Boolean saveLike(LikeRequestDto likeSaveRequestDto) {
        User user = userService.findUserByEmail(likeSaveRequestDto.getEmail());
        Comment comment = commentService.findCommentByCommentId(likeSaveRequestDto.getCommentId());
        if (!existsCommentByUserId(user, comment)) {
            return createLike(user, comment);
        }
        return deleteLike(user, comment);
    }

    /**
     * 댓글 아이디와 회원 이메일에 따른 좋아요 여부를 조회한다.
     *
     * @param commentId (댓글 아이디), email (회원 이메일)
     * @return Boolean (좋아요 여부)
     */
    @Override
    public Boolean findLike(Long commentId, String email) {
        User user = userService.findUserByEmail(email);
        Comment comment = commentService.findCommentByCommentId(commentId);
        return existsCommentByUserId(user, comment);
    }

    /**
     * 댓글과 회원에 따른 좋아요 여부를 조회한다.
     *
     * @param user (회원), comment (댓글)
     * @return boolean (좋아요 여부)
     */
    private boolean existsCommentByUserId(User user, Comment comment) {
        return likeRepository.existsByUserIdAndCommentId(user, comment);
    }

    /**
     * 좋아요를 저장한다.
     *
     * @param user (회원), comment (댓글)
     * @return boolean (좋아요 저장에 따른 true 반환)
     */
    private boolean createLike(User user, Comment comment) {
        Like like = Like.builder().build();
        like.setUser(user);
        like.setComment(comment);
        likeRepository.save(like);
        comment.addLikeList(like);
        increasePoint(user, POINTS_PER_LIKE);
        return true;
    }

    /**
     * 좋아요를 삭제한다.
     *
     * @param user (회원), comment (댓글)
     * @return boolean (좋아요 삭제에 따른 false 반환)
     */
    private boolean deleteLike(User user, Comment comment) {
        Like like = likeRepository.findByUserIdAndCommentId(user, comment);
        if (isSameUser(user, like)) {
            likeRepository.delete(like);
            increasePoint(user, POINTS_PER_CANCEL);
            return false;
        }
        throw new UnauthorizedEditException();
    }

    /**
     * 활동에 따른 포인트가 증가 및 감소한다.
     *
     * @param user (회원), @param point (증가 포인트)
     */
    private void increasePoint(User user, int point) {
        user.updatePoint(user.getPoint() + point);
    }

    /**
     * 좋아요 작성자와 좋아요 편집자가 같은지 확인한다.
     *
     * @param user (회원), like (좋아요)
     * @return boolean (좋아요 작성자와 편집자 동일 여부)
     */
    private boolean isSameUser(User user, Like like) {
        return user.equals(like.getUserId());
    }
}
