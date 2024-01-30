package AlsongDalsong_backend.AlsongDalsong.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Category;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Decision;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Old;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Todo;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Who;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import AlsongDalsong_backend.AlsongDalsong.service.photo.StorageService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserUpdateRequestDto;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

/**
 * 회원 비즈니스 로직 구현 클래스 테스트
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private User mockUser;
    private Post post;

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

        Todo todo = Todo.TO_BUY_OR_NOT_TO_BUY;
        Category category = Category.FASHION;
        Who who = Who.WOMAN;
        Old old = Old.TEENS;
        String date = "언제";
        String what = "무엇을";
        String content = "내용";
        String link = "링크";
        Integer importance = 3;
        Decision decision = Decision.UNDECIDED;
        String reason = "결정 이유";
        post = new Post(todo, category, who, old, date, what, content, link, importance, decision, reason);
        post.setUser(mockUser);
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
    void testFindUserProfileImageAsBytes() {
    }

    @Test
    void testFindUserProfileImageAsBase64() {
    }

    @Test
    void testModifyUserProfile() {
        when(userRepository.findByEmail(any())).thenReturn(mockUser);

        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto();
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
        when(postRepository.countByUserIdAndTodoAndDecision(mockUser, post.getTodo(), post.getDecision()))
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