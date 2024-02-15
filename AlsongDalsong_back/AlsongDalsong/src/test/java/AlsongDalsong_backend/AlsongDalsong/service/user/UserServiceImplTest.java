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

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Decision;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Todo;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.Scrap;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import AlsongDalsong_backend.AlsongDalsong.service.photo.StorageService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserUpdateRequestDto;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    private User user;
    private UserUpdateRequestDto userUpdateRequestDto;

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
        user = TestObjectFactory.initUser();
        user.addPostList(mock(Post.class));
        user.addScrapList(mock(Scrap.class));

        userUpdateRequestDto = TestObjectFactory.initUserUpdateRequestDto(user);
    }

    @Test
    @DisplayName("이메일로 회원 조회 테스트")
    void testFindUserByEmail() {
        when(userRepository.findByEmail(any())).thenReturn(user);

        User result = userService.findUserByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findByEmail(any());
    }

    @Test
    @DisplayName("회원 프로필 이미지 조회를 위한 Byte 배열 기반 이미지 응답 생성 테스트")
    void testFindUserProfileImageAsBytes() throws IOException {
        when(userRepository.findByEmail(any())).thenReturn(user);

        ResponseEntity<byte[]> result = userService.findUserProfileImageAsBytes(user.getEmail());

        assertNotNull(result);
    }

    @Test
    @DisplayName("회원 프로필 이미지 조회를 위한 Base64 기반 이미지 응답 생성 테스트")
    void testFindUserProfileImageAsBase64() throws IOException {
        when(userRepository.findByEmail(any())).thenReturn(user);

        ResponseEntity<String> result = userService.findUserProfileImageAsBase64(user.getEmail());

        assertNotNull(result);
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    void testModifyUserProfile() {
        when(userRepository.findByEmail(any())).thenReturn(user);

        User result = userService.modifyUserProfile(userUpdateRequestDto);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findByEmail(any());
    }

    @Test
    @DisplayName("회원 프로필 이미지 수정 테스트")
    void testModifyUserProfileImage() {
        when(userRepository.findByEmail(any())).thenReturn(user);
        doNothing().when(storageService).removeFile(any());
        when(storageService.addProfileImage(any())).thenReturn("새 프로필");

        MultipartFile profileImage = null;
        User result = userService.modifyUserProfileImage(user.getEmail(), profileImage);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findByEmail(any());
        verify(storageService, times(1)).removeFile(any());
        verify(storageService, times(1)).addProfileImage(any());
    }

    @Test
    @DisplayName("회원별 구매 성향 통계 조회 테스트")
    void testFindUserDecisionPropensity() {
        when(userRepository.findByEmail(any())).thenReturn(user);
        when(user.getPostList().get(0).getTodo()).thenReturn(Todo.TO_BUY_OR_NOT_TO_BUY);
        when(user.getPostList().get(0).getDecision()).thenReturn(Decision.UNDECIDED);
        when(postRepository.countByUserIdAndTodoAndDecision(user, user.getPostList().get(0).getTodo(),
                user.getPostList().get(0).getDecision())).thenReturn(1L);

        Map<String, Object> result = userService.findUserDecisionPropensity(user.getEmail());

        assertNotNull(result);
        assertEquals(12, result.size());

        verify(userRepository, times(1)).findByEmail(any());
        verify(postRepository, times(12)).countByUserIdAndTodoAndDecision(any(), any(), any());
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    void testWithdrawUserAccount() {
        when(userRepository.findByEmail(any())).thenReturn(user);

        boolean result = userService.withdrawUserAccount(user.getEmail());

        assertTrue(result);

        verify(userRepository, times(1)).findByEmail(any());
    }
}