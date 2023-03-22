package breakout;


/**
 * creates the EndGame button which allows the user to end the game
 * after finishing the game
 *
 * @author Edem Ahorlu
 * @author Christopher Shin
 *
 */





public class EndGameButton extends SplashScreenButton {

  public EndGameButton(String displayText, int translateX, int translateY,
      Breakout currentBreakoutGame) {
    super(displayText, translateX, translateY, currentBreakoutGame);
  }



  /**
   * ends/exit the game when the button is clicked on
   * @param previousLives number of lives from previous level
   * @param previousScore score from previous level
   */

  protected void setMouseAction(int previousLives, int previousScore) {
    this.setOnMouseClicked(e -> {
      currentBreakoutGame.endGame();
    });
  }
}
