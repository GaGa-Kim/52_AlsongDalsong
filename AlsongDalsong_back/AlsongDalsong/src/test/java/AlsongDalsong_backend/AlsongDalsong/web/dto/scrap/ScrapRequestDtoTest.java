package AlsongDalsong_backend.AlsongDalsong.web.dto.scrap;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.ValidatorUtil;
import org.junit.jupiter.api.Test;

/**
 * ScrapRequestDto 검증 테스트
 */
class ScrapRequestDtoTest {
    private final ValidatorUtil<ScrapRequestDto> validatorUtil = new ValidatorUtil<>();

    @Test
    void testVoteRequestDto() {
        ScrapRequestDto scrapRequestDto = ScrapRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .build();

        assertEquals(VALID_EMAIL, scrapRequestDto.getEmail());
        assertEquals(VALID_POST_ID, scrapRequestDto.getPostId());
    }

    @Test
    void email_validation() {
        ScrapRequestDto scrapRequestDto = ScrapRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .build();

        validatorUtil.validate(scrapRequestDto);
    }

    @Test
    void postId_validation() {
        ScrapRequestDto scrapRequestDto = ScrapRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .build();

        validatorUtil.validate(scrapRequestDto);
    }
}