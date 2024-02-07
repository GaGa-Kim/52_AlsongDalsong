package AlsongDalsong_backend.AlsongDalsong.web.dto.like;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_COMMENT_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * LikeRequestDto 검증 테스트
 */
class LikeRequestDtoTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testLikeRequestDto() {
        LikeRequestDto likeRequestDto = LikeRequestDto.builder()
                .email(VALID_EMAIL)
                .commentId(VALID_COMMENT_ID)
                .build();

        assertEquals(VALID_EMAIL, likeRequestDto.getEmail());
        assertEquals(VALID_COMMENT_ID, likeRequestDto.getCommentId());
    }

    @Test
    void email_validation() {
        LikeRequestDto likeRequestDto = LikeRequestDto.builder()
                .email(INVALID_EMAIL)
                .commentId(VALID_COMMENT_ID)
                .build();

        validate(likeRequestDto);
    }

    @Test
    void commentId_validation() {
        LikeRequestDto likeRequestDto = LikeRequestDto.builder()
                .email(VALID_EMAIL)
                .commentId(null)
                .build();

        validate(likeRequestDto);
    }

    void validate(LikeRequestDto likeRequestDto) {
        Set<ConstraintViolation<LikeRequestDto>> violations = validator.validate(likeRequestDto);
        for (ConstraintViolation<LikeRequestDto> violation : violations) {
            System.err.println(violation.getMessage());
        }
    }
}