package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

/**
 * 회원 컨트롤러
 */
@Api(tags={"User API (회원 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private final UserService userService;
    private final AwsS3Service awsS3Service;

    /**
    // 카카오 회원가입과 로그인
    @GetMapping("/auth/kakao")
    @ApiOperation(value = "카카오 회원가입과 로그인", notes = "카카오 회원가입과 로그인 API")
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
    **/

    // 일반 회원가입
    @PostMapping("/auth/signup")
    @ApiOperation(value = "일반 회원가입", notes = "일반 회원가입 API")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        // 받아온 정보로 회원가입
        return ResponseEntity.ok().body(userService.signup(userSaveRequestDto));
    }

    // 일반 로그인
    @GetMapping("/auth/login")
    @ApiOperation(value = "일반 로그인", notes = "일반 로그인 API")
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
    @ApiOperation(value = "회원 정보", notes = "회원 정보 API")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public ResponseEntity<UserResponseDto> getUser(@RequestParam("email") String email) {
        // 받아온 정보로 회원 정보 조회
        User user = userService.getUser(email);
        String profile = null;
        // 카카오에서 받아온 이미지일 경우에는 url 그대로 출력, 아닐 경우 사진이름으로 s3에서 url 받아오기
        if(user.getProfile().startsWith("http")) {
            profile = user.getProfile();
        }
        else{
            profile = awsS3Service.getS3(user.getProfile());
        }
        return ResponseEntity.ok().body(new UserResponseDto(user, profile));
    }

    // 회원 정보 수정
    @PutMapping("/api/user/updateInfo")
    @ApiOperation(value = "회원 수정", notes = "회원 수정 API")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        // 받아온 정보로 회원 정보 수정 및 프로필 저장
        User user = userService.updateUser(userUpdateRequestDto);
        String profile = null;
        if(user.getProfile().startsWith("http")) {
            profile = user.getProfile();
        }
        else{
            profile = awsS3Service.getS3(user.getProfile());
        }
        return ResponseEntity.ok().body(new UserResponseDto(user, profile));
    }

    // 회원 프로필 사진 수정
    @PutMapping(value = "/api/user/updateProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "회원 프로필 사진 수정", notes = "회원 프로필 사진 수정 API")
    public ResponseEntity<UserResponseDto> updateProfile(@RequestParam String email, @RequestPart MultipartFile multipartFile) {
        // 받아온 정보로 회원 정보 수정 및 프로필 저장
        User user = userService.updateProfile(email, multipartFile);
        String profile = null;
        if(user.getProfile().startsWith("http")) {
            profile = user.getProfile();
        }
        else{
            profile = awsS3Service.getS3(user.getProfile());
        }
        return ResponseEntity.ok().body(new UserResponseDto(user, profile));
    }

    // 회원 탈퇴
    @PostMapping("/api/user/withdraw")
    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴 API")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public ResponseEntity<Boolean> withdrawUser(@RequestParam String email) {
        return ResponseEntity.ok().body(userService.withdrawUser(email));
    }
}
