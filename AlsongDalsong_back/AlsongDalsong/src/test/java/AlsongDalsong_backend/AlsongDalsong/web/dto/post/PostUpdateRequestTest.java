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
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_TODO;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_WHAT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_WHO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * PostSaveUpdate 검증 테스트
 */
class PostUpdateRequestTest {
    private final ValidatorUtil<PostUpdateRequestDto> validatorUtil = new ValidatorUtil<>();
    private PostUpdateRequestVO postUpdateRequestVO;

    @BeforeEach
    void setUp() {
        postUpdateRequestVO = PostUpdateRequestVO.builder()
                .id(VALID_POST_ID)
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
                .deleteId(null)
                .build();
    }

    @Test
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
        assertEquals(postUpdateRequestVO.getContent(), postUpdateRequestDto.getContent());
        assertEquals(postUpdateRequestVO.getLink(), postUpdateRequestDto.getLink());
        assertEquals(postUpdateRequestVO.getImportance(), postUpdateRequestDto.getImportance());
    }

    @Test
    void postId_validation() {
        postUpdateRequestVO.setId(null);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    void email_validation() {
        postUpdateRequestVO.setEmail(INVALID_EMAIL);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    void todo_validation() {
        postUpdateRequestVO.setTodo(INVALID_BLANK);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    void category_validation() {
        postUpdateRequestVO.setCategory(INVALID_BLANK);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    void who_validation() {
        postUpdateRequestVO.setWho(INVALID_BLANK);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    void old_validation() {
        postUpdateRequestVO.setOld(INVALID_BLANK);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    void date_validation() {
        postUpdateRequestVO.setDate(INVALID_BLANK);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    void what_validation() {
        postUpdateRequestVO.setWhat(INVALID_BLANK);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
    void content_validation() {
        postUpdateRequestVO.setContent(INVALID_BLANK);
        parameter_validate(postUpdateRequestVO);
    }

    @Test
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