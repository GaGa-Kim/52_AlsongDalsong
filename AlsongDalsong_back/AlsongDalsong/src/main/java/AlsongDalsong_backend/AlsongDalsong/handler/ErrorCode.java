package AlsongDalsong_backend.AlsongDalsong.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 에러 처리를 위한 에러 코드
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 400 BAD_REQUEST : 잘못된 요청
    BAD_REQUEST(400, "Bad Request", "잘못된 요청입니다."),

    // 401 UNAUTHORIZED : 인증되지 않은 사용자
    UNAUTHENTICATED_USERS(401, "Unauthorized", "인증이 필요합니다."),

    // 403 FORBIDDEN : 접근권한 없음
    ACCESS_DENIED(403, "Forbidden", "접근이 거부되었습니다."),

    // 405 METHOD_NOT_ALLOWED : 지원하지 않는 HTTP Method
    METHOD_NOT_ALLOWED(405, "Method Not Allowed", "허용되지 않은 요청입니다."),

    // 404 NOT_FOUND : Resource를 찾을 수 없음
    RESOURCE_NOT_FOUND(404, "Not Found", "해당 정보를 찾을 수 없습니다."),

    // 500 INTERNAL_SERVER_ERROR : 서버 오류
    WITHDRAWN_USER(500, "Withdrawn User Error", "로그인에 실패했습니다. 탈퇴한 회원입니다."),
    DUPLICATE_EMAIL(500, "Duplicate Email Error", "이미 가입되어 있는 회원 이메일입니다."),
    UNAUTHORIZED_EDIT(500, "Unauthorized Edit Error", "승인되지 않은 편집 시도입니다."),

    SERVER_ERROR(500, "Internal Server Error", "예기치 못한 오류가 발생하였습니다.");

    private int status;
    private final String error;
    private final String message;
}