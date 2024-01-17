package AlsongDalsong_backend.AlsongDalsong.service.photo;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoResponseDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 사진을 위한 비즈니스 로직 인터페이스
 */
@Transactional
public interface PhotoService {
    // 사진을 추가한다.
    Photo addPhoto(Photo photo);

    // 사진 아이디로 사진을 조회한다.
    @Transactional(readOnly = true)
    Photo findPhotoByPhotoId(Long photoId);

    // 사진 아이디로 사진을 상세 조회한다.
    @Transactional(readOnly = true)
    PhotoResponseDto findPhoto(Long photoId);

    // 사진 아이디로 사진을 삭제한다.
    void removePhoto(Photo photo);
}
