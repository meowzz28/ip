package meow.main;

import meow.exception.MeowException;
import meow.task.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    protected ArrayList<Task> getTasks() throws MeowException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File f = new File("./data/meow.txt");
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

    protected void save(TaskList tasks) {
        try {
            FileWriter fw = new FileWriter(filePath);
            for (Task task : tasks.getTasks()) {
                fw.write(task.saveTaskString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException exception) {
            System.out.println("\tError saving tasks: " + exception.getMessage());
        }
    }
}
