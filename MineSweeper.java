package project3;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**************************************************
 * A driver class to instantiate and begin a game of 
 * MineSweeper
 * @author Pieter Holleman * @version February 22 2016
 */

public class MineSweeper {

	public static void main(String[] args) {

		String x;
		int boardSize = 0;
		int temp = 0;
		x = JOptionPane.showInputDialog(null, "Enter the size of the board.");

		// only set board size if it is valid, otherwise prompt user
		while (boardSize == 0) {
			temp = Integer.parseInt(x);
			if (temp <= 30 && temp >= 3) {
				boardSize = temp;
			} else {
				x = JOptionPane.showInputDialog(null, "Please enter an integer between 3 and 30");
				temp = Integer.parseInt(x);
			}

		}

		int bombs = 0;
		x = JOptionPane.showInputDialog(null, "How many mines?");

		// only set mine number if input is valid, otherwise prompt user until
		// valid entry
		while (bombs == 0) {

			temp = Integer.parseInt(x);
			if (temp < (boardSize * boardSize) && temp > 0) {
				bombs = temp;
			} else {
				x = JOptionPane.showInputDialog(null,
						"Please enter an positive number that is less than " + "the number of cells");
				temp = Integer.parseInt(x);
			}
		}

		// top level container
		JFrame frame = new JFrame("MineSweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// instantiate and add panel, passing user input as params
		MineSweeperPanel panel = new MineSweeperPanel(bombs, boardSize);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}

}
