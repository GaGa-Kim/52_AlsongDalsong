package AlsongDalsong_backend.AlsongDalsong.domain.like;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 댓글 좋아요 레포지토리
 */
public interface LikeRepository extends JpaRepository<Like, Long> {
}
