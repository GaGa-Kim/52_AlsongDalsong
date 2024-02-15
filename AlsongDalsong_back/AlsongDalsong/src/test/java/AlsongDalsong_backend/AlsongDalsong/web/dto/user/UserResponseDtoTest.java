package AlsongDalsong_backend.AlsongDalsong.web.dto.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * UserSaveResponseDto 검증 테스트
 */
class UserResponseDtoTest {
    @Test
    @DisplayName("UserResponseDto 생성 테스트")
    void testUserResponseDto() {
        User user = TestObjectFactory.initUser();
        user.addPostList(TestObjectFactory.initPost());
        user.addScrapList(TestObjectFactory.initScrap());

        UserResponseDto userResponseDto = new UserResponseDto(user);

        assertEquals(user.getId(), userResponseDto.getId());
        assertEquals(user.getKakaoId(), userResponseDto.getKakaoId());
        assertEquals(user.getName(), userResponseDto.getName());
        assertEquals(user.getEmail(), userResponseDto.getEmail());
        assertEquals(user.getNickname(), userResponseDto.getNickname());
        assertEquals(user.getIntroduce(), userResponseDto.getIntroduce());
        assertEquals(user.getRole().getRole(), userResponseDto.getRole());
        assertEquals(user.getPoint(), userResponseDto.getPoint());
        assertEquals(user.getSticker(), userResponseDto.getSticker());
        assertEquals(user.getWithdraw(), userResponseDto.getWithdraw());
    }
}