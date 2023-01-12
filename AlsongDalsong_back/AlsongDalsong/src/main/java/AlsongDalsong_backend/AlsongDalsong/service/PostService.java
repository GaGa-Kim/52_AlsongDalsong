package AlsongDalsong_backend.AlsongDalsong.service;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import AlsongDalsong_backend.AlsongDalsong.domain.photo.PhotoRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import AlsongDalsong_backend.AlsongDalsong.domain.post.PostRepository;
import AlsongDalsong_backend.AlsongDalsong.domain.user.User;
import AlsongDalsong_backend.AlsongDalsong.domain.user.UserRepository;
import AlsongDalsong_backend.AlsongDalsong.web.dto.photo.PhotoIdResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostResponseDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostSaveRequestDto;
import AlsongDalsong_backend.AlsongDalsong.web.dto.post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 게시글 서비스
 */
@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final AwsS3Service awsS3Service;
    private final PhotoService photoService;

    // 게시글 작성
    @Transactional
    public PostResponseDto save(PostSaveRequestDto postSaveRequestDto, List<MultipartFile> multipartFiles) {
        User user = userRepository.findByEmail(postSaveRequestDto.getEmail());
        Post post = postSaveRequestDto.toEntity();
        post.setUser(user);
        // 글 작성 시 + 1점
        user.updatePoint(user.getPoint() + 1);

        // 게시글에 사진이 있을 경우
        if(multipartFiles != null) {
            List<Photo> photoList = awsS3Service.uploadPhoto(multipartFiles);
            if(!photoList.isEmpty()) {
                for(Photo photo: photoList) {
                    post.addPhotoList(photoRepository.save(photo));
                }
            }
        }

        user.addPostList(postRepository.save(post));
        return new PostResponseDto(post, findPostId(post.getId()));
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto update(PostUpdateRequestDto postUpdateRequestDto, List<MultipartFile> multipartFiles, List<Long> deleteId) {
        User user = userRepository.findByEmail(postUpdateRequestDto.getEmail());
        Post post = postRepository.findById(postUpdateRequestDto.getId()).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));

        // 삭제할 파일이 있다면 파일 삭제
        if (!CollectionUtils.isEmpty(deleteId)) {
            for(Long deleteFileId: deleteId) {
                awsS3Service.deleteS3(photoService.findByPhotoId(deleteFileId).getPhotoName());
                photoService.delete(deleteFileId);
            }
        }

        if(user.equals(post.getUserId())) {
            // 추가할 파일이 있다면 파일 추가
            if(!CollectionUtils.isEmpty(multipartFiles)) {
                List<Photo> photoList = awsS3Service.uploadPhoto(multipartFiles);
                for(Photo photo: photoList) {
                    post.addPhotoList(photoRepository.save(photo));
                }
            }

            post.update(postUpdateRequestDto.getTodo(),
                        postUpdateRequestDto.getCategory(),
                        postUpdateRequestDto.getWho(),
                        postUpdateRequestDto.getOld(),
                        postUpdateRequestDto.getDate(),
                        postUpdateRequestDto.getWhat(),
                        postUpdateRequestDto.getContent(),
                        postUpdateRequestDto.getLink(),
                        postUpdateRequestDto.getImportance());

            return new PostResponseDto(post, findPostId(post.getId()));
        }
        else {
            throw new RuntimeException("게시글 수정에 실패했습니다.");
        }
    }

    // 게시글 삭제
    @Transactional
    public Boolean delete(Long id, String email) {
        User user = userRepository.findByEmail(email);
        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));
        List<Photo> photoList = photoRepository.findAllByPostId(post);

        // 사진과 게시글 모두 삭제
        if(user.equals(post.getUserId())) {
            for(Photo photo: photoList) {
                awsS3Service.deleteS3(photo.getPhotoName());
            }
            postRepository.delete(post);
            return true;
        }
        else {
            throw new RuntimeException("게시글 삭제에 실패했습니다.");
        }
    }

    // 게시글 확정
    @Transactional
    public PostResponseDto updateDecision(Long id, String decision, String reason) {
        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));
        post.setDecision(decision, reason);
        // 글 확정 시 + 5점
        post.getUserId().updatePoint(post.getUserId().getPoint() + 5);

        return new PostResponseDto(post, findPostId(post.getId()));
    }

    // 게시글 별 사진 아이디 전체 조회
    @Transactional(readOnly = true)
    public List<Long> findPostId(Long id){

        List<PhotoIdResponseDto> photoIdResponseDtoList = photoService.findAllByPost(id);
        List<Long> photoId = new ArrayList<>();

        for (PhotoIdResponseDto photoIdResponseDto : photoIdResponseDtoList) {
            photoId.add(photoIdResponseDto.getPhotoId());
        }

        return photoId;
    }
}
