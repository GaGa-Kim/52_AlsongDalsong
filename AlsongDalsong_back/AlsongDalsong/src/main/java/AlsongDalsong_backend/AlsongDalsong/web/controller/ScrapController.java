package AlsongDalsong_backend.AlsongDalsong.web.controller;

import static AlsongDalsong_backend.AlsongDalsong.constants.Message.INPUT_EMAIL;

import AlsongDalsong_backend.AlsongDalsong.constants.Message;
import AlsongDalsong_backend.AlsongDalsong.service.scrap.ScrapService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 스크랩 컨트롤러
 */
@Api(tags = {"Scrap API (스크랩 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/scrap")
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping("/save")
    @ApiOperation(value = "스크랩 작성/삭제", notes = "게시글을 스크랩한 후, true를 리턴합니다. 게시글이 이미 스크랩되어 있을 경우 스크랩이 취소되고 false를 리턴합니다.")
    @ApiImplicitParam(name = "scrapResponseDto", value = "스크랩 작성 정보", required = true)
    public ResponseEntity<Boolean> scrapSave(@RequestBody @Valid ScrapRequestDto scrapResponseDto) {
        return ResponseEntity.ok().body(scrapService.saveScrap(scrapResponseDto));
    }

    @PostMapping("/check")
    @ApiOperation(value = "사용자별 게시글에 따른 스크랩 여부 조회", notes = "사용자별로 게시글에 스크랩을 눌렀는지 조회합니다. 스크랩했다면 true, 그렇지 않다면 false를 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "게시글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com")
    })
    public ResponseEntity<Boolean> scrapDetails(@RequestParam @NotNull(message = Message.INPUT_POST_ID) Long postId,
                                                @RequestParam @Email(message = INPUT_EMAIL) String email) {
        return ResponseEntity.ok().body(scrapService.findScrap(postId, email));
    }

    @GetMapping("/inquire")
    @ApiOperation(value = "사용자별 스크랩 조회", notes = "사용자별 스크랩 목록을 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com")
    public ResponseEntity<List<ScrapResponseDto>> scrapUserList(@RequestParam @Email(message = INPUT_EMAIL) String email) {
        return ResponseEntity.ok().body(scrapService.findUserScraps(email));
    }
}
