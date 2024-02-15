package AlsongDalsong_backend.AlsongDalsong.web.dto.photo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * PhotoIdResponseDto 검증 테스트
 */
class PhotoIdResponseDtoTest {
    @Test
    @DisplayName("PhotoIdResponseDto 생성 테스트")
    void testPhotoIdResponseDto() {
        Photo photo = TestObjectFactory.initPhoto();
        photo.setPost(TestObjectFactory.initPost());

        PhotoIdResponseDto photoIdResponseDto = new PhotoIdResponseDto(photo);

        assertEquals(photo.getId(), photoIdResponseDto.getPhotoId());
    }
}