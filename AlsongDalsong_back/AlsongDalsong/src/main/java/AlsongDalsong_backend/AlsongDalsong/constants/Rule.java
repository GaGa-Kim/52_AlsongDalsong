package AlsongDalsong_backend.AlsongDalsong.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 애플리케이션 규칙
 */
@Getter
@RequiredArgsConstructor
public enum Rule {
    INITIAL_VALUE(0),
    STICKER_EARN_THRESHOLD(100),
    STICKER_ADD_AMOUNT(1);

    private final int rule;
}
