import java.util.Scanner;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    protected void printGreetMessage() {
        printLine();
        System.out.println("\tHello! I'm Meow\n\tWhat can I do for you?");
        printLine();
    }

    protected void printExitMessage() {
        printLine();
        System.out.println("\t Bye. Hope to see you again soon!");
        printLine();
    }

    private void printLine() {
        System.out.println("\t____________________________________________________________");
    }

    protected void printError(String message) {
        printLine();
        System.out.println("\t" + message);
        printLine();
    }

    protected void printAddedTask(Task task, int total) {
        printLine();
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + task);
        System.out.println("\tNow you have " + total + " tasks in the list.");
        printLine();
    }

    protected void printDeletedTask(Task task, int total) {
        printLine();
        System.out.println("\tNoted. I've removed this task:");
        System.out.println("\t  " + task);
        System.out.println("\tNow you have " + total + " tasks in the list.");
        printLine();
    }

    protected void printMarked(Task task) {
        printLine();
        if (task.isDone) {
            System.out.println("\tNice! I've marked this task as done:");
        } else {
            System.out.println("\tOK, I've marked this task as not done yet:");
        }
        System.out.println("\t  " + task);
        printLine();
    }

    protected void printTasks(TaskList tasks) {
        printLine();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i));
        }
        printLine();
    }

    protected String readCommand() {
        return scanner.nextLine();
    }

    protected void printLoadingError() {
        printError("Error loading file. Starting with empty task list.");
    }
}
