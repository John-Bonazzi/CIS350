package gui;
import javax.swing.*;
public abstract class GamePanel extends JPanel{

  /*
  * This method will perform most of the setup for each GUI and will primarily
  * call other methods and define instance variables.  Called from constructor.
  */
  public void init();//add parameters as needed.

  /*
  * This will return a Grid object that defines how many squares, rows/cols
  * their width/height and any other aspects we need.
  */
  public Grid makeGrid(Dimension size, int rowNum, int colNum);

  /*
  *
  */




}
