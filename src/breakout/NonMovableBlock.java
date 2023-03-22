package breakout;


/**
 * creates the non-moving block types
 *
 * @author Edem Ahorlu
 * @author Christopher Shin
 */


public class NonMovableBlock extends Block {

  public NonMovableBlock(int row, int column, int hitsToBreak, int powerup, int numberOfColumns,
      int sizeX, int sizeY) {
    super(row, column, hitsToBreak, powerup, numberOfColumns);
  }


  /**
   * method shouldn't have been made abstract and should have only been implemented in the
   * MovableBlock class
   * @param elapsedTime
   */


  protected void updateLocation(double elapsedTime) {
  }

}
