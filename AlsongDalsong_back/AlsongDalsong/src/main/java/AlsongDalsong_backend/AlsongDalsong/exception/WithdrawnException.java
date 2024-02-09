package AlsongDalsong_backend.AlsongDalsong.exception;

import AlsongDalsong_backend.AlsongDalsong.handler.ErrorCode;

/**
 * 탈퇴한 회원에 대해 로그인할 경우 발생하는 예외
 */
public class WithdrawnException extends RuntimeException {
    public WithdrawnException() {
        super(ErrorCode.WITHDRAWN_USER.getMessage());
    }
}
