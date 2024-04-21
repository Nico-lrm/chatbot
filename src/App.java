package fr.univ_lyon1.info.m1.elizagpt;

import fr.univ_lyon1.info.m1.elizagpt.controller.Controller;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageDao;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageSource;
import fr.univ_lyon1.info.m1.elizagpt.model.VerbFileParser;
import fr.univ_lyon1.info.m1.elizagpt.view.Display;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for the application (structure imposed by JavaFX).
 */
public class App extends Application {

    /**
     * With javafx, start() is called when the application is launched.
     */
    @Override
    public void start(final Stage stage) throws Exception {
        new VerbFileParser("french-verb-conjugation.csv", ",");
        Controller controller = new Controller(new MessageProcessor());
        Display display = new Display(controller, stage, 400, 400);
        //Display display2 = new Display(controller, new Stage(), 400, 400);
        controller.addDisplay(display);
        //controller.addDisplay(display2);
        MessageDao messageDao = MessageDao.getInstance();
        messageDao.addObserver(controller);
        messageDao.create("Bonjour", MessageSource.ELIZA);
    }


    /**
     * A main method in case the user launches the application using
     * App as the main class.
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}
