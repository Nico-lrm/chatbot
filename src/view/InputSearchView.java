package fr.univ_lyon1.info.m1.elizagpt.view;

import fr.univ_lyon1.info.m1.elizagpt.controller.Controller;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
/**
 * Creates and handles all user search inputs.
 */
public class InputSearchView {
    private final HBox input;
    private static int nextId = 0;
    private int id;

    /**
     * Creates inputs and handles their behaviors with the controller.
     * @param controller
     */
    public InputSearchView(final Controller controller) {
        this.id = nextId++;
        input = new HBox();
        input.setStyle("-fx-padding: 10px;");
        input.setAlignment(Pos.CENTER);

        Label label = new Label("Rechercher : ");
        TextField searchTextField = new TextField();
        HBox.setHgrow(searchTextField, Priority.ALWAYS);
        searchTextField.setOnKeyTyped(e -> {
            controller.searchMessage(searchTextField.getText(), id);
        });
        String[] searchMethod = {"Regex", "Substring", "Character"};
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(searchMethod));
        comboBox.setValue(searchMethod[0]);
        comboBox.setOnAction(e -> {
            controller.setSearchProcessor(comboBox.getValue(), id);
            searchTextField.setText("");
        });
        input.getChildren().addAll(label, searchTextField, comboBox);
    }

    public HBox getInput() {
        return this.input;
    }
}
