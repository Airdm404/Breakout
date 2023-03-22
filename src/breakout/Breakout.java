package breakout;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Breakout game.  This class is the main class of the program and handles the game itself.
 *
 * @author Christopher Shin
 * @author Edem Ahorlu
 */
public class Breakout extends Application {

  public static final String TITLE = "Breakout JavaFX";

  public static final int FRAMES_PER_SECOND = 120;
  private final int LEVEL_LIVES_COUNTER_X_POSITION = 0;
  private final int LEVEL_HIGH_SCORE_COUNTER_Y_POSITION = 20;
  private final int LIVES_SCORE_Y_COUNTER_POSITION = 45;
  private final int HIGH_SCORE_COUNTER_OFFSET = 180;
  private final int SCORE_COUNTER_OFFSET = 190;


  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final int SIZE = 400;
  public static final int BUTTON_TRANSLATE_X = SIZE / 4 + 20;
  public static final int BUTTON_TRANSLATE_Y = SIZE / 2;
  private final int END_GAME_BUTTON_TRANSLATE_X = BUTTON_TRANSLATE_X + 10;
  private final int END_GAME_BUTTON_TRANSLATE_Y = BUTTON_TRANSLATE_Y + 50;

  public static final Paint BACKGROUND = Color.AZURE;
  private int previousLives;
  private int previousScore;

  private Stage stage;
  private Scene scene;
  private Timeline animation;
  private boolean PAUSED = false;
  private static final String[] ALL_LEVEL_PATHS = {"level1.txt", "level2.txt", "level3.txt"};
  private static final String HIGH_SCORE_TXT = "highScore.txt";
  private List<Level> allLevels;
  private int currentLevel = 0;
  private Level currentLevelObject;
  private Text levelCounter;
  private Text highScoreCounter;
  private Text livesCounter;
  private Text scoreCounter;
  private int highScore;
  private boolean isHighScore = false;
  private Map<KeyCode, Integer> digitMap;
  private HiScore highScoreHandler;


  /**
   * starts the game inspired by labracers
   *
   * @param stage stage used to start the game
   */
  @Override
  public void start(Stage stage) {
    this.stage = stage;
    highScoreHandler = new HiScore(HIGH_SCORE_TXT);
    highScore = highScoreHandler.getHighScore();
    initializeLevels(ALL_LEVEL_PATHS);
    System.out.println(allLevels.size());
    if (allLevels.size() == 0) {
      stage.close();
      return;
    }
    digitMap = makeDigitMap();
    Scene myScene = initializeScene(SIZE, SIZE, BACKGROUND);
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();
    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }


  /**
   * initializes javafx scene inspired by labracers
   *
   * @param width      width of the game's scene
   * @param height     height of the game's scene
   * @param background background color of the scene
   * @return initialized scene
   */
  Scene initializeScene(int width, int height, Paint background) {
    scene = new Scene(new Group(), width, height, background);
    startLevel(currentLevel);
    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    scene.setOnMouseMoved(e -> handleMouseInput(e.getX()));
    return scene;
  }

  /**
   *
   * Starts a specified level
   *
   * @param levelToStart level to start game
   */

  void startLevel(int levelToStart) {
    try {
      currentLevelObject = allLevels.get(levelToStart);
      Group nextRoot = currentLevelObject.getRoot();
      scene.setRoot(nextRoot);
      showHighScoreCounter();
      makeLevelCounter();
      showLivesCounter();
      showScoreCounter();
    } catch (Exception e) {
      System.out.println("Could not start level");
    }
  }

  /**
   * initialize all levels upon start of game.  Stores level objects in list for access later.
   *
   * @param allLevelPaths array holding text file names that represent levels.
   */

  void initializeLevels(String[] allLevelPaths) {
    allLevels = new ArrayList<>();
    for (String currentLevelPath : allLevelPaths) {
      InputStream levelStream = Breakout.class.getClassLoader()
          .getResourceAsStream(currentLevelPath);
      if (levelStream != null) {
        Level levelToAdd = new Level(levelStream);
        allLevels.add(levelToAdd);
      }
    }
  }

  void resetLevel() {
    initializeLevels(ALL_LEVEL_PATHS);
  }

