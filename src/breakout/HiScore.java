package breakout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * HiScore class to handle high score file reading and writing.
 *
 * @author Christopher Shin
 * @author Edem Ahorlu
 */

public class HiScore {

  private int highScore;
  private String highScoreFileString;

  /**
   * constructor for HiScore.
   *
   * @param highScoreFileString file name that holds high score
   */

  public HiScore(String highScoreFileString) {
    this.highScoreFileString = highScoreFileString;
    readHighScoreFile();
  }

  /**
   * reads high score file
   * @return high score read from file
   */

  int readHighScoreFile() {
    InputStream highScoreFile = Breakout.class.getClassLoader()
        .getResourceAsStream(highScoreFileString);
    try {
      BufferedReader fileReader = new BufferedReader(new InputStreamReader(highScoreFile));

      highScore = Integer.parseInt(fileReader.readLine());
    } catch (Exception e) {
      highScore = 0;
    }
    return highScore;
  }

  int getHighScore() {
    return highScore;
  }

  /**
   * writes high score back to the high score file
   */

  void setHighScore() {
    try {
      Path highScorePath = Paths
          .get(Breakout.class.getClassLoader().getResource(highScoreFileString).toURI());
      BufferedWriter writer = new BufferedWriter(
          new FileWriter(highScorePath.toAbsolutePath().toString()));
      writer.write(Integer.toString(highScore));
      writer.close();
    } catch (IOException | URISyntaxException e) {
      System.out.println("File doesn't exist");
    }
  }
}
