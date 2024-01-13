package AlsongDalsong_backend.AlsongDalsong.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원 레포지토리
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); // 이메일로 회원 찾기

    boolean existsByEmail(String email);

    Optional<User> findOneWithRoleById(Long id);
}
