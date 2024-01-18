package AlsongDalsong_backend.AlsongDalsong.domain.scrap;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 스크랩 테이블
 */
@Entity
@ToString
@Getter
@NoArgsConstructor
public class Scrap extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Scrap_Id")
    private Long id; // 기본키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private User userId; // 회원 외래키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Post_Id")
    private Post postId; // 게시글 외래키

    public void setUser(User user) {
        this.userId = user;
        if (!userId.getScrapList().contains(this)) {
            user.getScrapList().add(this);
        }
    }

    public void setPost(Post post) {
        this.postId = post;
    }
}
