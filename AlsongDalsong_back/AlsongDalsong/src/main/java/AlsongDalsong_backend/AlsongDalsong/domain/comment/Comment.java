package AlsongDalsong_backend.AlsongDalsong.domain.comment;

import AlsongDalsong_backend.AlsongDalsong.domain.BaseTimeEntity;
import AlsongDalsong_backend.AlsongDalsong.domain.like.Like;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 댓글 테이블
 */
@Entity
@ToString
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Comment_Id")
    private Long id; // 기본키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private User userId; // 회원 외래키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Post_Id")
    private Post postId; // 게시글 외래키

    @Column(nullable = false)
    private String content; // 내용

    @OneToMany(mappedBy = "commentId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likeList = new ArrayList<>(); // 댓글 좋아요 리스트

    @Builder
    public Comment(String content) {
        this.content = content;
    }

    // 댓글 수정
    public Comment update(String content) {
        this.content = content;
        return this;
    }

    // 회원 연관관계 메소드
    public void setUser(User user) {
        this.userId = user;
        /*
        if(!userId.getCommentList().contains(this))
            user.getCommentList().add(this);
         */
    }

    // 게시글 연관관계 메소드
    public void setPost(Post post) {
        this.postId = post;
        if(!postId.getCommentList().contains(this))
            post.getCommentList().add(this);
    }

    // 댓글 좋아요 연관관계 메소드
    public void addLikeList(Like like) {
        this.likeList.add(like);
        if(like.getCommentId() != this) {
            like.setComment(this);
        }
    }
}
