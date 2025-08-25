package meow.task;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    public String saveTaskString() {
        return "";
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] "  + description;
    }

    public boolean isDone() {
        return this.isDone;
    }
}
