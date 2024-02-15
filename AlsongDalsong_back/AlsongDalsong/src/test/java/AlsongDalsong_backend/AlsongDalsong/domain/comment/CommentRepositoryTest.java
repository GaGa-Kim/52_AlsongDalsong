package AlsongDalsong_backend.AlsongDalsong.domain.comment;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * 댓글 레포지토리 테스트
 */
@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("게시글 아이디로 게시글 별 댓글 리스트 조회 테스트")
    void findAllByPostIdOrderByLikeListDesc() {
        Post post = postRepository.findById(VALID_POST_ID).orElse(null);
        List<Comment> foundCommentList = commentRepository.findAllByPostIdOrderByLikeListDesc(post);

        assertNotNull(foundCommentList);
        assertEquals(2, foundCommentList.size());
    }
}