package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.service.vote.VoteService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.vote.VoteRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 투표 컨트롤러
 */
@Api(tags = {"Vote API (투표 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class VoteController {
    private final VoteService voteService;

    // 게시글 투표하기
    @PostMapping("/api/vote/save")
    @ApiOperation(value = "게시글 투표하기", notes = "게시글에 투표/변경/취소를 한 후, 투표가 찬성이면 true, 반대면 false가 리턴합니다. 투표를 취소할 경우에는 '투표하지 않았습니다.'를 리턴합니다.")
    @ApiImplicitParam(name = "voteRequestDto", value = "투표 작성 정보", required = true)
    public ResponseEntity<String> voteSave(@RequestBody VoteRequestDto voteRequestDto) {
        return ResponseEntity.ok().body(voteService.saveVote(voteRequestDto));
    }

    // 사용자별 게시글에 따른 투표 여부 조회
    @PostMapping("/api/vote/check")
    @ApiOperation(value = "사용자별 게시글에 따른 투표 여부 조회", notes = "사용자별로 게시글 투표를 조회합니다. 투표가 찬성이면 true, 반대면 false가 리턴되며, 투표를 하지 않았을 경우에는 '투표하지 않았습니다.'를 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "게시글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com")
    })
    public ResponseEntity<String> voteDetails(@RequestParam Long postId, String email) {
        return ResponseEntity.ok().body(voteService.findVote(postId, email));
    }
}
