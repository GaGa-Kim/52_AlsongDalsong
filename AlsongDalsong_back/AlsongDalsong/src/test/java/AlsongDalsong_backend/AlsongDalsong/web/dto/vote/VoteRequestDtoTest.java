package AlsongDalsong_backend.AlsongDalsong.web.dto.vote;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_VOTE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * VoteRequestDto 검증 테스트
 */
class VoteRequestDtoTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
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
    void email_validation() {
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .email(INVALID_EMAIL)
                .postId(VALID_POST_ID)
                .vote(VALID_VOTE)
                .build();

        validate(voteRequestDto);
    }

    @Test
    void postId_validation() {
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(null)
                .vote(VALID_VOTE)
                .build();

        validate(voteRequestDto);
    }

    @Test
    void vote_validation() {
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .vote(null)
                .build();

        validate(voteRequestDto);
    }

    void validate(VoteRequestDto voteRequestDto) {
        Set<ConstraintViolation<VoteRequestDto>> violations = validator.validate(voteRequestDto);
        for (ConstraintViolation<VoteRequestDto> violation : violations) {
            System.err.println(violation.getMessage());
        }
    }
}