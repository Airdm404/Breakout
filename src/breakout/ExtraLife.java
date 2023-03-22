package breakout;

/**
 * creates the extra life power-up which increases the number of lives by 1
 *
 * @author Edem Ahorlu
 * @author Christopher Shin
 */
public class ExtraLife extends Powerup {

  public ExtraLife(double powerupXCoordinate, double powerupYCoordinate) {
    super(powerupXCoordinate, powerupYCoordinate);
  }


  /**
   * responsible for activating the ExtraLife power-up or making it drop from a block
   * @param levelObject object of the Level class
   */


  @Override
  void activatePowerup(Level levelObject) {
    levelObject.increaseLivesCheatCode();
  }
}
