package AlsongDalsong_backend.AlsongDalsong.web.dto.post;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_BLANK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.ValidatorUtil;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
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
        User user = TestObjectFactory.initUser();
        Post post = TestObjectFactory.initPost();
        post.setUser(user);
        postUpdateRequestVO = TestObjectFactory.initPostUpdateRequestVO(post);
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