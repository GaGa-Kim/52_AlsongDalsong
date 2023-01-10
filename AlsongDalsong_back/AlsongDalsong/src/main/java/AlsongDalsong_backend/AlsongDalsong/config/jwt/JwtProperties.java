package AlsongDalsong_backend.AlsongDalsong.config.jwt;

/**
 * jwt 토큰 정보
 */
public interface JwtProperties {

    String SECRET = "secret"; // 비밀키 (이후 변경)
    int EXPIRATION_TIME =  864000000; // 토큰 만료 기간
    String TOKEN_PREFIX = "Bearer "; // 토큰 앞에 붙는 형식
    String HEADER_STRING = "Authorization"; // 토큰을 넣어줄 헤더 항목
}