package meow.main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import meow.exception.MeowException;
import meow.task.Deadline;
import meow.task.Event;
import meow.task.Task;
import meow.task.Todo;

/**
 * Responsible for interpreting user commands.
 */
public class Parser {
    /**
     * Parses input command from command and executes the corresponding action,
     * Updates the TaskList and Storage if needed.
     *
     * @param input   raw input string entered by the user
     * @param tasks   current task list
     * @param ui      class for printing messages to the user
     * @param storage class for saving tasks
     * @return true if the user issued the exit command "bye", false otherwise
     * @throws MeowException if the command is invalid or improperly formatted
     */
    public static boolean parse(String input, TaskList tasks, Ui ui, Storage storage) throws MeowException {
        String[] words = input.split(" ");
        switch (words[0]) {
        case "bye":
            ui.printExitMessage();
            return true;
        case "list":
            ui.printTasks(tasks);
            break;
        case "mark":
            if (words.length < 2) {
                throw new MeowException("OOPS!!! Please tell me which task number to mark.");
            }
            Task mark = tasks.markDone(Integer.parseInt(words[1]) - 1);
            ui.printMarked(mark);
            storage.save(tasks);
            break;
        case "unmark":
            if (words.length < 2) {
                throw new MeowException("OOPS!!! Please tell me which task number to unmark.");
            }
            Task unmark = tasks.markUndone(Integer.parseInt(words[1]) - 1);
            ui.printMarked(unmark);
            storage.save(tasks);
            break;
        case "todo":
            String description1 = input.substring("todo".length()).trim();
            if (description1.isEmpty()) {
                throw new MeowException("OOPS!!! The description of a todo cannot be empty.");
            }
            Todo todo = new Todo(description1);
            tasks.add(todo);
            ui.printAddedTask(todo, tasks.size());
            storage.save(tasks);
            break;
        case "deadline":
            if (!input.contains("/by")) {
                throw new MeowException("OOPS!!! A deadline must include '/by'. "
                        + "Example: deadline return book /by Sunday");
            }
            String[] parts = input.split(" /by ", 2);
            String description2 = parts[0].substring("deadline".length()).trim();
            if (description2.isEmpty()) {
                throw new MeowException("OOPS!!! The description of a deadline cannot be empty!");
            }
            Deadline deadline = createDeadlineTask(description2, parts[1].trim());
            tasks.add(deadline);
            ui.printAddedTask(deadline, tasks.size());
            storage.save(tasks);
            break;
        case "event":
            if (!input.contains("/from") || !input.contains("/to")) {
                throw new MeowException("OOPS!!! An event must include both '/from' and '/to'. "
                        + "Example: event meeting /from Mon 2pm /to 4pm");
            }
            String[] firstSplit = input.split(" /from ", 2);
            String description3 = firstSplit[0].substring("event".length()).trim();
            if (description3.isEmpty()) {
                throw new MeowException("OOPS!!! The description of an event cannot be empty!");
            }
            String[] secondSplit = firstSplit[1].split(" /to ", 2);
            Event event = createEventTask(description3, secondSplit[0].trim(), secondSplit[1].trim());
            tasks.add(event);
            ui.printAddedTask(event, tasks.size());
            storage.save(tasks);
            break;
        case "delete":
            if (words.length < 2) {
                throw new MeowException("OOPS!!! Please tell me which task number to delete.");
            }
            Task deleted = tasks.delete(Integer.parseInt(words[1]) - 1);
            ui.printDeletedTask(deleted, tasks.size());
            storage.save(tasks);
            break;
        case "find":
            if (words.length < 2) {
                throw new MeowException("OOPS!!! Please tell me the keyword to search.");
            }
            String keyword = words[1];
            ArrayList<Task> found = tasks.findTasks(keyword);
            ui.printFoundTasks(found);
            break;
        default:
            throw new MeowException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
        return false;
    }

    public static Task parseSavedTask(String line) throws MeowException {
        String[] info = line.split("\\|");
        for (int i = 0; i < info.length; i++) {
            info[i] = info[i].trim();
        }
        String type = info[0];
        boolean isDone = info[1].equals("1");
        Task task = null;
        switch (type) {
        case "T":
            task = new Todo(info[2]);
            break;
        case "D":
            task = new Deadline(info[2], parseDateTime(info[3]));
            break;
        case "E":
            task = new Event(info[2], parseDateTime(info[3]), parseDateTime(info[4]));
            break;
        default:
        }
        if (task != null && isDone) {
            task.markDone();
        }
        return task;
    }

    private static LocalDateTime parseDateTime(String str) throws MeowException {
        try {
            return str.contains(" ")
                    ? LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    : LocalDateTime.parse(str + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException e) {
            throw new MeowException("OOPS!!! Please enter date "
                    + "in yyyy-MM-dd format optionally followed by time HH:mm.");
        }
    }

    private static Deadline createDeadlineTask(String description, String byString) throws MeowException {
        LocalDateTime by;
        try {
            by = parseDateTime(byString);
        } catch (DateTimeParseException e) {
            throw new MeowException("OOPS!!! Please enter date "
                    + "in yyyy-MM-dd format optionally followed by time HH:mm.");
        }
        return new Deadline(description, by);
    }

    private static Event createEventTask(String description, String fromString, String toString) throws MeowException {
        LocalDateTime from;
        LocalDateTime to;
        try {
            from = parseDateTime(fromString);
            to = parseDateTime(toString);
        } catch (DateTimeParseException e) {
            throw new MeowException("OOPS!!! Please enter date "
                    + "in yyyy-MM-dd format optionally followed by time HH:mm.");
        }
        return new Event(description, from, to);
    }

}
