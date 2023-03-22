package breakout;

/**
 * creates the NextLevel button which allows the user to proceed to the next level
 * after completing the current level
 *
 * @author Edem Ahorlu
 * @author Christopher Shin
 *
 */



public class NextLevelButton extends SplashScreenButton {

  public NextLevelButton(String displayText, int translateX, int translateY,
      Breakout currentBreakoutGame) {
    super(displayText, translateX, translateY, currentBreakoutGame);
  }




  /**
   * activates the next level of the game and also adds the score and lives
   * from the previous level to the current level
   * @param previousLives number of lives from previous level
   * @param previousScore score from previous level
   */

  protected void setMouseAction(int previousLives, int previousScore) {
    this.setOnMouseClicked(e -> {
      currentBreakoutGame.toNextLevel();
      currentBreakoutGame.pauseGame();
      Level currentLevelObject = currentBreakoutGame.getCurrentLevelObject();
      currentLevelObject.setNumberOfLives(previousLives);
      currentLevelObject.getLevelConfiguration().setScoreKeeper(previousScore);
    });
  }
}
