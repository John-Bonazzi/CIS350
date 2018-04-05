package memory;

import exceptions.NameEmptyException;

public class Player extends game_utilities.Player {

	public Player(String name, int turn, int score) throws NameEmptyException {
		super(name, turn);
		this.setScore(score);
	}
}
