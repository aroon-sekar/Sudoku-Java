

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SudokuTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SudokuTest
{
    /**
     * Default constructor for test class SudokuTest
     */
    public SudokuTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }

    @Test
    public void testmakeMove()
    {
        Sudoku sudoku1 = new Sudoku();
        java.lang.Boolean boolean1 = sudoku1.makeMove("1", "0", "2");
        assertEquals(true, boolean1);
    }

    @Test
    public void testgetGameSize()
    {
        Sudoku sudoku2 = new Sudoku();
        assertEquals(9, sudoku2.getGameSize());
    }

    @Test
    public void testgetIndividuallMove()
    {
        Sudoku sudoku1 = new Sudoku();
        assertEquals("9", sudoku1.getIndividualMove(0, 0));
    }
}



