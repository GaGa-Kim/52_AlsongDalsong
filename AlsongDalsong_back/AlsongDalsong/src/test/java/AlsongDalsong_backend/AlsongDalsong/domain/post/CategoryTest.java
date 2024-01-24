package AlsongDalsong_backend.AlsongDalsong.domain.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * 게시글 결정 완료 여부 열거형 값 테스트
 */
class CategoryTest {
    @Test
    void ofCategory_ValidCategory() {
        String validCategory = "패션";

        Category category = Category.ofCategory(validCategory);

        assertNotNull(category);
        assertEquals(Category.FASHION, category);
    }

    @Test
    void ofCategory_InvalidCategory() {
        String invalidCategory = "게임";

        Category category = Category.ofCategory(invalidCategory);

        assertNotNull(category);
        assertEquals(Category.NONE, category);
    }
}