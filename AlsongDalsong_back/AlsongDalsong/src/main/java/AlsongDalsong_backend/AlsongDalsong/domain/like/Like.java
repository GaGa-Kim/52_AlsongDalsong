package AlsongDalsong_backend.AlsongDalsong.domain.like;

import AlsongDalsong_backend.AlsongDalsong.domain.BaseTimeEntity;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @Builder
    public Like(Long id) {
        this.id = id;
    }
    
    public void setUser(User user) {
        this.userId = user;
    }

    public void setComment(Comment comment) {
        this.commentId = comment;
        if (!commentId.getLikeList().contains(this)) {
            comment.getLikeList().add(this);
        }
    }
}
