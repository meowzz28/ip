package meow.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void constructor_normalInput_success() {
        LocalDateTime from = LocalDateTime.of(2025, 8, 25, 18, 0);
        LocalDateTime to = LocalDateTime.of(2025, 8, 25, 18, 30);
        Event event = new Event("meeting", from, to);
        assertFalse(event.isDone);
        assertEquals(from, event.from);
        assertEquals("meeting", event.description);
    }

    @Test
    public void toString_meeting_success() {
        LocalDateTime from = LocalDateTime.of(2025, 8, 25, 18, 0);
        LocalDateTime to = LocalDateTime.of(2025, 8, 25, 18, 30);
        Event event = new Event("meeting", from, to);

        String expected = "[E][ ] meeting (from: Aug 25 2025 06:00 pm to: Aug 25 2025 06:30 pm )";
        assertEquals(expected, event.toString());
    }
}
