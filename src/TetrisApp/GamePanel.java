package TetrisApp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

public class GamePanel extends JPanel {
	
	private static final long serialVersionUID = -761825656979462027L;
	private static boolean canvas[][] = new boolean[10][24];
	private static int yPosition = 0;
	private static int xPosition = 0;
	private TetrominoBag bag = new TetrominoBag();
	private static Tetromino currentPiece;
	private  Timer speed = new Timer(500 , new repaintListener());


	public GamePanel() {
		setPreferredSize(new Dimension(300, 720));
		setBackground(Color.black);
		addKeyListener(new ControlListener());
		newGame();
	}
	public void newGame() {
		for(int i =0; i < 10; i++)
			Arrays.fill(canvas[i], false);
		bag.randomise();
		currentPiece = bag.getCurrent();
		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				canvas[xPosition + x][yPosition + y] = currentPiece.value(x, y);
			}
		}
		speed.start();
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
		
	}
	public void moveShape() {
		if(yPosition + currentPiece.length() > 23) {
			nextPiece();
		}
		for(int x = 0 ; x < currentPiece.length(); x++){			
				if(canvas[xPosition + x][yPosition + currentPiece.length()] && currentPiece.value(x, currentPiece.length() -1)) {
					nextPiece();					
				}				
			}

		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				canvas[xPosition + x][yPosition + y] = false;				
			}
		}
		yPosition++;
		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				if(currentPiece.value(x, y))
					canvas[xPosition + x][yPosition + y] = currentPiece.value(x, y);
			}
		}

	}
	public static void moveRight() {
		// TODO Auto-generated method stub
		if(xPosition == 10 ) {
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
		yPosition++;
		for(int x = 0 ; x < currentPiece.length(); x++){
			for(int y = 0; y < currentPiece.length(); y++) {
				if(currentPiece.value(x, y))
					canvas[xPosition + x][yPosition + y] = currentPiece.value(x, y);
			}
		}
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		for(int i =0; i < 10; i++) {
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
		moveShape();

	}
	public class repaintListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			repaint();
		}	
	}

}
