package AlsongDalsong_backend.AlsongDalsong.service;

import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.*;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 회원 서비스
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AwsS3Service awsS3Service;

    @Value("${jwt.secret}")
    public String secret;

    @Value("${jwt.expiration_time}")
    public int expiration_time;

    @Value("${kakao.client_id}")
    public String client_id;

    @Value("${kakao.redirect_uri}")
    public String redirect_uri;

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
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_uri);
        params.add("code", code);

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
                    .profile(profile.getKakao_account().getProfile().getProfile_image_url())
                    .introduce("")
                    .role("ROLE_USER")
                    .point(0)
                    .sticker(0)
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

    // 일반 회원가입
    @Transactional
    public UserResponseDto signup(UserSaveRequestDto userSaveRequestDto) {
        // 이메일로 이미 가입한 회원이 아니라면, 받은 정보를 가지고 회원가입
        if (userRepository.findByEmail(userSaveRequestDto.getEmail()) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        User user = userRepository.save(userSaveRequestDto.toEntity());
        return new UserResponseDto(user);
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
                .withExpiresAt(new Date(System.currentTimeMillis()+ expiration_time))
                .withClaim("id", user.getId()) // 아이디로 사용자 식별
                .withClaim("role", user.getRole())
                .sign(Algorithm.HMAC512(secret));

        return new TokenDto(jwtToken, user.getEmail());
    }

    // 회원 정보
    @Transactional(readOnly = true)
    public User getUser(String email) {
        // 이메일로 회원 정보 가져오기
        User user = userRepository.findByEmail(email);
        return user;
    }

    // 회원 프로필 사진 Bytearray
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> getProfileByte(String email) throws IOException {
        User user = userRepository.findByEmail(email);

        URL url = new URL(user.getProfile());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        byte[] bytes = IOUtils.toByteArray(inputStream);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", "profile");

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    // 회원 프로필 사진 Base64
    @Transactional(readOnly = true)
    public ResponseEntity<String> getProfileBase(String email) throws IOException {
        User user = userRepository.findByEmail(email);

        URL url = new URL(user.getProfile());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        byte[] bytes = IOUtils.toByteArray(inputStream);
        String encodedString = Base64.getEncoder().encodeToString(bytes);

        return new ResponseEntity<>(encodedString, HttpStatus.OK);
    }

    // 회원 정보 수정
    @Transactional
    public User updateUser(UserUpdateRequestDto userUpdateRequestDto) {
        // 이메일로 회원 정보 가져오기
        User user = userRepository.findByEmail(userUpdateRequestDto.getEmail());

        // 회원과 회원 수정하는 자가 같을 경우에만 수정 가능
        if(user.getEmail().equals(user.getEmail())) {
            user.update(userUpdateRequestDto.getNickname(),
                    userUpdateRequestDto.getIntroduce());

            return user;
        }
        else {
            throw new RuntimeException("회원 정보 수정에 실패했습니다.");
        }
    }

    // 회원 프로필 사진 수정
    @Transactional
    public User updateProfile(String email, MultipartFile multipartFile) {
        // 이메일로 회원 정보 가져오기
        User user = userRepository.findByEmail(email);
        // 이전 프로필 삭제
        awsS3Service.deleteS3(user.getProfile());
        // 프로필 저장
        String profile = awsS3Service.uploadProfile(multipartFile);
        // 회원 프로필 사진 수정
        user.updateProfile(profile);

        return user;
    }

    // 사용자별 구매 성향 (통계)
    @Transactional(readOnly = true)
    public Map<String, Object> propensity(String email) {
        User user = userRepository.findByEmail(email);

        Map<String, Object> propensityMap = new HashMap<>();
        propensityMap.put("살까 말까 미정", postRepository.countByUserIdAndTodoAndDecision(user, "살까 말까", "미정"));
        propensityMap.put("살까 말까 결정", postRepository.countByUserIdAndTodoAndDecision(user, "살까 말까", "결정"));
        propensityMap.put("살까 말까 취소", postRepository.countByUserIdAndTodoAndDecision(user, "살까 말까", "취소"));

        propensityMap.put("할까 말까 미정", postRepository.countByUserIdAndTodoAndDecision(user, "할까 말까", "미정"));
        propensityMap.put("할까 말까 결정", postRepository.countByUserIdAndTodoAndDecision(user, "할까 말까", "결정"));
        propensityMap.put("할까 말까 취소", postRepository.countByUserIdAndTodoAndDecision(user, "할까 말까", "취소"));

        propensityMap.put("갈까 말까 미정", postRepository.countByUserIdAndTodoAndDecision(user, "갈까 말까", "미정"));
        propensityMap.put("갈까 말까 결정", postRepository.countByUserIdAndTodoAndDecision(user, "갈까 말까", "결정"));
        propensityMap.put("갈까 말까 취소", postRepository.countByUserIdAndTodoAndDecision(user, "갈까 말까", "취소"));

        return propensityMap;
    }

    // 회원 탈퇴
    @Transactional
    public Boolean withdrawUser(String email) {
        User user = userRepository.findByEmail(email);

        // 회원과 탈퇴하는 자가 같을 경우에만 수정 가능
        if(user.getEmail().equals(email)) {
            user.setWithdraw();
            return true;
        }
        else {
            throw new RuntimeException("회원 탈퇴에 실패했습니다.");
        }
    }
}
