package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.domain.user.OauthToken;
import AlsongDalsong_backend.AlsongDalsong.domain.user.TokenDto;
import AlsongDalsong_backend.AlsongDalsong.service.AuthService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserSaveRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 인증 컨트롤러
 */
@Api(tags = {"Auth API (인증 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final AuthService authService;

    // 카카오 회원가입과 로그인 및 JWT 토큰 발급
    @GetMapping("/auth/kakao")
    @ApiOperation(value = "카카오 회원가입과 로그인", notes = "카카오 회원가입 또는 로그인을 한 후, jwt 토큰과 사용자 이메일을 리턴합니다.")
    @ApiImplicitParam(name = "code", value = "인가코드", example = "12345", required = true)
    public ResponseEntity<String> kakaoLogin(@RequestParam("code") String code) {
        OauthToken accessToken = authService.getAccessToken(code);
        TokenDto tokenDto = authService.kakaoSignup(accessToken.getAccess_token());
        return ResponseEntity.ok().headers(createHeader(tokenDto)).body(tokenDto.getEmail());
    }

    // 응답 헤더 생성 및 JWT 토큰 삽입
    private HttpHeaders createHeader(TokenDto tokenDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_AUTHORIZATION, BEARER_PREFIX + tokenDto.getToken());
        return headers;
    }

    // 일반 회원가입
    @PostMapping("/auth/signup")
    @ApiOperation(value = "일반 회원가입", notes = "일반 회원가입을 한 후, 가입된 사용자 정보를 리턴합니다.")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        return ResponseEntity.ok().body(authService.signup(userSaveRequestDto));
    }

    // 일반 로그인 및 JWT 토큰 발급
    @GetMapping("/auth/login")
    @ApiOperation(value = "일반 로그인", notes = "일반 로그인을 한 후, jwt 토큰과 사용자 이메일을 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public ResponseEntity<String> login(@RequestParam String email) {
        TokenDto tokenDto = authService.login(email);
        return ResponseEntity.ok().headers(createHeader(tokenDto)).body(tokenDto.getEmail());
    }
}
