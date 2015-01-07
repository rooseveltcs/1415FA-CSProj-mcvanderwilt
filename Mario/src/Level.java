import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Level {
	
	private File txtFile;
	private File imgFile;
	private File spriteFile;
	
	BufferedImage[] tiles;
	
	private JFrame frame;
	private ScrollingPanel panel;
	
	int[][] layout;
	BufferedImage tileSet;
	
	private static final int PXLS_PER_TILE = 32;
	
	private static final int ARR_WIDTH = 64;//# of tiles in row
	private static final int ARR_HEIGHT = 16;//# of tiles in column 
	
	private static final int TILES_WIDE = 32; //# of tiles across on panel
	
	private static final int PANEL_WIDTH =  TILES_WIDE * PXLS_PER_TILE;
	private static final int PANEL_HEIGHT = ARR_HEIGHT * PXLS_PER_TILE;
	
	private int deltaX;//The TOP-LEFT CORNER of the Array and of the background image; changes with player key input
	private int deltaY;
	
	//List of Active Sprites
	//Based on text file with all sprite names, types, positions 
	private ArrayList<Sprite> spriteList;
	//private SpriteX mario;
	
	public Level(File f, File imgF, File spriteF) throws IOException {
		txtFile = f;
		imgFile = imgF;
		spriteFile = spriteF;//contains a list of sprites - w/ type and initial x and y pos.
		
		panel = new ScrollingPanel();
		panel.setFocusable(true);
		panel.requestFocus();
		panel.addKeyListener(new Controller());
		
		frame = new JFrame();
		frame.add(panel);
		frame.setTitle("Mario");
		frame.setIconImage(ImageIO.read(new File("mario_Left1.png")));
		frame.setResizable(false);
		frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		tileSet = null;
		try {
		    tileSet = ImageIO.read(imgFile);
		} catch (IOException e) {
			System.out.println("Invalid image file");
		}
		
		int numTiles = tileSet.getWidth() / PXLS_PER_TILE;
		tiles = new BufferedImage[numTiles];
		for (int t = 0; t < numTiles; t++){
			tiles[t] = tileSet.getSubimage(PXLS_PER_TILE * t, 0, PXLS_PER_TILE, PXLS_PER_TILE);
		}
		
		deltaX = 0;
		deltaY = 0;
		
		layout = new int[ARR_WIDTH][ARR_HEIGHT];
		txtToArray();
		txtToSprites();
	}
	
	private int[][] txtToArray() throws FileNotFoundException {
		Scanner scanFile = new Scanner(txtFile);
		while (scanFile.hasNextLine()){
			for (int row = 0; row < ARR_HEIGHT; row++){
				for (int column = 0; column < ARR_WIDTH; column++){
					layout[column][row] = scanFile.nextInt();
				}
			}
		}
		scanFile.close();
		return layout;
	}
	
	private ArrayList<Sprite> txtToSprites() throws IOException {
		
		spriteList = new ArrayList<Sprite>();
		
		Scanner scanFile = new Scanner(spriteFile);
		while (scanFile.hasNextLine()){
			String spriteType = scanFile.next();
			int spXPos = scanFile.nextInt();
			int spYPos = scanFile.nextInt();
			if (spriteType.equalsIgnoreCase("mario")){
				Sprite s = new Mario(spXPos, spYPos);
				spriteList.add(s);
			} else {
				System.out.println("Error: unaccounted for sprite type in sprite file");
			}
			//needs to initialize sprite with correct type - can make more uniform (?)
		}
		scanFile.close();
		System.out.println(spriteList.toString());
		return spriteList;
	}
	
	//UPDATE LEVEL: shift background according to Mario's xPos/direction
	public void updateX() {
		
	}
	
	//UPDATE SPRITES: shift position according to Mario's movement; test for collision detections
	public void updateSpritesX(){
	//test collision	
		for (Sprite s : spriteList) {
			Rectangle spriteBox = s.getBoundingBox();
			//get corresponding array location
			//System.out.println(layout[deltaX / 32][deltaY/32]);
		}
		//get Sprite's location/bounding box
		//Make sure sprite is still on screen
		//check array at that location:
			//to see if there is a coin (change value in array to 0)
			//to see if there is a brick and depending on 
				//if brick collides with right or left wall of bounding box (HORIZONTALLY)--sprite stops (and if enemy turns around)
				//if brick collides w/ bottom/top of bounding box (VERTICALLY) - sprite stops vert. movement
			//to see if Mario collides w/ bottom of ? box
		//check spriteList to see if any other sprites are colliding (deal w/ accordingly with method in sprite's class
	//move
		//for (Sprite s : spriteList) {
		//change sprite's position based on delta
		//change sprite's state (i.e. jumping, facing left)
		//account for gravity, etc.}
	}
	
	public void draw() throws IOException{
		panel.repaint();
	}
	
	//Should go through array of active sprites in the level (initialized in constructor) and draw each of them
	public void drawSprites(Graphics g) throws IOException{
		//for each sprite in array
			//Draw each sprite
		for (Sprite s : spriteList){
			s.display(g);
		}
	}
	
	//TODO: Controller should just update delta and the which way Mario is facing; shouldn't call repaint component or actively update Sprites 
	private class Controller implements KeyListener {
		
		//TODO: not sure that motion should be uniform
		private static final int MOVE_STEP = 10;//movement per step (push of arrow)
		
		private boolean leftKeyPressed;
		private boolean rightKeyPressed;
		private boolean upKeyPressed;
		private boolean downKeyPressed;
		
		@Override
		public void keyPressed(KeyEvent ev) {
			//TODO: Account for multiple-keys pressed
			int keyCode = ev.getKeyCode();
			
			if (keyCode == KeyEvent.VK_LEFT){
				leftKeyPressed = true;
				if (deltaX > 0){//LEFT-HAND boundary for scrolling
					deltaX -= MOVE_STEP;
				}
			} else if (keyCode == KeyEvent.VK_RIGHT){
				rightKeyPressed = true;
				if (deltaX < (ARR_WIDTH) * PXLS_PER_TILE - PANEL_WIDTH){//RIGHT-HAND boundary for scrolling
					deltaX += MOVE_STEP;
				}
			} else if (keyCode == KeyEvent.VK_UP){
				upKeyPressed = true;
				deltaY += MOVE_STEP;
			} else if (keyCode == KeyEvent.VK_DOWN){
				//set to state "kneel" if on a block 
			}
			
			update();
			updateSprites();
			
		}

		@Override
		public void keyReleased(KeyEvent ev) {
			
			int keyCode = ev.getKeyCode();
			
			if (keyCode == KeyEvent.VK_LEFT){
				leftKeyPressed = false;
			} else if (keyCode == KeyEvent.VK_RIGHT){
				rightKeyPressed = false;
			} else if (keyCode == KeyEvent.VK_UP){
				upKeyPressed = false;
			}
		}

		public void update(){
			
		}
		
		public void updateSprites(){
			for (Sprite s : spriteList){
				if (leftKeyPressed){
					s.leftPressed(true);
				} else if (rightKeyPressed){
					s.leftPressed(false);
				} //else if (upKeyPressed) set Mario's state to jumping
				s.update(deltaX, deltaY);
				
				//collision detection
				Rectangle spriteBox = s.getBoundingBox();
				//use intersection method to test collision from all sides 
				
				
				//get corresponding array location
				int tileType = layout[s.xPos/32][s.yPos/32];
				//TODO: I think concept is sound, though currently mario doesn't move from his initial position (tile type doesn't change)
				//TODO: doesn't account for all 4
				System.out.println(s.xPos/32 + " " + s.yPos/32 + " " + tileType);
			}
			
		}
		
		@Override
		public void keyTyped(KeyEvent ev) {
		}
	}
	
	private class ScrollingPanel extends JPanel{
		
		private BufferedImage bkgrdImg;
		private BufferedImage resizedImage;
		
		private ScrollingPanel(){
			try {
				bkgrdImg = ImageIO.read(new File ("background.png"));
			} catch (IOException e) {
				System.out.println("Error translating background image from image file \"background.png\"");
			}
			
			resizedImage = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = resizedImage.createGraphics();//SCALES IMAGE TO MATCH PANEL H & W (facilitates scrolling)
			graphics.drawImage(bkgrdImg, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
			graphics.dispose();
		}
		
		//TODO: SCROLLING IS NOW FULLY FUNCTIONAL (though left-hand boundary begins at 0)
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			//BACKGROUND
			int imgWidth = panel.getWidth();//Currently same as image width
			int imgHeight = panel.getHeight();
			
			int breakPt = PANEL_WIDTH - (deltaX % PANEL_WIDTH);
			if (deltaX < 0){
				breakPt = Math.abs(deltaX % PANEL_WIDTH);
			}

			g.drawImage(resizedImage, breakPt - PANEL_WIDTH, 0, breakPt, PANEL_HEIGHT, 0, 0, imgWidth, imgHeight, panel);
			g.drawImage(resizedImage, breakPt, 0, breakPt + PANEL_WIDTH, PANEL_HEIGHT, 0, 0, imgWidth, imgHeight, panel);
			
			//BRICK LAYOUT

			int drawXPos, drawYPos;//place where tile will be drawn on JPanel
			for (int row = 0; row < ARR_HEIGHT; row++){//Stays unchanging
				//draw columns only from ___ to ____ + TILES_WIDE
				int startArr = deltaX / 32;//left-most column drawn on screen
				for (int column = startArr; column < startArr + TILES_WIDE; column++) {
					drawXPos = deltaX % PXLS_PER_TILE;
					drawYPos = row * PXLS_PER_TILE;	
					
					int tileType = layout[column][row];
					g.drawImage(tiles[tileType], drawXPos, drawYPos, panel);
				}
				/*
				for (int column = 0; column < ARR_WIDTH; column++){
					
					drawXPos = column * PXLS_PER_TILE - deltaX;
					drawYPos = row * PXLS_PER_TILE;
					
					int tileType = layout[column][row];
					if (drawXPos < PANEL_WIDTH + PXLS_PER_TILE && drawXPos >= -PXLS_PER_TILE){//Prevents off-panel stuff from being drawn 
						g.drawImage(tiles[tileType], drawXPos, drawYPos, panel);
					}
				} */
			}
			try {
				drawSprites(g);
			} catch (IOException e) {
				System.out.println("Error drawing sprites.");
			}
		}
	}
}
//need to implement a game loop that repaints the level every _ milliseconds 
	//figure out placement
	//figure out contents (method calls)
/*
double lastTime = getCurrentTime();
while (true)
{
  double current = getCurrentTime();
  double elapsed = current - lastTime;
  processInput();
  update(elapsed);
  render();
  lastTime = current;
}
*/