import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The main class for the Tic-Tac-Toe game.
 * It handles the game logic, GUI, and user interactions.
 */
public class GameMain extends JPanel implements MouseListener {
    // Add serialVersionUID for the Serializable class
    private static final long serialVersionUID = 1L;

    // Constants for game setup
    public static final int ROWS = 3;  // Number of rows
    public static final int COLS = 3;  // Number of columns
    public static final String TITLE = "Tic Tac Toe";  // Game title

    // Constants for dimensions used for drawing
    public static final int CELL_SIZE = 100;  // Width and height of a cell
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;  // Canvas width
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;  // Canvas height
    public static final int CELL_PADDING = CELL_SIZE / 6;  // Padding within a cell
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;  // Size of symbols (X or O)
    public static final int SYMBOL_STROKE_WIDTH = 8;  // Stroke width of the symbols

    // Game objects
    private Board board;  // The game board
    private GameState currentState;  // The current state of the game (Playing, Draw, Won)
    private Player currentPlayer;  // The current player (Cross or Nought)
    private JLabel statusBar;  // Status bar to display game status messages
    
    /**
     * Constructor to set up the game components and initialize the UI.
     */
    public GameMain() {
        // Register mouse event listener for the panel
        this.addMouseListener(this);

        // Setup the status bar to display status messages
        statusBar = new JLabel("         ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.LIGHT_GRAY);

        // Set the layout of the panel and add the status bar at the bottom
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);

        // Set the preferred size of the game board
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

        // Initialize the game board
        board = new Board();

        // Initialize the game state
        initGame();
    }

    /**
     * The main entry point for the application.
     * It sets up the main game window.
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create the main window to contain the game panel
                JFrame frame = new JFrame(TITLE);

                // Create a new instance of the GameMain panel and add it to the frame
                GameMain gameMain = new GameMain();
                frame.add(gameMain);

                // Set the default close operation of the frame to exit on close
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.pack();
                frame.setLocationRelativeTo(null);  // Center the window
                frame.setVisible(true);  // Make the window visible
            }
        });
    }

    /**
     * Custom painting method for the game board.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Clear the background
        setBackground(Color.WHITE);

        // Ask the game board to paint itself
        board.paint(g);

        // Update the status bar message based on the game state
        if (currentState == GameState.Playing) {
            statusBar.setForeground(Color.BLACK);
            if (currentPlayer == Player.Cross) {
                statusBar.setText("X's Turn");
            } else {
                statusBar.setText("O's Turn");
            }
        } else if (currentState == GameState.Draw) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == GameState.Cross_won) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == GameState.Naught_won) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    /**
     * Initializes the game board and sets the game state to start a new game.
     */
    public void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board.cells[row][col].clear();  // Clear all cells
            }
        }
        currentState = GameState.Playing;  // Set the game state to Playing
        currentPlayer = Player.Cross;  // 'X' starts first
    }

    /**
     * Updates the game state after each move.
     * 
     * @param thePlayer The player making the move.
     * @param row The row index of the move.
     * @param col The column index of the move.
     */
    public void updateGame(Player thePlayer, int row, int col) {
        if (board.hasWon(thePlayer, row, col)) {
            // Check which player has won and update the game state
            currentState = (thePlayer == Player.Cross) ? GameState.Cross_won : GameState.Naught_won;
        } else if (board.isDraw()) {
            // Update the game state to Draw
            currentState = GameState.Draw;
        }
        // Otherwise, keep playing
    }

    /**
     * Handles the mouse click event on the game board.
     * 
     * @param e The mouse event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Get the row and column of the clicked cell
        int rowSelected = mouseY / CELL_SIZE;
        int colSelected = mouseX / CELL_SIZE;

        if (currentState == GameState.Playing) {
            if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS && board.cells[rowSelected][colSelected].content == Player.Empty) {
                // Update the cell content and state
                board.cells[rowSelected][colSelected].content = currentPlayer;
                updateGame(currentPlayer, rowSelected, colSelected);

                // Switch player
                currentPlayer = (currentPlayer == Player.Cross) ? Player.Nought : Player.Cross;
            }
        } else {
            // Restart the game if it's over
            initGame();
        }

        // Redraw the graphics on the UI
        repaint();
    }

    // Other overridden methods from MouseListener interface (unused)
    @Override
    public void mousePressed(MouseEvent e) { /* Not used */ }

    @Override
    public void mouseReleased(MouseEvent e) { /* Not used */ }

    @Override
    public void mouseEntered(MouseEvent e) { /* Not used */ }

    @Override
    public void mouseExited(MouseEvent e) { /* Not used */ }
}
