package AlsongDalsong_backend.AlsongDalsong.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 애플리케이션 메시지
 */
@Getter
@RequiredArgsConstructor
public enum Message {
    WITHDRAW("탈퇴한 회원");

    private final String message;
}
