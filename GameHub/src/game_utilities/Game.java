package game_utilities;

import java.util.ArrayList;
import exceptions.*;

/**
 * Contains method used by games, like checking if a player has lost,
 * create a game board, keep the list of players, begin and end the game.
 * It is abstract, so each game should implement unique methods that are
 * game-dependent.
 * @author Gionata Bonazzi
 * @version 15 February 2018
 *
 */
public abstract class Game {
	
	private ArrayList<Player> players;
	
	private Player currentPlayer; //Saves the current player so it is easier to display his stats.
	
	private int numPlayers = 1;
	
	private int playerTurn = -1; //The range should be (1, numPlayers). A value < 1 means that the game is over.
	
	private int totalTurn = 0;//The total number of turns passed since the start. Every time a player pass a turn, the counter gets increased. This is not a counter for the total individual turns.
	
	public Game(int numPlayers, int score) throws NotEnoughPlayersException{
		if(numPlayers < 1) throw new NotEnoughPlayersException();
		this.numPlayers = numPlayers;
		this.playerTurn = 1;
		for(int i = 1; i <= numPlayers; i++) {
			players.add(new Player(i, score));
		}
		this.currentPlayer = getPlayer();
		totalTurn++;
	}
	
	public void addPlayer(Player p, int turn) {
		players.add(turn, p);
	}
	
	//Sets a new score, rewriting the old one
	private void setScore(int newScore) {
		this.currentPlayer.setScore(newScore);
	}
	
	//Add some point to the current score.
	public void addScore(int score) {
		int newScore = this.currentPlayer.getScore() + score;
		setScore(newScore);
	}
	
	private Player getPlayer() {
		return players.get(this.playerTurn - 1); //Finds and returns the current player.
	}
	
	public int getScore() {
		return this.currentPlayer.getScore();
	}
	
	public boolean gameOver() {
		int losses = 0;
		for(Player p: players) {
			if(p.hasLost()) losses++;
		}
		return numPlayers - losses <= 1; //Return true if there is one, or less, player left in the game. The game stops if there is less than one player left. 
	}
	
	public void nextTurn() {
		this.totalTurn++;
		do {
			this.playerTurn = (this.playerTurn + 1) % numPlayers;
		}while(getPlayer().hasLost());//If the current player has lost, go to the next player
		this.playerTurn++; //Normalize the playerTurn using the values ranging (1, numPlayers)
		this.currentPlayer = getPlayer();
		this.currentPlayer.incrementTotalTurns();
	}
	
	public String showScores() {
		String output = "";
		for(Player p: players) {
			output = output + p.getName() + ":\t" + p.getScore();
		}
		return output;
	}
	
}
