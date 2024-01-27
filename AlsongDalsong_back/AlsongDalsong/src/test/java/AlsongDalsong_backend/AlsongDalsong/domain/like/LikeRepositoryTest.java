package AlsongDalsong_backend.AlsongDalsong.domain.like;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.CommentRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * 댓글 좋아요 레포지토리 테스트
 */
@DataJpaTest
class LikeRepositoryTest {
    private final String existEmail = "1234@gmail.com";
    private final Long commentId = 1L;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeRepository likeRepository;

    @Test
    void findByUserIdAndCommentId() {
        User user = userRepository.findByEmail(existEmail);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        Like foundLike = likeRepository.findByUserIdAndCommentId(user, comment);

        assertNotNull(foundLike);
    }

    @Test
    void existByUserIdAndPostId() {
        User user = userRepository.findByEmail(existEmail);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        boolean existLike = likeRepository.existsByUserIdAndCommentId(user, comment);

        assertTrue(existLike);
    }
}