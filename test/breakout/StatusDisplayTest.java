package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class StatusDisplayTest extends DukeApplicationTest {
  private final String[] allLevelPaths = {"testlevel.txt", "level1.txt"};
  private final Breakout breakoutGame = new Breakout();
  private Scene myScene;
  private Ball myBall;
  private Text livesMessage;
  private Text scoreMessage;
  private Text levelMessage;
  private Text highScoreMessage;

  public void start(Stage stage) {
    breakoutGame.initializeLevels(allLevelPaths);
    myScene = breakoutGame
        .initializeScene(Breakout.SIZE, Breakout.SIZE, Breakout.BACKGROUND);
    stage.setScene(myScene);
    stage.show();

    myBall = lookup("#ball").query();
    livesMessage = lookup("#lives").query();
    scoreMessage = lookup("#score").query();
    levelMessage = lookup("#level").query();
    highScoreMessage = lookup("#highScore").query();
  }

  @Test
  public void TestInitialValues() {
    assertEquals("0", scoreMessage.getText().replaceAll("[^\\d.]", ""));
    assertEquals("0", highScoreMessage.getText().replaceAll("[^\\d.]", ""));
    assertEquals("3", livesMessage.getText().replaceAll("[^\\d.]", ""));
    assertEquals("1", levelMessage.getText().replaceAll("[^\\d.]", ""));
  }

  @Test
  public void TestHiScoreAndScoreIncrease() {
    myBall.setXPosition(200);
    myBall.setYPosition(115);
    myBall.setXVelocity(0);
    myBall.setYVelocity(-100);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    assertEquals("5", scoreMessage.getText().replaceAll("[^\\d.]", ""));
    assertEquals("5", highScoreMessage.getText().replaceAll("[^\\d.]", ""));
    assertEquals("3", livesMessage.getText().replaceAll("[^\\d.]", ""));
    assertEquals("1", levelMessage.getText().replaceAll("[^\\d.]", ""));
  }

  @Test
  public void TestHiScoreAndScoreIncreaseAndLifeDecrease() {
    myBall.setXPosition(200);
    myBall.setYPosition(115);
    myBall.setXVelocity(0);
    myBall.setYVelocity(-100);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    myBall.setXPosition(50);
    myBall.setYPosition(Breakout.SIZE - 5);
    myBall.setXVelocity(50);
    myBall.setYVelocity(100);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    assertEquals("5", scoreMessage.getText().replaceAll("[^\\d.]", ""));
    assertEquals("5", highScoreMessage.getText().replaceAll("[^\\d.]", ""));
    assertEquals("2", livesMessage.getText().replaceAll("[^\\d.]", ""));
    assertEquals("1", levelMessage.getText().replaceAll("[^\\d.]", ""));
  }

  @Test
  public void TestIncreaseScoreLoseLifeGoToNextLevel() {
    myBall.setXPosition(200);
    myBall.setYPosition(115);
    myBall.setXVelocity(0);
    myBall.setYVelocity(-100);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    myBall.setXPosition(50);
    myBall.setYPosition(Breakout.SIZE - 5);
    myBall.setXVelocity(50);
    myBall.setYVelocity(100);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    press(myScene, KeyCode.S);
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    javafxRun(() -> breakoutGame.step(Breakout.SECOND_DELAY));
    sleep(1000);
    assertEquals("5", scoreMessage.getText().replaceAll("[^\\d.]", ""));
    assertEquals("5", highScoreMessage.getText().replaceAll("[^\\d.]", ""));
    assertEquals("3", livesMessage.getText().replaceAll("[^\\d.]", ""));
    levelMessage = lookup("#level").query();
    assertEquals("2", levelMessage.getText().replaceAll("[^\\d.]", ""));
  }

}
