package AlsongDalsong_backend.AlsongDalsong.domain.vote;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Vote findByUserIdAndPostId(User user, Post post);
    Long countByPostIdAndVote(Post post, Boolean vote);
}
