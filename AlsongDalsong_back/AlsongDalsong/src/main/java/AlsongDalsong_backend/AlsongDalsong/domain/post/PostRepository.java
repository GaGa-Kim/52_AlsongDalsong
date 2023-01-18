package AlsongDalsong_backend.AlsongDalsong.domain.post;

import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTodo(String todo);
    List<Post> findByTodoAndCategory(String todo, String category);
    List<Post> findByUserId(User user);
    List<Post> findByTodoAndDecisionOrderByVoteListDesc(String todo, String decision);
    Long countByUserIdAndTodoAndDecision(User user, String todo, String decision);
}
