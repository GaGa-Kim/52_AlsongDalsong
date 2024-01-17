package AlsongDalsong_backend.AlsongDalsong.exception;

import java.util.NoSuchElementException;

public class NotExistException extends NoSuchElementException {
    private static final String ERROR_MESSAGE = "존재하지 않습니다.";

    public NotExistException() {
        super(ERROR_MESSAGE);
    }
}