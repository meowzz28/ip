package meow.task;

import java.time.Duration;

/**
 * Represents a task with a fixed duration but no specific start or end time.
 * Example: "Read sales report (needs: 2h 30m)".
 */
public class FixedDurationTask extends Task {
    private final Duration duration;

    public FixedDurationTask(String description, Duration duration) {
        super(description);
        this.duration = duration;
    }

    /**
     * Returns a string representation of this task for display to the user.
     *
     * @return formatted string representation of the task
     */
    @Override
    public String toString() {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();

        String durationStr = (hours > 0 ? hours + "h " : "")
                + (minutes > 0 ? minutes + "m" : "");

        return "[F]" + super.toString() + " (needs: " + durationStr.trim() + ")";
    }

    /**
     * Returns a string representation of this task for saving to storage.
     *
     * @return formatted storage string
     */
    @Override
    public String saveTaskString() {
        return "F | " + (isDone ? "1" : "0") + " | " + description + " | " + duration.toMinutes();
    }
}
