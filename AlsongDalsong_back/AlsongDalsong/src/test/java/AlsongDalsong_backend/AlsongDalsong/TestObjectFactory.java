package AlsongDalsong_backend.AlsongDalsong;

import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_CATEGORY;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_COMMENT_CONTENT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_COMMENT_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_DATE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_DECISION;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_EMAIL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_IMPORTANCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_INTRODUCE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_KAKAO_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_LIKE_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_LINK;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_NICKNAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_OLD;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_ORIG_PHOTO_NANE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PHOTO_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PHOTO_NAME;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PHOTO_URL;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_CONTENT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_POST_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_PROFILE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_REASON;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_SCRAP_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_TODO;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_TOKEN;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_USER_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_VOTE;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_VOTE_ID;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_WHAT;
import static AlsongDalsong_backend.AlsongDalsong.TestConstants.VALID_WHO;

import AlsongDalsong_backend.AlsongDalsong.domain.comment.Comment;
import AlsongDalsong_backend.AlsongDalsong.domain.like.Like;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.scrap.Scrap;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.vote.Vote;
import AlsongDalsong_backend.AlsongDalsong.web.dto.auth.TokenDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.comment.CommentUpdateRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.like.LikeRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostSaveRequestVO;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostUpdateRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostUpdateRequestVO;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.scrap.ScrapResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.user.UserUpdateRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.vote.VoteRequestDto;
import java.util.ArrayList;
import org.springframework.data.util.Pair;

/**
 * 테스트용 객체 생성 팩토리
 */
public class TestObjectFactory {
    // User Test Object
    public static User initUser() {
        return User.builder()
                .id(VALID_USER_ID)
                .kakaoId(VALID_KAKAO_ID)
                .name(VALID_NAME)
                .email(VALID_EMAIL)
                .nickname(VALID_NICKNAME)
                .profile(VALID_PROFILE)
                .introduce(VALID_INTRODUCE)
                .build();
    }

    public static UserSaveRequestDto initUserSaveRequestDto(User user) {
        return UserSaveRequestDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profile(user.getProfile())
                .introduce(user.getIntroduce())
                .build();
    }

    public static UserUpdateRequestDto initUserUpdateRequestDto(User user) {
        return UserUpdateRequestDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .introduce(user.getIntroduce())
                .build();
    }

    public static UserResponseDto initUserResponseDto(User user) {
        return new UserResponseDto(user);
    }

    public static TokenDto initTokenDto(User user) {
        return TokenDto.builder()
                .token(VALID_TOKEN)
                .email(user.getEmail())
                .build();
    }

    // Post Test Object
    public static Post initPost() {
        return Post.builder()
                .id(VALID_POST_ID)
                .todo(VALID_TODO)
                .category(VALID_CATEGORY)
                .who(VALID_WHO)
                .old(VALID_OLD)
                .date(VALID_DATE)
                .what(VALID_WHAT)
                .content(VALID_POST_CONTENT)
                .link(VALID_LINK)
                .importance(VALID_IMPORTANCE)
                .decision(VALID_DECISION)
                .reason(VALID_REASON)
                .build();
    }

    public static PostSaveRequestVO initPostSaveRequestVO(Post post) {
        return PostSaveRequestVO.builder()
                .email(post.getUserId().getEmail())
                .todo(post.getTodo().getTodo())
                .category(post.getCategory().getCategory())
                .what(post.getWhat())
                .old(post.getOld().getOld())
                .date(post.getDate())
                .who(post.getWho().getWho())
                .content(post.getContent())
                .link(post.getLink())
                .importance(post.getImportance())
                .photos(null)
                .build();
    }

    public static PostSaveRequestDto initPostSaveRequestDto(Post post) {
        return new PostSaveRequestDto(initPostSaveRequestVO(post));
    }

    public static PostUpdateRequestVO initPostUpdateRequestVO(Post post) {
        return PostUpdateRequestVO.builder()
                .id(post.getId())
                .email(post.getUserId().getEmail())
                .todo(post.getTodo().getTodo())
                .category(post.getCategory().getCategory())
                .what(post.getWhat())
                .old(post.getOld().getOld())
                .date(post.getDate())
                .who(post.getWho().getWho())
                .content(post.getContent())
                .link(post.getLink())
                .importance(post.getImportance())
                .photos(null)
                .deleteId(null)
                .build();
    }

    public static PostUpdateRequestDto initPostUpdateRequestDto(Post post) {
        return new PostUpdateRequestDto(initPostUpdateRequestVO(post));
    }

    public static PostResponseDto initPostResponseDto(Post post) {
        return new PostResponseDto(post, new ArrayList<>(), Pair.of(0L, 0L));
    }

    // Photo Test Object
    public static Photo initPhoto() {
        return Photo.builder()
                .id(VALID_PHOTO_ID)
                .origPhotoName(VALID_ORIG_PHOTO_NANE)
                .photoName(VALID_PHOTO_NAME)
                .photoUrl(VALID_PHOTO_URL)
                .build();
    }

    public static PhotoResponseDto initPhotoResponseDto(Photo photo) {
        return PhotoResponseDto.builder()
                .origPhotoName(photo.getOrigPhotoName())
                .photoName(photo.getPhotoName())
                .photoUrl(photo.getPhotoUrl())
                .build();
    }

    // Comment Test Object
    public static Comment initComment() {
        return Comment.builder()
                .id(VALID_COMMENT_ID)
                .content(VALID_COMMENT_CONTENT)
                .build();
    }

    public static CommentSaveRequestDto initCommentSaveRequestDto(Comment comment) {
        return CommentSaveRequestDto.builder()
                .email(comment.getUserId().getEmail())
                .postId(comment.getPostId().getId())
                .content(comment.getContent())
                .build();
    }

    public static CommentUpdateRequestDto initCommentUpdateRequestDto(Comment comment) {
        return CommentUpdateRequestDto.builder()
                .id(comment.getId())
                .email(comment.getUserId().getEmail())
                .postId(comment.getPostId().getId())
                .content(comment.getContent())
                .build();
    }

    public static CommentResponseDto initCommentResponseDto(Comment comment) {
        return new CommentResponseDto(comment);
    }

    // Like Test Object
    public static Like initLike() {
        return Like.builder()
                .id(VALID_LIKE_ID)
                .build();
    }

    public static LikeRequestDto initLikeRequestDto(Like like) {
        return LikeRequestDto.builder()
                .email(like.getUserId().getEmail())
                .commentId(like.getCommentId().getId())
                .build();
    }

    // Vote Test Object
    public static Vote initVote() {
        return Vote.builder()
                .id(VALID_VOTE_ID)
                .vote(VALID_VOTE)
                .build();
    }

    public static VoteRequestDto initVoteRequestDto(Vote vote, Boolean voteContent) {
        return VoteRequestDto.builder()
                .email(vote.getUserId().getEmail())
                .postId(vote.getPostId().getId())
                .vote(voteContent)
                .build();
    }

    // Scrap Test Object
    public static Scrap initScrap() {
        return Scrap.builder()
                .id(VALID_SCRAP_ID)
                .build();
    }

    public static ScrapRequestDto initScrapRequestDto(Scrap scrap) {
        return ScrapRequestDto.builder()
                .email(scrap.getUserId().getEmail())
                .postId(scrap.getPostId().getId())
                .build();
    }

    public static ScrapResponseDto initScrapResponseDto(Scrap scrap) {
        return new ScrapResponseDto(scrap.getPostId());
    }
}
