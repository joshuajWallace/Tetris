package app;
import java.awt.BorderLayout;

import javax.swing.JFrame;

public class App {
	//shows game state, score and next tetromino
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(new ScorePanel(), BorderLayout.WEST);
		frame.add(new GamePanel(), BorderLayout.CENTER);
		frame.pack();

		frame.setVisible(true);
		frame.setResizable(false);
	}
	//generate tetromino
	//timer move tetromino down - repaint
	//press arrow rotate left right, speed down

}