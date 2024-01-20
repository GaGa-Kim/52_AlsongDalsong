package AlsongDalsong_backend.AlsongDalsong.exception;

/**
 * 작성자와 편집자가 다를 경우 발생하는 예외
 */
public class UnauthorizedEditException extends RuntimeException {
    private static final String ERROR_MESSAGE = "승인되지 않은 편집 시도입니다.";

    public UnauthorizedEditException() {
        super(ERROR_MESSAGE);
    }
}
