package GoL;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//test|

/**
 * Controller works with data from the other classes and implements them.
 * Also responsible for handling issues and user input.
 *
 * @author Kristian Munter Simonsen.
 * @version 0.2 - April 18, 2016.
 *
 * @see javafx.fxml.Initializable  Controller initialization interface.
 */
public class Controller implements Initializable{
   @FXML
    private Canvas canvas;
    @FXML
    private Button startButton;
    private GraphicsContext graphicsContext;
    private GameBoard gb;
    private Timeline timeline;


    public Controller() throws IOException {
        gb = new GameBoard(11,12);
    }


    @Override
    /**
     * initialize initializes an url-handler which is implemented into the grid.
     *
     * @author Andreas Jacobsen.
     * @version 0.2 - April 18, 2016.
     *
     * @param location location is the destination of the url parameter.
     * @param resources resources holds the data from the URL.
     *
     * @see URL.
     * @see ResourceBundle.
     */
    public void initialize(URL location, ResourceBundle resources) {
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.HOTPINK);
        draw();
        Duration duration = Duration.millis(1000/5);
        KeyFrame keyFrame = new KeyFrame(duration, (ActionEvent e) -> {
            gb.nextGen();
            draw();
        });

        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);

    }

    @FXML
    /**
     * start handles the start function of the program.
     *
     * @author Boris Illievski.
     * @version 0.2 - April 18, 2016.
     */
    private void start(){
        if(timeline.getStatus() == Animation.Status.STOPPED){
            timeline.play();
            startButton.setText("Stop");
            rleParser innlastedFil = new rleParser();
         byte[][] board1 = innlastedFil.getBoard();

        }
        else {
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
    /**
     * filOpener handles the implementation of .rle-files
     *
     * @author Kristian Munter Simonsen.
     * @version 0.2 - April 18, 2016.
     */
    private void fileOpener (){

        timeline.stop(); //Stopper timeline for å laste filen, god praksis og stoppe

        Stage mainStage = (Stage)canvas.getScene().getWindow();


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
         new FileChooser.ExtensionFilter("RLE files", "*.rle"));

        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {

            rleParser parser = new rleParser();
            try {
            parser.readGameBoardFromDisk(selectedFile);
                gb.setBoard(parser.getBoard()); //setter størrelsen på brettet bassert på grid i rle fil, fungerer ikke før du trykker start
            }
            catch(IOException e){ //spytter ut eventuelle feilmedling
                System.out.println(e.getMessage());
            }

        }
        draw();

    }


    /**
     * The draw method draws a filled cell into the live cells
     *
     * @author Andreas Jacobsen
     * @version 0.2 - April 18, 2016.
     */
    private void draw(/*innparameter-med-eget-predefinert-board-*/) {

        byte[][] board = gb.getBoard();

        double xPos = 0;
        double yPos = 0;

        double cellSize;

        //Setter cellestørrelsen til det minste som kreves for å få plass til alt både i høyde og bredde
        double cellwidth = canvas.getWidth() / board[0].length;
        double cellheight = canvas.getHeight() / board.length;
        if(cellwidth > cellheight) {
            cellSize = cellheight;
        } else {
            cellSize = cellwidth;
        }


        graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        for(int row = 0; row < board.length; row++){        //her settes den første linjnen / dimensjonen av rekken i griddet
            for(int column = 0; column < board[0].length; column++){ //
                if(board[row][column] == 1){                                     // Celle Død eller Levende
                    graphicsContext.fillRect(xPos,yPos,cellSize,cellSize);
                }
                xPos += cellSize;
            }
            xPos = 0;
            yPos += cellSize;
        }
        grid();
    }
                   //  boardTufte = new boolean[rows][colms]; test


    /**
     * The grid method sets the size of the grid
     *
     * @author Boris Illievski
     * @version 0.2 - April 18, 2016.
     */
    public void grid(){


        final byte[][] board = gb.getBoard();

        double cellSize;


        //Setter cellestørrelsen til det minste som kreves for å få plass til alt både i høyde og bredde
        double cellwidth = canvas.getWidth() / board[0].length;
        double cellheight = canvas.getHeight() / board.length;
        if(cellwidth > cellheight) {
            cellSize = cellheight;
        } else {
            cellSize = cellwidth;
        }
        double boardWidth = cellSize * board[0].length;
        double boardHeight = cellSize * board.length ;
        graphicsContext.setLineWidth(0.25);
        for (int i=0; i<= board.length; i++){
            double horizontal = i*cellSize;
            graphicsContext.strokeLine(0,horizontal,boardWidth,horizontal);
        }

        for (int i=0; i<= board[0].length; i++){
            double vertical = i*cellSize;
            graphicsContext.strokeLine(vertical,0,vertical,boardHeight);
        }

        /*
        graphicsContext.setLineWidth(0.25);
        for (int i=0; i<= gb.getBoard().length; i++){
            double horizontal = i*(gb.getBoard().length*canvas.getHeight() / gb.getBoard().length)/gb.getBoard().length;
            graphicsContext.strokeLine(0,horizontal,canvas.getWidth(),horizontal);
        }

        for (int i=0; i<= gb.getBoard().length; i++){
            double vertical = i*(gb.getBoard().length*canvas.getWidth() / gb.getBoard().length)/gb.getBoard().length;
            graphicsContext.strokeLine(vertical,0,vertical,canvas.getHeight());
        }*/
    }


}
