package meow.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import meow.exception.MeowException;

/**
 * Main class for Meow Java Project
 * Responsible for the main interaction loop with the user
 */
public class Meow extends Application {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Meow(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.getTasks());
        } catch (MeowException e) {
            tasks = new TaskList();
        }
    }

    public Meow() {
        this("./data/meow.txt");
    }


    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Meow.class.getResource("/view/MeowWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MeowWindow>getController().setMeow(this); // inject the Meow instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            String response = Parser.parse(input, tasks, ui, storage);
            return response;
        } catch (MeowException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Unexpected error: " + e.getMessage();
        }
    }

}
