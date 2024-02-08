package AlsongDalsong_backend.AlsongDalsong.domain.vote;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_VOTE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_VOTE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 투표 테이블 테스트
 */
class VoteTest {
    private Vote vote;

    @BeforeEach
    void setUp() {
        vote = Vote.builder()
                .id(VALID_VOTE_ID)
                .vote(VALID_VOTE)
                .build();
    }

    @Test
    void testUpdate() {
        boolean reVote = false;
        vote.update(reVote);

        assertEquals(reVote, vote.getVote());
    }

    @Test
    void testSetUser() {
        User user = mock(User.class);
        vote.setUser(user);

        assertEquals(vote.getUserId(), user);
    }

    @Test
    void testSetPost() {
        Post post = mock(Post.class);
        vote.setPost(post);

        assertEquals(vote.getPostId(), post);
        verify(post, times(2)).getVoteList();
    }
}