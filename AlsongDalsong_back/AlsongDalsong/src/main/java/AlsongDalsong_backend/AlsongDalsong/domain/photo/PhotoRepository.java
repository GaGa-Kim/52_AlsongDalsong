package AlsongDalsong_backend.AlsongDalsong.domain.photo;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 사진 레포지토리
 */
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
