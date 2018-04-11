package prototype;
public class Player{

  private String playerName;

  private ColorStatus playerColor;

  public Player(String name, ColorStatus color){
    this.playerName = name;
    this.playerColor = color;
  }

  public String getName(){
    return this.playerName;
  }

  public ColorStatus playerColor(){
    return this.playerColor;
  }
}
