import java.io.File;
import java.io.IOException;

public class Game {

	private static final int FRAMES_PER_SECOND = 25;
	private static final int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;
	private static final int MAX_FRAMESKIP = 5;
	
	public static void main(String[] args) throws IOException {
		
		File file = new File("initialTxtArray.txt");//basic text array with #s corresponding to different tiles 7x12
		File imageFile = new File("Tiles.png");//row of tiles used in each level
		File spriteFile = new File("SpriteFile.txt");
		
		Level level = new Level(file, imageFile, spriteFile);
		
		//Game loop adapted from http://www.koonsolo.com/news/dewitters-gameloop/ 
		
		long next_game_tick = System.currentTimeMillis();
		// GetTickCount() returns the current number of milliseconds
		// that have elapsed since the system was started
		int loops;
		float interpolation;
		
		boolean gameISrunning = true;
		
		while (gameISrunning){
			
			loops = 0;
			while (System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP){
				level.updateX();//not the one contained in controller class
				
				next_game_tick += SKIP_TICKS;
				loops++;
			}
			
			interpolation = (float) (System.currentTimeMillis() + SKIP_TICKS - next_game_tick) / (float) (SKIP_TICKS);
			//level.draw(interpolation);
			level.draw();
			//TODO: game still does not account for lag
			//TODO: draw Level needs to account for interpolation (or game loop needs to modified/removed)
		}
	}
	
}
//has a game loop that
	//takes user input
	//updates Level data
	//updates Sprites
	//draws Level
	//draws Sprites 