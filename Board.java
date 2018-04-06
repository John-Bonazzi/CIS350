import ColorStatus;
public class Board{
  private final const SIZE = 8;
  private ColorStatus[][] board = new ColorStatus[SIZE][SIZE];

  public Board(){
    Init();
  }

  private void Init(){
    for(int r = 0; r < SIZE; r++){
      for(int c = 0; c < SIZE; c++){
        if(r % 2 == col % 2){
          if(row < 3)
            board[r][c] = ColorStatus.BLACK;
          else if(row > 4)
            board[r][c] = ColorStatus.WHITE;
          else
            board[r][c] = ColorStatus.EMPTY;
        }
        else
          board[r][c] = ColorStatus.EMPTY;
      }
    }
  }

  public ColorStatus[][] getBoard(){
    return this.board;
  }

  public int getValue(int posx, int posy) throws OutOfBoundsException{
    try{
      if(checkBounds(posx, posy))
        throw new OutOfBoundsException();
    }
    ColorStatus status = getColor(posx, posy);
    switch(status){
      case ColorStatus.EMPTY: return 0;
      case ColorStatus.WHITE: return 1;
      case ColorStatus.WHITE_KING: return 2;
      case ColorStatus.BLACK: return 3;
      case ColorStatus.BLACK_KING: return 4;
      default: return -1;
    }
  }

  public ColorStatus getColor(int posx, int posy) throws OutOfBoundsException{
    try{
      if(checkBounds(posx, posy))
        throw new OutOfBoundsException();
    }
    return this.board[posx][posy];
  }

  //FIXME: make a getLegalJumpsFrom

  private boolean canMove(ColorStatus player, int ir, int ic, int fr, int fc) throws OutOfBoundsException{
    try{
      if(checkBounds(ir, ic) || checkBounds(fr, fc))
        throw new OutOfBoundsException();
    }
    if(this.board[fr][fc] != ColorStatus.EMPTY)
      return false;
    if(player == ColorStatus.WHITE){
      if(this.board[ir][ic] == ColorStatus.WHITE && fr > ir)
        return false;
      return true;
    }
    if(player == ColorStatus.BLACK){
      if(this.board[ir][ic] == ColorStatus.BLACK && fr < ir)
        return false;
      return true;
    }
  }

  private boolean canJump(ColorStatus player, int ir, int ic, int mr, int mc, int fr, int fc) throws OutOfBoundsException{
    try{
      if(checkBounds(ir, ic) || checkBounds(mr, mc) || checkBounds(fr, fc))
        throw new OutOfBoundsException();
    }
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

  private boolean checkBounds(int num1, int num2){
    return (num1 < 0 && num1 >= SIZE) || (num2 < 0 && num2 >= SIZE);
  }
}
