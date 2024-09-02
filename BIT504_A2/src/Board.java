import java.awt.*;

/**
 * Represents the game board for Tic-Tac-Toe.
 */
public class Board {
    /** The width of the grid lines. */
    public static final int GRID_WIDTH = 8;

    /** Half the width of the grid lines for centering. */
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;

    /** 2D array of Cell instances representing the board grid. */
    Cell[][] cells;

    /**
     * Constructor to create the game board and initialize its cells.
     */
    public Board() {
        // Initialize the cells array with the specified number of rows and columns
        cells = new Cell[GameMain.ROWS][GameMain.COLS];

        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    /**
     * Checks whether the game has ended in a draw.
     * 
     * @return true if all cells are filled and no player has won; false otherwise.
     */
    public boolean isDraw() {
        // Iterate over all cells to check if any are still empty
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                if (cells[row][col].content == Player.Empty) {
                    return false; // Found an empty cell, not a draw
                }
            }
        }
        return true; // No empty cells, it is a draw
    }

    /**
     * Checks whether the specified player has won after their latest move.
     * 
     * @param thePlayer  The player to check for a winning condition.
     * @param playerRow  The row of the player's last move.
     * @param playerCol  The column of the player's last move.
     * @return true if the player has won; false otherwise.
     */
    public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
        // Check for 3-in-a-row horizontally
        if (cells[playerRow][0].content == thePlayer && cells[playerRow][1].content == thePlayer && cells[playerRow][2].content == thePlayer) {
            return true;
        }

        // Check for 3-in-a-column vertically
        if (cells[0][playerCol].content == thePlayer && cells[1][playerCol].content == thePlayer && cells[2][playerCol].content == thePlayer) {
            return true;
        }

        // Check for 3-in-a-diagonal (top-left to bottom-right)
        if (cells[0][0].content == thePlayer && cells[1][1].content == thePlayer && cells[2][2].content == thePlayer) {
            return true;
        }

        // Check for 3-in-a-diagonal (top-right to bottom-left)
        if (cells[0][2].content == thePlayer && cells[1][1].content == thePlayer && cells[2][0].content == thePlayer) {
            return true;
        }

        return false; // No winning condition met
    }

    /**
     * Draws the grid and the cells on the provided graphics context.
     * 
     * @param g The Graphics context to paint on.
     */
    public void paint(Graphics g) {
        // Draw the grid lines
        g.setColor(Color.gray);
        for (int row = 1; row < GameMain.ROWS; ++row) {
            g.fillRoundRect(0, GameMain.CELL_SIZE * row - GRID_WIDTH_HALF,
                    GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,
                    GRID_WIDTH, GRID_WIDTH);
        }
        for (int col = 1; col < GameMain.COLS; ++col) {
            g.fillRoundRect(GameMain.CELL_SIZE * col - GRID_WIDTH_HALF, 0,
                    GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,
                    GRID_WIDTH, GRID_WIDTH);
        }

        // Draw the individual cells
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col].paint(g);
            }
        }
    }
