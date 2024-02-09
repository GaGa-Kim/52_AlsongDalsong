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
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_SCRAP_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_TODO;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_USER_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_WHAT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_WHO;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import AlsongDalsong_backend.AlsongDalsong.config.SecurityConfig;
import AlsongDalsong_backend.AlsongDalsong.config.jwt.JwtRequestFilter;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.Scrap;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.service.scrap.ScrapService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
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
 * 스크랩 컨트롤러 테스트
 */
@WebMvcTest(controllers = ScrapController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)
        })
@WithMockUser(username = "테스트_최고관리자", roles = {"USER"})
class ScrapControllerTest {
    private Scrap scrap;
    private ScrapRequestDto scrapRequestDto;
    private ScrapResponseDto scrapResponseDto;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScrapService scrapService;

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

        scrap = Scrap.builder()
                .id(VALID_SCRAP_ID)
                .build();
        scrap.setUser(user);
        scrap.setPost(post);
        scrapRequestDto = ScrapRequestDto.builder()
                .email(scrap.getUserId().getEmail())
                .postId(scrap.getPostId().getId())
                .build();
        scrapResponseDto = new ScrapResponseDto(scrap.getPostId());
    }

    @Test
    void testScrapSaveAdd() throws Exception {
        when(scrapService.saveScrap(any())).thenReturn(true);

        mockMvc.perform(post("/api/scrap/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(scrapRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(scrapService, times(1)).saveScrap(any());
    }

    @Test
    void testScrapSaveDelete() throws Exception {
        when(scrapService.saveScrap(any())).thenReturn(false);

        mockMvc.perform(post("/api/scrap/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(scrapRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));

        verify(scrapService, times(1)).saveScrap(any());
    }

    @Test
    void testScrapDetails() throws Exception {
        when(scrapService.findScrap(any(), any())).thenReturn(true);

        mockMvc.perform(post("/api/scrap/check")
                        .param("postId", String.valueOf(scrap.getPostId().getId()))
                        .param("email", scrap.getUserId().getEmail())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(scrapService, times(1)).findScrap(any(), any());
    }

    @Test
    void testScrapUserList() throws Exception {
        when(scrapService.findUserScraps(any())).thenReturn(Collections.singletonList(scrapResponseDto));

        mockMvc.perform(get("/api/scrap/inquire")
                        .param("email", scrap.getUserId().getEmail())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(scrapService, times(1)).findUserScraps(any());
    }
}