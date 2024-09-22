

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class AssignTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class AssignTest
{
    /**
     * Default constructor for test class AssignTest
     */
    public AssignTest()
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
    public void testgetRow()
    {
        Sudoku sudoku1 = new Sudoku();
        Assign assign1 = new Assign(sudoku1, 1, 0, "9", "8");
        assertEquals(1, assign1.getRow());
    }
    @Test
    public void testgetCol()
    {
        Sudoku sudoku1 = new Sudoku();
        Assign assign1 = new Assign(sudoku1, 1, 0, "9", "8");
        assertEquals(0, assign1.getCol());
    }
    @Test
    public void testgetNumber()
    {
        Sudoku sudoku1 = new Sudoku();
        Assign assign1 = new Assign(sudoku1, 1, 0, "9", "8");
        assertEquals("9", assign1.getNumber());
    }
    @Test
    public void testgetPrevState()
    {
        Sudoku sudoku1 = new Sudoku();
        Assign assign1 = new Assign(sudoku1, 1, 0, "9", "8");
        assertEquals("8", assign1.getPrevMove());
    }

    @Test
    public void getIndividualMoveIntegration()
    {
        Sudoku sudoku2 = new Sudoku();
        Assign assign2 = new Assign(sudoku2, 1, 8, "1", "8");
        assertEquals("1", sudoku2.getIndividualMove(1, 8));
    }
}


