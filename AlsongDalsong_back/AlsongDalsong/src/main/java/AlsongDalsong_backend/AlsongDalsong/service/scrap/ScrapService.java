package AlsongDalsong_backend.AlsongDalsong.service.scrap;

import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapResponseDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * 스크랩을 위한 비즈니스 로직 인터페이스
 */
@Transactional
public interface ScrapService {
    // 스크랩을 추가하거나 스크랩을 취소한다.
    Boolean saveScrap(ScrapRequestDto scrapRequestDto);

    // 게시글 아이디와 회원 이메일에 따른 스크랩 여부를 조회한다.
    @Transactional(readOnly = true)
    Boolean findScrap(Long postId, String email);

    // 회원 아이디에 따른 스크랩 리스트를 조회한다.
    @Transactional(readOnly = true)
    List<ScrapResponseDto> findUserScraps(String email);
}
