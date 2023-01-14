package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.service.ScrapService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 스크랩 컨트롤러
 */
@Api(tags={"Scrap API (스크랩 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ScrapController {

    @Autowired
    private final ScrapService scrapService;

    // 스크랩
    @PostMapping("/api/scrap/save")
    @ApiOperation(value = "스크랩 작성/삭제", notes = "스크랩 작성/삭제 API")
    public ResponseEntity<Boolean> save(@RequestBody ScrapRequestDto scrapResponseDto) {
        return ResponseEntity.ok().body(scrapService.save(scrapResponseDto));
    }
    
    // 스크랩 조회
    @GetMapping("/api/scrap/inquire")
    @ApiOperation(value = "스크랩 조회", notes = "스크랩 작성/삭제 API")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com")
    public ResponseEntity<List<ScrapResponseDto>> inquire(String email) {
        return ResponseEntity.ok().body(scrapService.inquire(email));
    }

}
