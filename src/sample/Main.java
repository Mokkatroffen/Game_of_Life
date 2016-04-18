package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main is the initiator of the the program Game Of Life
 *
 * @author Andreas Jacobsen
 * @version 0.2 - April 18, 2016
 *
 * For extended information about Application:
 * @see javafx.application.Application
 */

public class Main extends Application {
    @Override

/**
 * The start method initialises the stage Stage while the Exception controls any exceptions.
 *
 * @author Boris Illievski
 * @version 0.2 - April 18, 2016
 *
 * @see Exception
 * @see Stage
 */
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().
                getResource("grafikk.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}