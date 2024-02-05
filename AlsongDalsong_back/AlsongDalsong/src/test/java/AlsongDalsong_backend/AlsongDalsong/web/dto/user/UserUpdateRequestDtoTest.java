package AlsongDalsong_backend.AlsongDalsong.web.dto.user;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_NICKNAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_INTRODUCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NICKNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * UserUpdateRequestDto 검증 테스트
 */
class UserUpdateRequestDtoTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

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
    void name_validation() {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .email(VALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .introduce(VALID_INTRODUCE)
                .build();

        validate(userUpdateRequestDto);
    }

    @Test
    void email_validation() {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .email(INVALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .introduce(VALID_INTRODUCE)
                .build();

        validate(userUpdateRequestDto);
    }

    @Test
    void nickname_validation() {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .email(VALID_EMAIL)
                .nickname(INVALID_NICKNAME)
                .introduce(VALID_INTRODUCE)
                .build();

        validate(userUpdateRequestDto);
    }

    void validate(UserUpdateRequestDto userUpdateRequestDto) {
        Set<ConstraintViolation<UserUpdateRequestDto>> violations = validator.validate(userUpdateRequestDto);
        for (ConstraintViolation<UserUpdateRequestDto> violation : violations) {
            System.err.println(violation.getMessage());
        }
    }
}