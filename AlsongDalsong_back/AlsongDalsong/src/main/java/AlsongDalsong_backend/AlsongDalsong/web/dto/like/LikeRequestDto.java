package AlsongDalsong_backend.AlsongDalsong.web.dto.like;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 좋아요 dto
 */
@Getter
@NoArgsConstructor
public class LikeRequestDto {

    @ApiModelProperty(notes = "좋아요 누르는 회원 이메일", example = "1234@gmail.com", required = true)
    private String email; // 회원

    @ApiModelProperty(notes = "댓글 id", example = "1", required = true)
    private Long commentId; // 댓글 id

    @Builder
    public LikeRequestDto(String email, Long commentId) {
        this.email = email;
        this.commentId = commentId;
    }
}