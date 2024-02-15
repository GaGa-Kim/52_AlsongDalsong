package AlsongDalsong_backend.AlsongDalsong.service.user;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_TOKEN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.Scrap;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import AlsongDalsong_backend.AlsongDalsong.except.DuplicateEmailException;
import AlsongDalsong_backend.AlsongDalsong.except.WithdrawnException;
import AlsongDalsong_backend.AlsongDalsong.jwt.TokenProvider;
import AlsongDalsong_backend.AlsongDalsong.web.dto.auth.TokenDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    private User user;
    private UserSaveRequestDto userSaveRequestDto;

    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        user = TestObjectFactory.initUser();
        user.addPostList(mock(Post.class));
        user.addScrapList(mock(Scrap.class));

        userSaveRequestDto = TestObjectFactory.initUserSaveRequestDto(user);
    }

    @Test
    @DisplayName("일반 회원가입을 진행한 후, 사용자 정보 반환 테스트")
    void testSignupAndReturnUser() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        UserResponseDto result = authService.signupAndReturnUser(userSaveRequestDto);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());

        verify(userRepository, times(1)).existsByEmail(any());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("일반 회원가입 시 동일한 이메일 회원이 있을 경우 예외 발생 테스트")
    void testSignupDuplicationEmailExcept() {
        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThatThrownBy(() -> authService.signupAndReturnUser(userSaveRequestDto))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    @DisplayName("일반 로그인을 진행한 후, JWT 토큰 반환 테스트")
    void testLoginAndGenerateToken() {
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(tokenProvider.createToken(any())).thenReturn(VALID_TOKEN);

        TokenDto result = authService.loginAndGenerateToken(user.getEmail());

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(VALID_TOKEN, result.getToken());

        verify(userRepository, times(1)).findByEmail(any());
        verify(tokenProvider, times(1)).createToken(any());
    }

    @Test
    @DisplayName("탈퇴한 회원으로 일반 로그인 시 예외 발생 테스트")
    void testLoginWithdrawnExcept() {
        User withdrawUser = mock(User.class);
        when(userRepository.findByEmail(any())).thenReturn(withdrawUser);
        when(withdrawUser.getWithdraw()).thenReturn(true);

        assertThatThrownBy(() -> authService.loginAndGenerateToken(withdrawUser.getEmail()))
                .isInstanceOf(WithdrawnException.class);
    }
}