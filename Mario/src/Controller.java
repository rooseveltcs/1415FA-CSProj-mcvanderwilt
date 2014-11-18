import java.awt.event.KeyEvent;


public class Controller {
	
	public int MarioXPos;
	public int MarioYPos;
	
	private boolean leftKeyPressed;
	private boolean rightKeyPressed;
	private boolean upKeyPressed;
	
	public Controller() {

	}
	
	private void processKeyPress (KeyEvent e){
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_LEFT){
			leftKeyPressed = true;
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
}


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