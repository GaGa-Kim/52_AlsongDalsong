package AlsongDalsong_backend.AlsongDalsong.web.dto.user;

import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 dto
 */
@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    @ApiModelProperty(notes = "이름", example = "김가경", required = true)
    private String name; // 이름

    @ApiModelProperty(notes = "이메일", example = "1234@gmail.com", required = true)
    private String email; // 이메일

    @ApiModelProperty(notes = "닉네임", example = "김가경", required = true)
    private String nickname; // 닉네임

    @ApiModelProperty(notes = "프로필 사진 Url", example = "www", required = true)
    private String profileUrl; // 프로필 사진 Url

    @ApiModelProperty(notes = "소개", example = "안녕하세요.", required = true)
    private String introduce; // 소개

    @Builder
    public UserSaveRequestDto(String name, String email, String nickname, String profileUrl, String introduce) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.introduce = introduce;
    }

    public User toEntity() {
        return User.builder()
                .kakaoId(null)
                .name(name)
                .email(email)
                .nickname(nickname)
                .profileUrl(profileUrl)
                .introduce(introduce)
                .role("ROLE_USER")
                .point(0)
                .build();
    }

}
