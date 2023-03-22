package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests for Level Class. Based off labracers test class
 *
 * @author Christopher Shin
 * @author Edem Ahorlu
 */
public class LevelTest extends DukeApplicationTest {

  private final String[] allLevelPaths = {"testlevel.txt"};
  private final Breakout breakoutGame = new Breakout();
  private Scene myScene;
  private Paddle myPaddle;
  private Ball myBall;

  public void start(Stage stage) {
    breakoutGame.initializeLevels(allLevelPaths);
    myScene = breakoutGame
        .initializeScene(breakoutGame.SIZE, breakoutGame.SIZE, breakoutGame.BACKGROUND);
    stage.setScene(myScene);
    stage.show();

    myPaddle = lookup("#paddle").query();
    myBall = lookup("#ball").query();
  }

  /**
   * based off labracers test
   */
  @Test
  public void testInitialPositions() {
    assertEquals(200, myBall.getCenterX());
    assertEquals(383, myBall.getCenterY());
    assertEquals(7, myBall.getRadius());

    assertEquals(150, myPaddle.getX());
    assertEquals(390, myPaddle.getY());
    assertEquals(100, myPaddle.getWidth());
    assertEquals(10, myPaddle.getHeight());

    assertEquals(50, breakoutGame.getLevelConfiguration().getBlock(0, 0).getY());
    assertEquals(0, breakoutGame.getLevelConfiguration().getBlock(0, 0).getX());
    assertEquals(80, breakoutGame.getLevelConfiguration().getBlock(1, 1).getX());
    assertEquals(70, breakoutGame.getLevelConfiguration().getBlock(1, 1).getY());
  }
}
