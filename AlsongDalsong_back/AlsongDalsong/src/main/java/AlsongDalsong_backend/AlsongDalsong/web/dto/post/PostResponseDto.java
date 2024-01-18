package AlsongDalsong_backend.AlsongDalsong.web.dto.post;

import AlsongDalsong_backend.AlsongDalsong.domain.Time;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Getter;
import org.springframework.data.util.Pair;

/**
 * 게시글 정보 dto
 */
@Getter
public class PostResponseDto {
    @ApiModelProperty(notes = "게시글 기본키", example = "1")
    private Long id;

    @ApiModelProperty(notes = "생성 날짜 및 시간", example = "3분 전")
    private String createdDateTime;

    @ApiModelProperty(notes = "작성자 이메일", example = "1234@gmail.com")
    private String email;

    @ApiModelProperty(notes = "작성자 닉네임", example = "가경")
    private String nickname;

    @ApiModelProperty(notes = "분류", example = "살까 말까")
    private String todo;

    @ApiModelProperty(notes = "카테고리", example = "패션")
    private String category;

    @ApiModelProperty(notes = "누가", example = "여성")
    private String who;

    @ApiModelProperty(notes = "연령", example = "20대")
    private String old;

    @ApiModelProperty(notes = "언제", example = "2022-01-02")
    private String date;

    @ApiModelProperty(notes = "무엇을", example = "신발")
    private String what;

    @ApiModelProperty(notes = "내용", example = "나이키 신발을~")
    private String content;

    @ApiModelProperty(notes = "링크", example = "www")
    private String link;

    @ApiModelProperty(notes = "중요도", example = "3")
    private Integer importance;

    @ApiModelProperty(notes = "결정 완료 여부", example = "미정")
    private String decision;

    @ApiModelProperty(notes = "결정 이유", example = "필요없어서")
    private String reason;

    @ApiModelProperty(notes = "게시글 사진 id", example = "[1, 2]")
    private List<Long> photoId;

    @ApiModelProperty(notes = "찬성 투표 수", example = "3")
    private Long agree;

    @ApiModelProperty(notes = "반대 투표 수", example = "3")
    private Long disagree;

    @ApiModelProperty(notes = "댓글 수", example = "3")
    private Integer comment;

    @ApiModelProperty(notes = "스크랩 수", example = "3")
    private Integer scrap;

    public PostResponseDto(Post post, List<Long> photoId, Pair<Long, Long> vote) {
        this.id = post.getId();
        this.createdDateTime = Time.calculateTime(post.getCreatedDateTime());
        this.email = post.getUserId().getEmail();
        this.nickname = post.getUserId().getNickname();
        this.todo = post.getTodo().getTodo();
        this.category = post.getCategory().getCategory();
        this.who = post.getWho().getWho();
        this.old = post.getOld().getOld();
        this.date = post.getDate();
        this.what = post.getWhat();
        this.content = post.getContent();
        this.link = post.getLink();
        this.importance = post.getImportance();
        this.decision = post.getDecision().getDecision();
        this.reason = post.getReason();
        this.photoId = photoId;
        this.agree = vote.getFirst();
        this.disagree = vote.getSecond();
        this.comment = post.getCommentList().size();
        this.scrap = post.getScrapList().size();
    }
}
