
import ColorStatus;
import java.util.ArrayList;

public class Game{

  private ArrayList<Player> players;
  private boolean gameRunnning = false;

  public Game() throws GameIsRunningException{
    try{
      if(!this.gameRunning)
        throw new GameIsRunningException();
    }
    this.players.clear(); //Make sure the array is empty.
    this.gameRunning = true;
    players.add(new Player("White", ColorStatus.WHITE));
    players.add(new Player("Black", ColorStatus.BLACK));
  }

  public Game(ColorStatus playerColor) throws GameIsRunningException{
    try{
      if(!this.gameRunning)
        throw new GameIsRunningException();
    }
    this.players.clear(); //Make sure the array is empty.
    this.gameRunning = true;
    players.add(new Player("Player", playerColor));
  }

  private boolean isJump(unsigned int ir, unsigned int ic, unsigned int fr, unsigned int fc){
    return (ir - fr == 2 || ic - fc == 2);
  }


}
