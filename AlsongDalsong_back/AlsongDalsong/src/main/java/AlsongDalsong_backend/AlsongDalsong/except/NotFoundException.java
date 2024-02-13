package AlsongDalsong_backend.AlsongDalsong.except;

import AlsongDalsong_backend.AlsongDalsong.handler.ErrorCode;

/**
 * 컨텐츠가 존재하지 않을 때 발생하는 예외
 */
public class NotFoundException extends IllegalArgumentException {
    public NotFoundException() {
        super(ErrorCode.RESOURCE_NOT_FOUND.getMessage());
    }
}