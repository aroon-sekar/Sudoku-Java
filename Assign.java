/**
 * Assign
 * This class handles the creation of all moves in the game
 * It stores the row and column of the move, and the state of the slot being assigned
 * Assigns each move the game based on row and columns
 * 
 * 
 * @author Lauren Scott
 * @author Arun Kumar Sekar
 * 
 * @version Janurary 2024
 */
public class Assign {

    private int col, row;//The row and column being assigned
    private Sudoku game;//The game 
    Slot[][] moves;//2D Array to store the game's moves
    
    private String prevState; // To store the previous state of the slot
    private String number; // To store the number for the move

   

    /**
     * Constructor for Assign class.
     * This gets the total number of moves and calls methods to determine the row that will be filled, and to set the state of the slot being assigned.
     * @param game - the game
     * @param col - the column the user has selected
     * @param player - a Boolean value that determines whether it is a player/computer move
     */
    public Assign(Sudoku game, int row, int col, String number, String prevState) {
        this.game = game;
        this.col = col;
        this.row = row;
        this.moves = game.getMoves();
        this.prevState = prevState;
        this.number = number; // Store the number
        
        assignMove(number); 
    }
    
    /**
     * assignMove
     * This method assigns the move to the game
     * @param player a Boolean value to determine whether it is a computer/player move
     */
    public void assignMove(String number) {
        moves[row][col].setState(number);

    }
    /**
     * getRow
     * This method returns the current row value for this move. It allows another element of the program to access this move's current row.
     * @return the row value
     */
    public int getRow() {
        return row;
    }
    
    /**
     * getCol
     * This method returns the current col value for this move. It allows another element of the program to access this move's current col.
     * @return the Col value
     */
    public int getCol() {
        return col;
    }
    
    
    /**
     * getPrevMove
     * This method returns the previous state of the slot to perform the undo operation
     * 
     * @return previous value of the slot
     */
    public String getPrevMove() {
        return prevState;
    }
    
    /**
     * getNumber
     * This method returns the latest iput number to perform the redo operation
     * 
     * @return latest value of the slot
     */

    public String getNumber() {
        return number;
    }

}//End of class Assign
