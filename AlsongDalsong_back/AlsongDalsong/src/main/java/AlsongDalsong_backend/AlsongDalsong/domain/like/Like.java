package AlsongDalsong_backend.AlsongDalsong.domain.like;

import AlsongDalsong_backend.AlsongDalsong.domain.BaseTimeEntity;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * 댓글 좋아요 테이블
 */
@Entity
@ToString
@Getter
@NoArgsConstructor
@Table(name = "likes")
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Like_Id")
    private Long id; // 기본키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private User userId; // 회원 외래키 (좋아요 누른 사람)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Comment_Id")
    private Comment commentId; // 댓글 외래키

    // 회원 연관관계 메소드
    public void setUser(User user) {
        this.userId = user;
        /*
        if(!userId.getLikeList().contains(this))
            user.getLikeList().add(this);
         */
    }

    // 댓글 연관관계 메소드
    public void setComment(Comment comment) {
        this.commentId = comment;
        if(!commentId.getLikeList().contains(this))
            comment.getLikeList().add(this);
    }

}
