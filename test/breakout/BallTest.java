package breakout;

import java.util.List;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests for Ball Class. Based off labracers test class
 *
 * @author Christopher Shin
 * @author Edem Ahorlu
 */
public class BallTest extends DukeApplicationTest {

  private final String[] allLevelPaths = {"testlevel.txt"};
  private final Breakout breakoutGame = new Breakout();
  private Scene myScene;
  private Paddle myPaddle;
  private Ball myBall;

  public void start(Stage stage) {
    breakoutGame.initializeLevels(allLevelPaths);
    myScene = breakoutGame
        .initializeScene(Breakout.SIZE, Breakout.SIZE, Breakout.BACKGROUND);
    stage.setScene(myScene);
    stage.show();

    myPaddle = lookup("#paddle").query();
    myBall = lookup("#ball").query();
  }

  @Test
  public void testBallBoundaryLeft() {
    myBall.setXPosition(5);
    myBall.setYPosition(200);
    myBall.setXVelocity(-100);
    myBall.setYVelocity(100);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    assertEquals(100, myBall.getXVelocity());
    assertEquals(100, myBall.getYVelocity());
  }

  @Test
  public void testBallBoundaryRight() {
    myBall.setXPosition(Breakout.SIZE - 5);
    myBall.setYPosition(200);
    myBall.setXVelocity(100);
    myBall.setYVelocity(100);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    assertEquals(-100, myBall.getXVelocity());
    assertEquals(100, myBall.getYVelocity());
  }

  @Test
  public void testBallBoundaryBottom() {
    myBall.setYPosition(Breakout.SIZE - 5);
    myBall.setXPosition(300);
    myBall.setXVelocity(100);
    myBall.setYVelocity(100);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    assertEquals(100, myBall.getXVelocity());
    assertEquals(100, myBall.getYVelocity());
    assertEquals(Breakout.SIZE / 2, breakoutGame.getCurrentLevelObject().getAllBalls().get(0).getCenterX());
    assertEquals(383, breakoutGame.getCurrentLevelObject().getAllBalls().get(0).getCenterY());
  }

  @Test
  public void testBallBounceOffBlockCorner() {
    myBall.setXPosition(240);
    myBall.setYPosition(90);
    myBall.setXVelocity(-100);
    myBall.setYVelocity(-100);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    assertEquals(100, myBall.getXVelocity());
    assertEquals(100, myBall.getYVelocity());
  }

  @Test
  public void testBallBounceOffWallCorner() {
    myBall.setXPosition(5);
    myBall.setYPosition(70);
    myBall.setXVelocity(-100);
    myBall.setYVelocity(-100);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    assertEquals(100, myBall.getXVelocity());
    assertEquals(100, myBall.getYVelocity());
  }

  @Test
  public void testBallBounceOffBottomOfBlock() {
    myBall.setXPosition(200);
    myBall.setYPosition(115);
    myBall.setXVelocity(100);
    myBall.setYVelocity(-100);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    assertEquals(100, myBall.getXVelocity());
    assertEquals(100, myBall.getYVelocity());
  }

  @Test
  public void testBallBounceOffBall() {
    press (myScene, KeyCode.B);
    List<Ball> allBallsList = breakoutGame.getCurrentLevelObject().getAllBalls();
    myBall.setXPosition(100);
    myBall.setYPosition(100);
    myBall.setXVelocity(100);
    myBall.setYVelocity(0);
    Ball otherBall = allBallsList.get(1);
    otherBall.setXPosition(115);
    otherBall.setYPosition(100);
    otherBall.setXVelocity(-100);
    otherBall.setYVelocity(0);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    assertEquals(-100, myBall.getXVelocity());
    assertEquals(100, otherBall.getXVelocity());
  }

}
