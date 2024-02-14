package AlsongDalsong_backend.AlsongDalsong.domain.user;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * 회원 레포지토리 테스트
 */
@DataJpaTest
class UserRepositoryTest {
    private final String notExistEmail = "not@gmail.com";

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("이미 가입된 이메일로 회원 조회 테스트")
    void findByEmail_ExistingEmail() {
        User foundUser = userRepository.findByEmail(VALID_EMAIL);

        assertNotNull(foundUser);
        assertEquals(VALID_EMAIL, foundUser.getEmail());
    }

    @Test
    @DisplayName("가입되지 않은 이메일로 회원 조회 테스트")
    void findByEmail_NotExistingEmail() {
        User foundUser = userRepository.findByEmail(notExistEmail);

        assertNull(foundUser);
    }

    @Test
    @DisplayName("이미 가입된 이메일로 회원 존재 확인 테스트")
    void existsByEmail_ExistingEmail() {
        boolean existUser = userRepository.existsByEmail(VALID_EMAIL);

        assertTrue(existUser);
    }

    @Test
    @DisplayName("가입되지 않은 이메일로 회원 존재 확인 테스트")
    void existsByEmail_NotExistingEmail() {
        boolean existUser = userRepository.existsByEmail(notExistEmail);

        assertFalse(existUser);
    }
}