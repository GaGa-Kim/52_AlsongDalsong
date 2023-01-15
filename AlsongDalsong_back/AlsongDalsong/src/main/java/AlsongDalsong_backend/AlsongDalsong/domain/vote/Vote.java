package AlsongDalsong_backend.AlsongDalsong.domain.vote;

import AlsongDalsong_backend.AlsongDalsong.domain.BaseTimeEntity;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

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
    public Vote(Boolean vote) {
        this.vote = vote;
    }

    // 투표 수정
    public Vote update(Boolean vote) {
        this.vote = vote;
        return this;
    }

    // 회원 연관관계 메소드
    public void setUser(User user) {
        this.userId = user;
        /*
        if(!userId.getVoteList().contains(this))
            user.getVoteList().add(this);
         */
    }

    // 게시글 연관관계 메소드
    public void setPost(Post post) {
        this.postId = post;
        if(!postId.getVoteList().contains(this))
            post.getVoteList().add(this);
    }
}
