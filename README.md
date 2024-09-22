
# Sudoku Game - Object-Oriented Programming Project

## Overview
This implements a **Sudoku** game built using **Java**. The project explores key concepts of **Object-Oriented Programming (OOP)**, such as inheritance, encapsulation, and modularity. The game includes both a **text-based interface** and a **Graphical User Interface (GUI)** using **Java Swing**. The application offers a user-friendly Sudoku gameplay experience with several features like undo/redo, difficulty selection, and save/load game functionality.

## Features
- **Multiple Difficulty Levels**: Offers both 4x4 (easy) and 9x9 (hard) game modes.
- **Text-Based Interface**: Play using command-line inputs with options to:
  - Make moves
  - Undo/Redo moves
  - Save and load games
  - Reset the game
- **Graphical User Interface (GUI)**: A visually interactive interface with:
  - Number validation (with color-coded feedback for invalid entries)
  - Move history tracking
  - Dynamic difficulty selection
  - File handling for saving and loading game states
- **Undo/Redo Functionality**: Implemented using stacks to track user moves.
- **Observer Pattern**: Real-time updates for the GUI as game states change.

## Class Design and Architecture
The design follows **Object-Oriented Principles** to ensure modularity and scalability. The major classes include:

### Core Classes
- **Sudoku**: Contains the core game logic, manages the board state, and validates moves.
- **Slot**: Represents an individual cell in the Sudoku grid, encapsulating its value and state.
- **Assign**: Handles user input, making tracking and managing moves easier.
  
### Interface Classes
- **UI**: Implements the text-based interface, providing functionality for making moves, saving/loading the game, and undoing/redoing.
- **SudokuGUI**: Extends `JPanel` and implements `Observer`, responsible for the graphical interface and interactive features.
  
### Additional Components
- **FileDriver**: Handles saving and loading the game state from files.
- **HelpClass**: Provides instructions and user guidance through a dialog box.
- **DifficultyManager**: Manages the difficulty settings and game variations.

## Design and Implementation Decisions
Key design choices include:
- **Modularity**: Each component has a single responsibility, making the system easier to maintain and expand.
- **Use of Stacks**: The undo/redo feature is implemented using stacks, a natural fit for tracking user moves.
- **Observer-Observable Pattern**: The GUI is updated dynamically using the observer pattern, separating game logic from the display logic.

## GUI Overview
- **Difficulty Selection**: Choose between easy (4x4) and hard (9x9) puzzles.
- **Move History Panel**: View previous moves and utilize undo/redo functionality.
- **Game Grid**: Interactive grid where users can input their numbers.
- **File Operations**: Save or load game states using a file browser.

![Sudoku GUI Screenshot](https://github.com/aroon-sekar/Sudoku-Java/blob/main/gui.png?raw=true)
![Sudoku UI Screenshot](https://github.com/aroon-sekar/Sudoku-Java/blob/main/ui.png?raw=true)

## How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/sudoku-game.git
   cd sudoku-game
   ```
2. Compile and run the program:
   ```bash
   javac -d bin src/*.java
   java -cp bin Main
   ```

## Testing
The project has been tested using both **White-Box** and **Black-Box** testing strategies:
- **White-Box Testing**: Unit tests were created using JUnit to validate core game logic, such as move validation, game state updates, and more.
- **Black-Box Testing**: Manual testing for the GUI and text-based UI to ensure smooth interactions and functionality.

Key tests:
- **Condition Testing**: Validated logical conditions within the core methods.
- **Integration Testing**: Ensured proper interaction between the `Sudoku` and `Assign` classes.
- **Usability Testing**: Verified that the GUI is user-friendly and provides appropriate feedback.

## Known Bugs and Limitations
- The text color for invalid numbers does not revert after undo/redo actions.
- The GUI is not fully responsive to window resizing.


