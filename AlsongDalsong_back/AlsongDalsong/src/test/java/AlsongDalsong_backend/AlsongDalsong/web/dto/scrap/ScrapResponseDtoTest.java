package AlsongDalsong_backend.AlsongDalsong.web.dto.scrap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.Scrap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * ScrapResponseDto 검증 테스트
 */
class ScrapResponseDtoTest {
    @Test
    @DisplayName("ScrapResponseDto 생성 테스트")
    void testScrapResponseDto() {
        Scrap scrap = TestObjectFactory.initScrap();
        scrap.setUser(TestObjectFactory.initUser());
        scrap.setPost(TestObjectFactory.initPost());

        ScrapResponseDto scrapResponseDto = new ScrapResponseDto(scrap.getPostId());

        assertEquals(scrap.getPostId().getId(), scrapResponseDto.getId());
        assertEquals(scrap.getPostId().getTodo().getTodo(), scrapResponseDto.getTodo());
        assertEquals(scrap.getPostId().getWhat(), scrapResponseDto.getWhat());
        assertEquals(0, scrapResponseDto.getPhotoId());
    }
}