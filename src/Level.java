import java.util.ArrayList;
import java.util.List;

/**
 * Level class
 * stores Tube type, enemy types, etc...
 * @author andreas
 *
 */
//TODO write a parser to load a script file that will describe the level for dynamic level creation 
public class Level {
	private Tube tube = null;
	private Player player = null;
	private List<Enemy> enemies = new ArrayList<Enemy>(); // enemy pool to choose from
	private int numEnemies = 0; // max number of enemies spawned
	private int enemycount = 0; // count enemies already spawned
	private float spawnspeed = 1f; // limiter for Enemy spawn speed
	private double enemyTime = 0f;
	
	public Level(Tube tube, int numEnemies, Game game){
		this.tube = tube;
		this.player = new Player("player1", game);
		this.numEnemies = numEnemies;
		
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Tube getTube() {
		return tube;
	}
	public void setTube(Tube tube) {
		this.tube = tube;
	}
	public int getNumEnemies() {
		return numEnemies;
	}
	public void setNumEnemies(int numEnemies) {
		this.numEnemies = numEnemies;
	}
	public List<Enemy> getEnemies() {
		return enemies;
	}
	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}
	public float getSpawnspeed() {
		return spawnspeed;
	}
	public void setSpawnspeed(float spawnspeed) {
		this.spawnspeed = spawnspeed;
	}
	public double getEnemyTime() {
		return enemyTime;
	}
	public void setEnemyTime(double enemyTime) {
		this.enemyTime = enemyTime;
	}
	public int getEnemycount() {
		return enemycount;
	}
	public void setEnemycount(int enemycount) {
		this.enemycount = enemycount;
	}
	
}
