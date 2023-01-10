package AlsongDalsong_backend.AlsongDalsong.domain.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 회원 로그인 성공 후, 반환될 jwt 토큰, 이메일
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    @ApiModelProperty(example = "토큰")
    private String token;

    @ApiModelProperty(example = "이메일")
    private String email;
}