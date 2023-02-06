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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 게시글 컨트롤러
 */
@Api(tags={"Post API (게시글 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping(value = "/api/post/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성한 후, 작성한 게시글을 리턴합니다.")
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

    // 게시글 상세 조회
    @GetMapping("/api/post/inquire")
    @ApiOperation(value = "게시글 상세 조회", notes = "게시글 id에 따라 게시글을 상세 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    public ResponseEntity<PostResponseDto> inquire(Long id) {
        return ResponseEntity.ok().body(postService.inquire(id));
    }

    // 게시글 수정
    @PutMapping(value = "/api/post/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한 후, 수정한 게시글을 리턴합니다.")
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
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한 후, true를 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com"),
    })
    public ResponseEntity<Boolean> delete(@RequestParam Long id, String email) {
        return ResponseEntity.ok().body(postService.delete(id, email));
    }

    // 게시글 확정
    @PutMapping(value = "/api/post/updateDecision")
    @ApiOperation(value = "게시글 확정", notes = "결정 미정이었던 게시글을 결정/취소에 따라 게시글을 확정한 후, 확정된 게시글을 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com"),
            @ApiImplicitParam(name = "decision", value = "결정 완료 (미정/결정/취소)", example = "취소"),
            @ApiImplicitParam(name = "reason", value = "결정 이유", example = "비싸서")
    })
    public ResponseEntity<PostResponseDto> updateDecision(@RequestParam Long id, String email, String decision, String reason) {
        return ResponseEntity.ok().body(postService.updateDecision(id, email, decision, reason));
    }


    // 살까 말까 / 할까 말까 / 갈까 말까로 분류별 최신글 조회
    @GetMapping("/api/post/inquireLatest")
    @ApiOperation(value = "살까 말까 / 할까 말까 / 갈까 말까로 분류별 최신글 조회", notes = "살까 말까 / 할까 말까 / 갈까 말까로 분류별 최신글 목록을 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "todo", value = "분류", example = "살까 말까")
    public ResponseEntity<List<PostResponseDto>> inquireLatest(String todo) {
        return ResponseEntity.ok().body(postService.inquireLatest(todo));
    }

    // 살까 말까 / 할까 말까 / 갈까 말까로 분류별 인기글 조회
    @GetMapping("/api/post/inquirePopular")
    @ApiOperation(value = "살까 말까 / 할까 말까 / 갈까 말까로 분류별 인기글 조회", notes = "살까 말까 / 할까 말까 / 갈까 말까로 분류별 인기글 목록을 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "todo", value = "분류", example = "살까 말까")
    public ResponseEntity<List<PostResponseDto>> inquirePopular(String todo) {
        return ResponseEntity.ok().body(postService.inquirePopular(todo));
    }

    // 분류의 카테고리별 조회
    @GetMapping("/api/post/inquireCategory")
    @ApiOperation(value = "분류의 카테고리별 조회", notes = "분류의 카테고리별로 게시글 목록을 조회하여 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "todo", value = "분류", example = "살까 말까"),
            @ApiImplicitParam(name = "category", value = "카테고리", example = "패션")
    })    public ResponseEntity<List<PostResponseDto>> inquireCategory(String todo, String category) {
        return ResponseEntity.ok().body(postService.inquireCategory(todo, category));
    }

    // 사용자별 쓴 글 조회
    @GetMapping("/api/post/my")
    @ApiOperation(value = "사용자별 쓴 글 조회", notes = "사용자별 쓴 글 목록을 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com")
    public ResponseEntity<List<PostResponseDto>> my(String email) {
        return ResponseEntity.ok().body(postService.my(email));
    }
}
