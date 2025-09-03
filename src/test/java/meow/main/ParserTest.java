package meow.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import meow.exception.MeowException;
import meow.task.Task;

public class ParserTest {
    private TaskList tasks;
    private StubUi ui;
    private StubStorage storage;

    @BeforeEach
    public void setup() {
        tasks = new TaskList();
        ui = new StubUi();
        storage = new StubStorage();
    }

    @Test
    public void addCase_todo_addsTodoSuccess() throws MeowException {
        boolean exit = Parser.parse("todo read book", tasks, ui, storage);
        assertFalse(exit);
        assertEquals(1, tasks.size());
        Task t = tasks.get(0);
        assertEquals("[T][ ] read book", t.toString());
        assertTrue(ui.lastMessage.contains("added"));
        assertTrue(storage.saved, "Storage should be saved");
    }

    @Test
    public void exitCase_bye_exitSuccess() throws MeowException {
        boolean exit = Parser.parse("bye", tasks, ui, storage);
        assertTrue(exit);
        assertTrue(ui.lastMessage.contains("Bye"));
    }

    private static class StubUi extends Ui {
        private String lastMessage = "";
        @Override
        public void printExitMessage() {
            lastMessage = "Bye!";
        }
        @Override
        public void printAddedTask(Task t, int size) {
            lastMessage = "added: " + t.toString();
        }
        @Override
        public void printTasks(TaskList tasks) {
            lastMessage = "listing tasks";
        }
        @Override
        public void printDeletedTask(Task t, int size) {
            lastMessage = "deleted: " + t.toString();
        }
        @Override
        public void printMarked(Task t) {
            lastMessage = "marked: " + t.toString();
        }
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
