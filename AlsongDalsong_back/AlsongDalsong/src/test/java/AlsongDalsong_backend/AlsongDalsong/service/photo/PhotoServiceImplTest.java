package AlsongDalsong_backend.AlsongDalsong.service.photo;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_ORIG_PHOTO_NANE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PHOTO_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PHOTO_NAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PHOTO_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.PhotoRepository;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoResponseDto;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 게시글 사진을 위한 비즈니스 로직 구현 클래스 테스트
 */
@ExtendWith(MockitoExtension.class)
class PhotoServiceImplTest {
    private Photo photo;

    @InjectMocks
    private PhotoServiceImpl photoService;

    @Mock
    private PhotoRepository photoRepository;

    @BeforeEach
    void setUp() {
        photo = Photo.builder()
                .id(VALID_PHOTO_ID)
                .origPhotoName(VALID_ORIG_PHOTO_NANE)
                .photoName(VALID_PHOTO_NAME)
                .photoUrl(VALID_PHOTO_URL)
                .build();
    }

    @Test
    void testAddPhoto() {
        when(photoRepository.save(any())).thenReturn(photo);

        Photo result = photoService.addPhoto(photo);

        assertNotNull(result);
        assertEquals(photo.getId(), result.getId());

        verify(photoRepository, times(1)).save(any());
    }

    @Test
    void testFindPhotoByPhotoId() {
        when(photoRepository.findById(any())).thenReturn(Optional.ofNullable(photo));

        Photo result = photoService.findPhotoByPhotoId(photo.getId());

        assertNotNull(result);
        assertEquals(photo.getId(), result.getId());

        verify(photoRepository, times(1)).findById(any());
    }

    @Test
    void testFindPhoto() {
        when(photoRepository.findById(any())).thenReturn(Optional.ofNullable(photo));

        PhotoResponseDto result = photoService.findPhoto(photo.getId());

        assertNotNull(result);
        assertEquals(photo.getPhotoUrl(), result.getPhotoUrl());

        verify(photoRepository, times(1)).findById(any());
    }

    @Test
    void testRemovePhoto() {
        doNothing().when(photoRepository).delete(any());

        photoService.removePhoto(photo);

        verify(photoRepository, times(1)).delete(any());
    }
}