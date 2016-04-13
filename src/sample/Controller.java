package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import sample.rleParser;

//test
public class Controller implements Initializable{
   @FXML
    Canvas canvas;
    @FXML
    Button startButton;
    GraphicsContext graphicsContext;
    GameBoard gb = new GameBoard(10,10);
    Timeline timeline;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.RED);
        draw();

        Duration duration = Duration.millis(1000/30);
        KeyFrame keyFrame = new KeyFrame(duration, (ActionEvent e) -> {
            gb.nextGen();
            draw();
        });

        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);

    }

    @FXML
    private void start(){
        if(timeline.getStatus() == Animation.Status.STOPPED){
            timeline.play();
            startButton.setText("Stop");
        }
        else {
            timeline.stop();
            startButton.setText("Start");
        }
    }

    @FXML
    private void fileOpener (){

        timeline.stop(); //Stopper timeline for å laste filen, god praksis og stoppe

        Stage mainStage = (Stage)canvas.getScene().getWindow();


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
         new FileChooser.ExtensionFilter("RLE files", "*.rle"));

        File selectedFile = fileChooser.showOpenDialog();
        if (selectedFile != null) {

            rleParser parser = new rleParser();
            try {
            parser.readGameBoardFromDisk(selectedFile);
            }
            catch(IOException e){ //spytter ut eventuelle feilmedling
                System.out.println(e.getMessage());
            }

        }

    }



    private void draw(/*innparameter-med-eget-predefinert-board-*/) {

        boolean[][] board = gb.getBoard();

        double xPos = 0;
        double yPos = 0;

        double cellSize = canvas.getHeight() / board.length;

        graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        for(int row = 0; row < board.length; row++){        //her settes den første linjnen / dimensjonen av rekken i griddet
            for(int column = 0; column < board[0].length; column++){ //
                if(board[row][column]){                                     // Celle Død eller Levende
                    graphicsContext.fillRect(xPos,yPos,cellSize,cellSize);
                }
                xPos += cellSize;
            }
            xPos = 0;
            yPos += cellSize;
        }
    }
}
