package AlsongDalsong_backend.AlsongDalsong.domain.like;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 좋아요 테이블 테스트
 */
class LikeTest {
    private Like like;

    @BeforeEach
    void setUp() {
        like = TestObjectFactory.initLike();
    }

    @Test
    @DisplayName("작성한 댓글 좋아요의 작성자 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        like.setUser(user);

        assertEquals(like.getUserId(), user);
    }

    @Test
    @DisplayName("작성한 댓글 좋아요의 댓글 연관관계 설정 테스트")
    void testSetComment() {
        Comment comment = mock(Comment.class);
        like.setComment(comment);

        assertEquals(like.getCommentId(), comment);
    }
}