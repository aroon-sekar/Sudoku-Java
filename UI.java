import java.util.*;
import java.io.*;

/**
 * This class provides a text based user interface for the player to interact with the game
 * It allows the user to make a move, save the game, load the game, undo a move, redo a move, clear the game and quit the game
 * It also displays the game in the terminal for the user to play
 * 
 * @author Lauren Scott
 * @author Arun Kumar Sekar
 * 
 * 
 * @version January 2024
 */
public class UI {
    
    /**
     * 
     * Default Constructor for the class UI
     * Creates a new TEXT-UI instance of the Sudoku game
     * Initializes the game variables
     * 
     */
    public UI() {
        thegame = new Sudoku();
        reader = new Scanner(System.in);
        
        uStack = new Stack<>();
        rStack = new Stack<>();
       
        gameLoop();
             
    }

    /**
     * winningAnnouncement
     * Method that outputs an announcement when the user has won the game
     */
    public void winningAnnouncement() {
        System.out.println("Yaaaayyyyyyyyy!!!!!!, you solved the puzzle");
        
    }
    /**
     * menu
     * Method that displays the menu to the user
     */
    public void menu() {

        System.out.println("Please select an option: \n"
            + "[M] make move\n"
            + "[S] save game\n"
            + "[L] load saved game\n"
            + "[U] undo move\n"
            + "[R] redo move\n"
            + "[C] clear game\n"
            + "[Q] quit game\n");

    }
    /**
     * 
     * displayGame
     * Method that displays the game for the user to play
     * It displays the game in the terminal
     * This method sets the game size to 9 or 4 depending on the level
     * Also this method setup the game layout in the Text-UI
     * 
     */
    public void displayGame() {
        if (thegame.getGameSize() == 9) {
            System.out.println("Col   0 1 2 3 4 5 6 7 8");
            System.out.println("      - - - - - - - - -");
        } else {
            System.out.println("Col   0 1 2 3 ");
            System.out.println("      - - - - ");
        }

        for (int i = 0; i < thegame.getGameSize(); i++) {
            System.out.print("Row "+i+"|");
            for (int c = 0; c < thegame.getGameSize(); c++) {
                if (thegame.getGameSize() == 9) {
                    if (c == 2 || c == 5 || c == 8) {
                        if (thegame.getIndividualMove(i,c).contains("-") ){
                            System.out.print(" " + "|");
                        } else{
                            System.out.print(thegame.getIndividualMove(i,c) + "|");
                        }
                    } else {
                        if (thegame.getIndividualMove(i,c).contains("-") ){
                            System.out.print(" " + ".");
                        } else{
                            System.out.print(thegame.getIndividualMove(i,c) + ".");
                        }
                    }

                } else if (thegame.getGameSize() == 4) {
                    if (c == 1 || c == 3) {
                        if (thegame.getIndividualMove(i,c).contains("-") ){
                            System.out.print(" " + "|");
                        } else{
                            System.out.print(thegame.getIndividualMove(i,c) + "|");
                        }
                    } else {
                        if (thegame.getIndividualMove(i,c).contains("-") ){
                            System.out.print(" " + ".");
                        } else{
                            System.out.print(thegame.getIndividualMove(i,c) + ".");
                        }
                    }

                
                }
            }if (thegame.getGameSize() == 9 && (i == 2 || i == 5|| i == 8)) {
                System.out.println("\n      - - - - - - - - -");

            } else if (thegame.getGameSize() == 9 ){
                System.out.println("\n      .................");

            } else if (thegame.getGameSize() == 4 && (i==1||i==3) ){
                System.out.println("\n      - - - - ");

            } else {
                System.out.println("\n     .........");
            }
        }
    }
    /**
     * Method that gets the user's choice from the menu and conducts the activities
     * accordingly
     * If invalid input is entered, the method will prompt the user to enter a valid input
     * It calls the makeMake method from the Sudoku class to make a move
     * 
     * 
     * @return the choice the user has selected
     * 
     */
    public String getChoice() {
         String choice = reader.next();
    if (choice.equalsIgnoreCase("M")) {
        int inputCheck = thegame.getGameSize() == 9 ? 9 : 4;
        
        // Here the row and column input are checked to ensure they are valid[0-8 or 0-3]

        System.out.print("Which row is the cell you wish to fill? (0-" + (inputCheck - 1) + ") ");
        String row = reader.next();
        // Validate the row input
        while (!row.matches("[0-" + (inputCheck - 1) + "]")) {
            System.out.println("Invalid input. Please enter a row number between 0 and " + (inputCheck - 1) + ".");
            row = reader.next();
        }
        
        System.out.print("Which column is the cell you wish to fill? (0-" + (inputCheck - 1) + ") ");
        String col = reader.next();
        // Validate the column input
        while (!col.matches("[0-" + (inputCheck - 1) + "]")) {
            System.out.println("Invalid input. Please enter a column number between 0 and " + (inputCheck - 1) + ".");
            col = reader.next();
        }
        
        System.out.print("Which number do you want to enter? (1-" + inputCheck + ")?  ");
        String number = reader.next();
        
        //checking if the input number is valid[1-9 or 1-4]
            while (!number.matches("[1-" + inputCheck + "]")) {
                System.out.println("Invalid input. Please enter a number between 1 and " + inputCheck + ".");
                System.out.print("Which number do you want to enter?  ");
                number = reader.next();
            }
        
        String prevState = thegame.getIndividualMove(Integer.parseInt(row), Integer.parseInt(col));

        if(thegame.makeMove(row, col, number)) {
          uStack.push(new Assign(thegame, Integer.parseInt(row), Integer.parseInt(col), number, prevState));//value pushed to ustack, added to undo stack
          System.out.println("Move made at row " + row + ", column " + col + " with number " + number); 
        } else {
            System.out.println("That cell cannot be changed or invalid input.");
            while (!thegame.makeMove(row, col, number)) {
                    System.out.print("Which row is the cell you wish to fill?  ");
                    row = reader.next();
                    System.out.print("Which colum is the cell you wish to fill?  ");
                    col = reader.next();
                    System.out.print("Which number do you want to enter?  ");
                    number = reader.next();
                }thegame.makeMove(row, col, number);
        }
    } else if (choice.equalsIgnoreCase("S")) {
        saveGame();
    } else if (choice.equalsIgnoreCase("U")) {
        undoMove();
    } else if (choice.equalsIgnoreCase("R")) {
        redoMove();
    } else if (choice.equalsIgnoreCase("L")) {
        loadGame();
    } else if (choice.equalsIgnoreCase("C")) {
        clearGame();
    } else if (choice.equalsIgnoreCase("Q")) {
        System.out.println("Quitting game.");
        System.exit(0);
    } else {
        System.out.println("Invalid Input. Please enter a choose a Valid option");
    }
    return choice;
    }
        

