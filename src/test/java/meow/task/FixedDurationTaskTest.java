package meow.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FixedDurationTaskTest {
    private FixedDurationTask task;

    @BeforeEach
    void setUp() {
        task = new FixedDurationTask("read report", Duration.ofMinutes(135)); // 2h 15m
    }

    @Test
    void testToString_notDone() {
        String expected = "[F][ ] read report (needs: 2h 15m)";
        assertEquals(expected, task.toString());
    }

    @Test
    void testToString_done() {
        task.markDone();
        String expected = "[F][X] read report (needs: 2h 15m)";
        assertEquals(expected, task.toString());
    }

    @Test
    void testSaveTaskString_notDone() {
        String expected = "F | 0 | read report | 135";
        assertEquals(expected, task.saveTaskString());
    }

    @Test
    void testSaveTaskString_done() {
        task.markDone();
        String expected = "F | 1 | read report | 135";
        assertEquals(expected, task.saveTaskString());
    }

    @Test
    void testToString_hoursOnly() {
        FixedDurationTask hoursTask = new FixedDurationTask("coding", Duration.ofHours(2));
        String expected = "[F][ ] coding (needs: 2h)";
        assertEquals(expected, hoursTask.toString());
    }

    @Test
    void testToString_minutesOnly() {
        FixedDurationTask minutesTask = new FixedDurationTask("exercise", Duration.ofMinutes(45));
        String expected = "[F][ ] exercise (needs: 45m)";
        assertEquals(expected, minutesTask.toString());
    }

}
