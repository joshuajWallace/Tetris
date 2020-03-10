package app;

import java.awt.*;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7730153057695726313L;
	
	private static int score = 0;
	private static JLabel informationGameState = new JLabel("0");
	private Font defaultFont = new Font("TimesRoman", Font.BOLD, 30);
	public ScorePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		final JLabel SCORE = new JLabel("Score:");
		SCORE.setFont(defaultFont);
		add(SCORE);
		updateScore(score);
		informationGameState.setFont(defaultFont);
		informationGameState.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		add(informationGameState);
		setPreferredSize(new Dimension(200,20));

	}
	public static void rowsCleared(int numberOfRows) {
		switch(numberOfRows) {
		case 1:
			updateScore(40);
			break;
		case 2: 
			updateScore(100);
			break;
		case 3: 
			updateScore(400);
			break;
		case 4:
			updateScore(1200);
			break;
		default: return;
		}
	}
	public static void updateScore(int add) {
		score += add;
		informationGameState.setText(Integer.toString(score));;
	}

}
