package prototype;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Board extends Observable{
	public static final int SIZE = 8;
	private ColorStatus[][] board = new ColorStatus[SIZE][SIZE];
	
	public Board(Game game) {
		this.addObserver(game);
		Init();
	}

	private void Init() {
		for (int c = 0; c < SIZE; c++) {
			for (int r = 0; r < SIZE; r++) {
				if (r % 2 != c % 2) {
					if (c < 3)
						board[r][c] = ColorStatus.BLACK;
					else if (c > 4)
						board[r][c] = ColorStatus.WHITE;
					else
						board[r][c] = ColorStatus.EMPTY;
				} else
					board[r][c] = ColorStatus.EMPTY;
			}
		}
	}

	public ColorStatus[][] getBoard() {
		return this.board;
	}

	public void move(Player player, int ir, int ic, int fr, int fc, boolean isJump) {
		ColorStatus temp = this.board[ir][ic];
		this.board[ir][ic] = ColorStatus.EMPTY;
		this.board[fr][fc] = temp;
		if (isJump) {
			this.board[(ir + fr) / 2][(ir + ic) / 2] = ColorStatus.EMPTY;
			if(checkBoard(player.playerColor(), player.kingColor())) {
				setChanged();
				notifyObservers();
			}
		}
	}

	public int getValue(int posx, int posy) throws OutOfBoundsException {

		if (checkBounds(posx) || checkBounds(posy))
			throw new OutOfBoundsException();

		ColorStatus status = getColor(posx, posy);
		switch (status) {
		case EMPTY:
			return 0;
		case WHITE:
			return 1;
		case WHITE_KING:
			return 2;
		case BLACK:
			return 3;
		case BLACK_KING:
			return 4;
		default:
			return -1;
		}
	}

	public ColorStatus getColor(int posx, int posy) throws OutOfBoundsException {
		if (checkBounds(posx) || checkBounds(posy))
			throw new OutOfBoundsException();

		return this.board[posx][posy];
	}

	// FIXME: make a getLegalJumpsFrom

	public boolean[][] canSelect(Player player) {
		ColorStatus playerColor = player.playerColor();
		ColorStatus checkerKing = player.kingColor();
		boolean[][] boardColor = new boolean[SIZE][SIZE];
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				boardColor[row][col] = false;
			}
		}

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {

				boardColor[row][col] = canMove(playerColor, checkerKing, row, col);

			}
		}

		// for (int i = 0; i < SIZE; i++) {
		// for (int j = 0; j < SIZE; j++) {
		//
		// if (playerColor == ColorStatus.WHITE || playerColor == checkerKing) {
		// boardColor[j][i] = this.canMove(playerColor, i, j, i + 1, j - 1)
		// || this.canMove(playerColor, i, j, i - 1, j - 1);
		// }
		// if (playerColor == ColorStatus.BLACK || playerColor == checkerKing) {
		// boardColor[j][i] = this.canMove(playerColor, i, j, i - 1, j + 1)
		// || this.canMove(playerColor, i, j, i + 1, j - 1);
		// }
		//
		// }
		// }
		return boardColor;
	}

	public boolean canMove(int fr, int fc) {

		if (checkBounds(fr) || checkBounds(fc)) {
			return false;
		}
		return board[fr][fc] == ColorStatus.EMPTY;
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

		if (this.board[r][c] == player || this.board[r][c] == king) {
			if (player == ColorStatus.WHITE || king == ColorStatus.BLACK_KING)
				result = result || this.board[up][right] == ColorStatus.EMPTY
						|| this.board[up][left] == ColorStatus.EMPTY;
			if (player == ColorStatus.BLACK || king == ColorStatus.WHITE_KING) {
				result = result || this.board[down][right] == ColorStatus.EMPTY
						|| this.board[down][left] == ColorStatus.EMPTY;
			}
		}
		return result;
	}

	public boolean[][] showOptions(Player player, int row, int col) {
		ColorStatus checker = player.playerColor();
		ColorStatus king = player.kingColor();
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

		if (canJump(checker, king, row, col)) {

			if (checker == ColorStatus.WHITE || king == ColorStatus.BLACK_KING) {
				if (canJump(checker, king, row, col, row - 2, col + 2)) {
					result[row - 2][col + 2] = true;
				}
				if (canJump(checker, king, row, col, row - 2, col - 2)) {
					result[row - 2][col - 2] = true;
				}
			}
			if (checker == ColorStatus.BLACK || king == ColorStatus.WHITE_KING) {
				if (canJump(checker, king, row, col, row + 2, col + 2)) {
					result[row + 2][col + 2] = true;
				}
				if (canJump(checker, king, row, col, row + 2, col - 2)) {
					result[row + 2][col - 2] = true;
				}
			}
		} else {

			if (checker == ColorStatus.WHITE || king == ColorStatus.BLACK_KING) {
				if (canMove(row - 1, col + 1)) {
					result[row - 1][col + 1] = true;
				}
				if (canMove(row - 1, col - 1)) {
					result[row - 1][col - 1] = true;
				}
			}
			if (checker == ColorStatus.BLACK || king == ColorStatus.WHITE_KING) {
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

	private boolean canJump(ColorStatus player, ColorStatus king, int r, int c) {
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
		if (player == ColorStatus.BLACK) {
			enemyChecker = ColorStatus.WHITE;
			enemyKing = ColorStatus.WHITE_KING;
		} else {
			enemyChecker = ColorStatus.BLACK;
			enemyKing = ColorStatus.BLACK_KING;
		}

		if (this.board[r][c] == player || this.board[r][c] == king) {
			if (player == ColorStatus.WHITE || king == ColorStatus.BLACK_KING) {
				result = result
						|| (this.board[upJ][rightJ] == ColorStatus.EMPTY && this.board[up][right] == enemyChecker
								|| this.board[up][right] == enemyKing)

						|| (this.board[upJ][leftJ] == ColorStatus.EMPTY && this.board[up][left] == enemyChecker
								|| this.board[up][left] == enemyKing);
			}

			if (player == ColorStatus.BLACK || king == ColorStatus.WHITE_KING) {
				result = result
						|| (this.board[upJ][rightJ] == ColorStatus.EMPTY && this.board[up][right] == enemyChecker
								|| this.board[up][right] == enemyKing)

						|| (this.board[upJ][leftJ] == ColorStatus.EMPTY && this.board[up][left] == enemyChecker
								|| this.board[up][left] == enemyKing);
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