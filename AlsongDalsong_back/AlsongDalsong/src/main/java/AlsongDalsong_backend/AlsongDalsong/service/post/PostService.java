package AlsongDalsong_backend.AlsongDalsong.service.post;

import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostUpdateRequestDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 게시글을 위한 비즈니스 로직 인터페이스
 */
@Transactional
public interface PostService {
    PostResponseDto addPostWithPhotos(PostSaveRequestDto postSaveRequestDto, List<MultipartFile> multipartFiles);

    @Transactional(readOnly = true)
    PostResponseDto findPostByPostId(Long postId);

    @Transactional(readOnly = true)
    List<PostResponseDto> findLatestPosts(String todo);

    @Transactional(readOnly = true)
    List<PostResponseDto> findPopularPosts(String todo);

    @Transactional(readOnly = true)
    List<PostResponseDto> findPostsByCategory(String todo, String category);

    @Transactional(readOnly = true)
    List<PostResponseDto> findUserPosts(String email);

    PostResponseDto modifyPost(PostUpdateRequestDto postUpdateRequestDto,
                               List<MultipartFile> multipartFiles,
                               List<Long> deletePhotoIds);

    PostResponseDto modifyPostDecision(Long id, String email, String decision, String reason);

    Boolean removePost(Long id, String email);
}
