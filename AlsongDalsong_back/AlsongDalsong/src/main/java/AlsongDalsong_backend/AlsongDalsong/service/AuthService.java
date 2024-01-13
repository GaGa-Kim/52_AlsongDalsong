package AlsongDalsong_backend.AlsongDalsong.service;

import AlsongDalsong_backend.AlsongDalsong.domain.user.KakaoProfile;
import AlsongDalsong_backend.AlsongDalsong.domain.user.KakaoProfile.KakaoAccount;
import AlsongDalsong_backend.AlsongDalsong.domain.user.OauthToken;
import AlsongDalsong_backend.AlsongDalsong.domain.user.TokenDto;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import AlsongDalsong_backend.AlsongDalsong.exception.DuplicateEmailException;
import AlsongDalsong_backend.AlsongDalsong.exception.WithdrawnException;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserSaveRequestDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 회원 가입 및 인증 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    public String secret;

    @Value("${jwt.expiration_time}")
    public int expiration_time;

    @Value("${kakao.client_id}")
    public String client_id;

    @Value("${kakao.redirect_uri}")
    public String redirect_uri;

    // 인가코드로 카카오 액세스 토큰 발급하기
    public OauthToken getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(createParam(code), createTokenHeader());
        ResponseEntity<OauthToken> accessTokenResponse = restTemplate.exchange(
                KAKAO_TOKEN_URL,
                HttpMethod.POST,
                request,
                OauthToken.class);
        return accessTokenResponse.getBody();
    }

    // 액세스 토큰 획득을 위한 HttpHeader 객체 생성
    private HttpHeaders createTokenHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    // HttpBody 객체 생성
    private MultiValueMap<String, String> createParam(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_uri);
        params.add("code", code);
        return params;
    }

    // 액세스 토큰으로 카카오 사용자 정보 가져오기
    public KakaoProfile findProfile(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> profileRequest = new HttpEntity<>(createProfileHeader(accessToken));
        ResponseEntity<KakaoProfile> kakaoProfileResponse = restTemplate.exchange(
                KAKAO_INFO_URL,
                HttpMethod.POST,
                profileRequest,
                KakaoProfile.class);
        return kakaoProfileResponse.getBody();
    }

    // 사용자 정보 획득을 위한 HttpHeader 객체 생성
    private HttpHeaders createProfileHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_AUTHORIZATION, BEARER_PREFIX + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    // 카카오 회원가입과 로그인
    public TokenDto kakaoSignup(String access_token) {
        KakaoProfile profile = findProfile(access_token);
        User user = findOrRegisterUser(profile);
        if (isWithdrawn(user)) {
            throw new WithdrawnException();
        }
        return createToken(user);
    }

    // 카카오 회원가입을 위한 회원 생성 또는 회원 조회
    private User findOrRegisterUser(KakaoProfile profile) {
        String email = profile.getKakao_account().getEmail();
        if (doesUserExistWithEmail(email)) {
            return findUserByEmail(email);
        }
        User user = createUserFromKakao(profile);
        userRepository.save(user);
        return user;
    }

    // 카카오 회원가입을 위한 회원 생성
    private User createUserFromKakao(KakaoProfile profile) {
        KakaoAccount account = profile.getKakao_account();
        return User.builder()
                .kakaoId(profile.getId())
                .name(account.getEmail().substring(0, account.getEmail().indexOf("@")))
                .email(account.getEmail())
                .nickname(account.getProfile().getNickname())
                .profile(account.getProfile().getProfile_image_url())
                .build();
    }

    // 일반 회원가입
    public UserResponseDto signup(UserSaveRequestDto userSaveRequestDto) {
        if (doesUserExistWithEmail(userSaveRequestDto.getEmail())) {
            throw new DuplicateEmailException();
        }
        User user = userRepository.save(userSaveRequestDto.toEntity());
        return new UserResponseDto(user);
    }

    // 일반 로그인
    public TokenDto login(String email) {
        User user = findUserByEmail(email);
        if (isWithdrawn(user)) {
            throw new WithdrawnException();
        }
        return createToken(user);
    }

    // jwt 토큰 생성
    public TokenDto createToken(User user) {
        String jwtToken = JWT.create()
                .withSubject(user.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration_time))
                .withClaim("id", user.getId()) // 아이디로 사용자 식별
                .withClaim("role", user.getRole().getRole())
                .sign(Algorithm.HMAC512(secret));
        return new TokenDto(jwtToken, user.getEmail());
    }

    // 이메일로 회원 가입 여부 확인
    private boolean doesUserExistWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // 이메일로 회원 조회
    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 회원 탈퇴 여부
    private boolean isWithdrawn(User user) {
        return user.getWithdraw();
    }
}