  /**
   * step function that handles the game after each step.  Updates the shapes, display variables,
   * and checks if the game is won/lost.
   *
   * @param elapsedTime time elapsed between steps
   */
  void step(double elapsedTime) {
    currentLevelObject.updateShapes(elapsedTime);
    livesCounter.setText("Lives: " + currentLevelObject.getNumberOfLives());
    scoreCounter.setText("Game Score: " + currentLevelObject.getLevelConfiguration().keepScore());
    levelCounter.setText("Level: " + (getCurrentlevel() + 1));
    if (currentLevelObject.numberOfBallsLeft() == 0) {
      currentLevelObject.decrementLives();
      currentLevelObject.resetPaddleAndBall();
    }

    if (currentLevelObject.getLevelConfiguration().keepScore() > highScore) {
      isHighScore = true;
      highScore = currentLevelObject.getLevelConfiguration().keepScore();
      highScoreCounter.setText("High Score: " + highScore);
    }
    checkLevelWon();
    checkGameLost();
  }

  private void checkLevelWon() {
    if (currentLevelObject.getLevelConfiguration().getNumberOfBlocksLeft() == 0) {
      currentLevelObject.getRoot().getChildren().clear();
      currentLevelObject.displayWon();
      previousLives += currentLevelObject.getNumberOfLives();
      previousScore = currentLevelObject.getLevelConfiguration().keepScore();
      pauseGame();
      if (currentLevel < ALL_LEVEL_PATHS.length - 1) {
        createNextLevelButton();
      } else {
        createEndGameButton();
        createPlayAgainButton();
      }
      if (isHighScore) {
        highScoreHandler.setHighScore();
      }
    }
  }

  private void checkGameLost() {
    if (currentLevelObject.getNumberOfLives() == 0) {
      previousLives = 0;
      previousScore = 0;
      currentLevelObject.getRoot().getChildren().clear();
      currentLevelObject.displayLoss();
      pauseGame();
      createRestartButton();
      if (isHighScore) {
        highScoreHandler.setHighScore();
      }
    }
  }


  /**
   * handles all keyboard input inspired by labracers
   *
   * @param code keyboard input
   */
  private void handleKeyInput(KeyCode code) {
    switch (code) {
      case LEFT -> currentLevelObject.getMainPaddle().updatePositionKeys(KeyCode.LEFT, PAUSED);
      case RIGHT -> currentLevelObject.getMainPaddle().updatePositionKeys(KeyCode.RIGHT, PAUSED);
      case R -> resetGame();
      case SPACE -> pauseGame();
      case S -> toNextLevel();
      case B -> currentLevelObject.addBall();
      case L -> currentLevelObject.increaseLivesCheatCode();
      case P -> currentLevelObject.addPowerUp();
      case T -> currentLevelObject.decrementLives();
      case D -> currentLevelObject.getLevelConfiguration().destroyFirstBlock();
      case K -> currentLevelObject.getAllBalls().get(0).addOnePowerToBall();
    }
    if (code.isDigitKey()) {
      toSpecificLevel(code);
    }
  }

  /**
   * handles all mouse input
   *
   * @param x x coordinate of mouse
   */
  private void handleMouseInput(double x) {
    if (x > (double) currentLevelObject.getMainPaddle().getPaddleWidth() / 2
        && x < SIZE - (double) currentLevelObject.getMainPaddle().getPaddleWidth() / 2 && !PAUSED) {
      currentLevelObject.getMainPaddle().updatePositionMouse(x);
    }
  }

  /**
   * Pauses game when Cheat Key SPACE is pressed
   */
  void pauseGame() {
    if (!PAUSED) {
      animation.pause();
    } else {
      animation.play();
    }
    PAUSED = !PAUSED;
  }

  private void createRestartButton() {
    SplashScreenButton restartButton = new GameLostButton("TRY AGAIN", BUTTON_TRANSLATE_X,
        BUTTON_TRANSLATE_Y, this);
    restartButton.setMouseAction(previousLives, previousScore);
    currentLevelObject.getRoot().getChildren().add(restartButton);
  }

  private void createPlayAgainButton() {
    SplashScreenButton playAgainButton = new GameLostButton("PLAY AGAIN", BUTTON_TRANSLATE_X,
        BUTTON_TRANSLATE_Y, this);
    playAgainButton.setMouseAction(previousLives, previousScore);
    currentLevelObject.getRoot().getChildren().add(playAgainButton);
  }


