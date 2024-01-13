package AlsongDalsong_backend.AlsongDalsong.exception;

public class DuplicateEmailException extends RuntimeException {
    private static final String ERROR_MESSAGE = "이미 가입되어 있는 유저입니다.";

    public DuplicateEmailException() {
        super(ERROR_MESSAGE);
    }
}