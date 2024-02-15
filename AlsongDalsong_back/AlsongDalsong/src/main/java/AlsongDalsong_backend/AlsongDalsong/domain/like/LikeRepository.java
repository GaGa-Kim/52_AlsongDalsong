package AlsongDalsong_backend.AlsongDalsong.domain.like;

import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 댓글 좋아요 레포지토리
 */
@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByUserIdAndCommentId(User user, Comment comment);

    boolean existsByUserIdAndCommentId(User user, Comment comment);
}
