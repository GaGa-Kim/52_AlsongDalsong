package AlsongDalsong_backend.AlsongDalsong.domain.photo;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_ORIG_PHOTO_NANE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PHOTO_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PHOTO_NAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PHOTO_URL;
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
        photo = Photo.builder()
                .id(VALID_PHOTO_ID)
                .origPhotoName(VALID_ORIG_PHOTO_NANE)
                .photoName(VALID_PHOTO_NAME)
                .photoUrl(VALID_PHOTO_URL)
                .build();
    }

    @Test
    void testSetPost() {
        Post post = mock(Post.class);
        photo.setPost(post);

        assertEquals(photo.getPostId(), post);
    }
}