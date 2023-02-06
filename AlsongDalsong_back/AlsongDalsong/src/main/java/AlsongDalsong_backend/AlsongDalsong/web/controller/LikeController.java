package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.service.LikeService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.like.LikeRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 댓글 좋아요 컨트롤러
 */
@Api(tags={"Like API (댓글 좋아요 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LikeController {

    private final LikeService likeService;

    // 댓글 좋아요
    @PostMapping("/api/like/save")
    @ApiOperation(value = "댓글 좋아요 작성/취소", notes = "댓글에 좋아요를 작성한 후, true를 리턴합니다. 댓글이 이미 좋아요일 경우 좋아요가 취소되고 false를 리턴합니다. ")
    public ResponseEntity<Boolean> save(@RequestBody LikeRequestDto likeSaveRequestDto) {
        return ResponseEntity.ok().body(likeService.save(likeSaveRequestDto));
    }
    
    // 사용자별 댓글에 따른 좋아요 여부 조회
    @PostMapping("/api/like/check")
    @ApiOperation(value = "사용자별 댓글에 따른 좋아요 여부 조회", notes = "사용자별로 댓글에 좋아요를 눌렀는지 조회합니다. 좋아요 했다면 true, 그렇지 않다면 false를 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "댓글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com")
    })
    public ResponseEntity<Boolean> check(@RequestParam Long id, String email) {
        return ResponseEntity.ok().body(likeService.check(id, email));
    }
}
