package AlsongDalsong_backend.AlsongDalsong.domain.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 게시글 결정 완료 여부 열거형 값 테스트
 */
class DecisionTest {
    @Test
    @DisplayName("올바른 게시글 결정 완료 열거형 값 테스트")
    void ofDecision_ValidDecision() {
        String validDecision = "결정";

        Decision decision = Decision.ofDecision(validDecision);

        assertNotNull(decision);
        assertEquals(Decision.DECIDED, decision);
    }

    @Test
    @DisplayName("올바르지 않은 게시글 결정 완료 열거형 값 테스트")
    void ofDecision_InvalidDecision() {
        String invalidDecision = "고민";

        Decision decision = Decision.ofDecision(invalidDecision);

        assertNotNull(decision);
        assertEquals(Decision.UNDECIDED, decision);
    }
}