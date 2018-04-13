package prototype;

import java.util.ArrayList;

public class Game {

	private ArrayList<Player> players;
	private boolean gameRunning = false;
	private ColorStatus currentPlayer; 

	public Game() throws GameIsRunningException {

		if (this.gameRunning)
			throw new GameIsRunningException();
		players = new ArrayList<Player>();
		this.gameRunning = true;
		players.add(new Player("White", ColorStatus.WHITE));
		players.add(new Player("Black", ColorStatus.BLACK));
		setCurrentPlayer(ColorStatus.WHITE);			//players.get(0).playerColor();
	}

	public Game(ColorStatus playerColor) throws GameIsRunningException {

		if (!this.gameRunning)
			throw new GameIsRunningException();

		this.players.clear(); // Make sure the array is empty.
		this.gameRunning = true;
		players.add(new Player("Player", playerColor));
		setCurrentPlayer(ColorStatus.WHITE);

	}

	private boolean isJump(int ir, int ic, int fr, int fc) {
		return (ir - fr == 2 || ic - fc == 2);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public boolean isGameRunning() {
		return gameRunning;
	}

	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}

	/**
	 * @return the currentPlayer
	 */
	public Player getCurrentPlayer() {
		for(Player p: players)
			if(p.playerColor() == currentPlayer)
				return p;
		return null;
	}

	/**
	 * @param currentPlayer the currentPlayer to set
	 */
	public void setCurrentPlayer(ColorStatus currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

}
