package AlsongDalsong_backend.AlsongDalsong.domain.vote;

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
    private final String existEmail = "123@gmail.com";
    private final Long postId = 1L;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private VoteRepository voteRepository;

    @Test
    void findByUserIdAndPostId() {
        User user = userRepository.findByEmail(existEmail);
        Post post = postRepository.findById(postId).orElse(null);
        Vote foundVote = voteRepository.findByUserIdAndPostId(user, post);

        assertNotNull(foundVote);
    }

    @Test
    void existByUserIdAndPostId() {
        User user = userRepository.findByEmail(existEmail);
        Post post = postRepository.findById(postId).orElse(null);
        boolean existVote = voteRepository.existsByUserIdAndPostId(user, post);

        assertTrue(existVote);
    }
}