package AlsongDalsong_backend.AlsongDalsong.service;

import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.comment.CommentRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 댓글 서비스
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 게시글에 댓글 작성
    @Transactional
    public List<CommentResponseDto> save(CommentSaveRequestDto commentSaveRequestDto) {
        User user = userRepository.findByEmail(commentSaveRequestDto.getEmail());
        Post post = postRepository.findById(commentSaveRequestDto.getPostId()).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));

        Comment comment = commentSaveRequestDto.toEntity();
        // 연관관계 설정
        comment.setUser(user);
        comment.setPost(post);
        post.addCommentList(commentRepository.save(comment));

        // 댓글 작성 시 + 1점
        user.updatePointAndSticker(user.getPoint() + 1, user.getSticker());

        return commentRepository.findAllByPostIdOrderByLikeListDesc(post)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    // 게시글별 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> inquire(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));

        return commentRepository.findAllByPostIdOrderByLikeListDesc(post)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    // 게시글의 댓글 수정
    @Transactional
    public List<CommentResponseDto> update(CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment comment = commentRepository.findById(commentUpdateRequestDto.getId()).orElseThrow(()-> new IllegalArgumentException("해당 댓글이 없습니다."));

        // 댓글 작성자와 댓글 수정하는 자가 같을 경우에만 수정 가능
        if(comment.getUserId().getEmail().equals(commentUpdateRequestDto.getEmail())) {
            comment.update(commentUpdateRequestDto.getContent());
            Post post = postRepository.findById(commentUpdateRequestDto.getPostId()).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));

            return commentRepository.findAllByPostIdOrderByLikeListDesc(post)
                    .stream()
                    .map(CommentResponseDto::new)
                    .collect(Collectors.toList());
        }
        else {
            throw new RuntimeException("댓글 수정에 실패했습니다.");
        }
    }

    // 게시글의 댓글 삭제
    @Transactional
    public Boolean delete(Long id, String email) {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 댓글이 없습니다."));

        // 댓글 작성자와 댓글 삭제하는 자가 같을 경우에만 삭제 가능
        if(email.equals(comment.getUserId().getEmail())) {
            commentRepository.delete(comment);

            return true;
        }
        else {
            throw new RuntimeException("댓글 삭제에 실패했습니다.");
        }
    }
}