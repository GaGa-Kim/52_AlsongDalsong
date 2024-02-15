package AlsongDalsong_backend.AlsongDalsong.service.user;

import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserUpdateRequestDto;
import java.io.IOException;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 회원 비즈니스 로직 인터페이스
 */
@Transactional
public interface UserService {
    // 이메일로 회원 정보를 조회한다.
    @Transactional(readOnly = true)
    User findUserByEmail(String email);

    // 회원 프로필 이미지 조회를 위한 Byte 배열 기반 이미지 응답을 생성한다.
    @Transactional(readOnly = true)
    ResponseEntity<byte[]> findUserProfileImageAsBytes(String email) throws IOException;

    // 회원 프로필 이미지 조회를 위한 Base64 기반 이미지 응답을 생성한다.
    @Transactional(readOnly = true)
    ResponseEntity<String> findUserProfileImageAsBase64(String email) throws IOException;

    // 회원 정보를 수정한다.
    User modifyUserProfile(UserUpdateRequestDto userUpdateRequestDto);

    // 회원 프로필 이미지를 수정한다.
    User modifyUserProfileImage(String email, MultipartFile profileImage);

    // 회원별 구매 성향 통계를 조회한다.
    @Transactional(readOnly = true)
    Map<String, Object> findUserDecisionPropensity(String email);

    // 회원을 탈퇴 처리한다.
    Boolean withdrawUserAccount(String email);
}
