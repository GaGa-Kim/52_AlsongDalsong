package AlsongDalsong_backend.AlsongDalsong.web.dto.user;

import AlsongDalsong_backend.AlsongDalsong.constants.Message;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 가입 dto
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserSaveRequestDto {
    @NotBlank(message = Message.INPUT_NAME)
    @ApiModelProperty(notes = "이름", example = "김가경", required = true)
    private String name;

    @Email(message = Message.INPUT_EMAIL)
    @ApiModelProperty(notes = "이메일", example = "1234@gmail.com", required = true)
    private String email;

    @NotBlank(message = Message.INPUT_NICKNAME)
    @ApiModelProperty(notes = "닉네임", example = "가경", required = true)
    private String nickname;

    @ApiModelProperty(notes = "프로필 사진", example = "http", required = true)
    private String profile;

    @ApiModelProperty(notes = "소개", example = "안녕하세요.", required = true)
    private String introduce;

    public User toEntity() {
        return User.builder()
                .kakaoId(null)
                .name(name)
                .email(email)
                .nickname(nickname)
                .profile(profile)
                .introduce(introduce)
                .build();
    }
}
