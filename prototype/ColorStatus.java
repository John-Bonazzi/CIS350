package prototype;

/***************************************************************
 * Enumerator for all the state that the board can have. There are 3 big
 * "state": Black: Everything that is owned by the black player BLACK for the
 * normal checkers BLACK_KING for the king checkers. White: Everything that is
 * owned by the white player WHITE for the normal checkers WHITE_KING for the
 * king checkers. Empty: Everything that is "owned" by the board EMPTY an empty
 * space, not owned by the black nor white player.
 *
 * @author Gionata Bonazzi
 * @author Cole Sellers
 * @author Brendan Cronan
 * @author Rosa Fleming
 * @version stable build 14 April 2018
 ***************************************************************/
public enum ColorStatus {
    /** Color options and status for checkers pieces.*/
    EMPTY, WHITE, BLACK, WHITE_KING, BLACK_KING
}
