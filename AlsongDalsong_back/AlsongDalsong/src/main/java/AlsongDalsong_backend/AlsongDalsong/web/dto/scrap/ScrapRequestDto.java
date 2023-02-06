package AlsongDalsong_backend.AlsongDalsong.web.dto.scrap;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 스크랩 dto
 */
@Getter
@NoArgsConstructor
public class ScrapRequestDto {

    @ApiModelProperty(notes = "좋아요 누르는 회원 이메일", example = "1234@gmail.com", required = true)
    private String email; // 회원

    @ApiModelProperty(notes = "게시글 id", example = "1", required = true)
    private Long postId; // 게시글 id

    @Builder
    public ScrapRequestDto(String email, Long postId) {
        this.email = email;
        this.postId = postId;
    }
}