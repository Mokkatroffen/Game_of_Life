/**
 * 1
 * GameBoard.java  contains the creation and paramters, values and limits of the board which the interaction is preformed.
 *
 * @author Andreas Jacobsen, Boris Illievski & Kristian Munter Simonsen
 * @version 1.0 - April 18, 2016
 * Every major change to the file will result in a +0.1 to version while a new hand-in results in a whole new version
 */
package GoL.Controller;

public class GameBoard {

    private byte[][] board;

    public void setBoard(byte[][] getBrett) {
        // boardTufte = getBrett;1
        this.board = getBrett;
    }

    public GameBoard() {
        this(10,10);
    }

    /**
     * GameBoard sets the values of the cells int the boards.
     * <p>
     * static representation of an given value from boolean[][].
     * This is in other words a two dimentional array.
     *
     * @param row    row holds the amount and thereby also the values of the booleans.
     * @param column column holds the amount of columns where the rows holds values.
     * @author Andreas Jacobsen.
     * @version 0.2 - April 18, 2016.
     */
    GameBoard(int row, int column) {
        board = new byte[row][column];

        //GLIDER FOR TESTING
        /*
        board  = new byte[][] {
                {0,1,0,0,0,0,0},
                {0,0,1,0,0,0,0},
                {1,1,1,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0}
        };
        */
    }

    byte[][] getBoard() {
        return board;
    }

    /**
     * GameBoard here calculates the amount of "live" neighbors.
     *
     * @param row    row holds the values of the cells.
     * @param column column holds the amount of columns in the board.
     * @return neighbors neighbors holds the amount of live cells around each cell.
     * @author Kristian Munter Simonsen.
     * @version 0.2 - April 18, 2016.
     */
    public int checkNeighbors(int row, int column) {
        int neighbors = 0;
        neighbors += checkLeftCells(row, column);

        if (column > 0) {
            if (board[row][column - 1] == 1) {
                neighbors++;
            }
        }

        if (column < board[0].length - 1) {
            if (board[row][column + 1] == 1) neighbors++;
        }
        neighbors += checkRightCells(row, column);
        return neighbors;
    }

    private int checkRightCells(int row, int column) {
        int neighbors = 0;
        if (row < board.length - 1) {
            if (board[row + 1][column] == 1) {
                neighbors++;
            }
            if (column > 0) {
                if (board[row + 1][column - 1] == 1) neighbors++;
            }
            if (column < board[0].length - 1) {
                if (board[row + 1][column + 1] == 1) neighbors++;
            }
        }
        return neighbors;
    }

    private int checkLeftCells(int row, int column) {
        int neighbors = 0;
        if (1 > row) return neighbors;

        if (board[row - 1][column] == 1) neighbors++;

        if (column > 0) {
            if (board[row - 1][column - 1] == 1) neighbors++;
        }

        if (column < board[0].length - 1) {
            if (board[row - 1][column + 1] == 1) neighbors++;
        }
        return neighbors;
    }

    /**
     * nextGen executes the rules of game of life for the next generation.
     * <p>
     * In general if there is 2 or 3 live cells around a cell the avlue = true.
     *
     * @author Boris Illievski.
     * @version 0.2 - April 18, 2016.
     */
    public void nextGen() {
        byte[][] nextGenBoard = new byte[board.length][board[0].length];

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {

                if (checkNeighbors(row, column) == 3) {
                    nextGenBoard[row][column] = 1;
                } else if (board[row][column] == 1 && checkNeighbors(row, column) == 2) {
                    nextGenBoard[row][column] = 1;
                } else if (checkNeighbors(row, column) > 3) {
                    nextGenBoard[row][column] = 0;
                } else if (checkNeighbors(row, column) < 2) {
                    nextGenBoard[row][column] = 0;
                }
            }
        }
        board = nextGenBoard;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte[] aBoard : board) {
            for (int j = 0; j < board[0].length; j++) {

                if (aBoard[j] == 1) {
                    sb.append("1");
                } else {
                    sb.append("0");
                }
            }
        }
        return sb.toString();
    }
}
