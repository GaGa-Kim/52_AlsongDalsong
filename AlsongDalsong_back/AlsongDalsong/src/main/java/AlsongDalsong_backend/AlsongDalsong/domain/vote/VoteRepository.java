package AlsongDalsong_backend.AlsongDalsong.domain.vote;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 투표 레포지토리
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Vote findByUserIdAndPostId(User user, Post post);

    boolean existsByUserIdAndPostId(User user, Post post);
}
