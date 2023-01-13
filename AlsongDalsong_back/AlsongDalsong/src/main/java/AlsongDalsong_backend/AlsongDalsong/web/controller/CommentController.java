package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.service.CommentService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentUpdateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 댓글 컨트롤러
 */
@Api(tags={"Comment API (댓글 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {

    @Autowired
    private final CommentService commentService;
    
    // 댓글 저장
    @PostMapping("/api/comment/save")
    @ApiOperation(value = "댓글 작성", notes = "댓글 작성 API")
    public ResponseEntity<List<CommentResponseDto>> save(@RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        return ResponseEntity.ok().body(commentService.save(commentSaveRequestDto));
    }

    // 댓글 조회
    @GetMapping("/api/comment/inquire")
    @ApiOperation(value = "댓글 조회", notes = "댓글 조회 API")
    @ApiImplicitParam(name = "postId", value = "게시글 id", example = "1")
    public ResponseEntity<List<CommentResponseDto>> inquire(@RequestParam Long postId) {
        return ResponseEntity.ok().body(commentService.inquire(postId));
    }

    // 댓글 수정
    @PutMapping("/api/comment/update")
    @ApiOperation(value = "댓글 수정", notes = "댓글 수정 API")
    public ResponseEntity<List<CommentResponseDto>> update(@RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        return ResponseEntity.ok().body(commentService.update(commentUpdateRequestDto));
    }

    // 댓글 삭제
    @DeleteMapping("/api/comment/delete")
    @ApiOperation(value = "댓글 삭제", notes = "댓글 삭제 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "댓글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "댓글 작성자 이메일", example = "1234@gmail.com"),
    })
    public ResponseEntity<Boolean> delete(@RequestParam Long id, String email) {
        return ResponseEntity.ok().body(commentService.delete(id, email));
    }
    
}
