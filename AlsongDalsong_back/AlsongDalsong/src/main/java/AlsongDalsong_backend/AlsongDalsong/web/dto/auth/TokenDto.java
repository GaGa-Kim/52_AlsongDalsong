package AlsongDalsong_backend.AlsongDalsong.web.dto.auth;

import AlsongDalsong_backend.AlsongDalsong.constants.Message;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 로그인 성공 후, 반환될 jwt 토큰, 이메일
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TokenDto {
    @NotBlank(message = Message.INPUT_NAME)
    @ApiModelProperty(example = "토큰")
    private String token;

    @Email(message = Message.INPUT_EMAIL)
    @ApiModelProperty(example = "이메일")
    private String email;
}