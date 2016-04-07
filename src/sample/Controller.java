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
import javafx.util.Duration;

import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;
//test
public class Controller implements Initializable{
   @FXML
    Canvas canvas;
    @FXML
    Button startButton;
    GraphicsContext graphicsContext;
    GameBoard gb = new GameBoard();
    Timeline timeline;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
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

    private void draw(/*innparameter-med-eget-predefinert-board-*/) {

        boolean[][] board = gb.getBoard();

        double xPos = 0;
        double yPos = 0;

        double cellSize = canvas.getHeight() / board.length;

        graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        for(int row = 0; row < gb.getBoard().length; row++){ //her settes den første linjnen / dimensjonen av rekken i griddet
            for(int column = 0; column < gb.getBoard()[0].length; column++){ //
                if(gb.getBoard()[row][column]){ // Celle Død eller Levende
                    graphicsContext.fillRect(xPos,yPos,cellSize,cellSize);
                }
                xPos += cellSize;
            }
            xPos = 0;
            yPos += cellSize;
        }
    }
}
