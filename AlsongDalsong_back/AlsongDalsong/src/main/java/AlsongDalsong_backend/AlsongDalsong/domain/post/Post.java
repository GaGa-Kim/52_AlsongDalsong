package AlsongDalsong_backend.AlsongDalsong.domain.post;

import AlsongDalsong_backend.AlsongDalsong.domain.BaseTimeEntity;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.Scrap;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.Vote;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private Todo todo; // 분류

    @Column(nullable = false)
    private Category category; // 카테고리

    @Column(nullable = false)
    private Who who; // 누가

    @Column(nullable = false)
    private Old old; // 연령

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
    private Decision decision; // 결정 완료 여부

    @Column(columnDefinition = "TEXT")
    private String reason; // 결정 이유

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photoList = new ArrayList<>(); // 게시글 사진 리스트

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>(); // 게시글 댓글 리스트

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> voteList = new ArrayList<>(); // 게시글 투표 리스트

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scrap> scrapList = new ArrayList<>(); // 게시글 스크랩 리스트

    @Builder
    public Post(Long id, Todo todo, Category category, Who who, Old old, String date, String what, String content,
                String link, Integer importance, Decision decision, String reason) {
        this.id = id;
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

    public Post update(String todo, String category, String who, String old, String date, String what, String content,
                       String link, Integer importance) {
        this.todo = Todo.ofTodo(todo);
        this.category = Category.ofCategory(category);
        this.who = Who.ofWho(who);
        this.old = Old.ofOld(old);
        this.date = date;
        this.what = what;
        this.content = content;
        this.link = link;
        this.importance = importance;
        return this;
    }

    public void setDecision(String decision, String reason) {
        this.decision = Decision.ofDecision(decision);
        this.reason = reason;
    }

    public void setUser(User user) {
        this.userId = user;
        if (!userId.getPostList().contains(this)) {
            user.getPostList().add(this);
        }
    }

    public void addPhotoList(Photo photo) {
        this.photoList.add(photo);
        if (photo.getPostId() != this) {
            photo.setPost(this);
        }
    }

    public void addCommentList(Comment comment) {
        this.commentList.add(comment);
        if (comment.getPostId() != this) {
            comment.setPost(this);
        }
    }

    public void addVoteList(Vote vote) {
        this.voteList.add(vote);
        if (vote.getPostId() != this) {
            vote.setPost(this);
        }
    }

    public void addScrapList(Scrap scrap) {
        this.scrapList.add(scrap);
        if (scrap.getPostId() != this) {
            scrap.setPost(this);
        }
    }
}
