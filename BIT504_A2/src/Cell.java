import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Represents a single cell on the Tic-Tac-Toe board.
 */
public class Cell {
    /** The content of this cell (Empty, Cross, or Naught). */
    Player content;

    /** The row and column position of this cell. */
    int row, col;

    /**
     * Constructor to initialize this cell with the specified row and column.
     * 
     * @param row The row of this cell.
     * @param col The column of this cell.
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        clear(); // Initialize cell content to Empty
    }

    /**
     * Paints the cell content on the graphics canvas.
     * 
     * @param g The Graphics context to paint on.
     */
    public void paint(Graphics g) {
        Graphics2D graphic2D = (Graphics2D) g;
        graphic2D.setStroke(new BasicStroke(GameMain.SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Calculate coordinates for drawing symbols
        int x1 = col * GameMain.CELL_SIZE + GameMain.CELL_PADDING;
        int y1 = row * GameMain.CELL_SIZE + GameMain.CELL_PADDING;

        if (content == Player.Cross) {
            graphic2D.setColor(Color.RED);
            int x2 = (col + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
            int y2 = (row + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
            graphic2D.drawLine(x1, y1, x2, y2);
            graphic2D.drawLine(x2, y1, x1, y2);
        } else if (content == Player.Naught) {
            graphic2D.setColor(Color.BLUE);
            graphic2D.drawOval(x1, y1, GameMain.SYMBOL_SIZE, GameMain.SYMBOL_SIZE);
        }
    }

    /**
     * Resets this cell's content to Empty.
     */
    public void clear() {
        content = Player.Empty;
    }
}
