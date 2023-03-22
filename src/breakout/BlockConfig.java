package breakout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.*;
import javafx.scene.Group;


/**
 * BlockConfig class
 *
 * This class represents a configuration of blocks, to be used by a Level.  Represents the blocks
 * to be broken in the level, and stores information about what blocks exist and what blocks are
 * broken.
 *
 * @author Christopher Shin
 * @author Edem Ahorlu
 */

public class BlockConfig {

  private final List<Block> blockList;
  public static final Paint[] BLOCK_COLORS = {Color.DARKORANGE, Color.MEDIUMVIOLETRED,
      Color.DODGERBLUE, Color.LAWNGREEN};
  private int numberOfColumns = 0;
  private int numberOfBlocksLeft;
  private int scoreKeeper = 0;
  private List<Powerup> powerupList = new ArrayList<>();

  /**
   * Initializes the block configuration given a certain file(level) Maintains an arraylist of
   * blocks for easy access/removal
   *
   * @param levelRawData InputStream representing the data in the input file.
   */

  public BlockConfig(InputStream levelRawData) {
    blockList = new ArrayList<>();
    setupBlocks(levelRawData);
  }

  /**
   * Instantiates each block, and adds them to the block list as well as the scene.
   *
   * @param levelRawData InputStream representing the data in the input file.
   */

  private void setupBlocks(InputStream levelRawData) {
    try {
      BufferedReader fileReader = new BufferedReader(new InputStreamReader(levelRawData));
      String rowOfBlocksRawData;
      int row = 0;
      while ((rowOfBlocksRawData = fileReader.readLine()) != null) {
        String[] rowOfBlocks = rowOfBlocksRawData.split(" ");
        this.numberOfColumns = rowOfBlocks.length;
        addBlockRow(rowOfBlocks, row);
        row++;
      }
      for (Block currentBlock : blockList) {
        setBlockColor(currentBlock);
      }
    } catch (IOException e) {
      System.out.println("File not found");
    }
  }

  /**
   * For a given row of input, add a whole row of blocks to the blocklist and scene.
   *
   * @param rowOfBlocks row of blocks to add
   * @param row         which row corresponds to current row of blocks
   */

  private void addBlockRow(String[] rowOfBlocks, int row) {
    for (int column = 0; column < numberOfColumns; column++) {
      String[] blockEntryBits = rowOfBlocks[column].split("(?<=\\d)(?=\\d)");
      int numHitsToBreak = Integer.parseInt(blockEntryBits[0]);
      int powerup = Integer.parseInt(blockEntryBits[1]);
      int movable = Integer.parseInt(blockEntryBits[2]);
      if (numHitsToBreak != 0) {
        Block newBlock;
        if (movable == 1) {
          newBlock = new MovableBlock(row, column, numHitsToBreak, powerup, rowOfBlocks.length,
              Breakout.SIZE, Breakout.SIZE);
        } else {
          newBlock = new NonMovableBlock(row, column, numHitsToBreak, powerup, rowOfBlocks.length,
              Breakout.SIZE, Breakout.SIZE);
        }
        blockList.add(newBlock);
        if (newBlock.getHitCount() > 0) {
          numberOfBlocksLeft++;
        }
      }
    }
  }

  /**
   * Retrieve block from block list
   *
   * @param row    row for block index
   * @param column column for block index
   * @return block at row, col
   */

  public Block getBlock(int row, int column) {
    return blockList.get(row * numberOfColumns + column);
  }

  /**
   * remove block if hit enough times
   *
   * @param root Group that holds all objects in scene
   * @param allPowerUps list that holds all powerups in the scene
   * @param elapsedTime time elapsed per step
   */

  public void updateBlockList(Group root, List<Powerup> allPowerUps, double elapsedTime) {
    for (Block currentBlock : this.blockList) {
      if (currentBlock.getHitCount() == 0 && !currentBlock.isBroken()) {
        if (currentBlock.hasPowerup()) {
          allPowerUps.add(currentBlock.getPowerup());
          root.getChildren().add(currentBlock.getPowerup());
        }
        root.getChildren().remove(currentBlock);
        scoreKeeper += 5;
        currentBlock.setBroken();
        numberOfBlocksLeft--;
      } else {
        setBlockColor(currentBlock);
        currentBlock.updateLocation(elapsedTime);
      }
    }
  }

  /**
   * used for cheat key 'D', destroys lowest and right most block in scene.  This is how
   * "first" block was interpreted by us.
   */

  void destroyFirstBlock() {
    for (int i = blockList.size() - 1; i >= 0; i--) {
      if (blockList.get(i).getHitCount() > 0) {
        blockList.get(i).setHitCountToZero();
        return;
      }
    }
  }

  private void setBlockColor(Block currentBlock) {
    if (currentBlock.getHitCount() == -1) {
      currentBlock.setFill(BLOCK_COLORS[0]);
    } else {
      currentBlock.setFill(BLOCK_COLORS[currentBlock.getHitCount()]);
    }
  }

  public int keepScore() {
    return scoreKeeper;
  }

  public void setScoreKeeper(int previousScoreCount) {
    scoreKeeper += previousScoreCount;
  }


  public int getNumberOfBlocksLeft() {
    return numberOfBlocksLeft;
  }

  /**
   * returns whole block list
   *
   * @return list of all blocks
   */

  public List<Block> getBlockList() {
    return blockList;
  }
}
