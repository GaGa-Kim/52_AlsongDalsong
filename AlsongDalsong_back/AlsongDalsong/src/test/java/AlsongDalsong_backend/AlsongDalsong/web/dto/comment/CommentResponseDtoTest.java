package AlsongDalsong_backend.AlsongDalsong.web.dto.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.Time;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import org.junit.jupiter.api.Test;

/**
 * CommentResponseDto 검증 테스트
 */
class CommentResponseDtoTest {
    @Test
    void testCommentResponseDto() {
        Comment comment = TestObjectFactory.initComment();
        comment.setUser(TestObjectFactory.initUser());
        comment.setPost(TestObjectFactory.initPost());
        comment.addLikeList(TestObjectFactory.initLike());

        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);

        assertEquals(comment.getId(), commentResponseDto.getId());
        assertEquals(Time.calculateTime(comment.getCreatedDateTime()), commentResponseDto.getCreatedDateTime());
        assertEquals(comment.getContent(), commentResponseDto.getContent());
        assertEquals(comment.getLikeList().size(), commentResponseDto.getLike());
    }
}