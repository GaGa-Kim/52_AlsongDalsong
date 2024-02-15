package AlsongDalsong_backend.AlsongDalsong.service.like;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.like.Like;
import AlsongDalsong_backend.AlsongDalsong.domain.like.LikeRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.except.UnauthorizedEditException;
import AlsongDalsong_backend.AlsongDalsong.service.comment.CommentService;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.like.LikeRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    private Like like;
    private LikeRequestDto likeRequestDto;

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
        like = TestObjectFactory.initLike();
        like.setUser(mock(User.class));
        like.setComment(mock(Comment.class));

        likeRequestDto = TestObjectFactory.initLikeRequestDto(like);
    }

    @Test
    @DisplayName("댓글 좋아요 작성 테스트")
    void testAddLike() {
        when(userService.findUserByEmail(any())).thenReturn(like.getUserId());
        when(commentService.findCommentByCommentId(any())).thenReturn(like.getCommentId());
        when(likeRepository.existsByUserIdAndCommentId(any(), any())).thenReturn(false);
        when(likeRepository.save(any())).thenReturn(like);

        boolean result = likeService.saveLike(likeRequestDto);

        assertTrue(result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(commentService, times(1)).findCommentByCommentId(any());
        verify(likeRepository, times(1)).existsByUserIdAndCommentId(any(), any());
        verify(likeRepository, times(1)).save(any());
        verify(like.getCommentId(), times(1)).addLikeList(any());
        verify(like.getUserId(), times(1)).updatePoint(any());
    }

    @Test
    @DisplayName("댓글 좋아요 삭제 테스트")
    void testDeleteLike() {
        when(userService.findUserByEmail(any())).thenReturn(like.getUserId());
        when(commentService.findCommentByCommentId(any())).thenReturn(like.getCommentId());
        when(likeRepository.existsByUserIdAndCommentId(any(), any())).thenReturn(true);
        when(likeRepository.findByUserIdAndCommentId(any(), any())).thenReturn(like);
        doNothing().when(likeRepository).delete(any());

        boolean result = likeService.saveLike(likeRequestDto);

        assertFalse(result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(commentService, times(1)).findCommentByCommentId(any());
        verify(likeRepository, times(1)).existsByUserIdAndCommentId(any(), any());
        verify(likeRepository, times(1)).findByUserIdAndCommentId(any(), any());
        verify(likeRepository, times(1)).delete(any());
        verify(like.getUserId(), times(1)).updatePoint(any());
    }

    @Test
    @DisplayName("댓글 좋아요 삭제 시 작성자와 편집자가 다를 경우 예외 발생 테스트")
    void testDeleteLikeUnauthorizedExcept() {
        User notWriter = mock(User.class);
        when(userService.findUserByEmail(any())).thenReturn(notWriter);
        when(commentService.findCommentByCommentId(any())).thenReturn(like.getCommentId());
        when(likeRepository.existsByUserIdAndCommentId(any(), any())).thenReturn(true);
        when(likeRepository.findByUserIdAndCommentId(any(), any())).thenReturn(like);

        assertThatThrownBy(() -> likeService.saveLike(likeRequestDto))
                .isInstanceOf(UnauthorizedEditException.class);
    }

    @Test
    @DisplayName("댓글 아이디와 회원 이메일로 댓글 좋아요 조회 테스트")
    void testFindLike() {
        when(userService.findUserByEmail(any())).thenReturn(like.getUserId());
        when(commentService.findCommentByCommentId(any())).thenReturn(like.getCommentId());
        when(likeRepository.existsByUserIdAndCommentId(any(), any())).thenReturn(true);

        boolean result = likeService.findLike(like.getCommentId().getId(), like.getUserId().getEmail());

        assertTrue(result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(commentService, times(1)).findCommentByCommentId(any());
        verify(likeRepository, times(1)).existsByUserIdAndCommentId(any(), any());
    }
}