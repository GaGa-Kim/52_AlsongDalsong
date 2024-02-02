package AlsongDalsong_backend.AlsongDalsong.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.service.post.PostService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostSaveRequestVO;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostUpdateRequestVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 게시글 컨트롤러 테스트
 */
@WebMvcTest(controllers = PostController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)
        })
@WithMockUser(username = "테스트_최고관리자", roles = {"USER"})
class PostControllerTest {
    private Post post;
    private List<Long> photoId;
    private Pair<Long, Long> vote;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

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
        photoId = new ArrayList<>();
        vote = Pair.of(0L, 0L);
    }

    @Test
    void testPostAdd() throws Exception {
        when(postService.addPostWithPhotos(any(), any())).thenReturn(new PostResponseDto(post, photoId, vote));

        PostSaveRequestVO postSaveRequestVO = new PostSaveRequestVO();
        mockMvc.perform(post("/api/post/save")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(new ObjectMapper().writeValueAsString(postSaveRequestVO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(post.getId()));

        verify(postService, times(1)).addPostWithPhotos(any(), any());
    }

    @Test
    void testPostDetails() throws Exception {
        when(postService.findPostDetailByPostId(any())).thenReturn(new PostResponseDto(post, photoId, vote));

        mockMvc.perform(get("/api/post/inquire")
                        .param("id", String.valueOf(anyLong()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()));

        verify(postService, times(1)).findPostDetailByPostId(any());
    }

    @Test
    void testPostLatestList() throws Exception {
        when(postService.findLatestPosts(any()))
                .thenReturn(Collections.singletonList(new PostResponseDto(post, photoId, vote)));

        mockMvc.perform(get("/api/post/inquireLatest")
                        .param("todo", anyString())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(postService, times(1)).findLatestPosts(any());
    }

    @Test
    void testPostPopularList() throws Exception {
        when(postService.findPopularPosts(any()))
                .thenReturn(Collections.singletonList(new PostResponseDto(post, photoId, vote)));

        mockMvc.perform(get("/api/post/inquirePopular")
                        .param("todo", anyString())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(postService, times(1)).findPopularPosts(any());
    }

    @Test
    void testPostCategoryList() throws Exception {
        when(postService.findPostsByCategory(any(), any()))
                .thenReturn(Collections.singletonList(new PostResponseDto(post, photoId, vote)));

        mockMvc.perform(get("/api/post/inquireCategory")
                        .param("todo", anyString())
                        .param("category", anyString())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(postService, times(1)).findPostsByCategory(any(), any());
    }

    @Test
    void testPostUserList() throws Exception {
        when(postService.findUserPosts(any()))
                .thenReturn(Collections.singletonList(new PostResponseDto(post, photoId, vote)));

        mockMvc.perform(get("/api/post/my")
                        .param("email", anyString())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(postService, times(1)).findUserPosts(any());
    }

    @Test
    void testPostModify() throws Exception {
        when(postService.modifyPost(any(), any(), any())).thenReturn(new PostResponseDto(post, photoId, vote));

        PostUpdateRequestVO postUpdateRequestVO = new PostUpdateRequestVO();
        mockMvc.perform(put("/api/post/update")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(new ObjectMapper().writeValueAsString(postUpdateRequestVO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(post.getId()));

        verify(postService, times(1)).modifyPost(any(), any(), any());
    }

    @Test
    void testPostRemove() throws Exception {
        when(postService.removePost(any(), any())).thenReturn(true);

        mockMvc.perform(delete("/api/post/delete")
                        .param("id", String.valueOf(anyLong()))
                        .param("email", anyString())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(postService, times(1)).removePost(any(), any());
    }

    @Test
    void testPostModifyDecision() throws Exception {
        when(postService.modifyPostDecision(any(), any(), any(), any()))
                .thenReturn(new PostResponseDto(post, photoId, vote));

        mockMvc.perform(put("/api/post/updateDecision")
                        .param("id", String.valueOf(anyLong()))
                        .param("email", anyString())
                        .param("decision", anyString())
                        .param("reason", anyString())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(post.getId()));

        verify(postService, times(1)).modifyPostDecision(any(), any(), any(), any());
    }
}