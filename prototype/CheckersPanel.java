package prototype;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.Timer;

/**************************************************************
 * This class is the panel in which most of the graphical work is done. It draws
 * the checkerboard and calls most of the game logic functions.
 *
 * @author Gionata Bonazzi
 * @author Cole Sellers
 * @author Brendan Cronan
 * @author Rosa Fleming
 * @version stable build 17 April 2018
 **************************************************************/
@SuppressWarnings("serial")
public class CheckersPanel extends JPanel implements Observer {

    /* ***************************************************************
     * Holds the size of the panel. Board dimensions are based on this.
     * ***************************************************************/
    /** Size of the board.*/
    private Dimension size;

    /** Field that holds the conversion from milliseconds to seconds.*/
    private final int TIMER_DELAY = 1000;
    /** This is a variable that holds a percentage
     * that many calculations use for ease of use.*/
    private final float PERCENTAGE = (float) 0.75;
    /** Field variable for the board object.*/
    private Board board;
    /** Field variable for the game object.*/
    private Game game;
    /** 2D array of ColorStatus that represents
     * the board and checker placement.*/
    private ColorStatus[][] checkerColor;
    /** The top-left coordinate of the board.*/
    private int boardX, boardY;
    /** Width and height of the board.*/
    private int boardWidth, boardHeight;
    /** Size of each individual square.*/
    private int tileSize;
    /** This is the initial 2D array of possible checkers that can move.*/
    private boolean[][] canMove;
    /** Varies between possible checkers
     * and possible moves of a certain checker.*/
    private boolean[][] options;
    /** Frame holding this panel to update the timer.*/
    private Checkers_GUI parentFrame;
    /** Timer object.*/
    private Timer gameTimer;
    /** Graphics object updated every time paintComponent
     * is called. allows global access.*/
    private Graphics g;
    /** Boolean to determine if you are against a player or AI.*/
    private boolean againstAI;
    /** boolean to determine if this is the first
     * time you have clicked on a square.*/
    private boolean first = true;

    /**************************************************************
     * Main constructor for the panel. sets the size and adds listeners.
     *
     * @param xSize
     *            X dimension for the panel
     * @param ySize
     *            Y dimension for the panel
     * @param gui
     *            The JFrame that created this object
     **************************************************************/

    public CheckersPanel(final int xSize, final int ySize,
            final Checkers_GUI gui) {
        size = new Dimension(xSize, ySize);
        this.setPreferredSize(size);
        this.againstAI = true;
        this.parentFrame = gui;
        // this.setBackground(Color.RED);
        this.game = new Game(this, gui, this.againstAI);
        this.board = new Board(this.game);
        this.initBoard();
        this.addMouseListener(new MListener());
        canMove = board.canSelect(game.getCurrentPlayer());
        options = canMove;
        this.gameTimer = new Timer(TIMER_DELAY, new TimerListener());
        this.gameTimer.start();
    }

    /**************************************************************
     * Starts a new game against AI.
     *
     * @param player
     *            Player name, passed to the game class.
     * @param gameMode
     *            Game mode to start.
     **************************************************************/
    public void newGameAI(final String player, final GameMode gameMode) {
        this.first = true;
        this.againstAI = true;
        this.game.setGameMode(gameMode);
        this.game.startGameAI(player);
        this.board = new Board(this.game);
        this.options = board.canSelect(game.getCurrentPlayer());
        this.gameTimer = new Timer(TIMER_DELAY, new TimerListener());
        this.gameTimer.start();
    }

    /**************************************************************
     * This method gets the winner of the game when the time is up.
     *
     * @return The name of the player
     **************************************************************/
    public String getWinner() {
        return this.game.getCurrentPlayer().getName();
    }

    /**************************************************************
     * Set player names to two inputs.
     *
     * @param player1
     *            name of player 1
     * @param player2
     *            name of player 2
     **************************************************************/
    public void setPlayersNames(final String player1, final String player2) {
        this.game.setNames(player1, player2);
    }

    /**************************************************************
     * Creates all of the dimensions for the board and sets field variables to
     * those values.
     **************************************************************/
    private void initBoard() {

        int centerX = (int) (this.size.getWidth() / 2);
        int centerY = (int) (this.size.getHeight() / 2);
        boardX = (int) (centerX - (centerX * PERCENTAGE));
        boardY = (int) (centerY - (centerY * PERCENTAGE));

        double widOrHei = Math.min(size.width, size.height);

        boardWidth = (int) (widOrHei * PERCENTAGE);
        boardHeight = (int) (widOrHei * PERCENTAGE);

        tileSize = boardWidth / Board.SIZE;
    }

