/**
 * This class starts the program.
 *
 * @author Alexandra Härnström
 * @version 1
 */

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method instantiates a new GUI object.
     * @param stage - The primary Stage
     */
    @Override
    public void start(Stage stage) {
        new GUI(stage);
    }
}