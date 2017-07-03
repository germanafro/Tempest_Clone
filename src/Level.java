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
	private Background background = null;
	private List<Enemy> enemies = new ArrayList<Enemy>(); // enemy pool to choose from
	private int numEnemies = 0; // max number of enemies spawned
	private int enemycount = 0; // count enemies already spawned
	private double spawnspeed = 1; // limiter for Enemy spawn speed
	
	private double enemyTime = 0f;
	private String bgm = null;

	//May vary Difficulty by killcount
	private int kills = 0;
	private int killGoal = 5;
	
	// Every spawnCurve Enemies spawn, the frequency with which the enemies spawn rises
	private int spawnCurve = 10;


	public Level(Tube tube, int numEnemies, Game game){
		this.tube = tube;
		this.player = new Player("player1", game);
		this.player.setY(this.tube.getRadius());
		this.setBackground(new Background("background", game));
		this.numEnemies = numEnemies;
	}
	
	
	public int getKills() {
		return kills;
	}
	public void setKills(int kills) {
		this.kills = kills;
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
	public double getSpawnspeed() {
		if(this.spawnspeed < 0){
			return 0.1;
		}else{
			return spawnspeed;
		}
		
	}
	public void setSpawnspeed(double spawnspeed) {
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
	public String getBgm() {
		return bgm;
	}
	public void setBgm(String bgm) {
		this.bgm = bgm;
	}
	public boolean isFinished() {
		return (this.getKills() >= this.getKillGoal()) ? true : false;
	}
	public int getKillGoal() {
		return killGoal;
	}
	public void setKillGoal(int killGoal) {
		this.killGoal = killGoal;
	}
	public int getSpawnCurve() {
		return spawnCurve;
	}
	public void setSpawnCurve(int spawnCurve) {
		this.spawnCurve = spawnCurve;
	}
	public Background getBackground() {
		return background;
	}
	public void setBackground(Background background) {
		this.background = background;
	}
	
}
