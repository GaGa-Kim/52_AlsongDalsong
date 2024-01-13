package AlsongDalsong_backend.AlsongDalsong.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Rule {
    INITIAL_VALUE(0),
    STICKER_EARN_THRESHOLD(100),
    STICKER_ADD_AMOUNT(1);

    private final int rule;
}
