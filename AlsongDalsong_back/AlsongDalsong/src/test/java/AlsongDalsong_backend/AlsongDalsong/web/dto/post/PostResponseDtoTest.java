package AlsongDalsong_backend.AlsongDalsong.web.dto.post;

import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.Time;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

/**
 * PostResponseDto 검증 테스트
 */
class PostResponseDtoTest {
    private List<Long> photoId;
    private Pair<Long, Long> vote;

    @BeforeEach
    void setUp() {
        photoId = List.of(1L);
        vote = Pair.of(1L, 1L);
    }

    @Test
    void
    testPostResponseDto() {
        Post post = TestObjectFactory.initPost();
        post.setUser(TestObjectFactory.initUser());
        post.addPhotoList(TestObjectFactory.initPhoto());
        post.addCommentList(TestObjectFactory.initComment());
        post.addVoteList(TestObjectFactory.initVote());
        post.addScrapList(TestObjectFactory.initScrap());

        PostResponseDto postResponseDto = new PostResponseDto(post, photoId, vote);

        assertEquals(post.getId(), postResponseDto.getId());
        assertEquals(Time.calculateTime(post.getCreatedDateTime()), postResponseDto.getCreatedDateTime());
        assertEquals(post.getUserId().getEmail(), postResponseDto.getEmail());
        assertEquals(post.getUserId().getNickname(), postResponseDto.getNickname());
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
        assertEquals(1, postResponseDto.getComment());
        assertEquals(1, postResponseDto.getScrap());
    }
}