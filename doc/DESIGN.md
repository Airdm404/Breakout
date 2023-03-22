# Game Design Final
### Names Edem Ahorlu Christopher Shin


## Team Roles and Responsibilities

 * Team Member #1 
 
 Edem: Focused on implementing features, restructuring the design of the code and making sure it adheres to the Single Responsibility Principle, Open/Closed Principle, and Liskov substitution principle. Responsible for aesthetics and User Interaction.

 * Team Member #2

Chris: Worked heavily on designing the project to adhere to the SRP, the Open-Closed Principle, and the Liskov substitution principle, refactoring code, implementing our designs.


## Design goals

The main goal of this project was to make new types of blocks, balls, and powerups easy to implement.  This was a major reason why we heavily emphasized the use of inheritance hierarchies in order to adhere to the open closed principle, since we knew that adding more functionality to the game most likely meant being able to add more functionality to the shapes on the screen.  These design goals steered us in a good direction, as Basic and Complete added more and more functionality/types of blocks to the game.  It also allowed us to use Inheritance to our advantage, as we could maintain lists of objects according to their super classes, rather than having to keep tracking of specific object types.


## High-level Design

#### Core Classes

The main classes in our project are Ball, PowerUp, Paddle, Block, BlockConfig, Level, Breakout. 

The Ball class handles the various attributes of the ball in the game: it defines its size, position and other physical features of the ball. It also handles the collision of the ball with the block, paddle, walls and other balls. The Powerup class is responsible for the physical features of all the power-ups: size, position, color, and speed. It’s a parent class to 3 child classes which are the 3 power-ups implemented in the game. The Paddle class defines the various attributes of the paddle and also handles the control of the paddle with your mouse and keyboard. The Block class is responsible for the various attributes of each of the blocks: if the block is a moving/non-moving block, breakable/unbreakable, possesses a power-up or not, and also handles the collision of the block and ball. The BlockConfig is responsible for setting up the blocks in the game. It matches each of the digits in the level\n.txt to some attributes of the block. Level class is responsible for the general features of the game and processes the interaction between each of the objects in the game. This is where all block, paddle, ball, powerup, and displayMessages objects are added. Some cheat codes are also made here. The Breakout is the main class of the game where you launch the game from. It contains the stage and also sets up the scene. It contains buttons, some cheat codes, and also controls the various levels of the game.

## Assumptions that Affect the Design

#### Features Affected by Assumptions

We assumed that the number of cheat keys/powerups was reasonably finite when designing this project, which allowed us to use switch case statements for those cases.  If there were hundreds of cheat keys/powerups, it would definitely make more sense to abstract out the need for a switch case statement, but just being able to use a switch case statement made development a lot faster and easier.  We also assume that the size of the window is 400x400 pixels.  We use the size constant throughout the project rather than hardcoding it in, especially when sizing the blocks, but things like text placement, ball size, paddle size etc.  do not scale according to the size constant in our project.  Making this assumption that the window size stays constant allowed us to easily make objects without having to worry about scaling, but also makes it harder to scale later.  Aside from these two things, I do not think we made any sweeping assumptions about the game that affected adding required features.


## New Features HowTo

#### Easy to Add Features

You can add additional levels to the game by creating a level\n.txt file in the “leveldata”  directory, and then enter a 3 digit number which represents the format for each of the blocks. You then add the name of the text file as a string in the ALL_LEVELS_PATHS String array in the Breakout class.

#### Other Features not yet Done

We finished all features aside from the optional one.
