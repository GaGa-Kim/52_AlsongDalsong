package AlsongDalsong_backend.AlsongDalsong.web.dto.comment;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_BLANK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_COMMENT_CONTENT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_COMMENT_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import AlsongDalsong_backend.AlsongDalsong.ValidatorUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * CommentUpdateRequestDto 검증 테스트
 */
class CommentUpdateRequestDtoTest {
    private final ValidatorUtil<CommentUpdateRequestDto> validatorUtil = new ValidatorUtil<>();

    @Test
    @DisplayName("CommentUpdateRequestDto 생성 테스트")
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
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        CommentUpdateRequestDto commentUpdateRequestDto = new CommentUpdateRequestDto();

        assertNotNull(commentUpdateRequestDto);
        assertNull(commentUpdateRequestDto.getId());
        assertNull(commentUpdateRequestDto.getEmail());
        assertNull(commentUpdateRequestDto.getPostId());
        assertNull(commentUpdateRequestDto.getContent());
    }

    @Test
    @DisplayName("댓글 아이디 유효성 검증 테스트")
    void commentId_validation() {
        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .id(null)
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .content(VALID_COMMENT_CONTENT)
                .build();

        validatorUtil.validate(commentUpdateRequestDto);
    }

    @Test
    @DisplayName("이메일 유효성 검증 테스트")
    void email_validation() {
        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .id(VALID_COMMENT_ID)
                .email(INVALID_EMAIL)
                .postId(VALID_POST_ID)
                .content(VALID_COMMENT_CONTENT)
                .build();

        validatorUtil.validate(commentUpdateRequestDto);
    }

    @Test
    @DisplayName("게시글 아이디 유효성 검증 테스트")
    void postId_validation() {
        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .id(VALID_COMMENT_ID)
                .email(VALID_EMAIL)
                .postId(null)
                .content(VALID_COMMENT_CONTENT)
                .build();

        validatorUtil.validate(commentUpdateRequestDto);
    }

    @Test
    @DisplayName("댓글 내용 유효성 검증 테스트")
    void content_validation() {
        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .id(VALID_COMMENT_ID)
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .content(INVALID_BLANK)
                .build();

        validatorUtil.validate(commentUpdateRequestDto);
    }
}