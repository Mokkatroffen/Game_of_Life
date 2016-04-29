package GoL;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.effect.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

//testikkel
public class Controller implements Initializable {
    @FXML
    Canvas canvas;
    @FXML
    Button startButton;
    @FXML
    Button nextGenButton;
    @FXML
    Button URLbutton;
    @FXML
    Slider slider;
    @FXML
    Label speedometer;

    @FXML
    CheckBox drawGrid;

    private GraphicsContext graphicsContext;
    private GameBoard gb = new GameBoard(11, 12);
    private Timeline timeline;
    double cellSize;

    public Controller() throws IOException {
        gb = new GameBoard(11, 12);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        canvas.setEffect(new Glow());
        graphicsContext = canvas.getGraphicsContext2D();

        //graphicsContext.setFill(Color.GREEN);
        graphicsContext.setStroke(Color.BLUE);
        draw();
        updateSpeed(5);
        addSliderListner();
        //URLbutton.setOnAction(event ->  );
        //nextGenButton.setOnAction(event -> canvasResizer()); ////// TODO:BYTT TIL WINDOW RESIZE
    }

    @FXML
    public void scrollHandler(ScrollEvent event) {
        double zoomFactor = 1.5;

        if (event.getDeltaY() <= 0) {
            // zoom out
            zoomFactor = 1 / zoomFactor;
        }
        zoom(zoomFactor, event.getSceneX(), event.getSceneY());
        //draw();
    }

    public void zoom(double factor, double x, double y) {
        // determine scale
        double oldScale = canvas.getScaleX();
        double scale = oldScale * factor;
        if (scale < 6 && scale > 0.4) {


            double f = (scale / oldScale) - 1;

            // determine offset that we will have to move the canvas
            Bounds bounds = canvas.localToScene(canvas.getBoundsInLocal());
            double dx = (x - (bounds.getWidth() / 2 + bounds.getMinX()));
            double dy = (y - (bounds.getHeight() / 2 + bounds.getMinY()));

            canvas.setTranslateX(canvas.getTranslateX() - f * dx);
            canvas.setTranslateY(canvas.getTranslateY() - f * dy);
            canvas.setScaleX(scale);
            canvas.setScaleY(scale);
        }
    }

    private void canvasResizer() {
        StackPane stackPane = (StackPane) canvas.getParent();
        canvas.setWidth(stackPane.getWidth());
        canvas.setHeight(stackPane.getHeight());
        draw();
    }

    private void updateSpeed(int frames) {

        Duration duration = Duration.millis(1000 / frames);
        KeyFrame keyFrame = new KeyFrame(duration, (ActionEvent e) -> {
            gb.nextGen();
            draw();
        });

        boolean running = false;
        if (Objects.nonNull(timeline)) {
            running = timeline.getStatus() == Animation.Status.RUNNING;
            timeline.stop();
        }

        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        if (running)
            timeline.play();
    }

