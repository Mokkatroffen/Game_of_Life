package sample;


public class GameBoard {



    private boolean [][] board;

    public GameBoard (int row, int column ){

        board = new boolean[row][column];

         board  = new boolean[][]{
                {false,true,false,false,false,false,false},          //1
                {false,false,true,false,false,false,false},          //2
                {true,true,true,false,false,false,false},            //3
                {false,false,false,false,false,false,false},            //4
                {false,false,false,false,false,false,false},          //5
                {false,false,false,false,false,false,false},          //6
                {false,false,false,false,false,false,false},          //7

        };

    }

    public GameBoard (){
    }

    public boolean[][] getBoard() {
        return board;
    }

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
