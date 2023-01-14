package AlsongDalsong_backend.AlsongDalsong.service;

import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.CommentRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.like.Like;
import AlsongDalsong_backend.AlsongDalsong.domain.like.LikeRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import AlsongDalsong_backend.AlsongDalsong.web.dto.like.LikeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글 좋아요 서비스
 */
@Service
@RequiredArgsConstructor
public class LikeService {
    
    @Autowired
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    
    // 댓글 좋아요
    @Transactional
    public Boolean save(LikeRequestDto likeSaveRequestDto) {
        User user = userRepository.findByEmail(likeSaveRequestDto.getEmail());
        Comment comment = commentRepository.findById(likeSaveRequestDto.getCommentId()).orElseThrow(()-> new IllegalArgumentException("해당 댓글이 없습니다."));

        // 한 번도 좋아요한 적 없다면 좋아요하기
        if(likeRepository.findByUserIdAndCommentId(user, comment) == null) {
            Like like = new Like();
            like.setUser(user);
            like.setComment(comment);
            comment.addLikeList(likeRepository.save(like));
            // 좋아요 작성 시 + 1점
            user.updatePointAndSticker(user.getPoint() + 1, user.getSticker());

            return true;
        }
        // 이미 좋아요 되어있다면 좋아요 취소하기
        else if(likeRepository.findByUserIdAndCommentId(user, comment) != null) {
            likeRepository.delete(likeRepository.findByUserIdAndCommentId(user, comment));
            // 좋아요 취소 시 + 1점
            user.updatePointAndSticker(user.getPoint() - 1, user.getSticker());
            return true;
        }
        else {
            throw new RuntimeException("댓글 좋아요에 실패했습니다.");
        }
    }
}
