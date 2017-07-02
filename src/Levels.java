import java.util.ArrayList;
import java.util.List;

public interface Levels {
	public static Level Level1(Game game){
		Tube tube = new Tube("Level1", 10, game);
		List<Enemy> enemies = new ArrayList<Enemy>();
		
		enemies.add(new Enemy("enemy", game));
		
		Level level = new Level(tube, 10, game);
		level.setEnemies(enemies);
		
		level.setBgm("sawsquarenoise_-_01_-_Interstellar.mp3");
		return level;
	}
	public static Level Level2(Game game){
		Tube tube = new HalfTube("Level2", 5, game);
		List<Enemy> enemies = new ArrayList<Enemy>();
		
		enemies.add(new Enemy("enemy", game));
		
		Level level = new Level(tube, 10, game);
		level.setEnemies(enemies);
		
		level.setBgm("sawsquarenoise_-_02_-_Fire_Darer.mp3");
		return level;
	}
	public static Level Level3(Game game){
		Tube tube = new Tube("Level3", 6, game);
		tube.setRadius(-1f);
		List<Enemy> enemies = new ArrayList<Enemy>();
		
		enemies.add(new Enemy("enemy", game));
		
		Level level = new Level(tube, 10, game);
		level.setEnemies(enemies);
		
		level.setBgm("sawsquarenoise_-_03_-_Field_Force.mp3");
		return level;
	}
	
	
	// special stuff
	public static Level StartMenu(Game game){
		Tube tube = new Tube("startmenu", 10, game); // TODO replace with intro screen
		List<Enemy> enemies = new ArrayList<Enemy>();
		Level level = new Level(tube, 0, game);
		level.setEnemies(enemies);
		level.setBgm("09 Come and Find Me - B mix.mp3");
		return level;
	}
	public static Level Ending(Game game){
		Tube tube = new Tube("ending", 10, game); // TODO replace with ending screen
		List<Enemy> enemies = new ArrayList<Enemy>();
		Level level = new Level(tube, 0, game);
		level.setEnemies(enemies);
		level.setBgm("Creo_-_Sphere.mp3");
		return level;
	}
}
