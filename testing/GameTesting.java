package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import prototype.ColorStatus;
import prototype.Game;
import prototype.GameMode;
import prototype.Player;
import prototype.CheckersAI;

/**
 * Test Unit for a Game object.
 * The Game object instantiated in this JUnit is made
 * with a different constructor than the one used in the software.
 * The difference is that, for testing, the Game does not
 * need the GUI elements.
 * Assumption: GUI elements have been tested manually.
 * Assumption #2: methods inherited by the Observable class have
 * been already tested.
 * 
 * Not tested:
 * Game(CheckersPanel, Checkers_GUI, boolean): this method is
 * 		essentially the same as Game(boolean) but with GUI elements.
 * startGame(): each method invoked in here has been tested.
 * startGameAI(): each method invoked in here has been tested.
 * getTime(), updateTime() and setTime(): These have a graphical
 * 		representation through the GUI in the form of a timer,
 * 		and can be manually tested along with the GUI.
 *
 * @author Gionata Bonazzi
 * @author Cole Sellers
 * @author Brendan Cronan
 * @author Rosa Fleming
 * @version 19 April 2018
 *
 */
class GameTesting {

	/** A Game object Player vs Player. **/
	private Game testSubjectVSPlayer;
	
	/** A Game object Player vs AI. **/
	private Game testSubjectVSAI;

	/****************************************************************
	 * Instantiate a new object after each test.
	 * This will assure that the game objects have
	 * default values before each test.
	 ***************************************************************/
	@BeforeEach
	void setUp() {
		this.testSubjectVSPlayer = new Game(false);
		this.testSubjectVSAI = new Game(true);
	}
	
	/****************************************************************
	 * Test Unit for getPlayers() method with the first player
	 * in the list, the "White" player.
	 * To compare the player in the game and the test sample,
	 * the method is comparing all the Player's qualities that
	 * are passed to the constructor.
	 ***************************************************************/
	@Test
	void getPlayersTestWhite() {
		Player testWhitePlayer = new Player("White", ColorStatus.WHITE);
		
		//Assumption: The first player is 
		//White in the current game's version.
		Player gameWhitePlayer = 
				this.testSubjectVSPlayer.getPlayers().get(0);
		assertEquals(testWhitePlayer.getName(), 
					 gameWhitePlayer.getName());
		assertEquals(testWhitePlayer.playerColor(), 
					 gameWhitePlayer.playerColor());
					
	}
	
	/****************************************************************
	 * Test Unit for getPlayers() method with the second player
	 * in the list, the "Black" player.
	 * To compare the player in the game and the test sample,
	 * the method is comparing all the Player's qualities that
	 * are passed to the constructor.
	 ***************************************************************/
	@Test
	void getPlayersTestBlack() {
		Player testBlackPlayer = new Player("Black", ColorStatus.BLACK);
		
		//Assumption: The second player in the list is 
		//Black in the current game's version.
		Player gameBlackPlayer = 
				this.testSubjectVSPlayer.getPlayers().get(1);
		assertEquals(testBlackPlayer.getName(), 
					 gameBlackPlayer.getName());
		assertEquals(testBlackPlayer.playerColor(), 
					 gameBlackPlayer.playerColor());
	}
	
	/****************************************************************
	 * Test Unit for getPlayers() on a game against AI, where
	 * the second player in the list is the AI.
	 * To compare the CheckersAI in the game and the test sample,
	 * the method is comparing the only quality passed to the
	 * constructor.
	 ***************************************************************/
	@Test
	void getPlayersTestBlackAI() {
		CheckersAI testAI = new CheckersAI(ColorStatus.BLACK);
		
		//Assumption: The second element is a CheckersAI object.
		CheckersAI gameAI = 
				(CheckersAI) this.testSubjectVSAI.getPlayers().get(1);
		assertEquals(testAI.playerColor(), gameAI.playerColor());
	}
	
