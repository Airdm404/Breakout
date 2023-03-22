package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests for Breakout Class. Based off labracers test class
 *
 * @author Christopher Shin
 * @author Edem Ahorlu
 */
public class BreakoutTest extends DukeApplicationTest {

  private final String[] allLevelPaths = {"testlevel.txt", "level1.txt", "level2.txt", "level3.txt"};
  private final Breakout breakoutGame = new Breakout();
  private Scene myScene;
  private Ball myBall;

  public void start(Stage stage) {
    breakoutGame.initializeLevels(allLevelPaths);
    myScene = breakoutGame
        .initializeScene(Breakout.SIZE, Breakout.SIZE, Breakout.BACKGROUND);
    stage.setScene(myScene);
    stage.show();

    myBall = lookup("#ball").query();
  }

  @Test
  public void testReset() {
    press(myScene, KeyCode.RIGHT);
    press(myScene, KeyCode.R);
    assertEquals(200, myBall.getCenterX());
    assertEquals(383, myBall.getCenterY());
    assertEquals(7, myBall.getRadius());
    assertTrue(300 >= myBall.getXVelocity());
    assertTrue(-300 <= myBall.getXVelocity());
    assertEquals(-300, myBall.getYVelocity());

    assertEquals(150, breakoutGame.getCurrentLevelObject().getMainPaddle().getX());
    assertEquals(390, breakoutGame.getCurrentLevelObject().getMainPaddle().getY());
    assertEquals(100, breakoutGame.getCurrentLevelObject().getMainPaddle().getWidth());
    assertEquals(10, breakoutGame.getCurrentLevelObject().getMainPaddle().getHeight());
  }

  @Test
  public void testLevel2() {
    press(myScene, KeyCode.S);
    press(myScene, KeyCode.S);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    myBall = lookup("#ball").query();
    myBall.setXPosition(200);
    myBall.setYPosition(97);
    myBall.setXVelocity(0);
    myBall.setYVelocity(-100);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    assertEquals(-100,myBall.getYVelocity());
  }
}
