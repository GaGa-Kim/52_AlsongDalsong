package AlsongDalsong_backend.AlsongDalsong.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.config.SecurityConfig;
import AlsongDalsong_backend.AlsongDalsong.config.jwt.JwtRequestFilter;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
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

/**
 * 댓글 컨트롤러 테스트
 */
@WebMvcTest(controllers = CommentController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class CommentControllerTest {
    private Comment comment;
    private CommentSaveRequestDto commentSaveRequestDto;
    private CommentUpdateRequestDto commentUpdateRequestDto;
    private CommentResponseDto commentResponseDto;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        comment = TestObjectFactory.initComment();
        comment.setUser(TestObjectFactory.initUser());
        comment.setPost(TestObjectFactory.initPost());
        comment.addLikeList(TestObjectFactory.initLike());

        commentSaveRequestDto = TestObjectFactory.initCommentSaveRequestDto(comment);
        commentUpdateRequestDto = TestObjectFactory.initCommentUpdateRequestDto(comment);
        commentResponseDto = TestObjectFactory.initCommentResponseDto(comment);
    }

    @Test
    void testCommentAdd() throws Exception {
        when(commentService.addComment(any())).thenReturn(Collections.singletonList(commentResponseDto));

        mockMvc.perform(post("/api/comment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentSaveRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(commentService, times(1)).addComment(any());
    }


    @Test
    void testCommentList() throws Exception {
        when(commentService.findPostCommentsByLikes(any())).thenReturn(Collections.singletonList(commentResponseDto));

        mockMvc.perform(get("/api/comment/inquire")
                        .param("postId", String.valueOf(comment.getPostId().getId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(commentService, times(1)).findPostCommentsByLikes(any());
    }


    @Test
    void testCommentModify() throws Exception {
        when(commentService.modifyComment(any())).thenReturn(Collections.singletonList(commentResponseDto));

        mockMvc.perform(put("/api/comment/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentUpdateRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(commentService, times(1)).modifyComment(any());
    }

    @Test
    void testCommentRemove() throws Exception {
        when(commentService.removeComment(any(), any())).thenReturn(true);

        mockMvc.perform(delete("/api/comment/delete")
                        .param("id", String.valueOf(comment.getId()))
                        .param("email", comment.getUserId().getEmail())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(commentService, times(1)).removeComment(any(), any());
    }
}