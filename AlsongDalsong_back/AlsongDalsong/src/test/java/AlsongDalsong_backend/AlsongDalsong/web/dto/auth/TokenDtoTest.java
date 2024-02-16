package AlsongDalsong_backend.AlsongDalsong.web.dto.auth;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_BLANK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import AlsongDalsong_backend.AlsongDalsong.ValidatorUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * TokenDtoTest 검증 테스트
 */
class TokenDtoTest {
    private final ValidatorUtil<TokenDto> validatorUtil = new ValidatorUtil<>();

    @Test
    @DisplayName("TokenDto 생성 테스트")
    void testTokenDto() {
        TokenDto tokenDto = TokenDto.builder()
                .token(VALID_TOKEN)
                .email(VALID_EMAIL)
                .build();

        assertEquals(VALID_TOKEN, tokenDto.getToken());
        assertEquals(VALID_EMAIL, tokenDto.getEmail());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        TokenDto tokenDto = new TokenDto();

        assertNotNull(tokenDto);
        assertNull(tokenDto.getToken());
        assertNull(tokenDto.getEmail());
    }

    @Test
    @DisplayName("토큰 유효성 검증 테스트")
    void token_validation() {
        TokenDto tokenDto = TokenDto.builder()
                .token(INVALID_BLANK)
                .email(VALID_EMAIL)
                .build();

        validatorUtil.validate(tokenDto);
    }

    @Test
    @DisplayName("이메일 유효성 검증 테스트")
    void email_validation() {
        TokenDto tokenDto = TokenDto.builder()
                .token(VALID_TOKEN)
                .email(INVALID_EMAIL)
                .build();

        validatorUtil.validate(tokenDto);
    }
}