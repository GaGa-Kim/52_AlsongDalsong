package AlsongDalsong_backend.AlsongDalsong.service;

import AlsongDalsong_backend.AlsongDalsong.domain.user.TokenDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserSaveRequestDto;

/**
 * 회원가입 및 인증을 위한 비즈니스 로직 인터페이스
 */
public interface AuthService {
    // 카카오 회원가입 또는 로그인을 진행한 후, JWT 토큰을 반환
    TokenDto kakaoSignupOrLogin(String access_token);

    // 일반 회원가입을 진행한 후, 사용자 정보를 반환
    UserResponseDto regularSignup(UserSaveRequestDto userSaveRequestDto);

    // 일반 로그인을 진행한 후, JWT 토큰을 반환
    TokenDto regularLogin(String email);
}
