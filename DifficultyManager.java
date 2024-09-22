/**
 * DifficultyManager
 * This class is used to manage logic of setting the difficulty of the game.
 * It is a subclass of the SudokuGUI class.
 * 
 * @author Arun Kumar Sekar
 * @version January 2024
 */
public class DifficultyManager {


    public enum Difficulty {
        EASY,
        HARD
    }

    private Difficulty currentDifficulty;
    private final int maxMoves9x9;
    private final int maxMoves4x4;

    /**
     * Constructor for objects of class DifficultyManager
     * initialises the default difficulty to EASY
     * initialises the constraints for the number of moves for each difficulty
     * 
     * @param maxMoves9x9 is the maximum number of moves allowed for a 9x9 grid
     * @param maxMoves4x4 is the maximum number of moves allowed for a 4x4 grid
     * 
     */
    public DifficultyManager() {
        currentDifficulty = Difficulty.EASY; // Default difficulty
        maxMoves9x9 = 100; // Example constraint for hard level in 9x9 game
        maxMoves4x4 = 20; // Example constraint for hard level in 4x4 game
    }

    /**
     * This method sets the difficulty of the game in the SudokuGUI class
     * 
     */
    public void setDifficulty(Difficulty difficulty) {
        this.currentDifficulty = difficulty;
    }

    /**
     * This method returns the difficulty of the game to the SudokuGUI class
     * 
     * @return current difficulty of the game
     */
    public Difficulty getDifficulty() {
        return currentDifficulty;
    }


    /**
     * This method checks if the player can make a move based on the current difficulty
     * 
     * @param currentMoveCount is the number of moves made by the player
     * @param gridSize is the size of the grid
     * 
     * @return true if the player can make a move, false otherwise
     */
    public boolean canMakeMove(int currentMoveCount, int gridSize) {
        if (currentDifficulty == Difficulty.EASY) {
            return true;
        } else {
            int maxMoves = (gridSize == 9) ? maxMoves9x9 : maxMoves4x4;
            return currentMoveCount < maxMoves;
        }
    }


    /**
     * This method returns the number of remaining moves based on the current difficulty
     * 
     * @param currentMoveCount is the number of moves made by the player
     * @param gridSize is the size of the grid
     * 
     * @return the number of remaining moves if the difficulty is HARD, -1 indicating unlimited moves for EASY
     */
    public int getRemainingMoves(int currentMoveCount, int gridSize) {
        if (currentDifficulty == Difficulty.HARD) {
            int maxMoves = (gridSize == 9) ? maxMoves9x9 : maxMoves4x4;
            return maxMoves - currentMoveCount;
        }
        return -1; 
    }

    /**
     * This method returns the maximum number of moves allowed for the current difficulty
     * 
     * @param gridSize is the size of the grid
     * 
     * @return the maximum number of moves allowed for the current difficulty
     */
    public int getMaxMoves(int gridSize) {
        return (gridSize == 9) ? maxMoves9x9 : maxMoves4x4;
    }
}// End of DifficultyManager
