package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    Canvas canvas;
    GraphicsContext graphicsContext;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphicsContext = canvas.getGraphicsContext2D();
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(i % 2 == 0 && j % 2 == 0 ){ // Celle Død eller Levende
                    graphicsContext.fillRect(i*canvas.getWidth()/10,j*canvas.getHeight()/10,canvas.getWidth()/10,canvas.getHeight()/10);
                }
            }
        }
    }
}
