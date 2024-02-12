package AlsongDalsong_backend.AlsongDalsong.web.controller;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_KAKAO_CODE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.config.SecurityConfig;
import AlsongDalsong_backend.AlsongDalsong.config.jwt.JwtRequestFilter;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.service.user.AuthService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.auth.TokenDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserSaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * 인증 컨트롤러 테스트
 */
@WebMvcTest(controllers = AuthController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class AuthControllerTest {
    private static final String AUTHORIZATION = "Authorization";
    private User user;
    private UserSaveRequestDto userSaveRequestDto;
    private UserResponseDto userResponseDto;
    private TokenDto tokenDto;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;

    @BeforeEach
    void setUp() {
        user = TestObjectFactory.initUser();
        user.addPostList(TestObjectFactory.initPost());
        user.addScrapList(TestObjectFactory.initScrap());

        userSaveRequestDto = TestObjectFactory.initUserSaveRequestDto(user);
        userResponseDto = TestObjectFactory.initUserResponseDto(user);
        tokenDto = TestObjectFactory.initTokenDto(user);
    }

    @Test
    void testSocialSignup() throws Exception {
        when(authService.socialSignupAndGenerateToken(any())).thenReturn(tokenDto);

        mockMvc.perform(get("/auth/kakao")
                        .param("code", VALID_KAKAO_CODE))
                .andExpect(status().isOk())
                .andExpect(header().exists(AUTHORIZATION))
                .andExpect(content().string(tokenDto.getEmail()));

        verify(authService, times(1)).socialSignupAndGenerateToken(any());
    }

    @Test
    void testSignup() throws Exception {
        when(authService.signupAndReturnUser(any())).thenReturn(userResponseDto);

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userSaveRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(userSaveRequestDto.getEmail()))
                .andExpect(jsonPath("$.nickname").value(userSaveRequestDto.getNickname()));

        verify(authService, times(1)).signupAndReturnUser(any());
    }

    @Test
    void testLogin() throws Exception {
        when(authService.loginAndGenerateToken(any())).thenReturn(tokenDto);

        mockMvc.perform(get("/auth/login")
                        .param("email", user.getEmail()))
                .andExpect(status().isOk())
                .andExpect(header().exists(AUTHORIZATION))
                .andExpect(content().string(tokenDto.getEmail()));

        verify(authService, times(1)).loginAndGenerateToken(any());
    }
}