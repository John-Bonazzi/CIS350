package engine;

public interface Memory {

  /** The pictures supported by the game **/
  private enum Picture {
  	EMPTY_SQUARE, FILLED_SQUARE, EMPTY_CIRCLE, FILLED_CIRCLE, EMPTY_TRIANGLE, FILLED_TRIANGLE
  }

  /** A tile on the grid can either be visible or not. **/
  private enum Visibility{
    VISIBLE, HIDDEN
  }

  /*****************************************************************************
  Default construct for the Memory class.
  @param num_pictures tells the program how many pictures to use.
    The number of elements in the game will be multiplied by 2, to account
    for the pairs.
  *****************************************************************************/
  public Memory(int num_pictures);

  /*****************************************************************************
  Shuffles the array of pictures, and saves in the array of integers the index
  of the other element of the pair. One element will be the index of pairs[],
  the other element will be the int value inside pairs[].
  @param memoryList an array containing a list of all the pictures in the game
  @param pairs an empty array to be updated with the pairs.
  *****************************************************************************/
  private void makePairs(Picture[] memoryList, int[] pairs);

  /*****************************************************************************
  If the two pictures are the same, their values in Visibility is set to
  VISIBLE.
  *****************************************************************************/
  public boolean isPair(Picture first, Picture second, Visibility[] list);

  /*****************************************************************************
  Takes the list of pictures and assign them a position that will be showed on
  the grid.
  This method will probably take the place of makePairs.
  *****************************************************************************/
  private void setPositions(Picture[] memoryList, int[] position);
}
