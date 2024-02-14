package AlsongDalsong_backend.AlsongDalsong.domain.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 게시글 누가(성별) 열거형 값 테스트
 */
class WhoTest {
    @Test
    @DisplayName("올바른 게시글 누가 열거형 값 테스트")
    void ofWho_ValidWho() {
        String validWho = "여성";

        Who who = Who.ofWho(validWho);

        assertNotNull(who);
        assertEquals(Who.WOMAN, who);
    }

    @Test
    @DisplayName("올바르지 않은 게시글 누가 열거형 값 테스트")
    void ofWho_InvalidWho() {
        String invalidWho = "아이";

        Who who = Who.ofWho(invalidWho);

        assertNotNull(who);
        assertEquals(Who.NONE, who);
    }
}