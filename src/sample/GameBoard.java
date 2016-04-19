/**
 * GameBoard.java  contains the creation and paramters, values and limits of the board which the interaction is preformed.
 * @author Andreas Jacobsen, Boris Illievski & Kristian Munter Simonsen
 * @version 1.0 - April 18, 2016
 * Every major change to the file will result in a +0.1 to version while a new hand-in results in a whole new version
 */
package sample;


import java.util.Arrays;

public class GameBoard {



    private byte [][] board;
     public void setBoard(byte [][]getBrett){
         // boardTufte = getBrett;
        this.board = getBrett;
    }

    /**
     * GameBoard sets the values of the cells int the boards.
     *
     * static representation of an given value from boolean[][].
     * This is in other words a two dimentional array.
     *
     * @author Andreas Jacobsen.
     * @version 0.2 - April 18, 2016.
     *
     * @param row row holds the amount and thereby also the values of the booleans.
     * @param column column holds the amount of columns where the rows holds values.
     */
    public GameBoard (int row, int column ){

        board = new byte[row][column];

        board  = new byte[100][100];
    }

    public GameBoard (){
    }

    public byte[][] getBoard() {
        return board;
    }

    /**
     * GameBoard here calculates the amount of "live" neighbors.
     *
     * @author Kristian Munter Simonsen.
     * @version 0.2 - April 18, 2016.
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
     * In general if there is 2 or 3 live cells around a cell the avlue = true.
     *
     * @author Boris Illievski.
     * @version 0.2 - April 18, 2016.
     */
    public void nextGen(){
        System.out.println(board.length + " collength " + board[0].length);
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

           // System.out.println();

        }

        board = nextGenBoard;

    }
    @Override
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
