package TetrisApp;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class ScorePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7730153057695726313L;
	
	private int score = 0;
	private JTextField informationGameState = new JTextField(8);
	public ScorePanel() {
		updateScore(score);
		informationGameState.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		informationGameState.setEditable(false);
		add(informationGameState);
		setPreferredSize(new Dimension(100,20));
	}
	public void rowsCleared(int numberOfRows) {
		switch(numberOfRows) {
		case 1:
			updateScore(40);
		case 2: 
			updateScore(100);
		case 3: 
			updateScore(400);
		case 4:
			updateScore(1200);
		default: return;
		}
	}
	public void updateScore(int add) {
		score += add;
		informationGameState.setText(String.valueOf(score));
	}

}
