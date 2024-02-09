package AlsongDalsong_backend.AlsongDalsong.exception;

import AlsongDalsong_backend.AlsongDalsong.handler.ErrorCode;

/**
 * 동일한 이메일로 가입된 회원 발견 시 발생 예외
 */
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL.getMessage());
    }
}