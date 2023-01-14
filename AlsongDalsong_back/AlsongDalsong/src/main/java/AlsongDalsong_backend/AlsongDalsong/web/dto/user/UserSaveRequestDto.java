package AlsongDalsong_backend.AlsongDalsong.web.dto.user;

import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 가입 dto
 */
@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    @ApiModelProperty(notes = "이름", example = "김가경", required = true)
    private String name; // 이름

    @ApiModelProperty(notes = "이메일", example = "1234@gmail.com", required = true)
    private String email; // 이메일

    @ApiModelProperty(notes = "닉네임", example = "가경", required = true)
    private String nickname; // 닉네임

    @ApiModelProperty(notes = "프로필 사진", example = "http", required = true)
    private String profile; // 프로필 사진

    @ApiModelProperty(notes = "소개", example = "안녕하세요.", required = true)
    private String introduce; // 소개

    @Builder
    public UserSaveRequestDto(String name, String email, String nickname, String profile, String introduce) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.profile = profile;
        this.introduce = introduce;
    }

    public User toEntity() {
        return User.builder()
                .kakaoId(null)
                .name(name)
                .email(email)
                .nickname(nickname)
                .profile(profile)
                .introduce(introduce)
                .role("ROLE_USER")
                .point(0)
                .sticker(0)
                .withdraw(false)
                .build();
    }
}
