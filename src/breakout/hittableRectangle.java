package breakout;

import javafx.scene.shape.Rectangle;

/**
 * Superclass of block and paddle, used to share hit detection method.
 *
 * @author Christopher Shin
 * @author Edem Ahorlu
 */

public class hittableRectangle extends Rectangle {

  private final boolean isBreakableBlock;
  private boolean isBroken;

  public hittableRectangle(int xCoordinate, int yCoordinate, int width, int height,
      boolean isBreakableBlock) {
    super(xCoordinate, yCoordinate, width, height);
    this.isBreakableBlock = isBreakableBlock;
  }

  /**
   * Object hit detection between ball and block, calculates the side lengths calculated by the triangle
   * formed by the closest point of the rectangle and the center of the ball.
   * <p>
   * inspired by: https://gamedev.stackexchange.com/questions/95817/breakout-collision-detection-ball-gets-trapped-inside-block
   *
   * @param currentBall ball to check collision with
   * @return true if there is a collision, else false
   */
  public boolean objectHitThisBlock(Ball currentBall) {
    double ballXCoordinate = currentBall.getCenterX();
    double ballYCoordinate = currentBall.getCenterY();
    double xDifference = ballXCoordinate - Math
        .max(this.getX(), Math.min(ballXCoordinate, this.getX() + this.getWidth()));
    double yDifference = ballYCoordinate - Math
        .max(this.getY(), Math.min(ballYCoordinate, this.getY() + this.getHeight()));
    return (!isBroken() && (xDifference * xDifference + yDifference * yDifference) <= (
        currentBall.getRadius() * currentBall.getRadius()));
  }

  public boolean isBroken() {
    return this.isBroken;
  }

  public void setBroken() {
    this.isBroken = true;
  }
}
