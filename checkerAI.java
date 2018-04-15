package prototype;

import java.util.ArrayList;
import java.util.Random;

public class checkerAI extends Player {
	
	public checkerAI(ColorStatus color) {
		super("AI", color);
	}

	public void makeMove(Board board, boolean[][] options) {
		int initialRow, initialCol;
		int[] pair = randomMove(options);
		initialRow = pair[0];
		initialCol = pair[1];
		options = board.showOptions(this, initialRow, initialCol);
		int finalRow, finalCol;
		pair = randomMove(options);
		finalRow = pair[0];
		finalCol = pair[1];
		board.move(this, initialRow, initialCol, finalRow, finalCol);
	}
	
	private int[] randomMove(boolean[][] options) {
		Random rand = new Random();
		ArrayList<int[]> temp = checkOptions(options);
		return temp.get(rand.nextInt(temp.size()));
	}
	
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
