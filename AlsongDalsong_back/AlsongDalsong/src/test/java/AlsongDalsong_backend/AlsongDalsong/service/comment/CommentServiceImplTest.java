package AlsongDalsong_backend.AlsongDalsong.service.comment;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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
import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.CommentRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.like.Like;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.except.NotFoundException;
import AlsongDalsong_backend.AlsongDalsong.except.UnauthorizedEditException;
import AlsongDalsong_backend.AlsongDalsong.service.post.PostService;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentUpdateRequestDto;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
        comment = TestObjectFactory.initComment();
        comment.setUser(mock(User.class));
        comment.setPost(mock(Post.class));
        comment.addLikeList(mock(Like.class));

        commentSaveRequestDto = TestObjectFactory.initCommentSaveRequestDto(comment);
        commentUpdateRequestDto = TestObjectFactory.initCommentUpdateRequestDto(comment);
    }

    @Test
    @DisplayName("게시글에 댓글 작성 후 댓글 리스트 조회 테스트")
    void testAddComment() {
        when(userService.findUserByEmail(any())).thenReturn(comment.getUserId());
        when(postService.findPostByPostId(any())).thenReturn(comment.getPostId());
        when(commentRepository.save(any())).thenReturn(comment);
        when(postService.findPostByPostId(any())).thenReturn(comment.getPostId());
        when(commentRepository.findAllByPostIdOrderByLikeListDesc(any())).thenReturn(Collections.singletonList(comment));

        List<CommentResponseDto> result = commentService.addComment(commentSaveRequestDto);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(comment.getId(), result.get(0).getId());

        verify(userService, times(1)).findUserByEmail(any());
        verify(postService, times(2)).findPostByPostId(any());
        verify(commentRepository, times(1)).save(any());
        verify(commentRepository, times(1)).findAllByPostIdOrderByLikeListDesc(any());
        verify(comment.getPostId(), times(1)).addCommentList(any());
        verify(comment.getUserId(), times(1)).updatePoint(any());
    }

    @Test
    @DisplayName("댓글 아이디로 댓글 조회 테스트")
    void testFindCommentByExistingCommentId() {
        when(commentRepository.findById(any())).thenReturn(Optional.ofNullable(comment));

        Comment result = commentService.findCommentByCommentId(comment.getId());

        assertNotNull(result);
        assertEquals(comment.getId(), result.getId());

        verify(commentRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("존재하지 않는 댓글 아이디로 댓글 조회 예외 발생 테스트")
    void testFindCommentByNonExistingCommentIdExcept() {
        Long nonExistingCommentId = 100L;
        when(commentRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.findCommentByCommentId(nonExistingCommentId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("게시글 아이디로 게시글 별 댓글 리스트 조회 테스트")
    void testFindPostCommentsByLikes() {
        when(postService.findPostByPostId(any())).thenReturn(comment.getPostId());
        when(commentRepository.findAllByPostIdOrderByLikeListDesc(any())).thenReturn(Collections.singletonList(comment));

        List<CommentResponseDto> result = commentService.findPostCommentsByLikes(comment.getPostId().getId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(comment.getId(), result.get(0).getId());

        verify(postService, times(1)).findPostByPostId(any());
        verify(commentRepository, times(1)).findAllByPostIdOrderByLikeListDesc(any());
    }

    @Test
    @DisplayName("댓글 수정 후 댓글 리스트 조회 테스트")
    void testModifyComment() {
        when(userService.findUserByEmail(any())).thenReturn(comment.getUserId());
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
    @DisplayName("댓글 수정 시 작성자와 편집자가 다를 경우 예외 발생 테스트")
    void testModifyCommentUnauthorizedExcept() {
        User notWriter = mock(User.class);
        when(userService.findUserByEmail(any())).thenReturn(notWriter);
        when(commentRepository.findById(any())).thenReturn(Optional.ofNullable(comment));

        assertThatThrownBy(() -> commentService.modifyComment(commentUpdateRequestDto))
                .isInstanceOf(UnauthorizedEditException.class);
    }

    @Test
    @DisplayName("댓글 아이디로 댓글 삭제 테스트")
    void testRemoveComment() {
        when(userService.findUserByEmail(any())).thenReturn(comment.getUserId());
        when(commentRepository.findById(any())).thenReturn(Optional.ofNullable(comment));
        doNothing().when(commentRepository).delete(any());

        boolean result = commentService.removeComment(comment.getId(), comment.getUserId().getEmail());

        assertTrue(result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(commentRepository, times(1)).findById(any());
        verify(commentRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("댓글 삭제 시 작성자와 편집자가 다를 경우 예외 발생 테스트")
    void testRemoveCommentUnauthorizedExcept() {
        User notWriter = mock(User.class);
        when(userService.findUserByEmail(any())).thenReturn(notWriter);
        when(commentRepository.findById(any())).thenReturn(Optional.ofNullable(comment));

        assertThatThrownBy(() -> commentService.removeComment(comment.getId(), comment.getUserId().getEmail()))
                .isInstanceOf(UnauthorizedEditException.class);
    }
}