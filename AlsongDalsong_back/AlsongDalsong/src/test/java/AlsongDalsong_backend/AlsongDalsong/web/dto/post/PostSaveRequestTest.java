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
 * PostSaveRequest 검증 테스트
 */
class PostSaveRequestTest {
    private final ValidatorUtil<PostSaveRequestDto> validatorUtil = new ValidatorUtil<>();
    private PostSaveRequestVO postSaveRequestVO;

    @BeforeEach
    void setUp() {
        Post post = TestObjectFactory.initPost();
        post.setUser(TestObjectFactory.initUser());
        postSaveRequestVO = TestObjectFactory.initPostSaveRequestVO(post);
    }

    @Test
    @DisplayName("PostSaveRequest 생성 테스트")
    void testPostSaveRequestDto() {
        PostSaveRequestDto postSaveRequestDto = PostSaveRequestDto.builder()
                .postSaveRequestVO(postSaveRequestVO)
                .build();

        assertEquals(postSaveRequestVO.getEmail(), postSaveRequestDto.getEmail());
        assertEquals(postSaveRequestVO.getTodo(), postSaveRequestDto.getTodo());
        assertEquals(postSaveRequestVO.getCategory(), postSaveRequestDto.getCategory());
        assertEquals(postSaveRequestVO.getWho(), postSaveRequestDto.getWho());
        assertEquals(postSaveRequestVO.getOld(), postSaveRequestDto.getOld());
        assertEquals(postSaveRequestVO.getDate(), postSaveRequestDto.getDate());
        assertEquals(postSaveRequestVO.getWhat(), postSaveRequestDto.getWhat());
        assertEquals(postSaveRequestVO.getContent(), postSaveRequestDto.getContent());
        assertEquals(postSaveRequestVO.getLink(), postSaveRequestDto.getLink());
        assertEquals(postSaveRequestVO.getImportance(), postSaveRequestDto.getImportance());
    }

    @Test
    @DisplayName("PostSaveRequest toEntity 생성 테스트")
    void testPostSaveRequestDtoEntity() {
        PostSaveRequestDto postSaveRequestDto = PostSaveRequestDto.builder()
                .postSaveRequestVO(postSaveRequestVO)
                .build();

        Post post = postSaveRequestDto.toEntity();
        post.setUser(TestObjectFactory.initUser());

        assertEquals(postSaveRequestVO.getEmail(), post.getUserId().getEmail());
        assertEquals(postSaveRequestVO.getTodo(), post.getTodo().getTodo());
        assertEquals(postSaveRequestVO.getCategory(), post.getCategory().getCategory());
        assertEquals(postSaveRequestVO.getWho(), post.getWho().getWho());
        assertEquals(postSaveRequestVO.getOld(), post.getOld().getOld());
        assertEquals(postSaveRequestVO.getDate(), post.getDate());
        assertEquals(postSaveRequestVO.getWhat(), post.getWhat());
        assertEquals(postSaveRequestVO.getContent(), post.getContent());
        assertEquals(postSaveRequestVO.getLink(), post.getLink());
        assertEquals(postSaveRequestVO.getImportance(), post.getImportance());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        PostSaveRequestDto postSaveRequestDto = new PostSaveRequestDto();

        assertNotNull(postSaveRequestDto);
        assertNull(postSaveRequestDto.getEmail());
        assertNull(postSaveRequestDto.getTodo());
        assertNull(postSaveRequestDto.getCategory());
        assertNull(postSaveRequestDto.getWho());
        assertNull(postSaveRequestDto.getOld());
        assertNull(postSaveRequestDto.getDate());
        assertNull(postSaveRequestDto.getWhat());
        assertNull(postSaveRequestDto.getContent());
        assertNull(postSaveRequestDto.getLink());
        assertNull(postSaveRequestDto.getImportance());
    }

    @Test
    @DisplayName("이메일 유효성 검증 테스트")
    void email_validation() {
        postSaveRequestVO.setEmail(INVALID_EMAIL);
        parameter_validate(postSaveRequestVO);
    }

    @Test
    @DisplayName("분류 유효성 검증 테스트")
    void todo_validation() {
        postSaveRequestVO.setTodo(INVALID_BLANK);
        parameter_validate(postSaveRequestVO);
    }

    @Test
    @DisplayName("카테고리 유효성 검증 테스트")
    void category_validation() {
        postSaveRequestVO.setCategory(INVALID_BLANK);
        parameter_validate(postSaveRequestVO);
    }

    @Test
    @DisplayName("누가 유효성 검증 테스트")
    void who_validation() {
        postSaveRequestVO.setWho(INVALID_BLANK);
        parameter_validate(postSaveRequestVO);
    }

    @Test
    @DisplayName("연령 유효성 검증 테스트")
    void old_validation() {
        postSaveRequestVO.setOld(INVALID_BLANK);
        parameter_validate(postSaveRequestVO);
    }

    @Test
    @DisplayName("날짜 유효성 검증 테스트")
    void date_validation() {
        postSaveRequestVO.setDate(INVALID_BLANK);
        parameter_validate(postSaveRequestVO);
    }

    @Test
    @DisplayName("무엇을 유효성 검증 테스트")
    void what_validation() {
        postSaveRequestVO.setWhat(INVALID_BLANK);
        parameter_validate(postSaveRequestVO);
    }

    @Test
    @DisplayName("게시글 내용 유효성 검증 테스트")
    void content_validation() {
        postSaveRequestVO.setContent(INVALID_BLANK);
        parameter_validate(postSaveRequestVO);
    }

    @Test
    @DisplayName("중요도 유효성 검증 테스트")
    void importance_validation() {
        postSaveRequestVO.setImportance(null);
        parameter_validate(postSaveRequestVO);
    }

    void parameter_validate(PostSaveRequestVO postSaveRequestVO) {
        PostSaveRequestDto postSaveRequestDto = PostSaveRequestDto.builder()
                .postSaveRequestVO(postSaveRequestVO)
                .build();

        validatorUtil.validate(postSaveRequestDto);
    }
}