package AlsongDalsong_backend.AlsongDalsong.service.vote;

import AlsongDalsong_backend.AlsongDalsong.web.dto.vote.VoteRequestDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 투표를 위한 비즈니스 로직 인터페이스
 */
@Transactional
public interface VoteService {
    // 게시글에 투표를 하거나 투표를 취소 및 수정한다.
    String saveVote(VoteRequestDto voteRequestDto);

    // 게시글 아이디와 회원 이메일에 따른 투표 여부를 조회한다.
    @Transactional(readOnly = true)
    String findVote(Long postId, String email);
}
