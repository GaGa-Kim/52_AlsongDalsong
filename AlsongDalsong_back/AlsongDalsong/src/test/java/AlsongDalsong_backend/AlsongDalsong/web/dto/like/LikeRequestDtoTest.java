package AlsongDalsong_backend.AlsongDalsong.web.dto.like;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_COMMENT_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.ValidatorUtil;
import org.junit.jupiter.api.Test;

/**
 * LikeRequestDto 검증 테스트
 */
class LikeRequestDtoTest {
    private final ValidatorUtil<LikeRequestDto> validatorUtil = new ValidatorUtil<>();

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

        validatorUtil.validate(likeRequestDto);
    }

    @Test
    void commentId_validation() {
        LikeRequestDto likeRequestDto = LikeRequestDto.builder()
                .email(VALID_EMAIL)
                .commentId(null)
                .build();

        validatorUtil.validate(likeRequestDto);
    }
}