  private void createNextLevelButton() {
    SplashScreenButton nextLevelButton = new NextLevelButton("NEXT LEVEL", BUTTON_TRANSLATE_X,
        BUTTON_TRANSLATE_Y, this);
    nextLevelButton.setMouseAction(previousLives, previousScore);
    currentLevelObject.getRoot().getChildren().add(nextLevelButton);
  }

  private void createEndGameButton() {
    SplashScreenButton endGameButton = new EndGameButton("END GAME", END_GAME_BUTTON_TRANSLATE_X,
        END_GAME_BUTTON_TRANSLATE_Y, this);
    endGameButton.setMouseAction(previousLives, previousScore);
    currentLevelObject.getRoot().getChildren().add(endGameButton);
  }

  private void makeLevelCounter() {
    levelCounter = new Message("Level: " + (getCurrentlevel() + 1), LEVEL_LIVES_COUNTER_X_POSITION,
        LEVEL_HIGH_SCORE_COUNTER_Y_POSITION, Color.AQUA);
    levelCounter.setId("level");
    currentLevelObject.getRoot().getChildren().add(levelCounter);
  }

  private void showHighScoreCounter() {
    highScoreCounter = new Message("High Score: " + highScore, SIZE - HIGH_SCORE_COUNTER_OFFSET,
        LEVEL_HIGH_SCORE_COUNTER_Y_POSITION, Color.AQUA);
    highScoreCounter.setId("highScore");
    currentLevelObject.getRoot().getChildren().add(highScoreCounter);
  }

  private void showLivesCounter() {
    livesCounter = new Message("Lives: " + currentLevelObject.getNumberOfLives(),
        LEVEL_LIVES_COUNTER_X_POSITION, LIVES_SCORE_Y_COUNTER_POSITION, Color.AQUA);
    livesCounter.setId("lives");
    currentLevelObject.getRoot().getChildren().add(livesCounter);
  }

  private void showScoreCounter() {
    scoreCounter = new Message(
        "Game Score: " + currentLevelObject.getLevelConfiguration().keepScore(),
        SIZE - SCORE_COUNTER_OFFSET, LIVES_SCORE_Y_COUNTER_POSITION, Color.AQUA);
    scoreCounter.setId("score");
    currentLevelObject.getRoot().getChildren().add(scoreCounter);
  }


  /**
   * resets position of ball and paddle when Cheat Key R is pressed
   */
  private void setCurrentLevel() {
    resetLevel();
    startLevel(currentLevel);
  }

  /**
   * resets game to first level
   */

  void resetGame() {
    currentLevel = 0;
    setCurrentLevel();
  }

  /**
   * sets level to next level
   */

  void toNextLevel() {
    if (currentLevel < allLevels.size()) {
      currentLevel++;
      setCurrentLevel();
    }
  }

  /**
   * sets level to a specific level based on the DIGIT cheatkey keyCode
   * @param levelCode digit pressed on keyboard
   */

  void toSpecificLevel(KeyCode levelCode) {
    if (!digitMap.containsKey(levelCode)) {
      return;
    }
    int level = digitMap.get(levelCode);
    if (level < ALL_LEVEL_PATHS.length) {
      currentLevel = level;
      setCurrentLevel();
    }
  }

  /**
   * makes a map that holds the level to jump to upon pressing a certain digit on the keyboard
   * @return map holding digit-level pairings.
   */

  Map<KeyCode, Integer> makeDigitMap() {
    Map<KeyCode, Integer> digitMap = new HashMap<>();
    digitMap.put(KeyCode.DIGIT1, 0);
    digitMap.put(KeyCode.DIGIT2, 1);
    digitMap.put(KeyCode.DIGIT3, 2);
    return digitMap;
  }

  private int getCurrentlevel() {
    return currentLevel;
  }

  public BlockConfig getLevelConfiguration() {
    return currentLevelObject.getLevelConfiguration();
  }

  public Level getCurrentLevelObject() {
    return currentLevelObject;
  }

  /**
   * stops animation and closes stage to end game.
   */

  void endGame() {
    animation.stop();
    stage.close();
  }


  /**
   * main
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

}

