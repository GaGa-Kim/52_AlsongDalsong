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
 * 회원가입 및 인증을 위한 비즈니스 로직 구현 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token"; // 카카오 액세스 토큰 발급 URL
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me"; // 카카오 사용자 정보 조회 URL
    private static final String AUTHORIZATION_HEADER = "Authorization"; // JWT 인증 헤더
    private static final String BEARER_PREFIX = "Bearer "; // JWT 토큰 인증 타입

    private final UserRepository userRepository; // 회원 레포지토리

    @Value("${jwt.secret}")
    public String secret;

    @Value("${jwt.expiration_time}")
    public int expiration_time;

    @Value("${kakao.client_id}")
    public String client_id;

    @Value("${kakao.redirect_uri}")
    public String redirect_uri;

    /**
     * 카카오 회원가입 또는 로그인을 진행한 후, JWT 토큰을 생성한다.
     *
     * @param code (카카오에서 발급된 인가코드)
     * @return TokenDto (토큰과 회원 정보 DTO)
     * @throws WithdrawnException (회원이 탈퇴한 경우 발생하는 예외)
     */
    @Override
    public TokenDto kakaoSignupOrLogin(String code) {
        OauthToken access_token = getAccessToken(code);
        KakaoProfile profile = getKakaoProfile(access_token.getAccess_token());
        User user = findOrSaveUser(profile);
        if (isWithdrawn(user)) {
            throw new WithdrawnException();
        }
        return createJwtToken(user);
    }

    /**
     * 일반 회원가입을 진행한 후, 사용자 정보를 생성한다.
     *
     * @param userSaveRequestDto (회원 저장 정보 DTO)
     * @return UserResponseDto (회원가입된 회원 정보 DTO)
     * @throws DuplicateEmailException (동일한 이메일의 회원이 있을 경우 발생하는 예외)
     */
    @Override
    public UserResponseDto regularSignup(UserSaveRequestDto userSaveRequestDto) {
        if (doesUserExistWithEmail(userSaveRequestDto.getEmail())) {
            throw new DuplicateEmailException();
        }
        User user = userRepository.save(userSaveRequestDto.toEntity());
        return new UserResponseDto(user);
    }

    /**
     * 일반 로그인을 진행한 후, JWT 토큰을 생성한다.
     *
     * @param email (회원 이메일)
     * @return TokenDto (토큰과 회원 정보 DTO)
     * @throws WithdrawnException (회원이 탈퇴한 경우 발생하는 예외)
     */
    @Override
    public TokenDto regularLogin(String email) {
        User user = findUserByEmail(email);
        if (isWithdrawn(user)) {
            throw new WithdrawnException();
        }
        return createJwtToken(user);
    }

    /**
     * 카카오 회원가입을 위해 인가코드를 사용해 액세스 토큰을 발급한다.
     *
     * @param code (카카오에서 발급된 인가코드)
     * @return OauthToken (카카오로부터 받은 액세스 토큰 데이터)
     */
    private OauthToken getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(createParam(code), createTokenHeader());
        ResponseEntity<OauthToken> accessTokenResponse = restTemplate.exchange(
                KAKAO_TOKEN_URL,
                HttpMethod.POST,
                request,
                OauthToken.class);
        return accessTokenResponse.getBody();
    }

    /**
     * 액세스 토큰 발급 통신을 위한 HttpBody 객체를 생성한다.
     *
     * @param code (카카오에서 발급된 인가코드)
     * @return MultiValueMap<String, String> (HTTP 요청의 파라미터)
     */
    private MultiValueMap<String, String> createParam(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_uri);
        params.add("code", code);
        return params;
    }

    /**
     * 액세스 토큰 발급을 위한 HttpHeader 객체를 생성한다.
     *
     * @return HttpHeaders (HTTP 통신을 위한 헤더)
     */
    private HttpHeaders createTokenHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    /**
     * 액세스 토큰으로 카카오 사용자 정보를 가져온다.
     *
     * @param accessToken (카카오에서 발급된 액세스 토큰)
     * @return KakaoProfile (카카오로부터 받은 사용자 정보)
     */
    private KakaoProfile getKakaoProfile(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> profileRequest = new HttpEntity<>(createProfileHeader(accessToken));
        ResponseEntity<KakaoProfile> kakaoProfileResponse = restTemplate.exchange(
                KAKAO_USER_INFO_URL,
                HttpMethod.POST,
                profileRequest,
                KakaoProfile.class);
        return kakaoProfileResponse.getBody();
    }

    /**
     * 사용자 정보 획득을 위한 HttpHeader 객체를 생성한다.
     *
     * @param accessToken (카카오에서 발급된 액세스 토큰)
     * @return HttpHeaders (HTTP 통신을 위한 헤더)
     */
    private HttpHeaders createProfileHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, BEARER_PREFIX + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    /**
     * 카카오 사용자 정보의 이메일로 이미 가입이 되어 있다면 회원 정보를 가져오고, 그렇지 않다면 회원을 생성한다.
     *
     * @param profile (카카오로부터 받은 사용자 정보)
     * @return User (회원가입된 회원 또는 로그인된 회원)
     */
    private User findOrSaveUser(KakaoProfile profile) {
        String email = profile.getKakao_account().getEmail();
        if (doesUserExistWithEmail(email)) {
            return findUserByEmail(email);
        }
        User user = createUserFromKakao(profile);
        userRepository.save(user);
        return user;
    }

    /**
     * 카카오 회원가입을 위한 회원을 생성한다.
     *
     * @param profile (카카오로부터 받은 사용자 정보)
     * @return User (회원가입될 회원)
     */
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

    /**
     * 회원 탈퇴 여부를 조회한다.
     *
     * @param user (회원)
     * @return boolean (회원 가입 여부)
     */
    private boolean isWithdrawn(User user) {
        return user.getWithdraw();
    }

    /**
     * 동일한 이메일의 회원이 이미 존재하는지 확인한다.
     *
     * @param email (회원 이메일)
     * @return boolean (이메일 중복 여부)
     */
    private boolean doesUserExistWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * 이메일로 회원을 조회한다.
     *
     * @param email (회원 이메일)
     * @return User (이메일로 조회한 회원)
     */
    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * JWT 토큰을 생성한다.
     *
     * @param user (회원)
     * @return TokenDto (토큰과 회원 정보 DTO)
     */
    private TokenDto createJwtToken(User user) {
        String jwtToken = JWT.create()
                .withSubject(user.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration_time))
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole().getRole())
                .sign(Algorithm.HMAC512(secret));
        return new TokenDto(jwtToken, user.getEmail());
    }
}