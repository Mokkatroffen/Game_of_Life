import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Main is the initiator of the the program Game Of Life.
 *
 * @author Andreas Jacobsen.
 * @version 0.2 - April 18, 2016.
 *          Main loads the .fxml file, sets icon and starts the launch method that initilazises the entire program.
 *          Main also dynamically setts the size of the application.
 * @see javafx.application.Application The entry point for JavaFX applications is the Application class.
 */
public class Main extends Application {
    private int width = 900;
    private int height = 900;

    /**
     * The start method initialises the stage Stage while the Exception controls any exceptions.
     *
     * @param stage sets the stage of the method.
     * @author Boris Illievski.
     * @version 1.0 - May 05, 2016.
     * @see Exception Constructs a new exception with {@code null} as its detail message.
     * @see Stage The JavaFX {@code Stage} class is the top level JavaFX container.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GoL/View/grafikk.fxml"));

        Scene scene = new Scene(root, width, height);
        addIcon(stage);
        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.show();
    }
    /**
     *main starts the program
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05, 2016
     *
     * @param args holds the arguments that is run
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void addIcon(Stage stage) {
        Image image = new Image(this.getClass().getResource("GoL/View/GoL.gif").toString());
        stage.getIcons().add(image);
        ImageView imageIcon = new ImageView(image);
        imageIcon.setFitWidth(50);
        imageIcon.setPreserveRatio(true);
    }
}
