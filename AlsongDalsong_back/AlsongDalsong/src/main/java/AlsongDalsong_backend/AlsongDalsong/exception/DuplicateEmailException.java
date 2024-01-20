package AlsongDalsong_backend.AlsongDalsong.exception;

/**
 * 동일한 이메일로 가입된 회원 발견 시 발생 예외
 */
public class DuplicateEmailException extends RuntimeException {
    private static final String ERROR_MESSAGE = "이미 가입되어 있는 회원입니다.";

    public DuplicateEmailException() {
        super(ERROR_MESSAGE);
    }
}