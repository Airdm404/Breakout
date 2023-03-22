package breakout;

/**
 * creates the double power power-up which increases the damage made to a block x2:
 * 2 hit blocks are instantly destroyed and 3 hit blocks could be destroyed in 2 hits
 *
 * @author Edem Ahorlu
 * @author Christopher Shin
 */

public class DoublePower extends Powerup {

  public DoublePower(double powerupXCoordinate, double powerupYCoordinate) {
    super(powerupXCoordinate, powerupYCoordinate);
  }

  /**
   * responsible for activating the DoublePower power-up or making it drop from a block
   * @param levelObject object of the Level class
   */

  @Override
  void activatePowerup(Level levelObject) {
    levelObject.getAllBalls().get(0).addOnePowerToBall();

  }

}
