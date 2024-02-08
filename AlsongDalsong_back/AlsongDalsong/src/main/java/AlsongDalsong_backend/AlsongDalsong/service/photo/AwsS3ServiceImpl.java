package AlsongDalsong_backend.AlsongDalsong.service.photo;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoResponseDto;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 * 회원 프로필 이미지, 게시글 사진 파일 스토리지(AWS S3)를 위한 비즈니스 로직 구현 클래스
 */
@Service
@RequiredArgsConstructor
public class AwsS3ServiceImpl implements StorageService {
    private static final String UPLOAD_FAIL = "업로드에 실패했습니다.";
    private static final String INVALID_FORMAT = "잘못된 형식의 파일 입니다.";
    private static final String FILE_NAME_SEPARATOR = ".";

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    /**
     * AWS S3 버킷에 회원 프로필 이미지(파일)를 저장한다.
     *
     * @param profileImage (회원 프로필 이미지)
     * @return String (프로필 이미지 변환 이름)
     */
    @Override
    public String addProfileImage(MultipartFile profileImage) {
        return addFile(profileImage);
    }

    /**
     * AWS S3 버킷에 게시글 사진들(파일들)을 저장한다.
     *
     * @param photos (게시글 사진들)
     * @return List<Photo> (게시글 사진들)
     */
    @Override
    public List<Photo> addPhoto(List<MultipartFile> photos) {
        return photos.stream()
                .map(file -> {
                    String photoName = addFile(file);
                    PhotoResponseDto photoResponseDto = PhotoResponseDto.builder()
                            .origPhotoName(file.getOriginalFilename())
                            .photoName(photoName)
                            .photoUrl(findFileUrl(photoName))
                            .build();
                    return Photo.builder()
                            .origPhotoName(photoResponseDto.getOrigPhotoName())
                            .photoName(photoResponseDto.getPhotoName())
                            .photoUrl(photoResponseDto.getPhotoUrl())
                            .build();
                })
                .collect(Collectors.toList());
    }

    /**
     * AWS S3 버킷에 파일 URL을 조회한다.
     *
     * @param fileName (파일 변환 이름)
     * @return String (파일 URL)
     */
    @Override
    public String findFileUrl(String fileName) {
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    /**
     * AWS S3 버킷에 파일을 조회해 다운로드한다.
     *
     * @param originFileName (원본 파일 이름), fileName (파일 변환 이름)
     * @return byte[] (다운로드 가능한 파일 ByteArray)
     * @throws IOException
     */
    @Override
    public ResponseEntity<byte[]> findFileObject(String originFileName, String fileName) throws IOException {
        byte[] bytes = findFileBytes(originFileName, fileName);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", originFileName);
        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    /**
     * AWS S3 버킷에 파일을 조회해 Base64로 변환한다.
     *
     * @param originFileName (원본 파일 이름), fileName (파일 변환 이름)
     * @return String (파일 Base64)
     * @throws IOException
     */
    @Override
    public ResponseEntity<String> findFileBase64(String originFileName, String fileName) throws IOException {
        byte[] bytes = findFileBytes(originFileName, fileName);
        String encodedString = Base64.getEncoder().encodeToString(bytes);
        return new ResponseEntity<>(encodedString, HttpStatus.OK);
    }

    /**
     * AWS S3 버킷에서 파일을 삭제한다.
     *
     * @param fileName (파일 변환 이름)
     */
    @Override
    public void removeFile(String fileName) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
        amazonS3Client.deleteObject(request);
    }

    /**
     * AWS S3 버킷에서 파일을 업로드한다.
     *
     * @param file (업로드할 파일)
     * @return String (업로드한 파일 이름)
     */
    private String addFile(MultipartFile file) {
        String name = createName(file.getOriginalFilename());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, name, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, UPLOAD_FAIL);
        }
        return name;
    }

    /**
     * 랜덤한 UUID를 생성해 사진 이름을 변환한다.
     *
     * @param originalFileName (원본 파일 이름)
     * @return String (파일 변환 이름)
     */
    private String createName(String originalFileName) {
        return UUID.randomUUID().toString().concat(extractFileExtension(originalFileName));
    }

    /**
     * 파일 확장자를 추출한다.
     *
     * @param originalFileName (원본 파일 이름)
     * @return String (추출한 파일 확장자)
     */
    private String extractFileExtension(String originalFileName) {
        try {
            return originalFileName.substring(originalFileName.lastIndexOf(FILE_NAME_SEPARATOR));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INVALID_FORMAT);
        }
    }

    /**
     * AWS S3 버킷에서 ByteArray 형식으로 파일을 조회한다.
     *
     * @param originFileName (원본 파일 이름), fileName (파일 변환 이름)
     * @return byte[] (파일 ByteArray)
     * @throws IOException
     */
    private byte[] findFileBytes(String originFileName, String fileName) throws IOException {
        S3Object o = amazonS3Client.getObject(new GetObjectRequest(bucket, fileName));
        S3ObjectInputStream objectInputStream = o.getObjectContent();
        return IOUtils.toByteArray(objectInputStream);
    }
}