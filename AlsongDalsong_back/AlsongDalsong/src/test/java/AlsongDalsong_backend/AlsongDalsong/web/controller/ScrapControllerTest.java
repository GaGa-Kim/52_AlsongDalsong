package AlsongDalsong_backend.AlsongDalsong.web.controller;

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

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.config.SecurityConfig;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.Scrap;
import AlsongDalsong_backend.AlsongDalsong.jwt.JwtRequestFilter;
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
@WebMvcTest(controllers = ScrapController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
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
        scrap = TestObjectFactory.initScrap();
        scrap.setUser(TestObjectFactory.initUser());
        scrap.setPost(TestObjectFactory.initPost());

        scrapRequestDto = TestObjectFactory.initScrapRequestDto(scrap);
        scrapResponseDto = TestObjectFactory.initScrapResponseDto(scrap);
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