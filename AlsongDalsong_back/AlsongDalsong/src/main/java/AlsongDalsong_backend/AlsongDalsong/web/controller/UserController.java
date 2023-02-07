package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.domain.user.OauthToken;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import AlsongDalsong_backend.AlsongDalsong.service.AwsS3Service;
import AlsongDalsong_backend.AlsongDalsong.service.UserService;
import AlsongDalsong_backend.AlsongDalsong.domain.user.TokenDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserUpdateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 회원 컨트롤러
 */
@Api(tags={"User API (회원 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final AwsS3Service awsS3Service;
    private final UserRepository userRepository;
    
    // 카카오 회원가입과 로그인
    @GetMapping("/auth/kakao")
    @ApiOperation(value = "카카오 회원가입과 로그인", notes = "카카오 회원가입 또는 로그인을 한 후, jwt 토큰과 사용자 이메일을 리턴합니다.")
    @ApiImplicitParam(name = "code", value = "인가코드", example = "12345", required = true)
    public ResponseEntity<String> getKakaoLogin(@RequestParam("code") String code) {
        // 인가코드로 카카오 액세스 토큰 발급하기
        OauthToken accessToken = userService.getAccessToken(code);
        // 액세스 토큰으로 카카오 회원가입과 로그인 후 토큰 발급하기
        TokenDto tokenDto = userService.kakaoSignup(accessToken.getAccess_token());
        // 응답 헤더에 토큰 삽입
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenDto.getToken());

        return ResponseEntity.ok().headers(headers).body(tokenDto.getEmail());
    }

    // 일반 회원가입
    @PostMapping("/auth/signup")
    @ApiOperation(value = "일반 회원가입", notes = "일반 회원가입을 한 후, 가입된 사용자 정보를 리턴합니다.")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        // 받아온 정보로 회원가입
        return ResponseEntity.ok().body(userService.signup(userSaveRequestDto));
    }

    // 일반 로그인
    @GetMapping("/auth/login")
    @ApiOperation(value = "일반 로그인", notes = "일반 로그인을 한 후, jwt 토큰과 사용자 이메일을 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public ResponseEntity<String> getLogin(@RequestParam String email) {

        // 로그인 후 토큰 발급하기
        TokenDto tokenDto = userService.login(email);
        // 응답 헤더에 토큰 삽입
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenDto.getToken());

        return ResponseEntity.ok().headers(headers).body(tokenDto.getEmail());
    }

    // 회원 정보
    @GetMapping("/api/user/me")
    @ApiOperation(value = "회원 정보", notes = "회원 정보를 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public ResponseEntity<UserResponseDto> getUser(@RequestParam("email") String email) {
        // 받아온 정보로 회원 정보 조회
        User user = userService.getUser(email);
        return ResponseEntity.ok().body(new UserResponseDto(user));
    }

    // 회원 정보 수정
    @PutMapping("/api/user/updateInfo")
    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정를 수정한 후, 수정된 회원 정보를 리턴합니다.")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        // 받아온 정보로 회원 정보 수정 및 프로필 저장
        User user = userService.updateUser(userUpdateRequestDto);
        return ResponseEntity.ok().body(new UserResponseDto(user));
    }

    // 회원 프로필 사진 URL 정보 조회
    @GetMapping("/api/user/profileUrl")
    @ApiOperation(value = "회원 프로필 URL 정보 조회", notes = "회원 프로필 사진 URL 정보를 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public ResponseEntity<String> getProfileUrl(@RequestParam("email") String email) throws IOException {
        User user = userRepository.findByEmail(email);

        // 카카오에서 받아온 프로필 사진이라면
        if(user.getProfile().startsWith("http")) {
            return ResponseEntity.ok().body(user.getProfile());
        }
        // 카카오 프로필 사진이 아니라면
        else {
            return  ResponseEntity.ok().body(awsS3Service.getS3(user.getProfile()));
        }
    }


    // 회원 프로필 사진 bytearray 정보 조회
    @GetMapping("/api/user/profileByte")
    @ApiOperation(value = "회원 프로필 bytearray 정보 조회", notes = "회원 프로필 사진을 bytearray로 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public ResponseEntity<byte[]> getProfileByte(@RequestParam("email") String email, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = userRepository.findByEmail(email);

        // 카카오에서 받아온 프로필 사진이라면
        if(user.getProfile().startsWith("http")) {
            return userService.getProfileByte(email);
        }
        // 카카오 프로필 사진이 아니라면
        else {
            return awsS3Service.getObject("profile", user.getProfile());
        }
    }

    // 회원 프로필 사진 Base64 정보 조회
    @GetMapping("/api/user/profileBase")
    @ApiOperation(value = "회원 프로필 Base64 정보 조회", notes = "회원 프로필 사진을 Base64로 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public ResponseEntity<String> getProfileBase(@RequestParam("email") String email) throws IOException {
        User user = userRepository.findByEmail(email);

        // 카카오에서 받아온 프로필 사진이라면
        if (user.getProfile().startsWith("http")) {
            return userService.getProfileBase(email);
        }
        // 카카오 프로필 사진이 아니라면
        else {
            return awsS3Service.getBase("profile", user.getProfile());
        }
    }

    // 회원 프로필 사진 수정
    @PutMapping(value = "/api/user/updateProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "회원 프로필 사진 수정", notes = "회원 프로필 사진을 수정한 후 수정된 회원 정보를 리턴합니다.")
    public ResponseEntity<UserResponseDto> updateProfile(@RequestParam String email, @RequestPart MultipartFile multipartFile) {
        // 받아온 정보로 회원 정보 수정 및 프로필 저장
        User user = userService.updateProfile(email, multipartFile);
        return ResponseEntity.ok().body(new UserResponseDto(user));
    }

    // 사용자별 구매 성향 (통계)
    @GetMapping("/api/user/propensity")
    @ApiOperation(value = "사용자별 구매 성향", notes = "사용자별 구매 성향을 리턴합니다. (살까 말까 미정/결정/취소, 할까 말까 미정/결정/취소, 갈까 말까 미정/결정/취소 갯수)")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public Map<String, Object> propensity(String email) {
        return userService.propensity(email);
    }

    // 회원 탈퇴
    @PostMapping("/api/user/withdraw")
    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴를 한 후, true를 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public ResponseEntity<Boolean> withdrawUser(@RequestParam String email) {
        return ResponseEntity.ok().body(userService.withdrawUser(email));
    }
}
