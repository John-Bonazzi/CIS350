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
	
	private int numPlayers = 1;
	
	private int playerTurn = -1;
	
	public Game(int numPlayers) throws NotEnoughPlayersException{
		if(numPlayers < 1) throw new NotEnoughPlayersException();
		this.numPlayers = numPlayers;
		this.playerTurn = 0;
	}

	public void addPlayer(Player p, int turn) {
		players.add(turn, p);
	}
}
