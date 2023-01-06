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
    private String origFileName; // 원본 이름

    @Column(nullable = false)
    private String fileName; // 변환된 파일 이름

    @Column(nullable = false)
    private String fileUrl; // 파일 Url

    @Builder
    public Photo(String origFileName, String fileName, String fileUrl) {
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    // 게시글 연관관계 메소드
    public void setPost(Post post) {
        this.postId = post;
        if(!postId.getPhotoList().contains(this))
            post.getPhotoList().add(this);
    }
}
