package AlsongDalsong_backend.AlsongDalsong.handler;

import AlsongDalsong_backend.AlsongDalsong.except.DuplicateEmailException;
import AlsongDalsong_backend.AlsongDalsong.except.NotFoundException;
import AlsongDalsong_backend.AlsongDalsong.except.UnauthorizedEditException;
import AlsongDalsong_backend.AlsongDalsong.except.WithdrawnException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception 발생 시 실패 정보 핸들러
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    // Valid Error 발생 시
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ValidationError> errors = new ArrayList<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            log.error("name : {}, message : {}", fieldError.getField(), fieldError.getDefaultMessage());
            ValidationError error = ValidationError.of(fieldError);
            errors.add(error);
        }
        ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST, errors);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Validated Error 발생 시
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        List<ValidationError> errors = new ArrayList<>();
        for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
            String paramName = getParameterName(constraintViolation.getPropertyPath().toString());
            log.error("name : {}, message : {}", paramName, constraintViolation.getMessageTemplate());
            ValidationError error = ValidationError.of(constraintViolation);
            errors.add(error);
        }
        ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST, errors);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // 지원하지 않은 HTTP Method 호출 시
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Authentication 객체가 필요한 권한을 보유하지 않을 경우
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        ErrorResponse response = new ErrorResponse(ErrorCode.ACCESS_DENIED);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // 가입되어 있는 회원 이메일일 경우
    @ExceptionHandler(DuplicateEmailException.class)
    protected ResponseEntity<ErrorResponse> handleDuplicateEmailException(DuplicateEmailException e) {
        log.error(e.getMessage());
        ErrorResponse response = new ErrorResponse(ErrorCode.DUPLICATE_EMAIL);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // 승인되지 않은 편집 시도를 할 경우
    @ExceptionHandler(UnauthorizedEditException.class)
    protected ResponseEntity<ErrorResponse> handleUnauthorizedEditException(UnauthorizedEditException e) {
        log.error(e.getMessage());
        ErrorResponse response = new ErrorResponse(ErrorCode.UNAUTHORIZED_EDIT);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // 해당 정보를 찾을 수 없는 경우
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        ErrorResponse response = new ErrorResponse(ErrorCode.RESOURCE_NOT_FOUND);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // 탈퇴한 회원에 대해 로그인할 경우
    @ExceptionHandler(WithdrawnException.class)
    protected ResponseEntity<ErrorResponse> handleWithdrawnException(WithdrawnException e) {
        log.error(e.getMessage());
        ErrorResponse response = new ErrorResponse(ErrorCode.WITHDRAWN_USER);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // 위에 해당하는 예외에 해당하지 않을 때 모든 예외를 처리하는 메소드
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage());
        ErrorResponse response = new ErrorResponse(ErrorCode.SERVER_ERROR);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    public static String getParameterName(String propertyPath) {
        String[] parts = propertyPath.split("\\.");
        return parts[parts.length - 1];
    }
}
