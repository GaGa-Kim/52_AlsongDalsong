package AlsongDalsong_backend.AlsongDalsong.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * 회원 레포지토리 테스트
 */
@DataJpaTest(properties = "spring.sql.init.mode=never")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .kakaoId(123L)
                .name("알송달송")
                .email("alsongdalsong@gmail.com")
                .nickname("알송이")
                .profile("profile.jpg")
                .introduce("안녕하세요")
                .build();
    }

    @Test
    void findByEmail_ExistingEmail() {
        userRepository.save(user);

        User foundUser = userRepository.findByEmail(user.getEmail());

        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    void findByEmail_NotExistingEmail() {
        String notExistEmail = "not@gmail.com";

        User foundUser = userRepository.findByEmail(notExistEmail);

        assertNull(foundUser);
    }

    @Test
    void existsByEmail_ExistingEmail() {
        userRepository.save(user);

        boolean existUser = userRepository.existsByEmail(user.getEmail());

        assertTrue(existUser);
    }

    @Test
    void existsByEmail_NotExistingEmail() {
        String notExistEmail = "not@gmail.com";

        boolean existUser = userRepository.existsByEmail(notExistEmail);

        assertFalse(existUser);
    }
}