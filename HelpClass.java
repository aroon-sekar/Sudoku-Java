import javax.swing.JOptionPane;

/**
 * This class is used to display a help dialogue box.
 * It is a subclass of the SudokuGUI class.
 * It inherits all the methods and variables of the SudokuGUI class.
 * 
 * @author Arun Kumar Sekar
 * @version January 2024
 */
public class HelpClass extends SudokuGUI {

    /**
     * Constructor for objects of class HelpClass
     * Calls the constructor of the SudokuGUI class
     */
    public HelpClass() {
        super();
    }
    

    /**
     * Displays a help dialogue box
     * It is called when the 'Help' button is clicked
     */
    public void displayHelpDialog() {
        JOptionPane.showMessageDialog(this, 
            "Help Information:\n" +
            "- Click on a cell to enter a number.\n" +
            "- Use the 'Undo' and 'Redo' buttons to undo or redo moves.\n" +
            "- You can 'Redo and 'Undo' a move any number of times. \n" +
            "- Use the 'Save' and 'Load' buttons to save or load games.\n" +
            "- The 'Clear' button resets the current game.\n" +
            "- To exit, click the 'Quit' button.\n"+
            "Text Color:\n" +
            "- The Greyed out numbers cannot be changed.\n" +
            "- Numbers already existing in the rows and columns are represented in 'Red' colour.\n" +
            "- A valid move is represented in 'Black' colour. \n" ,
            "Sudoku Help", JOptionPane.INFORMATION_MESSAGE);
    }
}// End of HelpClass
