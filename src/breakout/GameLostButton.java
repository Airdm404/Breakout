package breakout;


/**
 * creates the GameLost button which allows the user to restart
 * the game all over
 *
 * @author Edem Ahorlu
 * @author Christopher Shin
 *
 */




public class GameLostButton extends SplashScreenButton {

  public GameLostButton(String displayText, int translateX, int translateY,
      Breakout currentBreakoutGame) {
    super(displayText, translateX, translateY, currentBreakoutGame);
  }


  /**
   * restarts the game when the button is clicked on
   * @param previousLives number of lives from previous level
   * @param previousScore score from previous level
   */

  protected void setMouseAction(int previousLives, int previousScore) {
    this.setOnMouseClicked(e -> {
      currentBreakoutGame.resetGame();
      currentBreakoutGame.pauseGame();
    });

  }
}
