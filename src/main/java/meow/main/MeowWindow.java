package meow.main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the main GUI.
 */
public class MeowWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Meow meow;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/oiia.jpeg"));
    private Image meowImage = new Image(this.getClass().getResourceAsStream("/images/huh.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(
                DialogBox.getMeowDialog("Hello! I'm Meow\nWhat can I do for you?", meowImage)
        );
    }

    /** Injects the Meow instance */
    public void setMeow(Meow d) {
        meow = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Moew's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = meow.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMeowDialog(response, meowImage)
        );

        userInput.clear();

        if (response.equals("Bye. Hope to see you again soon!")) {
            Stage stage = (Stage) scrollPane.getScene().getWindow();
            stage.close();
        }


    }
}
