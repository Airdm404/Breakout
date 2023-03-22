package breakout;

/**
 * creates the moving block types
 *
 * @author Edem Ahorlu
 * @author Christopher Shin
 */


public class MovableBlock extends Block {

  private int xVelocity = 75;
  private final int SIZE_X;

  public MovableBlock(int row, int column, int hitsToBreak, int powerup, int numberOfColumns,
      int sizeX, int sizeY) {
    super(row, column, hitsToBreak, powerup, numberOfColumns);
    SIZE_X = sizeX;
  }




  /**
   * enables the block to move and sets the boundaries for the moving block
   * @param elapsedTime
   */

  protected void updateLocation(double elapsedTime) {
    this.setX(
        this.getX() + elapsedTime * xVelocity);
    if (this.getX() < 0 || this.getX() + this.getWidth() > SIZE_X) {
      xVelocity *= -1;
    }
  }
}
