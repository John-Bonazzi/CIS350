package game_utilities;

import exceptions.*;

/**
 * This class defines a Player object.
 * The Player object is something used by all games, and works the same way.
 * This object does not change anything in the game, but is used to save statistics
 * about the player. Which means that this class should not have anything else except setters and getters.
 * However, one exception is the method hasLost(), which is important to keep on the player-side of the code
 * so that each Player object is aware if it is still playing or not.
 * @author Gionata Bonazzi
 * @version 15 February 2018
 *
 */
public class Player {
	
	private int score = 0;

	private int turn;

	private int totalTurns;
	
	private String name;

	public Player(int turn, int score){
		defaultName(turn);
		this.turn = turn;
		this.score = score;
		this.totalTurns = 0;
	}

	private void defaultName(int turn) {
		this.name = "Player" + turn;
	}
	
	public void changeName(String name) {
		this.name = name;
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

	public void incrementTotalTurns() {
		this.totalTurns++;
	}
	
	public int getTotalTurns() {
		return this.totalTurns;
	}
}
