package AlsongDalsong_backend.AlsongDalsong.service.post;

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

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Decision;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.Scrap;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.Vote;
import AlsongDalsong_backend.AlsongDalsong.service.photo.PhotoService;
import AlsongDalsong_backend.AlsongDalsong.service.photo.StorageService;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostUpdateRequestDto;
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
        post = TestObjectFactory.initPost();
        post.setUser(mock(User.class));
        post.addPhotoList(mock(Photo.class));
        post.addCommentList(mock(Comment.class));
        post.addVoteList(mock(Vote.class));
        post.addScrapList(mock(Scrap.class));

        postSaveRequestDto = TestObjectFactory.initPostSaveRequestDto(post);
        postUpdateRequestDto = TestObjectFactory.initPostUpdateRequestDto(post);
    }

    @Test
    void testAddPostWithPhotos() {
        when(userService.findUserByEmail(any())).thenReturn(post.getUserId());
        when(postRepository.save(any())).thenReturn(post);
        when(storageService.addPhoto(any())).thenReturn(Collections.singletonList(post.getPhotoList().get(0)));
        when(photoService.addPhoto(any())).thenReturn(post.getPhotoList().get(0));

        List<MultipartFile> photos = new ArrayList<>();
        PostResponseDto result = postService.addPostWithPhotos(postSaveRequestDto, photos);

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());

        verify(userService, times(1)).findUserByEmail(any());
        verify(postRepository, times(1)).save(any());
        verify(post.getUserId(), times(1)).addPostList(any());
        verify(post.getUserId(), times(1)).updatePoint(anyInt());
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
        when(userService.findUserByEmail(any())).thenReturn(post.getUserId());
        when(post.getUserId().getPostList()).thenReturn(Collections.singletonList(post));

        List<PostResponseDto> result = postService.findUserPosts(post.getUserId().getEmail());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(post.getId(), result.get(0).getId());

        verify(userService, times(1)).findUserByEmail(any());
        verify(post.getUserId(), times(3)).getPostList();
    }

    @Test
    void testModifyPost() {
        when(userService.findUserByEmail(any())).thenReturn(post.getUserId());
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(post));
        when(storageService.addPhoto(any())).thenReturn(Collections.singletonList(post.getPhotoList().get(0)));
        when(photoService.addPhoto(any())).thenReturn(post.getPhotoList().get(0));

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
        when(userService.findUserByEmail(any())).thenReturn(post.getUserId());
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(post));
        doNothing().when(storageService).removeFile(any());
        doNothing().when(postRepository).delete(post);

        boolean result = postService.removePost(post.getId(), post.getUserId().getEmail());

        assertTrue(result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(postRepository, times(1)).findById(any());
        verify(storageService, times(1)).removeFile(any());
        verify(postRepository, times(1)).delete(any());
    }

    @Test
    void testModifyPostDecision() {
        when(userService.findUserByEmail(any())).thenReturn(post.getUserId());
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(post));

        PostResponseDto result =
                postService.modifyPostDecision(post.getId(), post.getUserId().getEmail(), Decision.DECIDED.getDecision(), null);

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());

        verify(userService, times(1)).findUserByEmail(any());
        verify(postRepository, times(1)).findById(any());
    }
}