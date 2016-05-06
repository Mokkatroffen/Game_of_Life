package inndata.GoL;

import GoL.Model.GameBoard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {
    private GameBoard testBoard;

    @Before
    /**
     * setUp holds the possitions of live cells for an given itteration for further testing
     *
     * @author Boris Illievski
     * @version 1.0 May 05, 2016
     *
     * @throws Exception The class and its subclasses are a form of that indicates conditions
     * that a reasonable application might want to catch.
     */
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

    @Test
    /**
     * checkNeighborsTest checks spesfict ammount of neighbors for a given posstion on the given test board
     *
     * @author Kristian Munter SImonsen
     * @version 1.0 May 05, 2016
     *
     * @throws Exception  The class and its subclasses are a form of that indicates conditions
     * that a reasonable application might want to catch.
     */
    public void checkNeighborsTest() throws Exception {
        assertEquals(0, testBoard.checkNeighbors(6,4));
        assertEquals(1, testBoard.checkNeighbors(0,1));
    }

    @Test
    /**
     *  testNextGen is an JUnit inndata for a given iteration of the given board.
     *
     *  testen vises det at hvis vi bytter ut en celle helt neders i høyre hjørne og kjøre next gen 2 ganger, så sier testen at den er riktig
     * Dette er fordi etter 2 nextGen iterasjoner så vil den ene cellen i hjørnet dø ut og mønsteret vil fortsatt være = assertionen men om vi
     * hadde byttet flere celler, eksempel den i bortestehjørne og en vedsiden, altså til venstre for cellen og over, så vill asertionen skrike etter hjelp
     *
     * @author Kristian Munter Simonsen
     * @version 1.0 May 05, 2016.
     * @throws Exception The class and its subclasses are a form of that indicates conditions
     * that a reasonable application might want to catch.
     */
    public void nextGen() throws Exception {

        testBoard.nextGen(); //1 iterasjon
        testBoard.nextGen(); //2 iterasjon

        assertEquals(testBoard.toString(), "0000000001000010100000110000000000000000000000000");
    }
}