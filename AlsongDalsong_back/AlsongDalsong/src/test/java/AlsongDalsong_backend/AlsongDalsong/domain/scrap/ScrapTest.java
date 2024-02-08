package AlsongDalsong_backend.AlsongDalsong.domain.scrap;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_SCRAP_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 스크랩 테이블 테스트
 */
class ScrapTest {
    private Scrap scrap;

    @BeforeEach
    void setUp() {
        scrap = Scrap.builder()
                .id(VALID_SCRAP_ID)
                .build();
    }

    @Test
    void testSetUser() {
        User user = mock(User.class);
        scrap.setUser(user);

        assertEquals(scrap.getUserId(), user);
    }

    @Test
    void testSetPost() {
        Post post = mock(Post.class);
        scrap.setPost(post);

        assertEquals(scrap.getPostId(), post);
    }
}