package AlsongDalsong_backend.AlsongDalsong.service.vote;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_VOTE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.Vote;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.VoteRepository;
import AlsongDalsong_backend.AlsongDalsong.service.post.PostService;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.vote.VoteRequestDto;
import org.junit.jupiter.api.BeforeEach;
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
    private User mockUser;
    private Post mockPost;
    private Vote vote;

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
        mockUser = mock(User.class);
        mockPost = mock(Post.class);

        vote = Vote.builder()
                .vote(VALID_VOTE)
                .build();
        vote.setUser(mockUser);
        vote.setPost(mockPost);
    }

    @Test
    void testSaveAddVote() {
        when(userService.findUserByEmail(any())).thenReturn(mockUser);
        when(postService.findPostByPostId(any())).thenReturn(mockPost);
        when(voteRepository.existsByUserIdAndPostId(any(), any())).thenReturn(false);
        when(voteRepository.save(any())).thenReturn(vote);

        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .email(vote.getUserId().getEmail())
                .postId(vote.getPostId().getId())
                .vote(true)
                .build();
        String result = voteService.saveVote(voteRequestDto);

        assertEquals("true", result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(postService, times(1)).findPostByPostId(anyLong());
        verify(voteRepository, times(1)).existsByUserIdAndPostId(any(), any());
        verify(voteRepository, times(1)).save(any());
        verify(mockPost, times(1)).addVoteList(any());
        verify(mockUser, times(1)).updatePoint(any());
    }

    @Test
    void testSaveDeleteVote() {
        when(userService.findUserByEmail(any())).thenReturn(mockUser);
        when(postService.findPostByPostId(any())).thenReturn(mockPost);
        when(voteRepository.existsByUserIdAndPostId(any(), any())).thenReturn(true);
        when(voteRepository.findByUserIdAndPostId(any(), any())).thenReturn(vote);
        doNothing().when(voteRepository).delete(vote);

        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .email(vote.getUserId().getEmail())
                .postId(vote.getPostId().getId())
                .vote(true)
                .build();
        String result = voteService.saveVote(voteRequestDto);

        assertEquals("투표하지 않았습니다.", result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(postService, times(1)).findPostByPostId(any());
        verify(voteRepository, times(1)).existsByUserIdAndPostId(any(), any());
        verify(voteRepository, times(1)).findByUserIdAndPostId(any(), any());
        verify(voteRepository, times(1)).delete(any());
        verify(mockUser, times(1)).updatePoint(any());
    }

    @Test
    void testSaveModifyVote() {
        when(userService.findUserByEmail(any())).thenReturn(mockUser);
        when(postService.findPostByPostId(any())).thenReturn(mockPost);
        when(voteRepository.existsByUserIdAndPostId(any(), any())).thenReturn(true);
        when(voteRepository.findByUserIdAndPostId(any(), any())).thenReturn(vote);

        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .email(vote.getUserId().getEmail())
                .postId(vote.getPostId().getId())
                .vote(false)
                .build();
        String result = voteService.saveVote(voteRequestDto);

        assertEquals("false", result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(postService, times(1)).findPostByPostId(any());
        verify(voteRepository, times(1)).existsByUserIdAndPostId(any(), any());
        verify(voteRepository, times(1)).findByUserIdAndPostId(any(), any());
    }

    @Test
    void testFindVote() {
        when(userService.findUserByEmail(any())).thenReturn(mockUser);
        when(postService.findPostByPostId(any())).thenReturn(mockPost);
        when(voteRepository.existsByUserIdAndPostId(any(), any())).thenReturn(true);
        when(voteRepository.findByUserIdAndPostId(any(), any())).thenReturn(vote);

        String result = voteService.findVote(mockPost.getId(), mockUser.getEmail());

        assertNotNull(result);

        verify(userService, times(1)).findUserByEmail(any());
        verify(postService, times(1)).findPostByPostId(any());
        verify(voteRepository, times(1)).existsByUserIdAndPostId(any(), any());
        verify(voteRepository, times(1)).findByUserIdAndPostId(any(), any());
    }
}