package AlsongDalsong_backend.AlsongDalsong.web.dto.vote;

import AlsongDalsong_backend.AlsongDalsong.domain.vote.Vote;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 투표 dto
 */
@Getter
@NoArgsConstructor
public class VoteRequestDto {

    @ApiModelProperty(notes = "투표하는 사람 이메일", example = "1234@gmail.com", required = true)
    private String email; // 이메일

    @ApiModelProperty(notes = "게시글 id", example = "1", required = true)
    private Long postId; // 게시글 id

    @ApiModelProperty(notes = "투표 (찬성이면 true, 반대면 false)", example = "true", required = true)
    private Boolean vote; // 투표
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