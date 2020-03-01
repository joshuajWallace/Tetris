package TetrisApp;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GamePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -761825656979462027L;

	public GamePanel() {
		setPreferredSize(new Dimension(300, 720));
		setBackground(Color.black);
		Border line = BorderFactory.createLineBorder(Color.blue, 5);
		Border etched = BorderFactory.createEtchedBorder(Color.BLUE, Color.DARK_GRAY);
		setBorder(BorderFactory.createCompoundBorder(line, etched));
	}
}
