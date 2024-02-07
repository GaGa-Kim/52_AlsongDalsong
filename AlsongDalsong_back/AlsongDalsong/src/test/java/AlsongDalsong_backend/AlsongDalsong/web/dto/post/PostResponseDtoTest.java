package AlsongDalsong_backend.AlsongDalsong.web.dto.post;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_CATEGORY;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_DATE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_DECISION;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_IMPORTANCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_INTRODUCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_KAKAO_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_LINK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NICKNAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_OLD;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_CONTENT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PROFILE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_REASON;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_TODO;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_WHAT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_WHO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.domain.Time;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

/**
 * PostResponseDto 검증 테스트
 */
class PostResponseDtoTest {
    private User user;
    private List<Long> photoId;
    private Pair<Long, Long> vote;

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
        photoId = List.of(1L);
        vote = Pair.of(1L, 1L);
    }

    @Test
    void
    testPostResponseDto() {
        Post post = Post.builder()
                .todo(VALID_TODO)
                .category(VALID_CATEGORY)
                .who(VALID_WHO)
                .old(VALID_OLD)
                .date(VALID_DATE)
                .what(VALID_WHAT)
                .content(VALID_POST_CONTENT)
                .link(VALID_LINK)
                .importance(VALID_IMPORTANCE)
                .decision(VALID_DECISION)
                .reason(VALID_REASON)
                .build();
        post.setUser(user);

        PostResponseDto postResponseDto = new PostResponseDto(post, photoId, vote);

        assertEquals(post.getId(), postResponseDto.getId());
        assertEquals(Time.calculateTime(post.getCreatedDateTime()), postResponseDto.getCreatedDateTime());
        assertEquals(user.getEmail(), postResponseDto.getEmail());
        assertEquals(user.getNickname(), postResponseDto.getNickname());
        assertEquals(post.getTodo().getTodo(), postResponseDto.getTodo());
        assertEquals(post.getCategory().getCategory(), postResponseDto.getCategory());
        assertEquals(post.getWho().getWho(), postResponseDto.getWho());
        assertEquals(post.getOld().getOld(), postResponseDto.getOld());
        assertEquals(post.getDate(), postResponseDto.getDate());
        assertEquals(post.getWhat(), postResponseDto.getWhat());
        assertEquals(post.getContent(), postResponseDto.getContent());
        assertEquals(post.getLink(), postResponseDto.getLink());
        assertEquals(post.getImportance(), postResponseDto.getImportance());
        assertEquals(post.getDecision().getDecision(), postResponseDto.getDecision());
        assertEquals(post.getReason(), postResponseDto.getReason());
        assertEquals(photoId, postResponseDto.getPhotoId());
        assertEquals(vote.getFirst(), postResponseDto.getAgree());
        assertEquals(vote.getSecond(), postResponseDto.getDisagree());
        assertEquals(0, postResponseDto.getComment());
        assertEquals(0, postResponseDto.getScrap());

    }
}