import java.util.Scanner;

public class Meow {
    private final Scanner scanner = new Scanner(System.in);
    private final Task[] list = new Task[100];
    private int count = 0;

    private void startRun() {

        printGreetMessage();

        while (true) {

            String input = scanner.nextLine();
            String[] words = input.split(" ");

            try {
                if (input.equals("bye")) {

                    printExitMessage();
                    break;

                } else if (input.equals("list")) {

                    printList();

                } else if (words[0].equals("mark")) {

                    if (words.length < 2) {
                        throw new MeowException("OOPS!!! Please tell me which task number to mark.");
                    }
                    markDone(Integer.parseInt(words[1]) - 1);

                } else if (words[0].equals("unmark")) {

                    if (words.length < 2) {
                        throw new MeowException("OOPS!!! Please tell me which task number to unmark.");
                    }
                    markUndone(Integer.parseInt(words[1]) - 1);

                } else if (words[0].equals("todo")) {

                    String description = input.substring("todo".length()).trim();
                    if (description.isEmpty()) {
                        throw new MeowException("OOPS!!! The description of a todo cannot be empty.");
                    }
                    Todo t = new Todo(description);
                    printAddedTask(t);

                } else if (words[0].equals("deadline")) {

                    if (!input.contains("/by")) {
                        throw new MeowException("OOPS!!! A deadline must include '/by'. Example: deadline return book /by Sunday");
                    }
                    String[] parts = input.split(" /by ", 2);
                    String description = parts[0].substring("deadline".length()).trim();
                    if (description.isEmpty()) {
                        throw new MeowException("OOPS!!! The description of a deadline cannot be empty!");
                    }
                    String by = parts[1].trim();
                    Deadline t = new Deadline(description, by);
                    printAddedTask(t);

                } else if (words[0].equals("event")) {

                    if (!input.contains("/from") || !input.contains("/to")) {
                        throw new MeowException("OOPS!!! An event must include both '/from' and '/to'. Example: event meeting /from Mon 2pm /to 4pm");
                    }
                    String[] firstSplit = input.split(" /from ", 2);
                    String description = firstSplit[0].substring("event".length()).trim();
                    if (description.isEmpty()) {
                        throw new MeowException("OOPS!!! The description of an event cannot be empty!");
                    }
                    String[] secondSplit = firstSplit[1].split(" /to ", 2);
                    String from = secondSplit[0].trim();
                    String to = secondSplit[1].trim();
                    Event t = new Event(description, from, to);
                    printAddedTask(t);

                } else {
                    throw new MeowException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (MeowException e) {
                printLine();
                System.out.println("\t" + e.getMessage());
                printLine();
            }
        }
    }

    private void printGreetMessage() {
        printLine();
        System.out.println("\tHello! I'm Meow\n\tWhat can I do for you?");
        printLine();
    }

    private void printExitMessage() {
        printLine();
        System.out.println("\t Bye. Hope to see you again soon!");
        printLine();
    }

    private void printAddedTask(Task task) {
        list[count++] = task;
        printLine();
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + task);
        System.out.println("\tNow you have " + this.count + " tasks in the list.");
        printLine();
    }

    private void printList() {
        printLine();
        for(int i = 0; i < count; i++){
            System.out.println("\t" + (i + 1) + ". " + list[i]);
        }
        printLine();
    }

    private void markDone(int index) {
        Task task = list[index];
        task.markDone();
        printLine();
        System.out.println("\tNice! I've marked this task as done:");
        System.out.println("\t  " + task);
        printLine();
    }

    private void markUndone(int index) {
        Task task = list[index];
        task.markUndone();
        printLine();
        System.out.println("\tOK, I've marked this task as not done yet:");
        System.out.println("\t  " + task);
        printLine();
    }

    private void printLine() {
        System.out.println("\t____________________________________________________________");
    }

    public static void main(String[] args) {
        Meow meow = new Meow();
        meow.startRun();
    }
}
