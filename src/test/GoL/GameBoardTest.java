package test.GoL;

import GoL.GameBoard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {
    private GameBoard testBoard;

    @Before
    public void setUp() throws Exception {
        testBoard = new GameBoard();

        byte[][] fasitBoard =  {
                {0,1,0,0,0,0,0},
                {0,0,1,0,0,0,0},
                {1,1,1,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0}
        };
        testBoard.setBoard(fasitBoard);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void checkNeighborsTest() throws Exception {
        assertEquals(0, testBoard.checkNeighbors(6,4));
        assertEquals(1, testBoard.checkNeighbors(0,1));
    }

    /**
     *  testNextGen is an JUnit test for a given iteration of the given board.
     *
     *  testen vises det at hvis vi bytter ut en celle helt neders i høyre hjørne og kjøre next gen 2 ganger, så sier testen at den er riktig
     * Dette er fordi etter 2 nextGen iterasjoner så vil den ene cellen i hjørnet dø ut og mønsteret vil fortsatt være = assertionen men om vi
     * hadde byttet flere celler, eksempel den i bortestehjørne og en vedsiden, altså til venstre for cellen og over, så vill asertionen skrike etter hjelp
     *
     * @author Kristian Munter Simonsen
     * @version 0.2 - April 18, 2016.
     * @throws Exception
     */
    @Test
    public void nextGen() throws Exception {

        testBoard.nextGen(); //1 iterasjon
        testBoard.nextGen(); //2 iterasjon

        assertEquals(testBoard.toString(), "0000000001000010100000110000000000000000000000000");
    }

    @Test
    public void toStringTest() throws Exception {

    }

}