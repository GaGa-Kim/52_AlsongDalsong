package AlsongDalsong_backend.AlsongDalsong.service;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Decision;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Todo;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 회원 비즈니스 로직 구현 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository; // 회원 레포지토리
    private final PostRepository postRepository; // 게시글 레포지토리
    private final AwsS3Service awsS3Service; // AWS S3 레포지토리

    /**
     * 회원 정보를 조회한다.
     *
     * @param email (회원 이메일)
     * @return User (이메일로 조회한 회원)
     */
    @Override
    @Transactional(readOnly = true)
    public User getUser(String email) {
        return findUserByEmail(email);
    }

    /**
     * 회원 프로필 이미지 조회를 위한 Byte 배열 기반 이미지 응답을 생성한다.
     *
     * @param email (회원 이메일)
     * @return byte[] (Byte 배열 기반 회원 프로필 이미지)
     * @throws IOException
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> getProfileByte(String email) throws IOException {
        User user = findUserByEmail(email);
        byte[] bytes = convertProfileImage(user.getProfile());
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
    @Transactional(readOnly = true)
    public ResponseEntity<String> getProfileBase(String email) throws IOException {
        User user = findUserByEmail(email);
        byte[] bytes = convertProfileImage(user.getProfile());
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
    public User updateUser(UserUpdateRequestDto userUpdateRequestDto) {
        User user = findUserByEmail(userUpdateRequestDto.getEmail());
        user.updateInfo(userUpdateRequestDto.getNickname(), userUpdateRequestDto.getIntroduce());
        return user;
    }

    /**
     * 회원 프로필 이미지를 수정한다.
     *
     * @param email (회원 이메일), multipartFile (변경할 프로필 이미지)
     * @return User (프로필 이미지가 수정된 회원)
     */
    @Override
    public User updateProfile(String email, MultipartFile multipartFile) {
        User user = findUserByEmail(email);
        deletePreAndUpdateProfile(user, multipartFile);
        return user;
    }

    /**
     * 회원 별 구매 성향 통계를 조회한다.
     *
     * @param email (회원 이메일)
     * @return Map<String, Object> (회원의 구매 성향 통계)
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getUserPropensity(String email) {
        User user = userRepository.findByEmail(email);
        Map<String, Object> propensityMap = new HashMap<>();
        for (Todo todo : Todo.values()) {
            for (Decision decision : Decision.values()) {
                addPropensityToMap(propensityMap, user, todo, decision);
            }
        }
        return propensityMap;
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

    /**
     * 회원을 탈퇴 처리한다.
     *
     * @param email (회원 이메일)
     * @return boolean (탈퇴 성공 여부)
     */
    @Override
    public Boolean withdrawUser(String email) {
        User user = findUserByEmail(email);
        user.withdrawUser();
        return true;
    }

    /**
     * 회원 프로필 이미지를 Byte 배열로 변환한다.
     *
     * @param profileUrl (회원 프로필 이미지 URL)
     * @return byte[] (회원 프로필 이미지 Byte 배열)
     * @throws IOException
     */
    private byte[] convertProfileImage(String profileUrl) throws IOException {
        URL url = new URL(profileUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        return IOUtils.toByteArray(inputStream);
    }

    /**
     * 이메일로 회원을 조회한다.
     *
     * @param email (회원 이메일)
     * @return User (이메일로 조회한 회원)
     */
    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * 기존의 프로필 이미지를 삭제한 후, 새로운 프로필 이미지로 변경한다.
     *
     * @param user (프로필 이미지를 변경할 회원), multipartFile (프로필 이미지)
     */
    private void deletePreAndUpdateProfile(User user, MultipartFile multipartFile) {
        awsS3Service.deleteS3(user.getProfile());
        String profile = awsS3Service.uploadProfile(multipartFile);
        user.updateProfile(profile);
    }
}
