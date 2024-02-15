package AlsongDalsong_backend.AlsongDalsong.except;

import AlsongDalsong_backend.AlsongDalsong.handler.ErrorCode;

/**
 * 작성자와 편집자가 다를 경우 발생하는 예외
 */
public class UnauthorizedEditException extends RuntimeException {
    public UnauthorizedEditException() {
        super(ErrorCode.UNAUTHORIZED_EDIT.getMessage());
    }
}
