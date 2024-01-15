package AlsongDalsong_backend.AlsongDalsong.service.user;

import AlsongDalsong_backend.AlsongDalsong.domain.user.TokenDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserSaveRequestDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원가입 및 인증을 위한 비즈니스 로직 인터페이스
 */
@Transactional
public interface AuthService {
    // 소셜(카카오) 회원가입 또는 로그인을 진행한 후, JWT 토큰을 반환한다.
    TokenDto socialSignupAndGenerateToken(String code);

    // 일반 회원가입을 진행한 후, 사용자 정보를 반환한다.
    UserResponseDto signupAndReturnUser(UserSaveRequestDto userSaveRequestDto);

    // 일반 로그인을 진행한 후, JWT 토큰을 반환한다.
    TokenDto loginAndGenerateToken(String email);
}
