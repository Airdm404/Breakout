# Game Plan
## Edem Ahorlu, Christopher Shin


### Breakout Variation Ideas

* Changing the arrangement of blocks as the player proceeds to newer levels, and adding unbreakable and multiple hit blocks.

* Adding powerups such as unlimited lifes and double damage balls.
 


### Interesting Existing Game Variations

 * Game 1
Super breakout: I find how the bricks don’t just appear when you start a new level but have an animation which aligns them striking. Also, the blocks that need multiple hits to break stood out to me. All these enhance the visuals of the game.

 * Game 2
Centipong: I like how the bricks are moving targets in this variant, and how there can be more than one ball at a time.  I don’t think these aspects of the game need to necessarily happen at the same time, so using either on a level would be interesting.


#### Block Ideas

 * Block 1
 Unbreakable blocks (don’t have to break them to advance, serves as barriers to break other blocks)

 * Block 2
 Blocks that take one hit to break

 * Block 3
Blocks that take multiple hits to break.

#### Power Up Ideas

 * Power Up 1
Extra ball to shoot with
 * Power Up 2
+1 life : Adds an extra life
 * Power Up 3
 Double damage balls: Makes twice as much damage, can take down blocks which might need 2 hits to break with 1 hit


#### Cheat Key Ideas

 * Cheat Key 1
“R” for reset ball and paddle to original position

 * Cheat Key 2
“U” for unlimited lives
 * Cheat Key 3
 “ “ space to pause the game

 * Cheat Key 4
 “M” for “max” paddle that covers the whole bottom of the screen

 * Cheat Key 5
 “E” for extra ball




#### Level Descriptions

 * Level 1
   * Block Configuration
   * [Regular block configuration]{}
  
   * Variation features
     * No powerups
     * One hit blocks

 * Level 2
   * Block Configuration
   * [Different block configuration]{}

   * Variation features
     * Some powerup
     * Multiple hit blocks


 * Level 3
   * Block Configuration
   * [This level has unbreakable blocks]{}

   * Variation features
    * Various powerups
    * Unbreakable blocks 
    * Multiple hit blocks



### Possible Classes

 * Class 1
   * Purpose
   Manages game loop, user input,and game itself.

   * Method
   resetLevel(int level): Restarts game from specified level


 * Class 2
   * Purpose
   Controls different block attributes: color, number of hits to break, and unbreakable blocks

   * Method
   createBlock(String color, int numberOfHitsToBreak, boolean unbreakable): creates a block configuration
   with the specified attributes in the parameter


 * Class 3
   * Purpose
   Controls different ball attributes: color, size, powerups

   * Method
   createBall(String color, int size, int power): creates a ball with the specified attributes in the parameter


 * Class 4
   * Purpose
   Controls the paddle attributes: speed, size 

   * Method
   changePaddleSize(int size): Makes paddle as wide as argument size


 * Class 5
   * Purpose
   Changes the levels and background of the game

   * Method
   getNextLevel(): Goes to next level when the current level is complete



