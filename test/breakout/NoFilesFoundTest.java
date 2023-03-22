package breakout;

import javafx.scene.Scene;
import javafx.stage.Stage;
import util.DukeApplicationTest;

public class NoFilesFoundTest extends DukeApplicationTest {

  private final String[] allLevelPaths = {"testlevelFAKE.txt"};
  private final Breakout breakoutGame = new Breakout();
  private Scene myScene;

  public void start(Stage stage) {
    breakoutGame.initializeLevels(allLevelPaths);
    myScene = breakoutGame
        .initializeScene(Breakout.SIZE, Breakout.SIZE, Breakout.BACKGROUND);
    stage.setScene(myScene);
    stage.show();
  }
}
