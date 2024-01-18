package AlsongDalsong_backend.AlsongDalsong.domain.comment;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 댓글 레포지토리
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostIdOrderByLikeListDesc(Post post);
}
