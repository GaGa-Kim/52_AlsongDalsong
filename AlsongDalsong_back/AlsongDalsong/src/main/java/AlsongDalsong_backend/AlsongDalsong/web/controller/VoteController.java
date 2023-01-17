package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.service.VoteService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.vote.VoteRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 투표 컨트롤러
 */
@Api(tags={"Vote API (투표 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class VoteController {

    @Autowired
    private final VoteService voteService;

    // 게시글 투표하기
    @PostMapping("/api/vote/save")
    @ApiOperation(value = "게시글 투표하기", notes = "게시글 투표하기 API (투표, 변경, 취소)")
    public ResponseEntity<Boolean> vote(@RequestBody VoteRequestDto voteRequestDto) {
        return ResponseEntity.ok().body(voteService.vote(voteRequestDto));
    }

    // 게시글에 따른 투표 여부 조회
    @PostMapping("/api/vote/check")
    @ApiOperation(value = "게시글에 따른 투표 여부 조회", notes = "게시글에 따른 투표 여부 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "게시글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com")
    })
    public ResponseEntity<String> check(@RequestParam Long postId, String email) {
        return ResponseEntity.ok().body(voteService.check(postId, email));
    }
}
