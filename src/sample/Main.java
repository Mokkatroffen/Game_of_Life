package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main is the initiator of the the program Game Of Life n
 *
 * @author Andreas Jacobsen
 *
 * @version 1.0 - April 18, 2016
 *
 * For extended information about Application:
 * @see javafx.application.Application
 */
public class Main extends Application {
    @Override

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