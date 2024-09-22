import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;
import java.util.Scanner;

/**
 * This class is used to save and load games.
 * It is a subclass of the SudokuGUI class.
 * It inherits all the methods and variables of the SudokuGUI class.
 * 
 * JFILECHOOSER has been refernced from Oracle.com(2024).
 * Credited in the References in Report
 * 
 * @author Arun Kumar Sekar
 * @version January 2024
 * 
 */
public class FileDriver {

    /**
     * This method loads the previously saved game from the file (sudukoGUI.txt) in the same state it was saved in.
     * Constructor for objects of class FileDriver
     * Calls the constructor of the SudokuGUI class
     * Uses the file chooser from the swing package to enable the external file browser
     * Displays the file chooser to the user
     *
     * @throws FileNotFoundException if the file is not found
     */
    public static void saveGame(Sudoku theGame, int gridSize, JTextArea statusArea) {
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Sudoku Files", "txt"));
        int option = fileChooser.showSaveDialog(null); 

        if (option == JFileChooser.APPROVE_OPTION) {
            File saveFile = fileChooser.getSelectedFile();
            if (!saveFile.getName().endsWith(".txt")) {
                saveFile = new File(saveFile.getParentFile(), saveFile.getName() + ".txt");
            }

            try (PrintWriter writer = new PrintWriter(saveFile)) {
                writer.println(gridSize);

                for (int i = 0; i < gridSize; i++) {
                    for (int j = 0; j < gridSize; j++) {
                        String cellState = theGame.getIndividualMove(i, j);
                        writer.println(i + " " + j + " " + cellState);
                    }
                }
                statusArea.setText("Game saved to " + saveFile.getName());
            } catch (FileNotFoundException e) {
                statusArea.setText("Error saving game: " + e.getMessage());
            }
        }
    }

    /**
     * This method loads the previously saved game from the file (sudukoGUI.txt) in the same state it was saved in.
     * Constructor for objects of class FileDriver
     * Calls the constructor of the SudokuGUI class
     * Uses the file chooser from the swing package to enable the external file browser
     * Displays the file chooser to the user
     *
     * @throws FileNotFoundException if the file is not found
     */

    public static void loadGame(Sudoku theGame, int gridSize, JTextArea statusArea, SudokuGUI sudokuGUI) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Sudoku Files", "txt"));
        int option = fileChooser.showOpenDialog(null); // parent component to JFileChooser is null

        if (option == JFileChooser.APPROVE_OPTION) {
            File loadFile = fileChooser.getSelectedFile();

            try (Scanner scanner = new Scanner(loadFile)) {
                int savedGridSize = scanner.nextInt();
                if (savedGridSize != gridSize) {
                    throw new IllegalStateException("Saved game size does not match current game size.");
                }

                while (scanner.hasNextInt()) {
                    int row = scanner.nextInt();
                    int col = scanner.nextInt();
                    String value = scanner.next();
                    theGame.makeMove(Integer.toString(row), Integer.toString(col), value);
                }
                sudokuGUI.displayGame();
                statusArea.setText("Game loaded from " + loadFile.getName());
            } catch (FileNotFoundException e) {
                statusArea.setText("Error loading game: " + e.getMessage());
            } catch (IllegalStateException e) {
                statusArea.setText(e.getMessage());
            }
        }
    }
}// End of FileDriver
