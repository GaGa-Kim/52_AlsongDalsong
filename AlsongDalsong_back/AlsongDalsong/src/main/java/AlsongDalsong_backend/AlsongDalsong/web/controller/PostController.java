package AlsongDalsong_backend.AlsongDalsong.web.controller;

import AlsongDalsong_backend.AlsongDalsong.service.post.PostService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostSaveRequestVO;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostUpdateRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostUpdateRequestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 게시글 컨트롤러
 */
@Api(tags = {"Post API (게시글 API)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {
    private final PostService postService;

    // 게시글 작성
    @PostMapping(value = "/api/post/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성한 후, 작성한 게시글을 리턴합니다.")
    public ResponseEntity<PostResponseDto> postAdd(PostSaveRequestVO postSaveRequestVo) {
        PostSaveRequestDto postSaveRequestDto = converToPostSaveRequestDto(postSaveRequestVo);
        return ResponseEntity.ok()
                .body(postService.addPostWithPhotos(postSaveRequestDto, postSaveRequestVo.getPhotos()));
    }

    // 게시글 상세 조회
    @GetMapping("/api/post/inquire")
    @ApiOperation(value = "게시글 상세 조회", notes = "게시글 id에 따라 게시글을 상세 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    public ResponseEntity<PostResponseDto> postDetails(Long id) {
        return ResponseEntity.ok().body(postService.findPostByPostId(id));
    }

    // 살까 말까 / 할까 말까 / 갈까 말까로 분류별 최신글 조회
    @GetMapping("/api/post/inquireLatest")
    @ApiOperation(value = "살까 말까 / 할까 말까 / 갈까 말까로 분류별 최신글 조회", notes = "살까 말까 / 할까 말까 / 갈까 말까로 분류별 최신글 목록을 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "todo", value = "분류", example = "살까 말까")
    public ResponseEntity<List<PostResponseDto>> postLatestList(String todo) {
        return ResponseEntity.ok().body(postService.findLatestPosts(todo));
    }

    // 살까 말까 / 할까 말까 / 갈까 말까로 분류별 인기글 조회
    @GetMapping("/api/post/inquirePopular")
    @ApiOperation(value = "살까 말까 / 할까 말까 / 갈까 말까로 분류별 인기글 조회", notes = "살까 말까 / 할까 말까 / 갈까 말까로 분류별 인기글 목록을 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "todo", value = "분류", example = "살까 말까")
    public ResponseEntity<List<PostResponseDto>> postPopularList(String todo) {
        return ResponseEntity.ok().body(postService.findPopularPosts(todo));
    }

    // 분류의 카테고리별 조회
    @GetMapping("/api/post/inquireCategory")
    @ApiOperation(value = "분류의 카테고리별 조회", notes = "분류의 카테고리별로 게시글 목록을 조회하여 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "todo", value = "분류", example = "살까 말까"),
            @ApiImplicitParam(name = "category", value = "카테고리", example = "패션")
    })
    public ResponseEntity<List<PostResponseDto>> postCategoryList(String todo, String category) {
        return ResponseEntity.ok().body(postService.findPostsByCategory(todo, category));
    }

    // 사용자별 쓴 글 조회
    @GetMapping("/api/post/my")
    @ApiOperation(value = "사용자별 쓴 글 조회", notes = "사용자별 쓴 글 목록을 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com")
    public ResponseEntity<List<PostResponseDto>> postUserList(String email) {
        return ResponseEntity.ok().body(postService.findUserPosts(email));
    }

    // 게시글 수정
    @PutMapping(value = "/api/post/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한 후, 수정한 게시글을 리턴합니다.")
    public ResponseEntity<PostResponseDto> postModify(PostUpdateRequestVO postUpdateRequestVO) {
        PostUpdateRequestDto postUpdateRequestDto = convertToPostUpdateRequestDto(postUpdateRequestVO);
        return ResponseEntity.ok()
                .body(postService.modifyPost(postUpdateRequestDto, postUpdateRequestVO.getPhotos(),
                        postUpdateRequestVO.getDeleteId()));
    }

    // 게시글 삭제
    @DeleteMapping("/api/post/delete")
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한 후, true를 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com"),
    })
    public ResponseEntity<Boolean> postRemove(@RequestParam Long id, String email) {
        return ResponseEntity.ok().body(postService.removePost(id, email));
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
    public ResponseEntity<PostResponseDto> postModifyDecision(@RequestParam Long id, String email,
                                                              String decision, String reason) {
        return ResponseEntity.ok().body(postService.modifyPostDecision(id, email, decision, reason));
    }

    // 저장 정보 변환
    private PostSaveRequestDto converToPostSaveRequestDto(PostSaveRequestVO postSaveRequestVO) {
        return PostSaveRequestDto.builder()
                .email(postSaveRequestVO.getEmail())
                .todo(postSaveRequestVO.getTodo())
                .category(postSaveRequestVO.getCategory())
                .who(postSaveRequestVO.getWho())
                .old(postSaveRequestVO.getOld())
                .date(postSaveRequestVO.getDate())
                .what(postSaveRequestVO.getWhat())
                .content(postSaveRequestVO.getContent())
                .link(postSaveRequestVO.getLink())
                .importance(postSaveRequestVO.getImportance())
                .build();
    }

    // 수정 정보 변환
    private PostUpdateRequestDto convertToPostUpdateRequestDto(PostUpdateRequestVO postUpdateRequestVO) {
        return PostUpdateRequestDto.builder()
                .id(postUpdateRequestVO.getId())
                .email(postUpdateRequestVO.getEmail())
                .todo(postUpdateRequestVO.getTodo())
                .category(postUpdateRequestVO.getCategory())
                .who(postUpdateRequestVO.getWho())
                .old(postUpdateRequestVO.getOld())
                .date(postUpdateRequestVO.getDate())
                .what(postUpdateRequestVO.getWhat())
                .content(postUpdateRequestVO.getContent())
                .link(postUpdateRequestVO.getLink())
                .importance(postUpdateRequestVO.getImportance())
                .build();
    }
}
