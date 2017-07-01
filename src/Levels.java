import java.util.ArrayList;
import java.util.List;

public interface Levels {
	public static Level Level1(Game game){
		Tube tube = new Tube("Level1", game);
		List<Enemy> enemies = new ArrayList<Enemy>();
		
		enemies.add(new Enemy("enemy", game));
		
		Level level = new Level(tube, 10, game);
		level.setEnemies(enemies);
		return level;
	}
}
