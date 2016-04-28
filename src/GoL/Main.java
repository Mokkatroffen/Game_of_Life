package GoL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**1
 * Main is the initiator of the the program Game Of Life.
 *
 * @author Andreas Jacobsen.
 * @version 0.2 - April 18, 2016.
 *
 * @see javafx.application.Application The entry point for JavaFX applications is the Application class.
 */

public class Main extends Application {
    //prefHeight="600.0" prefWidth="900.0";
    private int width = 900;
    private int height = 900;

    /**
 * The start method initialises the stage Stage while the Exception controls any exceptions.
 *
 * @author Boris Illievski.
 * @version 0.2 - April 18, 2016.
 *
 * @param stage sets the stage of the method.
 * @see Exception Constructs a new exception with {@code null} as its detail message.
 * @see Stage The JavaFX {@code Stage} class is the top level JavaFX container.
 */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("grafikk.fxml"));

        Scene scene = new Scene(root, width, height);

        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.show();
    }
//push
    public static void main(String[] args) {
        launch(args);
    }
}
