package AlsongDalsong_backend.AlsongDalsong.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원 레포지토리
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
}
