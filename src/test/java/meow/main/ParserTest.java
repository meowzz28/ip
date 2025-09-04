package meow.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import meow.exception.MeowException;
import meow.task.Task;

public class ParserTest {
    private TaskList tasks;
    private Ui ui;
    private StubStorage storage;

    @BeforeEach
    public void setup() {
        tasks = new TaskList();
        ui = new Ui();
        storage = new StubStorage();
    }

    @Test
    public void addCase_todo_addsTodoSuccess() throws MeowException {
        String response = Parser.parse("todo read book", tasks, ui, storage);
        assertEquals(1, tasks.size());
        Task t = tasks.get(0);
        assertEquals("[T][ ] read book", t.toString());
        assertTrue(response.contains("added"));
        assertTrue(storage.saved, "Storage should be saved");
    }

    @Test
    public void exitCase_bye_exitSuccess() throws MeowException {
        String response = Parser.parse("bye", tasks, ui, storage);
        assertTrue(response.contains("Bye"), "Response should contain 'Bye'");
    }

    private static class StubStorage extends Storage {
        private boolean saved = false;
        StubStorage() {
            super("test.txt");
        }
        @Override
        public void save(TaskList tasks) {
            saved = true;
        }
    }
}
