package AlsongDalsong_backend.AlsongDalsong.web.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * 댓글 수정 dto
 */
@Getter
public class CommentUpdateRequestDto {

    @ApiModelProperty(notes = "댓글 기본키", example = "1", required = true)
    private Long id; // 댓글 기본키

    @ApiModelProperty(notes = "댓글 작성자 이메일", example = "1234@gmail.com", required = true)
    private String email; // 회원 이메일

    @ApiModelProperty(notes = "게시글 id", example = "1", required = true)
    private Long postId; // 게시글 id

    @ApiModelProperty(notes = "내용", example = "꼭 구매하세요!", required = true)
    private String content; // 내용

    @Builder
    public CommentUpdateRequestDto(Long id, String email, Long postId, String content) {
        this.id = id;
        this.email = email;
        this.postId = postId;
        this.content = content;
    }
}
