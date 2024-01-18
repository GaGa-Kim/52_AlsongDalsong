package AlsongDalsong_backend.AlsongDalsong.domain.post;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 연령 열거형
 */
@Getter
@RequiredArgsConstructor
public enum Old {
    TEENS("10대"),
    TWENTIES("20대"),
    THIRTIES("30대"),
    FORTIES("40대"),
    NONE("없음");

    private final String old;

    public static Old ofOld(String old) {
        return Arrays.stream(Old.values()).filter(o -> o.getOld().equals(old)).findAny().orElse(NONE);
    }
}
