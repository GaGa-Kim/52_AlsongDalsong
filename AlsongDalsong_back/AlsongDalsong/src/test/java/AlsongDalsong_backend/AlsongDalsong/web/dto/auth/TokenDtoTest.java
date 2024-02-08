package AlsongDalsong_backend.AlsongDalsong.web.dto.auth;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_BLANK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.ValidatorUtil;
import org.junit.jupiter.api.Test;

/**
 * TokenDtoTest 검증 테스트
 */
class TokenDtoTest {
    private final ValidatorUtil<TokenDto> validatorUtil = new ValidatorUtil<>();

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

        validatorUtil.validate(tokenDto);
    }

    @Test
    void email_validation() {
        TokenDto tokenDto = TokenDto.builder()
                .token(VALID_TOKEN)
                .email(INVALID_EMAIL)
                .build();

        validatorUtil.validate(tokenDto);
    }
}