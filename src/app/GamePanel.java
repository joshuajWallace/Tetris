package app;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel{
	
	private static final long serialVersionUID = -761825656979462027L;
	private static boolean canvas[][] = new boolean[12][24];
	private static int yPosition = 0;
	private static int xPosition = 0;
	private static TetrominoBag bag = new TetrominoBag();
	private static Tetromino currentPiece;
	private  Timer speed = new Timer(500 , new RepaintListener());


	public GamePanel() {
		setPreferredSize(new Dimension(330, 720));
		setBackground(Color.black);
		addKeyListener(new ControlListener());
		setFocusable(true);
		newGame();
	}
	public void newGame() {
		for(int i =0; i < 12; i++)
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
		yPosition = 0;
		xPosition = 0;
		for(int x = 0 ; x < currentPiece.length(); x++){
			if(canvas[xPosition][yPosition]) {
				speed.stop();
				newGame();
			}			
		}
		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				canvas[xPosition + x][yPosition + y] = currentPiece.value(x, y);
			}
		}
		repaint();		
	}
	public void moveFall() {
		if(yPosition + currentPiece.length() > 23) {
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

		for(int y = 0; y < 23; y++) {
			if(y < yPosition) {
				canvas[xPosition][y] = false;
			}
		}
		insertPiece();
	}
	public static void moveRight() {
		if(xPosition == 9) {
			return;
		}
		for(int y = 0 ; y < currentPiece.length(); y++){			
				if(canvas[xPosition + currentPiece.length()][yPosition + y] && currentPiece.value(currentPiece.length() -1, y)) {
					return;					
				}				
			}

		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
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
	public void moveLeft() {
		if(xPosition == 0 ) {
			return;
		}
		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				if(currentPiece.value(x, y) && canvas[xPosition + x - 1][yPosition + y]) {
					return;	
				}
				else if(x > 0)
						if(!currentPiece.value(x, y) && currentPiece.value(x - 1, y) && canvas[xPosition - x][yPosition + y]){
							nextPiece();
				}			
			}
		}

		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
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
		for(int i =0; i < 12; i++) {
			for(int a = 0; a< 24; a++) {
				if(canvas[i][a]) {
					int x = i*30;
					int y = a*30;
					g.setColor(Color.green);
					g.fillRect(x, y, 30, 30);
					g.setColor(Color.YELLOW);
					g.drawRect(x, y, 30, 30);
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
