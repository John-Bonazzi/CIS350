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

		if (checkBounds(posx, posy))
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
		if (checkBounds(posx, posy))
			throw new OutOfBoundsException();

		return this.board[posx][posy];
	}

	// FIXME: make a getLegalJumpsFrom

	public boolean[][] checkerCanBeSelected(Player player) {
		ColorStatus playerColor = player.playerColor();
		ColorStatus checkerKing = ColorStatus.EMPTY;
		if (playerColor == ColorStatus.WHITE) {
			checkerKing = ColorStatus.WHITE_KING;
		} else if (playerColor == ColorStatus.BLACK) {
			checkerKing = ColorStatus.BLACK_KING;
		}
		boolean[][] boardColor = new boolean[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				boardColor[i][j] = false;
			}
		}
		
		
		
		boolean thereIsJump = false; // If there is at least one checker that can jump, then no checker can move.
		
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				
			}
		}
		
		
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {

				if (this.board[j][i] != ColorStatus.EMPTY) {
					/*
					 * White: i+1, j-1 i-1, j-1
					 *
					 * Black: i-1, j+1 i+1, j+1
					 */
					if (playerColor == ColorStatus.WHITE || playerColor == checkerKing) {
						boardColor[i][j] = this.canJump(playerColor, i, j, i + 1, j - 1, i + 2, j - 2)
								|| this.canJump(playerColor, i, j, i - 1, j - 1, i - 2, j - 2);
					}
					if (playerColor == ColorStatus.BLACK || playerColor == checkerKing) {
						boardColor[i][j] = this.canJump(playerColor, i, j, i - 1, j + 1, i - 2, j + 2)
								|| this.canJump(playerColor, i, j, i + 1, j - 1, i + 2, j - 2);
					}
					if (boardColor[i][j])
						thereIsJump = true;
				}
			}
		}
		if (thereIsJump)
			return boardColor;

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {

				if (playerColor == ColorStatus.WHITE || playerColor == checkerKing) {
					boardColor[j][i] = this.canMove(playerColor, i, j, i + 1, j - 1)
							|| this.canMove(playerColor, i, j, i - 1, j - 1);
				}
				if (playerColor == ColorStatus.BLACK || playerColor == checkerKing) {
					boardColor[j][i] = this.canMove(playerColor, i, j, i - 1, j + 1)
							|| this.canMove(playerColor, i, j, i + 1, j - 1);
				}

			}
		}
		return boardColor;
	}

	public boolean canMove(ColorStatus player, ColorStatus king, int r, int c) {

//		int temp = ir;
//		ir = ic;
//		ic = temp;
//		temp = fr;
//		fr = fc;
//		fc = temp;
		
		
		if (checkBounds(r, r+1) || checkBounds(c, c+1))
			// throw new OutOfBoundsException();
			return false;
		boolean result = false;
		
		if (this.board[r][c] == player || this.board[r][c] == king) {			
			if (player == ColorStatus.WHITE || king == ColorStatus.BLACK_KING)
				result = result || this.board[r-1][c+1] == ColorStatus.EMPTY || this.board[r-1][c-1] == ColorStatus.EMPTY;
			if(player == ColorStatus.BLACK || king == ColorStatus.WHITE_KING) {
				result = result || this.board[r+1][c+1] == ColorStatus.EMPTY || this.board[r+1][c-1] == ColorStatus.EMPTY;
			}
		}
		return result;
	}

	private boolean canJump(ColorStatus player, int ir, int ic, int mr, int mc, int fr, int fc)
			throws OutOfBoundsException {
		if (checkBounds(ir, ic) || checkBounds(mr, mc) || checkBounds(fr, fc))
			return false;
		// throw new OutOfBoundsException();

		if (this.board[fr][fc] != ColorStatus.EMPTY)
			return false;
		if (player == ColorStatus.WHITE) {
			if (this.board[ir][ic] == ColorStatus.WHITE && fr > ir)
				return false;
			if (this.board[mr][mc] != ColorStatus.BLACK && this.board[ir][ic] != ColorStatus.BLACK_KING)
				return false;
			return true;
		} else {
			if (this.board[ir][ic] == ColorStatus.BLACK && fr < ir)
				return false;
			if (this.board[mr][mc] == ColorStatus.WHITE && this.board[mr][mc] != ColorStatus.WHITE_KING)
				return false;
			return true;
		}
	}

	/*
	 * Returns true if the index is outside of the boundaries of the game.
	 */
	private boolean checkBounds(int num1, int num2) {
		return (num1 < 0 || num1 >= SIZE) || (num2 < 0 || num2 >= SIZE);
	}

}