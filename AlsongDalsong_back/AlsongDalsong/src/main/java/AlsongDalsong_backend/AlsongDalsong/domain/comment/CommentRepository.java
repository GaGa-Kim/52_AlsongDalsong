package AlsongDalsong_backend.AlsongDalsong.domain.comment;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostIdOrderByLikeListDesc(Post post);
}
