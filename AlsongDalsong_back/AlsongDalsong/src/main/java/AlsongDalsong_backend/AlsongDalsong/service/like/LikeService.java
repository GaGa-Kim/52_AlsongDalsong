package AlsongDalsong_backend.AlsongDalsong.service.like;

import AlsongDalsong_backend.AlsongDalsong.web.dto.like.LikeRequestDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글 좋아요를 위한 비즈니스 로직 인터페이스
 */
@Transactional
public interface LikeService {
    // 댓글에 좋아요를 하거나 좋아요를 취소한다.
    Boolean saveLike(LikeRequestDto likeSaveRequestDto);

    // 댓글 아이디와 회원 이메일에 따른 좋아요 여부를 조회한다.
    @Transactional(readOnly = true)
    Boolean findLike(Long commendId, String email);
}
