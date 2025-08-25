package meow.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with a description and a due date/time.
 */
public class Deadline extends Task {

    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns a string representation of the Deadline task,
     *
     * @return a formatted string representing the deadline task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + this.by.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a")) + ")";
    }

    @Override
    public String saveTaskString() {
        return "D | " + (this.isDone ? "1" : "0") + " | " + this.description + " | "
                + this.by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
