package AlsongDalsong_backend.AlsongDalsong.web.dto.comment;

import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 댓글 조회 dto
 */
@Getter
public class CommentResponseDto {

    @ApiModelProperty(notes = "댓글 기본키", example = "1")
    private Long id;

    @ApiModelProperty(notes = "생성 날짜 및 시간", example = "2023-01-15T16:34:30.388")
    private LocalDateTime createdDateTime; // 생성 날짜 및 시간

    @ApiModelProperty(notes = "댓글 작성자 이메일", example = "1234@gmail.com")
    private String email; // 작성자 이메일

    @ApiModelProperty(notes = "댓글 작성자 닉네임", example = "가경")
    private String nickname; // 작성자 닉네임

    @ApiModelProperty(notes = "댓글 작성자 프로필 사진", example = "http")
    private String profile; // 프로필 사진

    @ApiModelProperty(notes = "게시글 기본키", example = "1")
    private Long postId; // 게시글 기본키

    @ApiModelProperty(notes = "내용", example = "꼭 구매하세요")
    private String content; // 내용

    @ApiModelProperty(notes = "좋아요 갯수", example = "3")
    private Integer like; // 좋아요 갯수

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.createdDateTime = comment.getCreatedDateTime();
        this.email = comment.getUserId().getEmail();
        this.nickname = comment.getUserId().getNickname();
        this.profile = comment.getUserId().getProfile();
        this.postId = comment.getPostId().getId();
        this.content = comment.getContent();
        this.like = comment.getLikeList().size();
    }
}