package AlsongDalsong_backend.AlsongDalsong.web.dto.vote;

import AlsongDalsong_backend.AlsongDalsong.constants.Message;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.Vote;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 투표 dto
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteRequestDto {
    @Email(message = Message.INPUT_EMAIL)
    @ApiModelProperty(notes = "투표하는 사람 이메일", example = "1234@gmail.com", required = true)
    private String email;

    @NotNull(message = Message.INPUT_POST_ID)
    @ApiModelProperty(notes = "게시글 id", example = "1", required = true)
    private Long postId;

    @NotNull(message = Message.INPUT_VOTE)
    @ApiModelProperty(notes = "투표 (찬성이면 true, 반대면 false)", example = "true", required = true)
    private Boolean vote;

    @Builder
    public VoteRequestDto(String email, Long postId, Boolean vote) {
        this.email = email;
        this.postId = postId;
        this.vote = vote;
    }

    public Vote toEntity() {
        return Vote.builder()
                .vote(vote)
                .build();
    }
}