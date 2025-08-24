import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;

public class Meow {
    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<Task> list = new ArrayList<>();

    private void startRun() {

        getTasks();
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
                        throw new MeowException("OOPS!!! A deadline must include '/by'. "
                                + "Example: deadline return book /by Sunday");
                    }
                    String[] parts = input.split(" /by ", 2);
                    String description = parts[0].substring("deadline".length()).trim();
                    if (description.isEmpty()) {
                        throw new MeowException("OOPS!!! The description of a deadline cannot be empty!");
                    }
                    Deadline t = createDeadlineTask(description, parts[1].trim());
                    printAddedTask(t);

                } else if (words[0].equals("event")) {
                    if (!input.contains("/from") || !input.contains("/to")) {
                        throw new MeowException("OOPS!!! An event must include both '/from' and '/to'. "
                                + "Example: event meeting /from Mon 2pm /to 4pm");
                    }
                    String[] firstSplit = input.split(" /from ", 2);
                    String description = firstSplit[0].substring("event".length()).trim();
                    if (description.isEmpty()) {
                        throw new MeowException("OOPS!!! The description of an event cannot be empty!");
                    }
                    String[] secondSplit = firstSplit[1].split(" /to ", 2);
                    Event t = createEventTask(description, secondSplit[0].trim(), secondSplit[1].trim());
                    printAddedTask(t);
                } else if (words[0].equals("delete")) {
                    if (words.length < 2) {
                        throw new MeowException("OOPS!!! Please tell me which task number to delete.");
                    }
                    deleteTask(Integer.parseInt(words[1]) - 1);
                }else {
                    throw new MeowException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }

            } catch (MeowException e) {
                printLine();
                System.out.println("\t" + e.getMessage());
                printLine();
            }
        }
    }

    private Deadline createDeadlineTask(String description, String byString) throws MeowException {
        LocalDateTime by;
        try {
            by = byString.contains(" ")
                 ? LocalDateTime.parse(byString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                 : LocalDateTime.parse(byString + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        } catch (DateTimeParseException e) {
            throw new MeowException("OOPS!!! Please enter date in yyyy-MM-dd format optionally followed by time HH:mm.");
        }
        return new Deadline(description, by);
    }

    private Event createEventTask(String description, String fromString, String toString) throws MeowException {
        LocalDateTime from;
        LocalDateTime to;
        try {
            from = fromString.contains(" ")
                   ? LocalDateTime.parse(fromString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                   : LocalDateTime.parse(fromString + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            to = toString.contains(" ")
                    ? LocalDateTime.parse(toString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    : LocalDateTime.parse(toString + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException e) {
            throw new MeowException("OOPS!!! Please enter date in yyyy-MM-dd format optionally followed by time HH:mm.");
        }
        return new Event(description, from, to);
    }

    private void getTasks() {
        try {
            File f = new File("./data/meow.txt");
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
                return;
            }
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String line = s.nextLine();
                String[] info = line.split("\\|");
                for (int i = 0; i < info.length; i++) {
                    info[i] = info[i].trim();
                }
                String type = info[0];
                boolean isDone = info[1].equals("1");
                Task task;
                switch (type) {
                case "T":
                    task = new Todo(info[2]);
                    break;
                case "D":
                    task = createDeadlineTask(info[2], info[3]);
                    break;
                case "E":
                    task = createEventTask(info[2], info[3], info[4]);
                    break;
                default:
                    continue;
                }
                if (isDone) {
                    task.markDone();
                }
                this.list.add(task);
            }
            s.close();
        } catch (MeowException | IOException e) {
            printLine();
            System.out.println("\t" + e.getMessage());
            printLine();
        }
    }

    private void saveTasks() {
        try {
            FileWriter fw = new FileWriter("./data/meow.txt");
            for (Task task : list) {
                fw.write(task.saveTaskString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException exception) {
            System.out.println("\tError saving tasks: " + exception.getMessage());
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
        this.list.add(task);
        saveTasks();
        printLine();
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + task);
        System.out.println("\tNow you have " + this.list.size() + " tasks in the list.");
        printLine();
    }

    private void printList() {
        printLine();
        for(int i = 0; i < this.list.size(); i++){
            System.out.println("\t" + (i + 1) + ". " + this.list.get(i));
        }
        printLine();
    }

    private void markDone(int index) {
        Task task = this.list.get(index);
        task.markDone();
        saveTasks();
        printLine();
        System.out.println("\tNice! I've marked this task as done:");
        System.out.println("\t  " + task);
        printLine();

    }

    private void markUndone(int index) {
        Task task = this.list.get(index);
        task.markUndone();
        saveTasks();
        printLine();
        System.out.println("\tOK, I've marked this task as not done yet:");
        System.out.println("\t  " + task);
        printLine();
    }

    private void printLine() {
        System.out.println("\t____________________________________________________________");
    }

    private void deleteTask(int index) throws MeowException {
        if (index < 0 || index >= list.size()) {
            throw new MeowException("OOPS!!! The task number you provided is invalid.");
        }
        Task removedTask = list.remove(index);
        saveTasks();
        printLine();
        System.out.println("\tNoted. I've removed this task:");
        System.out.println("\t  " + removedTask);
        System.out.println("\tNow you have " + list.size() + " tasks in the list.");
        printLine();
    }

    public static void main(String[] args) {
        Meow meow = new Meow();
        meow.startRun();
    }
}
