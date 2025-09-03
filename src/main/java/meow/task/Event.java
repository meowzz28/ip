package meow.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a description, start time, and end time.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the Event task,
     *
     * @return a formatted string representing the event task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + this.from.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a"))
                + " to: " + this.to.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a")) + " )";
    }

    @Override
    public String saveTaskString() {
        return "E | " + (this.isDone ? "1" : "0") + " | " + this.description + " | "
                + this.from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " | "
                + this.to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
