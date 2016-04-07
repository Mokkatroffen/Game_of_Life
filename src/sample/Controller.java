package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;
//test
public class Controller implements Initializable {
   @FXML
    Canvas canvas;
    @FXML
    Button startButton;
    GraphicsContext graphicsContext;
    GameBoard gb = new GameBoard();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.GREEN);
        draw();

    }

    @FXML
    private void start(){
        draw();
    }

    private void draw(/*innparameter-med-eget-predefinert-board-*/) {
        graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        for(int i = 0; i < gb.getBoard().length; i++){ //her settes den første linjnen / dimensjonen av rekken i griddet
            for(int j = 0; j < gb.getBoard()[0].length; j++){ //
                if(gb.getBoard()[i][j]){ // Celle Død eller Levende
                    graphicsContext.fillRect(i*canvas.getWidth()/10,j*canvas.getHeight()/5,canvas.getWidth()/10,canvas.getHeight()/5);
                }
                gb.getBoard()[i][j] = !gb.getBoard()[i][j];
            }
        }
    }
}
