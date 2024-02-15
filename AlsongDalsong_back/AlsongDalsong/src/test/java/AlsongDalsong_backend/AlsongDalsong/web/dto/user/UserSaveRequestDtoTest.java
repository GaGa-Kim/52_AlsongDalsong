package AlsongDalsong_backend.AlsongDalsong.web.dto.user;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_BLANK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_INTRODUCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NICKNAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PROFILE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.ValidatorUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * UserSaveRequestDto 검증 테스트
 */
class UserSaveRequestDtoTest {
    private final ValidatorUtil<UserSaveRequestDto> validatorUtil = new ValidatorUtil<>();

    @Test
    @DisplayName("UserSaveRequestDto 생성 테스트")
    void testUserSaveRequestDto() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .name(VALID_NAME)
                .email(VALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();

        assertEquals(VALID_NAME, userSaveRequestDto.getName());
        assertEquals(VALID_EMAIL, userSaveRequestDto.getEmail());
        assertEquals(VALID_NICKNAME, userSaveRequestDto.getNickname());
        assertEquals(VALID_PROFILE, userSaveRequestDto.getProfile());
        assertEquals(VALID_INTRODUCE, userSaveRequestDto.getIntroduce());
    }

    @Test
    @DisplayName("이름 유효성 검증 테스트")
    void name_validation() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .name(INVALID_BLANK)
                .email(VALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();

        validatorUtil.validate(userSaveRequestDto);
    }

    @Test
    @DisplayName("이메일 유효성 검증 테스트")
    void email_validation() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .name(VALID_NAME)
                .email(INVALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();

        validatorUtil.validate(userSaveRequestDto);
    }

    @Test
    @DisplayName("닉네임 유효성 검증 테스트")
    void nickname_validation() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .name(VALID_NAME)
                .email(VALID_EMAIL)
                .nickname(INVALID_BLANK)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();

        validatorUtil.validate(userSaveRequestDto);
    }
}