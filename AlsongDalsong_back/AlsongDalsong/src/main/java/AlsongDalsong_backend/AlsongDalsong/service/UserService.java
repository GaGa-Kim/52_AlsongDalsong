package AlsongDalsong_backend.AlsongDalsong.service;

import AlsongDalsong_backend.AlsongDalsong.config.jwt.JwtProperties;
import AlsongDalsong_backend.AlsongDalsong.domain.user.TokenDto;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 회원 서비스
 */
@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final AwsS3Service awsS3Service;

    /**
    // 인가코드로 카카오 access_token 발급하기
    @Transactional
    public OauthToken getAccessToken(String code) {
        // Http 통신을 위해 생성
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader 객체 생성
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "{클라이언트 앱 키}");
        params.add("redirect_uri", "{리다이렉트 uri}");
        params.add("code", code);
        params.add("client_secret", "{시크릿 키}");

        // HttpEntity 객체 생성 후 HttpHeader와 HttpBody 정보 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // POST 방식으로 카카오와 통신 후 Json 형식으로 응답 받기
        ResponseEntity<String> accessTokenResponse = restTemplate.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, String.class);

        // 응답을 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oauthToken;
    }

    // 액세스 토큰으로 카카오 사용자 정보 가져오기
     @Transactional
     public KakaoProfile findProfile(String accessToken) {
        // Http 통신을 위해 생성
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader 객체 생성 후 액세스 토큰 정보 담기
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpEntity 객체 생성 후 HttpHeader 정보 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        // POST 방식으로 카카오와 통신 후 Json 형식으로 응답 받기
        ResponseEntity<String> kakaoProfileResponse = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest, String.class);

        // 응답을 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;
    }

    // 카카오 회원가입과 로그인
     @Transactional
     public TokenDto kakaoSignup(String access_token) {
        KakaoProfile profile = findProfile(access_token);

        // 사용자의 카카오 이메일로 이미 가입한 회원이 아니라면, 카카오 정보를 가지고 회원가입과 로그인
        User user = userRepository.findByEmail(profile.getKakao_account().getEmail());
        if(user == null) {
            user = User.builder()
                    .kakaoId(profile.getId())
                    .name(profile.getKakao_account().getEmail().substring(0, profile.getKakao_account().getEmail().indexOf("@")))
                    .email(profile.getKakao_account().getEmail())
                    .nickname(profile.getKakao_account().getProfile().getNickname())
                    .profileUrl(profile.getKakao_account().getProfile().getProfile_image_url())
                    .introduce("")
                    .role("ROLE_USER")
                    .point(0)
                    .withdraw(false)
                    .build();
            userRepository.save(user);
        }

        // 탈퇴한 회원이 아닐 경우 토큰 발급
         if (!user.getNickname().equals("탈퇴한 회원")) {
             return createToken(user);
         }
         else {
             throw new RuntimeException("로그인에 실패했습니다. 탈퇴한 회원입니다.");
         }
     }
     **/

    // 일반 회원가입
    @Transactional
    public UserResponseDto signup(UserSaveRequestDto userSaveRequestDto) {
        // 이메일로 이미 가입한 회원이 아니라면, 받은 정보를 가지고 회원가입
        if (userRepository.findByEmail(userSaveRequestDto.getEmail()) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        User user = userRepository.save(userSaveRequestDto.toEntity());
        return new UserResponseDto(user, user.getProfile());
    }

    // 일반 로그인
    @Transactional
    public TokenDto login(String email) {
        // 이메일로 회원 정보 찾기
        User user = userRepository.findByEmail(email);

        // 탈퇴한 회원인지 확인
        if (!user.getNickname().equals("탈퇴한 회원")) {
            return createToken(user);
        }
        else {
            throw new RuntimeException("로그인에 실패했습니다. 탈퇴한 회원입니다.");
        }
    }

    // jwt 토큰 생성
    @Transactional
    public TokenDto createToken(User user) {
        String jwtToken = JWT.create()
                .withSubject(user.getName())
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))
                .withClaim("id", user.getId()) // 아이디로 사용자 식별
                .withClaim("role", user.getRole())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return new TokenDto(jwtToken, user.getEmail());
    }

    // 회원 정보
    @Transactional(readOnly = true)
    public User getUser(String email) {
        // 이메일로 회원 정보 가져오기
        User user = userRepository.findByEmail(email);
        return user;
    }
    
    // 회원 정보 수정
    @Transactional
    public User updateUser(UserUpdateRequestDto userUpdateRequestDto) {

        // 이메일로 회원 정보 가져오기
        User user = userRepository.findByEmail(userUpdateRequestDto.getEmail());
        user.update(userUpdateRequestDto.getNickname(),
                userUpdateRequestDto.getIntroduce());
        return user;
    }

    // 회원 프로필 사진 수정
    @Transactional
    public User updateProfile(String email, MultipartFile multipartFile) {

        // 이메일로 회원 정보 가져오기
        User user = userRepository.findByEmail(email);
        awsS3Service.deleteS3(user.getProfile());
        // 프로필 저장
        String profile = awsS3Service.uploadProfile(multipartFile);
        // 회원 프로필 사진 수정
        user.updateProfile(profile);

        return user;
    }

    // 회원 탈퇴
    @Transactional
    public Boolean withdrawUser(String email) {
        User user = userRepository.findByEmail(email);

        if(user.getEmail().equals(email)) {
            user.setWithdraw();
            return true;
        }
        else {
            throw new RuntimeException("회원 탈퇴에 실패했습니다.");
        }
    }
}
