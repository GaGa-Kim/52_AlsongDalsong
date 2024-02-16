package AlsongDalsong_backend.AlsongDalsong.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.config.SecurityConfig;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.Vote;
import AlsongDalsong_backend.AlsongDalsong.jwt.JwtRequestFilter;
import AlsongDalsong_backend.AlsongDalsong.service.vote.VoteService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.vote.VoteRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
@WebMvcTest(controllers = VoteController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class VoteControllerTest {
    private Vote vote;
    private VoteRequestDto voteRequestDto;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VoteService voteService;

    @BeforeEach
    void setUp() {
        vote = TestObjectFactory.initVote();
        vote.setUser(TestObjectFactory.initUser());
        vote.setPost(TestObjectFactory.initPost());

        voteRequestDto = TestObjectFactory.initVoteRequestDto(vote, vote.getVote());
    }

    @Test
    @DisplayName("게시글에 투표를 한 후, true 리턴 테스트")
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
    @DisplayName("게시글에 투표를 취소한 후, 투표하지 않았습니다 리턴 테스트")
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
    @DisplayName("게시글에 투표를 변경한 후, false 리턴 테스트")
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
    @DisplayName("사용자별로 게시글 투표 조회 테스트")
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