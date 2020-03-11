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


public class GamePanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private int canvas[][] = new int[18][26];
	private TetrominoBag bag = new TetrominoBag();
	private Tetromino currentPiece;
	private final int MAX_X = 13;
	private final int MAX_Y = 24;
	private final int MIN_X = 4;
	private final int MIN_Y = 0;
	private int yPosition = MIN_Y;
	private int xPosition = MIN_X;
	private int linesCleared = 0;
	private int currentSpeed;
	private  Timer speed = new Timer(currentSpeed, new RepaintListener());


	public GamePanel() {
		setPreferredSize(new Dimension(300, 720));
		setBackground(Color.black);
		setBorder(BorderFactory.createLineBorder(Color.blue));
		addKeyListener(new ControlListener());
		setFocusable(true);
		try {
			Music music = new Music();
			music.play();
			newGame();
			}
		catch(Exception e) {
			newGame();
		}

	}
	
	
	public void newGame() {
		ScorePanel.resetScore();
		for(int i =0; i <= MAX_X; i++)
			Arrays.fill(canvas[i], 0);
		for(int i =0; i <= MAX_X; i++)
			canvas[i][MAX_Y] = 1;
		Arrays.fill(canvas[MIN_X - 1] , 1);
		Arrays.fill(canvas[MAX_X + 1], 1);
		linesCleared = 0;
		yPosition = MIN_Y;
		xPosition = MIN_X;
		bag.randomise();
		currentPiece = bag.getCurrent();
		insertPiece();
		repaint();
		currentSpeed = 800;
		speed.setDelay(currentSpeed);
		speed.start();
	}
	
	
	public void insertPiece() {
		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				if(currentPiece.value(x, y))
					canvas[xPosition + x][yPosition + y] = getColorValue();
			}
		}
	}
	
	public void deletePiece() {
		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				if(currentPiece.value(x, y) && canvas[xPosition + x][yPosition + y] != 0)
					canvas[xPosition + x][yPosition + y] = 0;				
			}
		}
	}
	
	
	public void rotate(){
		Tetromino temp = new Tetromino(currentPiece.getShape());
		temp.rotate();
		deletePiece();
		for(int x = 0 ; x < temp.length() ; x++){
			for(int y =  0; y < temp.length() ; y++) {
				if( canvas[xPosition + x][yPosition + y] != 0 && temp.value(x, y)){				
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
			if(canvas[xPosition][yPosition] != 0) {
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
				if(canvas[xPosition + x][yPosition + currentPiece.length() - y] != 0 && currentPiece.value(x, currentPiece.length() -1 - y)) {
					insertPiece();
					nextPiece();
					return;
				}	
			}
		}
		yPosition++;
		insertPiece();
	}
	
	
	public void moveRight() {
		deletePiece();
		for(int x = 0 ; x < currentPiece.length() ; x++){
			for(int y =  0; y < currentPiece.length() ; y++) {
				if(canvas[xPosition + currentPiece.length() - x][yPosition +  y] != 0  && currentPiece.value(currentPiece.length() -1 - x, y)) {
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
				if(canvas[xPosition + x - 1][yPosition +  y] != 0  && currentPiece.value(x, y)) {
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
				if(canvas[x][y] == 0) 
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
		linesCleared++;
		repaint();
	}
	private int getColorValue() {
		
		if(currentPiece.getColor() == Color.cyan)
			return 1;
		if(currentPiece.getColor() == Color.yellow)
			return 2;
		if(currentPiece.getColor() == Color.pink)
			return 3;
		if(currentPiece.getColor() == Color.green)
			return 4;
		if(currentPiece.getColor() == Color.red)
			return 5;
		if(currentPiece.getColor() == Color.blue)
			return 6;
		if(currentPiece.getColor() == Color.orange)
			return 7;
		else
			return 0;
	}

	public Color getColor(int colorPicker) {
		switch(colorPicker) {
		case 1:
			return Color.cyan;
		case 2:
			return Color.yellow;
		case 3:
			return Color.pink;
		case 4:
			return Color.green;
		case 5:
			return Color.red;
		case 6:
			return Color.blue;
		case 7:
			return Color.orange;
		default:return Color.black;
		}
				
	}

		

	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		for(int x = MIN_X; x <= MAX_X; x++) {
			for(int y = MIN_Y; y <= MAX_Y; y++) {
				if(canvas[x][y] != 0) {
					int xDraw = (x*30 - 120);
					int yDraw = (y*30);
					g.setColor(getColor(canvas[x][y]));
					g.fillRect(xDraw, yDraw, 30, 30);
					g.setColor(Color.GRAY);
					g.drawRect(xDraw, yDraw, 30, 30);
				}
			}
		}

	}
	
	
	private class RepaintListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(linesCleared > 6 && currentSpeed > 100) {
				linesCleared = 0;
				currentSpeed = speed.getDelay() - 100;
				speed.setDelay(currentSpeed);
			}	
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
				moveFall();
				repaint();
			}
			
		}

		public void keyReleased(KeyEvent event) {
			// No code - as its not used
			}
		}

	}
