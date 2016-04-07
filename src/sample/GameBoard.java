package sample;


public class GameBoard {
        private boolean[][] board  = {
            {true,false,false,true},
            {false,true,true,false},
            {false,true,true,false},
            {true,false,false,true}
    };

    public boolean[][] getBoard() {
        return board;
    }
}
