package AlsongDalsong_backend.AlsongDalsong.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import AlsongDalsong_backend.AlsongDalsong.config.jwt.TokenProvider;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import AlsongDalsong_backend.AlsongDalsong.web.dto.auth.TokenDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 회원가입 및 인증을 위한 비즈니스 로직 구현 클래스 테스트
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    private static final String authorizationCode = "authorization code";
    private static final String mockJwtToken = "jwtToken";
    private User user;

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        Long kakaoId = 123L;
        String name = "이름";
        String email = "이메일";
        String nickname = "닉네임";
        String profile = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
        String introduce = "소개";
        user = new User(kakaoId, name, email, nickname, profile, introduce);
    }

    @Test
    void testSocialSignupAndGenerateToken() {
    }

    @Test
    void testSocialLoginAndGenerateToken() {
    }

    @Test
    void testSignupAndReturnUser() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();
        UserResponseDto result = authService.signupAndReturnUser(userSaveRequestDto);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());

        verify(userRepository, times(1)).existsByEmail(any());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void testLoginAndGenerateToken() {
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(tokenProvider.createToken(any())).thenReturn(mockJwtToken);

        TokenDto result = authService.loginAndGenerateToken(user.getEmail());

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(mockJwtToken, result.getToken());

        verify(userRepository, times(1)).findByEmail(any());
        verify(tokenProvider, times(1)).createToken(any());
    }
}