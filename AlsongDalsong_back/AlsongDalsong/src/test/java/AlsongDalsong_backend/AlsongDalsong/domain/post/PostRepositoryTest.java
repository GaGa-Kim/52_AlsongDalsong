package AlsongDalsong_backend.AlsongDalsong.domain.post;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_CATEGORY;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_DECISION;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_TODO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * 게시글 레포지토리 테스트
 */
@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시글 분류별 게시글 리스트 조회 테스트")
    void findByTodo() {
        List<Post> foundPostList = postRepository.findByTodo(VALID_TODO);

        assertNotNull(foundPostList);
        assertEquals(4, foundPostList.size());
    }

    @Test
    @DisplayName("게시글 분류와 카테고리별 게시글 리스트 조회 테스트")
    void findByTodoAndCategory() {
        List<Post> foundPostList = postRepository.findByTodoAndCategory(VALID_TODO, VALID_CATEGORY);

        assertNotNull(foundPostList);
        assertEquals(4, foundPostList.size());
    }


    @Test
    @DisplayName("게시글 분류와 카테고리별 인기 투표 게시글 리스트 조회 테스트")
    void findByTodoAndDecisionOrderByVoteListDesc() {
        List<Post> foundPostList = postRepository.findByTodoAndDecisionOrderByVoteListDesc(VALID_TODO, VALID_DECISION);

        assertNotNull(foundPostList);
        assertEquals(4, foundPostList.size());
    }

    @Test
    @DisplayName("회원 아이디에 따른 게시글 분류와 결정별 게시글 개수 세기 테스트")
    void countByUserIdAndTodoAndDecision() {
        User user = userRepository.findByEmail(VALID_EMAIL);
        Long foundPostCount = postRepository.countByUserIdAndTodoAndDecision(user, VALID_TODO, VALID_DECISION);

        assertEquals(Long.valueOf(4), foundPostCount);
    }
}