package AlsongDalsong_backend.AlsongDalsong.service.user;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_INTRODUCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NICKNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Decision;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Todo;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import AlsongDalsong_backend.AlsongDalsong.service.photo.StorageService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserUpdateRequestDto;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 회원 비즈니스 로직 구현 클래스 테스트
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private final String profile = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
    private User mockUser;
    private Post mockPost;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private StorageService storageService;

    @BeforeEach
    void setUp() {
        mockUser = mock(User.class);
        mockPost = mock(Post.class);
        mockPost.setUser(mockUser);
    }

    @Test
    void testFindUserByEmail() {
        when(userRepository.findByEmail(any())).thenReturn(mockUser);

        User result = userService.findUserByEmail(mockUser.getEmail());

        assertNotNull(result);
        assertEquals(mockUser.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findByEmail(any());
    }

    @Test
    void testFindUserProfileImageAsBytes() throws IOException {
        when(userRepository.findByEmail(any())).thenReturn(mockUser);
        when(mockUser.getProfile()).thenReturn(profile);

        ResponseEntity<byte[]> result = userService.findUserProfileImageAsBytes(mockUser.getEmail());

        assertNotNull(result);
    }

    @Test
    void testFindUserProfileImageAsBase64() throws IOException {
        when(userRepository.findByEmail(any())).thenReturn(mockUser);
        when(mockUser.getProfile()).thenReturn(profile);

        ResponseEntity<String> result = userService.findUserProfileImageAsBase64(mockUser.getEmail());

        assertNotNull(result);
    }

    @Test
    void testModifyUserProfile() {
        when(userRepository.findByEmail(any())).thenReturn(mockUser);

        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto(VALID_EMAIL, VALID_NICKNAME, VALID_INTRODUCE);
        User result = userService.modifyUserProfile(userUpdateRequestDto);

        assertNotNull(result);
        assertEquals(mockUser.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findByEmail(any());
        verify(mockUser, times(1)).updateInfo(any(), any());
    }

    @Test
    void testModifyUserProfileImage() {
        when(userRepository.findByEmail(any())).thenReturn(mockUser);
        doNothing().when(storageService).removeFile(any());
        when(storageService.addProfileImage(any())).thenReturn("새 프로필");

        MultipartFile profileImage = null;
        User result = userService.modifyUserProfileImage(mockUser.getEmail(), profileImage);

        assertNotNull(result);
        assertEquals(mockUser.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findByEmail(any());
        verify(storageService, times(1)).removeFile(any());
        verify(storageService, times(1)).addProfileImage(any());
        verify(mockUser, times(1)).updateProfile(any());
    }

    @Test
    void testFindUserDecisionPropensity() {
        when(userRepository.findByEmail(any())).thenReturn(mockUser);
        when(mockPost.getTodo()).thenReturn(Todo.TO_BUY_OR_NOT_TO_BUY);
        when(mockPost.getDecision()).thenReturn(Decision.UNDECIDED);
        when(postRepository.countByUserIdAndTodoAndDecision(mockUser, mockPost.getTodo(), mockPost.getDecision()))
                .thenReturn(1L);

        Map<String, Object> result = userService.findUserDecisionPropensity(mockUser.getEmail());
        assertNotNull(result);
        assertEquals(12, result.size());

        verify(userRepository, times(1)).findByEmail(any());
        verify(postRepository, times(12)).countByUserIdAndTodoAndDecision(any(), any(), any());
    }

    @Test
    void testWithdrawUserAccount() {
        when(userRepository.findByEmail(any())).thenReturn(mockUser);

        boolean result = userService.withdrawUserAccount(mockUser.getEmail());
        assertTrue(result);

        verify(userRepository, times(1)).findByEmail(any());
        verify(mockUser, times(1)).withdrawUser();
    }
}