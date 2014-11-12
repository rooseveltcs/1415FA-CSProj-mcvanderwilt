import java.awt.Color;
import java.awt.Graphics;
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
	
	public static final int ARR_WIDTH = 16;//# of tiles in row
	public static final int ARR_HEIGHT = 8;//# of tiles in column 
	
	//List of Active Sprites
	
	public Level(File f, File imgF) throws FileNotFoundException {
		txtFile = f;
		imgFile = imgF;
		
		panel = new JPanel();
		panel.setBackground(new Color(102, 178, 255));
		
		frame = new JFrame();
		frame.add(panel);
		frame.setSize(ARR_WIDTH * 32, ARR_HEIGHT * 32);
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
					//System.out.print(layout[column][row]);
				}
				//System.out.println();
			}
		}
		return layout;
	}
	
	public void drawLevel(){
		//Should only select area of area showing on panel
		int xPos, yPos;
		
		Graphics g = panel.getGraphics();
		
		for (int row = 0; row < ARR_HEIGHT; row++){
			for (int column = 0; column < ARR_WIDTH; column++){
				
				xPos = column * 32;
				yPos = row * 32;
				
				int tileType = layout[column][row];
				g.drawImage(tiles[tileType], xPos, yPos, panel);
			}
		}
	}
	
}
