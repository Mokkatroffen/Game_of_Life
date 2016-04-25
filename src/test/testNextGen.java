package test;
import org.junit.Test;
import GoL.*;

import java.io.IOException;


/**
 * testNextGen is an JUnit test for a given iteration of the given board.
 *
 * @author Kristian Munter Simonsen
 * @version 0.2 - April 18, 2016.
 */
public class testNextGen  {
    private GameBoard testBoard;
    public testNextGen()throws IOException{
        testBoard = new GameBoard(7,7);
    }
    @Test
    public void testNextGen(){

        byte[][] fasitBoard =  {
                {0,1,0,0,0,0,0},
                {0,0,1,0,0,0,0},
                {1,1,1,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0}
        };


        /*INFO:

        I testen vises det at hvis vi bytter ut en celle helt neders i høyre hjørne og kjøre next gen 2 ganger, så sier testen at den er riktig
        Dette er fordi etter 2 nextGen iterasjoner så vil den ene cellen i hjørnet dø ut og mønsteret vil fortsatt være = assertionen men om vi
        hadde byttet flere celler, eksempel den i bortestehjørne og en vedsiden, altså til venstre for cellen og over, så vill asertionen skrike etter hjelp

        */




        testBoard.setBoard(fasitBoard);
        testBoard.nextGen();
        testBoard.nextGen();

        org.junit.Assert.assertEquals(testBoard.toString(),"0000000001000010100000110000000000000000000000000");






    }
}
