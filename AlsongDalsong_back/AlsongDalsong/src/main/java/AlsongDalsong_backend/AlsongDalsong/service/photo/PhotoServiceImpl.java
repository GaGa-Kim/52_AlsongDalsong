package AlsongDalsong_backend.AlsongDalsong.service.photo;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.PhotoRepository;
import AlsongDalsong_backend.AlsongDalsong.except.NotFoundException;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 게시글 사진을 위한 비즈니스 로직 구현 클래스
 */
@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;

    /**
     * 사진을 추가한다.
     *
     * @param photo (사진)
     * @return
     */
    @Override
    public Photo addPhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    /**
     * 사진 아이디로 사진을 조회한다.
     *
     * @param photoId (사진 아이디)
     * @return Photo (사진)
     */
    @Override
    public Photo findPhotoByPhotoId(Long photoId) {
        return photoRepository.findById(photoId).orElseThrow(NotFoundException::new);
    }

    /**
     * 사진 아이디로 사진을 상세 조회한다.
     *
     * @param photoId (사진 아이디)
     * @return PhotoResponseDto (게시글 정보 DTO)
     */
    @Override
    public PhotoResponseDto findPhoto(Long photoId) {
        Photo photo = findPhotoByPhotoId(photoId);
        return PhotoResponseDto.builder()
                .origPhotoName(photo.getOrigPhotoName())
                .photoName(photo.getPhotoName())
                .photoUrl(photo.getPhotoUrl())
                .build();
    }

    /**
     * 사진을 삭제한다.
     *
     * @param photo (사진)
     */
    @Override
    public void removePhoto(Photo photo) {
        photoRepository.delete(photo);
    }
}
