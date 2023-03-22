package breakout;

import javafx.scene.input.KeyCode;

/**
 * Creates the paddle in the game
 *
 * @author Edem Ahorlu
 * @author Christopher Shin
 */

public class Paddle extends hittableRectangle {

  private static final int PADDLE_HEIGHT = 10;
  private final int paddleWidth;
  private final int PADDLE_SPEED;

  public Paddle(int x, int y, int paddleSpeed, int paddleWidth) {
    super(x / 2 - paddleWidth / 2, y - PADDLE_HEIGHT, paddleWidth, PADDLE_HEIGHT, false);
    this.PADDLE_SPEED = paddleSpeed;
    this.paddleWidth = paddleWidth;
  }



  int getPaddleWidth() {
    return paddleWidth;
  }


  void setPaddleInMiddle() {
    this.setX((float) Breakout.SIZE / 2 - (float) this.getPaddleWidth() / 2);
  }

  int getPaddleSpeed() {
    return PADDLE_SPEED;
  }

  /**
   * updates the paddle depending on the position of the mouse
   *
   * @param x position of the mouse
   */

  public void updatePositionMouse(double x) {
    setX(x - (float) this.getPaddleWidth() / 2);
  }

  /**
   * updates the position of the paddle depending on a key press.
   *
   * @param code     key that is pressed
   * @param isPaused checks if game is currently paused
   */

  public void updatePositionKeys(KeyCode code, boolean isPaused) {
    if (!isPaused) {
      if (code == KeyCode.LEFT && this.getX() >= 0) {
        this.setX(this.getX() - Math.min(PADDLE_SPEED, this.getX()));
      } else if (code == KeyCode.RIGHT && this.getX() + this.getPaddleWidth() <= Breakout.SIZE) {
        this.setX(
            this.getX() + (Math
                .min(PADDLE_SPEED, Breakout.SIZE - this.getX() - this.getPaddleWidth())));
      }
    }
  }
}
