
/*
 * Project 2
 * Name: Robert Emmett Montgomery
 * E-Mail: rem68@georgetown.edu
 * Instructor: Singh
 * COSC 150
 * In accordance with the class policies and Georgetown's Honor Code,
 * I certify that, with the exceptions of the lecture and Blackboard 
 * notes and those items noted below, I have neither given nor received
 * any assistance on this project.
 * 
 * Description: 
 * This program uses a 2d array to hold values for the board.  The 2d array
 * is 25 tall by 12 wide.  There is 1 buffer column on either side that is 
 * not displayed when the board is drawn.  There is 1 buffer row at the bottom
 * that holds 1s so that nothing can fall below the 24th row (the last row that
 * is drawn).  There are 6 rows at the top that act as a off the scenes area
 * where the pieces are first drawn before they fall.
 * 
 * The pieces have different values for different colors, 1-7, and also have 
 * different shapes.
 * 
 */
 
 The board starts empty, and a piece begins to fall.  Once it stops moving, 
 a new piece is added. Pieces are added until the next piece can not come 
 into the viewable drawn area.
 
 Known issues with the code:
 One issue with the code is that the restart only works if the game is over.
 
 
 Scoring works in that each time the user clicks the down key and moves the
 piece downwards faster, you get 1 extra point.  100 points are awarded for 
 1 full row of blocks.
 
 Additional features:
 
 I have 7 pieces, instead of 4.  Each piece type has a different color.  The 
 user can increase the falling speed by clicking the down key.  There are 
 barriers that do not allow the user to make pieces go off screen.  The game
 can be restarted after the game is over.
 
 Included as a pdf is the usability test.  My two subjects were two of my 
 apartment mates, Alex Thompson and Phil Tam.  Alex Thompson is from 
 Kansas City, Kansas, is double majoring in finance and operations and 
 information management, and is a junior.  Phil Tam is from Fairfax, Virginia,
 is majoring in marketing and international business, and is also a junior.
 
 The users agreed that it is similar to other tetris games, except that the
 peices do not rotate, and this makes the game harder.  They thought the game
 is fair.  General suggestions for additions includes rotating pieces, pause,
 sound effects or music, better interface for restarting the game.
 
 After conducting the usability study, I had time to add the pause function.
 
 java version "1.6.0_41"
 
 TAs that helped me include: Adam and Duncan, and I discussed the project with
 Kyle Ehrlich, but not to any extent that would violate the honor code.
 