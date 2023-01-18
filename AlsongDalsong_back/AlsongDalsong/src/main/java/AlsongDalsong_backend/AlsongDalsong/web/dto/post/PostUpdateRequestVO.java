package AlsongDalsong_backend.AlsongDalsong.web.dto.post;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 게시글 수정 vo
 */
@Data
public class PostUpdateRequestVO {

    @ApiParam(name = "게시글 기본키", example = "1")
    private Long id;

    @ApiParam(name = "회원 이메일", example = "1234@gmail.com")
    private String email; // 회원

    @ApiParam(name = "분류", example = "살까 말까")
    private String todo; // 분류

    @ApiParam(name = "카테고리", example = "패션")
    private String category; // 카테고리

    @ApiParam(name = "누가", example = "여성")
    private String who; // 누가

    @ApiParam(name = "연령", example = "20대")
    private String old; // 연령

    @ApiParam(name = "언제", example = "2022년 11월")
    private String date; // 언제

    @ApiParam(name = "무엇을", example = "신발")
    private String what; // 무엇을

    @ApiParam(name = "내용", example = "나이키 신발을~")
    private String content; // 내용

    @ApiParam(name = "링크", example = "www")
    private String link; // 링크

    @ApiParam(name = "중요도", example = "3")
    private Integer importance; // 중요도

    @ApiParam(name = "사진들 (사진이 없다면 비워두기)")
    private List<MultipartFile> photos;

    @ApiParam(name = "삭제할 사진 id (삭제할 사진이 없다면 비워두기)")
    private List<Long> deleteId;
}
