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
 * 회원 정보 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AwsS3Service awsS3Service;

    // 회원 정보 조회
    @Transactional(readOnly = true)
    public User getUser(String email) {
        return findUserByEmail(email);
    }

    // 이메일로 회원 조회
    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 회원 정보 중 회원 프로필 사진 조회를 위한 Byte 배열 기반 이미지 응답 생성
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

    // 회원 정보 중 프로필 사진 조회를 위한 Base64 기반 이미지 응답 생성
    @Transactional(readOnly = true)
    public ResponseEntity<String> getProfileBase(String email) throws IOException {
        User user = findUserByEmail(email);
        byte[] bytes = convertProfileImage(user.getProfile());
        String encodedString = Base64.getEncoder().encodeToString(bytes);
        return new ResponseEntity<>(encodedString, HttpStatus.OK);
    }

    // 회원 프로필 이미지 Byte 배열 변환
    private byte[] convertProfileImage(String profileUrl) throws IOException {
        URL url = new URL(profileUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        return IOUtils.toByteArray(inputStream);
    }

    // 회원 정보 수정
    public User updateUser(UserUpdateRequestDto userUpdateRequestDto) {
        User user = findUserByEmail(userUpdateRequestDto.getEmail());
        user.updateInfo(userUpdateRequestDto.getNickname(), userUpdateRequestDto.getIntroduce());
        return user;
    }

    // 회원 프로필 사진 수정
    public User updateProfile(String email, MultipartFile multipartFile) {
        User user = findUserByEmail(email);
        deletePreAndUpdateProfile(user, multipartFile);
        return user;
    }

    // 프로필 사진 업데이트
    private void deletePreAndUpdateProfile(User user, MultipartFile multipartFile) {
        awsS3Service.deleteS3(user.getProfile());
        String profile = awsS3Service.uploadProfile(multipartFile);
        user.updateProfile(profile);
    }

    // 사용자별 구매 성향 통계
    @Transactional(readOnly = true)
    public Map<String, Object> propensity(String email) {
        User user = userRepository.findByEmail(email);
        Map<String, Object> propensityMap = new HashMap<>();
        for (Todo todo : Todo.values()) {
            for (Decision decision : Decision.values()) {
                addPropensityToMap(propensityMap, user, todo, decision);
            }
        }
        return propensityMap;
    }

    // 투두에 따른 사용자 통계 정보 저장
    private void addPropensityToMap(Map<String, Object> propensityMap, User user, Todo todo, Decision decision) {
        propensityMap.put(todo.getTodo() + " " + decision.getDecision(),
                postRepository.countByUserIdAndTodoAndDecision(user, todo.getTodo(), decision.getDecision()));
    }

    // 회원 탈퇴
    public Boolean withdrawUser(String email) {
        User user = findUserByEmail(email);
        user.withdrawUser();
        return true;
    }
}
