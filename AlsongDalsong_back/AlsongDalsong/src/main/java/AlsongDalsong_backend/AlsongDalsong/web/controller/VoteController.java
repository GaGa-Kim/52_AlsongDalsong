package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.service.VoteService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.vote.VoteRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    // 투표하기
    @PostMapping("/api/vote")
    @ApiOperation(value = "게시글 투표하기 (투표, 변경, 취소)", notes = "게시글 투표하기 API")
    public ResponseEntity<Boolean> vote(@RequestBody VoteRequestDto voteRequestDto) {
        return ResponseEntity.ok().body(voteService.vote(voteRequestDto));
    }

}
