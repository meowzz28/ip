package meow.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import meow.exception.MeowException;
import meow.task.Task;

/**
 * Handles reading from and writing to the task storage file.
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty() : "File path cannot be null or empty";
        this.filePath = filePath;
    }

    /**
     * Gets tasks from the storage file.
     * If the file does not exist, it creates a new empty file and returns an empty list.
     *
     * @return an ArrayList of Task objects loaded from the file
     * @throws MeowException if there is an I/O error or if a saved task is invalid
     */
    public ArrayList<Task> getTasks() throws MeowException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File f = new File(this.filePath);
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
                return tasks;
            }
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String line = s.nextLine();
                Task task = Parser.parseSavedTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            s.close();
        } catch (IOException e) {
            throw new MeowException("Error loading file: " + e.getMessage());
        }
        return tasks;
    }

    public void save(TaskList tasks) {
        try {
            Path path = Path.of(filePath);
            Files.write(path,
                    tasks.getTasks().stream()
                            .map(Task::saveTaskString)
                            .toList());
        } catch (IOException exception) {
            System.out.println("\tError saving tasks: " + exception.getMessage());
        }
    }
}
