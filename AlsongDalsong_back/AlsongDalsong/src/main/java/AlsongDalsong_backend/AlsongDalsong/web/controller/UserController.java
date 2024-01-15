package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.service.photo.AwsS3ServiceImpl;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserUpdateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 회원 컨트롤러
 */
@Api(tags = {"User API (회원 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private static final String HTTP_URL = "http";

    private final UserService userService; // 회원 서비스
    private final AwsS3ServiceImpl awsS3ServiceImpl; // 스토리지 서비스

    // 회원 정보
    @GetMapping("/api/user/me")
    @ApiOperation(value = "회원 정보", notes = "회원 정보를 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public ResponseEntity<UserResponseDto> userDetails(@RequestParam("email") String email) {
        User user = userService.findUserByEmail(email);
        return ResponseEntity.ok().body(new UserResponseDto(user));
    }

    // 회원 정보 수정
    @PutMapping("/api/user/updateInfo")
    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정를 수정한 후, 수정된 회원 정보를 리턴합니다.")
    @ApiImplicitParam(name = "userUpdateRequestDto", value = "회원 수정 정보", required = true)
    public ResponseEntity<UserResponseDto> userProfileModify(
            @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        User user = userService.modifyUserProfile(userUpdateRequestDto);
        return ResponseEntity.ok().body(new UserResponseDto(user));
    }

    // 회원 프로필 사진 URL 정보 조회
    @GetMapping("/api/user/profileUrl")
    @ApiOperation(value = "회원 프로필 URL 정보 조회", notes = "회원 프로필 사진 URL 정보를 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public ResponseEntity<String> userProfileImageAsUrl(@RequestParam("email") String email) throws IOException {
        User user = userService.findUserByEmail(email);
        if (isKakaoProfile(user)) {
            return ResponseEntity.ok().body(user.getProfile());
        }
        return ResponseEntity.ok().body(awsS3ServiceImpl.getS3(user.getProfile()));
    }

    // 회원 프로필 사진 bytearray 정보 조회
    @GetMapping("/api/user/profileByte")
    @ApiOperation(value = "회원 프로필 bytearray 정보 조회", notes = "회원 프로필 사진을 bytearray로 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public ResponseEntity<byte[]> userProfileImageAsBytes(@RequestParam("email") String email) throws IOException {
        User user = userService.findUserByEmail(email);
        if (isKakaoProfile(user)) {
            return userService.findUserProfileImageAsBytes(email);
        }
        return awsS3ServiceImpl.getObject("profile", user.getProfile());
    }

    // 회원 프로필 사진 Base64 정보 조회
    @GetMapping("/api/user/profileBase")
    @ApiOperation(value = "회원 프로필 Base64 정보 조회", notes = "회원 프로필 사진을 Base64로 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public ResponseEntity<String> userProfileImageAsBase64(@RequestParam("email") String email) throws IOException {
        User user = userService.findUserByEmail(email);
        if (isKakaoProfile(user)) {
            return userService.findUserProfileImageAsBase64(email);
        }
        return awsS3ServiceImpl.getBase("profile", user.getProfile());
    }

    // 회원 프로필 사진 수정
    @PutMapping(value = "/api/user/updateProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "회원 프로필 사진 수정", notes = "회원 프로필 사진을 수정한 후 수정된 회원 정보를 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public ResponseEntity<UserResponseDto> userProfileImageModify(@RequestParam String email,
                                                                  @RequestPart MultipartFile multipartFile) {
        User user = userService.modifyUserProfileImage(email, multipartFile);
        return ResponseEntity.ok().body(new UserResponseDto(user));
    }

    // 사용자별 구매 성향 (통계)
    @GetMapping("/api/user/propensity")
    @ApiOperation(value = "사용자별 구매 성향", notes = "사용자별 구매 성향을 리턴합니다. (살까 말까 미정/결정/취소, 할까 말까 미정/결정/취소, 갈까 말까 미정/결정/취소 갯수)")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public Map<String, Object> userPropensityDetails(String email) {
        return userService.findUserDecisionPropensity(email);
    }

    // 회원 탈퇴
    @PostMapping("/api/user/withdraw")
    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴를 한 후, true를 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com", required = true)
    public ResponseEntity<Boolean> userWithdraw(@RequestParam String email) {
        return ResponseEntity.ok().body(userService.withdrawUserAccount(email));
    }

    // 카카오 프로필 사진 URL인지 확인
    private boolean isKakaoProfile(User user) {
        return isStartHttpString(user.getProfile());
    }

    // URL이 http로 시작하는지 확인
    private boolean isStartHttpString(String url) {
        return url.startsWith(HTTP_URL);
    }
}
