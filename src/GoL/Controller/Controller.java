package GoL.Controller;
import GoL.Model.GameBoard;
import GoL.Model.DynamicBoard;
import GoL.Model.RleParser;
import GoL.Model.UrlHandler;
import GoL.View.Information;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    /**
     * Controller handles drawing of cell and interaction with graphical elements.
     * Here pressing buttons, sliders and live-drawing in the grid is handled.
     * Controller pulls data from the other classes and acts to controll data from them.
     * @author Andreas Jacobsen
     * @date 29. March 2016
     * @version 0.8
     */

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
    RadioButton setStatic;
    @FXML
    RadioButton setDynamic;
    @FXML
    CheckBox drawGrid;

    private GraphicsContext graphicsContext;
    public DynamicBoard db;
    public GameBoard gb ;
    private double cellSize;
    private Timeline timeline;
    double pressedX;
    double pressedY;
    boolean randomCollors = false;
    private boolean nonDynamic;
    private double cellHeight, cellWidth;

    /**
     * Controller Controller initializes controller
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     *
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public Controller() throws IOException {

    }

    /**
     * changeBoardType changes the board type between by creating a new dynamic og static board
     * depending on the users input.
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     *
     * @param height height sets the height of the board
     * @param width width sets the width of the baord
     */
    public void changeBoardType(int height, int width) {
        if(nonDynamic) {
            db = new DynamicBoard(width, height);
        } else {
            gb = new GameBoard(width, height);
        }
        nonDynamic = !nonDynamic;

        updateSpeed(5);
        addSliderListner();
        draw();
    }

    /**
     * fillCells fills the cells depending on the cells state
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05, 2016
     *
     * @param boardRow
     * @param boardColumn
     */
    private void fillCells(int boardRow, int boardColumn) {
        double xPos = 0;
        double yPos = 0;
        cellHeight = canvas.getHeight() / boardColumn;
        cellWidth = canvas.getWidth() / boardRow;
        cellSize = (cellWidth > cellHeight) ? cellHeight : cellWidth;
        for(int row = 0; row < boardRow; row++) {
            for (int column = 0; column < boardColumn; column++) {
                if(!nonDynamic) {
                    if(db.getCellState(row, column ) == 1) {
                        fillCell(xPos, yPos);
                    }
                } else {
                    if (gb.getCellState(row, column) == 1) {
                        fillCell(xPos, yPos);
                    }
                }

                xPos += cellSize;
            }
            xPos = 0;
            yPos += cellSize;
        }
    }

    /**
     * fillCell fills fills the cells based on either random or normal colours
     *
     * @author Boris Ilievski
     * @version 1.0 May 05, 2016
     *
     * @param xPos xPos sets the position of the rows
     * @param yPos yPos sets the position of the columns
     */
    private void fillCell(double xPos, double yPos) {
        graphicsContext.setFill((randomCollors)?(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255))):(Color.BLACK));
        graphicsContext.fillRect(xPos, yPos, cellSize, cellSize);
    }

    /**
     * clearCanvas clears the canvas inbetween the changes of the bardtypes
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     */
    private void clearCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * draw draws the cells if the grid is selected and clears the canvas inbetween
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05,2016
     */
    private void draw() {
        clearCanvas();
        int height = (!nonDynamic) ? db.getColumn() : gb.getColumn();
        int width = (!nonDynamic) ? db.getRow() : gb.getRow();
        fillCells(width, height);
        if(drawGrid.isSelected()) {
            grid();
        }
    }

    @FXML
    /**
     * setStatic setStatic is a setter method for the static board
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05, 2016
     */
    public void setStatic(){
        clearCanvas();
        changeBoardType(10,10);
    }

    @FXML
    /**
     * setDynamic setDynamic is a setter method for dynamic board for a predefinde board at startup
     *
     * @author Boris Ilievski
     * @version 1.0 May 05, 2016
     */
    public void setDynamic(){
        clearCanvas();
        changeBoardType(10,10);
    }

    /**
     * setDynamic setDynamic sets a dynamic board based on db's row and column
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     *
     * @param row column sets the rows of the board
     * @param column column set the columns of the board
     */
    private void setDynamic(int row, int column){
        db.setRow(row);
        db.setColumn(column);
    }

    @Override
    /**
     * initialize intitialize initializes the differend methods in correct order.
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05, 2016
     *
     * @see URL URL Class  represents a Uniform Resource Locator, a pointer to a "resource" on the World Wide Web.
     * @see ResourceBundle   Resource bundles contain locale-specific objects.
     */
    public void initialize(URL location, ResourceBundle resources) {


        nonDynamic = true;
        Information info = new Information();
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
    /**
     * scrollHandler scrollHandler handles the scroll event.
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05, 2016
     *
     * @see ScrollEvent Scroll event indicates that user performed scrolling by mouse wheel,
     * track pad, touch screen or other similar device.
     */
    public void scrollHandler(ScrollEvent event) {
        double zoomFactor = 1.5;

        if (event.getDeltaY() <= 0) {
            // zoom out
            zoomFactor = 1 / zoomFactor;
        }
        zoom(zoomFactor, event.getSceneX(), event.getSceneY());
        //draw();
    }


    /**
     * zoom handles the zoom opeartion via scroll
     *
     * @author Boris Ilievski
     * @version 1.0 May 05, 2016
     *
     * @param factor factor holds the factor by wich you zoom
     * @param x x holds the x possition of the mouse pointer
     * @param y y holds the y possition of the mouse pointer
     */
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
    /**
     * canvasResizer canvasResizer resizes the board based on the canvas set methods
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     */
    private void canvasResizer() {
        StackPane stackPane = (StackPane) canvas.getParent();
        canvas.setWidth(stackPane.getWidth());
        canvas.setHeight(stackPane.getHeight());
        draw();
    }

    /**
     * updateSpeed updateSpeed handles the update speed of the cells itterations
     *
     * @author Boris Ilievski
     * @version 1.0 May 05, 2016
     *
     * @param frames frames is the inparameter from the slider
     */
    private void updateSpeed(int frames) {
        Duration duration = Duration.millis(1000 / frames);
        KeyFrame keyFrame = new KeyFrame(duration, (ActionEvent e) -> {
            if(nonDynamic){
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

    /**
     * addSliderListner  listens to the input of the slider
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     */
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

    /**
     * setSliderText setSliderText puts out the refresh rate of the iterations to the user based on the slider
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05, 2016
     * @param value value is the value that the slider is set to.
     */
    private void setSliderText(double value) {
        speedometer.setText((int) value + " frames");
    }

    @FXML
    /**
     *start start set the name of the start button based on if the itteration is started or stopped.
     *
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     */
    private void start() {
        if (timeline.getStatus() == Animation.Status.STOPPED) {
            timeline.play();
            startButton.setText("Stop");
            RleParser innlastedFil = new RleParser();
            byte[][] board1 = innlastedFil.getBoard();

        } else {
            timeline.stop();
            startButton.setText("Start");

        }
    }

    /**
     * setRandomCollors is a setter method for the cells colour.
     *
     * @author Boris Ilievski
     * @version 1.0 May 05, 2016
     */
    private void setRandomCollors() {
        randomCollors = !randomCollors;
    }

    @FXML
    /**
     * startGrid startGrid sets the visual representation of the grid
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     */
    void startGrid(ActionEvent event) {
        grid();
    }

    @FXML
    /**
     * NextGen listens on the action event for the button nextgen and runs another itteration of the cells state.
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May, 05 2016
     */
    void NextGen(ActionEvent event) {
        if(nonDynamic){
            gb.nextGen();
        }else{
            db.nextGen();
        }
        draw();
    }


    @FXML
    /**
     * fileOpener stops the itteration and uploads the .rle file to the board.
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05, 2016
     */
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

            RleParser parser = new RleParser();
            try {
                parser.readGameBoardFromDisk(selectedFile);
                if(nonDynamic){
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
            draw();
        }
    }

    /**
     * drawDynamicBoard drawDynamicBoard draws the cords on the board based on the inputs from the user
     *draws cells only if dynamic board is set
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     *
     * @param xCord xCord is the coordiantes of the mouses x posstion
     * @param yCord yCord is the coordiantes of the mouses y posstion
     * @param event event listens to the event click of the user.
     */
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
    /**
     * drawCell drawCell is the method that handles the drawing of cells bassed on theire possiton.
     *
     * This has been duplicated to work around the click function that reacts both on click and release
     *
     * @author Boris Ilievski
     * @version 1.0 May 05, 2016
     */
    public void drawCell(MouseEvent event) {
        int xCord = (int) (Math.floor(event.getX() / cellSize));
        int yCord = (int) (Math.floor(event.getY() / cellSize));
        if(!nonDynamic){
            int maxX = db.getColumn();
            int maxY = db.getRow();
            if(xCord > maxX-1) return;
            if(yCord > maxY -1) return;
        }
        if(nonDynamic){
            int maxX = gb.getColumn();
            int maxY = gb.getRow();
            if(xCord > maxX-1) return;
            if(yCord > maxY -1) return;
        }
        System.out.println(xCord + ", " + yCord);
        if(event.getButton().equals(MouseButton.PRIMARY)){
            if(nonDynamic){
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
    /**
     * dragCell dragCell handles the function of draging and drawing cells on the board
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05, 2016
     */
    public void dragCell(MouseEvent event) {
        int xCord = (int) (Math.floor(event.getX() / cellSize));
        int yCord = (int) (Math.floor(event.getY() / cellSize));
        if(!nonDynamic){
            int maxX = db.getColumn();
            int maxY = db.getRow();
            if(xCord > maxX-1) return;
            if(yCord > maxY -1) return;
        }
        if(nonDynamic) {
            int maxX = gb.getColumn();
            int maxY = gb.getRow();
            if (xCord > maxX - 1) return;
            if (yCord > maxY - 1) return;
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
                if(nonDynamic) {
                    gb.setCellState(yCord,xCord,(byte)1) ;
                }
                else if(!nonDynamic){
                    db.setCellState(yCord,xCord,(byte)1) ;
                }
                graphicsContext.fillRect(xCord * cellSize, yCord * cellSize, cellSize, cellSize);
            }
        }
    }

    @FXML
    /**
     * readGameBoardFromURL readGameBoardFromURL handles the reading of the boards from an url location
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     *
     * @throws IOException
     */
    void readGameBoardFromURL(ActionEvent event) throws IOException {
        String url1 = "http://www.hioagaming.no/rats.rle";
        UrlHandler u = new UrlHandler();
        if(nonDynamic){
            gb = u.readGameBoardFromURL(gb);
        }
        else {
            db = u.readGameBoardFromURL(db);
        }
    }

    @FXML
    /**
     * setGrid starts the draw method.
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     */
    private void setGrid () {
        draw();
    }

    /**
     * grid grid sets the cellsize to the least that is needed to fit both in height and width.
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05, 2016
     */
    private void grid() {
        double cellSize;
        double col;
        double row;
        if(nonDynamic){
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
        if(nonDynamic){
            double boardWidth = cellSize * gb.getColumn();
            double boardHeight = cellSize * gb.getRow();
            graphicsContext.setLineWidth(0.20);
            //GRID LINESGoL/Controller/Controller.java:653
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
    }
}