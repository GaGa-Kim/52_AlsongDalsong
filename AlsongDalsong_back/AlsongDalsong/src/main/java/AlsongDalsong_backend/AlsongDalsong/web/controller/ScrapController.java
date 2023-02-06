package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.service.ScrapService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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

    private final ScrapService scrapService;

    // 스크랩
    @PostMapping("/api/scrap/save")

    @ApiOperation(value = "스크랩 작성/삭제", notes = "게시글을 스크랩한 후, true를 리턴합니다. 게시글이 이미 스크랩되어 있을 경우 스크랩이 취소되고 false를 리턴합니다.")
    public ResponseEntity<Boolean> save(@RequestBody ScrapRequestDto scrapResponseDto) {
        return ResponseEntity.ok().body(scrapService.save(scrapResponseDto));
    }

    // 사용자별 게시글에 따른 스크랩 여부 조회
    @PostMapping("/api/scrap/check")
    @ApiOperation(value = "사용자별 게시글에 따른 스크랩 여부 조회", notes = "사용자별로 게시글에 스크랩을 눌렀는지 조회합니다. 스크랩했다면 true, 그렇지 않다면 false를 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "게시글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com")
    })
    public ResponseEntity<Boolean> check(@RequestParam Long postId, String email) {
        return ResponseEntity.ok().body(scrapService.check(postId, email));
    }
    
    // 사용자별 스크랩 조회
    @GetMapping("/api/scrap/inquire")
    @ApiOperation(value = "사용자별 스크랩 조회", notes = "사용자별 스크랩 목록을 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com")
    public ResponseEntity<List<ScrapResponseDto>> inquire(String email) {
        return ResponseEntity.ok().body(scrapService.inquire(email));
    }

}
