package AlsongDalsong_backend.AlsongDalsong.web.dto.post;

import AlsongDalsong_backend.AlsongDalsong.constants.Message;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Category;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Decision;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Old;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Todo;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Who;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시글 작성 dto
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveRequestDto {
    @Email(message = Message.INPUT_EMAIL)
    @ApiModelProperty(notes = "회원 이메일", example = "1234@gmail.com", required = true)
    private String email;

    @NotBlank(message = Message.INPUT_TODO)
    @ApiModelProperty(notes = "분류", example = "살까 말까", required = true)
    private String todo;

    @NotBlank(message = Message.INPUT_CATEGORY)
    @ApiModelProperty(notes = "카테고리", example = "패션", required = true)
    private String category;

    @NotBlank(message = Message.INPUT_WHO)
    @ApiModelProperty(notes = "누가", example = "여성", required = true)
    private String who;

    @NotBlank(message = Message.INPUT_OLD)
    @ApiModelProperty(notes = "연령", example = "20대", required = true)
    private String old;

    @NotBlank(message = Message.INPUT_DATE)
    @ApiModelProperty(notes = "언제", example = "2022년 11월", required = true)
    private String date;

    @NotBlank(message = Message.INPUT_WHAT)
    @ApiModelProperty(notes = "무엇을", example = "신발", required = true)
    private String what;

    @NotBlank(message = Message.INPUT_POST_CONTENT)
    @ApiModelProperty(notes = "내용", example = "나이키 신발을~", required = true)
    private String content;

    @ApiModelProperty(notes = "링크", example = "www")
    private String link;

    @NotNull(message = Message.INPUT_IMPORTANCE)
    @ApiModelProperty(notes = "중요도", example = "3", required = true)
    private Integer importance;

    @Builder
    public PostSaveRequestDto(PostSaveRequestVO postSaveRequestVO) {
        this.email = postSaveRequestVO.getEmail();
        this.todo = postSaveRequestVO.getTodo();
        this.category = postSaveRequestVO.getCategory();
        this.who = postSaveRequestVO.getWho();
        this.old = postSaveRequestVO.getOld();
        this.date = postSaveRequestVO.getDate();
        this.what = postSaveRequestVO.getWhat();
        this.content = postSaveRequestVO.getContent();
        this.link = postSaveRequestVO.getLink();
        this.importance = postSaveRequestVO.getImportance();
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
