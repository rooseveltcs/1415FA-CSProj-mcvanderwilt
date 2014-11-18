import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Game {

	public static void main(String[] args) throws IOException {
		File file = new File("initialTxtArray.txt");//basic text array with #s corresponding to different tiles 7x12
		File imageFile = new File("Tiles.png");//row of tiles used in each level
		
		Controller control = new Controller();
		
		Level level = new Level(file, imageFile);
	}
	
}
//has a game loop that
	//takes user input
	//updates Level data
	//updates Sprites
	//draws Level
	//draws Sprites 