package AlsongDalsong_backend.AlsongDalsong.domain.user;

import lombok.Data;

/**
 * 카카오로부터 받은 access_token 정보를 담을 객체
 */
@Data
public class OauthToken {

    private String id_token;
    private String access_token; // 정보를 요청하기 위한 토큰
    private String token_type; // 반환된 토큰 유형
    private String refresh_token; // 새 액세스 토큰을 얻는데 사용하는 토큰
    private int expires_in; // 토큰 수명
    private String scope;
    private int refresh_token_expires_in;
}
