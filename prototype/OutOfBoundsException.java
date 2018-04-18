package prototype;

  /***************************************************************
  * This class is called if the user trys to move off the board
  * @author Gionata Bonazzi
  * @author Cole Sellers
  * @author Brendan Cronan
  * @author Rosa Fleming
  * @version stable build 17 April 2018
  ***************************************************************/
@SuppressWarnings("serial")
public class OutOfBoundsException extends RuntimeException
  /***************************************************************
  * If the user trys to move off the board it will print that they
  * passed illegal parameters.
  ***************************************************************/
  public OutOfBoundsException(){
    System.out.println("The parameter passed are out of the board's bounds");
  }
}
