import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;


public class Level {
	
	//Constants that indicate how many tiles wide and  high the level is (have to be changed if txtFile is  altered)
	public static final int ARR_HEIGHT = 16; //# of tiles in column
	public static final int ARR_WIDTH = 64; //# of tiles in row
	
	//Constant indicates how tall and wide each tile is
	public static final int PXLS_PER_TILE = 32;
	
	public int[][] tileLayout;
	public BufferedImage[] tiles;
	public ArrayList<Sprite> spriteList;
	
	/* Constructor 
	 * 
	 * 
	 * @param f: File containing integers 0-6 with each representing a different tile type; grid format
	 * @param imgF: File containing images of tiles
	 * @param spriteF: lists sprites and initial positions
	 * 
	 * @param view: Scrolling Panel (extends JPanel) displays Level
	 */
	public Level(File f, File imgF, File spriteF, Frame view) throws FileNotFoundException {
		tileLayout = txtToArray(f);
		tiles = imgToTiles(imgF);
		spriteList = txtToSprites(spriteF);
	}
	
	private int[][] txtToArray(File txtFile) throws FileNotFoundException {
		Scanner scanFile = new Scanner(txtFile);
		while (scanFile.hasNextLine()){
			for (int row = 0; row < ARR_HEIGHT; row++){
				for (int column = 0; column < ARR_WIDTH; column++){
					tileLayout[column][row] = scanFile.nextInt();
				}
			}
		}
		scanFile.close();
		return tileLayout;
	}
	
	private BufferedImage[] imgToTiles(File imgFile) throws FileNotFoundException {
		try {
			BufferedImage tileSet = ImageIO.read(imgFile);
			
			int numTiles = tileSet.getWidth() / PXLS_PER_TILE;
			tiles = new BufferedImage[numTiles];
			for (int t = 0; t < numTiles; t++){
				tiles[t] = tileSet.getSubimage(PXLS_PER_TILE * t, 0, PXLS_PER_TILE, PXLS_PER_TILE);
			}
			
		} catch (IOException e) {
			System.err.print("Invalid image file");
			tiles = null;
		}
		
		return tiles;
	}
	
	private ArrayList<Sprite> txtToSprites(File spriteF) throws FileNotFoundException {
		
		int numMarios = 0;//ensures that there is one (and only one mario created in the game)
		
		Scanner scanFile = new Scanner(spriteF);
		while (scanFile.hasNextLine()){
			//INTIALIZE SPRITES - not sure how I want to structure Sprite class
				//Sprite should have type (ie Mario, enemy, etc)
				//Sprite should have location (x, y) relative to screen (or to initial origin)
				//Sprite should have manner of updating 
				//Sprite should have various ways in which it interacts with other objects
		}
		
		return spriteList;
	}
}