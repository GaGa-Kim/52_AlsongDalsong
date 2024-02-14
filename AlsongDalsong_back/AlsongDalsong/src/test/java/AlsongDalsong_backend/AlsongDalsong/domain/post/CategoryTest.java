package AlsongDalsong_backend.AlsongDalsong.domain.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 게시글 카테고리 열거형 값 테스트
 */
class CategoryTest {
    @Test
    @DisplayName("올바른 게시글 카테고리 열거형 값 테스트")
    void ofCategory_ValidCategory() {
        String validCategory = "패션";

        Category category = Category.ofCategory(validCategory);

        assertNotNull(category);
        assertEquals(Category.FASHION, category);
    }

    @Test
    @DisplayName("올바르지 않은 게시글 카테고리 열거형 값 테스트")
    void ofCategory_InvalidCategory() {
        String invalidCategory = "게임";

        Category category = Category.ofCategory(invalidCategory);

        assertNotNull(category);
        assertEquals(Category.NONE, category);
    }
}