import java.io.File;
import java.io.FileNotFoundException;

public class Game {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("initialTxtArray.txt");//basic text array with #s corresponding to different tiles 7x12
		File imageFile = new File("Tiles.bmp");//row of tiles used in each level
		Level level = new Level(file, imageFile);
	}

}
