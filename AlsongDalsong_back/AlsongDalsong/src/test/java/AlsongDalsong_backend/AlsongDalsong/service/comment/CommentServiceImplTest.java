package AlsongDalsong_backend.AlsongDalsong.service.comment;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_COMMENT_CONTENT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_COMMENT_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.CommentRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.service.post.PostService;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentUpdateRequestDto;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 댓글을 위한 비즈니스 로직 구현 클래스 테스트
 */
@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    private User mockUser;
    private Post mockPost;
    private Comment comment;
    private CommentSaveRequestDto commentSaveRequestDto;
    private CommentUpdateRequestDto commentUpdateRequestDto;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserService userService;

    @Mock
    private PostService postService;

    @BeforeEach
    void setUp() {
        mockUser = mock(User.class);
        mockPost = mock(Post.class);

        comment = Comment.builder()
                .id(VALID_COMMENT_ID)
                .content(VALID_COMMENT_CONTENT)
                .build();
        comment.setUser(mockUser);
        comment.setPost(mockPost);

        commentSaveRequestDto = CommentSaveRequestDto.builder()
                .email(comment.getUserId().getEmail())
                .postId(comment.getPostId().getId())
                .content(comment.getContent())
                .build();
        commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .id(comment.getId())
                .email(comment.getUserId().getEmail())
                .postId(VALID_POST_ID)
                .content(comment.getContent())
                .build();
    }

    @Test
    void testAddComment() {
        when(userService.findUserByEmail(any())).thenReturn(mockUser);
        when(postService.findPostByPostId(any())).thenReturn(mockPost);
        when(commentRepository.save(any())).thenReturn(comment);
        when(postService.findPostByPostId(any())).thenReturn(mockPost);
        when(commentRepository.findAllByPostIdOrderByLikeListDesc(any())).thenReturn(Collections.singletonList(comment));

        List<CommentResponseDto> result = commentService.addComment(commentSaveRequestDto);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(comment.getId(), result.get(0).getId());

        verify(userService, times(1)).findUserByEmail(any());
        verify(postService, times(2)).findPostByPostId(any());
        verify(commentRepository, times(1)).save(any());
        verify(commentRepository, times(1)).findAllByPostIdOrderByLikeListDesc(any());
        verify(mockPost, times(1)).addCommentList(any());
        verify(mockUser, times(1)).updatePoint(any());
    }

    @Test
    void testFindCommentByCommentId() {
        when(commentRepository.findById(any())).thenReturn(Optional.ofNullable(comment));

        Comment result = commentService.findCommentByCommentId(comment.getId());

        assertNotNull(result);
        assertEquals(comment.getId(), result.getId());

        verify(commentRepository, times(1)).findById(any());
    }

    @Test
    void testFindPostCommentsByLikes() {
        when(postService.findPostByPostId(any())).thenReturn(mockPost);
        when(commentRepository.findAllByPostIdOrderByLikeListDesc(any())).thenReturn(Collections.singletonList(comment));

        List<CommentResponseDto> result = commentService.findPostCommentsByLikes(mockPost.getId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(comment.getId(), result.get(0).getId());

        verify(postService, times(1)).findPostByPostId(any());
        verify(commentRepository, times(1)).findAllByPostIdOrderByLikeListDesc(any());
    }

    @Test
    void testModifyComment() {
        when(userService.findUserByEmail(any())).thenReturn(mockUser);
        when(commentRepository.findById(any())).thenReturn(Optional.ofNullable(comment));
        when(commentRepository.findAllByPostIdOrderByLikeListDesc(any())).thenReturn(Collections.singletonList(comment));

        List<CommentResponseDto> result = commentService.modifyComment(commentUpdateRequestDto);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(comment.getId(), result.get(0).getId());

        verify(userService, times(1)).findUserByEmail(any());
        verify(commentRepository, times(1)).findById(any());
        verify(commentRepository, times(1)).findAllByPostIdOrderByLikeListDesc(any());
    }

    @Test
    void testRemoveComment() {
        when(userService.findUserByEmail(any())).thenReturn(mockUser);
        when(commentRepository.findById(any())).thenReturn(Optional.ofNullable(comment));
        doNothing().when(commentRepository).delete(any());

        boolean result = commentService.removeComment(comment.getId(), mockUser.getEmail());

        assertTrue(result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(commentRepository, times(1)).findById(any());
        verify(commentRepository, times(1)).delete(any());
    }
}