package fr.univ_lyon1.info.m1.elizagpt.view;

import fr.univ_lyon1.info.m1.elizagpt.controller.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * Creates and handles all user message inputs.
 */
public class InputMessageView {
    private final Pane input;

    /**
     * Creates inputs and handles their behaviors with the controller.
     * @param controller
     */
    public InputMessageView(final Controller controller) {
        input = new HBox();
        input.setStyle("-fx-padding: 10px");
        final TextField text = new TextField();
        HBox.setHgrow(text, Priority.ALWAYS);
        text.requestFocus();
        text.setOnAction(e -> {
            if (text.getText().isBlank() || text.getText().isEmpty()) {
                return;
            }
            controller.sendMessage(text.getText());
            text.setText("");
        });
        final Button send = new Button("Envoyer");
        send.setOnAction(e -> {
            if (text.getText().isBlank() || text.getText().isEmpty()) {
                return;
            }
            controller.sendMessage(text.getText());
            text.setText("");
        });
        input.getChildren().addAll(text, send);
    }

    public Pane getInput() {
        return this.input;
    }

}
