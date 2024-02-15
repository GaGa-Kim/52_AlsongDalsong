package AlsongDalsong_backend.AlsongDalsong.domain.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 게시글 분류 열거형 값 테스트
 */
class TodoTest {
    @Test
    @DisplayName("올바른 게시글 분류 열거형 값 테스트")
    void ofTodo_ValidTodo() {
        String validTodo = "살까 말까";

        Todo todo = Todo.ofTodo(validTodo);

        assertNotNull(todo);
        assertEquals(Todo.TO_BUY_OR_NOT_TO_BUY, todo);
    }

    @Test
    @DisplayName("올바르지 않은 게시글 분류 열거형 값 테스트")
    void ofTodo_InvalidTodo() {
        String invalidTodo = "고민이야";

        Todo todo = Todo.ofTodo(invalidTodo);

        assertNotNull(todo);
        assertEquals(Todo.NONE, todo);
    }
}