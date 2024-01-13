package AlsongDalsong_backend.AlsongDalsong.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Message {
    WITHDRAW("탈퇴한 회원");

    private final String message;
}
