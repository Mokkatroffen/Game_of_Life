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
 *
 * @see javafx.application.Application The entry point for JavaFX applications is the Application class.
 */
public class Main extends Application {
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
        Parent root = FXMLLoader.load(getClass().getResource("GoL/View/grafikk.fxml"));

        Scene scene = new Scene(root, width, height);
        addIcon(stage);
        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.show();
    }

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
