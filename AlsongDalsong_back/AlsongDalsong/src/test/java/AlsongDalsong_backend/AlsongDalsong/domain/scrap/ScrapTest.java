package AlsongDalsong_backend.AlsongDalsong.domain.scrap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 스크랩 테이블 테스트
 */
class ScrapTest {
    private Scrap scrap;

    @BeforeEach
    void setUp() {
        scrap = TestObjectFactory.initScrap();
    }

    @Test
    @DisplayName("작성한 스크랩의 작성자 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        scrap.setUser(user);

        assertEquals(scrap.getUserId(), user);
    }

    @Test
    @DisplayName("작성한 스크랩의 게시글 연관관계 설정 테스트")
    void testSetPost() {
        Post post = mock(Post.class);
        scrap.setPost(post);

        assertEquals(scrap.getPostId(), post);
    }
}