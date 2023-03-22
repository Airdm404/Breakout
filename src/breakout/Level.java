package breakout;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;

/**
 * Level class handles the shapes and interactions between them for a given level.
 *
 * @author Christopher Shin
 * @author Edem Ahorlu
 */

public class Level {

  public static final int PADDLE_SPEED = 30;
  public static final Paint PADDLE_COLOR = Color.AQUA;
  public static final Paint BALL_COLOR = Color.RED;
  public static final int INITIAL_PADDLE_WIDTH = 100;
  private final int MESSAGE_Y_POSITION_OFFSET = 20;
  private final int LOSS_MESSAGE_X_POSITION_OFFSET = 60;
  private final int WON_MESSAGE_X_POSITION_0FFSET = 100;

  private final List<Ball> allBalls = new ArrayList<>();
  private final BlockConfig levelConfiguration;
  private final List<Paddle> allPaddles = new ArrayList<>();
  private final List<Powerup> allPowerUps = new ArrayList<>();
  private Text lossMessage;
  private Text winMessage;
  private Paddle mainPaddle;
  private final Group root = new Group();
  private int numberOfLives = 3;
  private static final Random randomNumberGenerator = new Random();

  /**
   * constructor for level class
   * @param levelStream data stream from block configuration file.
   */

  public Level(InputStream levelStream) {
    addPaddle();
    addBall();
    levelConfiguration = new BlockConfig(levelStream);
    root.getChildren().addAll(levelConfiguration.getBlockList());
    makeLossMessage();
    makeWonMessage();
  }

  /**
   * handles ball movement, collisions, and bounds inspired by labracers
   *
   * @param elapsedTime time elapsed between steps
   */
  void updateShapes(double elapsedTime) {
    updateBalls(elapsedTime);
    updatePowerups(elapsedTime);
    levelConfiguration.updateBlockList(root, allPowerUps, elapsedTime);
  }

  private void updateBalls(double elapsedTime) {
    List<Ball> ballsOutOfBounds = new ArrayList<>();
    for (Ball currentBall : allBalls) {
      currentBall.updatePosition(elapsedTime);
      currentBall.detectCollisionBlock(levelConfiguration.getBlockList());
      currentBall.detectCollisionPaddle(mainPaddle);
      currentBall.detectCollisionWall();
      currentBall.detectCollisionBall(allBalls);
      if (currentBall.detectBallOutOfBounds()) {
        ballsOutOfBounds.add(currentBall);
      }
    }
    handleBallOutOfBounds(ballsOutOfBounds);
  }

  private void updatePowerups(double elapsedTime) {
    for (int i = 0; i < allPowerUps.size(); i++) {
      Powerup currentPowerUp = allPowerUps.get(i);
      currentPowerUp.updatePosition(elapsedTime);
      if (currentPowerUp.hitPaddle(mainPaddle)) {
        root.getChildren().remove(currentPowerUp);
        allPowerUps.remove(currentPowerUp);
        usePowerup(currentPowerUp);
      }
    }
  }

  private void usePowerup(Powerup currentPowerup) {
    currentPowerup.activatePowerup(this);
  }

  private void handleBallOutOfBounds(List<Ball> ballOutOfBoundsList) {
    for (Ball ballToRemove : ballOutOfBoundsList) {
      allBalls.remove(ballToRemove);
      root.getChildren().remove(ballToRemove);
    }
  }

  public int numberOfBallsLeft() {
    return allBalls.size();
  }

  /**
   * adds a ball to the current level.
   */

  public void addBall() {
    double paddleXCoordinate = mainPaddle.getX() + (float) mainPaddle.getPaddleWidth() / 2;
    double paddleYCoordinate = mainPaddle.getY();
    Ball ballToAdd = new Ball(paddleXCoordinate, paddleYCoordinate);
    ballToAdd.setFill(BALL_COLOR);
    ballToAdd.setId("ball");
    allBalls.add(ballToAdd);
    root.getChildren().add(ballToAdd);
  }

  /**
   * adds a powerup to the current level.
   */

  public void addPowerUp() {
    Powerup addedPowerup = new ExtraBall(randomNumberGenerator.nextInt(Breakout.SIZE),
        randomNumberGenerator.nextInt(Breakout.SIZE / 2));
    root.getChildren().add(addedPowerup);
    allPowerUps.add(addedPowerup);
  }

  /**
   * adds a paddle to the current level.
   */

  public void addPaddle() {
    Paddle paddleToAdd = new Paddle(Breakout.SIZE, Breakout.SIZE, PADDLE_SPEED,
        INITIAL_PADDLE_WIDTH);
    paddleToAdd.setFill(PADDLE_COLOR);
    paddleToAdd.setStroke(Color.BLACK);
    paddleToAdd.setId("paddle");
    allPaddles.add(paddleToAdd);
    mainPaddle = paddleToAdd;
    root.getChildren().add(paddleToAdd);
  }

  private void makeLossMessage() {
    lossMessage = new Message("YOU LOSE!!!", Breakout.SIZE / 2 - LOSS_MESSAGE_X_POSITION_OFFSET,
        Breakout.SIZE / 2 - MESSAGE_Y_POSITION_OFFSET, Color.RED);
    lossMessage.setId("lossMessage");
  }

  private void makeWonMessage() {
    winMessage = new Message("CONGRATULATIONS!!!",
        Breakout.SIZE / 2 - WON_MESSAGE_X_POSITION_0FFSET,
        Breakout.SIZE / 2 - MESSAGE_Y_POSITION_OFFSET, Color.BLUE);
    winMessage.setId("winMessage");
  }

  /**
   * displays a loss message whenever player loses
   */

  public void displayLoss() {
    root.getChildren().add(lossMessage);
  }

  /**
   * displays a win message whenever player wins
   */

  public void displayWon() {
    root.getChildren().add(winMessage);
  }

  /**
   * increments lives whenever 'L' is pressed.
   */

  public void increaseLivesCheatCode() {
    numberOfLives++;
  }

  /**
   * sets paddle to the middle of the scene.
   */

  public void resetPaddleAndBall() {
    mainPaddle.setPaddleInMiddle();
    addBall();
  }

  public Paddle getMainPaddle() {
    return mainPaddle;
  }

  public BlockConfig getLevelConfiguration() {
    return levelConfiguration;
  }

  public void decrementLives() {
    numberOfLives--;
  }

  public int getNumberOfLives() {
    return numberOfLives;
  }

  /**
   * sets number of lives to the lives alloted to the level + the number of lives remaining from
   * last level
   *
   * @param previousLevelLife lives remaining at end of last level.
   */
  public void setNumberOfLives(int previousLevelLife) {
    numberOfLives += previousLevelLife;
  }

  List<Powerup> getAllPowerUps() {
    return allPowerUps;
  }


  public Group getRoot() {
    return root;
  }

  public List<Ball> getAllBalls() {
    return allBalls;
  }
}
