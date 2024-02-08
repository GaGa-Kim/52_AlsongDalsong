package AlsongDalsong_backend.AlsongDalsong.web.dto.post;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_BLANK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_CATEGORY;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_DATE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_IMPORTANCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_LINK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_OLD;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_CONTENT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_TODO;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_WHAT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_WHO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * PostSaveRequest 검증 테스트
 */
class PostSaveRequestTest {
    private final ValidatorUtil<PostSaveRequestDto> validatorUtil = new ValidatorUtil<>();
    private PostSaveRequestVO postSaveRequestVO;

    @BeforeEach
    void setUp() {
        postSaveRequestVO = PostSaveRequestVO.builder()
                .email(VALID_EMAIL)
                .todo(VALID_TODO.getTodo())
                .category(VALID_CATEGORY.getCategory())
                .what(VALID_WHAT)
                .old(VALID_OLD.getOld())
                .date(VALID_DATE)
                .who(VALID_WHO.getWho())
                .content(VALID_POST_CONTENT)
                .link(VALID_LINK)
                .importance(VALID_IMPORTANCE)
                .photos(null)
                .build();
    }

    @Test
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
        assertEquals(postSaveRequestVO.getContent(), postSaveRequestDto.getContent());
        assertEquals(postSaveRequestVO.getLink(), postSaveRequestDto.getLink());
        assertEquals(postSaveRequestVO.getImportance(), postSaveRequestDto.getImportance());
    }

    @Test
    void email_validation() {
        postSaveRequestVO.setEmail(INVALID_EMAIL);
        parameter_validate(postSaveRequestVO);
    }

    @Test
    void todo_validation() {
        postSaveRequestVO.setTodo(INVALID_BLANK);
        parameter_validate(postSaveRequestVO);
    }

    @Test
    void category_validation() {
        postSaveRequestVO.setCategory(INVALID_BLANK);
        parameter_validate(postSaveRequestVO);
    }

    @Test
    void who_validation() {
        postSaveRequestVO.setWho(INVALID_BLANK);
        parameter_validate(postSaveRequestVO);
    }

    @Test
    void old_validation() {
        postSaveRequestVO.setOld(INVALID_BLANK);
        parameter_validate(postSaveRequestVO);
    }

    @Test
    void date_validation() {
        postSaveRequestVO.setDate(INVALID_BLANK);
        parameter_validate(postSaveRequestVO);
    }

    @Test
    void what_validation() {
        postSaveRequestVO.setWhat(INVALID_BLANK);
        parameter_validate(postSaveRequestVO);
    }

    @Test
    void content_validation() {
        postSaveRequestVO.setContent(INVALID_BLANK);
        parameter_validate(postSaveRequestVO);
    }

    @Test
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