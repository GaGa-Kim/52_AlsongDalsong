package AlsongDalsong_backend.AlsongDalsong.web.dto.photo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * PhotoResponseDto 검증 테스트
 */
class PhotoResponseDtoTest {
    @Test
    @DisplayName("PhotoResponseDto 생성 테스트")
    void testPhotoResponseDto() {
        Photo photo = TestObjectFactory.initPhoto();
        photo.setPost(TestObjectFactory.initPost());

        PhotoResponseDto photoResponseDto = PhotoResponseDto.builder()
                .origPhotoName(photo.getOrigPhotoName())
                .photoName(photo.getPhotoName())
                .photoUrl(photo.getPhotoUrl())
                .build();
        
        assertEquals(photo.getOrigPhotoName(), photoResponseDto.getOrigPhotoName());
        assertEquals(photo.getOrigPhotoName(), photoResponseDto.getOrigPhotoName());
        assertEquals(photo.getPhotoName(), photoResponseDto.getPhotoName());
        assertEquals(photo.getPhotoUrl(), photoResponseDto.getPhotoUrl());
    }
}