package AlsongDalsong_backend.AlsongDalsong.web.dto.photo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * 사진 정보 dto
 */
@Getter
public class PhotoResponseDto {
    @ApiModelProperty(notes = "원본 이름", example = "photo.jpg")
    private String origPhotoName;

    @ApiModelProperty(notes = "변환된 사진 이름", example = "20202001-gggg.jpg")
    private String photoName;

    @ApiModelProperty(notes = "사진 Url", example = "www")
    private String photoUrl;

    @Builder
    public PhotoResponseDto(String origPhotoName, String photoName, String photoUrl) {
        this.origPhotoName = origPhotoName;
        this.photoName = photoName;
        this.photoUrl = photoUrl;
    }
}