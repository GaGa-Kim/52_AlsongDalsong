package AlsongDalsong_backend.AlsongDalsong.exception;

public class NotFoundException extends IllegalArgumentException {
    private static final String ERROR_MESSAGE = "존재하지 않습니다.";

    public NotFoundException() {
        super(ERROR_MESSAGE);
    }
}