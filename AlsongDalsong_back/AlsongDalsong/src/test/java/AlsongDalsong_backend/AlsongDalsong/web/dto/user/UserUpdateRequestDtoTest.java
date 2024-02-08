package AlsongDalsong_backend.AlsongDalsong.web.dto.user;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_BLANK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_INTRODUCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NICKNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.ValidatorUtil;
import org.junit.jupiter.api.Test;

/**
 * UserUpdateRequestDto 검증 테스트
 */
class UserUpdateRequestDtoTest {
    private final ValidatorUtil<UserUpdateRequestDto> validatorUtil = new ValidatorUtil<>();

    @Test
    void testUserUpdateRequestDto() {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .email(VALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .introduce(VALID_INTRODUCE)
                .build();

        assertEquals(VALID_EMAIL, userUpdateRequestDto.getEmail());
        assertEquals(VALID_NICKNAME, userUpdateRequestDto.getNickname());
        assertEquals(VALID_INTRODUCE, userUpdateRequestDto.getIntroduce());
    }

    @Test
    void email_validation() {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .email(VALID_EMAIL)
                .nickname(INVALID_EMAIL)
                .introduce(VALID_INTRODUCE)
                .build();

        validatorUtil.validate(userUpdateRequestDto);
    }

    @Test
    void nickname_validation() {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .email(VALID_EMAIL)
                .nickname(INVALID_BLANK)
                .introduce(VALID_INTRODUCE)
                .build();

        validatorUtil.validate(userUpdateRequestDto);
    }

    @Test
    void introduce_validation() {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .email(VALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .introduce(INVALID_BLANK)
                .build();

        validatorUtil.validate(userUpdateRequestDto);
    }
}