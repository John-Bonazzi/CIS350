package engine;

public abstract class Checkers extends Applet {
  public void init(){
    /***************************************************************************
    This class will set the layout to null because i would like to
    do the layout on my own, sets the background color of the applet.
    creates a new object checkerCanvas. adds the ojebect just created.
    sets the beackground color of the buttons created in the canvas.
    as well as adds these buttons to board. it also sets the bounds
    of the board as well as the buttons and message.
    ***************************************************************************/
  }
}

class CheckersCanvas extends Canvas implements ActionListener, MouseListener(){
  //By clicking this the player resigns
  Button resignButton;
  //Starts a new game
  Button newgameButton;
  //displays message to player
  Label message;
  //this is where the data is held and it determines the list of legal moves
  CheckersData board;
  //checks id the current game is in progress.
  boolean gameINProgess;
  //which players turn is it
  int currentPlayer;
  //gives the selected pieces row and column
  int selectedRow, selectedCol;
  //an array of legal moves for the piece.
  CheckersMove[] legalmoves;


  Public CheckersCanvas(){
    /***************************************************************************
    This is the constructor for the checkers canvas class, this will create the
    buttons and labels listen for the mouse clicks and button clicks,it will
    also create the board and start the first game.
    ***************************************************************************/
  }

  Public void actionPerformed(ActionEvent e){
    /***************************************************************************
    This class will respond to the users clicks of the resign or the new
    game buttons.
    ***************************************************************************/
  }

  void doNewGame(){
    /***************************************************************************
    Begins a new game, sets up the pieces on the board. sets the current player
    to red, gets reds legal moves, sets selected row to -1 because red hasnt
    selected a piece yet. displays message telling red it is their move.
    sets gameInProgress to true, disables new game button, enables resign button
    and repaints the board.
    ***************************************************************************/
  }

  void doResign(){
    /***************************************************************************
    the current player whose turn it is loses and opponent win, checks if there
    is a current game in progress, if not it sends a message saying so, it
    then checks the current player and calls the game over class saying who
    wins.
    ***************************************************************************/
  }

  void gameOver(){
    /***************************************************************************
    sets the text of the message to the winner, enables the new game button
    and disables the resign button, also sets gameInProgress to false.
    ***************************************************************************/
  }

  void doClickSquare(){
    /***************************************************************************
    This method is called by the mousePressed() method when a player has clicked
    on a square. If the player has clicked on a square and it is legal it will
    return the row and column as the selected. this method also checks that
    there was in fact a piece selected and shows a error message if there is
    no piece selected. if the player clciked a square that that the piece
    selected can be leaglly moved, it makes the move then returns. also add a
    statement for when the piece has been slected but the player clicked
    on a sqaure that is not legally able to move to.
    ***************************************************************************/
  }

  void doMakeMove(CheckersMove move){
    /***************************************************************************
    if a move wa smade its possible there is another move i.e. double jump.
    this meth=od checks for legal moves at the square the player just jumped
    too if there is one the player must jump. the next step is that the players
    turn has ended so it changes to the other player and gets that players
    legal moves. if there are no legal moves for the player the game will end.
    sets the selected row to -1 to show that the player has yet to choose a
    piece to move. also added a method that automatically chooses a piece if
    all legal moves use the same piece. this method then re-paints the board.
    ***************************************************************************/
  }

  public void update(Graphics g){
    /***************************************************************************
    This method will completly redraw the canvas. all it does is call paint(g)
    ***************************************************************************/
  }

  public void paint(Graphics g){
    /***************************************************************************
    this will draw the checkerboard pattern, it will also draw the checkers.
    if the game is in progress it will also highlight the legal moves.
    ***************************************************************************/
  }

  public Dimension getPreferredSize(){
    /***************************************************************************
    returns the preferred size.
    ***************************************************************************/
  }

  public Dimension getMinimumSize(){
    /***************************************************************************
    returns the minimum size
    ***************************************************************************/
  }

  public void mousePressed(MouseEvent e){
    /***************************************************************************
    rsponds to the users click on the board, if there isnt a game in progress
     it will show an error message. If there is a game in progress it will
     get the selected row and column and call the doClickSquare() method.
    ***************************************************************************/
  }
}

abstract class CheckersMove{

  int fromRow, fromCol;
  int toRow, toCol;

  CheckersMove(int r1, int c1, int r2, int c2) {
    /***************************************************************************
    constructor that sets the values of instance variables
    ***************************************************************************/
  }

  boolean isJump(){
    /***************************************************************************
    tests whether a move is a jump. it is assumed the move is legal. in a jump
    the piece moves 2 rows.
    ***************************************************************************/
  }
}

abstract class CheckersData{

  public static final int EMPTY = 0;
  public static final int RED = 1
  public static final int RED_KING = 2;
  public static final int BLACK = 3;
  public static final int  BLACK_KING = 4;

  public CheckersData(){
    /***************************************************************************
    Constructor, Creates the board and sets it up for a new game.
    ***************************************************************************/
  }

  public void setUpGame(){

  }

  public int pieceAt(int row, int col){

  }

  public void setPieceAt(int row, int col, int piece){

  }

  public void makeMove(int fromRow, int fromCol, int toRow, int toCol){

  }

  public CheckersMove[] getLegalMoves(int player){

  }

  public CheckersMove[] getLegalJumpsFrom(int player, int row, int col){

  }

  private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3){

  }

  private boolean canMove(int player, int r1, int c1, int r2, int c2){

  }
}
