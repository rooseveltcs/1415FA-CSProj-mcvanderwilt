import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public abstract class Sprite {
	//TODO: make floats?
	public int xPos;
	public int yPos;
	
	public float xVelocity;
	public float yVelocity;
	
	public int width;
	public int height;
	
	public boolean facingLeft;
	public boolean inAir;
	
	public static final double ACCofGRAVITY = -.1;
	
	public ArrayList<State> states;
	public State currentState;
	
	
	public Rectangle getBoundingBox(){
		return new Rectangle (xPos, yPos, width, height);
	}
	
	abstract void update(int delta);//, int vertDelta);
	
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
			if (facingLeft) {
				//Not certain how to ensure uniformity (ie. making sure initial image faces left)
				g.drawImage(i, xPos + width, yPos, -width, height, null);
			} else {
				g.drawImage(i, xPos, yPos, null);//-width keeps top left corner in same place
			}
			g.drawRect(xPos, yPos, width, height);//shows bounding box
			g.fillOval(xPos - 5, yPos - 5, 10, 10);
		}
	}
	
	public void falling(){
		float elapsedTime = 0;
		double dt = 0.01;
		if (inAir){
			yVelocity += ACCofGRAVITY * dt;
		} else {
			yVelocity = 0;
		}
	}
}
