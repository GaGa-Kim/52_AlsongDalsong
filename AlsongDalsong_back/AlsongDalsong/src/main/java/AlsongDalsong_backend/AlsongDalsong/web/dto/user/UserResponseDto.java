package AlsongDalsong_backend.AlsongDalsong.web.dto.user;

import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 회원정보 dto
 */
@Getter
public class UserResponseDto {

	@ApiModelProperty(notes = "회원 기본키", example = "1")
	private final Long id; // 기본키

	@ApiModelProperty(notes = "카카오 아이디", example = "1234")
	private final Long kakaoId; // 카카오 아이디

	@ApiModelProperty(notes = "이름", example = "김가경")
	private final String name; // 이름

	@ApiModelProperty(notes = "유저 기본키", example = "1234@gmail.com")
	private final String email; // 이메일

	@ApiModelProperty(notes = "닉네임", example = "가경")
	private final String nickname; // 닉네임

	@ApiModelProperty(notes = "소개", example = "안녕하세요.")
	private final String introduce; // 소개

	@ApiModelProperty(notes = "권한", example = "ROLE_USER")
	private final String role; // 권한

	@ApiModelProperty(notes = "포인트 적립", example = "15")
	private final Integer point; // 포인트 적립

	@ApiModelProperty(notes = "스티커 갯수", example = "1")
	private final Integer sticker; // 스티커 갯수

	@ApiModelProperty(notes = "탈퇴 여부", example = "false")
	private final Boolean withdraw; // 탈퇴 여부

	public UserResponseDto(User user) {
		this.id = user.getId();
		this.kakaoId = user.getKakaoId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.nickname = user.getNickname();
		this.introduce = user.getIntroduce();
		this.role = user.getRole().getRole();
		this.point = user.getPoint();
		this.sticker = user.getSticker();
		this.withdraw = user.getWithdraw();
	}
}
