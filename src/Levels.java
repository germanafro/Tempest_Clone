import java.util.ArrayList;
import java.util.List;
/**
 * Container to store Level data
 * @author Andreas Berger
 *
 */
public interface Levels {
	public static Level Level1(Game game){
		Tube tube = new Tube("Level1", 10, "tube_purple.png", game);
		
		List<Integer> enemies = new ArrayList<Integer>();
		enemies.add(0);
		
		Level level = new Level(tube, 10, game);
		level.setEnemies(enemies);
		
		level.setBgm("sawsquarenoise_-_01_-_Interstellar.mp3");
		return level;
	}
	public static Level Level2(Game game){
		Tube tube = new HalfTube("Level2", 5, "tube_red.png", game);
		
		List<Integer> enemies = new ArrayList<Integer>();
		enemies.add(0);
		enemies.add(1);
		
		Level level = new Level(tube, 10, game);
		level.setEnemies(enemies);
		
		level.setBgm("sawsquarenoise_-_02_-_Fire_Darer.mp3");
		return level;
	}
	public static Level Level3(Game game){
		Tube tube = new Tube("Level3", 6, "tube_red.png", game);
		tube.setRadius(-1f);
		
		List<Integer> enemies = new ArrayList<Integer>();
		enemies.add(0);
		enemies.add(1);
		enemies.add(2);
		
		Level level = new Level(tube, 10, game);
		level.setEnemies(enemies);
		
		level.setBgm("sawsquarenoise_-_03_-_Field_Force.mp3");
		return level;
	}
	
	
	// special stuff
	public static Level StartMenu(Game game){
		Background tube = new Background("startmenu", "startmenu.png", game);
		tube.setZpos(40);
		Level level = new Level(tube, 0, game);
		level.setBgm("09 Come and Find Me - B mix.mp3");
		return level;
	}
	public static Level Ending(Game game){
		Background tube = new Background("ending", "credits.png", game);
		tube.setxScale(400);
		tube.setyScale(400);
		tube.setzScale(100);
		tube.setScale(300);
		tube.setDirty(true);
		Level level = new Level(tube, 0, game);
		level.setBgm("Creo_-_Sphere.mp3");
		return level;
	}
	
	public static Level GameOver(Game game){
		Background tube = new Background("gameover", "Mljuegos0.png", game);
		//tube.scale = 100;
		tube.setxScale(400);
		tube.setyScale(300);
		tube.setzScale(150);
		tube.setScale(300);
		tube.setDirty(true);
		Level level = new Level(tube, 0, game);
		level.setBgm("07 We're the Resistors.mp3");
		return level;
	}
}
