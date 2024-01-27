package AlsongDalsong_backend.AlsongDalsong.web.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 수정 dto
 */
@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {
    @ApiModelProperty(notes = "댓글 기본키", example = "1", required = true)
    private Long id;

    @ApiModelProperty(notes = "댓글 작성자 이메일", example = "1234@gmail.com", required = true)
    private String email;

    @ApiModelProperty(notes = "게시글 id", example = "1", required = true)
    private Long postId;

    @ApiModelProperty(notes = "내용", example = "꼭 구매하세요!", required = true)
    private String content;

    @Builder
    public CommentUpdateRequestDto(Long id, String email, Long postId, String content) {
        this.id = id;
        this.email = email;
        this.postId = postId;
        this.content = content;
    }
}
