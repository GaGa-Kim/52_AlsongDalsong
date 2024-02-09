package AlsongDalsong_backend.AlsongDalsong.web.controller;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_INTRODUCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_KAKAO_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NICKNAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PROFILE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_USER_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import AlsongDalsong_backend.AlsongDalsong.config.SecurityConfig;
import AlsongDalsong_backend.AlsongDalsong.config.jwt.JwtRequestFilter;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.service.photo.StorageService;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

/**
 * 회원 컨트롤러 테스트
 */
@WebMvcTest(controllers = UserController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)
        })
@WithMockUser(username = "테스트_최고관리자", roles = {"USER"})
class UserControllerTest {
    private User user;
    private UserUpdateRequestDto userUpdateRequestDto;
    private byte[] profileByteArray;
    private String profileByteBase;
    private HttpHeaders httpHeaders;
    private Map<String, Object> propensityMap;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private StorageService storageService;

    @BeforeEach
    void setUp() throws IOException {
        user = User.builder()
                .id(VALID_USER_ID)
                .kakaoId(VALID_KAKAO_ID)
                .name(VALID_NAME)
                .email(VALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();
        userUpdateRequestDto = UserUpdateRequestDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .introduce(user.getIntroduce())
                .build();
        profileByteArray = IOUtils.toByteArray(user.getProfile());
        profileByteBase = Base64.getEncoder().encodeToString(profileByteArray);
        httpHeaders = new HttpHeaders();
        propensityMap = new HashMap<>();
    }

    @Test
    void testUserDetails() throws Exception {
        when(userService.findUserByEmail(any())).thenReturn(user);

        mockMvc.perform(get("/api/user/me")
                        .param("email", user.getEmail())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()));

        verify(userService, times(1)).findUserByEmail(any());
    }

    @Test
    void testUserProfileModify() throws Exception {
        when(userService.modifyUserProfile(any())).thenReturn(user);

        mockMvc.perform(put("/api/user/updateInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userUpdateRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(user.getId()));

        verify(userService, times(1)).modifyUserProfile(any());
    }

    @Test
    void testUserProfileImageAsUrl() throws Exception {
        when(userService.findUserByEmail(any())).thenReturn(user);

        mockMvc.perform(get("/api/user/profileUrl")
                        .param("email", user.getEmail())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(user.getProfile()));

        verify(userService, times(1)).findUserByEmail(any());
    }

    @Test
    void testUserProfileImageAsBytes() throws Exception {
        when(userService.findUserByEmail(any())).thenReturn(user);
        when(userService.findUserProfileImageAsBytes(any()))
                .thenReturn(new ResponseEntity<>(profileByteArray, httpHeaders, HttpStatus.OK));

        mockMvc.perform(get("/api/user/profileByte")
                        .param("email", user.getEmail())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(user.getProfile()));

        verify(userService, times(1)).findUserByEmail(any());
        verify(userService, times(1)).findUserProfileImageAsBytes(any());
    }

    @Test
    void testUserProfileImageAsBase64() throws Exception {
        when(userService.findUserByEmail(any())).thenReturn(user);
        when(userService.findUserProfileImageAsBase64(any()))
                .thenReturn(new ResponseEntity<>(profileByteBase, httpHeaders, HttpStatus.OK));

        mockMvc.perform(get("/api/user/profileBase")
                        .param("email", user.getEmail())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(profileByteBase));

        verify(userService, times(1)).findUserByEmail(any());
        verify(userService, times(1)).findUserProfileImageAsBase64(any());
    }

    @Test
    void testUserProfileImageModify() throws Exception {
        when(userService.modifyUserProfileImage(any(), any())).thenReturn(user);

        MockMultipartFile profile = new MockMultipartFile("file", "newProfile.jpg", "image/jpeg", profileByteArray);
        MockMultipartHttpServletRequestBuilder builder = multipart("/api/user/updateProfile");
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });
        mockMvc.perform(builder
                        .file("multipartFile", profile.getBytes())
                        .param("email", user.getEmail())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(user.getId()));

        verify(userService, times(1)).modifyUserProfileImage(any(), any());
    }

    @Test
    void testUserPropensityDetails() throws Exception {
        when(userService.findUserDecisionPropensity(any())).thenReturn(propensityMap);

        mockMvc.perform(get("/api/user/propensity")
                        .param("email", user.getEmail())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(propensityMap));

        verify(userService, times(1)).findUserDecisionPropensity(any());
    }

    @Test
    void testUserWithdraw() throws Exception {
        when(userService.withdrawUserAccount(any())).thenReturn(true);

        mockMvc.perform(post("/api/user/withdraw")
                        .param("email", user.getEmail())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(userService, times(1)).withdrawUserAccount(any());
    }
}