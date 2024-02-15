package AlsongDalsong_backend.AlsongDalsong.web.dto.vote;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_VOTE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.ValidatorUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * VoteRequestDto 검증 테스트
 */
class VoteRequestDtoTest {
    private final ValidatorUtil<VoteRequestDto> validatorUtil = new ValidatorUtil<>();

    @Test
    @DisplayName("VoteRequestDto 생성 테스트")
    void testVoteRequestDto() {
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .vote(VALID_VOTE)
                .build();

        assertEquals(VALID_EMAIL, voteRequestDto.getEmail());
        assertEquals(VALID_POST_ID, voteRequestDto.getPostId());
        assertEquals(VALID_VOTE, voteRequestDto.getVote());
    }

    @Test
    @DisplayName("이메일 유효성 검증 테스트")
    void email_validation() {
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .email(INVALID_EMAIL)
                .postId(VALID_POST_ID)
                .vote(VALID_VOTE)
                .build();

        validatorUtil.validate(voteRequestDto);
    }

    @Test
    @DisplayName("게시글 아이디 유효성 검증 테스트")
    void postId_validation() {
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(null)
                .vote(VALID_VOTE)
                .build();

        validatorUtil.validate(voteRequestDto);
    }

    @Test
    @DisplayName("투표 내용 유효성 검증 테스트")
    void vote_validation() {
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .vote(null)
                .build();

        validatorUtil.validate(voteRequestDto);
    }
}