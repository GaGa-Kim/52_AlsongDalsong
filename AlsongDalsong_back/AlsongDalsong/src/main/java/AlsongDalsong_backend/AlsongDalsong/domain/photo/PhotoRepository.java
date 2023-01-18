package AlsongDalsong_backend.AlsongDalsong.domain.photo;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByPostId(Post post);
}
