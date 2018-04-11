package prototype;

public class Board {
	public static final int SIZE = 8;
	private ColorStatus[][] board = new ColorStatus[SIZE][SIZE];

	public Board() {
		Init();
	}

	private void Init() {
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				if (r % 2 == c % 2) {
					if (r < 3)
						board[r][c] = ColorStatus.BLACK;
					else if (r > 4)
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

	private boolean canMove(ColorStatus player, int ir, int ic, int fr, int fc) throws OutOfBoundsException {

		if (checkBounds(ir, ic) || checkBounds(fr, fc))
			throw new OutOfBoundsException();

		if (this.board[fr][fc] != ColorStatus.EMPTY)
			return false;
		else if (player == ColorStatus.WHITE) {
			if (this.board[ir][ic] == ColorStatus.WHITE && fr > ir)
				return false;
			return true;
		}
		else if (player == ColorStatus.BLACK) {
			if (this.board[ir][ic] == ColorStatus.BLACK && fr < ir)
				return false;
			return true;
		}
		else//probably not going to be reached.just in case
			return false;
	}

	private boolean canJump(ColorStatus player, int ir, int ic, int mr, int mc, int fr, int fc) throws OutOfBoundsException{
      if(checkBounds(ir, ic) || checkBounds(mr, mc) || checkBounds(fr, fc))
        throw new OutOfBoundsException();
    if(this.board[fr][fc] != ColorStatus.EMPTY)
      return false;
    if(player == ColorStatus.WHITE){
      if(this.board[ir][ic] == ColorStatus.WHITE && fr > ir)
        return false;
      if(this.board[mr][mc] != ColorStatus.BLACK && this.board[ir][ic] != ColorStatus.BLACK_KING)
        return false;
      return true;
    }
    else{
      if(this.board[ir][ic] == ColorStatus.BLACK && fr < ir)
        return false;
      if(this.board[mr][mc] == ColorStatus.WHITE && this.board[mr][mc] != ColorStatus.WHITE_KING )
        return false;
      return true;
    }
  }

	private boolean checkBounds(int num1, int num2) {
		return (num1 < 0 && num1 >= SIZE) || (num2 < 0 && num2 >= SIZE);
	}
}
