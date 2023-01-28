package AlsongDalsong_backend.AlsongDalsong.web.dto.scrap;

import AlsongDalsong_backend.AlsongDalsong.domain.post.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 스크랩 정보 dto
 */
@Getter
public class ScrapResponseDto {

    @ApiModelProperty(notes = "게시글 기본키", example = "1")
    private Long id;

    @ApiModelProperty(notes = "분류", example = "살까 말까")
    private String todo; // 분류

    @ApiModelProperty(notes = "무엇을", example = "신발")
    private String what; // 무엇을

    @ApiModelProperty(notes = "게시글 첫 번째 사진 id (썸네일 사진 id), 사진이 없다면 0", example = "1")
    private Long photoId; // 썸네일 사진 id

    public ScrapResponseDto(Post post) {
        this.id = post.getId();
        this.todo = post.getTodo();
        this.what = post.getWhat();
        if(post.getPhotoList().size() == 0) {
            this.photoId = 0L;
        }
        else {
            this.photoId = post.getPhotoList().get(0).getId();
        }
    }
}