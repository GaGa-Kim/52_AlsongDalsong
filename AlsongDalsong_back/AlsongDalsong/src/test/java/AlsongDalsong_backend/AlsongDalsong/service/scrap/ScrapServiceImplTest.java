package AlsongDalsong_backend.AlsongDalsong.service.scrap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Todo;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.Scrap;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.ScrapRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.service.post.PostService;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapResponseDto;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 스크랩을 위한 비즈니스 로직 구현 클래스 테스트
 */
@ExtendWith(MockitoExtension.class)
class ScrapServiceImplTest {
    private Scrap scrap;
    private ScrapRequestDto scrapRequestDto;

    @InjectMocks
    private ScrapServiceImpl scrapService;
    @Mock
    private ScrapRepository scrapRepository;
    @Mock
    private UserService userService;
    @Mock
    private PostService postService;

    @BeforeEach
    void setUp() {
        scrap = TestObjectFactory.initScrap();
        scrap.setUser(mock(User.class));
        scrap.setPost(mock(Post.class));

        scrapRequestDto = TestObjectFactory.initScrapRequestDto(scrap);
    }

    @Test
    void testSaveAddScrap() {
        when(userService.findUserByEmail(any())).thenReturn(scrap.getUserId());
        when(postService.findPostByPostId(any())).thenReturn(scrap.getPostId());
        when(scrapRepository.existsByUserIdAndPostId(any(), any())).thenReturn(false);
        when(scrapRepository.save(any())).thenReturn(scrap);

        boolean result = scrapService.saveScrap(scrapRequestDto);

        assertTrue(result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(postService, times(1)).findPostByPostId(any());
        verify(scrapRepository, times(1)).existsByUserIdAndPostId(any(), any());
        verify(scrapRepository, times(1)).save(any());
        verify(scrap.getUserId(), times(1)).addScrapList(any());
        verify(scrap.getPostId(), times(1)).addScrapList(any());
    }

    @Test
    void testSaveDeleteScrap() {
        when(userService.findUserByEmail(any())).thenReturn(scrap.getUserId());
        when(postService.findPostByPostId(any())).thenReturn(scrap.getPostId());
        when(scrapRepository.existsByUserIdAndPostId(any(), any())).thenReturn(true);
        when(scrapRepository.findByUserIdAndPostId(any(), any())).thenReturn(scrap);
        doNothing().when(scrapRepository).delete(scrap);

        boolean result = scrapService.saveScrap(scrapRequestDto);

        assertFalse(result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(postService, times(1)).findPostByPostId(any());
        verify(scrapRepository, times(1)).existsByUserIdAndPostId(any(), any());
        verify(scrapRepository, times(1)).findByUserIdAndPostId(any(), any());
        verify(scrapRepository, times(1)).delete(any());
    }

    @Test
    void testFindScrap() {
        when(userService.findUserByEmail(any())).thenReturn(scrap.getUserId());
        when(postService.findPostByPostId(any())).thenReturn(scrap.getPostId());
        when(scrapRepository.existsByUserIdAndPostId(any(), any())).thenReturn(true);

        boolean result = scrapService.findScrap(scrap.getPostId().getId(), scrap.getUserId().getEmail());

        assertTrue(result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(postService, times(1)).findPostByPostId(any());
        verify(scrapRepository, times(1)).existsByUserIdAndPostId(any(), any());
    }

    @Test
    void testFindUserScraps() {
        when(userService.findUserByEmail(any())).thenReturn(scrap.getUserId());
        when(postService.findPostByPostId(any())).thenReturn(scrap.getPostId());
        when(scrap.getPostId().getTodo()).thenReturn(Todo.TO_GO_OR_NOT_TO_GO);
        when(scrapRepository.findByUserId(any())).thenReturn(Collections.singletonList(scrap));

        List<ScrapResponseDto> result = scrapService.findUserScraps(scrap.getUserId().getEmail());

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(userService, times(1)).findUserByEmail(any());
        verify(postService, times(1)).findPostByPostId(any());
        verify(scrapRepository, times(1)).findByUserId(any());
    }
}