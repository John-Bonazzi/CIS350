package prototype;

import java.util.ArrayList;
import java.util.Random;

/***************************************************************
* The AI class is used when a player wants to play against the
* computer
* @author Gionata Bonazzi
* @author Cole Sellers
* @author Brendan Cronan
* @author Rosa Fleming
* @version stable build 17 April 2018
***************************************************************/
public class CheckersAI extends Player {

	/***************************************************************
	* Constructor of the AI class, calls the super class.
	* @param color
	* 					a color is passed to the constructor
	***************************************************************/
	public CheckersAI(ColorStatus color) {
		super("AI", color);
	}

	/***************************************************************
	* Makes the move for the AI
	* @param board
	*					The current status of the board
	* @param options
	* 				A boolean array of the move options
	***************************************************************/
	public void makeMove(Board board, boolean[][] options) {
		int initialRow, initialCol;
		int[] pair = randomMove(options);
		initialRow = pair[0];
		initialCol = pair[1];
		options = board.showOptions(this, initialRow, initialCol);
		int finalRow, finalCol;
		do {
			pair = randomMove(options);
			finalRow = pair[0];
			finalCol = pair[1];
		} while(finalRow == initialRow && finalCol == initialCol);
		boolean didJump = board.move(this, initialRow, initialCol, finalRow, finalCol);
		if(didJump) {
			if(board.canJump(this.playerColor(), this.kingColor(), finalRow, finalCol)) {
				initialRow = finalRow;
				initialCol = finalCol;
				options = board.showOptions(this, initialRow, initialCol);
				do {
					pair = randomMove(options);
					finalRow = pair[0];
					finalCol = pair[1];
				} while(finalRow == initialRow && finalCol == initialCol);
				board.move(this, initialRow, initialCol, finalRow, finalCol);
			}
		}
	}

	/***************************************************************
	* This clss is the logic behind choosing the random move, it
	* will create a randondom number and choose a move from the
	* options.
	* @param options
	* 					A boolean array of the move options
	***************************************************************/
	private int[] randomMove(boolean[][] options) {
		Random rand = new Random();
		ArrayList<int[]> temp = checkOptions(options);
		return temp.get(rand.nextInt(temp.size()));
	}

	/***************************************************************
	* Creates an Arraylist of moves based on the boolean array options.
	* @param options
	*						A boolean array of the move options
	* @return An arraylist of all the possible moves.
	***************************************************************/
	private ArrayList<int[]> checkOptions(boolean[][] options) {
		ArrayList<int[]> result = new ArrayList<int[]>();
		for(int row = 0; row < options.length; row++) {
			for(int col = 0; col < options[row].length; col++) {
				if(options[row][col]) {
					result.add(new int[] {row, col});
				}
			}
		}
		return result;
	}
}
