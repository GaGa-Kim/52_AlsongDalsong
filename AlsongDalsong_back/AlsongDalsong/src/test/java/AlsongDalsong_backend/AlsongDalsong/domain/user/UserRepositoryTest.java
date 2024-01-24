package AlsongDalsong_backend.AlsongDalsong.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * 회원 레포지토리 테스트
 */
@DataJpaTest
class UserRepositoryTest {
    private final String existEmail = "1234@gmail.com";
    private final String notExistEmail = "not@gmail.com";
    
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_ExistingEmail() {
        User foundUser = userRepository.findByEmail(existEmail);

        assertNotNull(foundUser);
        assertEquals(existEmail, foundUser.getEmail());
    }

    @Test
    void findByEmail_NotExistingEmail() {
        User foundUser = userRepository.findByEmail(notExistEmail);

        assertNull(foundUser);
    }

    @Test
    void existsByEmail_ExistingEmail() {
        boolean existUser = userRepository.existsByEmail(existEmail);

        assertTrue(existUser);
    }

    @Test
    void existsByEmail_NotExistingEmail() {
        boolean existUser = userRepository.existsByEmail(notExistEmail);

        assertFalse(existUser);
    }
}