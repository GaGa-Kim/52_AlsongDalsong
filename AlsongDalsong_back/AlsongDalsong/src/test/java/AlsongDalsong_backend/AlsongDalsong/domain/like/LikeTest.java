package AlsongDalsong_backend.AlsongDalsong.domain.like;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 좋아요 테이블 테스트
 */
class LikeTest {
    private Like like;

    @BeforeEach
    void setUp() {
        like = new Like();
    }

    @Test
    void testSetUser() {
        User user = mock(User.class);
        like.setUser(user);

        assertEquals(like.getUserId(), user);
    }

    @Test
    void testSetComment() {
        Comment comment = mock(Comment.class);
        like.setComment(comment);

        assertEquals(like.getCommentId(), comment);
    }
}