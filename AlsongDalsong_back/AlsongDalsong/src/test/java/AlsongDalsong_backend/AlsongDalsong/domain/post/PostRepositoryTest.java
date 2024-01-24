package AlsongDalsong_backend.AlsongDalsong.domain.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * 게시글 레포지토리 테스트
 */
@DataJpaTest
class PostRepositoryTest {
    private final String existEmail = "1234@gmail.com";
    private final Todo todo = Todo.TO_BUY_OR_NOT_TO_BUY;
    private final Category category = Category.FASHION;
    private final Decision decision = Decision.UNDECIDED;
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Test
    void findByTodo() {
        List<Post> foundPostList = postRepository.findByTodo(todo);

        assertNotNull(foundPostList);
        assertEquals(4, foundPostList.size());
    }

    @Test
    void findByTodoAndCategory() {
        List<Post> foundPostList = postRepository.findByTodoAndCategory(todo, category);

        assertNotNull(foundPostList);
        assertEquals(4, foundPostList.size());
    }


    @Test
    void findByTodoAndDecisionOrderByVoteListDesc() {
        List<Post> foundPostList = postRepository.findByTodoAndDecisionOrderByVoteListDesc(todo, decision);

        assertNotNull(foundPostList);
        assertEquals(4, foundPostList.size());
    }

    @Test
    void countByUserIdAndTodoAndDecision() {
        User user = userRepository.findByEmail(existEmail);
        Long foundPostCount = postRepository.countByUserIdAndTodoAndDecision(user, todo, decision);

        assertEquals(Long.valueOf(4), foundPostCount);
    }
}