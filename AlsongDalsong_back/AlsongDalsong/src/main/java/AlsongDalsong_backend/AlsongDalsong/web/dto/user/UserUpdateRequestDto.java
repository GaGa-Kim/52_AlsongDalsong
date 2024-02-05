package AlsongDalsong_backend.AlsongDalsong.web.dto.user;

import AlsongDalsong_backend.AlsongDalsong.constants.Message;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 수정 dto
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequestDto {
    @Email(message = Message.INPUT_EMAIL)
    @ApiModelProperty(notes = "이메일", example = "1234@gmail.com", required = true)
    private String email;

    @NotBlank(message = Message.INPUT_NICKNAME)
    @ApiModelProperty(notes = "닉네임", example = "가가경", required = true)
    private String nickname;

    @ApiModelProperty(notes = "소개", example = "안녕하세요.", required = true)
    private String introduce;

    @Builder
    public UserUpdateRequestDto(String email, String nickname, String introduce) {
        this.email = email;
        this.nickname = nickname;
        this.introduce = introduce;
    }
}
