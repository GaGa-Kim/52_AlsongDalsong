package AlsongDalsong_backend.AlsongDalsong.domain.scrap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * 스크랩 레포지토리 테스트
 */
@DataJpaTest
class ScrapRepositoryTest {
    private final String existEmail = "1234@gmail.com";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ScrapRepository scrapRepository;

    @Test
    void findByUserIdAndPostId() {
        User user = userRepository.findByEmail(existEmail);
        Post post = postRepository.findById(2L).orElse(null);
        Scrap foundScrap = scrapRepository.findByUserIdAndPostId(user, post);

        assertNotNull(foundScrap);
    }

    @Test
    void findByUserId() {
        User user = userRepository.findByEmail(existEmail);
        List<Scrap> foundScrapList = scrapRepository.findByUserId(user);

        assertNotNull(foundScrapList);
        assertEquals(1, foundScrapList.size());
    }
}