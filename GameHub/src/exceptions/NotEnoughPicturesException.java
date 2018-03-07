package exceptions;

public class NotEnoughPicturesException extends RuntimeException {

	public NotEnoughPicturesException(int maxSize, int currentSize) {
		super("The game supports a maximum of " + maxSize + " unique pictures. The user selected " + currentSize + " different pictures.");
	}

}
