package AlsongDalsong_backend.AlsongDalsong.domain.photo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 사진 레포지토리
 */
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