    private void addSliderListner() {
        slider.setOnMouseReleased(event -> {
            if (slider.getValue() == slider.getMax() || slider.getValue() == slider.getMin()) {
                updateSpeed((int) slider.getValue());
                setSliderText(slider.getValue());
            }
        });

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!slider.isValueChanging()) {
                updateSpeed(newValue.intValue());
                setSliderText(newValue.doubleValue());
            }
        });
    }

    private void setSliderText(double value) {
        speedometer.setText((int) value + " frames");
    }

    @FXML
    private void start() {
        if (timeline.getStatus() == Animation.Status.STOPPED) {
            timeline.play();
            startButton.setText("Stop");
            rleParser innlastedFil = new rleParser();
            byte[][] board1 = innlastedFil.getBoard();

        } else {
            timeline.stop();
            startButton.setText("Start");

        }
    }

    @FXML
    void startGrid(ActionEvent event) {
        grid();
    }

    @FXML
    void NextGen(ActionEvent event) {
        gb.nextGen();
        draw();
    }


    @FXML
    private void fileOpener() {

        timeline.stop(); //Stopper timeline for å laste filen, god praksis og stoppe

        Stage mainStage = (Stage) canvas.getScene().getWindow();


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("RLE files", "*.rle"));

        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            System.out.println("Controller.fileOpener:" + selectedFile);

            rleParser parser = new rleParser();
            try {
                parser.readGameBoardFromDisk(selectedFile);
                gb.setBoard(parser.getBoard()); //setter størrelsen på brettet bassert på grid i rle fil, fungerer ikke før du trykker start
            } catch (IOException e) { //spytter ut eventuelle feilmedling
                System.out.println(e.getMessage());
            }
            System.out.println("Loaded File " + selectedFile);

        }
        draw();

    }

    @FXML
    public void drawCell(MouseEvent event) {

        int xCord = (int) (Math.floor(event.getX() / cellSize));
        int yCord = (int) (Math.floor(event.getY() / cellSize));

        //System.out.println(xCord +", "+yCord);

        if (gb.getBoard()[yCord][xCord] == 1) {
            gb.getBoard()[yCord][xCord] = 0;
            graphicsContext.clearRect(xCord * cellSize, yCord * cellSize, cellSize, cellSize);

        } else if (gb.getBoard()[yCord][xCord] == 0) {
            gb.getBoard()[yCord][xCord] = 1;
            graphicsContext.fillRect(xCord * cellSize, yCord * cellSize, cellSize, cellSize);
        }
        if(drawGrid.isSelected()){
            graphicsContext.strokeRect(xCord * cellSize, yCord * cellSize, cellSize, cellSize);

        }
    }

    @FXML
    public void dragCell(MouseEvent event) {

        int xCord = (int) (Math.floor(event.getX() / cellSize));
        int yCord = (int) (Math.floor(event.getY() / cellSize));

        //System.out.println(xCord +", "+yCord);
        if ((xCord * cellSize) < canvas.getHeight() && (yCord * cellSize) < canvas.getWidth() && xCord >= 0 && yCord >= 0) {
            gb.getBoard()[yCord][xCord] = 1;
            graphicsContext.fillRect(xCord * cellSize, yCord * cellSize, cellSize, cellSize);
        }
    }


    @FXML
    void readGameBoardFromURL(ActionEvent event) throws IOException {
        String url1 = "http://www.hioagaming.no/rats.rle";
        UrlHandler u = new UrlHandler();
        //u.readGameBoardFromURL(url1);
        gb = u.readGameBoardFromURL(gb);
    }

    /*public void readGameBoardFromURL(String url) throws IOException{

    }*/
    private void draw(/*innparameter-med-eget-predefinert-board-*/) {

        byte[][] board = gb.getBoard();

        double xPos = 0;
        double yPos = 0;


        //Setter cellestørrelsen til det minste som kreves for å få plass til alt både i høyde og bredde
        double cellwidth = canvas.getWidth() / board[0].length;
        double cellheight = canvas.getHeight() / board.length;
        if (cellwidth > cellheight) {
            cellSize = cellheight;
        } else {
            cellSize = cellwidth;
        }

        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (byte[] aBoard : board) {        //her settes den første linjnen / dimensjonen av rekken i griddet
            for (int column = 0; column < board[0].length; column++) { //
                if (aBoard[column] == 1) {                                     // Celle Død eller Levende
                    graphicsContext.setFill(Color.rgb((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)));
                    //graphicsContext.fillOval(xPos, yPos, cellSize, cellSize); <- for sirkel!
                    graphicsContext.fillRect(xPos, yPos, cellSize, cellSize);
                }
                xPos += cellSize;
            }
            xPos = 0;
            yPos += cellSize;
        }
        if (drawGrid.isSelected()){
            grid();
    }
        //  boardTufte = new boolean[rows][colms]; test
    }

        @FXML
        private void setGrid () {
        draw();
        }

    //@FXML
    private void grid() {
        final byte[][] board = gb.getBoard();
        double cellSize;

        //Setter cellestørrelsen til det minste som kreves for å få plass til alt både i høyde og bredde
        double cellwidth = canvas.getWidth() / board[0].length;
        double cellheight = canvas.getHeight() / board.length;

        if (cellwidth > cellheight) {
            cellSize = cellheight;
        } else {
            cellSize = cellwidth;
        }
        double boardWidth = cellSize * board[0].length;
        double boardHeight = cellSize * board.length;
        graphicsContext.setLineWidth(0.20);

        //GRID LINES
        //if (drawGrid.isSelected()) {
            for (int i = 0; i <= board.length; i++) {
                double horizontal = i * cellSize;
                graphicsContext.strokeLine(0, horizontal, boardWidth, horizontal);
            }

            for (int i = 0; i <= board[0].length; i++) {
                double vertical = i * cellSize;
                graphicsContext.strokeLine(vertical, 0, vertical, boardHeight);
            }
        //}
       /* else{
            draw();
        }*/
    }
}

