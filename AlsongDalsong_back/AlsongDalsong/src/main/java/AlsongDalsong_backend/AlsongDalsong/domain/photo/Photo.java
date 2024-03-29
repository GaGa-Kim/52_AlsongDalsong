package AlsongDalsong_backend.AlsongDalsong.domain.photo;

import AlsongDalsong_backend.AlsongDalsong.domain.BaseTimeEntity;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
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
 * 사진 테이블
 */
@Entity
@ToString
@Getter
@NoArgsConstructor
public class Photo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Photo_Id")
    private Long id; // 기본키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Post_Id")
    private Post postId; // 게시글 외래키

    @Column(nullable = false)
    private String origPhotoName; // 원본 이름

    @Column(nullable = false)
    private String photoName; // 변환된 사진 이름

    @Column(nullable = false)
    private String photoUrl; // 사진 Url

    @Builder
    public Photo(Long id, String origPhotoName, String photoName, String photoUrl) {
        this.id = id;
        this.origPhotoName = origPhotoName;
        this.photoName = photoName;
        this.photoUrl = photoUrl;
    }

    public void setPost(Post post) {
        this.postId = post;
        if (!postId.getPhotoList().contains(this)) {
            post.getPhotoList().add(this);
        }
    }
}
