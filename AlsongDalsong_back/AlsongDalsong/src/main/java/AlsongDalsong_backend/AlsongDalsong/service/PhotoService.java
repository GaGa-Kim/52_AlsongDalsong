package AlsongDalsong_backend.AlsongDalsong.service;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.PhotoRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoIdResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 사진 서비스
 */
@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final PostRepository postRepository;

    // 사진 id에 따른 사진 개별 조회
    @Transactional(readOnly = true)
    public PhotoResponseDto findByPhotoId(Long id) {
        Photo photo = photoRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 사진이 없습니다."));

        PhotoResponseDto photoResponseDto = PhotoResponseDto.builder()
                .origPhotoName(photo.getOrigPhotoName())
                .photoName(photo.getPhotoName())
                .photoUrl(photo.getPhotoUrl())
                .build();

        return photoResponseDto;
    }

    // 게시글 id에 따른 사진 아이디 전체 조회
    @Transactional(readOnly = true)
    public List<PhotoIdResponseDto> findAllByPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        List<Photo> photoList = photoRepository.findAllByPostId(post);

        return photoList.stream()
                .map(PhotoIdResponseDto::new)
                .collect(Collectors.toList());
    }

    // 사진 삭제
    @Transactional
    public void delete(Long id) {
        Photo photo = photoRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 사진이 없습니다."));
        photoRepository.delete(photo);
    }
}