    /**************************************************************
     * This is the method that is called and draws nearly everything on the
     * board.
     *
     * @param graph
     *            This is a system variable.
     **************************************************************/
    public void paintComponent(final Graphics graph) {
        g = graph;

        g.setColor(Color.RED);

        // Draw the initial Board
        this.paintBoard(boardX, boardY, boardWidth, boardHeight);

        // Draw pieces
        this.paintPieces(boardX, boardY, boardWidth, boardHeight);

        // Highlight available pieces
        this.highlightCheckers(this.options);

    }

    /**************************************************************
     * This method highlights checkers that can move.
     *
     * @param brd
     *            which checkers to higlight in a 2D array of booleans.
     **************************************************************/
    private void highlightCheckers(final boolean[][] brd) {

        // The highlighting color.
        Color cy = Color.CYAN;

        // Draw a filled square for each true value in the matrix.
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (brd[row][col]) {
                    highlightSquare(col, row, cy);
                }
            }
        }

    }

    /**************************************************************
     * This method highlights a specific square given by x and y.
     *
     * @param x
     *            row to higlight.
     * @param y
     *            column to highlight.
     * @param c
     *            color with which to higlight.
     **************************************************************/
    private void highlightSquare(final int x, final int y, final Color c) {
        this.g.setColor(new Color(c.getRed(), c.getBlue(), c.getGreen(), 100));
        this.g.fillRect(boardX + x * tileSize, boardY + y * tileSize, tileSize,
                tileSize);
    }

    /**************************************************************
     * This helper method draws the squares.
     *
     * @param xI
     *            initial x position
     * @param yI
     *            initial y position
     * @param wid
     *            width of the board
     * @param hei
     *            height of the board
     **************************************************************/
    private void paintBoard(final int xI, final int yI, final int wid,
            final int hei) {
        /*
         * board Colors f0f1ff 1 square color aab1e6 2 square color 000000 white
         * piece ffffff black piece
         */
        // Color lightBlue = new Color(240,240,255);
        // Color darkBlue = new Color(170,177,230);
        Color lightBrown = new Color(210, 180, 140);
        Color darkBrown = new Color(153, 102, 0);

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {

                if (row % 2 == col % 2) {
                    g.setColor(darkBrown);
                } else {
                    g.setColor(lightBrown);
                }
                g.fillRect(xI + 1 + row * tileSize, yI + 1 + col * tileSize,
                        tileSize, tileSize);

            }
        }
    }

    /**************************************************************
     * This helper method draws the checkers.
     *
     * @param xI
     *            initial x position
     * @param yI
     *            initial y position
     * @param wid
     *            width of the board
     * @param hei
     *            height of the board
     **************************************************************/
    private void paintPieces(final int xI, final int yI, final int wid,
            final int hei) {

        checkerColor = board.getBoard();
        int x, y, w, h;
        int sx, sy;
        w = (int) (tileSize * PERCENTAGE);
        h = (int) (tileSize * PERCENTAGE);

        int fontSize = 30;

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                y = row * tileSize + yI + (tileSize - w) / 2;
                x = col * tileSize + xI + (tileSize - h) / 2;
                sy = row * tileSize + yI + (tileSize / 2) + (fontSize / 3);
                sx = col * tileSize + xI + (tileSize / 2) - (fontSize / 3);
                switch (checkerColor[row][col]) {
                case WHITE_KING:
                    g.setColor(Color.WHITE);
                    g.fillOval(x, y, w, h);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("SansSerif", Font.BOLD, fontSize));
                    g.drawString("K", sx, sy);
                    break;
                case WHITE:
                    g.setColor(Color.WHITE);
                    g.fillOval(x, y, w, h);
                    break;
                case BLACK_KING:
                    g.setColor(Color.BLACK);
                    g.fillOval(x, y, w, h);
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("SansSerif", Font.BOLD, fontSize));
                    g.drawString("K", sx, sy);
                    break;
                case BLACK:
                    g.setColor(Color.BLACK);
                    g.fillOval(x, y, w, h);
                    break;
                case EMPTY:
                    if (Checkers_GUI.debug) {
                        g.setColor(Color.RED);
                        g.fillRect(x, y, w, h);
                    }
                    break;
                default:
                    break;
                }
            }
        }
    }

    /**************************************************************
     * This method implements the observer pattern.
     *
     * @param o
     *            Observable object to recieve updates from.
     * @param arg
     *            Object for the argument to pass
     * @param player1
     * @param player2
     * @param gameMode
     **************************************************************/
    @Override
    public void update(final Observable o, final Object arg) {
        resetGame();
    }
    /** Create a new game. */
    public void newGame(final String player1,
            final String player2, final GameMode gameMode) {
        this.first = true;
        game.setGameMode(gameMode);
        game.startGame(player1, player2);
        this.againstAI = false;
        this.board = new Board(this.game);
        this.options = board.canSelect(game.getCurrentPlayer());
        this.gameTimer = new Timer(TIMER_DELAY, new TimerListener());
        this.gameTimer.start();
    }

    /**************************************************************
     * Method to end the game.
     **************************************************************/
    public void endGame() {
        game.nextPlayer();
        game.stopGame();
        this.board.setAllFalse();
        this.gameTimer.stop();
        repaint();
    }

    /**************************************************************
     * Method to restart the current game.
     **************************************************************/
    private void resetGame() {
        this.gameTimer.stop();
        this.options = this.board.setAllFalse();
        repaint();
    }

    /**************************************************************
     * Method to execute the AI turn.
     **************************************************************/
    private void makeAIturn() {
        // Making sure that is never called by error in a player vs player game.
        if (this.againstAI && this.game.isGameRunning()) {
            CheckersAI AI = (CheckersAI) game.getCurrentPlayer();
            AI.makeMove(this.board, this.options);
            this.game.nextPlayer();
            this.canMove = board.canSelect(game.getCurrentPlayer());
            this.options = canMove;
            repaint();
        }
    }

    /**************************************************************
     * Mouse listener class to detect clicks on different squares.
     **************************************************************/
    private class MListener implements MouseListener {
        /** If jump is not true.*/
        private boolean didJump = false;
        /** First row.*/
        private int originalRow;
        /** Last row.*/
        private int originalCol;

        @Override
        public void mouseReleased(final MouseEvent e) {
            int mx, my;
            mx = e.getX();
            my = e.getY();
            if (mx >= boardX && mx <= boardWidth + boardX) {
                if (my >= boardY && my <= boardWidth + boardY) {

                    int relX, relY;

                    relX = mx - boardX - 2; // loss of precision with integer
                                            // conversion
                    relY = my - boardY - 2; // loss of precision with integer
                                            // conversion

                    int col, row;
                    col = relX / tileSize;
                    row = relY / tileSize;
                    // options = canMove; // set to null because it checks for
                    // it in paintComponent.
                    // board.showOptions(game.getCurrentPlayer(), -1, -1);
                    // //sets all to false
                    if (Checkers_GUI.debug) {
                        System.out.println(
                                "SELECTED VALUE: " + checkerColor[row][col]);
                        System.out.println("COORDINATES: " + row + " ROW " + col
                                + " COLUMN.");
                    }

                    // After the debugging print of the coordinates and value
                    // selected, do nothing if the game is over.
                    if (!game.isGameRunning()) {
                        return;
                    }
                    // The position selected is a true (valid) position and it's
                    // the first action in the turn.
                    if (options[row][col] && first) {
                        originalRow = row;
                        originalCol = col;
                        options = board.showOptions(game.getCurrentPlayer(),
                                row, col);
                        first = false;
                    }
                    // The position selected is a true (valid) position and a
                    // checker has been selected.
                    else if (!first && options[row][col]) {

                        // If the selected move is the original checker.
                        if (col == originalCol && row == originalRow) {
                            first = true;
                            options = canMove;
                        } else {

                            // Prevent a player from doing more than 2 jumps in
                            // one turn.
                            if (didJump) {
                                board.move(game.getCurrentPlayer(), originalRow,
                                        originalCol, row, col);
                                didJump = false;
                            } else {
                                didJump = board.move(game.getCurrentPlayer(),
                                        originalRow, originalCol, row, col);
                                if (didJump) {
                                    originalRow = row;
                                    originalCol = col;
                                }
                            }

                            // If there was a jump, and the same checker can
                            // jump again, show the moves for
                            // that checker only
                            if (didJump && board.canJump(
                                    game.getCurrentPlayer().playerColor(),
                                    game.getCurrentPlayer().kingColor(),
                                    originalRow, originalCol)) {
                                options = board.showDoubleJumpOptions(
                                        game.getCurrentPlayer(), row, col);
                                originalCol = col;
                                originalRow = row;
                            } else {
                                game.nextPlayer();
                                canMove = board
                                        .canSelect(game.getCurrentPlayer());
                                options = canMove;
                                first = true;
                                this.didJump = false;
                                if (againstAI) {
                                    // Assuming that the human player is always
                                    // the white one.
                                    if (game.getCurrentPlayer()
                                            .playerColor()
                                            != ColorStatus.WHITE) {
                                        makeAIturn();
                                    }
                                }
                            }
                        }
                    }
                    repaint();
                }
            }
        }

        @Override
        public void mouseClicked(final MouseEvent e) {
        }

        @Override
        public void mousePressed(final MouseEvent e) {
        }

        @Override
        public void mouseEntered(final MouseEvent e) {
        }

        @Override
        public void mouseExited(final MouseEvent e) {
        }
    }

    /**************************************************************
     * Action listener for the timer in the JFrame above.
     **************************************************************/
    private class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            game.updateTime();
            parentFrame.updateTimeDisplay(game.getTime());
        }

    }

}
