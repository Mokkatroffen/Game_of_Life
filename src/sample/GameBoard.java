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

    public GameBoard (int row, int column ){

        board = new byte[row][column];

         board  = new byte[100][100];
    }

    public GameBoard (){
    }

    public byte[][] getBoard() {
        return board;
    }

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

    public void nextGen(){

        byte[][] nextGenBoard  = new byte[board.length][board[0].length];

        for(int row = 0; row < board.length; row++){
            for(int column = 0; column < board.length; column++){


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

}
