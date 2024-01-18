package AlsongDalsong_backend.AlsongDalsong.service.user;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Decision;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Todo;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import AlsongDalsong_backend.AlsongDalsong.service.photo.StorageService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserUpdateRequestDto;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 회원 비즈니스 로직 구현 클래스
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final StorageService storageService;

    /**
     * 이메일로 회원 정보를 조회한다.
     *
     * @param email (회원 이메일)
     * @return User (이메일로 조회한 회원)
     */
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * 회원 프로필 이미지 조회를 위한 Byte 배열 기반 이미지 응답을 생성한다.
     *
     * @param email (회원 이메일)
     * @return byte[] (Byte 배열 기반 회원 프로필 이미지)
     * @throws IOException
     */
    @Override
    public ResponseEntity<byte[]> findUserProfileImageAsBytes(String email) throws IOException {
        User user = findUserByEmail(email);
        byte[] bytes = convertImageToBytes(user.getProfile());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", "profile");
        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    /**
     * 회원 프로필 이미지 조회를 위한 Base64 기반 이미지 응답을 생성한다.
     *
     * @param email (회원 이메일)
     * @return String (Base64 기반 회원 프로필 이미지)
     * @throws IOException
     */
    @Override
    public ResponseEntity<String> findUserProfileImageAsBase64(String email) throws IOException {
        User user = findUserByEmail(email);
        byte[] bytes = convertImageToBytes(user.getProfile());
        String encodedString = Base64.getEncoder().encodeToString(bytes);
        return new ResponseEntity<>(encodedString, HttpStatus.OK);
    }

    /**
     * 회원 정보를 수정한다.
     *
     * @param userUpdateRequestDto (회원 수정 정보 DTO)
     * @return User (정보가 수정된 회원)
     */
    @Override
    public User modifyUserProfile(UserUpdateRequestDto userUpdateRequestDto) {
        User user = findUserByEmail(userUpdateRequestDto.getEmail());
        user.updateInfo(userUpdateRequestDto.getNickname(), userUpdateRequestDto.getIntroduce());
        return user;
    }

    /**
     * 회원 프로필 이미지를 수정한다.
     *
     * @param email (회원 이메일), profileImage (변경할 프로필 이미지)
     * @return User (프로필 이미지가 수정된 회원)
     */
    @Override
    public User modifyUserProfileImage(String email, MultipartFile profileImage) {
        User user = findUserByEmail(email);
        deletePrevAndUpdateProfile(user, profileImage);
        return user;
    }

    /**
     * 회원별 구매 성향 통계를 조회한다.
     *
     * @param email (회원 이메일)
     * @return Map<String, Object> (회원의 구매 성향 통계)
     */
    @Override
    public Map<String, Object> findUserDecisionPropensity(String email) {
        User user = findUserByEmail(email);
        Map<String, Object> propensityMap = new HashMap<>();
        for (Todo todo : Todo.values()) {
            for (Decision decision : Decision.values()) {
                addPropensityToMap(propensityMap, user, todo, decision);
            }
        }
        return propensityMap;
    }

    /**
     * 회원을 탈퇴 처리한다.
     *
     * @param email (회원 이메일)
     * @return boolean (탈퇴 성공 여부)
     */
    @Override
    public Boolean withdrawUserAccount(String email) {
        User user = findUserByEmail(email);
        user.withdrawUser();
        return true;
    }

    /**
     * 회원 프로필 이미지를 Byte 배열로 변환한다.
     *
     * @param profileImageUrl (회원 프로필 이미지 URL)
     * @return byte[] (회원 프로필 이미지 Byte 배열)
     * @throws IOException
     */
    private byte[] convertImageToBytes(String profileImageUrl) throws IOException {
        URL url = new URL(profileImageUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        return IOUtils.toByteArray(inputStream);
    }

    /**
     * 기존의 프로필 이미지를 삭제한 후, 새로운 프로필 이미지로 변경한다.
     *
     * @param user (프로필 이미지를 변경할 회원), profileImage (프로필 이미지)
     */
    private void deletePrevAndUpdateProfile(User user, MultipartFile profileImage) {
        storageService.removeFile(user.getProfile());
        String profile = storageService.addProfileImage(profileImage);
        user.updateProfile(profile);
    }

    /**
     * 투두에 따른 회원 통계 정보를 저장한다.
     *
     * @param propensityMap (구매 성향 통계), user (회원), todo (투두 분류), decision (구매 결정 여부)
     */
    private void addPropensityToMap(Map<String, Object> propensityMap, User user, Todo todo, Decision decision) {
        propensityMap.put(todo.getTodo() + " " + decision.getDecision(),
                postRepository.countByUserIdAndTodoAndDecision(user, todo, decision));
    }
}