	/****************************************************************
	 * Test Unit for setNames().
	 * The unit will test if both players have their names changed,
	 * that the names are assigned in the right order,
	 * and that their changed names have not been modified by the method.
	 ***************************************************************/
	@Test
	void setNamesTest() {
		String testName1 = "Test1";
		String testName2 = "Test2";
		this.testSubjectVSPlayer.setNames(testName1, testName2);
		Player firstPlayer = this.testSubjectVSPlayer.getPlayers().get(0);
		Player secondPlayer = this.testSubjectVSPlayer.getPlayers().get(1);
		assertEquals(firstPlayer.getName(), testName1);
		assertEquals(secondPlayer.getName(), testName2);
	}
	
	/****************************************************************
	 * Test Unit for setNames() on a game against AI.
	 * The unit will test if both the player and the AI have their 
	 * names changed, that the names are assigned in the right order,
	 * and that their changed names have not been modified by the method.
	 ****************************************************************/
	@Test
	void setNamesTestAI() {
		String testName1 = "Test1AI";
		String testName2 = "Test2AI";
		this.testSubjectVSAI.setNames(testName1, testName2);
		Player firstPlayer = this.testSubjectVSAI.getPlayers().get(0);
		CheckersAI secondPlayerAI = 
				(CheckersAI) this.testSubjectVSAI.getPlayers().get(1);
		assertEquals(firstPlayer.getName(), testName1);
		assertEquals(secondPlayerAI.getName(), testName2);
	}
	
	/****************************************************************
	 * Test Unit for getMode().
	 * After the constructor call, the default GameMode
	 * is FREE_MODE.
	 * This Unit will test that:
	 * 1. the default GameMode is FREE_MODE.
	 * 2. the getMode() method works properly.
	 ***************************************************************/
	@Test
	void getModeTest() {
		GameMode testMode = GameMode.FREE_MODE;
		GameMode gameMode = this.testSubjectVSPlayer.getMode();
		assertEquals(testMode, gameMode);
	}
	
	/****************************************************************
	 * Test Unit for getMode() against AI.
	 * After the constructor call, the default GameMode
	 * is FREE_MODE.
	 * This Unit will test that:
	 * 1. the default GameMode is FREE_MODE.
	 * 2. the getMode() method works properly.
	 ***************************************************************/
	@Test
	void getModeTestAI() {
		GameMode testModeAI = GameMode.FREE_MODE;
		GameMode gameModeAI = this.testSubjectVSAI.getMode();
		assertEquals(testModeAI, gameModeAI);
	}
	
	
	/****************************************************************
	 * Test Unit for isGameRunning(), both against a player and against AI.
	 * After the constructor call, the game should be running.
	 * The Unit will test whether the game runs or not.
	 ***************************************************************/
	@Test
	void isGameRunningTest() {
		assertTrue(this.testSubjectVSPlayer.isGameRunning());
		assertTrue(this.testSubjectVSAI.isGameRunning());
	}
	
	/****************************************************************
	 * Test Unit for setGameMode().
	 * The Unit will test whether the method can
	 * correctly change the game mode or not.
	 ****************************************************************/
	@Test
	void setGameModeTest() {
		GameMode testMode = GameMode.GAME_TIMED_MODE;
		this.testSubjectVSPlayer.setGameMode(testMode);
		GameMode gameMode = this.testSubjectVSPlayer.getMode();
		assertEquals(testMode, gameMode);
	}
	
	/****************************************************************
	 * Test Unit for setGameMode() against AI.
	 * The Unit will test whether the method can
	 * correctly change the game mode or not.
	 ****************************************************************/
	@Test
	void setGameModeTestAI() {
		GameMode testModeAI = GameMode.GAME_TIMED_MODE;
		this.testSubjectVSAI.setGameMode(testModeAI);
		GameMode gameModeAI = this.testSubjectVSAI.getMode();
		assertEquals(testModeAI, gameModeAI);
	}
	
