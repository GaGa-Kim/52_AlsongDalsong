package AlsongDalsong_backend.AlsongDalsong;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Category;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Decision;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Old;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Todo;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Who;

/**
 * 테스트 상수
 */
public class TestConstants {
    // User Valid Test Constants
    public static final String VALID_KAKAO_CODE = "code";
    public static final Long VALID_USER_ID = 1L;
    public static final Long VALID_KAKAO_ID = 1L;
    public static final String VALID_NAME = "이름";
    public static final String VALID_EMAIL = "1234@gmail.com";
    public static final String VALID_NICKNAME = "닉네임";
    public static final String VALID_PROFILE = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
    public static final String VALID_INTRODUCE = "소개";
    public static final String VALID_TOKEN = "code";

    // Post Valid Test Constants
    public static final Long VALID_POST_ID = 1L;
    public static final Todo VALID_TODO = Todo.TO_BUY_OR_NOT_TO_BUY;
    public static final Category VALID_CATEGORY = Category.FASHION;
    public static final Who VALID_WHO = Who.WOMAN;
    public static final Old VALID_OLD = Old.TWENTIES;
    public static final String VALID_DATE = "언제";
    public static final String VALID_WHAT = "무엇을";
    public static final String VALID_POST_CONTENT = "내용";
    public static final String VALID_LINK = "링크";
    public static final Integer VALID_IMPORTANCE = 3;
    public static final Decision VALID_DECISION = Decision.UNDECIDED;
    public static final String VALID_REASON = "결정 이유";

    // Photo Valid Test Constants
    public static final Long VALID_PHOTO_ID = 1L;
    public static final String VALID_ORIG_PHOTO_NANE = "testOriginal.png";
    public static final String VALID_PHOTO_NAME = "test.png";
    public static final String VALID_PHOTO_URL = "사진 URL";

    // Comment Valid Test Constants
    public static final Long VALID_COMMENT_ID = 1L;
    public static final String VALID_COMMENT_CONTENT = "댓글 내용";

    // Like Valid Test Constants
    public static final Long VALID_LIKE_ID = 1L;

    // Vote Valid Test Constants
    public static final Long VALID_VOTE_ID = 1L;
    public static final Boolean VALID_VOTE = true;

    // Scrap Valid Test Constants
    public static final Long VALID_SCRAP_ID = 1L;

    // Invalid Test Constants
    public static final String INVALID_BLANK = "";
    public static final String INVALID_EMAIL = "1234.gmail.com";
}
