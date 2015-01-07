import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public abstract class Sprite {
	public int xPos;
	public int yPos;
	
	public int width;
	public int height;
	
	public boolean facingLeft;
	
	public static final double ACCofGRAVITY = -9.8;
	
	public ArrayList<State> states;
	public State currentState;
	
	
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
	
	public void setCurrentState(State state){//direction ignored currently
		currentState = state;
	}
	
	public void leftPressed(boolean left){
	}
	
	public void display(Graphics g){
		for (BufferedImage i : currentState.images){
			if (!facingLeft) {
				g.drawImage(i, xPos, yPos, -width, height, null);
			} else {
				g.drawImage(i, xPos - width, yPos, null);//-width keeps top left corner in same place
			}
		}
	}
	
	public void falling(boolean inAir){
		while (inAir){
			
		}
	}
}
