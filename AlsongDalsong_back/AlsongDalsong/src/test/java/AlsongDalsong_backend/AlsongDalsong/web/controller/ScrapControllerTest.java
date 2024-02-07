package AlsongDalsong_backend.AlsongDalsong.web.controller;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
import AlsongDalsong_backend.AlsongDalsong.domain.post.Category;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Decision;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Old;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Todo;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Who;
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
    private Post post;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScrapService scrapService;

    @BeforeEach
    void setUp() {
        User user = new User();

        Todo todo = Todo.TO_BUY_OR_NOT_TO_BUY;
        Category category = Category.FASHION;
        Who who = Who.WOMAN;
        Old old = Old.TEENS;
        String date = "언제";
        String what = "무엇을";
        String content = "내용";
        String link = "링크";
        Integer importance = 3;
        Decision decision = Decision.UNDECIDED;
        String reason = "결정 이유";
        post = new Post(todo, category, who, old, date, what, content, link, importance, decision, reason);
        post.setUser(user);

        scrap = new Scrap();
        scrap.setUser(user);
        scrap.setPost(post);
    }

    @Test
    void testScrapSaveAdd() throws Exception {
        when(scrapService.saveScrap(any())).thenReturn(true);

        ScrapRequestDto scrapRequestDto = ScrapRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .build();
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

        ScrapRequestDto scrapRequestDto = ScrapRequestDto.builder()
                .email(VALID_EMAIL)
                .postId(VALID_POST_ID)
                .build();
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
                        .param("postId", String.valueOf(anyLong()))
                        .param("email", anyString())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(scrapService, times(1)).findScrap(any(), any());
    }

    @Test
    void testScrapUserList() throws Exception {
        when(scrapService.findUserScraps(any()))
                .thenReturn(Collections.singletonList(new ScrapResponseDto(scrap.getPostId())));

        mockMvc.perform(get("/api/scrap/inquire")
                        .param("email", anyString())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(scrapService, times(1)).findUserScraps(any());
    }
}