package AlsongDalsong_backend.AlsongDalsong.domain.scrap;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * 스크랩 레포지토리 테스트
 */
@DataJpaTest
class ScrapRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ScrapRepository scrapRepository;

    @Test
    @DisplayName("회원 아이디와 게시글 아이디로 스크랩 조회 테스트")
    void findByUserIdAndPostId() {
        User user = userRepository.findByEmail(VALID_EMAIL);
        Post post = postRepository.findById(VALID_POST_ID).orElse(null);
        Scrap foundScrap = scrapRepository.findByUserIdAndPostId(user, post);

        assertNotNull(foundScrap);
    }

    @Test
    @DisplayName("회원 아이디로 스크랩 리스트 조회 테스트")
    void findByUserId() {
        User user = userRepository.findByEmail(VALID_EMAIL);
        List<Scrap> foundScrapList = scrapRepository.findByUserId(user);

        assertNotNull(foundScrapList);
        assertEquals(1, foundScrapList.size());
    }

    @Test
    @DisplayName("회원 아이디와 게시글 아이디로 스크랩 존재 확인 테스트")
    void existByUserIdAndPostId() {
        User user = userRepository.findByEmail(VALID_EMAIL);
        Post post = postRepository.findById(VALID_POST_ID).orElse(null);

        boolean existScrap = scrapRepository.existsByUserIdAndPostId(user, post);

        assertTrue(existScrap);
    }
}