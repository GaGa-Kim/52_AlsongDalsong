package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.service.photo.PhotoService;
import AlsongDalsong_backend.AlsongDalsong.service.photo.StorageService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사진 컨트롤러
 */
@Api(tags = {"Photo API (사진 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/photo")
public class PhotoController {
    private final PhotoService photoService;
    private final StorageService storageService;

    @GetMapping("/photoInfo")
    @ApiOperation(value = "사진 id로 이미지 정보 조회", notes = "사진 id로 이미지 정보를 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "사진 id", example = "1")
    public ResponseEntity<PhotoResponseDto> photoDetails(@RequestParam Long id) {
        return ResponseEntity.ok().body(photoService.findPhoto(id));
    }

    @GetMapping("/photoURL")
    @ApiOperation(value = "사진 id로 이미지 URL 정보 조회", notes = "사진 id로 이미지 URL 정보를 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "사진 id", example = "1")
    public ResponseEntity<String> photoUrlDetails(@RequestParam Long id) {
        return ResponseEntity.ok().body(storageService.findFileUrl(getPhotoName(id).getSecond()));
    }

    @GetMapping("/photoByte")
    @ApiOperation(value = "사진 id로 이미지 bytearray 정보 조회", notes = "사진 id로 이미지를 bytearray로 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "사진 id", example = "1")
    public ResponseEntity<byte[]> photoByteArrayDetails(@RequestParam Long id) throws IOException {
        Pair<String, String> photoName = getPhotoName(id);
        return storageService.findFileObject(photoName.getFirst(), photoName.getSecond());
    }

    @GetMapping("/photoBase")
    @ApiOperation(value = "사진 id로 이미지 Base64 정보 조회", notes = "사진 id로 이미지를 Base64로 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "사진 id", example = "1")
    public ResponseEntity<String> photoBase64Details(@RequestParam Long id) throws IOException {
        Pair<String, String> photoName = getPhotoName(id);
        return storageService.findFileBase64(photoName.getFirst(), photoName.getSecond());
    }

    // 사진 id로 사진 원본 이름, 사진 변환 이름 조회
    private Pair<String, String> getPhotoName(Long photoId) {
        Photo photo = photoService.findPhotoByPhotoId(photoId);
        return Pair.of(photo.getOrigPhotoName(), photo.getPhotoName());
    }
}
