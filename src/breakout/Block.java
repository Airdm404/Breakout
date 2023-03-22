package breakout;

import javafx.scene.paint.Color;

/**
 * Block class
 *
 * This class represents a block in the breakout class. Extends hittable rectangle.
 *
 * @author Christopher Shin
 * @author Edem Ahorlu
 */

public abstract class Block extends hittableRectangle {

  private final int BLOCK_HEIGHT;
  private final int BLOCK_WIDTH;
  private static final int HEIGHT_TO_WIDTH_RATIO = 4;
  private static final int SCORE_TEXT_OFFSET = 50;
  private int hitCount = 0;
  private boolean hasPowerup;
  private Powerup powerupToDrop;


  /**
   * Block constructor for a regular block (one that is to be destroyed)
   *
   * @param row         row in which to add block
   * @param column      column in which to add block
   * @param hitsToBreak number of hits needed to break block
   * @param powerup     input for what powerup, if any, is contained within the block
   * @param numberOfColumns input for how many columns are in the row the block resides in
   */

  public Block(int row, int column, int hitsToBreak, int powerup, int numberOfColumns) {
    super(column * Breakout.SIZE / numberOfColumns,
        (row * Breakout.SIZE / numberOfColumns / HEIGHT_TO_WIDTH_RATIO) + SCORE_TEXT_OFFSET,
        Breakout.SIZE / numberOfColumns, Breakout.SIZE / numberOfColumns / HEIGHT_TO_WIDTH_RATIO,
        hitsToBreak != -1);
    this.setStroke(Color.BLACK);
    this.hitCount = hitsToBreak;
    this.BLOCK_WIDTH = Breakout.SIZE / numberOfColumns;
    this.BLOCK_HEIGHT = BLOCK_WIDTH / 4;
    processPowerup(powerup);
  }

  /**
   * Method to decrement the hit count of a block, given it is greater than 0.  Decrements by the
   * ball power, or however many hits are left if the ball power is greater than the number
   * of hits left.
   *
   * @param ballPower how many hits the ball that hit the blocks registers in one collision.
   */

  public void decrementHitCount(int ballPower) {
    if (this.hitCount > 0) {
      this.hitCount = Math.max(0, this.hitCount - ballPower);
    }
  }

  public void setHitCountToZero() {
    this.hitCount = 0;
  }

  /**
   * Processes the powerup input in constructor.  Assigns powerup to be dropped from the block
   * when it is destroyed.
   *
   * @param powerup value from level configuration file specifying type of powerup to drop
   */

  private void processPowerup(int powerup) {
    hasPowerup = powerup > 0;
    if (hasPowerup) {
      switch (powerup) {
        case 1 -> powerupToDrop = new ExtraBall(this.getCenterX(), this.getCenterY());
        case 2 -> powerupToDrop = new DoublePower(this.getCenterX(), this.getCenterY());
        case 3 -> powerupToDrop = new ExtraLife(this.getCenterX(), this.getCenterY());
      }
    }
  }

  /**
   * Updates the position of the block
   *
   * @param elapsedTime time elapsed per step.
   */

  protected abstract void updateLocation(double elapsedTime);

  public double getCenterX() {
    return this.getX() + this.getWidth() / 2;
  }


  public double getCenterY() {
    return this.getY() + this.getHeight() / 2;
  }


  public double getBottomY() {
    return this.getY() + this.getHeight();
  }

  public boolean hasPowerup() {
    return hasPowerup;
  }

  public Powerup getPowerup() {
    return powerupToDrop;
  }

  public int getHitCount() {
    return hitCount;
  }
}
