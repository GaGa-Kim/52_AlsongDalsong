package AlsongDalsong_backend.AlsongDalsong.service.photo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import AlsongDalsong_backend.AlsongDalsong.TestObjectFactory;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.PhotoRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.except.NotFoundException;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoResponseDto;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 게시글 사진을 위한 비즈니스 로직 구현 클래스 테스트
 */
@ExtendWith(MockitoExtension.class)
class PhotoServiceImplTest {
    private Photo photo;

    @InjectMocks
    private PhotoServiceImpl photoService;
    @Mock
    private PhotoRepository photoRepository;

    @BeforeEach
    void setUp() {
        photo = TestObjectFactory.initPhoto();
        photo.setPost(mock(Post.class));
    }

    @Test
    @DisplayName("사진 저장 테스트")
    void testAddPhoto() {
        when(photoRepository.save(any())).thenReturn(photo);

        Photo result = photoService.addPhoto(photo);

        assertNotNull(result);
        assertEquals(photo.getId(), result.getId());

        verify(photoRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("사진 아이디로 사진 조회 테스트")
    void testFindPhotoByExistingPhotoId() {
        when(photoRepository.findById(any())).thenReturn(Optional.ofNullable(photo));

        Photo result = photoService.findPhotoByPhotoId(photo.getId());

        assertNotNull(result);
        assertEquals(photo.getId(), result.getId());

        verify(photoRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("존재하지 않는 사진 아이디로 사진 조회 예외 발생 테스트")
    void testFindPhotoByNonExistingPhotoIdExcept() {
        Long nonExistingPhotoId = 100L;
        when(photoRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> photoService.findPhotoByPhotoId(nonExistingPhotoId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("사진 아이디로 사진 상세 조회 테스트")
    void testFindPhoto() {
        when(photoRepository.findById(any())).thenReturn(Optional.ofNullable(photo));

        PhotoResponseDto result = photoService.findPhoto(photo.getId());

        assertNotNull(result);
        assertEquals(photo.getPhotoUrl(), result.getPhotoUrl());

        verify(photoRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("사진 아이디로 사진 삭제 테스트")
    void testRemovePhoto() {
        doNothing().when(photoRepository).delete(any());

        photoService.removePhoto(photo);

        verify(photoRepository, times(1)).delete(any());
    }
}