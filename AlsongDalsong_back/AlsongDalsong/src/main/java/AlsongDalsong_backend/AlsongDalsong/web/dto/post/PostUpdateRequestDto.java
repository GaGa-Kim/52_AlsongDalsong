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
    private String email; // 회원

    @ApiModelProperty(notes = "분류", example = "살까 말까", required = true)
    private String todo; // 분류

    @ApiModelProperty(notes = "카테고리", example = "패션", required = true)
    private String category; // 카테고리

    @ApiModelProperty(notes = "누가", example = "여성", required = true)
    private String who; // 누가

    @ApiModelProperty(notes = "연령", example = "20대", required = true)
    private String old; // 연령

    @ApiModelProperty(notes = "언제", example = "2022년 11월", required = true)
    private String date; // 언제

    @ApiModelProperty(notes = "무엇을", example = "신발", required = true)
    private String what; // 무엇을

    @ApiModelProperty(notes = "내용", example = "나이키 신발을~", required = true)
    private String content; // 내용

    @ApiModelProperty(notes = "링크", example = "www")
    private String link; // 링크

    @ApiModelProperty(notes = "중요도", example = "3", required = true)
    private Integer importance; // 중요도

    @Builder
    public PostUpdateRequestDto(Long id, String email, String todo, String category, String who, String old, String date, String what, String content, String link, Integer importance) {
        this.id = id;
        this.email = email;
        this.todo = todo;
        this.category = category;
        this.who = who;
        this.old = old;
        this.date = date;
        this.what = what;
        this.content = content;
        this.link = link;
        this.importance = importance;
    }
}
