package memory;

import java.util.ArrayList;
import java.util.Random;


//FIXME: make a player class that can be used by everyone (has a name, a score etc...)
//FIXME: make a game class that can be used by everyone (contains the game board, the number of players, knows the turn, who is playing etc...)
//FIXME: make a file utilities class that contains some of the method to save information
//about general data like player's data, game's data.


public class MemoryGame {

	private ArrayList<Tile> tiles = new ArrayList<Tile>();

	private ArrayList<Integer> score;

	private int numPlayers = 1;

	private int player = -1;

	/*****************************************************************************
	 * Default construct for the Memory class.
	 *
	 * @param num_pictures
	 *            tells the program how many pictures to use. This is the number of
	 *            unique elements in the game, paired tiles count as one.
	 *****************************************************************************/
	public MemoryGame(int num_pictures, int players) {
		new MemoryGame(num_pictures, players, 10);
	}
	
	public MemoryGame(int num_pictures, int players, int initialScore) {
		if (players > 1)
			this.numPlayers = players;
		for (int i = 0; i < numPlayers; i++)
			score.add(initialScore);
		Picture[] p = Picture.values();
		for (int i = 0; i < num_pictures; i++) {
			Tile t = new Tile(p[i], Visibility.HIDDEN);
			tiles.add(t);
		}
		setPositions(tiles);
	}

	public int getScore(int player) {
		return score.get(player);
	}
	
	public int nextPlayer() {
		if(hasLost(this.player))
			this.player = (this.player + 1) % this.numPlayers;
		return this.player;
	}

	
	
	public void turn(int firstTile, int secondTile, int player) {
		int newScore;
		if(selectTiles(tiles.get(firstTile), tiles.get(secondTile)))
			newScore = score.get(player) + tiles.get(firstTile).positive_points;
		else
			newScore = score.get(player) + tiles.get(firstTile).negative_points;
		score.set(player, newScore);
	}

	
	public boolean hasLost(int player) {
		return score.get(player) <= 0;
	}

	public String showScores() {
		String output = "";
		for( int i = 0; i < this.numPlayers; i++, output += "\n")
			output = output + "Player " + (i + 1) + ":\t" + getScore(i);
		return output;
	}
	
	/*****************************************************************************
	 * Given a list of tiles, the method assigns a "paired" tile at a random position
	 *****************************************************************************/	
	private void setPositions(ArrayList<Tile> list) {
		int size  = list.size();
		Tile temp;
		Random rand = new Random();
		for(int i = 0; i < size; i++)
			list.add(list.get(i)); //Copy the list and append it to double the tiles.
		size = list.size();
		int pos;
		for(int i = 0; i < size; i++) {
			pos = i + rand.nextInt(size - i);
			temp = list.get(i);
			list.set(i, list.get(pos));
			list.set(pos, temp);
		}
	}

	private boolean selectTiles(Tile firstTile, Tile secondTile) {
		return firstTile.picture == secondTile.picture;
	}
}
