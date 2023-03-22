package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class PowerupTest extends DukeApplicationTest {

  private final String[] allLevelPaths = {"testlevel.txt"};
  private final Breakout breakoutGame = new Breakout();
  private Ball currentBall;

  public void start(Stage stage) {
    breakoutGame.initializeLevels(allLevelPaths);
    Scene myScene = breakoutGame
        .initializeScene(Breakout.SIZE, Breakout.SIZE, Breakout.BACKGROUND);
    stage.setScene(myScene);
    stage.show();
    currentBall = lookup("#ball").query();
  }

  @Test
  public void testExtraLifePowerUp() {
    currentBall.setXPosition(200);
    currentBall.setYPosition(115);
    currentBall.setXVelocity(0);
    currentBall.setYVelocity(-100);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    breakoutGame.getCurrentLevelObject().getAllPowerUps().get(0).setYPosition(Breakout.SIZE - 15);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    assertEquals(4, breakoutGame.getCurrentLevelObject().getNumberOfLives());
  }
}
