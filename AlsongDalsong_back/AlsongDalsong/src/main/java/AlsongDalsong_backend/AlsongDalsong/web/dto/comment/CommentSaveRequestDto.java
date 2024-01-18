package AlsongDalsong_backend.AlsongDalsong.web.dto.comment;

import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 저장 dto
 */
@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {
    @ApiModelProperty(notes = "댓글 작성자 이메일", example = "1234@gmail.com", required = true)
    private String email;

    @ApiModelProperty(notes = "게시글 id", example = "1", required = true)
    private Long postId;

    @ApiModelProperty(notes = "내용", example = "구매하세요", required = true)
    private String content;

    @Builder
    public CommentSaveRequestDto(String email, Long postId, String content) {
        this.email = email;
        this.postId = postId;
        this.content = content;
    }

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .build();
    }
}