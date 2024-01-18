package AlsongDalsong_backend.AlsongDalsong.domain.scrap;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 스크랩 레포지토리
 */
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Scrap findByUserIdAndPostId(User user, Post post);

    List<Scrap> findByUserId(User user);
}