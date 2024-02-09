package AlsongDalsong_backend.AlsongDalsong.handler;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Error 발생 시 response
 */
@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String message;
    private List<ValidationError> validation;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.error = errorCode.getError();
        this.message = errorCode.getMessage();
    }

    public ErrorResponse(ErrorCode errorCode, List<ValidationError> errors) {
        this.status = errorCode.getStatus();
        this.error = errorCode.getError();
        this.message = errorCode.getMessage();
        this.validation = errors;
    }
}