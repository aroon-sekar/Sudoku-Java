import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import java.util.Stack;
import java.util.Observer;
import java.util.Observable;



/**
* SudokuGUI
* This class is the GUI for the Sudoku game. It contains the main method and the GUI elements.
* The class extends JPanel and implements Observer to observe changes in the game.
*
* @author Arun Kumar Sekar

* @version January 2024
*/
@SuppressWarnings({ "deprecation", "serial" })
    public class SudokuGUI extends JPanel implements Observer {
    
        private int GRID_SIZE;//The size of the grid
        private Sudoku theGame;
        private int moveCount;//The number of moves made
        
        private Stack<Assign> uStack;
        private Stack<Assign> rStack;
        
        private JButton[][] cells; //Array of buttons to represent the cells
        private JButton saveButton, loadButton, clearButton, undoButton, redoButton, helpButton, quitButton;
       
        private JTextArea statusArea;
        private JTextArea moveHistoryArea;
        
        private JLabel moveCountLabel;
        private JLabel remainingMovesLabel;
        
        private DifficultyManager difficultyManager;

    /**
    * 
    * SudokuGUI
    * This is the constructor for the SudokuGUI class.
    * Initializes the Sudoku game model and sets up the graphical user interface components.
    * This includes creating the game grid, setting up control buttons (Save, Load, Clear, Undo, Redo, Help, Quit),
    * It also sets up the stacks for undo and redo.Constructor for the SudokuGUI class.
    *    
    */     

    public SudokuGUI() {
        theGame = new Sudoku();
        difficultyManager = new DifficultyManager();    
        this.GRID_SIZE = theGame.getGameSize(); // Sets the grid size based on the game file automatically

        //Setting up the difficulty Label
        difficultyManager = new DifficultyManager();
        remainingMovesLabel = new JLabel("");
        remainingMovesLabel.setHorizontalAlignment(JLabel.CENTER);
        remainingMovesLabel.setBorder(new LineBorder(Color.BLACK));
       
        //Setting up the move count Label
        moveCount = 0;
        moveCountLabel = new JLabel("Moves: 0");
        moveCountLabel.setHorizontalAlignment(JLabel.CENTER);
        moveCountLabel.setBorder(new LineBorder(Color.BLACK));
              
        // Setting up the border and padding for the main game grid
        Border roundedCornerBorder = new LineBorder(Color.BLACK, 1, true);
        Border paddingBorder = new EmptyBorder(10, 10, 10, 10);
        Border compoundBorder = new CompoundBorder(roundedCornerBorder, paddingBorder);
        
        // Setting up the main game grid
        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        gridPanel.setBorder(compoundBorder);
        cells = new JButton[GRID_SIZE][GRID_SIZE];
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col] = createCellButton(row, col);
                cells[row][col].setBorder(new RoundedBorder(10));
                gridPanel.add(cells[row][col]);
            }
        }

        // Setting up the buttons
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ae) {
          saveGame();
         }
        });
        
        loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ae) {
          loadGame();
         }
        });
        
        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ae) {
          clearGame();
         }
        });

        undoButton = new JButton("Undo");
        undoButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ae) {
          undoMove();
         }
        });
        
        redoButton = new JButton("Redo");
        redoButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ae) {
          redoMove();
         }
        });
        
        helpButton = new JButton("Help");
        helpButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent ae) {
         new HelpClass().displayHelpDialog();
         }
        });
       
        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ae) {
          System.exit(0);
         }
        });
        
        // Customize buttons with rounded corners
        saveButton.setBorder(new RoundedBorder(20));
        loadButton.setBorder(new RoundedBorder(20));
        clearButton.setBorder(new RoundedBorder(20));
        undoButton.setBorder(new RoundedBorder(20));
        redoButton.setBorder(new RoundedBorder(20));
        helpButton.setBorder(new RoundedBorder(20));
        quitButton.setBorder(new RoundedBorder(20));
        

        // Setting up the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(paddingBorder);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);
        buttonPanel.add(helpButton);
        buttonPanel.add(quitButton);
        
        buttonPanel.add(moveCountLabel);
        buttonPanel.add(remainingMovesLabel);

        // Setting up the status display area
        statusArea = new JTextArea(2, 20);
        statusArea.setEditable(false);
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(new JScrollPane(statusArea), BorderLayout.CENTER);
        statusPanel.setBorder(BorderFactory.createTitledBorder(compoundBorder, "Instructions"));

        // Setting up the move history area
        moveHistoryArea = new JTextArea(5, 20);
        moveHistoryArea.setEditable(false);
        JPanel moveHistoryPanel = new JPanel(new BorderLayout());
        moveHistoryPanel.add(new JScrollPane(moveHistoryArea), BorderLayout.CENTER);
        moveHistoryPanel.setBorder(BorderFactory.createTitledBorder(compoundBorder, "Keeping Track!"));

        // Setting up the main panel layout
        setLayout(new BorderLayout(10, 10)); 
        add(statusPanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
        add(moveHistoryPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        // Initialize the stacks
        uStack = new Stack<>();
        rStack = new Stack<>();

        // Observing the changes
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                theGame.getMoves()[row][col].addObserver(this);
            }
        }
              
        // Display the initial state of the game
        displayGame();
        
        // Update the cell appearance
        cellGraphics();
        
        //updating the remaining moves label
        updateRemainingMovesLabel();
    }
    
    
    /**
    * 
    * This class is used to create a rounded border 
    * It is used as a helper class for the SudokuGUI class.
    * The Rounded border ovverides the default border 
    *
    */  

    class RoundedBorder implements Border {
        private int radius;
        RoundedBorder(int radius) {
            this.radius = radius;
        }
        /**
        * 
        * It returns the insets of the border.
        * 
        * @param c The component for which this border insets value applies
        * @return The insets of the border
        *       
        */  
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }
        /**
        * 
        * Says whether the border is opaque or not.
        * 
        * @return True if the border is opaque, false otherwise
        *      
        */ 
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
        /**
        * 
        * This method paints the border of the specific component.
        * 
        * @param c The component for which this border is being painted
        * @param g The graphics context to use for painting the border
        * @param x,y The x and y position of the painted border
        * @param width,height The width and height of the painted border
        *       
        */ 
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }

    /**
    * createCellButton
    * This method creates a new cell for the Sudoku grid.
    * 
    * @param row The row number of the button
    * @param col The column number of the button

    * @return A JButton configured corresponding to the row and column
    */
    private JButton createCellButton(int row, int col) {
        JButton button = new JButton();
        button.addActionListener(e -> cellSelect(row, col));
        return button;
    }


    /**
    * moveCountLabel
    * Updates the label that displays the current move count.
    */
    private void moveCountLabel() {
        moveCountLabel.setText("Moves: " + moveCount);
    }
    
   /**
   * initializeDifficulty 
   * Initializes the select difficulty frame of the Sudoku game.
   * This method sets the difficulty and updates the label displaying the remaining moves.
   */
    public void initializeDifficulty() {
        selectDifficulty();
        updateRemainingMovesLabel();
    }
    
    /**
   * latestCellValue
   * Updates the value of the the specific cell
   * Sets the text to the current value
   * Also checks if the cell is fillable or not
   * 
   * @param row The row number of the cell
   * @param col The column number of the cell
   * 
   */ 
    private void latestCellValue(int row, int col) {
        JButton cellButton = cells[row][col];
        String cellValue = theGame.getIndividualMove(row, col);
        cellButton.setText(cellValue.equals("-") ? "" : cellValue);
        cellButton.setEnabled(isCellFillable(row, col));
    }

    /**
    * 
    * cellGraphics 
    * This method defines graphical properties of all cells in the Sudoku grid.
    * This includes the background color, text color, font, and border.
    * 
    */
    private void cellGraphics() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JButton cell = cells[row][col];
                if (this.isCellFillable(row, col)) {
                    cell.setBackground(Color.GREEN);
                } else {
                    cell.setBackground(Color.WHITE);
                }
                cell.setForeground(Color.BLACK);
                cell.setFont(new Font("Manrope", Font.BOLD, 20));
                cell.setBorder(new RoundedBorder(10));
            }
        }
    }
  
    /**
   * 
   * replayGame
   * Asks the user to play another game upon successfully solving the puzzle.
   * If the user chooses to play again, the game state is reset. Otherwise, the application exits.
   *
   */    
    private void replayGame() {
        int playAgainOption = JOptionPane.showConfirmDialog(this, "Yaaaayyyyyyyyy!!!!!!, you solved the puzzle\nWould you like to play again?", "Play Again?", JOptionPane.YES_NO_OPTION);
        if (playAgainOption == JOptionPane.YES_OPTION) {
            clearGame();
        } else {
        System.exit(0); 
        }
    }

    /**
    * 
    * displayGame
    * Updates the display of the entire game grid based on the current state of the game.
    * Each cell's text is set to display the value of the corresponding cell in the game model.
    * Once the grid is updated, the method checks if the game has been won. If so, it disables all cells.
    * 
    *
    */
    public void displayGame() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JButton cellButton = cells[row][col];
                String cellValue = theGame.getIndividualMove(row, col);
                cellButton.setText(cellValue.equals("-") ? "" : cellValue);
                cellButton.setEnabled(isCellFillable(row, col)); // Enable based on fillability
            }
        }    
        // Check if the game is solved and prompt to play again
        if (theGame.checkWin()) {
            for (JButton[] cellRow : cells) {
                for (JButton cell : cellRow) {
                    cell.setEnabled(false); // Disable all cells if the game is won
                }
            }
            replayGame();
        }
    }


    
    /**
    * CellSelect
    * Handles user interaction when a cell is selected in the Sudoku grid.
    * If a cell is fillable, it prompts the user to enter a number for the cell.
    * Validates the input to ensure it's a valid number for the specific Sudoku grid[9x9 or 4x4].
    * 
    * Also checks against the difficulty settings to limit the moves
    *
    * @param row The row number of the clicked cell.
    * @param col The column number of the clicked cell.
    *
    */
    private void cellSelect(int row, int col) {
        if (!isCellFillable(row, col)) {
            statusArea.setText("This cell cannot be changed.");
            return;
        }

        int maxValidNumber = GRID_SIZE == 9 ? 9 : 4;
        String number = JOptionPane.showInputDialog(this, "Enter a number for this box:(1-" + maxValidNumber + "): ");

        // Validate the input number
        while (number != null && !number.matches("[1-" + maxValidNumber + "]")) {
            statusArea.setText("Invalid input. Please enter a number between 1 and " + maxValidNumber + ".");
            number = JOptionPane.showInputDialog(this, "Enter a number for this box (1-" + maxValidNumber + "):");
        }

        if (number != null && number.matches("[1-" + maxValidNumber + "]")) {
            String prevState = theGame.getIndividualMove(row, col);
            // Check if the number is already in the same row or column
            boolean numberExists = isNumberInRowOrColumn(row, col, number);
            if (theGame.makeMove(Integer.toString(row), Integer.toString(col), number)) {
                uStack.push(new Assign(theGame, row, col, number, prevState));
                cells[row][col].setText(number);
                cells[row][col].setForeground(Color.BLACK);
                cells[row][col].setEnabled(true); 
                displayGame();
                moveCount++;
                moveCountLabel();
                updateMoveHistory("Move made at row " + row + ", column " + col + " = " + number + ". Total moves: " + moveCount);

                if (numberExists) {
                    statusArea.setText("Number " + number + " already exists in the same row or column. You can undo this.");
                    cells[row][col].setForeground(Color.RED);
                } else {
                    updateMoveHistory("Move made at row " + row + ", column " + col + " = " + number);
                    statusArea.setText("Valid Move");
                    cells[row][col].repaint();
                }

            } else {
                statusArea.setText("Invalid move. Please try again.");
            }
        }
    
        if (difficultyManager.canMakeMove(moveCount, GRID_SIZE)) {
            updateRemainingMovesLabel();
        } else {
            JOptionPane.showMessageDialog(this, "No more moves allowed on Hard difficulty.", "Move Limit Reached!!", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
    }   

    /**
    * Checks if a given number exists in the same row or column as the specified cell.
    * 
    * @param row    The row of the cell being checked.
    * @param col    The column of the cell being checked.
    * @param number The number to check for in the same row or column.
    *
    * @return true if the number exists in the same row or column, false otherwise.
    */

    private boolean isNumberInRowOrColumn(int row, int col, String number) {
    
        for (int c = 0; c < GRID_SIZE; c++) {
            if (c != col) { 
                String cellValue = theGame.getIndividualMove(row, c);
                if (number.equals(cellValue)) {
                    return true; // Number found in the same column
                }
            }
        }
        for (int r = 0; r < GRID_SIZE; r++) {
            if (r != row) { 
                String cellValue = theGame.getIndividualMove(r, col);
                if (number.equals(cellValue)) {
                    return true; // Number found in the same column
                }
            }
        }
        return false; // Number not found in the same row or column
    }

    /**
    * Saves the current game state to a file using the FileDriver class.
    */
    private void saveGame() {
        FileDriver.saveGame(theGame, GRID_SIZE, statusArea);
    }

    /**
    * loads the saved game state from a file using the FileDriver class.
    */
    private void loadGame() {
        FileDriver.loadGame(theGame, GRID_SIZE, statusArea, this);
    }

    /**
     * clearGame
     * This method resets the current game to a new game
     * It resets all the stacks 
     * Also the UI and move counts are updated to a new game
     *  
     */
    private void clearGame() {
        theGame = new Sudoku(); // Reset the game
    
        uStack.clear(); // Clear the move history
        rStack.clear();
    
        displayGame(); // Update the UI
        statusArea.setText("Game cleared.");
    
        moveCount = 0;
        moveCountLabel();
    }

    /**
     * undoMove
     * This method performs the undo move resulting in the poping the last-in value in the ustack
     * It will also push the move back onto the redo stack in case the user wants to redo the move again
     * If there are no moves(empty stack), the method prints out "No moves to undo"
     * Updates the move count, the move history area and the status area
     * 
     */
    private void undoMove() {
        if (uStack.isEmpty())
        {
            statusArea.setText("No moves to undo.");               
        } else {
            Assign top = uStack.pop();
            rStack.push(top); // Save the undone move for redoing
    
            theGame.makeMove(Integer.toString(top.getRow()), Integer.toString(top.getCol()), top.getPrevMove());
            displayGame();
            cellGraphics();
            
            statusArea.setText("Move undone.");
            moveCount--;
            moveCountLabel();
            updateMoveHistory("Undo made at row " + top.getRow() + ", column " + top.getCol() + ". Total moves: " + moveCount);   
        }   
    }
    
      /**
     * 
     * This method implement redoMove to pop from value from the rStack if there are any and make the move again
     * It will also push the move back onto the undo stack in case the user wants to undo the move again
     * If there are no moves(empty stack), the method prints out "No moves to redo"
     * Updates the move count, the move history area and the status area
     * 
     */
    private void redoMove() {
        if (!rStack.isEmpty()) 
        {
            Assign top = rStack.pop();
            uStack.push(top); // Push the redone move back onto the undo stack
            
            theGame.makeMove(Integer.toString(top.getRow()), Integer.toString(top.getCol()), top.getNumber());
            displayGame();
            cellGraphics();
            
            
            statusArea.setText("Move redone.");
            moveCount++;
            moveCountLabel();
            updateMoveHistory("Redo made at row " + top.getRow() + ", column " + top.getCol() + ". Total moves: " + moveCount);   
        } else {
            statusArea.setText("No moves to redo.");
        }
    }
       
    /**
     * updateMoveHistory
     * This method updates the move history area with the latest move
     * 
     * @param move
     */
    private void updateMoveHistory(String move) {
        moveHistoryArea.append(move + "\n"); // Append the move to the text area
        moveHistoryArea.setCaretPosition(moveHistoryArea.getDocument().getLength()); // Auto-scroll to the latest move
    }
    
    /**
     * isCellFillable
     * This method checks if the cell is fillable or not
     * 
     * @param row
     * @param col
     * @return boolean true is the cell is fillable, false otherwise
     */
    public boolean isCellFillable(int row, int col) {
        return theGame.getMoves()[row][col].getFillable();
    }

    /**
     * selectDifficulty
     * This method displays the difficulty selection frame
     * It sets the difficulty based on the user selection [EASY, HARD]
     * Uses the difficulty manager class to set the moves based on the difficulty
     * calls the updateRemainingMovesLabel() to update the remaining moves label
     */
    private void selectDifficulty() {
        Object[] options = {"Easy", "Hard"};
        int choice = JOptionPane.showOptionDialog(this, 
            "Select Difficulty Level:", 
            "Difficulty Selection", 
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.INFORMATION_MESSAGE, 
            null, options, options[0]);
    
        if (choice == 0) {
            difficultyManager.setDifficulty(DifficultyManager.Difficulty.EASY);
        } else if (choice == 1) {
            difficultyManager.setDifficulty(DifficultyManager.Difficulty.HARD);
        }
        updateRemainingMovesLabel();
        clearGame();
    }
    

    /**
     * updateRemainingMovesLabel
     * This method updates the remaining moves label based on the difficulty
     * If the difficulty is hard, it will display the remaining moves
     * If the difficulty is easy, it will display unlimited moves
     */
    private void updateRemainingMovesLabel() {
        if (difficultyManager.getDifficulty() == DifficultyManager.Difficulty.HARD) {
            int remainingMoves = difficultyManager.getRemainingMoves(moveCount, GRID_SIZE);
            remainingMovesLabel.setText("Remaining Moves: " + remainingMoves);
        } else {
            remainingMovesLabel.setText("Unlimited Moves");
        }
    }
    
    /**
    * Called whenever an observed object is changed. This method is part of the Observer implementation.
    * It updates the value displayed in a cell if the corresponding Observable object (a move) has changed.
    * 
    * @param o The observable object.
    * @param arg An argument passed to the notifyObservers method (not used here).
    */
    @Override
    public void update(Observable o, Object arg) {
        // This method is called whenever the observed object is changed
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (o == theGame.getMoves()[row][col]) {
                    latestCellValue(row, col); // Update the specific cell
                }
            }
        }
    }
    /**
     * main method
     * This is the main method of the SudokuGUI class
     * It creates a new JFrame and adds the SudokuGUI to the frame
     * 
     */ 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sudoku");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            SudokuGUI sudokuGUI = new SudokuGUI();
            sudokuGUI.initializeDifficulty(); 
            frame.getContentPane().add(sudokuGUI);
            frame.pack();
            frame.setVisible(true);
        });
    }
}//end of class SudoKuGUI
