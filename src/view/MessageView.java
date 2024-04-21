package fr.univ_lyon1.info.m1.elizagpt.view;

import fr.univ_lyon1.info.m1.elizagpt.controller.Controller;
import fr.univ_lyon1.info.m1.elizagpt.model.Message;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;

/**
 * Creates and handles all the message interface.
 */
public class MessageView {
    static final String BASE_STYLE = "-fx-padding: 8px; "
            + "-fx-margin: 5px; "
            + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10,0,0,0); "
            + "-fx-background-radius: 8px; " + "-fx-border-style: solid inside; "
            + "-fx-border-radius: 8px";
    static final String USER_STYLE = "-fx-background-color: rgba(105, 33, 91, 0.5);"
            + " -fx-border-color: rgba(105, 33, 91, 0.15);"
            + " -fx-text-fill: white; " + BASE_STYLE;
    static final String ELIZA_STYLE = "-fx-background-color: rgba(185, 185, 185, 0.5);"
            + " -fx-border-color: rgba(185, 185, 185, 0.15);"
            + " -fx-text-fill: #12121f; " + BASE_STYLE;
    private final VBox view;
    private final ScrollPane viewScroll;
    private Controller controller;

    /**
     * Creates the message scrolling interface.
     * @param controller
     */
    public MessageView(final Controller controller) {
        this.controller = controller;
        view = new VBox(15);
        view.setStyle("-fx-padding: 10px;");
        viewScroll = new ScrollPane();
        viewScroll.setContent(view);
        viewScroll.vvalueProperty().bind(view.heightProperty());
        viewScroll.setFitToWidth(true);
        VBox.setVgrow(viewScroll, Priority.ALWAYS);
    }

    /**
     * Updates the message list. Also notifies the controller in case of a message deletion.
     * @param messageList
     */
    // TODO : Add copy and delete button when the controller is created
    public void update(final ArrayList<Message> messageList) {
        this.view.getChildren().removeAll();
        this.view.getChildren().clear();
        for (Message mess : messageList) {
            VBox vBox = new VBox();
            HBox hBox = new HBox();

            final Label user = new Label();
            user.setStyle("-fx-font-size: 12px; -fx-text-fill: #6e6e6e;");

            final Label label = new Label(mess.getData());
            label.setWrapText(true);
            label.setTextAlignment(TextAlignment.JUSTIFY);
            label.setMaxWidth(300);

            // Copy and delete buttons
            HBox buttonBar = new HBox();
            buttonBar.setAlignment(Pos.CENTER_LEFT);
            Button delete = new Button("S");
            delete.setShape(new Circle(2));
            delete.setMaxSize(6, 6);
            delete.setOnAction(e -> {
                controller.deleteMessage(mess.getId());
            });
            Button copy = new Button("C");
            copy.setShape(new Circle(2));
            copy.setMaxSize(6, 6);
            copy.setOnAction(e -> {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(mess.getData());
                clipboard.setContent(content);
            });

            buttonBar.getChildren().addAll(delete, copy);
            buttonBar.setOpacity(0);
            hBox.setOnMouseEntered(e -> {
                buttonBar.setOpacity(1);
            });
            hBox.setOnMouseExited(e -> {
                buttonBar.setOpacity(0);
            });
            switch (mess.getMessageSource()) {
                case USER:
                    user.setText("Vous");
                    label.setStyle(USER_STYLE);
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    vBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.getChildren().addAll(buttonBar, label);
                    break;
                case ELIZA:
                    label.setStyle(ELIZA_STYLE);
                    user.setText("Eliza");
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    vBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.getChildren().addAll(label, buttonBar);
                    break;
                default:
                    break;
            }
            vBox.getChildren().addAll(user, hBox);
            view.getChildren().add(vBox);
        }
    }

    public ScrollPane getViewScroll() {
        return this.viewScroll;
    }
}
