/**1
 * GameBoard.java  contains the creation and paramters, values and limits of the board which the interaction is preformed.
 * @author Andreas Jacobsen, Boris Illievski & Kristian Munter Simonsen
 * @version 1.0 - April 18, 2016
 * Every major change to the file will result in a +0.1 to version while a new hand-in results in a whole new version
 */
package GoL.Model;


public class GameBoard {

    /**
     * GameBoard draws the gameboard based on values from a .rle file sent by the RleParser.
     * GameBoard also handles checking if a cell is alive or dead, and drawing cells based upon the life-state of a cell.
     * The class draws the static gameboard and can not be used for dynamic scaling of the gameboard, see DynamicBoard for that
     * @author Kristian Munter Simonsen
     * @version 1.0 May 02, 2016
     */

    private byte[][] board;
    private int row;
    private int column;

    /**
     * GameBoard sets the values of the cells int the boards.
     *
     * static representation of an given value from boolean[][].
     * This is in other words a two dimentional array.
     *
     * @author Andreas Jacobsen.
     * @version 1.0  May 05, 2016.
     *
     * @param row row holds the amount and thereby also the values of the booleans.
     * @param column column holds the amount of columns where the rows holds values.
     */
    public GameBoard (int row, int column ){
        board = new byte[row][column];
        this.row= row;
        this.column=column;
    }
    public void setBoard(byte [][]getBrett){
        this.board = getBrett;
    }



    /**
     * setBoard is a setter for the board
     *
     * @author Boris Ilievski
     * @version 1.0 May 05, 2016
     *
     * @param x x possition of the cell that is handled
     * @param y y possition of the cell that is handled
     * @param state state holds the state of the cell
     */
    public void setCellState(int x, int y, byte state){
        this.board[x][y]=state;
    }


    /**
     * getCellState is a getter for the state of the cells
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     *
     * @param x x is the rows
     * @param y y is the columns
     * @return return returns the board with the posstions
     */
    public byte getCellState(int x, int y){
       return board[x][y];
    }

    /**
     * getRow is a getter for the rows
     *
     * @author Boris Ilievski
     * @version 1.0 May 05, 2016
     *
     * @return return returns the boards length
     */
    public int getRow() {
        return board.length;
    }

    /**
     * setRow is a setter for the rows
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05, 2016
     *
     * @param row row holds the rows of the board
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * getColumn is a getter for colunmn
     *
     * @author Andreas Jacobsen
     * @version 1.0 May 05, 2016
     *
     * @return return returns the boards length
     */
    public int getColumn() {
        return board[0].length;
    }

    /**
     * setColumn is a setter for the column
     *
     * @author Boris Ilievski
     * @version 1.0 May 05, 2016
     *
     * @param column column is the ammount of columns
     */
    public void setColumn(int column) {
        this.column = column;
    }

    public GameBoard (){
    }
    /**
     * getBoard is a getter for the board
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05, 2016
     *
     * @return board
     */
    public byte[][] getBoard() {
        return board;
    }

    /**
     * GameBoard here calculates the amount of "live" neighbors.
     *
     * @author Kristian Munter Simonsen.
     * @version 1.0 May 05, 2016.
     *
     * @param row row holds the values of the cells.
     * @param column column holds the amount of columns in the board.
     *
     * @return neighbors neighbors holds the amount of live cells around each cell.
     */
    public int checkNeighbors(int row, int column){
        int neighbors = 0;
        if(row > 0){
            if(board[row-1][column] == 1) neighbors++;
            if(column > 0) {
                if (board[row - 1][column - 1]  == 1) neighbors++;
            }
            if (column < board[0].length-1){
                if(board[row-1][column+1] == 1) neighbors++;
            }
        }
        if (column > 0) {
            if (board[row][column - 1] == 1) {
                neighbors++;
            }
        }
        if (column < board[0].length-1){
            if(board[row][column+1] == 1) neighbors++;
        }
        if(row < board.length-1){
            if(board[row+1][column] == 1){ neighbors++;}
            if (column > 0) {
                if (board[row+1][column - 1] == 1) neighbors++;
            }
            if (column < board[0].length-1){
                if(board[row+1][column+1] == 1) neighbors++;
            }
        }
        return neighbors;
    }

    /**
     * nextGen executes the rules of game of life for the next generation.
     *
     * In general if there is 2 or 3 live cells around a cell the value = true.
     *
     * @author Boris Illievski.
     * @version 01.0 May 05, 2016.
     */
    public void nextGen(){
        byte[][] nextGenBoard  = new byte[board.length][board[0].length];
        for(int row = 0; row < board.length; row++){
            for(int column = 0; column < board[0].length; column++){
                if(checkNeighbors(row,column) == 3){
                    nextGenBoard[row][column] = 1;
                }
                else if(board[row][column] == 1 && checkNeighbors(row,column) ==2){
                    nextGenBoard[row][column] = 1;
                }
                else if (checkNeighbors(row,column) > 3){
                    nextGenBoard[row][column] = 0;
                }
                else if (checkNeighbors(row,column) < 2){
                    nextGenBoard[row][column] = 0;
                }
            }
        }
        board = nextGenBoard;
    }

    @Override
    /**
     * toString toString Create a copy, don't share the array
     *
     * @author Boris Ilievski
     * @version 1.0 May 05, 2016
     *
     *@return return returns the StringBuilders toString.
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length ; i++) {
            for (int j = 0; j < board[0].length ; j++) {

                if (board[i][j] ==1){
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
