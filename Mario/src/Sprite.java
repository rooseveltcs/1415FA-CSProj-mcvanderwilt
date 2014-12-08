import java.awt.Graphics2D;
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
	
	public BufferedImage spriteImg;//created from initial image file (not resized)
	public BufferedImage defaultImg;//For testing purposes
	
	//THIS IS FOR TESTING PURPOSES ONLY. WILL BE AN ABSTRACT CLASS WITH SPRITES AS SUBCLASSES
	public Sprite() throws IOException {
		xPos = 10;
		yPos = 10;
		
		//default width and height (would change during game)
		width = 32;
		height = 32;
		
		spriteImg = ImageIO.read(new File("mario_Left1.png"));
		defaultImg = sizeImage(width, height);

	}
	
	public Rectangle getBoundingBox(){
		return new Rectangle (xPos, yPos, width, height);
	}
	
	public void update(int delta, int vertDelta){
		xPos += delta;
		yPos += vertDelta;
	}
	
	public BufferedImage sizeImage(int width, int height){
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = resizedImage.createGraphics();
		graphics.drawImage(spriteImg, 0, 0, width, height, null);
		graphics.dispose();
		return resizedImage;
	}
	
}

/* SPRITE ABSTRACT CLASS
 *  sprite has a position
 *  a state (way facing, ifJumping, crouched, etc)
 *  a way that interacts with others (depends on angle of collision too)
 *  name/description (of type enemy)
 * 
 */
