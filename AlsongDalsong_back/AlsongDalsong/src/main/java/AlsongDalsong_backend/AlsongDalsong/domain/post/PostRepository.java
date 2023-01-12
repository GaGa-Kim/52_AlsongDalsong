package AlsongDalsong_backend.AlsongDalsong.domain.post;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
