package prototype;

/***************************************************************
* The Player class defines field variables that contain
* information about a player.
* @author Gionata Bonazzi
* @author Cole Sellers
* @author Brendan Cronan
* @author Rosa Fleming
* @version stable build 14 April 2018
***************************************************************/
public class Player {

	/** A name for the player **/
	private String playerName;

	/** the color for a standard checker **/
	private ColorStatus checkerColor;

	/** the color for a king checker **/
	private ColorStatus kingColor;

	/***************************************************************
	* Constructor for a Player object. It takes a name and a color
	* to save as field values for this Player.
	*
	* @param name
	*            the name of the player
	* @param color
	*            the player's color, can be either white or black.
	***************************************************************/
	public Player(String name, ColorStatus color) {
		this.playerName = name;
		this.checkerColor = color;
		if (color == ColorStatus.WHITE)
			this.kingColor = ColorStatus.WHITE_KING;
		else
			this.kingColor = ColorStatus.BLACK_KING;
	}

	/***************************************************************
	* Get method for the name field
	* @return the Player's name
	***************************************************************/
	public String getName() {
		return this.playerName;
	}

	/***************************************************************
	* Set method for the player's name.
	* @param name the new player's name
	***************************************************************/
	public void setName(String name) {
		this.playerName = name;
	}

	/***************************************************************
	* Get method for the color of the Player and the color of a
	* standard checker.
	* @return the player's color.
	***************************************************************/
	public ColorStatus playerColor() {
		return this.checkerColor;
	}

	/***************************************************************
	* Get method for the king's color.
	* @return the king's color.
	***************************************************************/
	public ColorStatus kingColor() {
		return this.kingColor;
	}
}
