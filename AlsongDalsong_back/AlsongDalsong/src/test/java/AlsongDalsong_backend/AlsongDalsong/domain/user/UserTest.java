package AlsongDalsong_backend.AlsongDalsong.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.constants.Rule;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.Scrap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 회원 테이블 테스트
 */
class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = TestObjectFactory.initUser();
    }

    @Test
    void testUpdateInfo() {
        String newNickName = "달송이";
        String newIntroduce = "안녕하십니까";
        user.updateInfo(newNickName, newIntroduce);

        assertEquals(newNickName, user.getNickname());
        assertEquals(newIntroduce, user.getIntroduce());
    }

    @Test
    void testUpdateProfile() {
        String newProfile = "profile.png";
        user.updateProfile(newProfile);

        assertEquals(newProfile, user.getProfile());
    }

    @Test
    void testUpdatePoint_canNotAddStickers() {
        int point = 50;
        user.updatePoint(point);

        assertEquals(point, user.getPoint());
        assertEquals(0, user.getSticker());
    }

    @Test
    void testUpdatePoint_canAddStickers() {
        int point = 101;
        user.updatePoint(point);

        assertEquals(point % Rule.STICKER_EARN_THRESHOLD.getRule(), user.getPoint());
        assertEquals(Rule.STICKER_ADD_AMOUNT.getRule(), user.getSticker());
    }

    @Test
    void testWithdrawUser() {
        assertFalse(user.getWithdraw());

        user.withdrawUser();
        assertTrue(user.getWithdraw());
        assertEquals("탈퇴한 회원", user.getNickname());
    }

    @Test
    void testAddPostList() {
        Post post = mock(Post.class);
        user.addPostList(post);

        assertTrue(user.getPostList().contains(post));
        verify(post, times(1)).setUser(user);
    }

    @Test
    void testAddScrapList() {
        Scrap scrap = mock(Scrap.class);
        user.addScrapList(scrap);

        assertTrue(user.getScrapList().contains(scrap));
        verify(scrap, times(1)).setUser(user);
    }
}