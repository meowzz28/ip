package meow.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;



public class ToDoTest {
    @Test
    public void toString_readBook_success() {
        Todo task = new Todo("read book");
        assertEquals("[T][ ] read book", task.toString());
    }

    @Test
    public void saveTaskString_readBookMarkedDone_success() {
        Todo task = new Todo("read book");
        task.markDone();
        assertEquals("T | 1 | read book", task.saveTaskString());
    }
}
