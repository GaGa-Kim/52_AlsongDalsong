package AlsongDalsong_backend.AlsongDalsong.domain.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.like.Like;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 댓글 테이블 테스트
 */
class CommentTest {
    private Comment comment;

    @BeforeEach
    void setUp() {
        comment = TestObjectFactory.initComment();
    }

    @Test
    @DisplayName("댓글 내용 수정 테스트")
    void testUpdate() {
        String newContent = "구매하지 마세요";
        comment.update(newContent);

        assertEquals(newContent, comment.getContent());
    }

    @Test
    @DisplayName("작성한 댓글의 작성자 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        comment.setUser(user);

        assertEquals(comment.getUserId(), user);
    }

    @Test
    @DisplayName("작성한 댓글의 게시글 연관관계 설정 테스트")
    void testSetPost() {
        Post post = mock(Post.class);
        comment.setPost(post);

        assertEquals(comment.getPostId(), post);
        verify(post, times(2)).getCommentList();
    }

    @Test
    @DisplayName("작성한 댓글의 댓글 좋아요 연관관계 설정 테스트")
    void testAddLikeList() {
        Like like = mock(Like.class);
        comment.addLikeList(like);

        assertTrue(comment.getLikeList().contains(like));
        verify(like, times(1)).setComment(comment);
    }
}