package AlsongDalsong_backend.AlsongDalsong.service.post;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_CATEGORY;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_DATE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_DECISION;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_IMPORTANCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_LINK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_OLD;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_CONTENT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_REASON;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_TODO;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_WHAT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_WHO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Decision;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.service.photo.PhotoService;
import AlsongDalsong_backend.AlsongDalsong.service.photo.StorageService;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostSaveRequestVO;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostUpdateRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostUpdateRequestVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

/**
 * 게시글을 위한 비즈니스 로직 구현 클래스 테스트
 */
@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    private User mockUser;
    private Photo mockPhoto;
    private Post post;
    private PostSaveRequestDto postSaveRequestDto;
    private PostUpdateRequestDto postUpdateRequestDto;

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    @Mock
    private PhotoService photoService;

    @Mock
    private StorageService storageService;

    @BeforeEach
    void setUp() {
        mockUser = mock(User.class);
        mockPhoto = mock(Photo.class);
        mockPhoto.setPost(post);

        post = Post.builder()
                .id(VALID_POST_ID)
                .todo(VALID_TODO)
                .category(VALID_CATEGORY)
                .who(VALID_WHO)
                .old(VALID_OLD)
                .date(VALID_DATE)
                .what(VALID_WHAT)
                .content(VALID_POST_CONTENT)
                .link(VALID_LINK)
                .importance(VALID_IMPORTANCE)
                .decision(VALID_DECISION)
                .reason(VALID_REASON)
                .build();
        post.setUser(mockUser);
        post.addPhotoList(mockPhoto);

        PostSaveRequestVO postSaveRequestVO = PostSaveRequestVO.builder()
                .email(post.getUserId().getEmail())
                .todo(post.getTodo().getTodo())
                .category(post.getCategory().getCategory())
                .what(post.getWhat())
                .old(post.getOld().getOld())
                .date(post.getDate())
                .who(post.getWho().getWho())
                .content(post.getContent())
                .link(post.getLink())
                .importance(post.getImportance())
                .photos(null)
                .build();
        postSaveRequestDto = new PostSaveRequestDto(postSaveRequestVO);
        PostUpdateRequestVO postUpdateRequestVO = PostUpdateRequestVO.builder()
                .id(post.getId())
                .email(post.getUserId().getEmail())
                .todo(post.getTodo().getTodo())
                .category(post.getCategory().getCategory())
                .what(post.getWhat())
                .old(post.getOld().getOld())
                .date(post.getDate())
                .who(post.getWho().getWho())
                .content(post.getContent())
                .link(post.getLink())
                .importance(post.getImportance())
                .photos(null)
                .deleteId(null)
                .build();
        postUpdateRequestDto = new PostUpdateRequestDto(postUpdateRequestVO);
    }

    @Test
    void testAddPostWithPhotos() {
        when(userService.findUserByEmail(any())).thenReturn(mockUser);
        when(postRepository.save(any())).thenReturn(post);
        when(storageService.addPhoto(any())).thenReturn(Collections.singletonList(mockPhoto));
        when(photoService.addPhoto(any())).thenReturn(mockPhoto);

        List<MultipartFile> photos = new ArrayList<>();
        PostResponseDto result = postService.addPostWithPhotos(postSaveRequestDto, photos);

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());

        verify(userService, times(1)).findUserByEmail(any());
        verify(postRepository, times(1)).save(any());
        verify(mockUser, times(1)).addPostList(any());
        verify(mockUser, times(1)).updatePoint(anyInt());
        verify(storageService, times(1)).addPhoto(any());
        verify(photoService, times(1)).addPhoto(any());
    }

    @Test
    void testFindPostByPostId() {
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(post));

        Post result = postService.findPostByPostId(post.getId());

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());

        verify(postRepository, times(1)).findById(any());
    }

    @Test
    void testFindPostDetailByPostId() {
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(post));

        PostResponseDto result = postService.findPostDetailByPostId(post.getId());

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());

        verify(postRepository, times(1)).findById(any());
    }

    @Test
    void testFindLatestPosts() {
        when(postRepository.findByTodo(any())).thenReturn(Collections.singletonList(post));

        List<PostResponseDto> result = postService.findLatestPosts(post.getTodo().getTodo());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(post.getId(), result.get(0).getId());

        verify(postRepository, times(1)).findByTodo(any());
    }

    @Test
    void testFindPopularPosts() {
        when(postRepository.findByTodoAndDecisionOrderByVoteListDesc(any(), any())).thenReturn(Collections.singletonList(post));

        List<PostResponseDto> result = postService.findPopularPosts(post.getTodo().getTodo());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(post.getId(), result.get(0).getId());

        verify(postRepository, times(1)).findByTodoAndDecisionOrderByVoteListDesc(any(), any());
    }

    @Test
    void testFindPostsByCategory() {
        when(postRepository.findByTodoAndCategory(any(), any())).thenReturn(Collections.singletonList(post));

        List<PostResponseDto> result =
                postService.findPostsByCategory(post.getTodo().getTodo(), post.getCategory().getCategory());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(post.getId(), result.get(0).getId());

        verify(postRepository, times(1)).findByTodoAndCategory(any(), any());
    }

    @Test
    void testFindUserPosts() {
        when(userService.findUserByEmail(any())).thenReturn(mockUser);
        when(mockUser.getPostList()).thenReturn(Collections.singletonList(post));

        List<PostResponseDto> result = postService.findUserPosts(mockUser.getEmail());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(post.getId(), result.get(0).getId());

        verify(userService, times(1)).findUserByEmail(any());
        verify(mockUser, times(3)).getPostList();
    }

    @Test
    void testModifyPost() {
        when(userService.findUserByEmail(any())).thenReturn(mockUser);
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(post));
        when(storageService.addPhoto(any())).thenReturn(Collections.singletonList(mockPhoto));
        when(photoService.addPhoto(any())).thenReturn(mockPhoto);

        List<MultipartFile> photos = new ArrayList<>();
        List<Long> deletePhotoIds = new ArrayList<>();
        PostResponseDto result = postService.modifyPost(postUpdateRequestDto, photos, deletePhotoIds);

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());

        verify(userService, times(1)).findUserByEmail(any());
        verify(postRepository, times(1)).findById(any());
        verify(storageService, times(1)).addPhoto(any());
        verify(photoService, times(1)).addPhoto(any());
    }

    @Test
    void testRemovePost() {
        when(userService.findUserByEmail(any())).thenReturn(mockUser);
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(post));
        doNothing().when(storageService).removeFile(any());
        doNothing().when(postRepository).delete(post);

        boolean result = postService.removePost(post.getId(), mockUser.getEmail());

        assertTrue(result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(postRepository, times(1)).findById(any());
        verify(storageService, times(1)).removeFile(any());
        verify(postRepository, times(1)).delete(any());
    }

    @Test
    void testModifyPostDecision() {
        when(userService.findUserByEmail(any())).thenReturn(mockUser);
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(post));

        PostResponseDto result =
                postService.modifyPostDecision(post.getId(), mockUser.getEmail(), Decision.DECIDED.getDecision(), null);

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());

        verify(userService, times(1)).findUserByEmail(any());
        verify(postRepository, times(1)).findById(any());
    }
}