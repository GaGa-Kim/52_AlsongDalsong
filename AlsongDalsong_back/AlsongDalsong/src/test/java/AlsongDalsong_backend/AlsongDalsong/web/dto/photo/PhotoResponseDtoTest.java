package AlsongDalsong_backend.AlsongDalsong.web.dto.photo;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_ORIG_PHOTO_NANE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PHOTO_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PHOTO_NAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PHOTO_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import org.junit.jupiter.api.Test;

/**
 * PhotoResponseDto 검증 테스트
 */
class PhotoResponseDtoTest {
    @Test
    void
    testPhotoResponseDto() {
        Photo photo = Photo.builder()
                .id(VALID_PHOTO_ID)
                .origPhotoName(VALID_ORIG_PHOTO_NANE)
                .photoName(VALID_PHOTO_NAME)
                .photoUrl(VALID_PHOTO_URL)
                .build();

        PhotoResponseDto photoResponseDto = new PhotoResponseDto(photo.getOrigPhotoName(), photo.getPhotoName(), photo.getPhotoUrl());

        assertEquals(photo.getOrigPhotoName(), photoResponseDto.getOrigPhotoName());
        assertEquals(photo.getOrigPhotoName(), photoResponseDto.getOrigPhotoName());
        assertEquals(photo.getPhotoName(), photoResponseDto.getPhotoName());
        assertEquals(photo.getPhotoUrl(), photoResponseDto.getPhotoUrl());
    }
}