package AlsongDalsong_backend.AlsongDalsong.exception;

public class DuplicateEmailException extends RuntimeException {
    private static final String ERROR_MESSAGE = "승인되지 않은 게시물 수정 시도입니다.";

    public DuplicateEmailException() {
        super(ERROR_MESSAGE);
    }
}