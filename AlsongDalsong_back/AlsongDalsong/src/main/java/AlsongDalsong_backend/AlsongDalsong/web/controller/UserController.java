package AlsongDalsong_backend.AlsongDalsong.web.controller;

import static AlsongDalsong_backend.AlsongDalsong.constants.Message.INPUT_EMAIL;

import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.service.photo.StorageService;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserUpdateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 회원 컨트롤러
 */
@Api(tags = {"User API (회원 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/user")
public class UserController {
    private static final String EMAIL_VALUE = "이메일";
    private static final String EMAIL_EXAMPLE = "1234@gmail.com";

    private static final String PROFILE_NAME = "profile";
    private static final String HTTP_URL = "http";

    private final UserService userService;
    private final StorageService storageService;

    @GetMapping("/me")
    @ApiOperation(value = "회원 정보", notes = "회원 정보를 리턴합니다.")
    @ApiImplicitParam(name = "email", value = EMAIL_VALUE, example = EMAIL_EXAMPLE, required = true)
    public ResponseEntity<UserResponseDto> userDetails(@RequestParam @Email(message = INPUT_EMAIL) String email) {
        User user = userService.findUserByEmail(email);
        return ResponseEntity.ok().body(new UserResponseDto(user));
    }

    @PutMapping("/updateInfo")
    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정를 수정한 후, 수정된 회원 정보를 리턴합니다.")
    @ApiImplicitParam(name = "userUpdateRequestDto", value = "회원 수정 정보", required = true)
    public ResponseEntity<UserResponseDto> userProfileModify(@RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto) {
        User user = userService.modifyUserProfile(userUpdateRequestDto);
        return ResponseEntity.ok().body(new UserResponseDto(user));
    }

    @GetMapping("/profileUrl")
    @ApiOperation(value = "회원 프로필 URL 정보 조회", notes = "회원 프로필 사진 URL 정보를 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "email", value = EMAIL_VALUE, example = EMAIL_EXAMPLE, required = true)
    public ResponseEntity<String> userProfileImageAsUrl(@RequestParam @Email(message = INPUT_EMAIL) String email) {
        User user = userService.findUserByEmail(email);
        if (isKakaoProfile(user)) {
            return ResponseEntity.ok().body(user.getProfile());
        }
        return ResponseEntity.ok().body(storageService.findFileUrl(user.getProfile()));
    }

    @GetMapping("/profileByte")
    @ApiOperation(value = "회원 프로필 bytearray 정보 조회", notes = "회원 프로필 사진을 bytearray로 리턴합니다.")
    @ApiImplicitParam(name = "email", value = EMAIL_VALUE, example = EMAIL_EXAMPLE, required = true)
    public ResponseEntity<byte[]> userProfileImageAsBytes(@RequestParam @Email(message = INPUT_EMAIL) String email) throws IOException {
        User user = userService.findUserByEmail(email);
        if (isKakaoProfile(user)) {
            return userService.findUserProfileImageAsBytes(email);
        }
        return storageService.findFileObject(PROFILE_NAME, user.getProfile());
    }

    @GetMapping("/profileBase")
    @ApiOperation(value = "회원 프로필 Base64 정보 조회", notes = "회원 프로필 사진을 Base64로 리턴합니다.")
    @ApiImplicitParam(name = "email", value = EMAIL_VALUE, example = EMAIL_EXAMPLE, required = true)
    public ResponseEntity<String> userProfileImageAsBase64(@RequestParam @Email(message = INPUT_EMAIL) String email) throws IOException {
        User user = userService.findUserByEmail(email);
        if (isKakaoProfile(user)) {
            return userService.findUserProfileImageAsBase64(email);
        }
        return storageService.findFileBase64(PROFILE_NAME, user.getProfile());
    }

    @PutMapping(value = "/updateProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "회원 프로필 사진 수정", notes = "회원 프로필 사진을 수정한 후 수정된 회원 정보를 리턴합니다.")
    @ApiImplicitParam(name = "email", value = EMAIL_VALUE, example = EMAIL_EXAMPLE, required = true)
    public ResponseEntity<UserResponseDto> userProfileImageModify(@RequestParam @Email(message = INPUT_EMAIL) String email,
                                                                  @RequestPart MultipartFile multipartFile) {
        User user = userService.modifyUserProfileImage(email, multipartFile);
        return ResponseEntity.ok().body(new UserResponseDto(user));
    }

    @GetMapping("/propensity")
    @ApiOperation(value = "사용자별 구매 성향", notes = "사용자별 구매 성향을 리턴합니다. (살까 말까 미정/결정/취소, 할까 말까 미정/결정/취소, 갈까 말까 미정/결정/취소 갯수)")
    @ApiImplicitParam(name = "email", value = EMAIL_VALUE, example = EMAIL_EXAMPLE, required = true)
    public Map<String, Object> userPropensityDetails(@RequestParam @Email(message = INPUT_EMAIL) String email) {
        return userService.findUserDecisionPropensity(email);
    }

    @PostMapping("/withdraw")
    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴를 한 후, true를 리턴합니다.")
    @ApiImplicitParam(name = "email", value = EMAIL_VALUE, example = EMAIL_EXAMPLE, required = true)
    public ResponseEntity<Boolean> userWithdraw(@RequestParam @Email(message = INPUT_EMAIL) String email) {
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
