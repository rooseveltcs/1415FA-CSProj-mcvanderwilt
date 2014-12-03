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

public class Level {
	
	private File txtFile;
	private File imgFile;
	
	BufferedImage[] tiles;
	
	private JFrame frame;
	private ScrollingPanel panel;
	
	int[][] layout;
	BufferedImage tileSet;
	
	private static final int PXLS_PER_TILE = 32;
	
	private static final int ARR_WIDTH = 32;//# of tiles in row
	private static final int ARR_HEIGHT = 16;//# of tiles in column 
	
	private Graphics g;

	private int delta;//The TOP-LEFT CORNER of the Array and of the background image; changes with player key input
	
	//List of Active Sprites
	//Based on text file with all sprite names, types, positions 
	private ArrayList<Sprite> spriteList;
	private Sprite mario;
	
	public Level(File f, File imgF) throws IOException {
		txtFile = f;
		imgFile = imgF;
		
		panel = new ScrollingPanel();
		
		g = panel.getGraphics();//TODO: Get rid of getGraphics and use paintComponent
		
		panel.setFocusable(true);
		panel.requestFocus();
		panel.addKeyListener(new Controller());
		
		frame = new JFrame();
		frame.add(panel);
		frame.setResizable(false);
		frame.setSize(ARR_WIDTH * 32, ARR_HEIGHT * 32);//ARRAY_WIDTH will not be the same as the frame width
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
		
		drawLevel();
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
	public void updateLevel() {
		
	}
	//UPDATE SPRITES: shift position according to Mario's movement; test for collision detections
	public void updateSprites(){
	
	}
	
	//TODO: Doesn't display image background consistently
	public void drawLevel() throws IOException{
		//Get Mario's position on screen
		int panelXPos, panelYPos;
		
		/*
		//TODO: Shouldn't draw full image just a selection based on Mario's xPos
		BufferedImage bckgrd = ImageIO.read(new File("background.png"));//TODO: Alter image to extend beyond panel width height
		//Image scaledBkgrd = bckgrd.getScaledInstance(ARR_WIDTH * 32, (ARR_HEIGHT - 1) * 32, java.awt.Image.SCALE_SMOOTH);
		g.drawImage(bckgrd, 0, 0, PXLS_PER_TILE * ARR_WIDTH, (ARR_HEIGHT - 1) * PXLS_PER_TILE, panel);
		*/
		int delta = 0;//for now//TODO: Test
		
		for (int row = 0; row < ARR_HEIGHT; row++){//Stays unchanging
			for (int column = 0; column < ARR_WIDTH; column++){
				
				//Assumes that one 32pixel panel is equivalent to mario's movement
				panelXPos = column * 32 - delta;
				panelYPos = row * 32;
				
				int tileType = layout[column][row];
				g.drawImage(tiles[tileType], panelXPos, panelYPos, panel);
			}
		}
		drawSprites(g);//TODO: Don't really want this in here. 
		//panel.paintComponent(g);
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
		
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			int pWidth = panel.getWidth();//Currently same as image width
			int pHeight = panel.getHeight();
			
			int breakPt = pWidth - (delta % pWidth);

			//TODO: Resolution's not great
			int imgWidth = pWidth;//Desired width/height of new scaled image
			int imgHeight = pHeight;
			BufferedImage resizedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = resizedImage.createGraphics();
			graphics.drawImage(bkgrdImg, 0, 0, imgWidth, imgHeight, null);
			graphics.dispose();
			
			//TODO: Scrolls continuously unless DELTA = negative (ie Mario starts by going left)
		
			g.drawImage(resizedImage, breakPt - pWidth, 0, breakPt, pHeight, 0, 0, imgWidth, imgHeight, panel);
			g.drawImage(resizedImage, breakPt, 0, breakPt + pWidth, pHeight, 0, 0, imgWidth, imgHeight, panel);
		}
	}
}
//need to implement a game loop that repaints the level every _ seconds 
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