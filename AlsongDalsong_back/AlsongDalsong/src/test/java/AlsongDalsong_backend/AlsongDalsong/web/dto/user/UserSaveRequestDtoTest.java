package AlsongDalsong_backend.AlsongDalsong.web.dto.user;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_BLANK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_INTRODUCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NICKNAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PROFILE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * UserSaveRequestDto 검증 테스트
 */
class UserSaveRequestDtoTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
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
    void name_validation() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .name(INVALID_BLANK)
                .email(VALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();

        validate(userSaveRequestDto);
    }

    @Test
    void email_validation() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .name(VALID_NAME)
                .email(INVALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();

        validate(userSaveRequestDto);
    }

    @Test
    void nickname_validation() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .name(VALID_NAME)
                .email(VALID_EMAIL)
                .nickname(INVALID_BLANK)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();

        validate(userSaveRequestDto);
    }

    void validate(UserSaveRequestDto userSaveRequestDto) {
        Set<ConstraintViolation<UserSaveRequestDto>> violations = validator.validate(userSaveRequestDto);
        for (ConstraintViolation<UserSaveRequestDto> violation : violations) {
            System.err.println(violation.getMessage());
        }
    }
}