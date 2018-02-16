package game_utilities;

import exceptions.*;

/**
 * This class defines a Player abstract object. Some information are used by
 * every game, like the player's turn, the player's score, the player's name.
 * Other methods, specific to the game, are to be implemented in the inherited
 * class.
 * 
 * @author Gionata Bonazzi
 * @version 15 February 2018
 *
 */
public abstract class Player {
	private int score = 0;

	private int turn;

	private String name;

	public Player(String name, int turn) throws NameEmptyException {
		if (name.isEmpty())
			throw new NameEmptyException();
		this.name = name;
		this.turn = turn;
	}

	public int getTurn() {
		return this.turn;
	}

	public int getScore() {
		return this.score;
	}

	public String getName() {
		return this.name;
	}

	public void setScore(int newScore) {
		this.score = newScore;
	}

	public boolean hasLost() {
		return this.score <= 0;
	}

}
