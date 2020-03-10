package app;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

public class GamePanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private boolean canvas[][] = new boolean[18][26];
	private TetrominoBag bag = new TetrominoBag();
	private Tetromino currentPiece;
	private final int MAX_X = 13;
	private final int MAX_Y = 24;
	private final int MIN_X = 4;
	private final int MIN_Y = 0;
	private int yPosition = MIN_Y;
	private int xPosition = MIN_X;
	
	private  Timer speed = new Timer(500 , new RepaintListener());


	public GamePanel() {
		setPreferredSize(new Dimension(300, 720));
		setBackground(Color.black);
		setBorder(BorderFactory.createLineBorder(Color.blue));
		addKeyListener(new ControlListener());
		setFocusable(true);
		newGame();
	}
	
	
	public void newGame() {
		for(int i =0; i <= MAX_X; i++)
			Arrays.fill(canvas[i], false);
		for(int i =0; i <= MAX_X; i++)
			canvas[i][MAX_Y] = true;
		Arrays.fill(canvas[MIN_X - 1] , true);
		Arrays.fill(canvas[MAX_X + 1], true);
		bag.randomise();
		currentPiece = bag.getCurrent();
		insertPiece();
		repaint();
		speed.start();
	}
	
	
	public void insertPiece() {
		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				if(currentPiece.value(x, y) && !canvas[xPosition + x][yPosition + y])
					canvas[xPosition + x][yPosition + y] = currentPiece.value(x, y);
			}
		}
	}
	
	
	public void deletePiece() {
		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				if(currentPiece.value(x, y) && canvas[xPosition + x][yPosition + y])
					canvas[xPosition + x][yPosition + y] = false;				
			}
		}
	}
	
	
	public void rotate(){
		Tetromino temp = new Tetromino(currentPiece.getShape());
		temp.rotate();
		deletePiece();
		for(int x = 0 ; x < temp.length() ; x++){
			for(int y =  0; y < temp.length() ; y++) {
				if( canvas[xPosition + x][yPosition + y] && temp.value(x, y)){				
					insertPiece();
					return;
				}
			}
		}
		currentPiece.rotate();
		insertPiece();
		repaint();
	}
	
	
	public void nextPiece() {
		checkRows();
		currentPiece = bag.getNext();
		yPosition = MIN_Y;
		xPosition = MIN_X;
		for(int x = 0 ; x < currentPiece.length(); x++){
			if(canvas[xPosition][yPosition]) {
				speed.stop();
				newGame();
			}			
		}
		insertPiece();
		repaint();		
	}
	
	
	public void moveFall() {
		deletePiece();
		for(int x = 0 ; x < currentPiece.length() ; x++){
			for(int y =  0; y < currentPiece.length() ; y++) {
				if(canvas[xPosition + x][yPosition + currentPiece.length() - y]  && currentPiece.value(x, currentPiece.length() -1 - y)) {
					insertPiece();
					nextPiece();
					return;
				}	
			}
		}
		yPosition++;
		insertPiece();
	}
	
	
	public void moveRight() {		deletePiece();
		deletePiece();
		for(int x = 0 ; x < currentPiece.length() ; x++){
			for(int y =  0; y < currentPiece.length() ; y++) {
				if(canvas[xPosition + currentPiece.length() - x][yPosition +  y]  && currentPiece.value(currentPiece.length() -1 - x, y)) {
					insertPiece();
					return;
				}	
			}
		}
		xPosition++;
		insertPiece();		
	}
	
	
	public void moveLeft() {
		deletePiece();
		for(int x = 0 ; x < currentPiece.length() ; x++){
			for(int y =  0; y < currentPiece.length() ; y++) {
				if(canvas[xPosition + x - 1][yPosition +  y]  && currentPiece.value(x, y)) {
					insertPiece();
					return;
				}	
			}
		}
		xPosition--;
		insertPiece();
	}
	
	
	public void checkRows() {
		int numberCleared = 0;
		for(int y = MIN_Y; y < MAX_Y; y++) {
			boolean full = true;
			for(int x = MIN_X; x <= MAX_X; x++) {
				if(!canvas[x][y]) 
					full = false;
				}
			if(full) {
				clearRows(y);
				numberCleared++;
			}
		}
		ScorePanel.rowsCleared(numberCleared);
			
	}
	public void clearRows(int row) {
		for(int x= MIN_X; x <= MAX_X;x++) {
			for(int y= row; y > MIN_Y;y--)
				canvas[x][y] = canvas[x][y-1];
		}
		repaint();
	}

		

	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		for(int x = MIN_X; x <= MAX_X; x++) {
			for(int y = MIN_Y; y <= MAX_Y; y++) {
				if(canvas[x][y]) {
					int xDraw = (x*30 - 120);
					int yDraw = (y*30);
					g.setColor(Color.green);
					g.fillRect(xDraw, yDraw, 30, 30);
					g.setColor(Color.YELLOW);
					g.drawRect(xDraw, yDraw, 30, 30);
				}
			}
		}

	}
	
	
	private class RepaintListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			moveFall();
			repaint();
		}	
	}
	
	
	private class ControlListener implements KeyListener {

		public void keyTyped(KeyEvent event) {
			// No code - as its not used
		}

		public void keyPressed(KeyEvent event) {
			//right
			if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
				moveRight();
				repaint();

			}
			//left
			if(event.getKeyCode() == KeyEvent.VK_LEFT) {
				moveLeft();
				repaint();
			}
			//up
			if(event.getKeyCode() == KeyEvent.VK_UP) {
				rotate();
			} 
			//down
			if(event.getKeyCode() == KeyEvent.VK_DOWN) {
				speed.setDelay(50);
			}
			
		}

		public void keyReleased(KeyEvent event) {
			if(event.getKeyCode() == KeyEvent.VK_DOWN) {
				speed.setDelay(500);
			}
		}

	}

}
