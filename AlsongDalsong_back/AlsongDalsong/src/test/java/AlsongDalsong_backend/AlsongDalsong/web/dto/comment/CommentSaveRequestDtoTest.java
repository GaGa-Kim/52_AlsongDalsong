package AlsongDalsong_backend.AlsongDalsong.web.dto.comment;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_BLANK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_CONTENT;
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
 * CommentSaveRequestDto 검증 테스트
 */
class CommentSaveRequestDtoTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testCommentSaveRequestDto() {
        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .content(VALID_CONTENT)
                .build();

        assertEquals(VALID_EMAIL, commentSaveRequestDto.getEmail());
        assertEquals(VALID_POST_ID, commentSaveRequestDto.getPostId());
        assertEquals(VALID_CONTENT, commentSaveRequestDto.getContent());
    }

    @Test
    void email_validation() {
        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .email(INVALID_EMAIL)
                .postId(VALID_POST_ID)
                .content(VALID_CONTENT)
                .build();

        validate(commentSaveRequestDto);
    }

    @Test
    void postId_validation() {
        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(null)
                .content(VALID_CONTENT)
                .build();

        validate(commentSaveRequestDto);
    }

    @Test
    void content_validation() {
        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .content(INVALID_BLANK)
                .build();

        validate(commentSaveRequestDto);
    }

    void validate(CommentSaveRequestDto commentSaveRequestDto) {
        Set<ConstraintViolation<CommentSaveRequestDto>> violations = validator.validate(commentSaveRequestDto);
        for (ConstraintViolation<CommentSaveRequestDto> violation : violations) {
            System.err.println(violation.getMessage());
        }
    }
}