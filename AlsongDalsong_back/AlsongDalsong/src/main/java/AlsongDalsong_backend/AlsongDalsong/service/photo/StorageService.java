package AlsongDalsong_backend.AlsongDalsong.service.photo;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 회원 프로필 이미지, 게시글 사진 파일 스토리지를 위한 비즈니스 로직 인터페이스
 */
@Transactional
public interface StorageService {
    // AWS S3 버킷에 회원 프로필 이미지(파일)를 저장한다.
    String addProfileImage(MultipartFile profileImage);

    // AWS S3 버킷에 게시글 사진들(파일들)을 저장한다.
    List<Photo> addPhoto(List<MultipartFile> photos);

    // AWS S3 버킷에 파일 URL을 조회한다.
    String findFileUrl(String fileName);

    // AWS S3 버킷에 파일을 조회해 다운로드한다.
    ResponseEntity<byte[]> findFileObject(String originFileName, String fileName) throws IOException;

    // AWS S3 버킷에 파일을 조회해 Base64로 변환한다.
    ResponseEntity<String> findFileBase64(String originFileName, String fileName) throws IOException;

    // AWS S3 버킷에서 파일을 삭제한다.
    void removeFile(String fileName);
}
