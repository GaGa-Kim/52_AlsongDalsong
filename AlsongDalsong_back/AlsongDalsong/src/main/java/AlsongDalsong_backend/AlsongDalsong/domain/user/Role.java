package AlsongDalsong_backend.AlsongDalsong.domain.user;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회원 역할 (레벨, 권한) 열거형 값
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    USER("일반 사용자"),
    MANAGER("관리자");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public static Role ofRole(String role) {
        return Arrays.stream(Role.values()).filter(r -> r.getRole().equals(role)).findAny().orElse(null);
    }
}
