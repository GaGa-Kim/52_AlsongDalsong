package AlsongDalsong_backend.AlsongDalsong.exception;

public class UnauthorizedPostEditException extends RuntimeException {
    private static final String ERROR_MESSAGE = "작성";

    public UnauthorizedPostEditException() {
        super(ERROR_MESSAGE);
    }
}
