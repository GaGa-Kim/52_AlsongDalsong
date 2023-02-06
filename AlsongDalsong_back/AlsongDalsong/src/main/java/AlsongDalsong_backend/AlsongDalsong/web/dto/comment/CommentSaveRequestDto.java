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
    private String email; // 회원 이메일

    @ApiModelProperty(notes = "게시글 id", example = "1", required = true)
    private Long postId; // 게시글 id

    @ApiModelProperty(notes = "내용", example = "구매하세요", required = true)
    private String content; // 내용

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