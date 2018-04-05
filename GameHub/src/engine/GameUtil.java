package engine;
import javax.swing.*;
import java.awt.*;
/*
* This class is a utility class intended to store methods that each class can
* utilize to lessen repeated code segments. For example, every class will need
* some sort of Matrix logic to read/write to their game board.  This class
* provides static methods to do so.
*/
public final class GameUtil{

  /*
  * This is a private constructor that should never be called.
  */
  private GameUtil(){
    //nothing goes here.
  }

  /*
  * This is an example of the format of any methods in this class.
  * They must be static.
  * Call the method like: GameUtil.add(3,3);
  */
  public static int add(int a, int b){
    //Example method
    int out = a + b;
    return out;
  }


}
