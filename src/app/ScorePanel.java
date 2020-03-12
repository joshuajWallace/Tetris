package app;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.CodeSource;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7730153057695726313L;
	private static JLabel highScore = new JLabel("0");
	private static int score = 0;
	private static JLabel currentScore = new JLabel("0");
	private Font defaultFont = new Font("TimesRoman", Font.BOLD, 30);
	public ScorePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		final JLabel SCORE = new JLabel("Score:");
		SCORE.setFont(defaultFont);
		add(SCORE);
		updateScore(score);
		currentScore.setFont(defaultFont);
		currentScore.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		add(currentScore);
		final JLabel HIGH_SCORE = new JLabel("High Score:");
		HIGH_SCORE.setFont(defaultFont);
		add(HIGH_SCORE);
		highScore.setFont(defaultFont);
		highScore.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		getHighScore();
		add(highScore);
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
		currentScore.setText(Integer.toString(score));
	}
	public static void resetScore() {
		int test = Integer.parseInt(highScore.getText());
		if(test < score) {
			highScore.setText(Integer.toString(score));
			setHighScore();
		}
		score = 0;
		currentScore.setText(Integer.toString(score));
	}
	public void getHighScore() {
		BufferedReader rd;
		try {
			CodeSource codeSource = App.class.getProtectionDomain().getCodeSource();
			File jarFile = new File(codeSource.getLocation().toURI().getPath());
			String file = jarFile.getParentFile().getPath();
			rd = new BufferedReader(new FileReader(new File(file + "\\highScore.txt")));
			highScore.setText(rd.readLine());
			rd.close();			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void setHighScore() {
		BufferedWriter bw;
		try {
			CodeSource codeSource = App.class.getProtectionDomain().getCodeSource();
			File jarFile = new File(codeSource.getLocation().toURI().getPath());
			String file = jarFile.getParentFile().getPath();
			bw = new BufferedWriter(new FileWriter(new File(file + "\\highScore.txt")));
			bw.write(highScore.getText());
			bw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
