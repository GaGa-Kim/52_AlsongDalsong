package AlsongDalsong_backend.AlsongDalsong.service.photo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import com.amazonaws.services.s3.AmazonS3Client;
import io.findify.s3mock.S3Mock;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final String name = "test.png";
    private final String originalFilename = "testOriginal.png";
    private final String contentType = "image/png";
    private final byte[] content = name.getBytes();

    @Autowired
    private AwsS3ServiceImpl awsS3Service;

    @BeforeAll
    static void setUp(@Value("${cloud.aws.s3.bucket}") String bucket,
                      @Autowired S3Mock s3Mock, @Autowired AmazonS3Client amazonS3Client) {
        s3Mock.start();
        amazonS3Client.createBucket(bucket);
    }

    @AfterAll
    static void tearDown(@Autowired S3Mock s3Mock, @Autowired AmazonS3Client amazonS3Client) {
        amazonS3Client.shutdown();
        s3Mock.stop();
    }

    @Test
    void testAddProfileImage() {
        MockMultipartFile file = new MockMultipartFile(name, originalFilename, contentType, content);
        String result = awsS3Service.addProfileImage(file);

        assertNotNull(result);
    }

    @Test
    void testAddPhoto() {
        MockMultipartFile file = new MockMultipartFile(name, originalFilename, contentType, content);
        List<MultipartFile> files = Collections.singletonList(file);
        List<Photo> result = awsS3Service.addPhoto(files);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(originalFilename, result.get(0).getOrigPhotoName());
    }

    @Test
    void testFindFileUrl() {
        MockMultipartFile file = new MockMultipartFile(name, originalFilename, contentType, content);
        String newFileName = awsS3Service.addProfileImage(file);

        String result = awsS3Service.findFileUrl(newFileName);

        assertNotNull(result);
    }

    @Test
    void testFindFileObject() throws IOException {
        MockMultipartFile file = new MockMultipartFile(name, originalFilename, contentType, content);
        String newFileName = awsS3Service.addProfileImage(file);

        ResponseEntity<byte[]> result = awsS3Service.findFileObject(originalFilename, newFileName);

        assertNotNull(result);
    }

    @Test
    void testFindFileBase64() throws IOException {
        MockMultipartFile file = new MockMultipartFile(name, originalFilename, contentType, content);
        String newFileName = awsS3Service.addProfileImage(file);

        ResponseEntity<String> result = awsS3Service.findFileBase64(originalFilename, newFileName);

        assertNotNull(result);
    }

    @Test
    void testRemoveFile() {
        MockMultipartFile file = new MockMultipartFile(name, originalFilename, contentType, content);
        String newFileName = awsS3Service.addProfileImage(file);

        awsS3Service.removeFile(newFileName);
    }
}