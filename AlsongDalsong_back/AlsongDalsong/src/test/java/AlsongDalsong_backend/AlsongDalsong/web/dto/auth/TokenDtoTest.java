package AlsongDalsong_backend.AlsongDalsong.web.dto.auth;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_BLANK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * TokenDtoTest 검증 테스트
 */
class TokenDtoTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testTokenDto() {
        TokenDto tokenDto = TokenDto.builder()
                .token(VALID_TOKEN)
                .email(VALID_EMAIL)
                .build();

        assertEquals(VALID_TOKEN, tokenDto.getToken());
        assertEquals(VALID_EMAIL, tokenDto.getEmail());
    }

    @Test
    void token_validation() {
        TokenDto tokenDto = TokenDto.builder()
                .token(INVALID_BLANK)
                .email(VALID_EMAIL)
                .build();

        validate(tokenDto);
    }

    @Test
    void email_validation() {
        TokenDto tokenDto = TokenDto.builder()
                .token(VALID_TOKEN)
                .email(INVALID_EMAIL)
                .build();

        validate(tokenDto);
    }

    void validate(TokenDto tokenDto) {
        Set<ConstraintViolation<TokenDto>> violations = validator.validate(tokenDto);
        for (ConstraintViolation<TokenDto> violation : violations) {
            System.err.println(violation.getMessage());
        }
    }
}