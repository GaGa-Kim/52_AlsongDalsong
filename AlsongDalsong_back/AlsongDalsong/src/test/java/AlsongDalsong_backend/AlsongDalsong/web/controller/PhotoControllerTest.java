package AlsongDalsong_backend.AlsongDalsong.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.config.SecurityConfig;
import AlsongDalsong_backend.AlsongDalsong.config.jwt.JwtRequestFilter;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.service.photo.PhotoService;
import AlsongDalsong_backend.AlsongDalsong.service.photo.StorageService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoResponseDto;
import java.io.IOException;
import java.util.Base64;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 사진 컨트롤러 테스트
 */
@WebMvcTest(controllers = PhotoController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class PhotoControllerTest {
    private Photo photo;
    private PhotoResponseDto photoResponseDto;
    private byte[] photoByteArray;
    private String photoByteBase;
    private HttpHeaders httpHeaders;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PhotoService photoService;
    @MockBean
    private StorageService storageService;

    @BeforeEach
    void setUp() throws IOException {
        photo = TestObjectFactory.initPhoto();
        photo.setPost(TestObjectFactory.initPost());

        photoResponseDto = TestObjectFactory.initPhotoResponseDto(photo);
        photoByteArray = IOUtils.toByteArray(photo.getPhotoUrl());
        photoByteBase = Base64.getEncoder().encodeToString(photoByteArray);
        httpHeaders = new HttpHeaders();
    }

    @Test
    void testPhotoDetails() throws Exception {
        when(photoService.findPhoto(any())).thenReturn(photoResponseDto);

        mockMvc.perform(get("/api/photo/photoInfo")
                        .param("id", String.valueOf(photo.getId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.origPhotoName").value(photo.getOrigPhotoName()))
                .andExpect(jsonPath("$.photoName").value(photo.getPhotoName()))
                .andExpect(jsonPath("$.photoUrl").value(photo.getPhotoUrl()));

        verify(photoService, times(1)).findPhoto(any());
    }

    @Test
    void testPhotoUrlDetails() throws Exception {
        when(photoService.findPhotoByPhotoId(any())).thenReturn(photo);
        when(storageService.findFileUrl(any())).thenReturn(photo.getPhotoUrl());

        mockMvc.perform(get("/api/photo/photoURL")
                        .param("id", String.valueOf(photo.getId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(photo.getPhotoUrl()));

        verify(storageService, times(1)).findFileUrl(any());
    }

    @Test
    void testPhotoByteArrayDetails() throws Exception {
        when(photoService.findPhotoByPhotoId(any())).thenReturn(photo);
        when(storageService.findFileObject(photo.getOrigPhotoName(), photo.getPhotoName()))
                .thenReturn(new ResponseEntity<>(photoByteArray, httpHeaders, HttpStatus.OK));

        mockMvc.perform(get("/api/photo/photoByte")
                        .param("id", String.valueOf(photo.getId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(photoByteArray));

        verify(storageService, times(1)).findFileObject(any(), any());
    }

    @Test
    void testPhotoBase64Details() throws Exception {
        when(photoService.findPhotoByPhotoId(any())).thenReturn(photo);
        when(storageService.findFileBase64(photo.getOrigPhotoName(), photo.getPhotoName()))
                .thenReturn(new ResponseEntity<>(photoByteBase, httpHeaders, HttpStatus.OK));

        mockMvc.perform(get("/api/photo/photoBase")
                        .param("id", String.valueOf(photo.getId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(photoByteBase));

        verify(storageService, times(1)).findFileBase64(any(), any());
    }
}