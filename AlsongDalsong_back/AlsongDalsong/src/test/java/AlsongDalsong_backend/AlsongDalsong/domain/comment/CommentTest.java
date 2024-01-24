package AlsongDalsong_backend.AlsongDalsong.domain.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import AlsongDalsong_backend.AlsongDalsong.domain.like.Like;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 댓글 테이블 테스트
 */
class CommentTest {
    private Comment comment;

    @BeforeEach
    void setComment() {
        comment = Comment.builder().content("구매하세요").build();
    }

    @Test
    void testUpdate() {
        String newContent = "구매하지 마세요";
        comment.update(newContent);

        assertEquals(newContent, comment.getContent());
    }

    @Test
    void testSetUser() {
        User user = mock(User.class);
        comment.setUser(user);

        assertEquals(comment.getUserId(), user);
    }

    @Test
    void testSetPost() {
        Post post = mock(Post.class);
        comment.setPost(post);

        assertEquals(comment.getPostId(), post);
        verify(post, times(2)).getCommentList();
    }

    @Test
    void testAddLikeList() {
        Like like = mock(Like.class);
        comment.addLikeList(like);

        assertTrue(comment.getLikeList().contains(like));
        verify(like, times(1)).setComment(comment);
    }
}