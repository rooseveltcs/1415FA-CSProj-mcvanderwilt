import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Game {

	private static final int FRAMES_PER_SECOND = 25;
	private static final int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;
	
	public static void main(String[] args) throws IOException {
		File file = new File("initialTxtArray.txt");//basic text array with #s corresponding to different tiles 7x12
		File imageFile = new File("Tiles.png");//row of tiles used in each level
		
		Level level = new Level(file, imageFile);
		/*
		DWORD next_game_tick = GetTickCount();
		// GetTickCount() returns the current number of milliseconds
		// that have elapsed since the system was started
	    int sleep_time = 0;
		
		boolean gameISrunning = true;
		
		while (gameISrunning){
			level.update();
			level.render();
		}*/
	}
	
}
//has a game loop that
	//takes user input
	//updates Level data
	//updates Sprites
	//draws Level
	//draws Sprites 