import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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

//TODO: implement offset and boundaries (so scrolling stops when level txt file ends).
//TODO: Create game loop
public class Level {
	
	private File txtFile;
	private File imgFile;
	
	BufferedImage[] tiles;
	
	private JFrame frame;
	private ScrollingPanel panel;
	
	int[][] layout;
	BufferedImage tileSet;
	
	private static final int PXLS_PER_TILE = 32;
	
	private static final int ARR_WIDTH = 64;//# of tiles in row
	private static final int ARR_HEIGHT = 16;//# of tiles in column 
	
	private int delta;//The TOP-LEFT CORNER of the Array and of the background image; changes with player key input
	
	//List of Active Sprites
	//Based on text file with all sprite names, types, positions 
	private ArrayList<Sprite> spriteList;
	private Sprite mario;
	
	public Level(File f, File imgF) throws IOException {
		txtFile = f;
		imgFile = imgF;
		
		panel = new ScrollingPanel();
		
		panel.setFocusable(true);
		panel.requestFocus();
		panel.addKeyListener(new Controller());
		
		frame = new JFrame();
		frame.add(panel);
		frame.setResizable(false);
		frame.setSize(1024, ARR_HEIGHT * 32);//ARRAY_WIDTH will not be the same as the frame width
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
		
		delta = 0;
		
		layout = new int[ARR_WIDTH][ARR_HEIGHT];
		txtToArray();
		
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
	
	//UPDATE LEVEL: shift background according to Mario's xPos/direction
	public void update() {
		
	}
	//UPDATE SPRITES: shift position according to Mario's movement; test for collision detections
	public void updateSprites(){
	
	}
	
	public void drawLevel() throws IOException{
	}
	
	//Should go through array of active sprites in the level (initialized in constructor) and draw each of them
	public void drawSprites(Graphics g) throws IOException{
		//for each sprite in array
			//Draw each sprite
		mario = new Sprite();
		g.drawImage(mario.initialState, mario.xPos, mario.yPos, panel);//Will be handled by a for:each loop
	}
	
	private class Controller implements KeyListener {
		
		private static final int MOVE_STEP = 10;//movement per step (push of arrow)
		
		private boolean leftKeyPressed;
		private boolean rightKeyPressed;
		private boolean upKeyPressed;
		
		@Override
		public void keyPressed(KeyEvent ev) {
			
			int keyCode = ev.getKeyCode();
			
			if (keyCode == KeyEvent.VK_LEFT){
				leftKeyPressed = true;
				delta -= MOVE_STEP;
				panel.repaint();
			} else if (keyCode == KeyEvent.VK_RIGHT){
				rightKeyPressed = true;
				delta += MOVE_STEP;
				panel.repaint();
			} else if (keyCode == KeyEvent.VK_UP){
				upKeyPressed = true;
			}
		/*	try {
				//updateLevel();
				////updateSprites();//goes in and adds delta to sprite's position; tests for collision; changes mario's state
				drawLevel();
			} catch (IOException e) {
				System.out.println("Error: drawing level");
			}*/
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
				System.out.println(delta);
			}
		}

		@Override
		public void keyTyped(KeyEvent ev) {
		}
	}
	
	private class ScrollingPanel extends JPanel{
		
		private Graphics g;
		
		private BufferedImage bkgrdImg;
		
		private ScrollingPanel(){
			Graphics g = getGraphics();
			
			try {
				bkgrdImg = ImageIO.read(new File ("background.png"));
			} catch (IOException e) {
				System.out.println("Error translating background image from image file \"background.png\"");
			}
		}
		
		//SCROLLING IS NOW FULLY FUNCTIONAL (though boundaries haven't been implemented)
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			//BACKGROUND
			int pWidth = panel.getWidth();//Currently same as image width
			int pHeight = panel.getHeight();
			
			int breakPt = pWidth - (delta % pWidth);
			if (delta < 0){
				breakPt = Math.abs(delta % pWidth);
			}

			//TODO: Resolution's not great
			int imgWidth = pWidth;//Desired width/height of new scaled image
			int imgHeight = pHeight;
			BufferedImage resizedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = resizedImage.createGraphics();
			graphics.drawImage(bkgrdImg, 0, 0, imgWidth, imgHeight, null);
			graphics.dispose();
			
			g.drawImage(resizedImage, breakPt - pWidth, 0, breakPt, pHeight, 0, 0, imgWidth, imgHeight, panel);
			g.drawImage(resizedImage, breakPt, 0, breakPt + pWidth, pHeight, 0, 0, imgWidth, imgHeight, panel);
			
			//BRICK LAYOUT
			int drawXPos, drawYPos;
			for (int row = 0; row < ARR_HEIGHT; row++){//Stays unchanging
				for (int column = 0; column < ARR_WIDTH; column++){
					
					//Assumes that one 32pixel panel is equivalent to mario's movement
					drawXPos = column * PXLS_PER_TILE - delta;
					drawYPos = row * PXLS_PER_TILE;
					
					int tileType = layout[column][row];
					if (drawXPos < pWidth + PXLS_PER_TILE && drawXPos >= -PXLS_PER_TILE){//Prevents off-panel stuff from being drawn 
						g.drawImage(tiles[tileType], drawXPos, drawYPos, panel);
					}
				}
			}
			try {
				drawSprites(g);
			} catch (IOException e) {
				System.out.println("Error drawing sprites (IO Exception).");
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