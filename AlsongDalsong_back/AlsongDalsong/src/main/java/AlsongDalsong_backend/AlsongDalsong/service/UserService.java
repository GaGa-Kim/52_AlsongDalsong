package AlsongDalsong_backend.AlsongDalsong.service;

import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserUpdateRequestDto;
import java.io.IOException;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 회원 비즈니스 로직 인터페이스
 */
public interface UserService {
    User getUser(String email);

    ResponseEntity<byte[]> getProfileByte(String email) throws IOException;

    ResponseEntity<String> getProfileBase(String email) throws IOException;

    User updateUser(UserUpdateRequestDto userUpdateRequestDto);

    User updateProfile(String email, MultipartFile multipartFile);

    Map<String, Object> getUserPropensity(String email);

    Boolean withdrawUser(String email);
}
