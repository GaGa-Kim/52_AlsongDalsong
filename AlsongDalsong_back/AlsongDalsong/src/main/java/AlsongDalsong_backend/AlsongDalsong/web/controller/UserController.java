package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.service.UserService;
import AlsongDalsong_backend.AlsongDalsong.domain.user.TokenDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserSaveRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

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
    @GetMapping("/api/me")
    @ApiOperation(value = "회원 정보", notes = "회원 정보 API")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public ResponseEntity<UserResponseDto> getUser(@RequestParam("email") String email) {
        // 받아온 정보로 회원 정보 조회
        User user = userService.getUser(email);
        return ResponseEntity.ok().body(new UserResponseDto(user));
    }
}
