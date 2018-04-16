package prototype;
import java.lang.RuntimeException;
public class GameIsRunningException extends RuntimeException{
  public GameIsRunningException(){
    System.out.println("The game is already running.");
  }
}
