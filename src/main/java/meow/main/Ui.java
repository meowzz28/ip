package meow.main;

import java.util.ArrayList;
import java.util.Scanner;

import meow.task.Task;

/**
 * Handles printing messages to users
 */
public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Prints a greeting message to the user when the program starts.
     */
    public void printGreetMessage() {
        printLine();
        System.out.println("\tHello! I'm meow.main.Meow\n\tWhat can I do for you?");
        printLine();
    }

    public void printExitMessage() {
        printLine();
        System.out.println("\t Bye. Hope to see you again soon!");
        printLine();
    }

    private void printLine() {
        System.out.println("\t____________________________________________________________");
    }

    /**
     * Prints an error message to the user.
     *
     * @param message the error message to display
     */
    public void printError(String message) {
        printLine();
        System.out.println("\t" + message);
        printLine();
    }


    /**
     * Prints a message indicating that a task has been added,
     * along with the current total number of tasks.
     *
     * @param task  the task that was added
     * @param total the total number of tasks after addition
     */
    public void printAddedTask(Task task, int total) {
        printLine();
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + task);
        System.out.println("\tNow you have " + total + " tasks in the list.");
        printLine();
    }

    /**
     * Prints a message indicating that a task has been deleted,
     * along with the current total number of tasks.
     *
     * @param task  the task that was deleted
     * @param total the total number of tasks after deletion
     */
    public void printDeletedTask(Task task, int total) {
        printLine();
        System.out.println("\tNoted. I've removed this task:");
        System.out.println("\t  " + task);
        System.out.println("\tNow you have " + total + " tasks in the list.");
        printLine();
    }

    /**
     * Prints a message indicating that a task has been marked
     * as done or not done.
     *
     * @param task the task that was marked
     */
    public void printMarked(Task task) {
        printLine();
        if (task.isDone()) {
            System.out.println("\tNice! I've marked this task as done:");
        } else {
            System.out.println("\tOK, I've marked this task as not done yet:");
        }
        System.out.println("\t  " + task);
        printLine();
    }

    /**
     * Prints all tasks in the given TaskList, numbered sequentially.
     *
     * @param tasks the TaskList to display
     */
    public void printTasks(TaskList tasks) {
        printLine();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i));
        }
        printLine();
    }

    /**
     * Reads a line of input from the user.
     *
     * @return the user's input as a String
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    public void printLoadingError() {
        printError("Error loading file. Starting with empty task list.");
    }

    public void printFoundTasks(ArrayList<Task> tasks) {
        printLine();
        System.out.println("\tHere are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i));
        }
        printLine();
    }
}
