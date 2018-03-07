package memory;

import java.util.ArrayList;
import java.util.Random;
import game_utilities.*;
import exceptions.*;


//FIXME: make a file utilities class that contains some of the method to save information
//about general data like player's data, game's data.


public class MemoryGame extends Game{

	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	public MemoryGame(int num_pictures, int players, int initialScore) throws NotEnoughPicturesException {
		super(players, initialScore);
		Picture[] p = Picture.values();
		if(num_pictures > p.length) throw new NotEnoughPicturesException(p.length, num_pictures);
		for (int i = 0; i < num_pictures; i++) {
			tiles.add(new Tile(p[i], Visibility.HIDDEN));
		}
		setPositions(this.tiles);
	}

	//NOTE: tiles go from 1 to numTiles, so in the array the index is parameter - 1
	//NOTE: every tile has a negative value, so when the user select two wrong tiles, the negative values are added together, and the mean is taken as new value. note that this mean is rounded down to an integer.
	public void gameTurn(int firstTile, int secondTile) {
		Tile first = tiles.get(firstTile - 1);
		Tile second = tiles.get(secondTile - 1);
		boolean positive = selectTiles(first, second);//Find where to add points or subtract them
		int newScore = 0;
		if(positive)
			newScore = first.getPoints(positive); //Get the right amount of points
		else
			newScore = (int) (first.getPoints(positive) + second.getPoints(positive)) / 2; //Takes the mean value, rounded to the lowest integer. example: 5 and 6 -> 5.5, value assigned = 5
		super.addScore(newScore); //Add those points to the total
	}

	/*****************************************************************************
	 * Given a list of tiles, the method assigns a "paired" tile at a random position.
	 * The method copies the tiles in the list, to double the actual list.
	 * It does this because the game pairs a tile with an exact copy of the object
	 * in another position (so, same field's values), and this method 
	 * copies the list doubling it.
	 *****************************************************************************/
	private void setPositions(ArrayList<Tile> list) {
		int size  = list.size();
		Tile temp;
		Random rand = new Random();
		for(int i = 0; i < size; i++)
			list.add(list.get(i)); //Copy the list and append it to double the tiles.
		size = list.size(); //get the new size
		int pos;
		for(int i = 0; i < size; i++) {
			pos = i + rand.nextInt(size - i);
			temp = list.get(i);
			list.set(i, list.get(pos));
			list.set(pos, temp);
		}
	}

	private boolean selectTiles(Tile firstTile, Tile secondTile) {
		if(firstTile.getPicture() == secondTile.getPicture()) {
			tiles.remove(firstTile);
			tiles.remove(secondTile);
			return true;
		}
		else {
			return false;
		}
		
	}
}
