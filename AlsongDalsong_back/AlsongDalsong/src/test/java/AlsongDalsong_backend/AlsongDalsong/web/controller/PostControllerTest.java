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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.config.SecurityConfig;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.jwt.JwtRequestFilter;
import AlsongDalsong_backend.AlsongDalsong.service.post.PostService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostSaveRequestVO;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostUpdateRequestVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
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
 * 게시글 컨트롤러 테스트
 */
@WebMvcTest(controllers = PostController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class PostControllerTest {
    private Post post;
    private PostSaveRequestVO postSaveRequestVO;
    private PostUpdateRequestVO postUpdateRequestVO;
    private PostResponseDto postResponseDto;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostService postService;

    @BeforeEach
    void setUp() {
        post = TestObjectFactory.initPost();
        post.setUser(TestObjectFactory.initUser());
        post.addPhotoList(TestObjectFactory.initPhoto());
        post.addCommentList(TestObjectFactory.initComment());
        post.addVoteList(TestObjectFactory.initVote());
        post.addScrapList(TestObjectFactory.initScrap());

        postSaveRequestVO = TestObjectFactory.initPostSaveRequestVO(post);
        postUpdateRequestVO = TestObjectFactory.initPostUpdateRequestVO(post);
        postResponseDto = TestObjectFactory.initPostResponseDto(post);
    }

    @Test
    @DisplayName("게시글을 작성한 후, 작성한 게시글 리턴 테스트")
    void testPostAdd() throws Exception {
        when(postService.addPostWithPhotos(any(), any())).thenReturn(postResponseDto);

        mockMvc.perform(post("/api/post/save")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(new ObjectMapper().writeValueAsString(postSaveRequestVO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(postUpdateRequestVO.getId()));

        verify(postService, times(1)).addPostWithPhotos(any(), any());
    }

    @Test
    @DisplayName("게시글 아이디에 따라 게시글 상세 조회 리턴 테스트")
    void testPostDetails() throws Exception {
        when(postService.findPostDetailByPostId(any())).thenReturn(postResponseDto);

        mockMvc.perform(get("/api/post/inquire")
                        .param("id", String.valueOf(post.getId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postUpdateRequestVO.getId()));

        verify(postService, times(1)).findPostDetailByPostId(any());
    }

    @Test
    @DisplayName("살까 말까 / 할까 말까 / 갈까 말까로 분류별 최신글 목록 조회 리턴 테스트")
    void testPostLatestList() throws Exception {
        when(postService.findLatestPosts(any())).thenReturn(Collections.singletonList(postResponseDto));

        mockMvc.perform(get("/api/post/inquireLatest")
                        .param("todo", post.getTodo().getTodo())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(postService, times(1)).findLatestPosts(any());
    }

    @Test
    @DisplayName("살까 말까 / 할까 말까 / 갈까 말까로 분류별 인기글 목록 조회 리턴 테스트")
    void testPostPopularList() throws Exception {
        when(postService.findPopularPosts(any())).thenReturn(Collections.singletonList(postResponseDto));

        mockMvc.perform(get("/api/post/inquirePopular")
                        .param("todo", post.getTodo().getTodo())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(postService, times(1)).findPopularPosts(any());
    }

    @Test
    @DisplayName("분류의 카테고리별로 게시글 목록 조회 리턴 테스트")
    void testPostCategoryList() throws Exception {
        when(postService.findPostsByCategory(any(), any())).thenReturn(Collections.singletonList(postResponseDto));

        mockMvc.perform(get("/api/post/inquireCategory")
                        .param("todo", post.getTodo().getTodo())
                        .param("category", post.getCategory().getCategory())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(postService, times(1)).findPostsByCategory(any(), any());
    }

    @Test
    @DisplayName("사용자별 쓴 글 목록 조회 리턴 테스트")
    void testPostUserList() throws Exception {
        when(postService.findUserPosts(any())).thenReturn(Collections.singletonList(postResponseDto));

        mockMvc.perform(get("/api/post/my")
                        .param("email", post.getUserId().getEmail())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(postService, times(1)).findUserPosts(any());
    }

    @Test
    @DisplayName("게시글을 수정한 후, 수정한 게시글 리턴 테스트")
    void testPostModify() throws Exception {
        when(postService.modifyPost(any(), any(), any())).thenReturn(postResponseDto);

        mockMvc.perform(put("/api/post/update")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(new ObjectMapper().writeValueAsString(postUpdateRequestVO))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(postUpdateRequestVO.getId()));

        verify(postService, times(1)).modifyPost(any(), any(), any());
    }

    @Test
    @DisplayName("게시글을 삭제한 후, true 리턴 테스트")
    void testPostRemove() throws Exception {
        when(postService.removePost(any(), any())).thenReturn(true);

        mockMvc.perform(delete("/api/post/delete")
                        .param("id", String.valueOf(post.getId()))
                        .param("email", post.getUserId().getEmail())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(postService, times(1)).removePost(any(), any());
    }

    @Test
    @DisplayName("결정 미정이었던 게시글을 결정/취소에 따라 게시글을 확정한 후, 확정된 게시글 리턴 테스트")
    void testPostModifyDecision() throws Exception {
        when(postService.modifyPostDecision(any(), any(), any(), any())).thenReturn(postResponseDto);

        mockMvc.perform(put("/api/post/updateDecision")
                        .param("id", String.valueOf(post.getId()))
                        .param("email", post.getUserId().getEmail())
                        .param("decision", post.getDecision().getDecision())
                        .param("reason", post.getReason())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(postUpdateRequestVO.getId()));

        verify(postService, times(1)).modifyPostDecision(any(), any(), any(), any());
    }
}