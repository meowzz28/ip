package meow.main;

import meow.exception.MeowException;

public class Meow {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Meow(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.getTasks());
        } catch (MeowException e) {
            ui.printLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.printGreetMessage();
        while (true) {
            try {
                String input = ui.readCommand();
                boolean isExit = Parser.parse(input, tasks, ui, storage);
                if (isExit) {
                    break;
                }
            } catch (MeowException e) {
                ui.printError(e.getMessage());
            } catch (Exception e) {
                ui.printError("Unexpected error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Meow("./data/meow.txt").run();
    }
}
