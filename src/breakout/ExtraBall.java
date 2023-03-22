package breakout;

/**
 * creates the extra ball power-up which increases the number of balls by 1
 *
 * @author Edem Ahorlu
 * @author Christopher Shin
 */

public class ExtraBall extends Powerup {

  public ExtraBall(double powerupXCoordinate, double powerupYCoordinate) {
    super(powerupXCoordinate, powerupYCoordinate);
  }


  /**
   * responsible for activating the ExtraBall power-up or making it drop from a block
   * @param levelObject object of the Level class
   */

  @Override
  void activatePowerup(Level levelObject) {
    levelObject.addBall();
  }
}
