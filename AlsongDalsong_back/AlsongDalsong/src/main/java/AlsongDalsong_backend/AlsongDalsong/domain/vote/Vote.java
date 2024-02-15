package AlsongDalsong_backend.AlsongDalsong.domain.vote;

import AlsongDalsong_backend.AlsongDalsong.domain.BaseTimeEntity;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 투표 테이블
 */
@Entity
@ToString
@Getter
@NoArgsConstructor
public class Vote extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Vote_Id")
    private Long id; // 기본키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private User userId; // 회원 외래키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Post_Id")
    private Post postId; // 게시글 외래키

    @Column(nullable = false)
    private Boolean vote; // 투표

    @Builder
    public Vote(Long id, Boolean vote) {
        this.id = id;
        this.vote = vote;
    }

    public Vote update(Boolean vote) {
        this.vote = vote;
        return this;
    }

    public void setUser(User user) {
        this.userId = user;
    }

    public void setPost(Post post) {
        this.postId = post;
        if (!postId.getVoteList().contains(this)) {
            post.getVoteList().add(this);
        }
    }
}
