package AlsongDalsong_backend.AlsongDalsong.service.like;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.like.Like;
import AlsongDalsong_backend.AlsongDalsong.domain.like.LikeRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.service.comment.CommentService;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.like.LikeRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 댓글 좋아요를 위한 비즈니스 로직 구현 클래스 테스트
 */
@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {
    private User mockUser;
    private Comment mockComment;
    private Like like;

    @InjectMocks
    private LikeServiceImpl likeService;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private UserService userService;

    @Mock
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        mockUser = mock(User.class);
        mockComment = mock(Comment.class);

        like = new Like();
        like.setUser(mockUser);
        like.setComment(mockComment);
    }

    @Test
    void testSaveAddLike() {
        when(userService.findUserByEmail(any())).thenReturn(mockUser);
        when(commentService.findCommentByCommentId(any())).thenReturn(mockComment);
        when(likeRepository.existsByUserIdAndCommentId(any(), any())).thenReturn(false);
        when(likeRepository.save(any())).thenReturn(like);

        LikeRequestDto likeRequestDto = LikeRequestDto.builder()
                .email(like.getUserId().getEmail())
                .commentId(like.getCommentId().getId())
                .build();
        boolean result = likeService.saveLike(likeRequestDto);

        assertTrue(result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(commentService, times(1)).findCommentByCommentId(any());
        verify(likeRepository, times(1)).existsByUserIdAndCommentId(any(), any());
        verify(likeRepository, times(1)).save(any());
        verify(mockComment, times(1)).addLikeList(any());
        verify(mockUser, times(1)).updatePoint(any());
    }

    @Test
    void testSaveDeleteLike() {
        when(userService.findUserByEmail(any())).thenReturn(mockUser);
        when(commentService.findCommentByCommentId(any())).thenReturn(mockComment);
        when(likeRepository.existsByUserIdAndCommentId(any(), any())).thenReturn(true);
        when(likeRepository.findByUserIdAndCommentId(any(), any())).thenReturn(like);
        doNothing().when(likeRepository).delete(any());

        LikeRequestDto likeRequestDto = LikeRequestDto.builder()
                .email(like.getUserId().getEmail())
                .commentId(like.getCommentId().getId())
                .build();
        boolean result = likeService.saveLike(likeRequestDto);

        assertFalse(result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(commentService, times(1)).findCommentByCommentId(any());
        verify(likeRepository, times(1)).existsByUserIdAndCommentId(any(), any());
        verify(likeRepository, times(1)).findByUserIdAndCommentId(any(), any());
        verify(likeRepository, times(1)).delete(any());
        verify(mockUser, times(1)).updatePoint(any());
    }

    @Test
    void testFindLike() {
        when(userService.findUserByEmail(any())).thenReturn(mockUser);
        when(commentService.findCommentByCommentId(any())).thenReturn(mockComment);
        when(likeRepository.existsByUserIdAndCommentId(any(), any())).thenReturn(true);

        boolean result = likeService.findLike(mockComment.getId(), mockUser.getEmail());

        assertTrue(result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(commentService, times(1)).findCommentByCommentId(any());
        verify(likeRepository, times(1)).existsByUserIdAndCommentId(any(), any());
    }
}