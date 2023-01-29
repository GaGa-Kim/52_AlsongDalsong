package AlsongDalsong_backend.AlsongDalsong.web.dto.post;

import AlsongDalsong_backend.AlsongDalsong.domain.Time;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

/**
 * 게시글 정보 dto
 */
@Getter
public class PostResponseDto {

    @ApiModelProperty(notes = "게시글 기본키", example = "1")
    private Long id;

    @ApiModelProperty(notes = "생성 날짜 및 시간", example = "3분 전")
    private String createdDateTime; // 생성 날짜 및 시간

    @ApiModelProperty(notes = "작성자 이메일", example = "1234@gmail.com")
    private String email; // 작성자 이메일

    @ApiModelProperty(notes = "작성자 닉네임", example = "가경")
    private String nickname; // 작성자 닉네임

    @ApiModelProperty(notes = "분류", example = "살까 말까")
    private String todo; // 분류

    @ApiModelProperty(notes = "카테고리", example = "패션")
    private String category; // 카테고리

    @ApiModelProperty(notes = "누가", example = "여성")
    private String who; // 누가

    @ApiModelProperty(notes = "연령", example = "20대")
    private String old; // 연령

    @ApiModelProperty(notes = "언제", example = "2022-01-02")
    private String date; // 언제

    @ApiModelProperty(notes = "무엇을", example = "신발")
    private String what; // 무엇을

    @ApiModelProperty(notes = "내용", example = "나이키 신발을~")
    private String content; // 내용

    @ApiModelProperty(notes = "링크", example = "www")
    private String link; // 링크

    @ApiModelProperty(notes = "중요도", example = "3")
    private Integer importance; // 중요도

    @ApiModelProperty(notes = "결정 완료 여부", example = "미정")
    private String decision; // 결정 완료 여부

    @ApiModelProperty(notes = "결정 이유", example = "필요없어서")
    private String reason; // 결정 이유

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

    public PostResponseDto(Post post, List<Long> photoId, Long agree, Long disagree) {
        this.id = post.getId();
        this.createdDateTime = Time.calculateTime(post.getCreatedDateTime());
        this.email = post.getUserId().getEmail();
        this.nickname = post.getUserId().getNickname();
        this.todo = post.getTodo();
        this.category = post.getCategory();
        this.who = post.getWho();
        this.old = post.getOld();
        this.date = post.getDate();
        this.what = post.getWhat();
        this.content = post.getContent();
        this.link = post.getLink();
        this.importance = post.getImportance();
        this.decision = post.getDecision();
        this.reason = post.getReason();
        this.photoId = photoId;
        this.agree = agree;
        this.disagree = disagree;
        this.comment = post.getCommentList().size();
        this.scrap = post.getScrapList().size();
    }
}
