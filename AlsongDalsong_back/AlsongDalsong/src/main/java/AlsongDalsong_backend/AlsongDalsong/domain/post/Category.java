package AlsongDalsong_backend.AlsongDalsong.domain.post;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 카테고리 열거형
 */
@Getter
@RequiredArgsConstructor
public enum Category {
    FASHION("패션"),
    APPLIANCE("가전"),
    BEAUTY("뷰티"),
    FOOD("식품"),
    NONE("없음");

    private final String category;

    public static Category ofCategory(String category) {
        return Arrays.stream(Category.values()).filter(c -> c.getCategory().equals(category)).findAny().orElse(NONE);
    }
}
