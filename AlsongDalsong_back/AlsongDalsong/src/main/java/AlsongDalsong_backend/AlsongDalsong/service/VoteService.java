package AlsongDalsong_backend.AlsongDalsong.service;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.Vote;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.VoteRepository;
import AlsongDalsong_backend.AlsongDalsong.web.dto.vote.VoteRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 투표 서비스
 */
@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    
    // 게시글 투표하기
    @Transactional
    public String vote(VoteRequestDto voteRequestDto) {
        User user = userRepository.findByEmail(voteRequestDto.getEmail());
        Post post = postRepository.findById(voteRequestDto.getPostId()).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        // 한 번도 투표한 적 없다면 투표하기
        if (voteRepository.findByUserIdAndPostId(user, post) == null) {
            Vote vote = voteRequestDto.toEntity();

            // 연관관계 설정
            vote.setUser(user);
            vote.setPost(post);
            post.addVoteList(voteRepository.save(vote));

            // 글 투표 시 + 1점
            user.updatePointAndSticker(user.getPoint() + 1, user.getSticker());

            return voteRequestDto.getVote().toString();
        }
        else if (voteRepository.findByUserIdAndPostId(user, post) != null) {
            Vote vote = voteRepository.findByUserIdAndPostId(user, post);

            // 투표 취소하기
            if ((voteRequestDto.getVote().equals(voteRepository.findByUserIdAndPostId(user, post).getVote()))) {
                // 글 투표 취소시 - 1점
                user.updatePointAndSticker(user.getPoint() - 1, user.getSticker());
                voteRepository.delete(vote);
                return "투표하지 않았습니다.";
            }
            // 반대로 투표 변경하기
            else {
                vote.update(voteRequestDto.getVote());
                return voteRequestDto.getVote().toString();
            }
        }
        else {
            throw new RuntimeException("투표에 실패했습니다.");
        }
    }

    // 게시글에 따른 투표 여부 조회
    @Transactional(readOnly = true)
    public String check(Long postId, String email) {
        User user = userRepository.findByEmail(email);
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        // 투표 했다면
        if(voteRepository.findByUserIdAndPostId(user, post) != null) {
            return voteRepository.findByUserIdAndPostId(user, post).getVote().toString();
        }
        else {
            return "투표하지 않았습니다.";
        }
    }
}
