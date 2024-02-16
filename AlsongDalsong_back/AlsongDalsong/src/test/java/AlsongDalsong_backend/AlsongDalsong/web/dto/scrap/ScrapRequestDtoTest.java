package AlsongDalsong_backend.AlsongDalsong.web.dto.scrap;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import AlsongDalsong_backend.AlsongDalsong.ValidatorUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * ScrapRequestDto 검증 테스트
 */
class ScrapRequestDtoTest {
    private final ValidatorUtil<ScrapRequestDto> validatorUtil = new ValidatorUtil<>();

    @Test
    @DisplayName("ScrapRequestDto 생성 테스트")
    void testScrapRequestDto() {
        ScrapRequestDto scrapRequestDto = ScrapRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .build();

        assertEquals(VALID_EMAIL, scrapRequestDto.getEmail());
        assertEquals(VALID_POST_ID, scrapRequestDto.getPostId());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        ScrapRequestDto scrapRequestDto = new ScrapRequestDto();

        assertNotNull(scrapRequestDto);
        assertNull(scrapRequestDto.getEmail());
        assertNull(scrapRequestDto.getPostId());
    }

    @Test
    @DisplayName("이메일 유효성 검증 테스트")
    void email_validation() {
        ScrapRequestDto scrapRequestDto = ScrapRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .build();

        validatorUtil.validate(scrapRequestDto);
    }

    @Test
    @DisplayName("게시글 아이디 유효성 검증 테스트")
    void postId_validation() {
        ScrapRequestDto scrapRequestDto = ScrapRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .build();

        validatorUtil.validate(scrapRequestDto);
    }
}