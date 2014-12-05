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
		}
		
		*   
		*    const int TICKS_PER_SECOND = 25;
    const int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
    const int MAX_FRAMESKIP = 5;

    DWORD next_game_tick = GetTickCount();
    int loops;
    float interpolation;

    bool game_is_running = true;
    while( game_is_running ) {

        loops = 0;
        while( GetTickCount() > next_game_tick && loops < MAX_FRAMESKIP) {
            update_game();

            next_game_tick += SKIP_TICKS;
            loops++;
        }

        interpolation = float( GetTickCount() + SKIP_TICKS - next_game_tick )
                        / float( SKIP_TICKS );
        display_game( interpolation );
    }
		*
		*/
	}
	
}
//has a game loop that
	//takes user input
	//updates Level data
	//updates Sprites
	//draws Level
	//draws Sprites 