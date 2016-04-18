/**
 * GameBoard.java  contains the creation and paramters, values and limits of the board which the interaction is preformed.
 * @author Andreas Jacobsen, Boris Illievski & Kristian Munter Simonsen
 * @version 1.0 - April 18, 2016
 * Every major change to the file will result in a +0.1 to version while a new hand-in results in a whole new version
 */
package sample;


public class GameBoard {



    private boolean [][] board;

    /**
     * GameBoard sets the values of the cells int the boards
     *
     * @author Andreas Jacobsen
     * @version 0.2 - April 18, 2016
     *
     * @param row row holds the ammount and thereby also the values of the booleans
     * @param column column holds the ammount of colloums where the rows holds values
     */
    public GameBoard (int row, int column ){

        board = new boolean[row][column];

         board  = new boolean[][]{
                {false,true,false,false,false,false,false},          //1
                {false,false,true,false,false,false,false},          //2
                {true,true,true,false,false,false,false},            //3
                {false,false,false,false,false,false,false},         //4
                {false,false,false,false,false,false,false},         //5
                {false,false,false,false,false,false,false},         //6
                {false,false,false,false,false,false,false},         //7

        };

        /*
        board  = new boolean[][]{
                {false,false,true,true,false,false,false},          //1
                {false,true,false,true,false,false,false},          //2
                {true,false,false,true,false,true,true},            //3
                {true,true,false,true,false,false,true},            //4
                {false,true,false,true,false,false,false},          //5
                {false,true,false,false,true,false,false},          //6
                {false,false,true,true,false,false,false},          //7

        };
        */
    }

    public GameBoard (){
    }

    public boolean[][] getBoard() {
        return board;
    }

    /**
     * GameBoard here calculates the ammount of "live" neighbors
     *
     * @author Kristian Munter Simonsen
     * @version 0.2 - April 18, 2016
     *
     * @param row row holds the values of the cells
     * @param column column holds the ammount of colloums in the board
     *
     * @return neighbors neighbors holds the ammount of live cells around each cell
     */
    public int checkNeighbors(int row, int column){
        int neighbors = 0;

        if(row > 0){

            if(board[row-1][column]) neighbors++;

            if(column > 0) {
                if (board[row - 1][column - 1]) neighbors++;
            }

            if (column < board[0].length-1){
                if(board[row-1][column+1]) neighbors++;
            }
        }

        if (column > 0) {
            if (board[row][column - 1]) {
                neighbors++;
            }
        }

        if (column < board[0].length-1){
            if(board[row][column+1]) neighbors++;
        }





        if(row < board.length-1){
            if(board[row+1][column]){ neighbors++;}


            if (column > 0) {
                if (board[row+1][column - 1]) neighbors++;
            }

            if (column < board[0].length-1){
                    if(board[row+1][column+1]) neighbors++;
            }
        }

        return neighbors;

    }

    /**
     * nextGen executes the rules of game of life for the next generation
     *
     * @author Boris Illievski
     * @version 0.2 - April 18, 2016
     */
    public void nextGen(){

        boolean[][] nextGenBoard  = new boolean[board.length][board[0].length];

        for(int row = 0; row < board.length; row++){
            for(int column = 0; column < board.length; column++){

                System.out.print(checkNeighbors(row,column)+ " ");

                    if(checkNeighbors(row,column) == 3){
                        nextGenBoard[row][column] = true;
                    }
                    else if(board[row][column] && checkNeighbors(row,column) ==2){
                        nextGenBoard[row][column] = true;
                    }
                    else if (checkNeighbors(row,column) > 3){
                        nextGenBoard[row][column] = false;
                    }
                    else if (checkNeighbors(row,column) < 2){
                        nextGenBoard[row][column] = false;
                    }
            }

            System.out.println();

        }

        board = nextGenBoard;

    }

}
