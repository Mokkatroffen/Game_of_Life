package GoL.Model;

import java.util.ArrayList;
import java.util.List;


/**
 * Draws the dynamic board. The board calls the draw method every time a cell hits one of the edges to re-draw so the cells don't leave the canvas.
 *
 * @author Andreas Jacobsen
 * @version 0.4 - May 2, 2016
 */
public class DynamicBoard {


    private ArrayList<ArrayList<Byte>> board;
    private int row;
    private int collum;

    /**
     * DynamicBoard DynamicBoard is a method for creating the dynamic board.
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05, 2016
     *
     * @param row inparameter for the ammount of rows
     * @param collum inparamtere for the ammount of collumns
     */
    public DynamicBoard(int row, int collum){
        this.row = row;
        this.collum = collum;
        createBoard();
    }

    /**
     * createBoard creates the board and is called in the constructor
     *
     * @author Boris Ilievski
     * @version 1.0 May 05, 2016
     */
    private void  createBoard(){
        board=new ArrayList<>();
        for (int i = 0; i <row ; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j <collum ; j++) {
                board.get(i).add((byte)0);
            }
        }
    }

    /**
     * getCellState returns the state of a cell.
     * is a getter for the state of the cells in the board.
     *
     * @Author Andreas Jacobsen
     * @Date 1.0 May 05, 2016
     *
     * @param row gets it's position in row.
     * @param col gets it's position in column.
     * @return returns data
     */
    public byte getCellState(int row, int col){

        return board.get(row).get(col); //HER FEILER
    }

    /**
     * setCellState is a setter for the cellstate of the board
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     *
     * @param x x sets the rows of the board
     * @param y y sets the columns of the board
     * @param state sets the state of the cells
     */
    public void setCellState(int x, int y, byte state){
        board.get(x).set(y,state);
    }

    /**
     * getRow is a getter for the rows
     *
     * @author Boris Ilievski
     * @version 1.0 May 05, 2016
     *
     * @return return returns the row
     */
    public int getRow() {
        return row;
    }

    /**
     * setRow is a setter of of the rows.
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     *
     * @param row row is a parameter for the rows.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * getColumn getColumn is a getter for the columns
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05, 2016
     *
     * @return return returns the columns
     */
    public int getColumn() {
        return collum;
    }

    /**
     * setColumn setColumn is a setter for the columns
     *
     * @author Boris Ilievski
     * @version 1.0 May 05, 2016
     *
     * @param collum collum is the columns of the setter
     */
    public void setColumn(int collum) {
        this.collum = collum;
    }
    /**
     * setBoard setBoard is s setter for the board with the boards rows, columns and board.
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05,2016
     *
     * @param newBoard newboard sets the type of the new board and inputs the status of the cells.
     */
    public void setBoard(byte[][] newBoard ) {
        this.row = newBoard.length;
        this.collum = newBoard[0].length;
        this.board = new ArrayList<>();
        for (int i = 0; i < newBoard.length; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < newBoard[i].length; j++) {
                board.get(i).add(newBoard[i][j]); //rettet i og j sin rekkefølge
            }
        }

    }

    /**
     * checkNeighbors checkneighbors checks the neighbors of the dynamic board
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     *
     * @param row row is the rows
     * @param column column is the columns.
     * @return neighbors neighbors is the ammount of neighbors that the if's have callculated.
     */
    public int checkNeighbors(int row, int column){
        int neighbors = 0;
        if(row > 0){

            if(board.get(row-1).get(column) == 1) neighbors++;

            if(column > 0) {
                if (board.get(row - 1).get(column - 1)  == 1) neighbors++;
            }

            if (column < this.collum-1){
                if(board.get(row-1).get(column+1) == 1) neighbors++;
            }
        }

        if (column > 0) {
            if (board.get(row).get(column - 1) == 1) {
                neighbors++;
            }
        }

        if (column < this.collum-1){
            if(board.get(row).get(column+1) == 1) neighbors++;
        }

        if(row < this.row-1){

            if(board.get(row+1).get(column) == 1){ neighbors++;}


            if (column > 0) {
                if (board.get(row+1).get(column - 1) == 1) neighbors++;
            }

            if (column < this.collum-1){
                if(board.get(row+1).get(column+1) == 1) neighbors++;
            }
        }

        return neighbors;

    }

    /**
     * nextGen executes the rules of game of life for the next generation.
     *
     * In general if there is 2 or 3 live cells around a cell the avlue = true.
     *
     * @author Boris Illievski.
     * @version 1.0 May 05, 2016.
     */
    public void nextGen(){
        expandBoard();

        ArrayList<ArrayList<Byte>> nextGenBoard=new ArrayList<>();
        for (int i = 0; i <this.row ; i++) {
            nextGenBoard.add(new ArrayList<>());
            for (int j = 0; j < this.collum; j++) {
                nextGenBoard.get(i).add((byte)0);
            }
        }
        for(int row = 0; row < this.row; row++){
            for(int column = 0; column < this.collum; column++){


                if(checkNeighbors(row,column) == 3){
                    nextGenBoard.get(row).set(column,(byte)1);
                }
                else if(getCellState(row,column) == 1 && checkNeighbors(row,column) ==2){
                    nextGenBoard.get(row).set(column,(byte)1);
                }
                else if (checkNeighbors(row,column) > 3){
                    nextGenBoard.get(row).set(column,(byte)0);
                }
                else if (checkNeighbors(row,column) < 2){
                    nextGenBoard.get(row).set(column,(byte)0);
                }
            }

        }
        board = nextGenBoard;
    }

    /**
     * expandBoard expandBoard handles the expansion of the board.
     *
     * There are listeners in the corners of the board. So that if there is a cell that is true in the outer
     * reaches of the board, the board will expand by one.
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05, 2016
     */
    private void expandBoard(){
        for (int y = 0; y < this.row; y++) {
            if(board.get(y).get(0)==1){
                this.collum++;
                //System.out.println("+ VENSTRE");
                for (int i = 0; i <this.row; i++) {
                    board.get(i).add(0,(byte)0);
                }
            }
            if(board.get(y).get(collum-1)==1){
                this.collum++;
                //System.out.println("+ HØYRE");
                for (int i = 0; i <this.row; i++) {
                    board.get(i).add((byte)0);
                }
            }
        }

        for (int x = 0; x < this.collum; x++) {
            if(board.get(0).get(x)==1){
                this.row++;
                //System.out.println("+ TOPP");
                board.add(0, new ArrayList<>());
                for (int i = 0; i <this.collum; i++) {
                    board.get(0).add((byte)0);
                }
            }
            if(board.get(row-1).get(x)==1){
                this.row++;
                //System.out.println("+ BUNN");
                board.add(new ArrayList<>());
                for (int i = 0; i <this.collum; i++) {
                    board.get(row-1).add((byte)0);
                }
            }
        }
    }

    @Override
    /**
     * toString toString Create a copy, don't share the array
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     *
     *@return return returns the StringBuilders toString.
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < collum; j++) {
                if (board.get(i).get(j) ==1){
                    sb.append("1");
                }
                else{
                    sb.append("0");
                }
            }
        }
        return sb.toString();
    }
}
