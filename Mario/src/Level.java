import java.awt.Color;
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
	
	private JFrame frame;
	private JPanel panel;
	
	char[][] layout;
	BufferedImage tileSet;
	
	public static final int ARR_WIDTH = 12;//# of tiles in row
	public static final int ARR_HEIGHT = 8;//# of tiles in column 
	
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
		BufferedImage[] tiles = new BufferedImage[numTiles];
		
		layout = new char[ARR_WIDTH][ARR_HEIGHT];
		txtToArray();
	}
	
	private char[][] txtToArray() throws FileNotFoundException {
		Scanner scanFile = new Scanner(txtFile);
		while (scanFile.hasNextLine()){
			for (int row = 0; row < ARR_HEIGHT; row++){
				for (int column = 0; column < ARR_WIDTH; column++){
					//TODO: perhaps use string to char array (would have to get rid of spaces in txtFile)
					layout[column][row] = scanFile.next().charAt(0);
				}
			}
		}
		return layout;
	}
	
}
