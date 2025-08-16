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
            if (input.equals("bye")) {
                printExitMessage();
                break;
            } else if (input.equals("list")) {
                printList();
            } else if (words[0].equals("mark")) {
                markDone(Integer.parseInt(words[1]) - 1);
            } else if (words[0].equals("unmark")) {
                markUndone(Integer.parseInt(words[1]) - 1);
            }else {
                printMessage(input);
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

    private void printMessage(String input) {
        Task newTask = new Task(input);
        list[count++] = newTask;
        printLine();
        System.out.println("\tadded: " + input);
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
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        printLine();
    }

    private void markUndone(int index) {
        Task task = list[index];
        task.markUndone();
        printLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
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