    /**
     * 
     * This method saves the current state of the game to a file (sudukoUI.txt)
     * It writes the value of each cell corresponding to the row and column to make it easier to retrieve the values
     * Also the game size is saved to the file
     * 
     * @throws FileNotFoundException if the file is not found
     * 
     */
    public void saveGame() {
          try {
        PrintWriter writer = new PrintWriter(new File(FILENAME));
        writer.println(thegame.getGameSize());
        
        for (int i = 0; i < thegame.getGameSize(); i++) {
            for (int j = 0; j < thegame.getGameSize(); j++) {
                String cellState = thegame.getIndividualMove(i, j);
                writer.println(i + " " + j + " " + cellState);
            }
        }

        writer.close();
        System.out.println("Sudoku game saved to file: " + FILENAME);
    } catch (FileNotFoundException e) {
        System.out.println("An error occurred while saving the Sudoku game to file: " + e.getMessage());
    }
    }

     /**
     * This method loads the previously saved game from the file (sudukoUI.txt) in the same state it was saved in.
     * 
     * @throws FileNotFoundException if the file is not found
     */
    public void loadGame() {
        try {
        Scanner fileScanner = new Scanner(new File(FILENAME));
        clearGame();
        
        int gameSize = fileScanner.nextInt();
        
        while (fileScanner.hasNext()) {
            int row = fileScanner.nextInt();
            int col = fileScanner.nextInt();
            String cellState = fileScanner.next();
            
            thegame.makeMove(Integer.toString(row), Integer.toString(col), cellState);
        }
        
        fileScanner.close();
        System.out.println("Sudoku game loaded from file: " + FILENAME);
    } catch (FileNotFoundException e) {
        System.out.println("An error occurred while loading the Sudoku game from file: " + e.getMessage());
    }
    }

