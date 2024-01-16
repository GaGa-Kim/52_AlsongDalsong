package AlsongDalsong_backend.AlsongDalsong.service.scrap;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.Scrap;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.ScrapRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.exception.NotFoundException;
import AlsongDalsong_backend.AlsongDalsong.service.post.PostService;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 스크랩을 위한 비즈니스 로직 구현 클래스
 */
@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {
    private final ScrapRepository scrapRepository;
    private final UserService userService;
    private final PostService postService;

    /**
     * 스크랩을 추가하거나 스크랩을 취소한다.
     *
     * @param scrapRequestDto (스크랩 저장 정보 DTO)
     * @return Boolean (스크랩 또는 스크랩 취소 여부)
     */
    @Override
    public Boolean saveScrap(ScrapRequestDto scrapRequestDto) {
        User user = userService.findUserByEmail(scrapRequestDto.getEmail());
        Post post = postService.findPostByPostId(scrapRequestDto.getPostId());
        if (!exitsScrapByUserId(user, post)) {
            return createLike(user, post);
        }
        return deleteLike(user, post);
    }

    /**
     * 게시글 아이디와 회원 이메일에 따른 스크랩 여부를 조회한다.
     *
     * @param postId (게시글 아이디), email (회원 이메일)
     * @return Boolean (스크랩 여부)
     */
    @Override
    public Boolean findScrap(Long postId, String email) {
        User user = userService.findUserByEmail(email);
        Post post = postService.findPostByPostId(postId);
        return exitsScrapByUserId(user, post);
    }

    /**
     * 회원 아이디에 따른 스크랩 리스트를 조회한다.
     *
     * @param email (회원 이메일)
     * @return List<ScrapResponseDto> (회원 스크랩 DTO 리스트)
     */
    @Override
    public List<ScrapResponseDto> findUserScraps(String email) {
        User user = userService.findUserByEmail(email);
        return scrapRepository.findByUserId(user)
                .stream()
                .map(scrap -> new ScrapResponseDto(postService.findPostByPostId(scrap.getPostId().getId())))
                .collect(Collectors.toList());
    }

    /**
     * 게시글과 회원에 따른 스크랩 여부를 조회한다.
     *
     * @param user (회원), post (게시글)
     * @return boolean (스크랩 여부)
     */
    private boolean exitsScrapByUserId(User user, Post post) {
        return scrapRepository.existsByUserIdAndPostId(user, post);
    }

    /**
     * 스크랩읉 저장한다.
     *
     * @param user (회원), post (게시글)
     * @return boolean (스크랩 저장에 따른 true 반환)
     */
    private boolean createLike(User user, Post post) {
        Scrap scrap = new Scrap();
        scrap.setUser(user);
        scrap.setPost(post);
        scrapRepository.save(scrap);
        user.addScrapList(scrap);
        post.addScrapList(scrap);
        return true;
    }

    /**
     * 스크랩을 삭제한다.
     *
     * @param user (회원), post (게시글)
     * @return boolean (스크랩 삭제에 따른 false 반환)
     */
    private boolean deleteLike(User user, Post post) {
        Scrap scrap = scrapRepository.findByUserIdAndPostId(user, post);
        if (isSameUser(user, scrap)) {
            scrapRepository.delete(scrap);
            return false;
        }
        throw new NotFoundException();
    }

    /**
     * 스크랩 작성자와 스크랩 편집자가 같은지 확인한다.
     *
     * @param user (회원), scrap (스크랩)
     * @return boolean (스크랩 작성자와 편집자 동일 여부)
     */
    private boolean isSameUser(User user, Scrap scrap) {
        return user.equals(scrap.getUserId());
    }
}