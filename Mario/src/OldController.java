/*import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class OldController implements KeyListener {
	
	private Level level;
	private JPanel panel;
	
	public int MarioXPos;
	public int MarioYPos;
	
	private boolean leftKeyPressed;
	private boolean rightKeyPressed;
	private boolean upKeyPressed;
	
	public OldController(JPanel panel) {
		this.level = level;
		panel = level.getPanel();
		panel.setFocusable(true);
		panel.requestFocus();
		//panel.addKeyListener(new Controller());
	}
	
	
	private void processKeyPress (KeyEvent e){
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_LEFT){
			leftKeyPressed = true;
			System.out.println("Left");
			//Level drawn changes
			//Mario Position changes
			//Sprite position changes
			 
		} else if (keyCode == KeyEvent.VK_RIGHT){
			rightKeyPressed = true;
		} else if (keyCode == KeyEvent.VK_UP){
			upKeyPressed = true;
		}
	} 
	//TODO: Account for multiple keys pressed
	

	@Override
	public void keyPressed(KeyEvent e) {
		processKeyPress(e);
	}


	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("press");
	}


	@Override
	public void keyTyped(KeyEvent e) {
	}
}*/


//process key press
//updateLevel
	//what section of array is drawn
	//position of scrolling background
//updateSprites
	//COLLISION DETECTION
	//way Mario is facing
	//positions of other sprites (depending on Mario's movement)
//drawLevel
//drawSprites (or part of drawLevel)