    /**
     * 
     * This method performs the undo move resulting in the poping the last-in value in the ustack
     * It will also push the move back onto the redo stack in case the user wants to redo the move again
     * If there are no moves(empty stack), the method prints out "No moves to undo"
     * 
     */
    public void undoMove() {
         if (!uStack.isEmpty()) {
            Assign top = uStack.pop();
            rStack.push(top); // Adding to redo stack
            thegame.makeMove(Integer.toString(top.getRow()), Integer.toString(top.getCol()), top.getPrevMove());
            System.out.println("Undo move at row " + top.getRow() + ", column " + top.getCol());
        } else {
            System.out.println("No more moves to undo.");
        }
    }
    /**
     * 
     * This method implement redoMove to pop from value from the rStack if there are any and make the move again
     * It will also push the move back onto the undo stack in case the user wants to undo the move again
     * If there are no moves(empty stack), the method prints out "No moves to redo"
     * 
     */
    
    public void redoMove() {
        if (!rStack.isEmpty()) {
            Assign redoMove = rStack.pop();
            uStack.push(redoMove); // Push the move back onto the undo stack
            thegame.makeMove(Integer.toString(redoMove.getRow()), Integer.toString(redoMove.getCol()), redoMove.getNumber());
            System.out.println("Redo move at row " + redoMove.getRow() + ", column " + redoMove.getCol());
        } else {
            System.out.println("No moves to redo.");
        }
    }
    
     /**
     * 
     * This method resets the current game to a new game
     * It resets all the stacks 
     *  
     */
    public void clearGame() {
        thegame = new Sudoku();
        uStack = new Stack<>();
        rStack = new Stack<>();
        System.out.println("Game Reset. Starting a new Game\n");
        System.out.println("GOOD LUCK MATE!\n");
    }

    /**
     * 
     * This method allows the user to replay the game once they have won 
     * Once the player wins and selects Y to replay the game, the game is reset and the user can play again
     * If N is selected, the game ends
     * 
     */
    
    public void replayGame() {
        Scanner input = new Scanner(System.in);
        System.out.println("Would you like to play again? (Y/N)");
        String userChoice = input.nextLine();

        if (userChoice.equalsIgnoreCase("Y")) {
            thegame = new Sudoku();
            uStack = new Stack<>();
            rStack = new Stack<>();
            gameLoop();
            
            
        } else {
            
            System.out.println("Thank you for playing!");
            System.exit(0);
        }
    }

    /**
     * The main method within the Java application. 
     * It's the core method of the program and calls all others.
     */
    public static void main(String args[]) {
        UI thisUI = new UI();
    }

    private Sudoku thegame;//this is the game model
    private String menuChoice;//this is the users choice from the menu
    private Scanner reader;//this scanner is used to read the terminal
     
    private Stack<Assign> uStack;//this is the stack for undo method
    private Stack<Assign> rStack; // this is the stack for redo method
    



    /**
     * It conducts the main loop of the game
     * It runs the game until the user quits[Q] or wins and choose to replay the game[Y/N]
     * 
     */
    private void gameLoop() {
        menuChoice = "";
        while (!menuChoice.equalsIgnoreCase("Q") && !thegame.checkWin()) {
            displayGame();
            menu();
            menuChoice = getChoice();
        }

        if (thegame.checkWin()) {
            winningAnnouncement();
            replayGame();
        }
    }
    
    private static final String FILENAME = "sudukoUI.txt";//the Game is saved and loaded to this file


}//end of class UI