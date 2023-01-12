package AlsongDalsong_backend.AlsongDalsong.domain.post;

import AlsongDalsong_backend.AlsongDalsong.domain.BaseTimeEntity;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.Scrap;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.Vote;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 게시글 테이블
 */
@Entity
@ToString
@Getter
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Post_Id")
    private Long id; // 기본키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private User userId; // 회원 외래키

    @Column(nullable = false)
    private String todo; // 분류

    @Column(nullable = false)
    private String category; // 카테고리

    @Column(nullable = false)
    private String who; // 누가

    @Column(nullable = false)
    private String old; // 연령

    @Column(nullable = false)
    private String date; // 언제

    @Column(nullable = false)
    private String what; // 무엇을

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 내용

    private String link; // 링크

    @Column(nullable = false)
    private Integer importance; // 중요도

    @Column(nullable = false)
    private String decision; // 결정 완료 여부

    @Column(columnDefinition = "TEXT")
    private String reason; // 결정 이유

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scrap> scrapList = new ArrayList<>(); // 게시글 스크랩 리스트

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photoList = new ArrayList<>(); // 게시글 사진 리스트

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>(); // 게시글 댓글 리스트

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> voteList = new ArrayList<>(); // 게시글 투표 리스트

    @Builder
    public Post(String todo, String category, String who, String old, String date, String what, String content, String link, Integer importance, String decision, String reason) {
        this.todo = todo;
        this.category = category;
        this.who = who;
        this.old = old;
        this.date = date;
        this.what = what;
        this.content = content;
        this.link = link;
        this.importance = importance;
        this.decision = decision;
        this.reason = reason;
    }

    // 게시글 수정
    public Post update(String todo, String category, String who, String old, String date, String what, String content, String link, Integer importance) {
        this.todo = todo;
        this.category = category;
        this.who = who;
        this.old = old;
        this.date = date;
        this.what = what;
        this.content = content;
        this.link = link;
        this.importance = importance;
        return this;
    }

    // 게시글 확정
    public void setDecision(String decision, String reason) {
        this.decision = decision;
        this.reason = reason;
    }

    // 회원 연관관계 메소드
    public void setUser(User user) {
        this.userId = user;
        if(!userId.getPostList().contains(this))
            user.getPostList().add(this);
    }

    // 스크랩 연관관계 메소드
    public void addScrapList(Scrap scrap) {
        this.scrapList.add(scrap);
        if(scrap.getPostId() != this) {
            scrap.setPost(this);
        }
    }

    // 사진 연관관계 메소드
    public void addPhotoList(Photo photo) {
        this.photoList.add(photo);
        if(photo.getPostId() != this) {
            photo.setPost(this);
        }
    }

    // 댓글 연관관계 메소드
    public void addCommentList(Comment comment) {
        this.commentList.add(comment);
        if(comment.getPostId() != this) {
            comment.setPost(this);
        }
    }

    // 투표 연관관계 메소드
    public void addVoteList(Vote vote) {
        this.voteList.add(vote);
        if(vote.getPostId() != this) {
            vote.setPost(this);
        }
    }
}
