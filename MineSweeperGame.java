package project3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/********************************************************
 * A class to handle all of the basic functions of the classic
 * game "MineSweeper". Allows the player to adjust the difficulty by 
 * changing the number of mines and the size of the board
 * @author Rosa Fleming
 *@version February 14, 2018
 */
public class MineSweeperGame {

	/* a 2 dimensional array of cells to represent the board */
	private Cell[][] board;

	/* the status of the game (won, lost, or not over) */
	private GameStatus status;

	/* the total number of mines */
	private int totalMineCount;

	/* the total number of cells */
	private int cellCount;

	/* the number of cells that have been selected so far */
	private int cellsSelected;

	/**********************************************************************
	 * Constructor
	 * 
	 * @param mineCount:
	 *            the number of mines the game will have
	 * @param boardSize:
	 *            the length of one board side
	 */
	public MineSweeperGame(int mineCount, int boardSize) {

		// cellCount is later used to determine win or loss
		cellCount = boardSize * boardSize;

		// totalMineCount is later used to determine win or loss
		totalMineCount = mineCount;

		// instantiate the board
		board = new Cell[boardSize][boardSize];
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				board[row][col] = new Cell();
			}
		}

		// use a random number generator to randomly distribute bombs across
		// board
		Random random = new Random();
		int i = 0;
		while (i < mineCount) {
			int row = random.nextInt(boardSize);
			int col = random.nextInt(boardSize);

			if (!board[row][col].isMine()) {
				board[row][col].setMine();
				++i;
			}

		}

		// initialize status and cellsSelected
		status = GameStatus.NOTOVERYET;
		cellsSelected = 0;

	}

	/***************************************************************
	 * A method to return a specified cell
	 * 
	 * @param row:
	 *            the row of the cell
	 * @param col:
	 *            the column of the cell
	 * @return: the specified cell
	 */
	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	/*******************************************************
	 * Determines the number of mines in the 8 cells immediately surrounding the
	 * specified cell
	 * 
	 * @param row:
	 *            row of the cell specified
	 * @param col:
	 *            column of the cell specified
	 * @return: the number of bombs touching the specified cell
	 */

	public int countMines(int row, int col) {

		// for counting
		int mineCount = 0;

		// iterate through surrounding cells, counting mines
		for (int i = row - 1; i <= row + 1; ++i) {
			for (int a = col - 1; a <= col + 1; ++a) {

				if ((i < board.length && i >= 0) && (a < board.length && a >= 0)) {
					if (board[i][a].isMine())
						++mineCount;
				}

			}
		}
		return mineCount;
	}

	/******************************************************************************
	 * method to select a cell and direct game behavior accordingly
	 * 
	 * @param row:
	 *            row of the cell selected
	 * @param col:
	 *            column of the cell selected
	 */
	public void select(int row, int col) {

		// if there are mines touching the selected cell, expose that cell,
		// label it
		// with the number of neighboring mines, and increment cellsSelected.
		// if the cell is a mine, a loss occurs
		if (countMines(row, col) > 0) {

			if (board[row][col].isMine()) {
				status = GameStatus.LOST;
				return;
			}

			board[row][col].expose();
			++cellsSelected;
			board[row][col].setMineCount(countMines(row, col));

			// return to avoid reaching the rest of the method
			return;
		}

		// if the cell is blank, reveal all neighbor cells until non-blank cell
		// is reached
		for (int i = row - 1; i <= row + 1; ++i) {

			for (int a = col - 1; a <= col + 1; ++a) {

				// avoid NullPointerException
				if ((i < board.length && i >= 0) && (a < board.length && a >= 0)) {

					// if the cell is blank, select it with a recursive call and
					// increment cellsSelected
					if (!board[i][a].isMine() && countMines(i, a) == 0 && !board[i][a].isExposed()) {

						board[i][a].expose();
						++cellsSelected;
						select(i, a);

					}

					// if the cell is not blank or a mine, expose it and
					// increment cellsSelected, but halt
					// recursion
					if (!board[i][a].isMine() && !board[i][a].isExposed() && countMines(i, a) > 0) {

						board[i][a].expose();
						++cellsSelected;
						board[i][a].setMineCount(countMines(i, a));

					}

				}

			}
		}

	}

	/***************************************************************************************
	 * a method to return the gameStatus so that the GUI can be displayed
	 * accordingly this method also checks for winning conditions and will set
	 * the status to "WON" before returning if necessary
	 * 
	 * @return: the status of the game
	 */
	public GameStatus getGameStatus() {

		if (cellsSelected == (cellCount - totalMineCount)) {
			status = GameStatus.WON;
		}

		return status;
	}

}
