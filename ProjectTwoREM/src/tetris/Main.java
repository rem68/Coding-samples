package tetris;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

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

public class Main extends JFrame {
	GameScreen gameScreen;
	public Main() {
		initializeUserInterface();
	}

	public final void initializeUserInterface() {
		gameScreen = new GameScreen(301,541);

		JMenuBar barForMenu = new JMenuBar();
		
		JMenu fileOption = new JMenu("File"); // menu with file, new game and exit
		fileOption.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem fileNewGame = new JMenuItem("New Game");
		fileNewGame.setMnemonic(KeyEvent.VK_N);
		fileNewGame.setToolTipText("Restart Tetris Game");
		fileNewGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		fileNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Boolean restartBool = true;
				gameScreen.restartGame(restartBool);
			}
			
		});
		
		JMenuItem fileExitGame = new JMenuItem("Exit Game");
		fileExitGame.setMnemonic(KeyEvent.VK_Q);
		fileExitGame.setToolTipText("Exit Tetris application");
		fileExitGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		
		fileExitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		fileOption.add(fileNewGame);
		fileOption.add(fileExitGame);
		
		barForMenu.add(fileOption);
		
	    setJMenuBar(barForMenu);

	    setTitle("Tetris");
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	     
    	setTitle("Emmett's Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(301, 585);
        add(gameScreen);
	}
	
	
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Main tetrisGame = new Main();
				tetrisGame.setVisible(true);  
			}
	     });
	 }
}