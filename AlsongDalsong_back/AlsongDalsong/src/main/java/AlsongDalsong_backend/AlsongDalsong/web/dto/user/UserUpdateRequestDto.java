package AlsongDalsong_backend.AlsongDalsong.web.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 수정 dto
 */
@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    @ApiModelProperty(notes = "이메일", example = "1234@gmail.com", required = true)
    private String email; // 이메일

    @ApiModelProperty(notes = "닉네임", example = "가가경", required = true)
    private String nickname; // 닉네임

    @ApiModelProperty(notes = "소개", example = "안녕하세요.", required = true)
    private String introduce; // 소개

    @Builder
    public UserUpdateRequestDto(String email, String nickname, String introduce) {
        this.email = email;
        this.nickname = nickname;
        this.introduce = introduce;
    }
}
