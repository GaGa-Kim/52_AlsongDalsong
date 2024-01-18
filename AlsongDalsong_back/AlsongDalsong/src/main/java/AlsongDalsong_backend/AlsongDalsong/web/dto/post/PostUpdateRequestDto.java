package AlsongDalsong_backend.AlsongDalsong.web.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * 게시글 수정 dto
 */
@Getter
public class PostUpdateRequestDto {
    @ApiModelProperty(notes = "게시글 기본키", example = "1")
    private Long id;

    @ApiModelProperty(notes = "회원 이메일", example = "1234@gmail.com", required = true)
    private String email;

    @ApiModelProperty(notes = "분류", example = "살까 말까", required = true)
    private String todo;

    @ApiModelProperty(notes = "카테고리", example = "패션", required = true)
    private String category;

    @ApiModelProperty(notes = "누가", example = "여성", required = true)
    private String who;

    @ApiModelProperty(notes = "연령", example = "20대", required = true)
    private String old;

    @ApiModelProperty(notes = "언제", example = "2022년 11월", required = true)
    private String date;

    @ApiModelProperty(notes = "무엇을", example = "신발", required = true)
    private String what;

    @ApiModelProperty(notes = "내용", example = "나이키 신발을~", required = true)
    private String content;

    @ApiModelProperty(notes = "링크", example = "www")
    private String link;

    @ApiModelProperty(notes = "중요도", example = "3", required = true)
    private Integer importance;

    @Builder
    public PostUpdateRequestDto(PostUpdateRequestVO postUpdateRequestVO) {
        this.id = postUpdateRequestVO.getId();
        this.email = postUpdateRequestVO.getEmail();
        this.todo = postUpdateRequestVO.getTodo();
        this.category = postUpdateRequestVO.getCategory();
        this.who = postUpdateRequestVO.getWho();
        this.old = postUpdateRequestVO.getOld();
        this.date = postUpdateRequestVO.getDate();
        this.what = postUpdateRequestVO.getWhat();
        this.content = postUpdateRequestVO.getContent();
        this.link = postUpdateRequestVO.getLink();
        this.importance = postUpdateRequestVO.getImportance();
    }
}
