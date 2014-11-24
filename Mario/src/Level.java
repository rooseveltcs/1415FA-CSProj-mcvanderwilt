import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Level {
	
	private File txtFile;
	private File imgFile;
	
	BufferedImage[] tiles;
	
	private JFrame frame;
	private JPanel panel;
	
	int[][] layout;
	BufferedImage tileSet;
	
	public static final int ARR_WIDTH = 32;//# of tiles in row
	public static final int ARR_HEIGHT = 16;//# of tiles in column 
	
	private Graphics g;
	//TODO: place this back as an instance field << private Graphics g;
	private int xPos;//The TOP-LEFT CORNER of the Array and of the background image; changes with player key input
	
	//List of Active Sprites
	private Sprite mario;
	
	public Level(File f, File imgF) throws IOException {
		txtFile = f;
		imgFile = imgF;
		
		
		panel = new JPanel();
		panel.setBackground(new Color(102, 178, 255));
		
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
		
		int numTiles = tileSet.getWidth() / 32;//32 = number of pixels in each tile
		tiles = new BufferedImage[numTiles];
		for (int t = 0; t < numTiles; t++){
			tiles[t] = tileSet.getSubimage(32 * t, 0, 32, 32);//Make 32 a static variable
		}
		
		xPos = 0;
		
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
		return layout;
	}
	
	
	//UPDATE LEVEL: shift background according to Mario's xPos/direction
	public void updateLevel() {
		
	}
	//UPDATE SPRITES: shift position according to Mario's movement; test for collision detections
	
	
	//TODO: Doesn't display image background consistently
	public void drawLevel() throws IOException{
		//Should only select area of area showing on panel
		//Get Mario's position on screen
		int arrayXPos, arrayYPos;
		
		//Graphics g = panel.getGraphics();//TODO: Make an instance field
		
		//TODO: Shouldn't draw full image just a selection based on Mario's xPos
		Image bckgrd = ImageIO.read(new File("background.png"));
		Image scaledBkgrd = bckgrd.getScaledInstance(ARR_WIDTH * 32, (ARR_HEIGHT - 1) * 32, java.awt.Image.SCALE_SMOOTH);
		g.drawImage(scaledBkgrd, 0, 0, panel);
		
		for (int row = 0; row < ARR_HEIGHT; row++){//Stays unchanging
			for (int column = xPos; column < ARR_WIDTH; column++){//TODO: Should alter starting point depending on Mario's position
				
				arrayXPos = column * 32;
				arrayYPos = row * 32;
				
				int tileType = layout[column][row];
				g.drawImage(tiles[tileType], arrayXPos, arrayYPos, panel);
			}
		}
		
		drawSprites(g);//TODO: Don't really want this in here.
	}
	
	//Should go through array of active sprites in the level (initialized in constructor) and draw each of them
	public void drawSprites(Graphics g) throws IOException{
		//Go through array
		//Draw each sprite
		mario = new Sprite();
		g.drawImage(mario.initialState, mario.xPos, mario.yPos, panel);//Will be handled by a for:each loop
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	private class Controller implements KeyListener {
//TODO: Left Arrow Key Code doesn't match up with VK_LEFT constant 
		@Override
		public void keyPressed(KeyEvent ev) {
			// TODO Auto-generated method stub
			System.out.println("KEY pressed");
			
			//will be made conditional to Left-Key pressed
			mario.xPos = mario.xPos + 10;
			/*try {
				drawLevel();
			} catch (IOException e) {
			}*/
			
			int keyCode = ev.getKeyCode();
			if (keyCode == KeyEvent.VK_LEFT){
				System.out.println("Left pressed");
			}
		}

		@Override
		public void keyReleased(KeyEvent ev) {
			// TODO Auto-generated method stub
			System.out.println("KEY released");
			int keyCode = ev.getKeyCode();
			
			System.out.println(keyCode);
			System.out.println(KeyEvent.VK_RIGHT);

			
			if (keyCode == KeyEvent.VK_LEFT){
				System.out.println("Left released");
			}
		}

		@Override
		public void keyTyped(KeyEvent ev) {
		}
		
	}
}
