package breakout;

import java.util.List;
import java.util.Random;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Ball class
 *
 * This class represents a ball in the breakout class.
 *
 * @author Christopher Shin
 * @author Edem Ahorlu
 */

public class Ball extends Circle {

  private static final int RADIUS = 7;
  private static final int MAGNITUDE = 300;
  private static final int X_VELOCITY_ANGLE = 180;
  private int xVelocity;
  private int yVelocity;
  private int ballPower = 1;


  /**
   * Constructor for the ball class
   *
   * @param ballXCoordinate starting xcoordinate for ball
   * @param ballYCoordinate starting ycoordinate for ball
   */


  public Ball(double ballXCoordinate, double ballYCoordinate) {
    super(ballXCoordinate, ballYCoordinate - RADIUS, RADIUS);
    setInitialVelocities();
  }

  private void setInitialVelocities() {
    Random randomNumGenerator = new Random();
    int theta = randomNumGenerator.nextInt(X_VELOCITY_ANGLE);
    xVelocity = (int) (MAGNITUDE * Math.cos(theta));
    yVelocity = -MAGNITUDE;
  }

  /**
   * updates the position of the ball depending on the current velocity
   *
   * @param elapsedTime time elapsed at step.
   */

  public void updatePosition(double elapsedTime) {
    setCenterX(this.getCenterX() + this.xVelocity * elapsedTime);
    setCenterY(this.getCenterY() + this.yVelocity * elapsedTime);
  }

  /**
   * Checks for detections between the ball and a wall
   */

  void detectCollisionWall() {
    if (this.getCenterX() - RADIUS <= 0 || this.getCenterX() + RADIUS >= Breakout.SIZE) {
      setXVelocity(this.xVelocity * -1);
    }
    if (this.getCenterY() - RADIUS <= 0) {
      setYVelocity(this.yVelocity * -1);
    }
  }

  /**
   * returns true if a ball hits the bottom of the screen, false otherwise.
   *
   * @return boolean indicating whether ball is out of bounds or not.
   */

  boolean detectBallOutOfBounds() {
    return this.getCenterY() + this.getRadius() >= Breakout.SIZE;
  }

  /**
   * Detects a collision between a ball and a block.  Breaks when collision detected so that only
   * one block can be destroyed per time step.
   *
   * @param blockArray array of available blocks in the scene
   */

  void detectCollisionBlock(List<Block> blockArray) {
    for (Block currentBlock : blockArray) {
      if (currentBlock.objectHitThisBlock(this)) {
        currentBlock.decrementHitCount(ballPower);
        this.bounceOffRectangle(currentBlock, this);
        break; //only one block can be destroyed per timestep
      }
    }
  }

  /**
   * Detects a collision between a paddle and a ball
   *
   * @param paddle Paddle to check collision with ball
   */

  void detectCollisionPaddle(Paddle paddle) {
    if (paddle.objectHitThisBlock(this)) {
      this.bounceOffRectangle(paddle, this);
    }
  }

  /**
   * detects a collision between a ball and another ball
   *
   * @param allBalls list of all balls currently on the screen
   */

  void detectCollisionBall(List<Ball> allBalls) {
    for (Ball otherBall : allBalls) {
      if (otherBall == this) {
        continue;
      }
      double xDifference = this.getCenterX() - otherBall.getCenterX();
      double yDifference = this.getCenterY() - otherBall.getCenterY();
      if (Math.pow(xDifference, 2) + Math.pow(yDifference, 2) < Math.pow(2 * this.getRadius(), 2)) {
        bounceOffBall(this, otherBall);
      }
    }
  }

