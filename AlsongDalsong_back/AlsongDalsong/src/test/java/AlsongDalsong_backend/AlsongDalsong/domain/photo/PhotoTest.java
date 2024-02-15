package AlsongDalsong_backend.AlsongDalsong.domain.photo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 사진 테이블 테스트
 */
class PhotoTest {
    private Photo photo;

    @BeforeEach
    void setUp() {
        photo = TestObjectFactory.initPhoto();
    }

    @Test
    @DisplayName("작성한 게시글 사진의 게시글 연관관계 설정 테스트")
    void testSetPost() {
        Post post = mock(Post.class);
        photo.setPost(post);

        assertEquals(photo.getPostId(), post);
    }
}