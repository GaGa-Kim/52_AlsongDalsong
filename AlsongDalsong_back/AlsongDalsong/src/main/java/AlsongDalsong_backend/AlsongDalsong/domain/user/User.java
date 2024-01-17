package AlsongDalsong_backend.AlsongDalsong.domain.user;

import AlsongDalsong_backend.AlsongDalsong.constants.Message;
import AlsongDalsong_backend.AlsongDalsong.constants.Rule;
import AlsongDalsong_backend.AlsongDalsong.domain.BaseTimeEntity;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.Scrap;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 회원 테이블
 */
@Entity
@ToString
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id")
    private Long id; // 기본키

    // @Column(nullable = false)
    private Long kakaoId; // 카카오 아이디

    @Column(nullable = false)
    private String name; // 이름

    @Column(nullable = false)
    private String email; // 이메일

    @Column(nullable = false)
    private String nickname; // 닉네임

    private String profile; // 프로필 사진

    @Column(columnDefinition = "TEXT")
    private String introduce; // 소개

    @Column(nullable = false)
    private Role role; // 권한

    @Column(nullable = false)
    private Integer point; // 포인트 적립

    @Column(nullable = false)
    private Integer sticker; // 스티커 갯수

    @Column(nullable = false)
    private Boolean withdraw; // 탈퇴 여부

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Post> postList = new ArrayList<>(); // 회원 게시글 리스트

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Scrap> scrapList = new ArrayList<>(); // 회원 게시글 스크랩 리스트

    @Builder
    public User(Long kakaoId, String name, String email, String nickname, String profile, String introduce) {
        this.kakaoId = kakaoId;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.profile = profile;
        this.introduce = introduce;
        this.role = Role.USER;
        this.point = Rule.INITIAL_VALUE.getRule();
        this.sticker = Rule.INITIAL_VALUE.getRule();
        this.withdraw = false;
    }

    public void updateInfo(String nickname, String introduce) {
        this.nickname = nickname;
        this.introduce = introduce;
    }

    public void updateProfile(String profile) {
        this.profile = profile;
    }

    public void updatePoint(Integer updatedPoint) {
        this.point = updatedPoint;
        if (canAddStickers(this.point)) {
            addSticker();
            resetPoint();
        }
    }

    private boolean canAddStickers(Integer currentPoint) {
        return currentPoint >= Rule.STICKER_EARN_THRESHOLD.getRule();
    }

    private void addSticker() {
        this.sticker += Rule.STICKER_ADD_AMOUNT.getRule();
    }

    private void resetPoint() {
        this.point %= Rule.STICKER_EARN_THRESHOLD.getRule();
    }

    // 회원 탈퇴 수정
    public void withdrawUser() {
        this.withdraw = true;
        this.nickname = Message.WITHDRAW.getMessage();
    }

    // 게시글 연관관계 메소드
    public void addPostList(Post post) {
        this.postList.add(post);
        if (post.getUserId() != this) {
            post.setUser(this);
        }
    }

    // 스크랩 연관관계 메소드
    public void addScrapList(Scrap scrap) {
        this.scrapList.add(scrap);
        if (scrap.getUserId() != this) {
            scrap.setUser(this);
        }
    }
}