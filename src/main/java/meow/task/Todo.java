package meow.task;

/**
 * Represents a Todo Task
 */
public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }


    /**
     * Returns a string representation of the todo task,
     *
     * @return a formatted string representing the todo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a string suitable for saving the todo task to a file.
     *
     * @return a formatted string representing the todo task for storage
     */
    @Override
    public String saveTaskString() {
        return "T | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }

}
