package AlsongDalsong_backend.AlsongDalsong.service;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.Scrap;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.ScrapRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 스크랩 서비스
 */
@Service
@RequiredArgsConstructor
public class ScrapService {

    @Autowired
    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostService postService;
    
    // 스크랩
    public Boolean save(ScrapRequestDto scrapRequestDto) {
        User user = userRepository.findByEmail(scrapRequestDto.getEmail());
        Post post = postRepository.findById(scrapRequestDto.getPostId()).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));

        // 한 번도 스크랩한 적 없다면 스크랩하기
        if(scrapRepository.findByUserIdAndPostId(user, post) == null) {
            Scrap scrap = new Scrap();
            scrap.setUser(user);
            scrap.setPost(post);
            user.addScrapList(scrapRepository.save(scrap));
            
            return true;
        }
        // 이미 스크랩 되어있다면 스크랩 취소하기
        else if (scrapRepository.findByUserIdAndPostId(user, post) != null) {
            scrapRepository.delete(scrapRepository.findByUserIdAndPostId(user, post));
            return true;
        }
        else {
            throw new RuntimeException("스크랩에 실패했습니다.");
        }
    }
    
    // 스크랩 조회
    public List<ScrapResponseDto> inquire(String email) {
        // 내가 스크랩한 게시글의 정보 담기
        User user = userRepository.findByEmail(email);
        List<Scrap> scraps = scrapRepository.findAllByUserId(user);
        List<Post> posts = new ArrayList<>();
        for (Scrap scrap: scraps) {
            Post post = postRepository.findById(scrap.getPostId().getId()).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));
            posts.add(post);
        }

        return posts
                .stream()
                .map(ScrapResponseDto::new)
                .collect(Collectors.toList());
    }
}