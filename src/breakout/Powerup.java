package breakout;

import javafx.scene.paint.Color;

/**
 * Superclass for power-up which all power ups extend
 *
 * @author Edem Ahorlu
 * @author Christopher Shin
 */

abstract class Powerup extends Ball {

  public static final Color POWERUP_COLOR = Color.DARKCYAN;
  public static final int POWERUP_FALL_SPEED_Y = 50;
  public static final int POWERUP_FALL_SPEED_X = 0;


  public Powerup(double powerupXCoordinate, double powerupYCoordinate) {
    super(powerupXCoordinate, powerupYCoordinate);
    setFill(POWERUP_COLOR);
  }

  /**
   * responsible for making the power up move from when it is dropped to the
   * bottom of the screen
   * @param elapsedTime time elapsed at step.
   */




  public void updatePosition(double elapsedTime) {
    setCenterY(this.getCenterY() + POWERUP_FALL_SPEED_Y * elapsedTime);
  }

  @Override
  public int getYVelocity() {
    return POWERUP_FALL_SPEED_Y;
  }

  @Override
  public int getXVelocity() {
    return POWERUP_FALL_SPEED_X;
  }

  /**
   * checks to see if user collected power up with paddle
   * @param paddle
   * @return True if power up hits paddle and False if otherwise
   */

  boolean hitPaddle(Paddle paddle) {
    return paddle.objectHitThisBlock(this);
  }

  /**
   * activates the power up or let's it drop from block
   * @param levelObject object of the Level class
   */

  abstract void activatePowerup(Level levelObject);
}
