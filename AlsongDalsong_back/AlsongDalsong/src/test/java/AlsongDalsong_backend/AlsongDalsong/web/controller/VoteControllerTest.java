package AlsongDalsong_backend.AlsongDalsong.web.controller;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_VOTE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import AlsongDalsong_backend.AlsongDalsong.config.SecurityConfig;
import AlsongDalsong_backend.AlsongDalsong.config.jwt.JwtRequestFilter;
import AlsongDalsong_backend.AlsongDalsong.service.vote.VoteService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.vote.VoteRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoteService voteService;

    @Test
    void testVoteSaveAdd() throws Exception {
        when(voteService.saveVote(any())).thenReturn("true");

        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .vote(VALID_VOTE)
                .build();
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

        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .vote(VALID_VOTE)
                .build();
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

        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .vote(VALID_VOTE)
                .build();
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
                        .param("postId", String.valueOf(anyLong()))
                        .param("email", anyString())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("true"));

        verify(voteService, times(1)).findVote(any(), any());
    }
}