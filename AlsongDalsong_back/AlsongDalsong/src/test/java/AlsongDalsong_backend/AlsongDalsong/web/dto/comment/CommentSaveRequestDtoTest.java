package AlsongDalsong_backend.AlsongDalsong.web.dto.comment;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_BLANK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_COMMENT_CONTENT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import AlsongDalsong_backend.AlsongDalsong.ValidatorUtil;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * CommentSaveRequestDto 검증 테스트
 */
class CommentSaveRequestDtoTest {
    private final ValidatorUtil<CommentSaveRequestDto> validatorUtil = new ValidatorUtil<>();

    @Test
    @DisplayName("CommentSaveRequestDto 생성 테스트")
    void testCommentSaveRequestDto() {
        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .content(VALID_COMMENT_CONTENT)
                .build();

        assertEquals(VALID_EMAIL, commentSaveRequestDto.getEmail());
        assertEquals(VALID_POST_ID, commentSaveRequestDto.getPostId());
        assertEquals(VALID_COMMENT_CONTENT, commentSaveRequestDto.getContent());
    }

    @Test
    @DisplayName("CommentSaveRequestDto toEntity 생성 테스트")
    void testCommentSaveRequestDtoEntity() {
        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .content(VALID_COMMENT_CONTENT)
                .build();

        Comment comment = commentSaveRequestDto.toEntity();

        assertNotNull(comment);
        assertEquals(VALID_COMMENT_CONTENT, comment.getContent());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        CommentSaveRequestDto commentSaveRequestDto = new CommentSaveRequestDto();

        assertNotNull(commentSaveRequestDto);
        assertNull(commentSaveRequestDto.getEmail());
        assertNull(commentSaveRequestDto.getPostId());
        assertNull(commentSaveRequestDto.getContent());
    }

    @Test
    @DisplayName("이메일 유효성 검증 테스트")
    void email_validation() {
        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .email(INVALID_EMAIL)
                .postId(VALID_POST_ID)
                .content(VALID_COMMENT_CONTENT)
                .build();

        validatorUtil.validate(commentSaveRequestDto);
    }

    @Test
    @DisplayName("게시글 아이디 유효성 검증 테스트")
    void postId_validation() {
        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(null)
                .content(VALID_COMMENT_CONTENT)
                .build();

        validatorUtil.validate(commentSaveRequestDto);
    }

    @Test
    @DisplayName("댓글 내용 유효성 검증 테스트")
    void content_validation() {
        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .content(INVALID_BLANK)
                .build();

        validatorUtil.validate(commentSaveRequestDto);
    }
}