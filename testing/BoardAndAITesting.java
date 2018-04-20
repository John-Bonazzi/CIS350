package testing;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import prototype.Board;
import prototype.CheckersAI;
import prototype.ColorStatus;
import prototype.Game;
import prototype.Player;

/****************************************************************
 * JUnit testing for the Board class.
 * The Board class is deeply connected to the GUI,
 * meaning that there is a direct representation of the Board's
 * elements in the GUI.
 * Thus, it has been easier to manually test the class through
 * the GUI.
 * However, this Unit will test only some of the methods from Board
 * and will only test it once, assuming that a good outcome
 * from this JUnit, and no apparent bugs in the GUI would
 * mean that the Board has been thoroughly tested.
 * Methods not tested here:
 * move(Player, int, int, int, int): Tested with different console outputs
 * showDoubleJumpOptions(): Tested with different console outputs.
 * showOptions(): Tested with different console outputs.
 * 
 * @author Gionata Bonazzi
 * @author Cole Sellers
 * @author Brendan Cronan
 * @author Rosa Fleming
 * @version 19 April 2018
 ****************************************************************/
class BoardAndAITesting {
	
	/** Game used to test makeMove() for AI testing. **/
	private Game gameAI = new Game(true);
	
	/** Board used to test makeMove() for AI testing. **/
	private Board testSubjectAI = new Board(gameAI);
	
	/** Game passed to the Board to test for the observer pattern. **/
	private Game game;
	
	/** Board object used to test the class. **/
	private Board testSubject;
	
	/****************************************************************
	 * Set up a new game and a new board before each Test Unit.
	 ***************************************************************/
	@BeforeEach
	void setUp() {
		this.game = new Game(false);
		this.testSubject = new Board(game);
	}

