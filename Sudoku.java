
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * This is the class Sudoku and it handles the functionality of the main game.
 * @author Lauren Scott
 * @version Student Sample Code
 */
public class Sudoku {
    private String[][] solution;//This array stores the solution to the game
    private Slot[][] populatedBoard;//This is the board of moves for the game
    private Scanner reader;//This scanner is used to read the game and level files
    private int gameSize;    //This will be the size of the game
    private String level = "Levels/su1.txt";//This is the level file,changable for easy and hard
    /**
     * This is the constructor for the class Sudoku
     */
    public Sudoku() {
        try {
            reader = new Scanner(new File(level));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        gameSize = calculateGameSize();
        solution = new String[gameSize][gameSize];
        populatedBoard = new Slot[gameSize][gameSize];
        readLevelFile();
        loadWinSolution();
    }
    /**
     * This method gets the entire set of moves in the game
     * @return the set of moves
     */
    public Slot[][] getMoves() {
        return populatedBoard;
    }
    /**
     * This method gets an individual cell's state
     * @param row - the row of the move
     * @param col - the column of the move
     * @return The state of that cell
     */
    public String getIndividualMove(int row, int col) {
        return populatedBoard[row][col].getState();
    }
    /**
     * This method reads the game size from the file 
     * @return the size of the puzzle
     */
    public int calculateGameSize() {
        return Integer.parseInt(reader.next());
    }
    /**
     * This method provides access to the gameSize from other classes
     * @return the size of the puzzle
     */
    public int getGameSize() {
        return gameSize;
    }
    /**
     * This method reads the level file to populate the game
     * @return The moves stored in the file
     */
    public Slot[][] readLevelFile() {
    
        while (reader.hasNext()) {
            int row =Integer.parseInt(reader.next());
            int col =Integer.parseInt(reader.next());
            String move = reader.next();
            
            populatedBoard[row][col] = new Slot(col, row, move, false);
            
        }
        return populatedBoard;
    }
    /**
     * This method reads the solution file that corresponds to the level file
     */
    public void loadWinSolution() {
        
        Scanner reader = null;
        try {
            reader = new Scanner(new File("Solutions/su1solution.txt"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while (reader.hasNext()) {
            int row =Integer.parseInt(reader.next());
            int col =Integer.parseInt(reader.next());
            String move = reader.next();
            solution[row][col] = move;
            
        }
        
    }
    /**
     * This method checks whether the gane has been won
     * @return whether the game has been won
     */
    public Boolean checkWin(){
        for (int i = 0; i<gameSize; i++) {
            for (int c = 0; c <gameSize; c++) {
                if (!populatedBoard[i][c].getState().equals(solution[i][c])) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * This method allows a user to make a move in the game
     * @param row - the row of the move
     * @param col - the column of the move
     * @param number - the number they are wishing to enter in the cell
     * @return whether the move was valid
     */
    public Boolean makeMove(String row, String col, String number){
        int enteredRow = Integer.parseInt(row);
        int enteredCol = Integer.parseInt(col);
        if (populatedBoard[enteredRow][enteredCol].getFillable()){
            populatedBoard[enteredRow][enteredCol].setState(number);
            return true;
        } else {
            return false;
        }
    }

}//end of class Sudoku
