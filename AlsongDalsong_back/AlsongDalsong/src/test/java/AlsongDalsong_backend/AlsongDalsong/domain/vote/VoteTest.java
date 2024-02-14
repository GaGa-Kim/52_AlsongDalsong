package AlsongDalsong_backend.AlsongDalsong.domain.vote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 투표 테이블 테스트
 */
class VoteTest {
    private Vote vote;

    @BeforeEach
    void setUp() {
        vote = TestObjectFactory.initVote();
    }

    @Test
    @DisplayName("투표 수정 테스트")
    void testUpdate() {
        boolean reVote = false;
        vote.update(reVote);

        assertEquals(reVote, vote.getVote());
    }

    @Test
    @DisplayName("작성한 투표의 작성자 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        vote.setUser(user);

        assertEquals(vote.getUserId(), user);
    }

    @Test
    @DisplayName("작성한 투표의 게시글 연관관계 설정 테스트")
    void testSetPost() {
        Post post = mock(Post.class);
        vote.setPost(post);

        assertEquals(vote.getPostId(), post);
        verify(post, times(2)).getVoteList();
    }
}