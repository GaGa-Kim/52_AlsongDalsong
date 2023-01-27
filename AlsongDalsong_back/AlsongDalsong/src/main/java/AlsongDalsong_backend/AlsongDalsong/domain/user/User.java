package AlsongDalsong_backend.AlsongDalsong.domain.user;

import AlsongDalsong_backend.AlsongDalsong.domain.BaseTimeEntity;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.Scrap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private String role; // 권한

    @Column(nullable = false)
    private Integer point; // 포인트 적립

    @Column(nullable = false)
    private Integer sticker; // 스티커 갯수

    @Column(nullable = false)
    private Boolean withdraw; // 탈퇴 여부

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scrap> scrapList = new ArrayList<>(); // 회원 게시글 스크랩 리스트

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>(); // 회원 게시글 리스트

    /*
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>(); // 회원 댓글 리스트

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likeList = new ArrayList<>(); // 회원 댓글 좋아요 리스트

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> voteList = new ArrayList<>(); // 회원 게시글 투표 리스트
    */

    @Builder
    public User(Long kakaoId, String name, String email, String nickname, String profile, String introduce, String role, Integer point, Integer sticker, Boolean withdraw) {
        this.kakaoId = kakaoId;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.profile = profile;
        this.introduce = introduce;
        this.role = role;
        this.point = point;
        this.sticker = sticker;
        this.withdraw = withdraw;
    }

    // 회원 정보 수정
    public User update(String nickname, String introduce) {
        this.nickname = nickname;
        this.introduce = introduce;
        return this;
    }

    // 회원 프로필 사진 수정
    public User updateProfile(String profile) {
        this.profile = profile;
        return this;
    }

    // 회원 포인트 적립 수정
    public void updatePointAndSticker(Integer point, Integer sticker) {
        this.point = point;
        if (this.point >= 100) {
            this.point = point % 100;
            this.sticker = sticker + 1;
        }
    }

    // 회원 탈퇴 수정
    public void setWithdraw() {
        this.withdraw = true;
        this.nickname = "탈퇴한 회원";
    }

    // 스크랩 연관관계 메소드
    public void addScrapList(Scrap scrap) {
        this.scrapList.add(scrap);
        if(scrap.getUserId() != this) {
            scrap.setUser(this);
        }
    }

    // 게시글 연관관계 메소드
    public void addPostList(Post post) {
        this.postList.add(post);
        if(post.getUserId() != this) {
            post.setUser(this);
        }
    }

    /* 댓글 연관관계 메소드
    public void addCommentList(Comment comment) {
        this.commentList.add(comment);
        if(comment.getUserId() != this) {
            comment.setUser(this);
        }
    }

    // 댓글 좋아요 연관관계 메소드
    public void addLikeList(Like like) {
        this.likeList.add(like);
        if(like.getUserId() != this) {
            like.setUser(this);
        }
    }

    // 투표 연관관계 메소드
    public void addVoteList(Vote vote) {
        this.voteList.add(vote);
        if(vote.getUserId() != this) {
            vote.setUser(this);
        }
    }
     */
}