package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.PhotoRepository;
import AlsongDalsong_backend.AlsongDalsong.service.AwsS3Service;
import AlsongDalsong_backend.AlsongDalsong.service.PhotoService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 사진 컨트롤러
 */
@Api(tags={"Photo API (사진 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PhotoController {

    private final PhotoRepository photoRepository;
    private final PhotoService photoService;
    private final AwsS3Service awsS3Service;

    // 사진 id로 이미지 정보 조회
    @GetMapping("/api/photo/photoInfo")
    @ApiOperation(value = "사진 id로 이미지 정보 조회", notes = "사진 id로 이미지 정보를 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "사진 id", example = "1")
    public ResponseEntity<PhotoResponseDto> findById(@RequestParam Long id) {
        return ResponseEntity.ok().body(photoService.findByPhotoId(id));
    }

    // 사진 id로 이미지 URL 정보 조회
    @GetMapping("/api/photo/photoURL")
    @ApiOperation(value = "사진 id로 이미지 URL 정보 조회", notes = "사진 id로 이미지 URL 정보를 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "사진 id", example = "1")
    public ResponseEntity<String> getS3(@RequestParam Long id) {
        Photo photo = photoRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 사진이 없습니다."));
        return ResponseEntity.ok().body(awsS3Service.getS3(photo.getPhotoName()));
    }

    // 사진 id로 이미지 bytearray 정보 조회
    @GetMapping("/api/photo/photoByte")
    @ApiOperation(value = "사진 id로 이미지 bytearray 정보 조회", notes = "사진 id로 이미지를 bytearray로 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "사진 id", example = "1")
    public ResponseEntity<byte[]> getByte(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Photo photo = photoRepository.findById(id).orElse(null);
        return awsS3Service.getObject(photo.getOrigPhotoName(), photo.getPhotoName());
    }

    // 사진 id로 이미지 Base64 정보 조회
    @GetMapping("/api/photo/photoBase")
    @ApiOperation(value = "사진 id로 이미지 Base64 정보 조회", notes = "사진 id로 이미지를 Base64로 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "사진 id", example = "1")
    public ResponseEntity<String> getBase(@RequestParam Long id) throws IOException {
        Photo photo = photoRepository.findById(id).orElse(null);
        return awsS3Service.getBase(photo.getOrigPhotoName(), photo.getPhotoName());
    }
}
