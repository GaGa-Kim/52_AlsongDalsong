package AlsongDalsong_backend.AlsongDalsong.exception;

/**
 * 컨텐츠가 존재하지 않을 때 발생하는 예외
 */
public class NotFoundException extends IllegalArgumentException {
    private static final String ERROR_MESSAGE = "존재하지 않습니다.";

    public NotFoundException() {
        super(ERROR_MESSAGE);
    }
}