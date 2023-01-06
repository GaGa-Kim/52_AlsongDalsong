package AlsongDalsong_backend.AlsongDalsong.domain.sticker;

import AlsongDalsong_backend.AlsongDalsong.domain.BaseTimeEntity;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * 스티커 테이블
 */
@Entity
@ToString
@Getter
@NoArgsConstructor
public class Sticker extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Sticker_Id")
    private Long id; // 기본키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private User userId; // 회원 외래키

    @Column(nullable = false)
    private String name; // 스티커 이름

    @Builder
    public Sticker(String name) {
        this.name = name;
    }

    // 회원 연관관계 메소드
    public void setUser(User user) {
        this.userId = user;
        if(!userId.getStickerList().contains(this))
            user.getStickerList().add(this);
    }
}
