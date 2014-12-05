import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Sprite {
	public int xPos;
	public int yPos;
	
	public int width;
	public int height;
	
	//array of States (images to loop through while in state, name, and movement type)
	
	public BufferedImage initialState;//For testing purposes
	
	//THIS IS FOR TESTING PURPOSES ONLY. WILL BE AN ABSTRACT CLASS WITH SPRITES AS SUBCLASSES
	public Sprite() throws IOException {
		xPos = 10;
		yPos = 10;
		
		//default width and height (would change during game)
		width = 32;
		height = 32;
		
		initialState = ImageIO.read(new File("mario_Left1.png"));
		
		//TODO: Doesn't work; necessary if Mario is to change size during the game
		/*Image img = ImageIO.read(new File("mario_Left1.png"));
		img = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		BufferedImage initialState = (BufferedImage) img;*/
	}
	
	public Rectangle getBoundingBox(){
		return new Rectangle (xPos, yPos, width, height);
	}
	
	public void update(int delta, int vertDelta){
		xPos += delta;
		yPos += vertDelta;
	}
	
}

/* SPRITE ABSTRACT CLASS
 *  sprite has a position
 *  a state (way facing, ifJumping, crouched, etc)
 *  a way that interacts with others (depends on angle of collision too)
 *  name/description (of type enemy)
 * 
 */
