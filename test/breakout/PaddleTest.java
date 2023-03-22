package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests for Paddle Class. Based off labracers test class
 *
 * @author Christopher Shin
 * @author Edem Ahorlu
 */
public class PaddleTest extends DukeApplicationTest {

  private final String[] allLevelPaths = {"testlevel.txt"};
  private final Breakout breakoutGame = new Breakout();
  private Scene myScene;
  private Paddle myPaddle;

  public void start(Stage stage) {
    breakoutGame.initializeLevels(allLevelPaths);
    myScene = breakoutGame
        .initializeScene(Breakout.SIZE, Breakout.SIZE, Breakout.BACKGROUND);
    stage.setScene(myScene);
    stage.show();

    myPaddle = lookup("#paddle").query();
  }

  @Test
  public void testPaddleRight() {
    press(myScene, KeyCode.RIGHT);
    assertEquals(150 + myPaddle.getPaddleSpeed(), myPaddle.getX());
  }

  @Test
  public void testPaddleLeft() {
    press(myScene, KeyCode.LEFT);
    assertEquals(150 - myPaddle.getPaddleSpeed(), myPaddle.getX());
  }

  @Test
  public void testBoundaryPaddleLeft() {
    for (int i = 0; i < 20; i++) {
      press(myScene, KeyCode.LEFT);
      assertTrue(myPaddle.getX() >= 0);
    }
  }

  @Test
  public void testBoundaryPaddleRight() {
    for (int i = 0; i < 20; i++) {
      press(myScene, KeyCode.RIGHT);
      assertTrue(myPaddle.getX() + myPaddle.getPaddleWidth() <= breakoutGame.SIZE);
    }
  }
}
