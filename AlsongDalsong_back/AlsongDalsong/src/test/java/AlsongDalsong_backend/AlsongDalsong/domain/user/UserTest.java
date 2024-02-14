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
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("회원 정보 수정 테스트")
    void testUpdateInfo() {
        String newNickName = "달송이";
        String newIntroduce = "안녕하십니까";
        user.updateInfo(newNickName, newIntroduce);

        assertEquals(newNickName, user.getNickname());
        assertEquals(newIntroduce, user.getIntroduce());
    }

    @Test
    @DisplayName("회원 프로필 사진 수정 테스트")
    void testUpdateProfile() {
        String newProfile = "profile.png";
        user.updateProfile(newProfile);

        assertEquals(newProfile, user.getProfile());
    }

    @Test
    @DisplayName("스티커 추가 없이 포인트 업데이트 테스트")
    void testUpdatePoint_canNotAddStickers() {
        int point = 50;
        user.updatePoint(point);

        assertEquals(point, user.getPoint());
        assertEquals(0, user.getSticker());
    }

    @Test
    @DisplayName("스티커 추가 및 포인트 업데이트 테스트")
    void testUpdatePoint_canAddStickers() {
        int point = 101;
        user.updatePoint(point);

        assertEquals(point % Rule.STICKER_EARN_THRESHOLD.getRule(), user.getPoint());
        assertEquals(Rule.STICKER_ADD_AMOUNT.getRule(), user.getSticker());
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    void testWithdrawUser() {
        assertFalse(user.getWithdraw());

        user.withdrawUser();
        assertTrue(user.getWithdraw());
        assertEquals("탈퇴한 회원", user.getNickname());
    }

    @Test
    @DisplayName("회원의 게시글 연관관계 설정 테스트")
    void testAddPostList() {
        Post post = mock(Post.class);
        user.addPostList(post);

        assertTrue(user.getPostList().contains(post));
        verify(post, times(1)).setUser(user);
    }

    @Test
    @DisplayName("회원의 스크랩 연관관계 설정 테스트")
    void testAddScrapList() {
        Scrap scrap = mock(Scrap.class);
        user.addScrapList(scrap);

        assertTrue(user.getScrapList().contains(scrap));
        verify(scrap, times(1)).setUser(user);
    }
}