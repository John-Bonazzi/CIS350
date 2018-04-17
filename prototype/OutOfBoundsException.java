package prototype;

@SuppressWarnings("serial")
public class OutOfBoundsException extends RuntimeException{
  public OutOfBoundsException(){
    System.out.println("The parameter passed are out of the board's bounds");
  }
}
