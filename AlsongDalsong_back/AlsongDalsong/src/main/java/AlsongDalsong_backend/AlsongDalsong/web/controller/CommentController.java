package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.service.comment.CommentServiceImpl;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentUpdateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 댓글 컨트롤러
 */
@Api(tags = {"Comment API (댓글 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {

    private final CommentServiceImpl commentServiceImpl;

    // 게시글에 댓글 작성
    @PostMapping("/api/comment/save")
    @ApiOperation(value = "게시글에 댓글 작성", notes = "게시글에 댓글을 작성한 후, 게시글의 댓글 목록을 리턴합니다.")
    public ResponseEntity<List<CommentResponseDto>> save(@RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        return ResponseEntity.ok().body(commentServiceImpl.save(commentSaveRequestDto));
    }

    // 게시글별 댓글 조회
    @GetMapping("/api/comment/inquire")
    @ApiOperation(value = "게시글별 댓글 조회", notes = "게시글 id에 따라 게시글별 댓글 목록을 리턴합니다.")
    @ApiImplicitParam(name = "postId", value = "게시글 id", example = "1")
    public ResponseEntity<List<CommentResponseDto>> inquire(@RequestParam Long postId) {
        return ResponseEntity.ok().body(commentServiceImpl.inquire(postId));
    }

    // 게시글의 댓글 수정
    @PutMapping("/api/comment/update")
    @ApiOperation(value = "게시글의 댓글 수정", notes = "게시글의 댓글을 수정한 후, 게시글의 댓글 목록을 리턴합니다.")
    public ResponseEntity<List<CommentResponseDto>> update(
            @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        return ResponseEntity.ok().body(commentServiceImpl.update(commentUpdateRequestDto));
    }

    // 게시글의 댓글 삭제
    @DeleteMapping("/api/comment/delete")
    @ApiOperation(value = "게시글의 댓글 삭제", notes = "게시글의 댓글을 삭제한 후, true를 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "댓글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "댓글 작성자 이메일", example = "1234@gmail.com"),
    })
    public ResponseEntity<Boolean> delete(@RequestParam Long id, String email) {
        return ResponseEntity.ok().body(commentServiceImpl.delete(id, email));
    }

}
