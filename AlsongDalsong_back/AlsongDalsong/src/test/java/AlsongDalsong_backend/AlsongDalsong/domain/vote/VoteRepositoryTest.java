package AlsongDalsong_backend.AlsongDalsong.domain.vote;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * 투표 레포지토리 테스트
 */
@DataJpaTest
class VoteRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private VoteRepository voteRepository;

    @Test
    void findByUserIdAndPostId() {
        User user = userRepository.findByEmail(VALID_EMAIL);
        Post post = postRepository.findById(VALID_POST_ID).orElse(null);
        Vote foundVote = voteRepository.findByUserIdAndPostId(user, post);

        assertNotNull(foundVote);
    }

    @Test
    void existByUserIdAndPostId() {
        User user = userRepository.findByEmail(VALID_EMAIL);
        Post post = postRepository.findById(VALID_POST_ID).orElse(null);
        boolean existVote = voteRepository.existsByUserIdAndPostId(user, post);

        assertTrue(existVote);
    }
}