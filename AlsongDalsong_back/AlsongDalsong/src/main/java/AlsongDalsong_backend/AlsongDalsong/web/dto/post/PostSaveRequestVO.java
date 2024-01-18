package AlsongDalsong_backend.AlsongDalsong.web.dto.post;

import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 게시글 작성 vo
 */
@Data
public class PostSaveRequestVO {
    @ApiParam(name = "회원 이메일", example = "1234@gmail.com")
    private String email;

    @ApiParam(name = "분류", example = "살까 말까")
    private String todo;

    @ApiParam(name = "카테고리", example = "패션")
    private String category;

    @ApiParam(name = "누가", example = "여성")
    private String who;

    @ApiParam(name = "연령", example = "20대")
    private String old;

    @ApiParam(name = "언제", example = "2022년 11월")
    private String date;

    @ApiParam(name = "무엇을", example = "신발")
    private String what;

    @ApiParam(name = "내용", example = "나이키 신발을~")
    private String content;

    @ApiParam(name = "링크", example = "www")
    private String link;

    @ApiParam(name = "중요도", example = "3")
    private Integer importance;

    @ApiParam(name = "사진들 (사진이 없다면 비워두기)")
    private List<MultipartFile> photos;
}
