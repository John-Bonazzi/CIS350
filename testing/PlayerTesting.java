package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import prototype.ColorStatus;
import prototype.Player;

/******************************************************************************
 * Testing Unit for the Player class.
 * The assumption is that if all test cases
 * are passed, the constructor is also tested correctly.
 * @author Gionata Bonazzi
 * @author Cole Sellers
 * @author Brendan Cronan
 * @author Rosa Fleming
 * @version 19 April 2018
 *****************************************************************************/
class PlayerTesting {

	/** A Player instance that will be used for testing. **/
	private Player testSubject = new Player("Test", ColorStatus.WHITE);;
	
	/** The name used for comparison tests. **/
	private String testName = "Test";
	
	/** Player color used for comparison tests. **/
	private ColorStatus testPlayerColor = ColorStatus.WHITE;
	
	/** Player's king color used for comparison tests. **/
	private ColorStatus testKingColor = ColorStatus.WHITE_KING;

	
	/**********************************************************************
	 * Test Unit to test the getName() method.
	 * It will compare the name obtained by using the
	 * getName() method, and the one saved as a field
	 * variable in this JUnit, testing for equality. 
	 **********************************************************************/
	@Test
	void getNameTest() {
		String playerName = this.testSubject.getName();
		assertEquals(this.testName, playerName);
	}
	
	/**********************************************************************
	 * Test Unit to test the setName() method.
	 * It will compare the name obtained by using the
	 * getName() method, called after the setName(), 
	 * and the one defined on the method's first line, testing
	 * for equality.
	 * By testing for equality this way, we know
	 * that the setName() method is not changing the
	 * parameter in any way.
	 * Assumption: getName() has been tested before this
	 * test unit.
	 **********************************************************************/
	@Test
	void setNameTest() {
		String newTestName = "newName";
		this.testSubject.setName(newTestName);
		assertEquals(newTestName, this.testSubject.getName());
	}
	
	/**********************************************************************
	 * Test Unit to test the playerColor() method.
	 * It will compare the ColorStatus obtained by using the
	 * playerColor() method, and the one saved as a field
	 * variable in this JUnit, testing for equality.
	 **********************************************************************/
	@Test
	void playerColorTest() {
		ColorStatus playerColor = this.testSubject.playerColor();
		assertEquals(this.testPlayerColor, playerColor);
	}
	
	/**********************************************************************
	 * Test Unit to test the kingColorTest() method.
	 * It will compare the ColorStatus obtained by using the
	 * kingColor() method, and the one saved as a field
	 * variable in this JUnit, testing for equality.
	 **********************************************************************/
	@Test
	void kingColorTest() {
		ColorStatus kingColor = this.testSubject.kingColor();
		assertEquals(this.testKingColor, kingColor);
	}

}
