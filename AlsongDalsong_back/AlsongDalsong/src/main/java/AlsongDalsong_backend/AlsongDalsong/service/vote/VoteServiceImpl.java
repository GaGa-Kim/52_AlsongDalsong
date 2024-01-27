package AlsongDalsong_backend.AlsongDalsong.service.vote;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.Vote;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.VoteRepository;
import AlsongDalsong_backend.AlsongDalsong.exception.NotFoundException;
import AlsongDalsong_backend.AlsongDalsong.service.post.PostService;
import AlsongDalsong_backend.AlsongDalsong.service.user.UserService;
import AlsongDalsong_backend.AlsongDalsong.web.dto.vote.VoteRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 게시글 투표를 위한 비즈니스 로직 구현 클래스
 */
@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private static final String NOT_VOTE = "투표하지 않았습니다.";
    private static final int POINTS_PER_VOTE = 1;
    private static final int POINTS_PER_CANCEL = -1;

    private final VoteRepository voteRepository;
    private final UserService userService;
    private final PostService postService;

    /**
     * 게시글에 투표를 하거나 투표를 취소 및 수정한다.
     *
     * @param voteRequestDto (투표 저장 정보 DTO)
     * @return String (찬성 투표, 반대 투표, 투표 미정 여부)
     */
    @Override
    public String saveVote(VoteRequestDto voteRequestDto) {
        User user = userService.findUserByEmail(voteRequestDto.getEmail());
        Post post = postService.findPostByPostId(voteRequestDto.getPostId());
        if (!existsVoteByUserId(user, post)) {
            return createVote(user, post, voteRequestDto);
        }
        return deleteOrModifyVote(user, post, voteRequestDto);
    }

    /**
     * 게시글 아이디와 회원 이메일에 따른 투표 여부를 조회한다.
     *
     * @param postId (게시글 아이디), email (회원 이메일)
     * @return String (찬성 투표, 반대 투표, 투표 미정 여부)
     */
    @Override
    public String findVote(Long postId, String email) {
        User user = userService.findUserByEmail(email);
        Post post = postService.findPostByPostId(postId);
        if (existsVoteByUserId(user, post)) {
            return findUserVote(user, post).getVote().toString();
        }
        return NOT_VOTE;
    }

    /**
     * 게시글과 회원에 따른 투표 여부를 조회한다.
     *
     * @param user (회원), post (게시글)
     * @return Boolean (투표 여부)
     */
    private boolean existsVoteByUserId(User user, Post post) {
        return voteRepository.existsByUserIdAndPostId(user, post);
    }

    /**
     * 게시글과 회원에 따른 투표를 조회한다.
     *
     * @param user (회원), post (게시글)
     * @return Vote (투표)
     */
    private Vote findUserVote(User user, Post post) {
        return voteRepository.findByUserIdAndPostId(user, post);
    }

    /**
     * 투표를 저장한다.
     *
     * @param user (회원), post (게시글), voteRequestDto (투표 저장 정보 DTO)
     * @return boolean (투표 저장에 따른 true 반환)
     */
    private String createVote(User user, Post post, VoteRequestDto voteRequestDto) {
        Vote vote = voteRepository.save(voteRequestDto.toEntity());
        vote.setUser(user);
        vote.setPost(post);
        post.addVoteList(vote);
        increasePoint(user, POINTS_PER_VOTE);
        return voteRequestDto.getVote().toString();
    }

    /**
     * 투표를 삭제하거나 반대로 수정한다.
     *
     * @param user (회원), post (게시글), voteRequestDto (투표 저장 정보 DTO)
     * @return String (투표에 따른 결과 반환)
     */
    private String deleteOrModifyVote(User user, Post post, VoteRequestDto voteRequestDto) {
        Vote vote = voteRepository.findByUserIdAndPostId(user, post);
        if (isCancelVote(user, post, voteRequestDto)) {
            increasePoint(user, POINTS_PER_CANCEL);
            voteRepository.delete(vote);
            return NOT_VOTE;
        }
        if (isSameUser(user, vote)) {
            vote.update(voteRequestDto.getVote());
            return voteRequestDto.getVote().toString();
        }
        throw new NotFoundException();
    }

    /**
     * 투표를 삭제할 것인지 수정할 것인지 조회한다.
     *
     * @param user (회원), post (게시글), voteRequestDto (투표 저장 정보 DTO)
     * @return boolean (투표 삭제 또는 반대로 수정 여부)
     */
    private boolean isCancelVote(User user, Post post, VoteRequestDto voteRequestDto) {
        return voteRequestDto.getVote().equals(findUserVote(user, post).getVote());
    }

    /**
     * 활동에 따른 포인트가 증가한다.
     *
     * @param user (회원), @param point (증가 포인트)
     */
    private void increasePoint(User user, int point) {
        user.updatePoint(user.getPoint() + point);
    }

    /**
     * 투표 작성자와 투표 편집자가 같은지 확인한다.
     *
     * @param user (회원), vote (투표)
     * @return boolean (투표 작성자와 편집자 동일 여부)
     */
    private boolean isSameUser(User user, Vote vote) {
        return user.equals(vote.getUserId());
    }
}
