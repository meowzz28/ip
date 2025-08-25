package meow.task;

/**
 * Represents a generic task.
 * This is the parent class of specific task types like Todo, Deadline, and Event.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns a status icon representing whether the task is done.
     *
     * @return "X" if the task is done, " " (space) otherwise
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as done.
     */
    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    public String saveTaskString() {
        return "";
    }

    /**
     * Returns a string representation of the task.
     *
     * @return a formatted string representing the task
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] "  + description;
    }


    /**
     * Returns whether the task is marked as done.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean isDone() {
        return this.isDone;
    }
}
