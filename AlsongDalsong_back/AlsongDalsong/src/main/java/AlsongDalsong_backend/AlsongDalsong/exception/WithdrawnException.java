package AlsongDalsong_backend.AlsongDalsong.exception;

/**
 * 탈퇴한 회원에 대해 로그인할 경우 발생하는 예외
 */
public class WithdrawnException extends RuntimeException {
    private static final String ERROR_MESSAGE = "로그인에 실패했습니다. 탈퇴한 회원입니다.";

    public WithdrawnException() {
        super(ERROR_MESSAGE);
    }
}
