package breakout;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Superclass to create buttons
 * @author Edem Ahorlu
 * @author Christopher Shin
 *
 */


public abstract class SplashScreenButton extends Button {

  protected Breakout currentBreakoutGame;

  public SplashScreenButton(String displayText, int translateX, int translateY,
      Breakout currentBreakoutGame) {
    super(displayText);
    this.setTranslateX(translateX);
    this.setTranslateY(translateY);
    this.currentBreakoutGame = currentBreakoutGame;
    this.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
  }



  /**
   * determines what the button do
   * @param previousLives
   * @param previousScore
   */

  protected abstract void setMouseAction(int previousLives, int previousScore);

}