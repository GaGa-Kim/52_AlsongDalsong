package AlsongDalsong_backend.AlsongDalsong.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 회원 역할 열거형 값 테스트
 */
class RoleTest {
    @Test
    @DisplayName("올바른 회원 역할 열거형 값 테스트")
    void ofRole_ValidRole() {
        String validRole = "일반 사용자";

        Role role = Role.ofRole(validRole);

        assertNotNull(role);
        assertEquals(Role.USER, role);
    }

    @Test
    @DisplayName("올바르지 않은 회원 역할 열거형 값 테스트")
    void ofRole_InvalidRole() {
        String invalidRole = "역할 없음";

        Role role = Role.ofRole(invalidRole);

        assertNull(role);
    }
}