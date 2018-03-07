package exceptions;

public class NameEmptyException extends RuntimeException {
	public NameEmptyException() {
		super("The name has to be at least one letter long.");
	}
}
