package AlsongDalsong_backend.AlsongDalsong.service.photo;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_ORIG_PHOTO_NANE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PHOTO_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import AlsongDalsong_backend.AlsongDalsong.config.GlobalConfig;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import com.amazonaws.services.s3.AmazonS3Client;
import io.findify.s3mock.S3Mock;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * 회원 프로필 이미지, 게시글 사진 파일 스토리지(AWS S3)를 위한 비즈니스 로직 구현 클래스 테스트
 */
@SpringBootTest
@Import(AwsS3MockConfig.class)
class AwsS3ServiceImplTest {
    private final String contentType = "image/png";
    private final byte[] content = VALID_PHOTO_NAME.getBytes();
    private final MockMultipartFile file = new MockMultipartFile(VALID_PHOTO_NAME, VALID_ORIG_PHOTO_NANE, contentType, content);

    @Autowired
    private AwsS3ServiceImpl awsS3Service;

    @BeforeAll
    static void setUp(@Autowired S3Mock s3Mock,
                      @Autowired AmazonS3Client amazonS3Client,
                      @Autowired GlobalConfig globalConfig) {
        s3Mock.start();
        amazonS3Client.createBucket(globalConfig.getAws_bucket());
    }

    @AfterAll
    static void tearDown(@Autowired S3Mock s3Mock,
                         @Autowired AmazonS3Client amazonS3Client) {
        amazonS3Client.shutdown();
        s3Mock.stop();
    }

    @Test
    @DisplayName("AWS S3 버킷에 회원 프로필 이미지(파일) 저장 테스트")
    void testAddProfileImage() {
        String result = awsS3Service.addProfileImage(file);

        assertNotNull(result);
    }

    @Test
    @DisplayName("AWS S3 버킷에 게시글 사진들(파일들) 저장 테스트")
    void testAddPhoto() {
        List<MultipartFile> files = Collections.singletonList(file);
        List<Photo> result = awsS3Service.addPhoto(files);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(VALID_ORIG_PHOTO_NANE, result.get(0).getOrigPhotoName());
    }

    @Test
    @DisplayName("AWS S3 버킷에 파일 URL 조회 테스트")
    void testFindFileUrl() {
        String newFileName = awsS3Service.addProfileImage(file);

        String result = awsS3Service.findFileUrl(newFileName);

        assertNotNull(result);
    }

    @Test
    @DisplayName("AWS S3 버킷에 파일을 조회해 다운로드 변환 테스트")
    void testFindFileObject() throws IOException {
        String newFileName = awsS3Service.addProfileImage(file);

        ResponseEntity<byte[]> result = awsS3Service.findFileObject(VALID_ORIG_PHOTO_NANE, newFileName);

        assertNotNull(result);
    }

    @Test
    @DisplayName("AWS S3 버킷에 파일을 조회해 Base64로 변환 테스트")
    void testFindFileBase64() throws IOException {
        String newFileName = awsS3Service.addProfileImage(file);

        ResponseEntity<String> result = awsS3Service.findFileBase64(VALID_ORIG_PHOTO_NANE, newFileName);

        assertNotNull(result);
    }

    @Test
    @DisplayName("AWS S3 버킷에서 파일 삭제 테스트")
    void testRemoveFile() {
        String newFileName = awsS3Service.addProfileImage(file);

        awsS3Service.removeFile(newFileName);
    }
}