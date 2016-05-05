package GoL.Controller;
import GoL.View.information;
import GoL.Controller.dynamicBoard;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.input.MouseButton;
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
    private CheckBox getLSD;
    @FXML
    MenuButton Menu;
    @FXML
    CheckBox setStatic;
    @FXML
    CheckBox setDynamic;
    @FXML
    CheckBox drawGrid;


//if mouse e inside grid

    private GraphicsContext graphicsContext;
    public  dynamicBoard db;
    public  GameBoard gb ;
    //private Timeline timeline;
    private double cellSize;
    private Timeline timeline;
    //======== Variabler for drag ========
    double pressedX;
    double pressedY;
    boolean randomCollors = false;
    //===============================
    /*public AnimatedZoomOperator() {
        this.timeline = new Timeline(60);
    }*/
    public Controller() throws IOException {

    }

    @FXML
    public void setStatic(){
        gb = new GameBoard(11,12);
        draw();
    }
    @FXML
    public void setDynamic(){
        db = new dynamicBoard(11,12);
        draw();
    }

    private void setDynamic(int row, int column){
        db.setRow(row);
        db.setColumn(column);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        information info = new information();
        info.info();
        //===============================
        canvas.setOnMousePressed(e -> {
            pressedX = e.getX();
            pressedY = e.getY();
        });
        //=================================
        getLSD.setOnAction(event -> setRandomCollors());

        canvas.setEffect(new Glow());
        graphicsContext = canvas.getGraphicsContext2D();

        //graphicsContext.setFill(Color.GREEN);
        graphicsContext.setStroke(Color.BLUE);
        // draw();
        //setStatic();
        setDynamic();
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
            if(gb!=null){
                gb.nextGen();
            }
            else{
                db.nextGen();
            }
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

    private void setRandomCollors() {
        randomCollors = !randomCollors;
    }

    @FXML
    void startGrid(ActionEvent event) {
        grid();
    }

    @FXML
    void NextGen(ActionEvent event) {
        if(gb!=null){
            gb.nextGen();
        }else{
            db.nextGen();
        }
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
                if(gb!=null){
                    gb.setBoard(parser.getBoard()); //setter størrelsen på brettet bassert på grid i rle fil, fungerer ikke før du trykker start
                    canvasResizer();
                }
                else{
                    db.setRow(parser.getRow());
                    db.setColumn(parser.getColumn());
                    db.setBoard(parser.getBoard()); //setter størrelsen på brettet bassert på grid i rle fil, fungerer ikke før du trykker start
                    canvasResizer();
                }

            } catch (IOException e) { //spytter ut eventuelle feilmedling
                System.out.println(e.getMessage());
            }
            System.out.println("Loaded File " + selectedFile);

        }
        draw();

    }

    //brukes i drawCell dersom det  er dynamic board
    private void drawDynamicBoard(int xCord, int yCord, MouseEvent event){
        if (db.getCellState(yCord, xCord) == 1 && event.isControlDown()) {
//                db.getBoard()[yCord][xCord] = 0;
            db.setCellState(yCord,xCord,(byte)0) ;
            graphicsContext.clearRect(xCord * cellSize, yCord * cellSize, cellSize, cellSize);
        }
        else if (db.getCellState(yCord, xCord)  == 0) {
            db.setCellState(yCord,xCord,(byte)1) ;
            graphicsContext.fillRect(xCord * cellSize, yCord * cellSize, cellSize, cellSize);
        }
    }

    //brukes i drawcell hvis det er static board
    private void drawStaticBoard(int xCord, int yCord, MouseEvent event){
        if (gb.getCellState(yCord, xCord) == 1 && event.isControlDown()) {
//                gb.getBoard()[yCord][xCord] = 0;
            gb.setCellState(yCord,xCord,(byte)0) ;
            graphicsContext.clearRect(xCord * cellSize, yCord * cellSize, cellSize, cellSize);
        }
        else if (gb.getCellState(yCord, xCord)  == 0) {
            gb.setCellState(yCord,xCord,(byte)1) ;
            graphicsContext.fillRect(xCord * cellSize, yCord * cellSize, cellSize, cellSize);
        }
    }
    @FXML
    public void drawCell(MouseEvent event) {

        int xCord = (int) (Math.floor(event.getX() / cellSize));
        int yCord = (int) (Math.floor(event.getY() / cellSize));
        if(db != null){
            int maxX = db.getColumn();
            int maxY = db.getRow();
            if(xCord > maxX-1) return;
            if(yCord > maxY -1) return;
        }
        if(gb != null){
            int maxX = gb.getColumn();
            int maxY = gb.getRow();
            if(xCord > maxX-1) return;
            if(yCord > maxY -1) return;
        }
        else if(gb == null && db == null ){
            return;
        }

        System.out.println(xCord + ", " + yCord);
        if(event.getButton().equals(MouseButton.PRIMARY)){
            if(gb!=null){
                drawStaticBoard(xCord, yCord, event);
            }
            else{
                drawDynamicBoard(xCord, yCord, event);
            }

            if (drawGrid.isSelected()) {
                graphicsContext.strokeRect(xCord * cellSize, yCord * cellSize, cellSize, cellSize);
            }
        }
    }





    @FXML
    public void dragCell(MouseEvent event) {
        int xCord = (int) (Math.floor(event.getX() / cellSize));
        int yCord = (int) (Math.floor(event.getY() / cellSize));
        if(db != null){
            int maxX = db.getColumn();
            int maxY = db.getRow();
            if(xCord > maxX-1) return;
            if(yCord > maxY -1) return;
        }
        if(gb != null){
            int maxX = gb.getColumn();
            int maxY = gb.getRow();
            if(xCord > maxX-1) return;
            if(yCord > maxY -1) return;
        }
        else if(gb == null && db == null ){
            return;
        }

        if (event.isSecondaryButtonDown()){
            //==============pan=================
            canvas.setTranslateX(canvas.getTranslateX() + event.getX() - pressedX);
            canvas.setTranslateY(canvas.getTranslateY() + event.getY() - pressedY);

            //===============================
        }
        if(event.isPrimaryButtonDown() && !event.isSecondaryButtonDown()) {
            //System.out.println(xCord +", "+yCord);
            if ((xCord * cellSize) < canvas.getHeight() && (yCord * cellSize) < canvas.getWidth() && xCord >= 0 && yCord >= 0) {
//                gb.getBoard()[yCord][xCord] = 1;
                if(gb!=null) {
                    gb.setCellState(yCord,xCord,(byte)1) ;
                }
                else if(db!=null){
                    db.setCellState(yCord,xCord,(byte)1) ;
                }
                graphicsContext.fillRect(xCord * cellSize, yCord * cellSize, cellSize, cellSize);
            }
        }
    }


    @FXML
    void readGameBoardFromURL(ActionEvent event) throws IOException {
        String url1 = "http://www.hioagaming.no/rats.rle";
        UrlHandler u = new UrlHandler();
        //u.readGameBoardFromURL(url1);
        if(gb!=null){
            gb = u.readGameBoardFromURL(gb);
        }
        else if(db!=null){
            System.out.println(db);
            db = u.readGameBoardFromURL(db);
        }
    }

    /*public void readGameBoardFromURL(String url) throws IOException{

    }*/
    private void draw(/*innparameter-med-eget-predefinert-board-*/) {

//        byte[][] board = gb.getBoard();

        double xPos = 0;
        double yPos = 0;
        int boardRow;
        int boardColumn;
        if(gb!= null) {
            boardRow=gb.getRow();
            boardColumn = gb.getColumn();
            System.out.println(boardRow + ", " + boardColumn);
        }else{
            boardRow= db.getRow();
            boardColumn = db.getColumn();
            System.out.println(boardRow + ", " + boardColumn);
        }

        System.out.println("størrelse" + boardRow + ", " + boardColumn);

        //Setter cellestørrelsen til det minste som kreves for å få plass til alt både i høyde og bredde
//        double cellwidth = canvas.getWidth() / board[0].length;
        double cellwidth = canvas.getWidth() / boardColumn;
//        double cellheight = canvas.getHeight() / board.length;
        double cellheight = canvas.getHeight() / boardRow;
        if (cellwidth > cellheight) {
            cellSize = cellheight;
        } else {
            cellSize = cellwidth;
        }

        if(randomCollors)
        {
            graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//            for (byte[] aBoard : board) {        //her settes den første linjnen / dimensjonen av rekken i griddet

//                for (int column = 0; column < board[0].length; column++) { //
                if(gb!=null) {
                    for(int row = 0; row < boardRow; row++) {
                        for (int column = 0; column < boardColumn; column++) {
//                    if (aBoard[column] == 1) {                                   // Celle Død eller Levende
                            if (gb.getCellState(row, column) == 1) {                                    // Celle Død eller Levende
                                graphicsContext.setFill(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
                                //graphicsContext.fillOval(xPos, yPos, cellSize, cellSize); <- for sirkel!
                                graphicsContext.fillRect(xPos, yPos, cellSize, cellSize);
                            }
                            xPos += cellSize;
                        }
                        xPos = 0;
                        yPos += cellSize;

                    }
                    }else {
                    boardRow = db.getRow();
                    boardColumn = db.getColumn();
                    for (int row = 0; row < boardRow; row++) {
                        for (int column = 0; column < boardColumn; column++) {
//                    if (aBoard[column] == 1) {                                   // Celle Død eller Levende
                            if (db.getCellState(row, column) == 1) {                                    // Celle Død eller Levende
                                graphicsContext.setFill(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
                                //graphicsContext.fillOval(xPos, yPos, cellSize, cellSize); <- for sirkel!
                                graphicsContext.fillRect(xPos, yPos, cellSize, cellSize);
                            }
                            xPos += cellSize;
                        }
                        xPos = 0;
                        yPos += cellSize;
                    }

                }

            if (drawGrid.isSelected()){
                grid();
            }
        }

        else  {
            graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            //for (byte[] aBoard : board) {        //her settes den første linjnen / dimensjonen av rekken i griddet

                //for (int column = 0; column < board[0].length; column++) { //
                if(gb!=null){
                    for(int row = 0; row < boardRow; row++){
                    for (int column = 0; column < boardColumn; column++) {
                        //if (aBoard[column] == 1) {
                        if (gb.getCellState(row,column) == 1) {   //Celle Død eller Levende
                            graphicsContext.setFill(Color.BLACK);
                            //graphicsContext.fillOval(xPos, yPos, cellSize, cellSize); <- for sirkel!
                            graphicsContext.fillRect(xPos, yPos, cellSize, cellSize);
                        }
                        xPos += cellSize;
                    }
                        xPos = 0;
                        yPos += cellSize;

                    }
                }else{

                    boardRow = db.getRow();
                    boardColumn = db.getColumn();
                    for(int row = 0; row < boardRow; row++) {
                        for (int column = 0; column < boardColumn; column++) {
                            //if (aBoard[column] == 1) {
                            if (db.getCellState(row, column) == 1) {   //Celle Død eller Levende
                                graphicsContext.setFill(Color.BLACK);
                                //graphicsContext.fillOval(xPos, yPos, cellSize, cellSize); <- for sirkel!
                                graphicsContext.fillRect(xPos, yPos, cellSize, cellSize);
                            }
                            xPos += cellSize;
                        }
                        xPos = 0;
                        yPos += cellSize;
                    }

                }



            if (drawGrid.isSelected()){
                grid();
            }
        }

        //  boardTufte = new boolean[rows][colms]; inndata
    }

    @FXML
    private void setGrid () {
        draw();
    }

    //@FXML
    private void grid() {
//        final byte[][] board = gb.getBoard();
        double cellSize;
        double col;
        double row;
        if(gb!=null){
            col = gb.getColumn();
            row = gb.getRow();
        }else{
            col = db.getColumn();
            row = db.getRow();
        }
        //Setter cellestørrelsen til det minste som kreves for å få plass til alt både i høyde og bredde
        double cellwidth = canvas.getWidth() / col;
        double cellheight = canvas.getHeight() / row;

        if (cellwidth > cellheight) {
            cellSize = cellheight;
        } else {
            cellSize = cellwidth;
        }
        if(gb!=null){
            double boardWidth = cellSize * gb.getColumn();
            double boardHeight = cellSize * gb.getRow();
            graphicsContext.setLineWidth(0.20);
            //GRID LINES
            //if (drawGrid.isSelected()) {
            for (int i = 0; i <= row; i++) {
                double horizontal = i * cellSize;
                graphicsContext.strokeLine(0, horizontal, boardWidth, horizontal);
            }

            for (int i = 0; i <= col; i++) {
                double vertical = i * cellSize;
                graphicsContext.strokeLine(vertical, 0, vertical, boardHeight);
            }
        }else{
            double boardWidth = cellSize * db.getColumn();
            double boardHeight = cellSize * db.getRow();
            graphicsContext.setLineWidth(0.20);

            //GRID LINES
            //if (drawGrid.isSelected()) {
            for (int i = 0; i <= db.getRow(); i++) {
                double horizontal = i * cellSize;
                graphicsContext.strokeLine(0, horizontal, boardWidth, horizontal);
            }

            for (int i = 0; i <= db.getColumn(); i++) {
                double vertical = i * cellSize;
                graphicsContext.strokeLine(vertical, 0, vertical, boardHeight);
            }
        }


        //}
       /* else{
            draw();
        }*/
    }
}