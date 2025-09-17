package meow.main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import meow.exception.MeowException;
import meow.task.Deadline;
import meow.task.Event;
import meow.task.FixedDurationTask;
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
    public static String parse(String input, TaskList tasks, Ui ui, Storage storage) throws MeowException {

        String[] words = input.split(" ");

        switch (words[0]) {
        case "bye":
            return ui.getExitMessage();

        case "list":
            return ui.getTasks(tasks);

        case "mark":
            if (words.length < 2) {
                throw new MeowException(" Please tell me which task number to mark.");
            }

            Task mark = tasks.markDone(Integer.parseInt(words[1]) - 1);
            storage.save(tasks);
            return ui.getMarked(mark);

        case "unmark":
            if (words.length < 2) {
                throw new MeowException(" Please tell me which task number to unmark.");
            }

            Task unmark = tasks.markUndone(Integer.parseInt(words[1]) - 1);
            storage.save(tasks);
            return ui.getMarked(unmark);

        case "todo":
            String description1 = input.substring("todo".length()).trim();

            if (description1.isEmpty()) {
                throw new MeowException(" The description of a todo cannot be empty.");
            }

            Todo todo = new Todo(description1);
            tasks.add(todo);
            storage.save(tasks);
            return ui.getAddedTask(todo, tasks.size());

        case "deadline":
            if (!input.contains("/by")) {
                throw new MeowException(" A deadline must include '/by'. "
                        + "Example: deadline return book /by Sunday");
            }

            String[] parts = input.split(" /by ", 2);
            String description2 = parts[0].substring("deadline".length()).trim();

            if (description2.isEmpty()) {
                throw new MeowException(" The description of a deadline cannot be empty!");
            }

            Deadline deadline = createDeadlineTask(description2, parts[1].trim());
            tasks.add(deadline);
            storage.save(tasks);
            return ui.getAddedTask(deadline, tasks.size());

        case "event":
            if (!input.contains("/from") || !input.contains("/to")) {
                throw new MeowException(" An event must include both '/from' and '/to'. "
                        + "Example: event meeting /from Mon 2pm /to 4pm");
            }

            String[] firstSplit = input.split(" /from ", 2);
            String description3 = firstSplit[0].substring("event".length()).trim();

            if (description3.isEmpty()) {
                throw new MeowException(" The description of an event cannot be empty!");
            }

            String[] secondSplit = firstSplit[1].split(" /to ", 2);
            Event event = createEventTask(description3, secondSplit[0].trim(), secondSplit[1].trim());
            tasks.add(event);
            storage.save(tasks);
            return ui.getAddedTask(event, tasks.size());

        case "fixed":
            if (!input.contains("/needs")) {
                throw new MeowException(" A fixed duration task must include '/needs'. "
                        + "Example: fixed read report /needs 2h 30m");
            }

            String[] fixedSplit = input.split(" /needs", 2);
            String fixedDescription = fixedSplit[0].substring("fixed".length()).trim();

            if (fixedDescription.isEmpty()) {
                throw new MeowException(" The description of a fixed duration task cannot be empty!");
            }

            Duration duration = parseDuration(fixedSplit[1].trim());
            FixedDurationTask fixedTask = new FixedDurationTask(fixedDescription, duration);
            tasks.add(fixedTask);
            storage.save(tasks);
            return ui.getAddedTask(fixedTask, tasks.size());

        case "delete":
            if (words.length < 2) {
                throw new MeowException(" Please tell me which task number to delete.");
            }

            Task deleted = tasks.delete(Integer.parseInt(words[1]) - 1);
            storage.save(tasks);
            return ui.getDeletedTask(deleted, tasks.size());

        case "find":
            if (words.length < 2) {
                throw new MeowException(" Please tell me the keyword to search.");
            }

            String keyword = words[1];
            ArrayList<Task> found = tasks.findTasks(keyword);
            return ui.getFoundTasks(found);

        default:
            throw new MeowException(" I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Parses a duration string into a Duration object.
     *
     * @param input the raw duration string entered by the user
     * @return a Duration representing the parsed duration
     * @throws MeowException if the input format is invalid
     */
    private static Duration parseDuration(String input) throws MeowException {
        try {
            long hours = 0;
            long minutes = 0;

            input = input.toLowerCase().trim();
            String[] parts = input.split(" ");

            for (String part : parts) {
                if (part.endsWith("h")) {
                    hours += Long.parseLong(part.replace("h", ""));
                } else if (part.endsWith("m")) {
                    minutes += Long.parseLong(part.replace("m", ""));
                } else {
                    throw new NumberFormatException();
                }
            }

            return Duration.ofHours(hours).plusMinutes(minutes);
        } catch (NumberFormatException e) {
            throw new MeowException(" Duration must be in format like '2h', '90m', or '1h 30m'.");
        }
    }

    /**
     * Parses a saved task string from storage into a Task object.
     *
     * @param line the raw saved task string from storage
     * @return the reconstructed Task object
     * @throws MeowException if the saved string is invalid or parsing fails
     */
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
        case "F":
            long minutes = Long.parseLong(info[3]);
            task = new FixedDurationTask(info[2], Duration.ofMinutes(minutes));
            break;
        default:
            assert false : "Unexpected task type: " + type;
        }
        if (isDone) {
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
            throw new MeowException(" Please enter date "
                    + "in yyyy-MM-dd format optionally followed by time HH:mm.");
        }
    }

    private static Deadline createDeadlineTask(String description, String byString) throws MeowException {
        LocalDateTime by;

        try {
            by = parseDateTime(byString);
        } catch (DateTimeParseException e) {
            throw new MeowException(" Please enter date "
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
            throw new MeowException(" Please enter date "
                    + "in yyyy-MM-dd format optionally followed by time HH:mm.");
        }

        return new Event(description, from, to);
    }

}
