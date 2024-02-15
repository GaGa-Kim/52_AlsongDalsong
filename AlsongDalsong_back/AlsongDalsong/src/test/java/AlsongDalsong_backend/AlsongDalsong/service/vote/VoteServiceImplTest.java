package AlsongDalsong_backend.AlsongDalsong.service.vote;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.Vote;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.VoteRepository;
import AlsongDalsong_backend.AlsongDalsong.except.UnauthorizedEditException;
import AlsongDalsong_backend.AlsongDalsong.service.post.PostService;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.vote.VoteRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 게시글 투표를 위한 비즈니스 로직 구현 클래스 테스트
 */
@ExtendWith(MockitoExtension.class)
class VoteServiceImplTest {
    private Vote vote;
    private VoteRequestDto voteTrueRequestDto;
    private VoteRequestDto voteFalseResponseDto;

    @InjectMocks
    private VoteServiceImpl voteService;
    @Mock
    private VoteRepository voteRepository;
    @Mock
    private UserService userService;
    @Mock
    private PostService postService;

    @BeforeEach
    void setUp() {
        vote = TestObjectFactory.initVote();
        vote.setUser(mock(User.class));
        vote.setPost(mock(Post.class));

        voteTrueRequestDto = TestObjectFactory.initVoteRequestDto(vote, true);
        voteFalseResponseDto = TestObjectFactory.initVoteRequestDto(vote, false);
    }

    @Test
    @DisplayName("게시글에 투표 작성 테스트")
    void testAddVote() {
        when(userService.findUserByEmail(any())).thenReturn(vote.getUserId());
        when(postService.findPostByPostId(any())).thenReturn(vote.getPostId());
        when(voteRepository.existsByUserIdAndPostId(any(), any())).thenReturn(false);
        when(voteRepository.save(any())).thenReturn(vote);

        String result = voteService.saveVote(voteTrueRequestDto);

        assertEquals("true", result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(postService, times(1)).findPostByPostId(anyLong());
        verify(voteRepository, times(1)).existsByUserIdAndPostId(any(), any());
        verify(voteRepository, times(1)).save(any());
        verify(vote.getPostId(), times(1)).addVoteList(any());
        verify(vote.getUserId(), times(1)).updatePoint(any());
    }

    @Test
    @DisplayName("게시글에 투표 삭제 테스트")
    void testDeleteVote() {
        when(userService.findUserByEmail(any())).thenReturn(vote.getUserId());
        when(postService.findPostByPostId(any())).thenReturn(vote.getPostId());
        when(voteRepository.existsByUserIdAndPostId(any(), any())).thenReturn(true);
        when(voteRepository.findByUserIdAndPostId(any(), any())).thenReturn(vote);
        doNothing().when(voteRepository).delete(vote);

        String result = voteService.saveVote(voteTrueRequestDto);

        assertEquals("투표하지 않았습니다.", result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(postService, times(1)).findPostByPostId(any());
        verify(voteRepository, times(1)).existsByUserIdAndPostId(any(), any());
        verify(voteRepository, times(1)).findByUserIdAndPostId(any(), any());
        verify(voteRepository, times(1)).delete(any());
        verify(vote.getUserId(), times(1)).updatePoint(any());
    }

    @Test
    @DisplayName("게시글에 투표 수정 테스트")
    void testModifyVote() {
        when(userService.findUserByEmail(any())).thenReturn(vote.getUserId());
        when(postService.findPostByPostId(any())).thenReturn(vote.getPostId());
        when(voteRepository.existsByUserIdAndPostId(any(), any())).thenReturn(true);
        when(voteRepository.findByUserIdAndPostId(any(), any())).thenReturn(vote);

        String result = voteService.saveVote(voteFalseResponseDto);

        assertEquals("false", result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(postService, times(1)).findPostByPostId(any());
        verify(voteRepository, times(1)).existsByUserIdAndPostId(any(), any());
        verify(voteRepository, times(1)).findByUserIdAndPostId(any(), any());
    }

    @Test
    @DisplayName("게시글에 투표 삭제 또는 수정 시 작성자와 편집자가 다를 경우 예외 발생 테스트")
    void testDeleteOrModifyVoteUnauthorizedExcept() {
        User withdrawUser = mock(User.class);
        when(userService.findUserByEmail(any())).thenReturn(withdrawUser);
        when(postService.findPostByPostId(any())).thenReturn(vote.getPostId());
        when(voteRepository.existsByUserIdAndPostId(any(), any())).thenReturn(true);
        when(voteRepository.findByUserIdAndPostId(any(), any())).thenReturn(vote);

        assertThatThrownBy(() -> voteService.saveVote(voteFalseResponseDto))
                .isInstanceOf(UnauthorizedEditException.class);
    }

    @Test
    @DisplayName("게시글 아이디와 회원 이메일에 따른 투표 조회 테스트")
    void testFindVote() {
        when(userService.findUserByEmail(any())).thenReturn(vote.getUserId());
        when(postService.findPostByPostId(any())).thenReturn(vote.getPostId());
        when(voteRepository.existsByUserIdAndPostId(any(), any())).thenReturn(true);
        when(voteRepository.findByUserIdAndPostId(any(), any())).thenReturn(vote);

        String result = voteService.findVote(vote.getPostId().getId(), vote.getUserId().getEmail());

        assertNotNull(result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(postService, times(1)).findPostByPostId(any());
        verify(voteRepository, times(1)).existsByUserIdAndPostId(any(), any());
        verify(voteRepository, times(1)).findByUserIdAndPostId(any(), any());
    }
}