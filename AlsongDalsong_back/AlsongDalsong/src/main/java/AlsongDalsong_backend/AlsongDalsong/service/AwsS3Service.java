package AlsongDalsong_backend.AlsongDalsong.service;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoResponseDto;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * AWS S3 프로필/사진 저장 서비스
 */
@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    // S3 버킷에 회원 프로필 이미지 저장
    public String uploadProfile(MultipartFile multipartFile) {
        String profileName = createPhotoName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try(InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, profileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch(IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "프로필 업로드에 실패했습니다.");
        }

        return profileName;
    }

    // S3 버킷에 게시글 이미지 저장
    @Transactional
    public List<Photo> uploadPhoto(List<MultipartFile> multipartFiles) {
        List<Photo> photoList = new ArrayList<>();

        for(MultipartFile multipartFile: multipartFiles) {

            String photoName = createPhotoName(multipartFile.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try(InputStream inputStream = multipartFile.getInputStream()) {
                amazonS3Client.putObject(new PutObjectRequest(bucket, photoName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
            } catch(IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "사진 업로드에 실패했습니다.");
            }

            PhotoResponseDto photoResponseDto = PhotoResponseDto.builder()
                    .origPhotoName(multipartFile.getOriginalFilename())
                    .photoName(photoName)
                    .photoUrl(amazonS3Client.getUrl(bucket, photoName).toString())
                    .build();

            Photo photo = new Photo(
                    photoResponseDto.getOrigPhotoName(),
                    photoResponseDto.getPhotoName(),
                    photoResponseDto.getPhotoUrl()
            );

            photoList.add(photo);

        }
        return photoList;
    }

    // S3 버킷에서 사진 URL 가져오기
    @Transactional(readOnly = true)
    public String getS3(String photoName) {
        return amazonS3Client.getUrl(bucket, photoName).toString();
    }

    // S3 버킷에서 사진 삭제
    @Transactional
    public void deleteS3(String photoName) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, photoName);
        amazonS3Client.deleteObject(request);
    }

    // S3 버킷에서 사진 가져와서 다운로드
    @Transactional
    public ResponseEntity<byte[]> getObject(String originPhotoName, String photoName) throws IOException {
        S3Object o = amazonS3Client.getObject(new GetObjectRequest(bucket, photoName));
        S3ObjectInputStream objectInputStream = ((S3Object) o).getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", originPhotoName);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    // S3 버킷에서 사진 가져와서 Base64 변환
    @Transactional
    public ResponseEntity<String> getBase(String originPhotoName, String photoName) throws IOException {
        S3Object o = amazonS3Client.getObject(new GetObjectRequest(bucket, photoName));
        S3ObjectInputStream objectInputStream = ((S3Object) o).getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);
        String encodedString = Base64.getEncoder().encodeToString(bytes);

        return new ResponseEntity<>(encodedString, HttpStatus.OK);
    }

    // 사진 이름 변환
    public String createPhotoName(String photoName) {
        return UUID.randomUUID().toString().concat(getPhotoExtension(photoName));
    }

    // 사진 확장자
    public String getPhotoExtension(String photoName) {
        try {
            return photoName.substring(photoName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 사진(" + photoName + ") 입니다.");
        }
    }
}