	/****************************************************************
	 * Helper method that makes a matrix that resembles the initial
	 * state of a checkers' board.
	 * The black checkers' and white checkers' coordinates are put
	 * in two arrays as an integer with two digits, the first is
	 * the row, the second the column (from the right to the left).
	 * @return A matrix resembling the Board's matrix right after being
	 * 	instantiated.
	 ****************************************************************/
	private ColorStatus[][] makeBoardForTestInit() {
		int[] blackSpaces = 
			{01, 03, 05, 07, 10, 12, 14, 16, 21, 23, 25, 27};
		int[] whiteSpaces = 
			{61, 63, 65, 67, 70, 72, 74, 76, 50, 52, 54, 56};
		ColorStatus[][] result = new ColorStatus[8][8];
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				result[row][col] = ColorStatus.EMPTY;		
			}
		}
		for (int row = 0; row < result.length; row++) {
			for (int col = 0; col < result[0].length; col++) {
				for (int scan = 0; 
					scan < blackSpaces.length; 
					scan++) {
					if ((int) blackSpaces[scan] / 10 == row 
						&& blackSpaces[scan] % 10 
						== col) {
						result[row][col] = 
							ColorStatus.BLACK;
					} else if   ((int) 
							whiteSpaces[scan] / 10 
							== row 
							&& whiteSpaces[scan] 
							% 10 == col) {
						result[row][col] = 
							ColorStatus.WHITE;
					}
				}
			}
		}
		return result;
	}
	
	/****************************************************************
	 * Test Unit to test:
	 * 1. The Board constructor and, indirectly, Init()
	 * 2. The getBoard() method.
	 * A matrix identical to the one created by Init() is made
	 * using an helper method.
	 * If the two matrices are identical, while being
	 * created in two different ways, then the test is passed.
	 ***************************************************************/
	@Test
	void initTest() {
		this.testSubject = new Board(this.game);
		ColorStatus[][] testBoard = makeBoardForTestInit();
		assertArrayEquals(this.testSubject.getBoard(), testBoard);
	}
	
	/**************************************************************
	 * Test Unit for setAllFalse().
	 * This is a strange testing;
	 * on one hand, having multiple assertFalse(),
	 * called in a nested loop, is not standard practice.
	 * On the other hand, the matrix created by the tested method
	 * could not be recreated in any other way than by hand.
	 * So instead of comparing the return value with a sample board,
	 * the unit is going to look that each value is false.
	 **************************************************************/
	@Test
	void setAllFalseTest() {
		boolean[][] testBoard = 
				this.testSubject.setAllFalse();
		for (int i = 0; i < testBoard.length; i++) {
			for (int j = 0; j < testBoard[i].length; j++) {
				assertFalse(testBoard[i][j]);
			}
		}
	}
	
	/****************************************************************
	 * Helper method that makes a matrix that resembles the moves 
	 * the "White" player can do in the game's first turn.
	 * The true values coordinates are: 5 0, 5 2, 5 4, 5 6 (row col).
	 * @return A matrix resembling the "White"'s player first turn's
	 *  movement choices.
	 ****************************************************************/
	private boolean[][] makeBoardForTestCanSelect() {
		boolean[][] result = new boolean[8][8];
		for (int row = 0; 
				row < result.length; row++) {
			for (int col = 0; 
					col < result[row].length; col++) {
				result[row][col] = false;
			}
		}
		result[5][0] = true;
		result[5][2] = true;
		result[5][4] = true;
		result[5][6] = true;
		return result;
	}
	
	/****************************************************************
	 * Test Unit for canSelect().
	 * Assumption: the game is in its first turn, the player selected
	 * is the "White", so all checkers at row 6 (5 in the code) can move.
	 * Meaning: the corresponding values to the checkers in row 6 are true,
	 * everything else is false.
	 ***************************************************************/
	@Test
	void canSelectTest() {
		Player player = this.game.getPlayers().get(0);
		boolean[][] boardTest = this.testSubject.canSelect(player);
		boolean[][] sampleTest = makeBoardForTestCanSelect();
		assertArrayEquals(boardTest, sampleTest);
	}
	
	/****************************************************************
	 * Test Unit for canMove(int, int).
	 * This test is checking if, at the initial state of
	 * the board, a checker can move to row 4 column 1.
	 * That would be true, since checkers 5, 0 and 5, 2 
	 * can move there (row, col).
	 ****************************************************************/
	@Test
	void canMoveTest() {
		assertTrue(this.testSubject.canMove(4, 1));
	}
	
	/****************************************************************
	 * Test Unit for canMove(ColorStatus, ColorStatus, int, int).
	 * This test is checking if, at the initial state of
	 * the board when the "White" player is playing,
	 * the checker at position 5 row 2 column can move.
	 ****************************************************************/
	@Test
	void canMoveTest2() {
		assertTrue(this.testSubject.canMove(
				ColorStatus.WHITE, ColorStatus.WHITE_KING, 
				5, 2));
		
	}

	/****************************************************************
	 * Test Unit for canJump(ColorStatus, ColorStatus, int, int).
	 * Unfortunately, there is no way to manually create a board.
	 * For that reason, this unit will test whether the white
	 * checker at position 5 row 2 column can jump at the beginning.
	 * The method has been thoroughly tested along with the GUI
	 * through manual testing by playing and by reading the record
	 * created by console outputs.
	 ***************************************************************/
	@Test
	void canJumpTest() {
		boolean testFalse = 
				this.testSubject.canJump(ColorStatus.WHITE, 
				ColorStatus.WHITE_KING, 5, 2);
		assertFalse(testFalse);
	}
	
	/**************************************************************
	 * Test Unit for makeMove(), a CheckerAI method.
	 * The method is the only public one in the class,
	 * and it generates a random result on the board.
	 * The unit is set up so that, at the initial state,
	 * the AI can only move a checker on the third row 
	 * from the top (row 2 in the code).
	 * A boolean is created, a loop goes through the third
	 * row and search for a unique black checker.
	 * If it does find one, then the boolean will be true,
	 * and it will pass the test.
	 * If it does not find one, or it finds more than one checker
	 * on that row, then the boolean will be false, and it won't
	 * pass the test.
	 **************************************************************/
	@Test
	void makeMoveAITest() {
		CheckersAI aiTest = (CheckersAI)
				this.gameAI.getPlayers().get(1);
		boolean[][] testOptions = 
				this.testSubjectAI.canSelect(aiTest);
		aiTest.makeMove(this.testSubjectAI, testOptions, this.gameAI);
		ColorStatus[][] testBoard = this.testSubjectAI.getBoard();
		boolean testTrue = false;
		for (int i = 0; i < testBoard[3].length; i++) {
			if (!testTrue) {
				testTrue = testBoard[3][i] == ColorStatus.BLACK;
			} else if (testBoard[3][i] == ColorStatus.BLACK) {
				testTrue = false;
				break;
			}
		}
		assertTrue(testTrue);
	}
}
