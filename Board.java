package prototype;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/***************************************************************
 * The Board class is used to create the board and hold the 
 *  logic that is used to play checkers.
 * @author Gionata Bonazzi
 * @author Cole Sellers
 * @author Brendan Cronan
 * @author Rosa Fleming
 * @version stable build 15 April 2018
 ***************************************************************/
public class Board extends Observable {
	//The final size of the board is 8x8
	public static final int SIZE = 8;
	//Creates a instance of the board set to the size of the board
	private ColorStatus[][] board = new ColorStatus[SIZE][SIZE];

/***************************************************************
 * Constructor of the board class, it takes in an Object of the
 * game abd adds an observer of and initialize the board.
 * @param game
 * 					A instance of the game class.
 ***************************************************************/
	public Board(Game game) {
		this.addObserver(game);
		Init();
	}

	/***************************************************************
	* initializes the board to 8x8 setting the rows and columns. 
	* it then sets the colorStatus of each specific tile to either
	* White, Black, or empty.
	***************************************************************/
	private void Init() {
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				if (r % 2 != c % 2) {
					if (r < 3)
						this.board[r][c] = ColorStatus.BLACK;
					else if (r > 4)
						this.board[r][c] = ColorStatus.WHITE;
					else
						this.board[r][c] = ColorStatus.EMPTY;
				} else
					this.board[r][c] = ColorStatus.EMPTY;
			}
		}
	}

	/***************************************************************
	* Gets the current state of the board.
	* @return The current state of the board.
 	***************************************************************/
	public ColorStatus[][] getBoard() {
		return this.board;
	}

	/***************************************************************
  * Constructor of the board class, it takes in an Object of the
  * game abd adds an observer of and initialize the board.
  * @param player
	* 				A instance of the player class.
	* @param ir
	*					The initial row of the piece
	* @param ic
	*					The initial column of the piece
	* @param fr
	*					The final row of the piece after move
	* @param fc
	*					The final column of the piece after move.
	* @return if the player has made a jump 
  ***************************************************************/
	public boolean move(Player player, int ir, int ic, int fr, int fc) {
		boolean hasJumped = false;
		ColorStatus temp = this.board[ir][ic];
		System.out.println("BEFORE VALUE: " + this.board[ir][ic]);
		this.board[ir][ic] = ColorStatus.EMPTY;
		System.out.println("Initial coordinate: " + ir + " row " + ic + " col");
		System.out.println("VALUE: " + this.board[ir][ic]);
		System.out.println("VALUE BEFORE MOVE: " + this.board[fr][fc]);
		if (fr == 0 && player.playerColor() == ColorStatus.WHITE) {
			temp = ColorStatus.WHITE_KING;
		} else if (fr == SIZE && player.playerColor() == ColorStatus.BLACK) {
			temp = ColorStatus.BLACK_KING;
		}
		this.board[fr][fc] = temp;
		System.out.println("Final coordinate: " + fr + " row " + fc + " col");
		System.out.println("VALUE AFTER MOVE: " + this.board[fr][fc]);
		if (Math.abs(ir - fr) == 2 && Math.abs(ic - fc) == 2) {
			int row = (ir + fr) / 2;
			int col = (ic + fc) / 2;
			System.out.println("IF");
			System.out.println("middle coordinate: " + row + " row " + (ic + fc) / 2 + " col");
			System.out.println("BEFORE JUMP VALUE: " + this.board[row][col]);

			this.board[row][col] = ColorStatus.EMPTY;
			System.out.println("middle coordinate: " + row + " row " + col + " col");
			System.out.println("VALUE MIDDLE JUMP: " + this.board[row][col]);
			hasJumped = true;
			/*
			 * if(checkBoard(player.playerColor(), player.kingColor())) { setChanged();
			 * notifyObservers(); }
			 */
		}
		System.out.println("-----------------------------------------");
		System.out.println("-----------------------------------------");
		System.out.println("-----------------------------------------");
		return hasJumped;
	}


	/***************************************************************
	* 
	* @return 
	*			The current state of the board is returned.
 	***************************************************************/
	public ColorStatus getColor(int row, int col) {
		if (checkBounds(row) || checkBounds(col))
			return ColorStatus.EMPTY;

		return this.board[row][col];
	}

	public boolean[][] canSelect(Player player) {
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

				boardColor[row][col] = canMove(playerColor, checkerKing, row, col);

			}
		}

		return boardColor;
	}

	public boolean canMove(int fr, int fc) {

		if (checkBounds(fr) || checkBounds(fc)) {
			return false;
		}
		return this.board[fr][fc] == ColorStatus.EMPTY;
	}

	public boolean canMove(ColorStatus player, ColorStatus king, int r, int c) {
		int up, down, left, right;
		up = r - 1;
		down = r + 1;
		left = c - 1;
		right = c + 1;
		if (checkBounds(up))
			up = down;
		if (checkBounds(down))
			down = up;
		if (checkBounds(left))
			left = right;
		if (checkBounds(right))
			right = left;

		boolean result = false;
		ColorStatus checker = this.board[r][c];
		if (checker == player || checker == king) {
			if (checker == ColorStatus.WHITE || checker == ColorStatus.BLACK_KING || checker == ColorStatus.WHITE_KING)
				result = result || this.board[up][right] == ColorStatus.EMPTY
						|| this.board[up][left] == ColorStatus.EMPTY;
			if (checker == ColorStatus.BLACK || checker == ColorStatus.BLACK_KING || checker == ColorStatus.WHITE_KING) {
				result = result || this.board[down][right] == ColorStatus.EMPTY
						|| this.board[down][left] == ColorStatus.EMPTY;
			}
		}
		return result;
	}

	public boolean[][] showOptions(Player player, int row, int col) {
		ColorStatus allyChecker = player.playerColor();
		ColorStatus allyKing = player.kingColor();
		ColorStatus checker = this.board[row][col];
		boolean[][] result = new boolean[SIZE][SIZE];

		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				result[r][c] = false;
			}
		}
		if (row == -1 && col == -1) {
			return result;
		}
		result[row][col] = true;

		if (canJump(allyChecker, allyKing, row, col)) {
			System.out.println("The Checker color: " + checker + " can jump."); //FIXME: DEBUG

			if (checker == ColorStatus.WHITE || checker == ColorStatus.BLACK_KING
					|| checker == ColorStatus.WHITE_KING) {
				if (canJump(allyChecker, allyKing, row, col, row - 2, col + 2)) {
					result[row - 2][col + 2] = true;
				}
				if (canJump(allyChecker, allyKing, row, col, row - 2, col - 2)) {
					result[row - 2][col - 2] = true;
				}
			}
			if (checker == ColorStatus.BLACK || checker == ColorStatus.BLACK_KING
					|| checker == ColorStatus.WHITE_KING) {
				if (canJump(allyChecker, allyKing, row, col, row + 2, col + 2)) {
					result[row + 2][col + 2] = true;
				}
				if (canJump(allyChecker, allyKing, row, col, row + 2, col - 2)) {
					result[row + 2][col - 2] = true;
				}
			}
		} 
		else {

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

		}

		return result;
	}

	private boolean canJump(ColorStatus player, ColorStatus king, int ir, int ic, int fr, int fc) {

		if (checkBounds(fr) || checkBounds(fc)) {
			return false;
		}

		if (this.board[fr][fc] != ColorStatus.EMPTY)
			return false;

		int mr, mc;
		mr = (fr + ir) / 2;
		mc = (fc + ic) / 2;

		return this.board[mr][mc] != player && this.board[mr][mc] != king && this.board[mr][mc] != ColorStatus.EMPTY;
	}

	public boolean canJump(ColorStatus player, ColorStatus king, int r, int c) {
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
			if (checker == ColorStatus.WHITE || checker == ColorStatus.BLACK_KING || checker == ColorStatus.WHITE_KING) {
				result = result
						|| (this.board[upJ][rightJ] == ColorStatus.EMPTY && (this.board[up][right] == enemyChecker
								|| this.board[up][right] == enemyKing))

						|| (this.board[upJ][leftJ] == ColorStatus.EMPTY && (this.board[up][left] == enemyChecker
								|| this.board[up][left] == enemyKing));
			}

			if (checker == ColorStatus.BLACK || checker == ColorStatus.BLACK_KING ||  checker == ColorStatus.WHITE_KING) {
				result = result
						|| (this.board[downJ][rightJ] == ColorStatus.EMPTY && (this.board[down][right] == enemyChecker
								|| this.board[down][right] == enemyKing))

						|| (this.board[downJ][leftJ] == ColorStatus.EMPTY && (this.board[down][left] == enemyChecker
								|| this.board[down][left] == enemyKing));
			}

		}
		return result;
	}

	private boolean checkBoard(ColorStatus checker, ColorStatus king) {
		for (int r = 0; r < SIZE; r++) {
			for (ColorStatus c : this.board[r]) {
				if (c != checker || c != king || c != ColorStatus.EMPTY) {
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * Returns true if the index is outside of the boundaries of the game.
	 */
	private boolean checkBounds(int num1) {
		return (num1 < 0 || num1 >= SIZE);
	}

}