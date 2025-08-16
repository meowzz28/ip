import java.util.Scanner;

public class Meow {
    private final Scanner scanner = new Scanner(System.in);
    private final String[] list = new String[100];
    private int count = 0;

    private void startRun() {
        printGreetMessage();
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                printExitMessage();
                break;
            } else if(input.equals("list")) {
                printList();
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
        System.out.println("\tadded: " + input);
        System.out.println("\t____________________________________________________________");
        list[count++] = input;
    }

    private void printList() {
        System.out.println("\t____________________________________________________________");
        for(int i = 0; i < count; i++){
            System.out.println("\t" + (i + 1) + ". " + list[i]);
        }
        System.out.println("\t____________________________________________________________");
    }

    public static void main(String[] args) {
        Meow meow = new Meow();
        meow.startRun();
    }
}
