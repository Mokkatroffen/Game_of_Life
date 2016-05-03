package GoL.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreas on 30.04.16.
 */
public class dynamicBoard {


    private ArrayList<ArrayList<Byte>> board;
    public int row;
    public int collum;

    public dynamicBoard(int row, int collum){
        this.row = row;
        this.collum = collum;

        board=new ArrayList<>();
        for (int i = 0; i <row ; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j <collum ; j++) {
                board.get(i).add((byte)0);
            }
        }
    }



    public byte getCellState(int x, int y){
        return board.get(x).get(y);
    }

    public void setCellState(int x, int y, byte state){
        board.get(x).set(y,state);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return collum;
    }



    public void setColumn(int collum) {
        this.collum = collum;
    }

    public void setBoard(byte [][]newBoard ) {
        this.board = new ArrayList<>();
        for (int i = 0; i < newBoard.length; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < newBoard[i].length; j++) {
                board.get(i).add(newBoard[i][j]); //rettet i og j sin rekkefølge
            }
        }

    }

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
     * @version 0.2 - April 18, 2016.
     */
    public void nextGen(){
        expandBoard();
        //System.out.println(board.length + " collength " + board[0].length);

        ArrayList<ArrayList<Byte>> nextGenBoard=new ArrayList<>();
        for (int i = 0; i <this.row ; i++) {
            nextGenBoard.add(new ArrayList<>());
            for (int j = 0; j < this.collum; j++) {
                nextGenBoard.get(i).add((byte)0);
            }
        }
        for(int row = 0; row < this.row; row++){
            for(int column = 0; column < this.collum; column++){

                //System.out.print(checkNeighbors(row,column)+ " ");

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

            //System.out.println();

        }
        board = nextGenBoard;
    }

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

    //gi arrayboard (array) med byte verdier
    //Hver gang det er en celle i utkanten av brettet, så ekspanderer du.

}
