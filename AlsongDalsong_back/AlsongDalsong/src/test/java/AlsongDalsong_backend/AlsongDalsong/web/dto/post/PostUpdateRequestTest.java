package AlsongDalsong_backend.AlsongDalsong.web.dto.post;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_BLANK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.ValidatorUtil;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * PostSaveUpdate 검증 테스트
 */
class PostUpdateRequestTest {
    private final ValidatorUtil<PostUpdateRequestDto> validatorUtil = new ValidatorUtil<>();
    private PostUpdateRequestVO postUpdateRequestVO;

    @BeforeEach
    void setUp() {
        Post post = TestObjectFactory.initPost();
        post.setUser(TestObjectFactory.initUser());
        postUpdateRequestVO = TestObjectFactory.initPostUpdateRequestVO(post);
    }

    @Test
    @DisplayName("PostUpdateRequest 생성 테스트")
    void testPostUpdateRequestDto() {
        PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto.builder()
                .postUpdateRequestVO(postUpdateRequestVO)
                .build();

        assertEquals(postUpdateRequestVO.getEmail(), postUpdateRequestDto.getEmail());
        assertEquals(postUpdateRequestVO.getTodo(), postUpdateRequestDto.getTodo());
        assertEquals(postUpdateRequestVO.getCategory(), postUpdateRequestDto.getCategory());
        assertEquals(postUpdateRequestVO.getWho(), postUpdateRequestDto.getWho());
        assertEquals(postUpdateRequestVO.getOld(), postUpdateRequestDto.getOld());
        assertEquals(postUpdateRequestVO.getDate(), postUpdateRequestDto.getDate());
        assertEquals(postUpdateRequestVO.getWhat(), postUpdateRequestDto.getWhat());
        assertEquals(postUpdateRequestVO.getContent(), postUpdateRequestDto.getContent());
        assertEquals(postUpdateRequestVO.getLink(), postUpdateRequestDto.getLink());
        assertEquals(postUpdateRequestVO.getImportance(), postUpdateRequestDto.getImportance());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        PostUpdateRequestDto postUpdateRequestDto = new PostUpdateRequestDto();

        assertNotNull(postUpdateRequestDto);
        assertNull(postUpdateRequestDto.getEmail());
        assertNull(postUpdateRequestDto.getTodo());
        assertNull(postUpdateRequestDto.getCategory());
        assertNull(postUpdateRequestDto.getWho());
        assertNull(postUpdateRequestDto.getOld());
        assertNull(postUpdateRequestDto.getDate());
        assertNull(postUpdateRequestDto.getWhat());
        assertNull(postUpdateRequestDto.getContent());
        assertNull(postUpdateRequestDto.getLink());
        assertNull(postUpdateRequestDto.getImportance());
    }

    @Test
    @DisplayName("게시글 아이디 유효성 검증 테스트")
    void postId_validation() {
        postUpdateRequestVO.setId(null);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    @DisplayName("이메일 유효성 검증 테스트")
    void email_validation() {
        postUpdateRequestVO.setEmail(INVALID_EMAIL);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    @DisplayName("분류 유효성 검증 테스트")
    void todo_validation() {
        postUpdateRequestVO.setTodo(INVALID_BLANK);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    @DisplayName("카테고리 유효성 검증 테스트")
    void category_validation() {
        postUpdateRequestVO.setCategory(INVALID_BLANK);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    @DisplayName("누가 유효성 검증 테스트")
    void who_validation() {
        postUpdateRequestVO.setWho(INVALID_BLANK);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    @DisplayName("연령 유효성 검증 테스트")
    void old_validation() {
        postUpdateRequestVO.setOld(INVALID_BLANK);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    @DisplayName("날짜 유효성 검증 테스트")
    void date_validation() {
        postUpdateRequestVO.setDate(INVALID_BLANK);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    @DisplayName("무엇을 유효성 검증 테스트")
    void what_validation() {
        postUpdateRequestVO.setWhat(INVALID_BLANK);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    @DisplayName("게시글 내용 유효성 검증 테스트")
    void content_validation() {
        postUpdateRequestVO.setContent(INVALID_BLANK);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    @DisplayName("중요도 유효성 검증 테스트")
    void importance_validation() {
        postUpdateRequestVO.setImportance(null);
        parameter_validate(postUpdateRequestVO);
    }

    void parameter_validate(PostUpdateRequestVO postUpdateRequestVO) {
        PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto.builder()
                .postUpdateRequestVO(postUpdateRequestVO)
                .build();

        validatorUtil.validate(postUpdateRequestDto);
    }
}