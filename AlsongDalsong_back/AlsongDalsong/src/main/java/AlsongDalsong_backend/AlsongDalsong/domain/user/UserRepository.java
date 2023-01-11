package AlsongDalsong_backend.AlsongDalsong.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 회원 레포지토리
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); // 이메일로 회원 찾기
    Optional<User> findOneWithRoleById(Long id);
}
