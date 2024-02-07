package AlsongDalsong_backend.AlsongDalsong.web.dto.comment;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_COMMENT_CONTENT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_INTRODUCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_KAKAO_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NICKNAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PROFILE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.domain.Time;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * CommentResponseDto 검증 테스트
 */
class CommentResponseDtoTest {
    private User user;
    private Post post;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .kakaoId(VALID_KAKAO_ID)
                .name(VALID_NAME)
                .email(VALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();
        post = new Post();
    }

    @Test
    void testCommentResponseDtoTest() {
        Comment comment = Comment.builder().content(VALID_COMMENT_CONTENT).build();
        comment.setUser(user);
        comment.setPost(post);

        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);

        assertEquals(comment.getId(), commentResponseDto.getId());
        assertEquals(Time.calculateTime(comment.getCreatedDateTime()), commentResponseDto.getCreatedDateTime());
        assertEquals(comment.getContent(), commentResponseDto.getContent());
        assertEquals(comment.getLikeList().size(), commentResponseDto.getLike());
    }
}