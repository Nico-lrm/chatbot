package fr.univ_lyon1.info.m1.elizagpt.view;

import fr.univ_lyon1.info.m1.elizagpt.controller.Controller;
import fr.univ_lyon1.info.m1.elizagpt.model.Message;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Main view class. Contains ,articulates and updates every view element.
 */
public class Display {
    private MessageView messageView;
    private InputMessageView inputMessageView;
    private InputSearchView inputSearchView;

    /**
     * Creates the main interface.
     * @param controller
     * @param stage
     * @param width
     * @param height
     */
    public Display(final Controller controller,
                   final Stage stage,
                   final int width,
                   final int height) {
        stage.setTitle("Eliza GPT");
        final VBox root = new VBox(10);

        this.inputSearchView = new InputSearchView(controller);
        this.messageView = new MessageView(controller);
        this.inputMessageView = new InputMessageView(controller);

        root.getChildren().add(inputSearchView.getInput());
        root.getChildren().add(messageView.getViewScroll());
        root.getChildren().add(inputMessageView.getInput());

        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Updates the message list for the message view.
     * @param messageList
     */
    public void update(final ArrayList<Message> messageList) {
        this.messageView.update(messageList);
    }
}
