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
	
	private final long serialVersionUID = -761825656979462027L;
	private boolean canvas[][] = new boolean[16][26];
	private int yPosition = 2;
	private int xPosition = 3;
	private TetrominoBag bag = new TetrominoBag();
	private Tetromino currentPiece;
	private final int MAX_X = 12;
	private final int MAX_Y = 23;
	private final int MIN_X = 1;
	private final int MIN_Y = 1;
	
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
		for(int i =0; i < MAX_X; i++)
			Arrays.fill(canvas[i], false);
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
	public void nextPiece() {	
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
		if(yPosition + currentPiece.length() -1 == MAX_Y ) {
			nextPiece();
		}
		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				if(y == 0) {
					if(currentPiece.value(x, currentPiece.length() - 1) && canvas[xPosition + x][yPosition +currentPiece.length()]) {
						nextPiece();	
					}
				}
				else
					if(!currentPiece.value(x, currentPiece.length() - y) && currentPiece.value(x, currentPiece.length() - (1+y)) && canvas[xPosition + x][yPosition +currentPiece.length() - y]){
						nextPiece();
				}
			
			}
		}
		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				if(currentPiece.value(x, y) && canvas[xPosition + x][yPosition + y])
					canvas[xPosition + x][yPosition + y] = false;				
			}
		}
		yPosition++;
		insertPiece();
	}
	public void moveRight() {
		if(xPosition + currentPiece.length() > MAX_X ){
			return;
		}
		//needs to deal with out of bounds, catch somehow
		for(int x = 0 ; x < (MAX_X - xPosition) && x < currentPiece.length() ; x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				/*if(x==0)
					if(currentPiece.value(currentPiece.length() - (x+ 1), y) && canvas[xPosition + currentPiece.length() - x][yPosition + y] )
						return;
				if(currentPiece.value(currentPiece.length() - (x+ 1), y) && canvas[xPosition + currentPiece.length() - x][yPosition + y] && !currentPiece.value(currentPiece.length() - x, y)) {
					return;
				}*/
				if( canvas[xPosition + currentPiece.length() - x][yPosition + y] && canvas[xPosition + currentPiece.length() - x + 1][yPosition + y])
					return;
			}
		}

		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				if(xPosition + x <= MAX_X && currentPiece.value(x, y))
					canvas[xPosition + x][yPosition + y] = false;				
			}
		}
		xPosition++;
		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				if(currentPiece.value(x, y))
					canvas[xPosition + x][yPosition + y] = currentPiece.value(x, y);
			}
		}
		
	}
	public boolean checkRow() {
		for(int i =0; i <= MAX_X; i++) {
			for(int a = 0; a <= MAX_Y; a++) {
				if()
			}
		
	}
	public void moveLeft() {
		if(xPosition <= MIN_X ) {
			return;
		}
		for(int x = 0 ; x < xPosition && x <  currentPiece.length() ; x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				if(x == 0 && currentPiece.value(x, y) && canvas[xPosition + x - 1][yPosition + y]) {
					return;	
				}
				else if(x > 0)
						if(!currentPiece.value(x, y) && currentPiece.value(x - 1, y) && canvas[xPosition - x][yPosition + y]){
							return;
				}			
			}
		}

		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				if(xPosition + x <= MAX_X)
					canvas[xPosition + x][yPosition + y] = false;				
			}
		}
		xPosition--;
		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				if(currentPiece.value(x, y))
					canvas[xPosition + x][yPosition + y] = currentPiece.value(x, y);
			}
		}

		
	}
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		for(int x =0; x <= MAX_X; x++) {
			for(int y = 0; y <= MAX_Y; y++) {
				if(canvas[x][y]) {
					int xDraw = (x*30 - 30);
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
