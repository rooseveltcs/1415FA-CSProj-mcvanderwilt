import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public abstract class Sprite {
	public int xPos;
	public int yPos;
	
	public int width;
	public int height;
	
	public Rectangle getBoundingBox(){
		return new Rectangle (xPos, yPos, width, height);
	}
	
	abstract void update(int delta, int vertDelta);
	
	public BufferedImage sizeImage(Image spriteImg, int width, int height){
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = resizedImage.createGraphics();
		graphics.drawImage(spriteImg, 0, 0, width, height, null);
		graphics.dispose();
		return resizedImage;
	}
}
