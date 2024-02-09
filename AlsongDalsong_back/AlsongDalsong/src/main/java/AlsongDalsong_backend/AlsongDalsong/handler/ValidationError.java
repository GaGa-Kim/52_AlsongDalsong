package AlsongDalsong_backend.AlsongDalsong.handler;

import static AlsongDalsong_backend.AlsongDalsong.handler.GlobalExceptionHandler.getParameterName;

import javax.validation.ConstraintViolation;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

/**
 * Validation 에러 처리
 */
@Getter
@Builder
@RequiredArgsConstructor
public class ValidationError {
    private final String field;
    private final String value;
    private final String message;

    public static ValidationError of(FieldError fieldError) {
        return ValidationError.builder()
                .field(fieldError.getField())
                .value(String.valueOf(fieldError.getRejectedValue()))
                .message(fieldError.getDefaultMessage())
                .build();
    }

    public static ValidationError of(ConstraintViolation violation) {
        return ValidationError.builder()
                .field(getParameterName(violation.getPropertyPath().toString()))
                .value(String.valueOf(violation.getInvalidValue()))
                .message(violation.getMessageTemplate())
                .build();
    }
}
