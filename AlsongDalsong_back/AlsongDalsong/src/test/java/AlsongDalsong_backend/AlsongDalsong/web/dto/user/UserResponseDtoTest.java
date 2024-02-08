package AlsongDalsong_backend.AlsongDalsong.web.dto.user;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_INTRODUCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_KAKAO_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NICKNAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PROFILE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import org.junit.jupiter.api.Test;

/**
 * UserSaveResponseDto 검증 테스트
 */
class UserResponseDtoTest {
    @Test
    void testUserResponseDto() {
        User user = User.builder()
                .id(VALID_USER_ID)
                .kakaoId(VALID_KAKAO_ID)
                .name(VALID_NAME)
                .email(VALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();

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