package AlsongDalsong_backend.AlsongDalsong.web.controller;

import static AlsongDalsong_backend.AlsongDalsong.constants.Message.INPUT_EMAIL;

import AlsongDalsong_backend.AlsongDalsong.constants.Message;
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
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 게시글 컨트롤러
 */
@Api(tags = {"Post API (게시글 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성한 후, 작성한 게시글을 리턴합니다.")
    public ResponseEntity<PostResponseDto> postAdd(@Valid PostSaveRequestVO postSaveRequestVO) {
        PostSaveRequestDto postSaveRequestDto = new PostSaveRequestDto(postSaveRequestVO);
        return ResponseEntity.ok()
                .body(postService.addPostWithPhotos(postSaveRequestDto, postSaveRequestVO.getPhotos()));
    }

    @GetMapping("/inquire")
    @ApiOperation(value = "게시글 상세 조회", notes = "게시글 id에 따라 게시글을 상세 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    public ResponseEntity<PostResponseDto> postDetails(@RequestParam @NotNull(message = Message.INPUT_POST_ID) Long id) {
        return ResponseEntity.ok().body(postService.findPostDetailByPostId(id));
    }

    @GetMapping("/inquireLatest")
    @ApiOperation(value = "살까 말까 / 할까 말까 / 갈까 말까로 분류별 최신글 조회", notes = "살까 말까 / 할까 말까 / 갈까 말까로 분류별 최신글 목록을 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "todo", value = "분류", example = "살까 말까")
    public ResponseEntity<List<PostResponseDto>> postLatestList(@RequestParam @NotBlank(message = Message.INPUT_TODO) String todo) {
        return ResponseEntity.ok().body(postService.findLatestPosts(todo));
    }

    @GetMapping("/inquirePopular")
    @ApiOperation(value = "살까 말까 / 할까 말까 / 갈까 말까로 분류별 인기글 조회", notes = "살까 말까 / 할까 말까 / 갈까 말까로 분류별 인기글 목록을 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "todo", value = "분류", example = "살까 말까")
    public ResponseEntity<List<PostResponseDto>> postPopularList(@RequestParam @NotBlank(message = Message.INPUT_TODO) String todo) {
        return ResponseEntity.ok().body(postService.findPopularPosts(todo));
    }

    @GetMapping("/inquireCategory")
    @ApiOperation(value = "분류의 카테고리별 조회", notes = "분류의 카테고리별로 게시글 목록을 조회하여 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "todo", value = "분류", example = "살까 말까"),
            @ApiImplicitParam(name = "category", value = "카테고리", example = "패션")
    })
    public ResponseEntity<List<PostResponseDto>> postCategoryList(@RequestParam @NotBlank(message = Message.INPUT_TODO) String todo,
                                                                  @RequestParam @NotBlank(message = Message.INPUT_CATEGORY) String category) {
        return ResponseEntity.ok().body(postService.findPostsByCategory(todo, category));
    }

    @GetMapping("/my")
    @ApiOperation(value = "사용자별 쓴 글 조회", notes = "사용자별 쓴 글 목록을 조회하여 리턴합니다.")
    @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com")
    public ResponseEntity<List<PostResponseDto>> postUserList(@RequestParam @Email(message = INPUT_EMAIL) String email) {
        return ResponseEntity.ok().body(postService.findUserPosts(email));
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한 후, 수정한 게시글을 리턴합니다.")
    public ResponseEntity<PostResponseDto> postModify(@Valid PostUpdateRequestVO postUpdateRequestVO) {
        PostUpdateRequestDto postUpdateRequestDto = new PostUpdateRequestDto(postUpdateRequestVO);
        return ResponseEntity.ok()
                .body(postService.modifyPost(postUpdateRequestDto, postUpdateRequestVO.getPhotos(),
                        postUpdateRequestVO.getDeleteId()));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한 후, true를 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com"),
    })
    public ResponseEntity<Boolean> postRemove(@RequestParam @NotNull(message = Message.INPUT_POST_ID) Long id,
                                              @RequestParam @Email(message = INPUT_EMAIL) String email) {
        return ResponseEntity.ok().body(postService.removePost(id, email));
    }

    @PutMapping(value = "/updateDecision")
    @ApiOperation(value = "게시글 확정", notes = "결정 미정이었던 게시글을 결정/취소에 따라 게시글을 확정한 후, 확정된 게시글을 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "게시글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "이메일", example = "1234@gmail.com"),
            @ApiImplicitParam(name = "decision", value = "결정 완료 (미정/결정/취소)", example = "취소"),
            @ApiImplicitParam(name = "reason", value = "결정 이유", example = "비싸서")
    })
    public ResponseEntity<PostResponseDto> postModifyDecision(@RequestParam @NotNull(message = Message.INPUT_POST_ID) Long id,
                                                              @RequestParam @Email(message = INPUT_EMAIL) String email,
                                                              @RequestParam @NotBlank(message = Message.INPUT_DECISION) String decision,
                                                              @RequestParam @NotBlank(message = Message.INPUT_REASON) String reason) {
        return ResponseEntity.ok().body(postService.modifyPostDecision(id, email, decision, reason));
    }
}
