package AlsongDalsong_backend.AlsongDalsong.web.controller;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_CATEGORY;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_DATE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_DECISION;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_IMPORTANCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_INTRODUCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_KAKAO_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_LINK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NICKNAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_OLD;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_CONTENT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PROFILE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_REASON;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_TODO;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_USER_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_VOTE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_VOTE_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_WHAT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_WHO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import AlsongDalsong_backend.AlsongDalsong.config.SecurityConfig;
import AlsongDalsong_backend.AlsongDalsong.config.jwt.JwtRequestFilter;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.Vote;
import AlsongDalsong_backend.AlsongDalsong.service.vote.VoteService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.vote.VoteRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 투표 컨트롤러 테스트
 */
@WebMvcTest(controllers = VoteController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)
        })
@WithMockUser(username = "테스트_최고관리자", roles = {"USER"})
class VoteControllerTest {
    private Vote vote;
    private VoteRequestDto voteRequestDto;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoteService voteService;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(VALID_USER_ID)
                .kakaoId(VALID_KAKAO_ID)
                .name(VALID_NAME)
                .email(VALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();
        Post post = Post.builder()
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

        vote = Vote.builder()
                .id(VALID_VOTE_ID)
                .vote(VALID_VOTE)
                .build();
        vote.setUser(user);
        vote.setPost(post);
        voteRequestDto = VoteRequestDto.builder()
                .email(vote.getUserId().getEmail())
                .postId(vote.getPostId().getId())
                .vote(vote.getVote())
                .build();
    }

    @Test
    void testVoteSaveAdd() throws Exception {
        when(voteService.saveVote(any())).thenReturn("true");

        mockMvc.perform(post("/api/vote/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(voteRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("true"));

        verify(voteService, times(1)).saveVote(any());
    }

    @Test
    void testVoteSaveDelete() throws Exception {
        when(voteService.saveVote(any())).thenReturn("투표하지 않았습니다.");

        mockMvc.perform(post("/api/vote/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(voteRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("투표하지 않았습니다."));

        verify(voteService, times(1)).saveVote(any());
    }

    @Test
    void testVoteSaveModify() throws Exception {
        when(voteService.saveVote(any())).thenReturn("false");

        mockMvc.perform(post("/api/vote/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(voteRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("false"));

        verify(voteService, times(1)).saveVote(any());
    }

    @Test
    void voteDetails() throws Exception {
        when(voteService.findVote(any(), any())).thenReturn("true");

        mockMvc.perform(post("/api/vote/check")
                        .param("postId", String.valueOf(vote.getPostId().getId()))
                        .param("email", vote.getUserId().getEmail())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("true"));

        verify(voteService, times(1)).findVote(any(), any());
    }
}