package AlsongDalsong_backend.AlsongDalsong.service.post;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
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
    // 게시글을 작성한다.
    PostResponseDto addPostWithPhotos(PostSaveRequestDto postSaveRequestDto, List<MultipartFile> multipartFiles);

    // 게시글 아이디로 게시글을 조회한다.
    @Transactional(readOnly = true)
    Post findPostByPostId(Long postId);

    // 게시글 아이디로 게시글을 상세 조회한다.
    @Transactional(readOnly = true)
    PostResponseDto findPostDetailByPostId(Long postId);

    // 분류별 최신글 리스트를 조회한다.
    @Transactional(readOnly = true)
    List<PostResponseDto> findLatestPosts(String todo);

    // 분류별 인기글 리스트를 조회한다. 이때 결정이 완료된 글은 제외된다.
    @Transactional(readOnly = true)
    List<PostResponseDto> findPopularPosts(String todo);

    // 분류 및 카테고리별 게시글 리스트를 조회한다.
    @Transactional(readOnly = true)
    List<PostResponseDto> findPostsByCategory(String todo, String category);

    // 회원 아이디에 따른 게시글 리스트를 조회한다.
    @Transactional(readOnly = true)
    List<PostResponseDto> findUserPosts(String email);

    // 게시글을 수정한다.
    PostResponseDto modifyPost(PostUpdateRequestDto postUpdateRequestDto,
                               List<MultipartFile> photos,
                               List<Long> deletePhotoIds);

    // 게시글을 삭제한다.
    PostResponseDto modifyPostDecision(Long postId, String email, String decision, String reason);

    // 게시글을 확정한다.
    Boolean removePost(Long postId, String email);
}
