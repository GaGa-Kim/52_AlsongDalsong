package AlsongDalsong_backend.AlsongDalsong.web.dto.scrap;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * ScrapRequestDto 검증 테스트
 */
class ScrapRequestDtoTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

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

        validate(scrapRequestDto);
    }

    @Test
    void postId_validation() {
        ScrapRequestDto scrapRequestDto = ScrapRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .build();

        validate(scrapRequestDto);
    }

    void validate(ScrapRequestDto scrapRequestDto) {
        Set<ConstraintViolation<ScrapRequestDto>> violations = validator.validate(scrapRequestDto);
        for (ConstraintViolation<ScrapRequestDto> violation : violations) {
            System.err.println(violation.getMessage());
        }
    }
}