package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    Canvas canvas;
    GraphicsContext graphicsContext;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.GREEN);
        for(int i = 0; i < 100; i++){
            for(int j = 0; j < 100; j++){
                if(i % 1 == 0 && j % 1== 0 ){ // Celle Død eller Levende
                    graphicsContext.clearRect(i*canvas.getWidth()/30,j*canvas.getHeight()/15,canvas.getWidth()/30,canvas.getHeight()/15);


                }
            }
        }
    }
}
