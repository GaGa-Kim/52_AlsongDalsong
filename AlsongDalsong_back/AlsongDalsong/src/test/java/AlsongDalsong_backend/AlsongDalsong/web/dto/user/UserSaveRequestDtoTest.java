package AlsongDalsong_backend.AlsongDalsong.web.dto.user;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_BLANK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.INVALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_INTRODUCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NICKNAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PROFILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import AlsongDalsong_backend.AlsongDalsong.ValidatorUtil;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * UserSaveRequestDto 검증 테스트
 */
class UserSaveRequestDtoTest {
    private final ValidatorUtil<UserSaveRequestDto> validatorUtil = new ValidatorUtil<>();

    @Test
    @DisplayName("UserSaveRequestDto 생성 테스트")
    void testUserSaveRequestDto() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .name(VALID_NAME)
                .email(VALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();

        assertEquals(VALID_NAME, userSaveRequestDto.getName());
        assertEquals(VALID_EMAIL, userSaveRequestDto.getEmail());
        assertEquals(VALID_NICKNAME, userSaveRequestDto.getNickname());
        assertEquals(VALID_PROFILE, userSaveRequestDto.getProfile());
        assertEquals(VALID_INTRODUCE, userSaveRequestDto.getIntroduce());
    }

    @Test
    @DisplayName("UserSaveRequestDto toEntity 생성 테스트")
    void testUserSaveRequestDtoEntity() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .name(VALID_NAME)
                .email(VALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();

        User user = userSaveRequestDto.toEntity();

        assertEquals(VALID_NAME, user.getName());
        assertEquals(VALID_EMAIL, user.getEmail());
        assertEquals(VALID_NICKNAME, user.getNickname());
        assertEquals(VALID_PROFILE, user.getProfile());
        assertEquals(VALID_INTRODUCE, user.getIntroduce());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();

        assertNotNull(userSaveRequestDto);
        assertNull(userSaveRequestDto.getName());
        assertNull(userSaveRequestDto.getEmail());
        assertNull(userSaveRequestDto.getNickname());
        assertNull(userSaveRequestDto.getProfile());
        assertNull(userSaveRequestDto.getIntroduce());
    }

    @Test
    @DisplayName("이름 유효성 검증 테스트")
    void name_validation() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .name(INVALID_BLANK)
                .email(VALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();

        validatorUtil.validate(userSaveRequestDto);
    }

    @Test
    @DisplayName("이메일 유효성 검증 테스트")
    void email_validation() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .name(VALID_NAME)
                .email(INVALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();

        validatorUtil.validate(userSaveRequestDto);
    }

    @Test
    @DisplayName("닉네임 유효성 검증 테스트")
    void nickname_validation() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .name(VALID_NAME)
                .email(VALID_EMAIL)
                .nickname(INVALID_BLANK)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();

        validatorUtil.validate(userSaveRequestDto);
    }
}