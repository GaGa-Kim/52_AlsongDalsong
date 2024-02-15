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
import AlsongDalsong_backend.AlsongDalsong.domain.like.Like;
import AlsongDalsong_backend.AlsongDalsong.jwt.JwtRequestFilter;
import AlsongDalsong_backend.AlsongDalsong.service.like.LikeService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.like.LikeRequestDto;
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
 * 댓글 좋아요 컨트롤러 테스트
 */
@WebMvcTest(controllers = LikeController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class LikeControllerTest {
    private Like like;
    private LikeRequestDto likeRequestDto;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        like = TestObjectFactory.initLike();
        like.setUser(TestObjectFactory.initUser());
        like.setComment(TestObjectFactory.initComment());

        likeRequestDto = TestObjectFactory.initLikeRequestDto(like);
    }

    @Test
    @DisplayName("게시글을 스크랩한 후, true 리턴 테스트")
    void testLikeSaveAdd() throws Exception {
        when(likeService.saveLike(any())).thenReturn(true);

        mockMvc.perform(post("/api/like/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(likeRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(likeService, times(1)).saveLike(any());
    }

    @Test
    @DisplayName("게시글이 이미 스크랩되어 있을 경우 스크랩이 취소되고 false 리턴 테스트")
    void testLikeSaveDelete() throws Exception {
        when(likeService.saveLike(any())).thenReturn(false);

        mockMvc.perform(post("/api/like/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(likeRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));

        verify(likeService, times(1)).saveLike(any());
    }

    @Test
    @DisplayName("사용자별 스크랩 목록 조회 리턴 테스트")
    void testLikeDetails() throws Exception {
        when(likeService.findLike(any(), any())).thenReturn(true);

        mockMvc.perform(post("/api/like/check")
                        .param("id", String.valueOf(like.getId()))
                        .param("email", like.getUserId().getEmail())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(likeService, times(1)).findLike(any(), any());
    }
}