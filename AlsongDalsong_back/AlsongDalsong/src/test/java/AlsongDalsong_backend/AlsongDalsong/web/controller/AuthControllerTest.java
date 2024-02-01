package AlsongDalsong_backend.AlsongDalsong.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
@WebMvcTest(controllers = AuthController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)
        })
@WithMockUser(username = "테스트_최고관리자", roles = {"USER"})
class AuthControllerTest {
    private User user;
    private TokenDto tokenDto;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @BeforeEach
    void setUp() {
        Long kakaoId = 123L;
        String name = "이름";
        String email = "이메일";
        String nickname = "닉네임";
        String profile = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
        String introduce = "소개";
        user = new User(kakaoId, name, email, nickname, profile, introduce);
        tokenDto = new TokenDto("code", "이메일");
    }

    @Test
    void testSocialSignup() throws Exception {
        when(authService.socialSignupAndGenerateToken(any())).thenReturn(tokenDto);

        mockMvc.perform(get("/auth/kakao")
                        .param("code", anyString()))
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andExpect(content().string(tokenDto.getEmail()));

        verify(authService, times(1)).socialSignupAndGenerateToken(any());
    }

    @Test
    void testSignup() throws Exception {
        when(authService.signupAndReturnUser(any())).thenReturn(new UserResponseDto(user));

        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userSaveRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.nickname").value(user.getNickname()));

        verify(authService, times(1)).signupAndReturnUser(any());
    }

    @Test
    void testLogin() throws Exception {
        when(authService.loginAndGenerateToken(any())).thenReturn(tokenDto);

        mockMvc.perform(get("/auth/login")
                        .param("email", anyString()))
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andExpect(content().string(tokenDto.getEmail()));

        verify(authService, times(1)).loginAndGenerateToken(any());
    }
}