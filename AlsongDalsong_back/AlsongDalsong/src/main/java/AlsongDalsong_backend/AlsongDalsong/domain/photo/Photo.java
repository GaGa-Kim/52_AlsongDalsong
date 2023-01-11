package AlsongDalsong_backend.AlsongDalsong.domain.photo;

import AlsongDalsong_backend.AlsongDalsong.domain.BaseTimeEntity;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

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
    public Photo(String origPhotoName, String photoName, String photoUrl) {
        this.origPhotoName = origPhotoName;
        this.photoName = photoName;
        this.photoUrl = photoUrl;
    }

    // 게시글 연관관계 메소드
    public void setPost(Post post) {
        this.postId = post;
        if(!postId.getPhotoList().contains(this))
            post.getPhotoList().add(this);
    }
}
