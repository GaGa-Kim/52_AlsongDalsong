package AlsongDalsong_backend.AlsongDalsong.web.dto.like;

import AlsongDalsong_backend.AlsongDalsong.constants.Message;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 좋아요 dto
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeRequestDto {
    @Email(message = Message.INPUT_EMAIL)
    @ApiModelProperty(notes = "좋아요 누르는 회원 이메일", example = "1234@gmail.com", required = true)
    private String email;

    @NotNull(message = Message.INPUT_COMMENT_ID)
    @ApiModelProperty(notes = "댓글 id", example = "1", required = true)
    private Long commentId;

    @Builder
    public LikeRequestDto(String email, Long commentId) {
        this.email = email;
        this.commentId = commentId;
    }
}