package exceptions;

public class NotEnoughPlayersException extends RuntimeException {

	public NotEnoughPlayersException() {
		super("You need at least one player to play this game.");
	}
}
