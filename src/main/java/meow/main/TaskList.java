package meow.main;

import meow.task.Task;

import java.util.ArrayList;

/**
 * Manages a list of Task objects.
 * It provides methods to add, delete, retrieve, and update tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param tasks the initial list of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index the index of the task to retrieve
     * @return the Task at the given index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    public Task delete(int index) {
        return tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying ArrayList of tasks.
     *
     * @return the ArrayList of tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index the index of the task to mark as done
     * @return the Task that was marked
     */
    public Task markDone(int index) {
        Task task = tasks.get(index);
        task.markDone();
        return task;
    }

    public Task markUndone(int index) {
        Task task = tasks.get(index);
        task.markUndone();
        return task;
    }

}
