package AlsongDalsong_backend.AlsongDalsong.web.dto.scrap;

import AlsongDalsong_backend.AlsongDalsong.constants.Message;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 스크랩 dto
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScrapRequestDto {
    @Email(message = Message.INPUT_EMAIL)
    @ApiModelProperty(notes = "좋아요 누르는 회원 이메일", example = "1234@gmail.com", required = true)
    private String email;

    @NotNull(message = Message.INPUT_POST_ID)
    @ApiModelProperty(notes = "게시글 id", example = "1", required = true)
    private Long postId;

    @Builder
    public ScrapRequestDto(String email, Long postId) {
        this.email = email;
        this.postId = postId;
    }
}