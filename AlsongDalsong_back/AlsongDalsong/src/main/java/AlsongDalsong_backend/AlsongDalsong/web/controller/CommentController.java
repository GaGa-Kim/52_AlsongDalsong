package AlsongDalsong_backend.AlsongDalsong.web.controller;

import static AlsongDalsong_backend.AlsongDalsong.constants.Message.INPUT_COMMENT_ID;
import static AlsongDalsong_backend.AlsongDalsong.constants.Message.INPUT_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.constants.Message.INPUT_POST_ID;

import AlsongDalsong_backend.AlsongDalsong.service.comment.CommentService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentUpdateRequestDto;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 댓글 컨트롤러
 */
@Api(tags = {"Comment API (댓글 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    @ApiOperation(value = "게시글에 댓글 작성", notes = "게시글에 댓글을 작성한 후, 게시글의 댓글 목록을 리턴합니다.")
    @ApiImplicitParam(name = "commentSaveRequestDto", value = "댓글 작성 정보", required = true)
    public ResponseEntity<List<CommentResponseDto>> commentAdd(@RequestBody @Valid CommentSaveRequestDto commentSaveRequestDto) {
        return ResponseEntity.ok().body(commentService.addComment(commentSaveRequestDto));
    }

    @GetMapping("/inquire")
    @ApiOperation(value = "게시글별 댓글 조회", notes = "게시글 id에 따라 게시글별 댓글 목록을 리턴합니다.")
    @ApiImplicitParam(name = "postId", value = "게시글 id", example = "1")
    public ResponseEntity<List<CommentResponseDto>> commentList(@RequestParam @NotNull(message = INPUT_POST_ID) Long postId) {
        return ResponseEntity.ok().body(commentService.findPostCommentsByLikes(postId));
    }

    @PutMapping("/update")
    @ApiOperation(value = "게시글의 댓글 수정", notes = "게시글의 댓글을 수정한 후, 게시글의 댓글 목록을 리턴합니다.")
    @ApiImplicitParam(name = "commentUpdateRequestDto", value = "댓글 수정 정보", required = true)
    public ResponseEntity<List<CommentResponseDto>> commentModify(@RequestBody @Valid CommentUpdateRequestDto commentUpdateRequestDto) {
        return ResponseEntity.ok().body(commentService.modifyComment(commentUpdateRequestDto));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "게시글의 댓글 삭제", notes = "게시글의 댓글을 삭제한 후, true를 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "댓글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "댓글 작성자 이메일", example = "1234@gmail.com"),
    })
    public ResponseEntity<Boolean> commentRemove(@RequestParam @NotNull(message = INPUT_COMMENT_ID) Long id,
                                                 @RequestParam @Email(message = INPUT_EMAIL) String email) {
        return ResponseEntity.ok().body(commentService.removeComment(id, email));
    }
}
