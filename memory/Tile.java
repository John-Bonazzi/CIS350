package memory;

import java.util.Random;

public class Tile {

	private Picture picture;

	private Visibility visibility;

	private int positive_points; // assign a value to a tile, randomly generated. Adds a bit of luck.

	private int negative_points; // assign a negative value to a tile, if they are not matched, take the points out.

	public Tile(Picture picture, Visibility visibility) {
		this.picture = picture;
		this.visibility = visibility;

		Random rand = new Random();

		this.positive_points = rand.nextInt(6) + 5; // Get a number from 5 to 10.
		this.negative_points = (int) (-1) * (9 + rand.nextInt(21)) / positive_points; // Get a number from 0 to 4. 0 really small chance to appear.
	}
	
	public Picture getPicture() {
		return this.picture;
	}
	
	public Visibility getVisibility() {
		return this.visibility;
	}

	public int getPoints(boolean positive) {
		if(positive) return this.positive_points;
		return this.negative_points;
	}
}
