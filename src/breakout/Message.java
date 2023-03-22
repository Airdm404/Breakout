package breakout;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * class that defines a display status message for use in a scene.
 *
 * @author Christopher Shin
 * @author Edem Ahorlu
 */

public class Message extends Text {

  private static final int STROKE_WIDTH = 5;
  private static final int FONT_SIZE = 20;

  /**
   * constructor for a message.
   * @param message text to be displayed
   * @param xLocation x location of message to be displayed
   * @param yLocation y location of message to be displayed
   * @param fillColor color of message to be displayed
   */

  public Message(String message, int xLocation, int yLocation, Color fillColor) {
    super(xLocation, yLocation, message);
    this.setFill(fillColor);
    this.setStrokeWidth(STROKE_WIDTH);
    this.setFontSmoothingType(FontSmoothingType.LCD);
    this.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.ITALIC, FONT_SIZE));
  }
}
