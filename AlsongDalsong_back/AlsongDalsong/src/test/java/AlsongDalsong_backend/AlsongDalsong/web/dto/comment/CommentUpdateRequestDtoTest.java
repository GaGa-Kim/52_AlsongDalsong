package AlsongDalsong_backend.AlsongDalsong.web.dto.comment;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_BLANK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_COMMENT_CONTENT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_COMMENT_ID;
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
 * CommentUpdateRequestDto 검증 테스트
 */
class CommentUpdateRequestDtoTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testCommentUpdateRequestDto() {
        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .id(VALID_COMMENT_ID)
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .content(VALID_COMMENT_CONTENT)
                .build();

        assertEquals(VALID_COMMENT_ID, commentUpdateRequestDto.getId());
        assertEquals(VALID_EMAIL, commentUpdateRequestDto.getEmail());
        assertEquals(VALID_POST_ID, commentUpdateRequestDto.getPostId());
        assertEquals(VALID_COMMENT_CONTENT, commentUpdateRequestDto.getContent());
    }

    @Test
    void commentId_validation() {
        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .id(VALID_COMMENT_ID)
                .email(VALID_EMAIL)
                .postId(null)
                .content(VALID_COMMENT_CONTENT)
                .build();

        validate(commentUpdateRequestDto);
    }

    @Test
    void email_validation() {
        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .id(VALID_COMMENT_ID)
                .email(INVALID_EMAIL)
                .postId(VALID_POST_ID)
                .content(VALID_COMMENT_CONTENT)
                .build();

        validate(commentUpdateRequestDto);
    }

    @Test
    void postId_validation() {
        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .id(VALID_COMMENT_ID)
                .email(VALID_EMAIL)
                .postId(null)
                .content(VALID_COMMENT_CONTENT)
                .build();

        validate(commentUpdateRequestDto);
    }

    @Test
    void content_validation() {
        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .id(VALID_COMMENT_ID)
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .content(INVALID_BLANK)
                .build();

        validate(commentUpdateRequestDto);
    }

    void validate(CommentUpdateRequestDto commentUpdateRequestDto) {
        Set<ConstraintViolation<CommentUpdateRequestDto>> violations = validator.validate(commentUpdateRequestDto);
        for (ConstraintViolation<CommentUpdateRequestDto> violation : violations) {
            System.err.println(violation.getMessage());
        }
    }
}