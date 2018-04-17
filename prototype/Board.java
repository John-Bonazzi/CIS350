package prototype;

import java.util.Observable;

public class Board extends Observable {
	public static final int SIZE = 8;
	private ColorStatus[][] board = new ColorStatus[SIZE][SIZE];

	public Board(Game game) {
		this.addObserver(game);
		Init();
	}

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

	public ColorStatus[][] getBoard() {
		return this.board;
	}

	public boolean move(Player player, int ir, int ic, int fr, int fc) {

		// Allows for double jump.
		boolean hasJumped = false;

		// temporary value to move.
		ColorStatus temp = this.board[ir][ic];

		// Debug console messages
		if (Checkers_GUI.debug) {
			System.out.println("-----------------------------------------");
			System.out.println("-----------------------------------------");
			System.out.println("-----------------------------------------");
			System.out.println("VALUE BEFORE MOVING: " + this.board[ir][ic]);
		}

		this.board[ir][ic] = ColorStatus.EMPTY;

		// Debug console messages
		if (Checkers_GUI.debug) {
			System.out.println("INITIAL COORDINATES: " + ir + " ROW " + ic + " COLUMN.");
			System.out.println("VALUE AFTER MOVING: " + this.board[ir][ic]);
			System.out.println("VALUE AT THE DESTINATION BEFORE MOVING: " + this.board[fr][fc]);
		}

		// If a normal checker reaches the opposite side of the board, it becomes a
		// King.
		if (fr == 0 && player.playerColor() == temp && player.playerColor() == ColorStatus.WHITE) {
			temp = ColorStatus.WHITE_KING;
		} else if (fr == SIZE - 1 && player.playerColor() == temp && player.playerColor() == ColorStatus.BLACK) {
			temp = ColorStatus.BLACK_KING;
		}

		this.board[fr][fc] = temp;

		// Debug console messages
		if (Checkers_GUI.debug) {
			System.out.println("FINAL COORDINATE: " + fr + " ROW " + fc + " COL");
			System.out.println("VALUE AT THE DESTINATION AFTER MOVING: " + this.board[fr][fc]);
		}

		// Checking if the move is a jump.
		if (Math.abs(ir - fr) == 2 && Math.abs(ic - fc) == 2) {
			int row = (ir + fr) / 2;
			int col = (ic + fc) / 2;

			// Debug console messages
			if (Checkers_GUI.debug) {
				System.out.println("MIDDLE COORDINATE: " + row + " ROW " + col + " COL");
				System.out.println("BEFORE JUMP VALUE: " + this.board[row][col]);
			}

			this.board[row][col] = ColorStatus.EMPTY;

			// Debug console messages
			if (Checkers_GUI.debug) {
				System.out.println("middle coordinate: " + row + " row " + col + " col");
				System.out.println("VALUE MIDDLE JUMP: " + this.board[row][col]);
			}

			hasJumped = true;

			// Notify the game if one player loses all checkers.

			if (checkBoard(player.playerColor(), player.kingColor())) {
				if(Checkers_GUI.debug) {
					System.out.println("Notifying that the game is over...");
				}
				setChanged();
				notifyObservers();
			}

		}

		
		return hasJumped;
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
			if (checker == ColorStatus.BLACK || checker == ColorStatus.BLACK_KING
					|| checker == ColorStatus.WHITE_KING) {
				result = result || this.board[down][right] == ColorStatus.EMPTY
						|| this.board[down][left] == ColorStatus.EMPTY;
			}
		}
		return result;
	}

	public boolean[][] setAllFalse() {
		boolean[][] result = new boolean[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				result[i][j] = false;
			}
		}
		return result;
	}

	public boolean[][] showDoubleJumpOptions(Player player, int row, int col){
		ColorStatus allyChecker = player.playerColor();
		ColorStatus allyKing = player.kingColor();
		ColorStatus checker = this.board[row][col];
		boolean[][] result = setAllFalse();
		if (checker == ColorStatus.WHITE || checker == ColorStatus.BLACK_KING || checker == ColorStatus.WHITE_KING) {
			if (canJump(allyChecker, allyKing, row, col, row - 2, col + 2)) {
				result[row - 2][col + 2] = true;
				if (Checkers_GUI.debug) {
					System.out.println("Can Jump at coordinates: " + (row - 2) + " Row " + (col + 2) + " Column");
				}
			}
			if (canJump(allyChecker, allyKing, row, col, row - 2, col - 2)) {
				result[row - 2][col - 2] = true;
				if (Checkers_GUI.debug) {
					System.out.println("Can Jump at coordinates: " + (row - 2) + " Row " + (col - 2) + " Column");
				}
			}
		}
		if (checker == ColorStatus.BLACK || checker == ColorStatus.BLACK_KING || checker == ColorStatus.WHITE_KING) {
			if (canJump(allyChecker, allyKing, row, col, row + 2, col + 2)) {
				result[row + 2][col + 2] = true;
				if (Checkers_GUI.debug) {
					System.out.println("Can Jump at coordinates: " + (row + 2) + " Row " + (col + 2) + " Column");
				}
			}
			if (canJump(allyChecker, allyKing, row, col, row + 2, col - 2)) {
				result[row + 2][col - 2] = true;
				if (Checkers_GUI.debug) {
					System.out.println("Can Jump at coordinates: " + (row + 2) + " Row " + (col - 2) + " Column");
				}
			}
		}
		return result;
	}
	
	public boolean[][] showOptions(Player player, int row, int col) {
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
				System.out.println("The Checker color: " + checker + " can jump.");
			}
		}
		if (checker == ColorStatus.WHITE || checker == ColorStatus.BLACK_KING || checker == ColorStatus.WHITE_KING) {
			if (canJump(allyChecker, allyKing, row, col, row - 2, col + 2)) {
				result[row - 2][col + 2] = true;
				hasJumped = true;
				if (Checkers_GUI.debug) {
					System.out.println("Can Jump at coordinates: " + (row - 2) + " Row " + (col + 2) + " Column");
				}
			}
			if (canJump(allyChecker, allyKing, row, col, row - 2, col - 2)) {
				result[row - 2][col - 2] = true;
				hasJumped = true;
				if (Checkers_GUI.debug) {
					System.out.println("Can Jump at coordinates: " + (row - 2) + " Row " + (col - 2) + " Column");
				}
			}
		}
		if (checker == ColorStatus.BLACK || checker == ColorStatus.BLACK_KING || checker == ColorStatus.WHITE_KING) {
			if (canJump(allyChecker, allyKing, row, col, row + 2, col + 2)) {
				result[row + 2][col + 2] = true;
				hasJumped = true;
				if (Checkers_GUI.debug) {
					System.out.println("Can Jump at coordinates: " + (row + 2) + " Row " + (col + 2) + " Column");
				}
			}
			if (canJump(allyChecker, allyKing, row, col, row + 2, col - 2)) {
				result[row + 2][col - 2] = true;
				hasJumped = true;
				if (Checkers_GUI.debug) {
					System.out.println("Can Jump at coordinates: " + (row + 2) + " Row " + (col - 2) + " Column");
				}
			}
		}
		if (hasJumped) {
			return result;
		}
		if (checker == ColorStatus.WHITE || checker == ColorStatus.BLACK_KING || checker == ColorStatus.WHITE_KING) {
			if (canMove(row - 1, col + 1)) {
				result[row - 1][col + 1] = true;
			}
			if (canMove(row - 1, col - 1)) {
				result[row - 1][col - 1] = true;
			}
		}
		if (checker == ColorStatus.BLACK || checker == ColorStatus.BLACK_KING || checker == ColorStatus.WHITE_KING) {
			if (canMove(row + 1, col + 1)) {
				result[row + 1][col + 1] = true;
			}
			if (canMove(row + 1, col - 1)) {
				result[row + 1][col - 1] = true;
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
			if (checker == ColorStatus.WHITE || checker == ColorStatus.BLACK_KING
					|| checker == ColorStatus.WHITE_KING) {
				result = result
						|| (this.board[upJ][rightJ] == ColorStatus.EMPTY
								&& (this.board[up][right] == enemyChecker || this.board[up][right] == enemyKing))

						|| (this.board[upJ][leftJ] == ColorStatus.EMPTY
								&& (this.board[up][left] == enemyChecker || this.board[up][left] == enemyKing));
			}

			if (checker == ColorStatus.BLACK || checker == ColorStatus.BLACK_KING
					|| checker == ColorStatus.WHITE_KING) {
				result = result
						|| (this.board[downJ][rightJ] == ColorStatus.EMPTY
								&& (this.board[down][right] == enemyChecker || this.board[down][right] == enemyKing))

						|| (this.board[downJ][leftJ] == ColorStatus.EMPTY
								&& (this.board[down][left] == enemyChecker || this.board[down][left] == enemyKing));
			}

		}
		return result;
	}

	private boolean checkBoard(ColorStatus checker, ColorStatus king) {
		for (int row = 0; row < SIZE; row++) {
			for (ColorStatus c : this.board[row]) {
				if (c != checker && c != king && c != ColorStatus.EMPTY) {
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