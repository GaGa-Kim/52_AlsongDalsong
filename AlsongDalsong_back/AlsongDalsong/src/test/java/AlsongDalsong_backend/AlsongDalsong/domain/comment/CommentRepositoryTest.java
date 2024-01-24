package AlsongDalsong_backend.AlsongDalsong.domain.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * 댓글 레포지토리 테스트
 */
@DataJpaTest
class CommentRepositoryTest {
    private final Long postId = 1L;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void findAllByPostIdOrderByLikeListDesc() {
        Post post = postRepository.findById(postId).orElse(null);
        List<Comment> foundCommentList = commentRepository.findAllByPostIdOrderByLikeListDesc(post);

        assertNotNull(foundCommentList);
        assertEquals(2, foundCommentList.size());
    }
}