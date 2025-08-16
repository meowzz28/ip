import java.util.Scanner;

public class Meow {
    private Scanner scanner = new Scanner(System.in);

    private void startRun() {
        printGreetMessage();
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                printExitMessage();
                break;
            } else {
                printMessage(input);
            }
        }
    }

    private void printGreetMessage() {
        System.out.println("\t____________________________________________________________");
        System.out.println("\tHello! I'm Meow\n\tWhat can I do for you?");
        System.out.println("\t____________________________________________________________");
    }

    private void printExitMessage() {
        System.out.println("\t____________________________________________________________");
        System.out.println("\t Bye. Hope to see you again soon!");
        System.out.println("\t____________________________________________________________");
    }

    private void printMessage(String input) {
        System.out.println("\t____________________________________________________________");
        System.out.println("\t" + input);
        System.out.println("\t____________________________________________________________");
    }

    public static void main(String[] args) {
        Meow meow = new Meow();
        meow.startRun();
    }
}
