package AlsongDalsong_backend.AlsongDalsong.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import AlsongDalsong_backend.AlsongDalsong.config.SecurityConfig;
import AlsongDalsong_backend.AlsongDalsong.config.jwt.JwtRequestFilter;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.service.comment.CommentService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentUpdateRequestDto;
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

@WebMvcTest(controllers = CommentController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)
        })
class CommentControllerTest {
    private Comment comment;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        User user = new User();
        Post post = new Post();

        comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
    }

    @Test
    @WithMockUser(username = "테스트_최고관리자", roles = {"USER"})
    void testCommentAdd() throws Exception {
        when(commentService.addComment(any()))
                .thenReturn(Collections.singletonList(new CommentResponseDto(comment)));

        CommentSaveRequestDto commentSaveRequestDto = new CommentSaveRequestDto();
        mockMvc.perform(post("/api/comment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentSaveRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }


    @Test
    @WithMockUser(username = "테스트_최고관리자", roles = {"USER"})
    void testCommentList() throws Exception {
        when(commentService.findPostCommentsByLikes(any()))
                .thenReturn(Collections.singletonList(new CommentResponseDto(comment)));

        mockMvc.perform(get("/api/comment/inquire")
                        .param("postId", String.valueOf(anyLong()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }


    @Test
    @WithMockUser(username = "테스트_최고관리자", roles = {"USER"})
    void testCommentModify() throws Exception {
        when(commentService.modifyComment(any()))
                .thenReturn(Collections.singletonList(new CommentResponseDto(comment)));

        CommentUpdateRequestDto commentUpdateRequestDto = new CommentUpdateRequestDto();
        mockMvc.perform(put("/api/comment/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentUpdateRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser(username = "테스트_최고관리자", roles = {"USER"})
    void testCommentRemove() throws Exception {
        when(commentService.removeComment(any(), any())).thenReturn(true);

        mockMvc.perform(delete("/api/comment/delete")
                        .param("id", String.valueOf(anyLong()))
                        .param("email", anyString())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }
}