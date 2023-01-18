package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.service.PostService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostSaveRequestVO;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostUpdateRequestVO;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostUpdateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 게시글 컨트롤러
 */
@Api(tags={"Post API (게시글 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    @Autowired
    private final PostService postService;

    // 게시글 작성
    @PostMapping(value = "/api/post/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "게시글 작성", notes = "게시글 작성 API")
    public ResponseEntity<PostResponseDto> save(PostSaveRequestVO postSaveRequestVo) {
        PostSaveRequestDto postSaveRequestDto = PostSaveRequestDto
                .builder()
                .email(postSaveRequestVo.getEmail())
                .todo(postSaveRequestVo.getTodo())
                .category(postSaveRequestVo.getCategory())
                .who(postSaveRequestVo.getWho())
                .old(postSaveRequestVo.getOld())
                .date(postSaveRequestVo.getDate())
                .what(postSaveRequestVo.getWhat())
                .what(postSaveRequestVo.getWhat())
                .content(postSaveRequestVo.getContent())
                .link(postSaveRequestVo.getLink())
                .importance(postSaveRequestVo.getImportance())
                .build();
        return ResponseEntity.ok().body(postService.save(postSaveRequestDto, postSaveRequestVo.getPhotos()));
    }

    // 게시글 조회
    @GetMapping("/api/post/inquire")
    @ApiOperation(value = "게시글 조회", notes = "게시글 조회 API")
    @ApiImplicitParam(name = "postId", value = "게시글 id", example = "1")
    public ResponseEntity<PostResponseDto> inquire(Long postId) {
        return ResponseEntity.ok().body(postService.inquire(postId));
    }

    // 게시글 수정
    @PutMapping(value = "/api/post/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "게시글 수정", notes = "게시글 수정 API")
    public ResponseEntity<PostResponseDto> update(PostUpdateRequestVO postUpdateRequestVO) {
        PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto
                .builder()
                .id(postUpdateRequestVO.getId())
                .email(postUpdateRequestVO.getEmail())
                .todo(postUpdateRequestVO.getTodo())
                .category(postUpdateRequestVO.getCategory())
                .who(postUpdateRequestVO.getWho())
                .old(postUpdateRequestVO.getOld())
                .date(postUpdateRequestVO.getDate())
                .what(postUpdateRequestVO.getWhat())
                .what(postUpdateRequestVO.getWhat())
                .content(postUpdateRequestVO.getContent())
                .link(postUpdateRequestVO.getLink())
                .importance(postUpdateRequestVO.getImportance())
                .build();
        return ResponseEntity.ok().body(postService.update(postUpdateRequestDto, postUpdateRequestVO.getPhotos(), postUpdateRequestVO.getDeleteId()));
    }

    // 게시글 삭제
    @DeleteMapping("/api/post/delete")
    @ApiOperation(value = "게시글 삭제", notes = "게시글 삭제 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com"),
    })
    public ResponseEntity<Boolean> delete(@RequestParam Long id, String email) {
        return ResponseEntity.ok().body(postService.delete(id, email));
    }

    // 게시글 확정
    @PutMapping(value = "/api/post/updateDecision")
    @ApiOperation(value = "게시글 확정", notes = "게시글 확정 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com"),
            @ApiImplicitParam(name = "decision", value = "결정 완료", example = "취소"),
            @ApiImplicitParam(name = "reason", value = "결정 이유", example = "비싸서")
    })
    public ResponseEntity<PostResponseDto> updateDecision(@RequestParam Long id, String email, String decision, String reason) {
        return ResponseEntity.ok().body(postService.updateDecision(id, email, decision, reason));
    }
}
