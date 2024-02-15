package AlsongDalsong_backend.AlsongDalsong.domain.post;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 결정 완료 여부 열거형
 */
@Getter
@RequiredArgsConstructor
public enum Decision {
    UNDECIDED("미정"),
    DECIDED("결정"),
    CANCELED("취소");

    private final String decision;

    public static Decision ofDecision(String decision) {
        return Arrays.stream(Decision.values())
                .filter(d -> d.getDecision().equals(decision)).findAny().orElse(UNDECIDED);
    }
}