  /**
   * Method for handling cases for collisions between the ball and the block Corner cases are tough,
   * and this article helped a lot in understanding the logic behind where the ball goes after
   * collision
   *
   * @param currentBlock block that ball is bouncing off of
   * @param currentBall  ball that is bouncing off the block
   * @see "https://gamedev.stackexchange.com/questions/95817/breakout-collision-detection-ball-gets-trapped-inside-block"
   */
  private void bounceOffRectangle(Rectangle currentBlock, Ball currentBall) {
    double centerRectangleX = currentBlock.getX() + currentBlock.getWidth() / 2;
    double centerRectangleY = (currentBlock.getY() + currentBlock.getHeight() / 2);
    float xDifference = (float) ((float) Math.abs(centerRectangleX - currentBall.getCenterX())
        - currentBlock.getWidth() / 2);
    float yDifference = (float) ((float) Math.abs(centerRectangleY - currentBall.getCenterY())
        - currentBlock.getHeight() / 2);
    float delta = xDifference - yDifference;
    if (Math.abs(delta) < 0.001) {
      setXVelocity(this.xVelocity * -1);
      setYVelocity(this.yVelocity * -1);
    } else if (delta < 0) {
      setYVelocity(this.yVelocity * -1);
    } else {
      setXVelocity(this.xVelocity * -1);
    }
  }

  /**
   * Elastic collision function for balls, a much trickier problem than with a ball/wall/block
   * collision I use the process outlined on pages 2 and 3 of the link below.  Essentially, this
   * algorithm calculates the new velocities of both balls based on their starting velocities.  I
   * assume for this project that the masses of any two balls will be equal.
   *
   * @param currentBall ball that collided with other ball
   * @param otherBall   ball that collided with current ball
   * @see "https://www.vobarian.com/collisions/2dcollisions2.pdf"
   */

  private void bounceOffBall(Ball currentBall, Ball otherBall) {
    double[] normalVector = new double[]{otherBall.getCenterX() - currentBall.getCenterX(),
        otherBall.getCenterY() - currentBall.getCenterY()};
    double ballLocationMagnitude = Math
        .sqrt(Math.pow(normalVector[0], 2) + Math.pow(normalVector[1], 2));
    double[] unitNormal = new double[]{normalVector[0] / ballLocationMagnitude,
        normalVector[1] / ballLocationMagnitude};
    double[] unitTangent = new double[]{-1 * unitNormal[1], unitNormal[0]};
    int[] velocity1 = new int[]{currentBall.getXVelocity(), currentBall.getYVelocity()};
    int[] velocity2 = new int[]{otherBall.getXVelocity(), otherBall.getYVelocity()};
    double v1Normal = unitNormal[0] * velocity1[0] + unitNormal[1] * velocity1[1];
    double v1Tangent = unitTangent[0] * velocity1[0] + unitTangent[1] * velocity1[1];
    double v2Normal = unitNormal[0] * velocity2[0] + unitNormal[1] * velocity2[1];
    double v2Tangent = unitTangent[0] * velocity2[0] + unitTangent[1] * velocity2[1];
    double[] v1pn = new double[]{v2Normal * unitNormal[0] + v1Tangent * unitTangent[0],
        v2Normal * unitNormal[1] + v1Tangent * unitTangent[1]};
    double[] v2pn = new double[]{v1Normal * unitNormal[0] + v2Tangent * unitTangent[0],
        v1Normal * unitNormal[1] + v2Tangent * unitTangent[1]};
    currentBall.setXVelocity((int) v1pn[0]);
    currentBall.setYVelocity((int) v1pn[1]);
    otherBall.setXVelocity((int) v2pn[0]);
    otherBall.setYVelocity((int) v2pn[1]);
  }

  void addOnePowerToBall() {
    ballPower++;
  }

  /**
   * set x velocity to input
   *
   * @param xVelocity x velocity to change to
   */

  void setXVelocity(int xVelocity) {
    this.xVelocity = xVelocity;
  }

  /**
   * set y velocity to input
   *
   * @param yVelocity y velocity to change to
   */

  void setYVelocity(int yVelocity) {
    this.yVelocity = yVelocity;
  }

  /**
   * set x position to parameter
   *
   * @param xPosition x position to set ball to
   */

  void setXPosition(int xPosition) {
    this.setCenterX(xPosition);
  }

  /**
   * set y position to parameter
   *
   * @param yPosition y position to set ball to
   */

  void setYPosition(int yPosition) {
    this.setCenterY(yPosition);
  }

  /**
   * get y velocity
   *
   * @return y velocity of ball
   */

  int getYVelocity() {
    return this.yVelocity;
  }

  /**
   * get x velocity
   *
   * @return x velocity of ball
   */

  int getXVelocity() {
    return this.xVelocity;
  }
}
