package meow.main;

import java.util.ArrayList;

import meow.task.Task;

/**
 * Handles messages to users for the GUI.
 */
public class Ui {

    public String getGreetMessage() {
        return "Huh? What can I do for you?";
    }

    public String getExitMessage() {
        return "Bye. Hope to see you again soon!";
    }

    public String getErrorMessage(String message) {
        return message;
    }

    /**
     * Returns a message confirming a task has been added.
     *
     * @param task  the task that was added
     * @param total the total number of tasks after addition
     * @return confirmation message string
     */
    public String getAddedTask(Task task, int total) {
        return "Got it. I've added this task:\n"
                + "  " + task + "\n"
                + "Now you have " + total + " tasks in the list.";
    }

    /**
     * Returns a message confirming a task has been deleted.
     *
     * @param task  the task that was deleted
     * @param total the total number of tasks after deletion
     * @return confirmation message string
     */
    public String getDeletedTask(Task task, int total) {
        return "Noted. I've removed this task:\n"
                + "  " + task + "\n"
                + "Now you have " + total + " tasks in the list.";
    }

    /**
     * Returns a message confirming a task has been marked as done or not done.
     *
     * @param task the task that was marked
     * @return confirmation message string
     */
    public String getMarked(Task task) {
        String status = task.isDone()
                ? "Nice! I've marked this task as done:\n"
                : "OK, I've marked this task as not done yet:\n";
        return status + "  " + task;
    }

    public String getTasks(TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are your tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Returns a formatted list of tasks matching a search query.
     *
     * @param tasks the list of matching tasks
     * @return formatted string of found tasks
     */
    public String getFoundTasks(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }
}
