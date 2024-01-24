package AlsongDalsong_backend.AlsongDalsong.domain.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * 게시글 연령 열거형 값 테스트
 */
class OldTest {
    @Test
    void ofOld_ValidOld() {
        String validOld = "10대";

        Old old = Old.ofOld(validOld);

        assertNotNull(old);
        assertEquals(Old.TEENS, old);
    }

    @Test
    void ofOld_InvalidOld() {
        String invalidOld = "80대";

        Old old = Old.ofOld(invalidOld);

        assertNotNull(old);
        assertEquals(Old.NONE, old);
    }
}