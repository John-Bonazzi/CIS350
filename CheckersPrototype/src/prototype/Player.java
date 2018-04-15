package prototype;
public class Player{

  private String playerName;

  private ColorStatus checkerColor;
  
  private ColorStatus kingColor;

  public Player(String name, ColorStatus color){
    this.playerName = name;
    this.checkerColor = color;
    if(color == ColorStatus.WHITE)
    		this.kingColor = ColorStatus.WHITE_KING;
    else
    		this.kingColor =ColorStatus.BLACK_KING;
  }

  public String getName(){
    return this.playerName;
  }

  public ColorStatus playerColor(){
    return this.checkerColor;
  }
  
  public ColorStatus kingColor() {
	  return this.kingColor;
  }
}