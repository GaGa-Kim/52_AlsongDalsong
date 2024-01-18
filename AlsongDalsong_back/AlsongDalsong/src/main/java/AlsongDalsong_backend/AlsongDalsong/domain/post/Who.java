package AlsongDalsong_backend.AlsongDalsong.domain.post;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 누가 열거형
 */
@Getter
@RequiredArgsConstructor
public enum Who {
    MAN("남성"),
    WOMAN("여성"),
    NONE("없음");

    private final String who;

    public static Who ofWho(String who) {
        return Arrays.stream(Who.values()).filter(w -> w.getWho().equals(who)).findAny().orElse(NONE);
    }
}
