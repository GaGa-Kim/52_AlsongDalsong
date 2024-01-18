package AlsongDalsong_backend.AlsongDalsong.web.dto.post;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Category;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Decision;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Old;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Todo;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Who;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시글 작성 dto
 */
@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
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
    public PostSaveRequestDto(String email, String todo, String category, String who, String old, String date,
                              String what, String content, String link, Integer importance) {
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

    public Post toEntity() {
        return Post.builder()
                .todo(Todo.ofTodo(todo))
                .category(Category.ofCategory(category))
                .who(Who.ofWho(who))
                .old(Old.ofOld(old))
                .date(date)
                .what(what)
                .content(content)
                .link(link)
                .importance(importance)
                .decision(Decision.UNDECIDED)
                .reason(null)
                .build();
    }
}
