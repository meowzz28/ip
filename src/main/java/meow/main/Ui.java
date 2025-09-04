package meow.main;

import java.util.ArrayList;

import meow.task.Task;

/**
 * Handles messages to users for the GUI.
 */
public class Ui {

    public String getGreetMessage() {
        return "Hello! I'm Meow\nWhat can I do for you?";
    }

    public String getExitMessage() {
        return "Bye. Hope to see you again soon!";
    }

    public String getErrorMessage(String message) {
        return message;
    }

    public String getAddedTask(Task task, int total) {
        return "Got it. I've added this task:\n"
                + "  " + task + "\n"
                + "Now you have " + total + " tasks in the list.";
    }

    public String getDeletedTask(Task task, int total) {
        return "Noted. I've removed this task:\n"
                + "  " + task + "\n"
                + "Now you have " + total + " tasks in the list.";
    }

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

    public String getFoundTasks(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }
}
