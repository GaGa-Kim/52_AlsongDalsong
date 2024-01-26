package AlsongDalsong_backend.AlsongDalsong.domain.photo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 사진 테이블 테스트
 */
class PhotoTest {
    private Photo photo;

    @BeforeEach
    void setUp() {
        photo = Photo.builder().origPhotoName("picture").photoName("picture").photoUrl("www.google.com").build();
    }

    @Test
    void testSetPost() {
        Post post = mock(Post.class);
        photo.setPost(post);

        assertEquals(photo.getPostId(), post);
    }
}