	/****************************************************************
	 * Test Unit for stopGame().
	 * The Unit will first stop the game, and
	 * then check if isGameRunning() returns false.
	 ***************************************************************/
	@Test
	void stopGameTest() {
		this.testSubjectVSPlayer.stopGame();
		assertFalse(this.testSubjectVSPlayer.isGameRunning());
	}
	
	/****************************************************************
	 * Test Unit for stopGame() against AI.
	 * The Unit will first stop the game, and
	 * then check if isGameRunning() returns false.
	 ***************************************************************/
	@Test
	void stopGameTestAI() {
		this.testSubjectVSAI.stopGame();
		assertFalse(this.testSubjectVSAI.isGameRunning());
	}
	
	/****************************************************************
	 * Test Unit for getCurrentPlayer().
	 * After being instantiated, a Game objects should
	 * set the current player to the first one, or the "White" one.
	 * This test checks whether it is true or not.
	 ***************************************************************/
	@Test
	void getCurrentPlayerTest() {
		String testName = "Test";
		Player testPlayer = new Player(testName, ColorStatus.WHITE);
		Player gamePlayer = this.testSubjectVSPlayer.getCurrentPlayer();
		
		//Make sure that the name of the player is similar.
		//We are interested mostly in testing the ColorStatus.
		gamePlayer.setName(testName);
		assertEquals(testPlayer.getName(), gamePlayer.getName());
		assertEquals(testPlayer.playerColor(), gamePlayer.playerColor());
	}
	
	/****************************************************************
	 * Test Unit for getCurrentPlayer() against AI.
	 * After being instantiated, a Game objects should
	 * set the current player to the first one, or the "White" one.
	 * This test checks whether it is true or not.
	 ***************************************************************/
	@Test
	void getCurrentPlayerTestAI() {
		String testName = "Test";
		Player testPlayer = new Player(testName, ColorStatus.WHITE);
		Player gamePlayer = this.testSubjectVSAI.getCurrentPlayer();
		
		//Make sure that the name of the player is similar.
		//We are interested mostly in testing the ColorStatus.
		gamePlayer.setName(testName);
		assertEquals(testPlayer.getName(), gamePlayer.getName());
		assertEquals(testPlayer.playerColor(), gamePlayer.playerColor());
	}
	
	/****************************************************************
	 * Test Unit for nextPlayer().
	 * After being instantiated, the Game should
	 * set the current player to the "White" player.
	 * After a nextPlayer() call, the current player
	 * should be the "Black".
	 * The unit will check whether that is true or not,
	 * by comparing the current "Black" player with a
	 * previously created "Black" player.
	 ****************************************************************/
	@Test
	void nextPlayerTest() {
		String testName = "Test";
		Player testPlayer = new Player(testName, ColorStatus.BLACK);
		this.testSubjectVSPlayer.nextPlayer();
		Player gamePlayer = this.testSubjectVSPlayer.getCurrentPlayer();
		gamePlayer.setName(testName);
		assertEquals(testPlayer.getName(), gamePlayer.getName());
		assertEquals(testPlayer.playerColor(), gamePlayer.playerColor());
	}
	
	/****************************************************************
	 * Test Unit for nextPlayer() against AI.
	 * After being instantiated, the Game should
	 * set the current player to the "White" player.
	 * After a nextPlayer() call, the current player
	 * should be the AI "Black".
	 * The unit will check whether that is true or not,
	 * by comparing the current "Black" AI with a
	 * previously created "Black" AI.
	 ****************************************************************/
	@Test
	void nextPlayerTestAI() {
		String testName = "Test";
		CheckersAI testAI = new CheckersAI(ColorStatus.BLACK);
		this.testSubjectVSAI.nextPlayer();
		CheckersAI gameAI = (CheckersAI) this.testSubjectVSAI.getCurrentPlayer();
		gameAI.setName(testName);
		testAI.setName(testName);
		assertEquals(testAI.getName(), gameAI.getName());
		assertEquals(testAI.playerColor(), gameAI.playerColor());
	}
	
}
