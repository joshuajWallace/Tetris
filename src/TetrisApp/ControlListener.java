package TetrisApp;
import java.awt.event.*;

/**
 * @author Josh
 *
 */
public class ControlListener implements KeyListener {

	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent event) {
		//right
		if(event.getKeyCode() == 39) {
			GamePanel.moveRight();
		}
		//left
		if(event.getKeyCode() == 37) {
			//GamePanel.moveLeft();
		}
		//up
		if(event.getKeyCode() == 38) {
			
		} 
		//down
		if(event.getKeyCode() == 40) {
			
		}
		
	}

	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

}
