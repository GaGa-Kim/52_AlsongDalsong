package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.service.LikeService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.like.LikeRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private final LikeService likeService;

    // 댓글 좋아요
    @PostMapping("/api/like/save")
    @ApiOperation(value = "댓글 좋아요 작성/삭제", notes = "댓글 좋아요 작성/삭제 API")
    public ResponseEntity<Boolean> save(@RequestBody LikeRequestDto likeSaveRequestDto) {
        return ResponseEntity.ok().body(likeService.save(likeSaveRequestDto));
    }
    
    // 사용자에 따른 댓글 좋아요 조회
    @PostMapping("/api/like/inquire")
    @ApiOperation(value = "댓글 좋아요 조회", notes = "댓글 좋아요 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "댓글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com")
    })
    public ResponseEntity<Boolean> inquire(@RequestParam Long id, String email) {
        return ResponseEntity.ok().body(likeService.inquire(id, email));
    }
}
