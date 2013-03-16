package tetris;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class GameScreen extends JPanel implements KeyListener {
	private static final int UPDATE_RATE_FOR_NORMAL_MODE = 2;  
	private GamePiece gamePiece;  //used to keep track of gamePiece coordinates       					
	private Integer score; // keeps the score
	private Boolean gameOver; // if a piece tries to be put on the board, but there is already a piece in the starting area, the game is over
	private Boolean restartGame; // used to restart the game
	private Boolean gamePaused; // used to pause the game. A feature that was added after the usability test
	private TetrisThread gameThread; // Thread for the game
	private DrawCanvas canvas; 	// used to paint the game screen			
	private int canvasWidth;
	private int canvasHeight;
	private Integer[][] gameList = new Integer[25][12];
	
	
	public GameScreen(int width, int height) {
		  
	      canvasWidth = width;
	      canvasHeight = height;
	      this.addKeyListener(this); //key listeners for left, right, down, or spacebar
		  this.setFocusable(true);
	      initUI();
	}
	
	public final void initUI() {
		score = 0; //beginning score of 0
		gameOver = false; // game is not over
		restartGame = false; // game does not need to be restarted
		gamePaused = false; // game is not currently paused
		
		clearGameBoard(); //sets the 2d array that holds the board data to all 0s

	    canvas = new DrawCanvas(); // the custom drawing panel to draw the game
	    this.setLayout(new BorderLayout());
	    this.add(canvas, BorderLayout.CENTER);
	    
	    // Start the pieces falling
	    gameStart();   
	   }
	   
	public void gameStart() {
		gameThread = new TetrisThread(); //starts the thread for the game
		gameThread.start();
		
	}

	class TetrisThread extends Thread { // the main "While loop" for the game
		public void run() {
			
			while (true) { // the outside loop that runs forever so that after the game is over, the thread continues to run so that you can restart the game
				if (restartGame == true) {
					clearGameBoard(); // when restarting the game, need to clear all data from the 2d array that is the "board"
					gameOver = false; // set the game to not be over, ie restarting the game
					score = 0; // reset the score to 0
				}
			
				Random randomNumber = new Random();
				int integerForShapeOfPiece = randomNumber.nextInt(7); // randomize a number 1 - 7, the number for each of the different pieces
				integerForShapeOfPiece +=1; // because usually it is [0-7) but needs to be [1-8)
				gamePiece = new GamePiece(integerForShapeOfPiece); // add a new game piece
				if (gamePiece.getShape() == 1) {// piece that has red color (because its spots are filled with 1s) and starts just above the screen, piece shaped so:
					gameList[3][6] = 1;			// 
					gameList[4][6] = 1;			//     #  
					gameList[5][6] = 1;			//     #
					gameList[5][5] = 1;			//   # #
				}
				if (gamePiece.getShape() == 2) {//piece that has blue color (because its spots are filled with 2s) and starts just above the screen, piece shaped so:
					gameList[2][5] = 2;			//   #
					gameList[3][5] = 2;			//   #
					gameList[4][5] = 2;			//   #
					gameList[5][5] = 2;			//   #
				}
				if (gamePiece.getShape() == 3) {//piece that has green color (because its spots are filled with 3s) and starts just above the screen, piece shaped so:
					gameList[5][5] = 3;			//
					gameList[5][6] = 3;			//
					gameList[4][5] = 3;			//   # #
					gameList[4][6] = 3;			//   # #
				}
				if (gamePiece.getShape() == 4) {//piece that has yellow color (because its spots are filled with 4s) and starts just above the screen, piece shaped so:
					gameList[5][5] = 4;			//
					gameList[4][5] = 4;			//   #
					gameList[4][6] = 4;			//   # # 
					gameList[3][5] = 4;			//   #
				}
				if (gamePiece.getShape() == 5) {//piece that has orange color (because its spots are filled with 5s) and starts just above the screen, piece shaped so:
					gameList[5][6] = 5;			//
					gameList[4][6] = 5;			//  # #
					gameList[3][6] = 5;			//    #
					gameList[3][5] = 5;			//    #
				}
				if (gamePiece.getShape() == 6) {//piece that has purple color (because its spots are filled with 6s) and starts just above the screen, piece shaped so:
					gameList[5][6] = 6;			//  
					gameList[4][6] = 6;			//  # 
					gameList[4][5] = 6;			//  # # 
					gameList[3][5] = 6;			//    #
				}			
				if (gamePiece.getShape() == 7) {//piece that has a magenta color (because its spots are filled with 7s) and starts just above the screen, piece shaped so:
					gameList[5][7] = 7;			//
					gameList[5][6] = 7;			//
					gameList[4][6] = 7;			//  # #
					gameList[4][5] = 7;			//    # # 
				}
			
				while (!gameOver) { // while the game is not over
					restartGame = false; // reset the restartGame to false so that you can restart again if needed
				
					gameUpdate(); // update the 2d array numbers by letting the piece fall, also deleting full rows, adding points, and finally checking if the game is over
					repaint();   // repaint the board using the 2d array of numbers, with a number for each spot
					try {
						Thread.sleep(1000 / UPDATE_RATE_FOR_NORMAL_MODE); // update the game each 1 second
					} 
					catch (InterruptedException ex) {}
				}
			}
		}
	}
	
	public void gameUpdate() {  // update the game by moving the falling piece down 1 each 1 second
		if (gamePiece.pieceMoving()) { //if the piece doesn't have anything blocking its way downward
			Integer[] xCoordsToWatch = gamePiece.getXBarriers(); // get the lists of spots that it needs to look at to make sure it can fall downward
			Integer[] yCoordsToWatch = gamePiece.getYBarriers();  

			if (gamePiece.getShape() == 1) { // if the falling piece is of shape 1
				if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] == 0 && gameList[yCoordsToWatch[1] + 1][xCoordsToWatch[1]] == 0) { // to fall downward it only needs to look at the first to "blocks" of the 4 block piece that are on the bottom and would block downward movment
					Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
					for (int counter = 0; counter < 4; counter++) { // move each of the 4 coordinates of the piece
						Integer counter2 = 0;
							int tempY = temporaryArray[counter][counter2];
							int tempX = temporaryArray[counter][counter2+1];
							gameList[tempY + 1][tempX] = gameList[tempY][tempX]; // move each of the 4 "blocks" down one
							gameList[tempY][tempX] = 0;
							temporaryArray[counter][counter2] = tempY + 1;
							gamePiece.setPieceCoordinates(temporaryArray);
							if (yCoordsToWatch[counter] == tempY){
								yCoordsToWatch[counter] = tempY + 1;  // update the yCoords that need to be watched
								gamePiece.setYBarriers(yCoordsToWatch);
							}
					}
				}
				else if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] != 0 || gameList[yCoordsToWatch[1] + 1][xCoordsToWatch[1]] != 0) {
					gamePiece.setPieceMoving(false); // if anything is under the yCoords to watch, it can't fall anymore, so set the piece to stop moving
				}
			}
			if (gamePiece.getShape() == 2) { // if the falling piece is of shape 2
				if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] == 0) {
					Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
					for (int counter = 0; counter < 4; counter++) { // move each of the 4 coordinates of the piece
						Integer counter2 = 0;
							int tempY = temporaryArray[counter][counter2];
							int tempX = temporaryArray[counter][counter2+1];
							gameList[tempY + 1][tempX] = gameList[tempY][tempX];
							gameList[tempY][tempX] = 0;
							temporaryArray[counter][counter2] = tempY + 1;
							gamePiece.setPieceCoordinates(temporaryArray);
							if (yCoordsToWatch[counter] == tempY){
								yCoordsToWatch[counter] = tempY + 1; // update the yCoords that need to be watched
								gamePiece.setYBarriers(yCoordsToWatch);
							}
					}
				}
				else if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] != 0) {
					gamePiece.setPieceMoving(false); // if anything is under the yCoords to watch, it can't fall anymore, so set the piece to stop moving
				}
			}
			if (gamePiece.getShape() == 3) { // if the falling piece is of shape 3
				if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] == 0 && gameList[yCoordsToWatch[1] + 1][xCoordsToWatch[1]] == 0) {
					Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
					for (int counter = 0; counter < 4; counter++) { // move each of the 4 coordinates of the piece
						Integer counter2 = 0;
							int tempY = temporaryArray[counter][counter2];
							int tempX = temporaryArray[counter][counter2+1];
							gameList[tempY + 1][tempX] = gameList[tempY][tempX];
							gameList[tempY][tempX] = 0;
							temporaryArray[counter][counter2] = tempY + 1;
							gamePiece.setPieceCoordinates(temporaryArray);
							if (yCoordsToWatch[counter] == tempY){
								yCoordsToWatch[counter] = tempY + 1; // update the yCoords that need to be watched
								gamePiece.setYBarriers(yCoordsToWatch);
							}
					}
				}
				else if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] != 0 || gameList[yCoordsToWatch[1] + 1][xCoordsToWatch[1]] != 0) {
					gamePiece.setPieceMoving(false); // if anything is under the yCoords to watch, it can't fall anymore, so set the piece to stop moving
				}
			}
			if (gamePiece.getShape() == 4) { // if the falling piece is of shape 4
				if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] == 0 && gameList[yCoordsToWatch[2] + 1][xCoordsToWatch[2]] == 0) {
					Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
					for (int counter = 0; counter < 4; counter++) { // move each of the 4 coordinates of the piece
						Integer counter2 = 0;
							int tempY = temporaryArray[counter][counter2];
							int tempX = temporaryArray[counter][counter2+1];
							gameList[tempY + 1][tempX] = gameList[tempY][tempX];
							gameList[tempY][tempX] = 0;
							temporaryArray[counter][counter2] = tempY + 1;
							gamePiece.setPieceCoordinates(temporaryArray);
							if (yCoordsToWatch[counter] == tempY){
								yCoordsToWatch[counter] = tempY + 1; // update the yCoords that need to be watched
								gamePiece.setYBarriers(yCoordsToWatch);
							}
					}
				}
				else if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] != 0 || gameList[yCoordsToWatch[2] + 1][xCoordsToWatch[2]] != 0) {
					gamePiece.setPieceMoving(false); // if anything is under the yCoords to watch, it can't fall anymore, so set the piece to stop moving
				}
			}
			if (gamePiece.getShape() == 5) { // if the falling piece is of shape 5
				if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] == 0 && gameList[yCoordsToWatch[3] + 1][xCoordsToWatch[3]] == 0) {
					Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
					for (int counter = 0; counter < 4; counter++) { // move each of the 4 coordinates of the piece
						Integer counter2 = 0;
							int tempY = temporaryArray[counter][counter2];
							int tempX = temporaryArray[counter][counter2+1];
							gameList[tempY + 1][tempX] = gameList[tempY][tempX];
							gameList[tempY][tempX] = 0;
							temporaryArray[counter][counter2] = tempY + 1;
							gamePiece.setPieceCoordinates(temporaryArray);
							if (yCoordsToWatch[counter] == tempY){
								yCoordsToWatch[counter] = tempY + 1; // update the yCoords that need to be watched
								gamePiece.setYBarriers(yCoordsToWatch);
							}
					}
				}
				else if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] != 0 || gameList[yCoordsToWatch[3] + 1][xCoordsToWatch[3]] != 0) {
					gamePiece.setPieceMoving(false); // if anything is under the yCoords to watch, it can't fall anymore, so set the piece to stop moving
				}
			}
			if (gamePiece.getShape() == 6) { // if the falling piece is of shape 6
				if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] == 0 && gameList[yCoordsToWatch[2] + 1][xCoordsToWatch[2]] == 0) {
					Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
					for (int counter = 0; counter < 4; counter++) { // move each of the 4 coordinates of the piece
						Integer counter2 = 0;
							int tempY = temporaryArray[counter][counter2];
							int tempX = temporaryArray[counter][counter2+1];
							gameList[tempY + 1][tempX] = gameList[tempY][tempX];
							gameList[tempY][tempX] = 0;
							temporaryArray[counter][counter2] = tempY + 1;
							gamePiece.setPieceCoordinates(temporaryArray);
							if (yCoordsToWatch[counter] == tempY){
								yCoordsToWatch[counter] = tempY + 1; // update the yCoords that need to be watched
								gamePiece.setYBarriers(yCoordsToWatch);
							}
					}
				}
				else if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] != 0 || gameList[yCoordsToWatch[2] + 1][xCoordsToWatch[2]] != 0) {
					gamePiece.setPieceMoving(false); // if anything is under the yCoords to watch, it can't fall anymore, so set the piece to stop moving
				}
			}
			if (gamePiece.getShape() == 7) { // if the falling piece is of shape 7
				if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] == 0 && gameList[yCoordsToWatch[1] + 1][xCoordsToWatch[1]] == 0 && gameList[yCoordsToWatch[3] + 1][xCoordsToWatch[3]] == 0) {
					Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
					for (int counter = 0; counter < 4; counter++) { // move each of the 4 coordinates of the piece
						Integer counter2 = 0;
							int tempY = temporaryArray[counter][counter2];
							int tempX = temporaryArray[counter][counter2+1];
							gameList[tempY + 1][tempX] = gameList[tempY][tempX];
							gameList[tempY][tempX] = 0;
							temporaryArray[counter][counter2] = tempY + 1;
							gamePiece.setPieceCoordinates(temporaryArray);
							if (yCoordsToWatch[counter] == tempY){
								yCoordsToWatch[counter] = tempY + 1; // update the yCoords that need to be watched
								gamePiece.setYBarriers(yCoordsToWatch);
							}
					}
				}
				else if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] != 0 || gameList[yCoordsToWatch[1] + 1][xCoordsToWatch[1]] != 0 || gameList[yCoordsToWatch[3] + 1][xCoordsToWatch[3]] != 0) {
					gamePiece.setPieceMoving(false); // if anything is under the yCoords to watch, it can't fall anymore, so set the piece to stop moving
				}
			}
		}
		else if (!gamePiece.pieceMoving()) { // if the piece has stopped moving because directly under it are filled spaces
			Integer counter1 = 5;
			Integer counter2 = 1;
			if (gameList[counter1][counter2] != 0 || gameList[counter1][counter2 + 1] != 0 || gameList[counter1][counter2 + 2] != 0 || gameList[counter1][counter2 + 3] != 0 || gameList[counter1][counter2 + 4] != 0 || gameList[counter1][counter2 + 5] != 0 || 
					gameList[counter1][counter2 + 6] != 0 || gameList[counter1][counter2 + 7] != 0 || gameList[counter1][counter2 + 8] != 0 || gameList[counter1][counter2 + 9] != 0) {
				gameOver = true; // see if the game is over because the latest moving piece stopped while still still in the original buffer zone and did not fully make it onto the board
			}
			counter1 = 23;
			counter2 = 1;
			Integer counter3 = 23;
			while (counter1 >= 6) { // go through bottom to top and see if any rows are completely full, if so then delete them all, move everything above it down one space, and add 100 points
				counter2 = 1;
				while (gameList[counter1][counter2] != 0 && gameList[counter1][counter2 + 1] !=0 && gameList[counter1][counter2 + 2] !=0 && gameList[counter1][counter2 + 3] !=0 && gameList[counter1][counter2 + 4] !=0 && gameList[counter1][counter2 + 5] !=0
						&& gameList[counter1][counter2 + 6] !=0 && gameList[counter1][counter2 + 7] != 0 && gameList[counter1][counter2 + 8] !=0 && gameList[counter1][counter2 + 9] !=0) {
					counter3 = counter1;
					while (counter3 >= 6) {
						counter2 = 1;
						while (counter2 <= 10) {
							gameList[counter3][counter2] = gameList[counter3 - 1][counter2];
							gameList[counter3-1][counter2] = 0;
							counter2 += 1;
						}
						counter3 -= 1;
					}
					counter2 = 1;
					score += 100; // adding 100 points
				}
			counter1 -= 1;
			}
			
			Random randomNumber = new Random(); // if the game is not over add a new piece 
		    int integerForShapeOfPiece = randomNumber.nextInt(7);
		    integerForShapeOfPiece +=1;
			gamePiece = new GamePiece(integerForShapeOfPiece);
			if (gamePiece.getShape() == 1) {// piece that has red color (because its spots are filled with 1s) and starts just above the screen, piece shaped so:
				gameList[3][6] = 1;			// 
				gameList[4][6] = 1;			//     #  
				gameList[5][6] = 1;			//     #
				gameList[5][5] = 1;			//   # #
			}
			if (gamePiece.getShape() == 2) {//piece that has blue color (because its spots are filled with 2s) and starts just above the screen, piece shaped so:
				gameList[2][5] = 2;			//   #
				gameList[3][5] = 2;			//   #
				gameList[4][5] = 2;			//   #
				gameList[5][5] = 2;			//   #
			}
			if (gamePiece.getShape() == 3) {//piece that has green color (because its spots are filled with 3s) and starts just above the screen, piece shaped so:
				gameList[5][5] = 3;			//
				gameList[5][6] = 3;			//
				gameList[4][5] = 3;			//   # #
				gameList[4][6] = 3;			//   # #
			}
			if (gamePiece.getShape() == 4) {//piece that has yellow color (because its spots are filled with 4s) and starts just above the screen, piece shaped so:
				gameList[5][5] = 4;			//
				gameList[4][5] = 4;			//   #
				gameList[4][6] = 4;			//   # # 
				gameList[3][5] = 4;			//   #
			}
			if (gamePiece.getShape() == 5) {//piece that has orange color (because its spots are filled with 5s) and starts just above the screen, piece shaped so:
				gameList[5][6] = 5;			//
				gameList[4][6] = 5;			//  # #
				gameList[3][6] = 5;			//    #
				gameList[3][5] = 5;			//    #
			}
			if (gamePiece.getShape() == 6) {//piece that has purple color (because its spots are filled with 6s) and starts just above the screen, piece shaped so:
				gameList[5][6] = 6;			//  
				gameList[4][6] = 6;			//  # 
				gameList[4][5] = 6;			//  # # 
				gameList[3][5] = 6;			//    #
			}			
			if (gamePiece.getShape() == 7) {//piece that has a magenta color (because its spots are filled with 7s) and starts just above the screen, piece shaped so:
				gameList[5][7] = 7;			//
				gameList[5][6] = 7;			//
				gameList[4][6] = 7;			//  # #
				gameList[4][5] = 7;			//    # # 
			}
		}
	}

	class DrawCanvas extends JPanel { // main draw function that goes through the 2d array from top right to bottom left
		@Override	
		public void paintComponent(Graphics g) {
			super.paintComponent(g);		         
		 	Integer counter1 = 6;
			Integer counter2 = 1;
			Integer counter3 = 0;
			
			while (counter1 <= 23) { // goes through each spot of the 2d array
				counter2 = 1; 
				while (counter2 <= 10) {
					if (gameList[counter1][counter2] == 0) { // if the spot has a 0 in it, paint a 30x30 block that is white
						Graphics2D myPiece = (Graphics2D) g;
						Color color = new Color (255, 255, 255);
						Integer xValue = (counter2-1)*30 + 1;
						Integer yValue = (counter1-6)*30 + 1;
						Integer widthAndHeight = 30;
							
						myPiece.setColor(color);
						myPiece.fillRect(xValue, yValue, widthAndHeight, widthAndHeight);
					}
					else if (gameList[counter1][counter2] == 1) { // if the spot has a 1 in it, paint a 30x30 block that is red 
						Integer xValue = (counter2-1)*30;
						Integer yValue = (counter1-6)*30;
								
						Graphics2D myPiece = (Graphics2D) g;
						Color color = new Color(0,0,0); // has a black outline
							
						Integer widthAndHeight = 30;
						myPiece.setColor(color);							
						myPiece.drawRect(xValue, yValue, widthAndHeight, widthAndHeight);
						color = new Color(255,0,0);
						myPiece.setColor(color);
						myPiece.fillRect(xValue +1, yValue+1, widthAndHeight -1, widthAndHeight -1);
					}
					else if (gameList[counter1][counter2] == 2) { // if the spot has a 2 in it, paint a 30x30 block that is blue 
						Integer xValue = (counter2-1)*30 ;
						Integer yValue = (counter1-6)*30 ;
					
						Graphics2D myPiece = (Graphics2D) g;
						Color color = new Color(0,0,0); // has a black outline
							
						Integer widthAndHeight = 30;
						myPiece.setColor(color);
						myPiece.drawRect(xValue, yValue, widthAndHeight, widthAndHeight);
						color = new Color(0,0,255);
						myPiece.setColor(color);
						myPiece.fillRect(xValue +1, yValue+1, widthAndHeight -1, widthAndHeight -1);
					}
						else if (gameList[counter1][counter2] == 3) { // if the spot has a 3 in it, paint a 30x30 block that is green
							Integer xValue = (counter2-1)*30 ;
							Integer yValue = (counter1-6)*30 ;
							
							Graphics2D myPiece = (Graphics2D) g;
							Color color = new Color(0,0,0); // has a black outline
							
							Integer widthAndHeight = 30;
							myPiece.setColor(color);
							myPiece.drawRect(xValue, yValue, widthAndHeight, widthAndHeight);
							color = new Color(0,153,0);
							myPiece.setColor(color);
							myPiece.fillRect(xValue +1, yValue+1, widthAndHeight -1, widthAndHeight -1);
						}
						else if (gameList[counter1][counter2] == 4) { // if the spot has a 4 in it, paint a 30x30 block that is yellow 
							Integer xValue = (counter2-1)*30 ;
							Integer yValue = (counter1-6)*30 ;
							
							Graphics2D myPiece = (Graphics2D) g;
							Color color = new Color(0,0,0); // has a black outline
							
							Integer widthAndHeight = 30;
							myPiece.setColor(color);
							myPiece.drawRect(xValue, yValue, widthAndHeight, widthAndHeight);
							color = new Color(255,255,0);
							myPiece.setColor(color);
							myPiece.fillRect(xValue +1, yValue+1, widthAndHeight -1, widthAndHeight -1);
						}
						else if (gameList[counter1][counter2] == 5) { // if the spot has a 5 in it, paint a 30x30 block that is orange
							Integer xValue = (counter2-1)*30 ;
							Integer yValue = (counter1-6)*30 ;
							
							Graphics2D myPiece = (Graphics2D) g;
							Color color = new Color(0,0,0); // has a black outline
							
							Integer widthAndHeight = 30;
							myPiece.setColor(color);
							myPiece.drawRect(xValue, yValue, widthAndHeight, widthAndHeight);
							color = new Color(255,111,0);
							myPiece.setColor(color);
							myPiece.fillRect(xValue +1, yValue+1, widthAndHeight -1, widthAndHeight -1);
						}
						else if (gameList[counter1][counter2] == 6) { // if the spot has a 6 in it, paint a 30x30 block that is purple 
							Integer xValue = (counter2-1)*30 ;
							Integer yValue = (counter1-6)*30 ;
							
							Graphics2D myPiece = (Graphics2D) g;
							Color color = new Color(0,0,0); // has a black outline
							
							Integer widthAndHeight = 30;
							myPiece.setColor(color);
							myPiece.drawRect(xValue, yValue, widthAndHeight, widthAndHeight);
							color = new Color(102,0,204);
							myPiece.setColor(color);
							myPiece.fillRect(xValue +1, yValue+1, widthAndHeight -1, widthAndHeight -1);
						}						
						else if (gameList[counter1][counter2] == 7) { // if the spot has a 7 in it, paint a 30x30 block that is magenta
							Integer xValue = (counter2-1)*30 ;
							Integer yValue = (counter1-6)*30 ;
							
							Graphics2D myPiece = (Graphics2D) g;
							Color color = new Color(0,0,0); // has a black outline
							
							Integer widthAndHeight = 30;
							myPiece.setColor(color);
							myPiece.drawRect(xValue, yValue, widthAndHeight, widthAndHeight);
							color = new Color(204,0,102);
							myPiece.setColor(color);
							myPiece.fillRect(xValue +1, yValue+1, widthAndHeight -1, widthAndHeight -1);
						}
						counter2 += 1;
						counter3 += 1;
					}
					counter1 += 1;
				}
				

		        g.setColor(Color.BLACK);
		        g.setFont(new Font("Impact", Font.PLAIN, 13));
		        g.drawString("Score: " + score.toString(), 225, 20); // draw the score
		        
		        if (gameOver) { // draw the game over message if the game is over
		        	Color color = new Color(0,255,255);
			        g.setColor(color);
			        g.setFont(new Font("Impact", Font.PLAIN, 65));
		        	g.drawString("GAME OVER", 10, 300);
		        }
		        if (gamePaused) { // draw the game paused message if the game is paused
		        	Color color = new Color(0,255,255);
			        g.setColor(color);
			        g.setFont(new Font("Impact", Font.PLAIN, 52));
		        	g.drawString("GAME PAUSED", 10, 300);
		        }
		 	}
		
		    @Override
		    public Dimension getPreferredSize() {
		    	return (new Dimension(canvasWidth, canvasHeight));
		    }
	}
	
	public void keyPressed(KeyEvent e) { // the various key events are left, right, down, and space bar
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT : {  // if the left key is pushed the game tries to move the piece to the left, if there is nothing blocking its way
				if (gamePiece.pieceMoving()) { // if the piece is moving
					Integer[] xCoordsToWatch = gamePiece.getXBarriers(); // get the barriers so that we can check if anything is blocking its way
					Integer[] yCoordsToWatch = gamePiece.getYBarriers();
					if (xCoordsToWatch[0] != 1 && xCoordsToWatch[1] != 1 && xCoordsToWatch[2] != 1 && xCoordsToWatch[3] != 1) {  // first make sure user is not trying to move left off the screen 
						if (gamePiece.getShape() == 1) { // check the possible places that would hinder movement left for a piece of shape 1
							if (gameList[yCoordsToWatch[0]][xCoordsToWatch[0] - 1] == 0 && gameList[yCoordsToWatch[2]][xCoordsToWatch[2] - 1] == 0 && gameList[yCoordsToWatch[3]][xCoordsToWatch[3] - 1] == 0) {
								Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
								for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one left
									Integer counter2 = 0;
									int tempY = temporaryArray[counter][counter2];
									int tempX = temporaryArray[counter][counter2+1];
									gameList[tempY][tempX - 1] = gameList[tempY][tempX];
									gameList[tempY][tempX] = 0;
									temporaryArray[counter][counter2 + 1] = tempX - 1;
									gamePiece.setPieceCoordinates(temporaryArray);
									if (xCoordsToWatch[counter] == tempX){
										xCoordsToWatch[counter] = tempX - 1;
										gamePiece.setXBarriers(xCoordsToWatch);
	            					}
	            				}
	            				repaint(); 
	            			}
	            		}
						if (gamePiece.getShape() == 2) { // check the possible places that would hinder movement left for a piece of shape 2
							if (gameList[yCoordsToWatch[0]][xCoordsToWatch[0] - 1] == 0 && gameList[yCoordsToWatch[1]][xCoordsToWatch[1] - 1] == 0 && gameList[yCoordsToWatch[2]][xCoordsToWatch[2] - 1] == 0 && gameList[yCoordsToWatch[3]][xCoordsToWatch[3] - 1] == 0) {
								Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
								for (int counter = 0; counter < 4; counter++) {  // move each of the 4 "blocks" one left
									Integer counter2 = 0;
									int tempY = temporaryArray[counter][counter2];
									int tempX = temporaryArray[counter][counter2+1];
									gameList[tempY][tempX - 1] = gameList[tempY][tempX];
									gameList[tempY][tempX] = 0;
									temporaryArray[counter][counter2 + 1] = tempX - 1;
									gamePiece.setPieceCoordinates(temporaryArray);
									if (xCoordsToWatch[counter] == tempX){
										xCoordsToWatch[counter] = tempX - 1;
										gamePiece.setXBarriers(xCoordsToWatch);
	            					}
	            				}
	            			repaint(); 
							}
	            		}
	            		if (gamePiece.getShape() == 3) { // check the possible places that would hinder movement left for a piece of shape 3
	            			if (gameList[yCoordsToWatch[0]][xCoordsToWatch[0] - 1] == 0 &&  gameList[yCoordsToWatch[2]][xCoordsToWatch[2] - 1] == 0) {
	            				Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	            				for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one left
	            					Integer counter2 = 0;
	            					int tempY = temporaryArray[counter][counter2];
	            					int tempX = temporaryArray[counter][counter2+1];
	            					gameList[tempY][tempX - 1] = gameList[tempY][tempX];
	            					gameList[tempY][tempX] = 0;
	            					temporaryArray[counter][counter2 + 1] = tempX - 1;
	            					gamePiece.setPieceCoordinates(temporaryArray);
	            					if (xCoordsToWatch[counter] == tempX){
	            						xCoordsToWatch[counter] = tempX - 1;
	            						gamePiece.setXBarriers(xCoordsToWatch);
	            					}	
	            				}
	            			repaint(); 
	            			}
	            		}
	            		if (gamePiece.getShape() == 4) { // check the possible places that would hinder movement left for a piece of shape 4
	            			if (gameList[yCoordsToWatch[0]][xCoordsToWatch[0] - 1] == 0 && gameList[yCoordsToWatch[1]][xCoordsToWatch[1] - 1] == 0 && gameList[yCoordsToWatch[3]][xCoordsToWatch[3] - 1] == 0) {
	            				Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	            				for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one left
	            					Integer counter2 = 0;
	            					int tempY = temporaryArray[counter][counter2];
	            					int tempX = temporaryArray[counter][counter2+1];
	            					gameList[tempY][tempX - 1] = gameList[tempY][tempX];
	            					gameList[tempY][tempX] = 0;
	            					temporaryArray[counter][counter2 + 1] = tempX - 1;
	            					gamePiece.setPieceCoordinates(temporaryArray);
	            					if (xCoordsToWatch[counter] == tempX){
	            						xCoordsToWatch[counter] = tempX - 1;
	            						gamePiece.setXBarriers(xCoordsToWatch);
	            					}	
	            				}
	            				repaint(); 
	            			}
	            		}
	            		if (gamePiece.getShape() == 5) { // check the possible places that would hinder movement left for a piece of shape 5
	            			if (gameList[yCoordsToWatch[0]][xCoordsToWatch[0] - 1] == 0 && gameList[yCoordsToWatch[1]][xCoordsToWatch[1] - 1] == 0 && gameList[yCoordsToWatch[3]][xCoordsToWatch[3] - 1] == 0) {
	            				Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	            				for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one left
	            					Integer counter2 = 0;
	            					int tempY = temporaryArray[counter][counter2];
	            					int tempX = temporaryArray[counter][counter2+1];
	            					if (counter == 3) { // for the 3rd block, do not paint a empty block to the right because it will overwrite another block of the piece
	            						gameList[tempY][tempX - 1] = gameList[tempY][tempX];
	            					}
	            					else {
	            						gameList[tempY][tempX - 1] = gameList[tempY][tempX];
	            						gameList[tempY][tempX] = 0;
	            					}
	            					temporaryArray[counter][counter2 + 1] = tempX - 1;
	            					gamePiece.setPieceCoordinates(temporaryArray);
	            					if (xCoordsToWatch[counter] == tempX){
	            						xCoordsToWatch[counter] = tempX - 1;
	            						gamePiece.setXBarriers(xCoordsToWatch);
	            					}	
	            				}
	            				repaint(); 
	            			}
	            		}
	            		if (gamePiece.getShape() == 6) { // check the possible places that would hinder movement left for a piece of shape 6
	            			if (gameList[yCoordsToWatch[0]][xCoordsToWatch[0] - 1] == 0 && gameList[yCoordsToWatch[2]][xCoordsToWatch[2] - 1] == 0 && gameList[yCoordsToWatch[3]][xCoordsToWatch[3] - 1] == 0) {
	            				Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	            				for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one left
	            					Integer counter2 = 0;
	            					int tempY = temporaryArray[counter][counter2];
	            					int tempX = temporaryArray[counter][counter2+1];
	            					if (counter == 2) { // for the 2nd block, do not paint a empty block to the right because it will overwrite another block of the piece
	            						gameList[tempY][tempX - 1] = gameList[tempY][tempX];
	            					}
	            					else {
	            						gameList[tempY][tempX - 1] = gameList[tempY][tempX];
		            					gameList[tempY][tempX] = 0;
	            					}
	            					temporaryArray[counter][counter2 + 1] = tempX - 1;
	            					gamePiece.setPieceCoordinates(temporaryArray);
	            					if (xCoordsToWatch[counter] == tempX){
	            						xCoordsToWatch[counter] = tempX - 1;
	            						gamePiece.setXBarriers(xCoordsToWatch);
	            					}	
	            				}
	            				repaint(); 
	            			}
	            		}
	            		if (gamePiece.getShape() == 7) { // check the possible places that would hinder movement left for a piece of shape 7
	            			if (gameList[yCoordsToWatch[1]][xCoordsToWatch[1] - 1] == 0 && gameList[yCoordsToWatch[3]][xCoordsToWatch[3] - 1] == 0) {
	            				Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	            				for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one left
	            					Integer counter2 = 0;
	            					int tempY = temporaryArray[counter][counter2];
	            					int tempX = temporaryArray[counter][counter2+1];
	            					if (counter == 1 || counter == 3) { // for the 1st or 3rd block, do not paint a empty block to the right because it will overwrite another block of the piece
	            						gameList[tempY][tempX - 1] = gameList[tempY][tempX];
	            					}
	            					else {
	            						gameList[tempY][tempX - 1] = gameList[tempY][tempX];
	            						gameList[tempY][tempX] = 0;
	            					}
	            					temporaryArray[counter][counter2 + 1] = tempX - 1;
	            					gamePiece.setPieceCoordinates(temporaryArray);
	            					if (xCoordsToWatch[counter] == tempX){
	            						xCoordsToWatch[counter] = tempX - 1;
	            						gamePiece.setXBarriers(xCoordsToWatch);
	            					}	
	            				}
	            				repaint(); 
	            			}
	            		}
	            	}
	            }
	    		break;
	        }
			case KeyEvent.VK_RIGHT: { // if the right key is pushed the game tries to move the piece to the right, if there is nothing blocking its way
				if (gamePiece.pieceMoving()) { // if the piece is moving
					Integer[] xCoordsToWatch = gamePiece.getXBarriers(); // get the barriers so that we can check if anything is blocking its way
					Integer[] yCoordsToWatch = gamePiece.getYBarriers();
					if (xCoordsToWatch[0] != 10 && xCoordsToWatch[1] != 10 && xCoordsToWatch[2] != 10 && xCoordsToWatch[3] != 10) { // first make sure user is not trying to move right off the screen 
						if (gamePiece.getShape() == 1) { // check the possible places that would hinder movement right for a piece of shape 1
							if (gameList[yCoordsToWatch[1]][xCoordsToWatch[1] + 1] == 0 && gameList[yCoordsToWatch[2]][xCoordsToWatch[2] + 1] == 0 && gameList[yCoordsToWatch[3]][xCoordsToWatch[3] + 1] == 0) {
								Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
								for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one right
									Integer counter2 = 0;
									int tempY = temporaryArray[counter][counter2];
									int tempX = temporaryArray[counter][counter2+1];
									if (counter == 1) { // for the 1st block, do not paint a empty block to the left because it will overwrite another block of the piece
										gameList[tempY][tempX + 1] = gameList[tempY][tempX];
	            					}
									else {
										gameList[tempY][tempX + 1] = gameList[tempY][tempX];
										gameList[tempY][tempX] = 0;
	            					}
									temporaryArray[counter][counter2 + 1] = tempX + 1;
									gamePiece.setPieceCoordinates(temporaryArray);
									if (xCoordsToWatch[counter] == tempX){
										xCoordsToWatch[counter] = tempX + 1;
										gamePiece.setXBarriers(xCoordsToWatch);
	            					}
	            				}
								repaint(); 
	            			}
	            		}
	            		if (gamePiece.getShape() == 2) { // check the possible places that would hinder movement right for a piece of shape 2
	            			if (gameList[yCoordsToWatch[0]][xCoordsToWatch[0] + 1] == 0 && gameList[yCoordsToWatch[1]][xCoordsToWatch[1] + 1] == 0 && gameList[yCoordsToWatch[2]][xCoordsToWatch[2] + 1] == 0 && gameList[yCoordsToWatch[3]][xCoordsToWatch[3] + 1] == 0) {
	            				Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	            				for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one right
	            					Integer counter2 = 0;
	            					int tempY = temporaryArray[counter][counter2];
	            					int tempX = temporaryArray[counter][counter2+1];
	            					gameList[tempY][tempX + 1] = gameList[tempY][tempX];
	            					gameList[tempY][tempX] = 0;
	            					temporaryArray[counter][counter2 + 1] = tempX + 1;
	            					gamePiece.setPieceCoordinates(temporaryArray);
	            					if (xCoordsToWatch[counter] == tempX){
	            						xCoordsToWatch[counter] = tempX + 1;
	            						gamePiece.setXBarriers(xCoordsToWatch);
	            					}
	            				}
	            				repaint(); 
	            			}
	            		}
	            		if (gamePiece.getShape() == 3) { // check the possible places that would hinder movement right for a piece of shape 3
	            			if (gameList[yCoordsToWatch[1]][xCoordsToWatch[1] + 1] == 0 && gameList[yCoordsToWatch[3]][xCoordsToWatch[3] + 1] == 0) {
	            				Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	            				for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one right
	            					Integer counter2 = 0;
	            					int tempY = temporaryArray[counter][counter2];
	            					int tempX = temporaryArray[counter][counter2+1];
	            					if (counter == 1 || counter == 3) { // for the 1st or 3rd block, do not paint a empty block to the left because it will overwrite another block of the piece
	            						gameList[tempY][tempX + 1] = gameList[tempY][tempX];
	            					}
	            					else {
	            						gameList[tempY][tempX + 1] = gameList[tempY][tempX];
	            						gameList[tempY][tempX] = 0;
	            					}
	            					temporaryArray[counter][counter2 + 1] = tempX + 1;
	           						gamePiece.setPieceCoordinates(temporaryArray);
	           						if (xCoordsToWatch[counter] == tempX){
	           							xCoordsToWatch[counter] = tempX + 1;
	           							gamePiece.setXBarriers(xCoordsToWatch);
	           						}
	           					}
	           					repaint(); 
	           				}
	           			}
	            		if (gamePiece.getShape() == 4) { // check the possible places that would hinder movement right for a piece of shape 4
	            			if (gameList[yCoordsToWatch[0]][xCoordsToWatch[0] + 1] == 0 && gameList[yCoordsToWatch[2]][xCoordsToWatch[2] + 1] == 0 && gameList[yCoordsToWatch[3]][xCoordsToWatch[3] + 1] == 0) {
	            				Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	            				for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one right
	            					Integer counter2 = 0;
	            					int tempY = temporaryArray[counter][counter2];
	            					int tempX = temporaryArray[counter][counter2+1];
	            					if (counter == 2) { // for the 2nd block, do not paint a empty block to the left because it will overwrite another block of the piece
	            						gameList[tempY][tempX + 1] = gameList[tempY][tempX];
	            					}
	            					else {
	            						gameList[tempY][tempX + 1] = gameList[tempY][tempX];
	            						gameList[tempY][tempX] = 0;
	            					}
	            					temporaryArray[counter][counter2 + 1] = tempX + 1;
	            					gamePiece.setPieceCoordinates(temporaryArray);
	            					if (xCoordsToWatch[counter] == tempX){
	            						xCoordsToWatch[counter] = tempX + 1;
	            						gamePiece.setXBarriers(xCoordsToWatch);
	            					}
	            				}
	            				repaint(); 
	            			}
	            		}
	            		if (gamePiece.getShape() == 5) { // check the possible places that would hinder movement right for a piece of shape 5
	            			if (gameList[yCoordsToWatch[0]][xCoordsToWatch[0] + 1] == 0 && gameList[yCoordsToWatch[1]][xCoordsToWatch[1] + 1] == 0 && gameList[yCoordsToWatch[2]][xCoordsToWatch[2] + 1] == 0) {
	            				Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	            				for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one right
	            					Integer counter2 = 0;
	            					int tempY = temporaryArray[counter][counter2];
	            					int tempX = temporaryArray[counter][counter2+1];
	            					gameList[tempY][tempX + 1] = gameList[tempY][tempX];
	            					gameList[tempY][tempX] = 0;
	            					temporaryArray[counter][counter2 + 1] = tempX + 1;
	            					gamePiece.setPieceCoordinates(temporaryArray);
	            					if (xCoordsToWatch[counter] == tempX){
	            						xCoordsToWatch[counter] = tempX + 1;
	            						gamePiece.setXBarriers(xCoordsToWatch);
	            					}
	            				}
	            				repaint(); 
	            			}
	            		}
	            		if (gamePiece.getShape() == 6) { // check the possible places that would hinder movement right for a piece of shape 6
	            			if (gameList[yCoordsToWatch[0]][xCoordsToWatch[0] + 1] == 0 && gameList[yCoordsToWatch[1]][xCoordsToWatch[1] + 1] == 0 && gameList[yCoordsToWatch[3]][xCoordsToWatch[3] + 1] == 0) {
	            				Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	            				for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one right
	            					Integer counter2 = 0;
	            					int tempY = temporaryArray[counter][counter2];
	            					int tempX = temporaryArray[counter][counter2+1];
	            					gameList[tempY][tempX + 1] = gameList[tempY][tempX];
	            					gameList[tempY][tempX] = 0;
	            					temporaryArray[counter][counter2 + 1] = tempX + 1;
	            					gamePiece.setPieceCoordinates(temporaryArray);
	            					if (xCoordsToWatch[counter] == tempX){
	            						xCoordsToWatch[counter] = tempX + 1;
	            						gamePiece.setXBarriers(xCoordsToWatch);
	            					}
	            				}
	            				repaint(); 
	            			}
	            		}
	            		if (gamePiece.getShape() == 7) { // check the possible places that would hinder movement right for a piece of shape 7
	            			if (gameList[yCoordsToWatch[0]][xCoordsToWatch[0] + 1] == 0 && gameList[yCoordsToWatch[2]][xCoordsToWatch[2] + 1] == 0) {
	            				Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	            				for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one right
	            					Integer counter2 = 0;
	            					int tempY = temporaryArray[counter][counter2];
	            					int tempX = temporaryArray[counter][counter2+1];
	            					gameList[tempY][tempX + 1] = gameList[tempY][tempX];
	            					gameList[tempY][tempX] = 0;
	            					temporaryArray[counter][counter2 + 1] = tempX + 1;
	            					gamePiece.setPieceCoordinates(temporaryArray);
	            					if (xCoordsToWatch[counter] == tempX){
	            						xCoordsToWatch[counter] = tempX + 1;
	            						gamePiece.setXBarriers(xCoordsToWatch);
	            					}
	            				}
	            				repaint(); 
	            			}
	            		}
	            	}
	           	}
	    		break;
			}
	        case KeyEvent.VK_DOWN: { // if the down key is pushed the game tries to move the piece down, if there is nothing blocking its way
	        	if (gamePiece.pieceMoving()) { // if the piece is moving
	        		Integer[] xCoordsToWatch = gamePiece.getXBarriers(); // get the barriers so that we can check if anything is blocking its way
	        		Integer[] yCoordsToWatch = gamePiece.getYBarriers();
	    			if (gamePiece.getShape() == 1) { // check the possible places that would hinder movement down for a piece of shape 1
	    				if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] == 0 && gameList[yCoordsToWatch[1] + 1][xCoordsToWatch[1]] == 0) {
	    					Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	    					for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one down
	    						Integer counter2 = 0;
	    						int tempY = temporaryArray[counter][counter2];
	    						int tempX = temporaryArray[counter][counter2+1];
	    						gameList[tempY + 1][tempX] = gameList[tempY][tempX];
	    						gameList[tempY][tempX] = 0;
	    						temporaryArray[counter][counter2] = tempY + 1;
	    						gamePiece.setPieceCoordinates(temporaryArray);
	    						if (yCoordsToWatch[counter] == tempY) {
	    							yCoordsToWatch[counter] = tempY + 1;
	    							gamePiece.setYBarriers(yCoordsToWatch);
	   							}
	   						}
	   						score += 1; // increase the score by one for making the piece fall faster
	   						repaint();
    					}
	    				else if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] != 0 || gameList[yCoordsToWatch[1] + 1][xCoordsToWatch[1]] != 0 || gameList[yCoordsToWatch[2] + 1][xCoordsToWatch[2]] != 0  || gameList[yCoordsToWatch[3] + 1][xCoordsToWatch[3]] !=0) {
	    					gamePiece.setPieceMoving(false); // if the piece has anything underneath it, set the piece moving to false
	    				}
	    			}
	    			if (gamePiece.getShape() == 2) { // check the possible places that would hinder movement down for a piece of shape 2
	    				if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] == 0) {
	    					Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	    					for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one down
	    						Integer counter2 = 0;
	    						int tempY = temporaryArray[counter][counter2];
	    						int tempX = temporaryArray[counter][counter2+1];
	    						gameList[tempY + 1][tempX] = gameList[tempY][tempX];
	    						gameList[tempY][tempX] = 0;
	    						temporaryArray[counter][counter2] = tempY + 1;
	    						gamePiece.setPieceCoordinates(temporaryArray);
	    						if (yCoordsToWatch[counter] == tempY){
	   								yCoordsToWatch[counter] = tempY + 1;
	   								gamePiece.setYBarriers(yCoordsToWatch);
	   							}
	    					}
	    					score +=1; // increase the score by one for making the piece fall faster
	    					repaint();
	    				}
	    				else if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] != 0 || gameList[yCoordsToWatch[1] + 1][xCoordsToWatch[1]] != 0 || gameList[yCoordsToWatch[2] + 1][xCoordsToWatch[2]] != 0  || gameList[yCoordsToWatch[3] + 1][xCoordsToWatch[3]] !=0) {
	    					gamePiece.setPieceMoving(false); // if the piece has anything underneath it, set the piece moving to false
	    				}
	    			}
	    			if (gamePiece.getShape() == 3) { // check the possible places that would hinder movement down for a piece of shape 3
	    				if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] == 0 && gameList[yCoordsToWatch[1] + 1][xCoordsToWatch[1]] == 0) {
	    					Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	    					for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one down
	    						Integer counter2 = 0;
	    						int tempY = temporaryArray[counter][counter2];
	    						int tempX = temporaryArray[counter][counter2+1];
	    						gameList[tempY + 1][tempX] = gameList[tempY][tempX];
	    						gameList[tempY][tempX] = 0;
	   							temporaryArray[counter][counter2] = tempY + 1;
	   							gamePiece.setPieceCoordinates(temporaryArray);
	   							if (yCoordsToWatch[counter] == tempY){
	   								yCoordsToWatch[counter] = tempY + 1;
	    							gamePiece.setYBarriers(yCoordsToWatch);
	    						}
	    					}
	    					score+=1; // increase the score by one for making the piece fall faster
	    					repaint();
	    				}
	    				else if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] != 0 || gameList[yCoordsToWatch[1] + 1][xCoordsToWatch[1]] != 0 || gameList[yCoordsToWatch[2] + 1][xCoordsToWatch[2]] != 0  || gameList[yCoordsToWatch[3] + 1][xCoordsToWatch[3]] !=0) {
	    					gamePiece.setPieceMoving(false); // if the piece has anything underneath it, set the piece moving to false
	    				}
	    			}
	    			if (gamePiece.getShape() == 4) { // check the possible places that would hinder movement down for a piece of shape 4
	    				if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] == 0 && gameList[yCoordsToWatch[2] + 1][xCoordsToWatch[2]] == 0) {
	    					Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	    					for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one down
	    						Integer counter2 = 0;
	    						int tempY = temporaryArray[counter][counter2];
	    						int tempX = temporaryArray[counter][counter2+1];
	    						gameList[tempY + 1][tempX] = gameList[tempY][tempX];
	    						gameList[tempY][tempX] = 0;
	    						temporaryArray[counter][counter2] = tempY + 1;
	    						gamePiece.setPieceCoordinates(temporaryArray);
	    						if (yCoordsToWatch[counter] == tempY){
	    							yCoordsToWatch[counter] = tempY + 1;
	    							gamePiece.setYBarriers(yCoordsToWatch);
	    						}
	    					}
	    					score+=1; // increase the score by one for making the piece fall faster
	    					repaint();
	    				}
	    				else if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] != 0 || gameList[yCoordsToWatch[2] + 1][xCoordsToWatch[2]] != 0) {
	    					gamePiece.setPieceMoving(false); // if the piece has anything underneath it, set the piece moving to false
	    				}
	    			}
	    			if (gamePiece.getShape() == 5) { // check the possible places that would hinder movement down for a piece of shape 5
	    				if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] == 0 && gameList[yCoordsToWatch[3] + 1][xCoordsToWatch[3]] == 0) {
	    					Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	    					for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one down
	    						Integer counter2 = 0;
	    						int tempY = temporaryArray[counter][counter2];
	    						int tempX = temporaryArray[counter][counter2+1];
	    						gameList[tempY + 1][tempX] = gameList[tempY][tempX];
	    						gameList[tempY][tempX] = 0;
	    						temporaryArray[counter][counter2] = tempY + 1;
	    						gamePiece.setPieceCoordinates(temporaryArray);
	    						if (yCoordsToWatch[counter] == tempY){
	    							yCoordsToWatch[counter] = tempY + 1;
	    							gamePiece.setYBarriers(yCoordsToWatch);
	    						}
	    					}
	    					score+=1; // increase the score by one for making the piece fall faster
	    					repaint();
	    				}
	    				else if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] != 0 || gameList[yCoordsToWatch[3] + 1][xCoordsToWatch[3]] != 0) {
	    					gamePiece.setPieceMoving(false); // if the piece has anything underneath it, set the piece moving to false
	    				}
	    			}
	    			if (gamePiece.getShape() == 6) { // check the possible places that would hinder movement down for a piece of shape 6
	    				if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] == 0 && gameList[yCoordsToWatch[2] + 1][xCoordsToWatch[2]] == 0) {
	    					Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	    					for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one down
	    						Integer counter2 = 0;
	    						int tempY = temporaryArray[counter][counter2];
	    						int tempX = temporaryArray[counter][counter2+1];
	    						gameList[tempY + 1][tempX] = gameList[tempY][tempX];
	    						gameList[tempY][tempX] = 0;
	    						temporaryArray[counter][counter2] = tempY + 1;
	    						gamePiece.setPieceCoordinates(temporaryArray);
	    						if (yCoordsToWatch[counter] == tempY){
	    							yCoordsToWatch[counter] = tempY + 1;
	    							gamePiece.setYBarriers(yCoordsToWatch);
	   							}
	    					}
	    					score+=1; // increase the score by one for making the piece fall faster
	    					repaint();
	    				}
	    				else if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] != 0 || gameList[yCoordsToWatch[2] + 1][xCoordsToWatch[2]] != 0) {
	    					gamePiece.setPieceMoving(false); // if the piece has anything underneath it, set the piece moving to false
	    				}
	    			}
	    			if (gamePiece.getShape() == 7) { // check the possible places that would hinder movement down for a piece of shape 7
	    				if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] == 0 && gameList[yCoordsToWatch[1] + 1][xCoordsToWatch[1]] == 0 && gameList[yCoordsToWatch[3] + 1][xCoordsToWatch[3]] == 0) {
	    					Integer[][] temporaryArray = gamePiece.getPieceCoordinates();
	    					for (int counter = 0; counter < 4; counter++) { // move each of the 4 "blocks" one down
	    						Integer counter2 = 0;
	    						int tempY = temporaryArray[counter][counter2];
	    						int tempX = temporaryArray[counter][counter2+1];
	    						gameList[tempY + 1][tempX] = gameList[tempY][tempX];
	    						gameList[tempY][tempX] = 0;
	    						temporaryArray[counter][counter2] = tempY + 1;
	    						gamePiece.setPieceCoordinates(temporaryArray);
	   							if (yCoordsToWatch[counter] == tempY){
	    							yCoordsToWatch[counter] = tempY + 1;
	    							gamePiece.setYBarriers(yCoordsToWatch);
	    						}
	    					}
	    					score+=1; // increase the score by one for making the piece fall faster
	    					repaint();
	    				}
	    				else if (gameList[yCoordsToWatch[0] + 1][xCoordsToWatch[0]] != 0 || gameList[yCoordsToWatch[1] + 1][xCoordsToWatch[1]] != 0 || gameList[yCoordsToWatch[3] + 1][xCoordsToWatch[3]] != 0) {
	    					gamePiece.setPieceMoving(false); // if the piece has anything underneath it, set the piece moving to false
	    				}
	    			}
	            }
	            break;
	        }
	        case KeyEvent.VK_SPACE : { // if the space bar is pushed the game pauses by suspending the gameThread thread
	        	if (!gamePaused) {
	           		gameThread.suspend();
	           		gamePaused = true;
	           		repaint();
	            }
	        	else {
	        		gameThread.resume();
	        		gamePaused = false;
	        		repaint();
	            }
	        	break;
	        }	
		}	 
	}
	public void keyReleased(KeyEvent e) {} 
	public void keyTyped(KeyEvent f) {}

	public void restartGame(Boolean restartBool) { // restart the game
		restartGame = restartBool;
	}
	private void clearGameBoard () { // clear the board by making the spots visible on the screen 0, and the ones to the bottom just off screen 1
		Integer counter1 = 0;
		Integer counter2 = 0;
		
		while (counter1 <= 24) {
			counter2 = 0;
			while (counter2 < 12) {
				if (counter1 == 24) {
					gameList[counter1][counter2] = 1;
					counter2 +=1;
				}
				else {
					gameList[counter1][counter2] = 0;
					counter2 += 1;
				}
			}
			counter1 += 1;
		}
	}   
}