package AlsongDalsong_backend.AlsongDalsong.web.dto.photo;

import AlsongDalsong_backend.AlsongDalsong.domain.photo.Photo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 사진 아이디 dto
 */
@Getter
public class PhotoIdResponseDto {

    @ApiModelProperty(notes = "사진 아이디", example = "1")
    private Long photoId;

    public PhotoIdResponseDto(Photo photo) {
        this.photoId = photo.getId();
    }
}
