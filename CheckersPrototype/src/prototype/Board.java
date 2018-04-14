package prototype;

public class Board {
	public static final int SIZE = 8;
	private ColorStatus[][] board = new ColorStatus[SIZE][SIZE];

	public Board() {
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
		ColorStatus checkerKing = ColorStatus.EMPTY;
		if (playerColor == ColorStatus.WHITE) {
			checkerKing = ColorStatus.WHITE_KING;
		} else if (playerColor == ColorStatus.BLACK) {
			checkerKing = ColorStatus.BLACK_KING;
		}
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

	private boolean isJump(int ir, int ic, int fr, int fc) {
		return (ir - fr == 2 || ic - fc == 2);
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
		if (this.board[r][c] == player || this.board[r][c] == king) {
			if (player == ColorStatus.WHITE) {
				result = result
						|| (this.board[upJ][rightJ] == ColorStatus.EMPTY && this.board[up][right] == ColorStatus.BLACK
								|| this.board[up][right] == ColorStatus.BLACK_KING)

						|| (this.board[upJ][leftJ] == ColorStatus.EMPTY && this.board[up][left] == ColorStatus.BLACK
								|| this.board[up][left] == ColorStatus.BLACK_KING);
			}

			if (player == ColorStatus.BLACK) {
				result = result
						|| (this.board[upJ][rightJ] == ColorStatus.EMPTY && this.board[up][right] == ColorStatus.WHITE
								|| this.board[up][right] == ColorStatus.WHITE_KING)

						|| (this.board[upJ][leftJ] == ColorStatus.EMPTY && this.board[up][left] == ColorStatus.WHITE
								|| this.board[up][left] == ColorStatus.WHITE_KING);
			}

			if (player == ColorStatus.WHITE_KING) {
				result = result
						|| (this.board[upJ][rightJ] == ColorStatus.EMPTY && this.board[up][right] == ColorStatus.BLACK
								|| this.board[up][right] == ColorStatus.BLACK_KING)

						|| (this.board[upJ][leftJ] == ColorStatus.EMPTY && this.board[up][left] == ColorStatus.BLACK
								|| this.board[up][left] == ColorStatus.BLACK_KING)

						|| (this.board[downJ][rightJ] == ColorStatus.EMPTY
								&& this.board[down][right] == ColorStatus.BLACK
								|| this.board[down][right] == ColorStatus.BLACK_KING)

						|| (this.board[downJ][leftJ] == ColorStatus.EMPTY && this.board[down][left] == ColorStatus.BLACK
								|| this.board[down][left] == ColorStatus.BLACK_KING);
			}

			if (player == ColorStatus.BLACK_KING) {
				result = result
						|| (this.board[upJ][rightJ] == ColorStatus.EMPTY && this.board[up][right] == ColorStatus.WHITE
								|| this.board[up][right] == ColorStatus.WHITE_KING)

						|| (this.board[upJ][leftJ] == ColorStatus.EMPTY && this.board[up][left] == ColorStatus.WHITE
								|| this.board[up][left] == ColorStatus.WHITE_KING)

						|| (this.board[downJ][rightJ] == ColorStatus.EMPTY
								&& this.board[down][right] == ColorStatus.WHITE
								|| this.board[down][right] == ColorStatus.WHITE_KING)

						|| (this.board[downJ][leftJ] == ColorStatus.EMPTY && this.board[down][left] == ColorStatus.WHITE
								|| this.board[down][left] == ColorStatus.WHITE_KING);
			}

			if (player == ColorStatus.BLACK || king == ColorStatus.WHITE_KING) {
				result = result || this.board[downJ][rightJ] == ColorStatus.EMPTY
						|| this.board[downJ][leftJ] == ColorStatus.EMPTY;
			}

		}
		return result;
	}

	/*
	 * Returns true if the index is outside of the boundaries of the game.
	 */
	private boolean checkBounds(int num1) {
		return (num1 < 0 || num1 >= SIZE);
	}

}