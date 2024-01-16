package AlsongDalsong_backend.AlsongDalsong.service.photo;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.PhotoRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.exception.NotFoundException;
import AlsongDalsong_backend.AlsongDalsong.service.post.PostService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoIdResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 게시글 사진을 위한 비즈니스 로직 구현 클래스
 */
@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    private final PostService postService;

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
     * 게시글 아이디로 사진 아이디 리스트를 조회한다.
     *
     * @param postId (게시글 아이디)
     * @return List<PhotoIdResponseDto> (사진 아이디 DTO 리스트)
     */
    @Override
    public List<PhotoIdResponseDto> findPhotoList(Long postId) {
        Post post = postService.findPostByPostId(postId);
        return photoRepository.findAllByPostId(post)
                .stream()
                .map(PhotoIdResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 사진 아이디로 사진을 삭제한다.
     *
     * @param photoId (사진 아이디)
     */
    @Override
    public void removePhoto(Long photoId) {
        Photo photo = findPhotoByPhotoId(photoId);
        photoRepository.delete(photo);
    }
}
