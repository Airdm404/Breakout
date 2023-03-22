## Lab Discussion
### Team 10
### Edem Ahorlu, Christopher Shin


### Issues in Current Code

Need inheritance hierarchies for Block and Powerups

#### Method or Class: Block
 * Design issues
 
 Block doesn't use an inheritance hierarchy, therefore the code isn't very flexible and it's
 hard to add new types of blocks.
 
 

#### Method or Class: Powerups
 * Design issues

    Powerups have the same issue, so to get rid of a bad if tree, we need to make powerups more
    flexible and utilize inheritance.


### Refactoring Plan

 * What are the code's biggest issues?
 
 Inheritance, small syntax/checkstyle issues, splash buttons aren't working for some reason.

 * Which issues are easy to fix and which are hard?
 
 Inheritance shouldn't be too hard to fix, figuring out how to make a level fully customizable using
 only the text file will be harder to implement.

 * What are good ways to implement the changes "in place"?
 
 Make the new classes and just replace the current code with method/object instances where needed and see 
 if the code works with the new additions.


### Refactoring Work

 * Issue chosen: Fix and Alternatives

Powerups: New subclasses for each powerup.  We couldn't think of a much better solution to this problem.

 * Issue chosen: Fix and Alternatives
 
 Blocks: New subclasses for each block.  An alternative would be to just do everything without subclasses
 since we don't have that many types of blocks, but we need to think about how we could add new
 blocks if we wanted to in the future.