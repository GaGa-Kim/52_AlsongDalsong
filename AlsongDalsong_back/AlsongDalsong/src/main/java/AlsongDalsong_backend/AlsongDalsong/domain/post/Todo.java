package AlsongDalsong_backend.AlsongDalsong.domain.post;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 분류 열거형
 */
@Getter
@RequiredArgsConstructor
public enum Todo {
    TO_BUY_OR_NOT_TO_BUY("살까 말까"),
    TO_DO_OR_NOT_TO_DO("할까 말까"),
    TO_GO_OR_NOT_TO_GO("갈까 말까"),
    NONE("없음");

    private final String todo;

    public static Todo ofTodo(String todo) {
        return Arrays.stream(Todo.values()).filter(t -> t.getTodo().equals(todo)).findAny().orElse(NONE);
    }
}
