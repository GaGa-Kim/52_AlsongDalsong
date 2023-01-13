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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private final PhotoRepository photoRepository;
    private final PhotoService photoService;
    private final AwsS3Service awsS3Service;

    // 사진 정보 조회
    @GetMapping("/api/photo/photoInfo")
    @ApiOperation(value = "사진 id로 이미지 정보 조회", notes = "사진 id로 이미지 정보 조회 API")
    @ApiImplicitParam(name = "id", value = "사진 id", example = "1")
    public ResponseEntity<PhotoResponseDto> findById(@RequestParam Long id) {
        return ResponseEntity.ok().body(photoService.findByPhotoId(id));
    }

    // 사진 URL 정보 조회
    @GetMapping("/api/photo/photoURL")
    @ApiOperation(value = "사진 id로 이미지 URL 정보 조회", notes = "사진 id로 이미지 URL 정보 조회 API")
    @ApiImplicitParam(name = "id", value = "사진 id", example = "1")
    public ResponseEntity<String> getS3(@RequestParam Long id) {
        Photo photo = photoRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 사진이 없습니다."));
        return ResponseEntity.ok().body(awsS3Service.getS3(photo.getPhotoName()));
    }

    // 사진 다운로드
    @GetMapping("/api/photo/photoDownload")
    @ApiOperation(value = "사진 id로 이미지 다운로드", notes = "사진 id로 이미지 다운로드 API")
    @ApiImplicitParam(name = "id", value = "사진 id", example = "1")
    public ResponseEntity<byte[]> downloadFiles(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Photo photo = photoRepository.findById(id).orElse(null);
        return awsS3Service.getObject(photo.getOrigPhotoName(), photo.getPhotoName());
    }
}