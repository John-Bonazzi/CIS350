package prototype;

import java.util.Observable;

/***************************************************************
 * The Board class is used to create the board and hold the logic that is used
 * to play checkers.
 *
 * @author Gionata Bonazzi
 * @author Cole Sellers
 * @author Brendan Cronan
 * @author Rosa Fleming
 * @version stable build 17 April 2018
 ***************************************************************/
public class Board extends Observable {
   /**The final SIZE of the board is 8x8.*/
    public static final int SIZE = 8;
    /**Creates a instance of the board set to the size of the board.*/
    private ColorStatus[][] board = new ColorStatus[SIZE][SIZE];

    /***************************************************************
     * Constructor of the board class, it takes in an Object of the game abd
     * adds an observer of and initialize the board.
     *
     * @param game
     *            A instance of the game class.
     ***************************************************************/
    public Board(final Game game) {
        this.addObserver(game);
        Init();
    }

    /***************************************************************
     * initializes the board to 8x8 setting the rows and columns. it then sets
     * the colorStatus of each specific tile to either White, Black, or empty.
     ***************************************************************/
    private void Init() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (r % 2 != c % 2) {
                    if (r < 3) {
                        this.board[r][c] = ColorStatus.BLACK;
                    } else if (r > 4) {
                        this.board[r][c] = ColorStatus.WHITE;
                    } else {
                        this.board[r][c] = ColorStatus.EMPTY;
                    }
                } else {
                    this.board[r][c] = ColorStatus.EMPTY;
                }
            }
        }
    }

    /***************************************************************
     * Gets the current state of the board.
     *
     * @return The current state of the board.
     ***************************************************************/
    public ColorStatus[][] getBoard() {
        return this.board;
    }

    /***************************************************************
     * Constructor of the board class, it takes in an Object of the game abd
     * adds an observer of and initialize the board.
     *
     * @param player
     *            A instance of the player class.
     * @param ir
     *            The initial row of the piece
     * @param ic
     *            The initial column of the piece
     * @param fr
     *            The final row of the piece after move
     * @param fc
     *            The final column of the piece after move.
     * @return if the player has made a jump
     ***************************************************************/
    public boolean move(final Player player, final int ir, final int ic,
            final int fr, final int fc) {

        // Allows for double jump.
        boolean hasJumped = false;

        // temporary value to move.
        ColorStatus temp = this.board[ir][ic];

        // Debug console messages
        if (Checkers_GUI.debug) {
            System.out.println("------------------------------");
            System.out.println("------------------------------");
            System.out.println("------------------------------");
            System.out.println("VALUE BEFORE MOVING: " + this.board[ir][ic]);
        }

        this.board[ir][ic] = ColorStatus.EMPTY;

        // Debug console messages
        if (Checkers_GUI.debug) {
            System.out.println(
                    "INITIAL COORDINATES: " + ir + " ROW " + ic + " COLUMN.");
            System.out.println("VALUE AFTER MOVING: " + this.board[ir][ic]);
            System.out.println("VALUE AT THE DESTINATION BEFORE MOVING: "
                    + this.board[fr][fc]);
        }

        // If a normal checker reaches the opposite side of the board, it
        // becomes a
        // King.
        if (fr == 0 && player.playerColor() == temp
                && player.playerColor() == ColorStatus.WHITE) {
            temp = ColorStatus.WHITE_KING;
        } else if (fr == SIZE - 1 && player.playerColor() == temp
                && player.playerColor() == ColorStatus.BLACK) {
            temp = ColorStatus.BLACK_KING;
        }

        this.board[fr][fc] = temp;

        // Debug console messages
        if (Checkers_GUI.debug) {
            System.out
                    .println("FINAL COORDINATE: " + fr + " ROW " + fc + " COL");
            System.out.println("VALUE AT THE DESTINATION AFTER MOVING: "
                    + this.board[fr][fc]);
        }

        // Checking if the move is a jump.
        if (Math.abs(ir - fr) == 2 && Math.abs(ic - fc) == 2) {
            int row = (ir + fr) / 2;
            int col = (ic + fc) / 2;

            // Debug console messages
            if (Checkers_GUI.debug) {
                System.out.println(
                        "MIDDLE COORDINATE: " + row + " ROW " + col + " COL");
                System.out
                        .println("BEFORE JUMP VALUE: " + this.board[row][col]);
            }

            this.board[row][col] = ColorStatus.EMPTY;

            // Debug console messages
            if (Checkers_GUI.debug) {
                System.out.println(
                        "middle coordinate: " + row + " row " + col + " col");
                System.out
                        .println("VALUE MIDDLE JUMP: " + this.board[row][col]);
            }

            hasJumped = true;

            // Notify the game if one player loses all checkers.

            if (checkBoard(player.playerColor(), player.kingColor())) {
                if (Checkers_GUI.debug) {
                    System.out.println("Notifying that the game is over...");
                }
                setChanged();
                notifyObservers();
            }

        }

        return hasJumped;
    }

    /***************************************************************
     * Boolean method that determines if the piece can be selected.
     *
     * @param player
     *            A instance of the player object
     * @return True if the player can be selected or false if it cannot be
     *         selected
     ***************************************************************/
    public boolean[][] canSelect(final Player player) {
        ColorStatus playerColor = player.playerColor();
        ColorStatus checkerKing = player.kingColor();
        boolean[][] boardColor = new boolean[SIZE][SIZE];
        boolean canJump = false;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                boardColor[row][col] = false;
            }
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (canJump(playerColor, checkerKing, row, col)) {
                    boardColor[row][col] = true;
                    canJump = true;
                }
            }
        }

        if (canJump) {
            return boardColor;
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {

                boardColor[row][col] = canMove(playerColor, checkerKing, row,
                        col);

            }
        }

        return boardColor;
    }

    /***************************************************************
     * A boolean method that checks if the final position is a move.
     *
     * @param fr
     *            The row the piece moves from
     * @param fc
     *            The column the pece moves from. return true if the piece can
     *            move or false if it cannot move.
     * @return board
     ***************************************************************/
    public boolean canMove(final int fr, final int fc) {

        if (checkBounds(fr) || checkBounds(fc)) {
            return false;
        }
        return this.board[fr][fc] == ColorStatus.EMPTY;
    }

    /***************************************************************
     * A boolean method that checks if the piece can move in any direction.
     *
     * @param player
     *            A instance of the ColorStatus that gets the color of the
     *            player.
     * @param king
     *            A instance of ColorStatus that checks if the player is a king.
     * @param r
     *            Gets the row of the piece.
     * @param c
     *            Gets the column of the piece.
     * @return True if the piece can move or false if the piece can not.
     ***************************************************************/
    public boolean canMove(final ColorStatus player, final ColorStatus king,
            final int r, final int c) {
        int up, down, left, right;
        up = r - 1;
        down = r + 1;
        left = c - 1;
        right = c + 1;
        if (checkBounds(up)) {
            up = down;
        }
        if (checkBounds(down)) {
            down = up;
        }
        if (checkBounds(left)) {
            left = right;
        }
        if (checkBounds(right)) {
            right = left;
        }

        boolean result = false;
        ColorStatus checker = this.board[r][c];
        if (checker == player || checker == king) {
            if (checker == ColorStatus.WHITE
                    || checker == ColorStatus.BLACK_KING
                    || checker == ColorStatus.WHITE_KING) {
                result = result || this.board[up][right] == ColorStatus.EMPTY
                        || this.board[up][left] == ColorStatus.EMPTY;
            }
            if (checker == ColorStatus.BLACK
                    || checker == ColorStatus.BLACK_KING
                    || checker == ColorStatus.WHITE_KING) {
                result = result || this.board[down][right] == ColorStatus.EMPTY
                        || this.board[down][left] == ColorStatus.EMPTY;
            }
        }
        return result;
    }

    /***************************************************************
     * Sets all the tiles to false.
     *
     * @return the results of setting all the tiles to false.
     **************************************************************/
    public boolean[][] setAllFalse() {
        boolean[][] result = new boolean[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                result[i][j] = false;
            }
        }
        return result;
    }

    /***************************************************************
     * Returns true if the index is outside of the boundaries of the game.
     *
     * @param player
     *            A instance of the Player Object
     * @param row
     *            The row of the piece that is being hecked if a double jump is
     *            possible
     * @param col
     *            The column of the piece that is being hecked if a double jump
     *            is possible
     * @return true if there is a double jump or false if there is not
     **************************************************************/
    public boolean[][] showDoubleJumpOptions(final Player player, final int row,
            final int col) {
        ColorStatus allyChecker = player.playerColor();
        ColorStatus allyKing = player.kingColor();
        ColorStatus checker = this.board[row][col];
        boolean[][] result = setAllFalse();
        if (checker == ColorStatus.WHITE || checker == ColorStatus.BLACK_KING
                || checker == ColorStatus.WHITE_KING) {
            if (canJump(allyChecker, allyKing, row, col, row - 2, col + 2)) {
                result[row - 2][col + 2] = true;
                if (Checkers_GUI.debug) {
                    System.out.println("Can Jump at coordinates: " + (row - 2)
                            + " Row " + (col + 2) + " Column");
                }
            }
            if (canJump(allyChecker, allyKing, row, col, row - 2, col - 2)) {
                result[row - 2][col - 2] = true;
                if (Checkers_GUI.debug) {
                    System.out.println("Can Jump at coordinates: " + (row - 2)
                            + " Row " + (col - 2) + " Column");
                }
            }
        }
        if (checker == ColorStatus.BLACK || checker == ColorStatus.BLACK_KING
                || checker == ColorStatus.WHITE_KING) {
            if (canJump(allyChecker, allyKing, row, col, row + 2, col + 2)) {
                result[row + 2][col + 2] = true;
                if (Checkers_GUI.debug) {
                    System.out.println("Can Jump at coordinates: " + (row + 2)
                            + " Row " + (col + 2) + " Column");
                }
            }
            if (canJump(allyChecker, allyKing, row, col, row + 2, col - 2)) {
                result[row + 2][col - 2] = true;
                if (Checkers_GUI.debug) {
                    System.out.println("Can Jump at coordinates: " + (row + 2)
                            + " Row " + (col - 2) + " Column");
                }
            }
        }
        return result;
    }

    /***************************************************************
     * A boolean method that Shows the player whose turn it is the current
     * pieces that can be moved that turn.
     *
     * @param player
     *            Passes an instance of Player
     * @param row
     *            The row of the piece
     * @param col
     *            The Column of the piece
     * @return true or false depending on if the piece at specific row and
     *         column has a has a move.
     ***************************************************************/
    public boolean[][] showOptions(final Player player, final int row,
            final int col) {
        ColorStatus allyChecker = player.playerColor();
        ColorStatus allyKing = player.kingColor();
        ColorStatus checker = this.board[row][col];
        boolean[][] result = setAllFalse();
        boolean hasJumped = false;

        if (row == -1 && col == -1) {
            return result;
        }

        result[row][col] = true;

        if (canJump(allyChecker, allyKing, row, col)) {
            if (Checkers_GUI.debug) {
                System.out.println(
                        "The Checker color: " + checker + " can jump.");
            }
        }
        if (checker == ColorStatus.WHITE || checker == ColorStatus.BLACK_KING
                || checker == ColorStatus.WHITE_KING) {
            if (canJump(allyChecker, allyKing, row, col, row - 2, col + 2)) {
                result[row - 2][col + 2] = true;
                hasJumped = true;
                if (Checkers_GUI.debug) {
                    System.out.println("Can Jump at coordinates: " + (row - 2)
                            + " Row " + (col + 2) + " Column");
                }
            }
            if (canJump(allyChecker, allyKing, row, col, row - 2, col - 2)) {
                result[row - 2][col - 2] = true;
                hasJumped = true;
                if (Checkers_GUI.debug) {
                    System.out.println("Can Jump at coordinates: " + (row - 2)
                            + " Row " + (col - 2) + " Column");
                }
            }
        }
        if (checker == ColorStatus.BLACK || checker == ColorStatus.BLACK_KING
                || checker == ColorStatus.WHITE_KING) {
            if (canJump(allyChecker, allyKing, row, col, row + 2, col + 2)) {
                result[row + 2][col + 2] = true;
                hasJumped = true;
                if (Checkers_GUI.debug) {
                    System.out.println("Can Jump at coordinates: " + (row + 2)
                            + " Row " + (col + 2) + " Column");
                }
            }
            if (canJump(allyChecker, allyKing, row, col, row + 2, col - 2)) {
                result[row + 2][col - 2] = true;
                hasJumped = true;
                if (Checkers_GUI.debug) {
                    System.out.println("Can Jump at coordinates: " + (row + 2)
                            + " Row " + (col - 2) + " Column");
                }
            }
        }
        if (hasJumped) {
            return result;
        }
        if (checker == ColorStatus.WHITE || checker == ColorStatus.BLACK_KING
                || checker == ColorStatus.WHITE_KING) {
            if (canMove(row - 1, col + 1)) {
                result[row - 1][col + 1] = true;
            }
            if (canMove(row - 1, col - 1)) {
                result[row - 1][col - 1] = true;
            }
        }
        if (checker == ColorStatus.BLACK || checker == ColorStatus.BLACK_KING
                || checker == ColorStatus.WHITE_KING) {
            if (canMove(row + 1, col + 1)) {
                result[row + 1][col + 1] = true;
            }
            if (canMove(row + 1, col - 1)) {
                result[row + 1][col - 1] = true;
            }
        }

        return result;
    }

    /***************************************************************
     * A boolean method in which determines if the piece can jump another piece.
     *
     * @param player
     *            A instance of the Player object
     * @param king
     *            Checks if the piece is a king or not
     * @param ir
     *            The initial row of the jumping piece
     * @param ic
     *            The initial column of the jumping piece
     * @param fr
     *            The final row of the jumping piece
     * @param fc
     *            The final column of thw jumping piece.
     * @return true if a player can jump or false if a player can not jump.
     ***************************************************************/
    private boolean canJump(final ColorStatus player, final ColorStatus king,
            final int ir, final int ic, final int fr, final int fc) {

        if (checkBounds(fr) || checkBounds(fc)) {
            return false;
        }

        if (this.board[fr][fc] != ColorStatus.EMPTY) {
            return false;
        }

        int mr, mc;
        mr = (fr + ir) / 2;
        mc = (fc + ic) / 2;

        return this.board[mr][mc] != player && this.board[mr][mc] != king
                && this.board[mr][mc] != ColorStatus.EMPTY;
    }

    /***************************************************************
     * A boolean method in which determines if the piece can jump another piece.
     *
     * @param player
     *            A instance of the Player object
     * @param king
     *            Checks if the piece is a king or not
     * @param r
     *            The row of the piece
     * @param c
     *            The column of the piece
     * @return True if the piece can jump or false if the piece cannot.
     ***************************************************************/
    public boolean canJump(final ColorStatus player, final ColorStatus king,
            final int r, final int c) {
        int up, down, left, right;
        int upJ, downJ, leftJ, rightJ;

        up = r - 1;
        down = r + 1;
        left = c - 1;
        right = c + 1;

        upJ = r - 2;
        downJ = r + 2;
        leftJ = c - 2;
        rightJ = c + 2;
        if (checkBounds(upJ)) {
            up = down;
            upJ = downJ;
        }
        if (checkBounds(downJ)) {
            down = up;
            downJ = upJ;
        }

        if (checkBounds(leftJ)) {
            left = right;
            leftJ = rightJ;
        }
        if (checkBounds(rightJ)) {
            right = left;
            rightJ = leftJ;
        }

        boolean result = false;
        ColorStatus enemyChecker;
        ColorStatus enemyKing;
        ColorStatus checker = this.board[r][c];
        if (player == ColorStatus.BLACK) {
            enemyChecker = ColorStatus.WHITE;
            enemyKing = ColorStatus.WHITE_KING;
        } else {
            enemyChecker = ColorStatus.BLACK;
            enemyKing = ColorStatus.BLACK_KING;
        }

        if (checker == player || checker == king) {
            if (checker == ColorStatus.WHITE
                    || checker == ColorStatus.BLACK_KING
                    || checker == ColorStatus.WHITE_KING) {
                result = result
                        || (this.board[upJ][rightJ] == ColorStatus.EMPTY
                                && (this.board[up][right] == enemyChecker
                                        || this.board[up][right] == enemyKing))

                        || (this.board[upJ][leftJ] == ColorStatus.EMPTY
                                && (this.board[up][left] == enemyChecker
                                        || this.board[up][left] == enemyKing));
            }

            if (checker == ColorStatus.BLACK
                    || checker == ColorStatus.BLACK_KING
                    || checker == ColorStatus.WHITE_KING) {
                result = result
                        || (this.board[downJ][rightJ] == ColorStatus.EMPTY
                                && (this.board[down][right] == enemyChecker
                                        ||
                                        this.board[down][right] == enemyKing))

                        || (this.board[downJ][leftJ] == ColorStatus.EMPTY
                                && (this.board[down][left] == enemyChecker
                                        ||
                                        this.board[down][left] == enemyKing));
            }

        }
        return result;
    }

    /***************************************************************
     * Checks the board to see if team still has checkers.
     *
     * @param checker
     *            checks the color of the checker.
     * @param king
     *            Checks if the piece is a king or not
     * @return false if there is no piece and true if there is
     ***************************************************************/
    private boolean checkBoard(final ColorStatus checker,
            final ColorStatus king) {
        for (int row = 0; row < SIZE; row++) {
            for (ColorStatus c : this.board[row]) {
                if (c != checker && c != king && c != ColorStatus.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /***************************************************************
     * Returns true if the index is outside of the boundaries of the game.
     *
     * @param num1
     *            Checks if the number is out of the bounds of the board
     * @return true if it is out of bounds or false if it is not.
     **************************************************************/
    private boolean checkBounds(final int num1) {
        return (num1 < 0 || num1 >= SIZE);
    }
}
