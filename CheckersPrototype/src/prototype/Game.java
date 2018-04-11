package prototype;
import java.util.ArrayList;

public class Game{

  private ArrayList<Player> players;
  private boolean gameRunning = false;

  public Game() throws GameIsRunningException{
   
      if(!this.gameRunning)
        throw new GameIsRunningException();

    this.players.clear(); //Make sure the array is empty.
    this.gameRunning = true;
    players.add(new Player("White", ColorStatus.WHITE));
    players.add(new Player("Black", ColorStatus.BLACK));
  }

  public Game(ColorStatus playerColor) throws GameIsRunningException{
    
      if(!this.gameRunning)
        throw new GameIsRunningException();
    

    this.players.clear(); //Make sure the array is empty.
    this.gameRunning = true;
    players.add(new Player("Player", playerColor));
   
  }

  private boolean isJump(int ir, int ic, int fr, int fc){
    return (ir - fr == 2 || ic - fc == 2);
  }


}
