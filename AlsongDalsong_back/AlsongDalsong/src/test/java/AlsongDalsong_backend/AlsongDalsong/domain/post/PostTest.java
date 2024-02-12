package AlsongDalsong_backend.AlsongDalsong.domain.post;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_REASON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.Scrap;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.Vote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 게시글 테이블 테스트
 */
class PostTest {
    private Post post;

    @BeforeEach
    void setUp() {
        post = TestObjectFactory.initPost();
    }

    @Test
    void testUpdate() {
        String newContent = "신발을 새로 살까 말까요?";
        post.update(post.getTodo().getTodo(), post.getCategory().getCategory(),
                post.getWho().getWho(), post.getOld().getOld(), post.getDate(),
                post.getWhat(), newContent, post.getLink(), post.getImportance());

        assertEquals(newContent, post.getContent());
    }

    @Test
    void testSetDecision() {
        post.setDecision(Decision.DECIDED.getDecision(), VALID_REASON);

        assertEquals(Decision.DECIDED, post.getDecision());
        assertEquals(VALID_REASON, post.getReason());
    }

    @Test
    void testSetUser() {
        User user = mock(User.class);
        post.setUser(user);

        assertEquals(post.getUserId(), user);
        verify(user, times(2)).getPostList();
    }

    @Test
    void testAddPhotoList() {
        Photo photo = mock(Photo.class);
        post.addPhotoList(photo);

        assertTrue(post.getPhotoList().contains(photo));
        verify(photo, times(1)).setPost(post);
    }

    @Test
    void testAddCommentList() {
        Comment comment = mock(Comment.class);
        post.addCommentList(comment);

        assertTrue(post.getCommentList().contains(comment));
        verify(comment, times(1)).setPost(post);
    }

    @Test
    void testAddVoteList() {
        Vote vote = mock(Vote.class);
        post.addVoteList(vote);

        assertTrue(post.getVoteList().contains(vote));
        verify(vote, times(1)).setPost(post);
    }

    @Test
    void testAddScrapList() {
        Scrap scrap = mock(Scrap.class);
        post.addScrapList(scrap);

        assertTrue(post.getScrapList().contains(scrap));
        verify(scrap, times(1)).setPost(post);
    }
}