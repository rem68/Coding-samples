package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JPanel;


public class GamePiece extends JPanel {
	Boolean pieceContinuing = true; // is the piece still continuing?
	Integer shape = 0;
	Integer[] xBarriers = new Integer[4]; // the x values of the 4 blocks of a piece that need to be watched to make sure they can move to the next spot
	Integer[] yBarriers = new Integer[4]; // the y values of the 4 blocks of a piece that need to be watched to make sure they can move to the next spot
	Integer[][] pieceCoordinates = new Integer[4][2]; // holds the y and x coordinates of the 4 blocks of a piece
	
	public GamePiece(Integer s) {
		shape = s;
		switch (shape) {
            case 1 : {
            	//color = (255, 0, 0) aka red 
            	pieceCoordinates[0][0] = 5; //coordinates of the game piece from the gameList 2d array (y,x)
            	pieceCoordinates[0][1] = 5; // (5,5)
            	pieceCoordinates[1][0] = 5;
            	pieceCoordinates[1][1] = 6; // (5,6)
            	pieceCoordinates[2][0] = 4;
            	pieceCoordinates[2][1] = 6; // (4,6)
            	pieceCoordinates[3][0] = 3;
            	pieceCoordinates[3][1] = 6; // (3,6)
            	
            	xBarriers[0] = 5; // watch the x = 5 from the first point
            	xBarriers[1] = 6; // watch the x = 6 from the second point
            	xBarriers[2] = 6; // watch the x = 6 from the third point
            	xBarriers[3] = 6; // watch the x = 6 from the fourth point
            	
            	yBarriers[0] = 5; // watch the y = 5 from the first point
            	yBarriers[1] = 5; // watch the y = 5 from the second point
            	yBarriers[2] = 4; // watch the y = 4 from the third point
            	yBarriers[3] = 3; // watch the y = 3 from the fourth point
            	
            	break;
            }
            case 2 : {
            	//color = (0, 0, 255) aka blue               	
            	pieceCoordinates[0][0] = 5; //coordinates of the game piece from the gameList 2d array (y,x)
            	pieceCoordinates[0][1] = 5; // (5,5)
            	pieceCoordinates[1][0] = 4;
            	pieceCoordinates[1][1] = 5; // (4,5)
            	pieceCoordinates[2][0] = 3; 
            	pieceCoordinates[2][1] = 5; // (3,5)
            	pieceCoordinates[3][0] = 2;
            	pieceCoordinates[3][1] = 5; // (2,5)
            	
            	xBarriers[0] = 5; // watch the x = 5 from the first point
            	xBarriers[1] = 5; // watch the x = 5 from the second point
            	xBarriers[2] = 5; // watch the x = 5 from the third point
            	xBarriers[3] = 5; // watch the x = 5 from the fourth point
            	
            	yBarriers[0] = 5; // watch the y = 5 from the first point
            	yBarriers[1] = 4; // watch the y = 4 from the second point 
            	yBarriers[2] = 3; // watch the y = 3 from the third point
            	yBarriers[3] = 2; // watch the y = 2 from the fourth point
            	
            	break;
            }
            case 3 : {
            	//color = (0, 153, 0) aka green               	
            	pieceCoordinates[0][0] = 5; //coordinates of the game piece from the gameList 2d array (y,x)
            	pieceCoordinates[0][1] = 5; // (5,5)
            	pieceCoordinates[1][0] = 5;
            	pieceCoordinates[1][1] = 6; // (5,6)
            	pieceCoordinates[2][0] = 4;
            	pieceCoordinates[2][1] = 5; // (4,5)
            	pieceCoordinates[3][0] = 4;
            	pieceCoordinates[3][1] = 6; // (4,6)
            	
            	xBarriers[0] = 5; // watch the x = 5 from the first point
            	xBarriers[1] = 6; // watch the x = 6 from the second point
            	xBarriers[2] = 5; // watch the x = 5 from the third point
            	xBarriers[3] = 6; // watch the x = 6 from the fourth point
            	
            	yBarriers[0] = 5; // watch the y = 5 from the first point
            	yBarriers[1] = 5; // watch the y = 5 from the second point
            	yBarriers[2] = 4; // watch the y = 4 from the third point
            	yBarriers[3] = 4; // watch the y = 4 from the fourth point
            	
            	break;
            }
            case 4 : {
            	//color = (255, 255, 0) aka yellow
            	pieceCoordinates[0][0] = 5; //coordinates of the game piece from the gameList 2d array (y,x)
            	pieceCoordinates[0][1] = 5; // (5,5)
            	pieceCoordinates[1][0] = 4;
            	pieceCoordinates[1][1] = 5; // (4,5)
            	pieceCoordinates[2][0] = 4;
            	pieceCoordinates[2][1] = 6; // (4,6)
            	pieceCoordinates[3][0] = 3;
            	pieceCoordinates[3][1] = 5; // (3,5)
            	
            	xBarriers[0] = 5; // watch the x = 5 from the first point
            	xBarriers[1] = 5; // watch the x = 5 from the second point
            	xBarriers[2] = 6; // watch the x = 6 from the third point
            	xBarriers[3] = 5; // watch the x = 5 from the fourth point
            	
            	yBarriers[0] = 5; // watch the y = 5 from the first point
            	yBarriers[1] = 4; // watch the y = 4 from the second point
            	yBarriers[2] = 4; // watch the y = 4 from the third point
            	yBarriers[3] = 3; // watch the y = 3 from the fourth point
            	break;
            }
            case 5 : {
            	//color = (255, 111, 0) aka orange
            	pieceCoordinates[0][0] = 5; //coordinates of the game piece from the gameList 2d array (y,x)
            	pieceCoordinates[0][1] = 6; // (5,6)
            	pieceCoordinates[1][0] = 4;
            	pieceCoordinates[1][1] = 6; // (4,6)
            	pieceCoordinates[2][0] = 3;
            	pieceCoordinates[2][1] = 6; // (3,6)
            	pieceCoordinates[3][0] = 3;
            	pieceCoordinates[3][1] = 5; // (3,5)
            	
            	xBarriers[0] = 6; // watch the x = 6 from the first point
            	xBarriers[1] = 6; // watch the x = 6 from the second point
            	xBarriers[2] = 6; // watch the x = 6 from the third point
            	xBarriers[3] = 5; // watch the x = 5 from the fourth point
            	
            	yBarriers[0] = 5; // watch the y = 5 from the first point 
            	yBarriers[1] = 4; // watch the y = 4 from the second point
            	yBarriers[2] = 3; // watch the y = 3 from the third point
            	yBarriers[3] = 3; // watch the y = 3 from the fourth point
            	break;
            }
            case 6 : {
            	//color = (102, 0, 204) purple
            	pieceCoordinates[0][0] = 5; //coordinates of the game piece from the gameList 2d array (y,x)
            	pieceCoordinates[0][1] = 6; // (5,6)
            	pieceCoordinates[1][0] = 4;
            	pieceCoordinates[1][1] = 6; // (4,6)
            	pieceCoordinates[2][0] = 4;
            	pieceCoordinates[2][1] = 5; // (4,5)
            	pieceCoordinates[3][0] = 3;
            	pieceCoordinates[3][1] = 5; // (3,5)
            	
            	xBarriers[0] = 6; // watch the x = 6 from the first point
            	xBarriers[1] = 6; // watch the x = 6 from the second point
            	xBarriers[2] = 5; // watch the x = 5 from the third point
            	xBarriers[3] = 5; // watch the x = 5 from the fourth point
            	
            	yBarriers[0] = 5; // watch the y = 5 from the first point
            	yBarriers[1] = 4; // watch the y = 4 from the second point
            	yBarriers[2] = 4; // watch the y = 4 from the third point
            	yBarriers[3] = 3; // watch the y = 3 from the fourth point
            	break;
            }
            case 7 : {
            	//color = (204, 0, 102) aka magenta
            	pieceCoordinates[0][0] = 5; //coordinates of the game piece from the gameList 2d array (y,x)
            	pieceCoordinates[0][1] = 7; // (5,7)
            	pieceCoordinates[1][0] = 5;
            	pieceCoordinates[1][1] = 6; // (5,6)
            	pieceCoordinates[2][0] = 4;
            	pieceCoordinates[2][1] = 6; // (4,6)
            	pieceCoordinates[3][0] = 4;
            	pieceCoordinates[3][1] = 5; // (4,5)
            	
            	xBarriers[0] = 7; // watch the x = 7 from the first point
            	xBarriers[1] = 6; // watch the x = 6 from the second point
            	xBarriers[2] = 6; // watch the x = 6 from the third point
            	xBarriers[3] = 5; // watch the x = 5 from the fourth point
            	
            	yBarriers[0] = 5; // watch the y = 5 from the first point
            	yBarriers[1] = 5; // watch the y = 5 from the second point
            	yBarriers[2] = 4; // watch the y = 4 from the third point
            	yBarriers[3] = 4; // watch the y = 4 from the fourth point
            	break;
            }
		}	

	}

	public void setPieceMoving(Boolean b) {
    	pieceContinuing = b; // used to set the piece to stop moving
    }
	public Boolean pieceMoving() {
		return pieceContinuing;
	}

	public Integer getShape() {
		return shape;
	}
	public Integer[] getXBarriers() {
		return xBarriers;
	}
	public Integer[] getYBarriers() {
		return yBarriers;
	}
	public void setXBarriers(Integer[] incomingBarriers) {
		xBarriers = incomingBarriers;
	}
	public void setYBarriers(Integer[] incomingBarriers) {
		yBarriers = incomingBarriers;
	}
	public Integer[][] getPieceCoordinates() {
		return pieceCoordinates;
	}
	public void setPieceCoordinates(Integer[][] a) {
		pieceCoordinates = a;
	}
}