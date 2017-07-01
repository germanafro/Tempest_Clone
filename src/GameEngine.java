import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameEngine {
	
	private Game game;
	
	public GameEngine(Game game){
		this.setGame(game);
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		// do once: curently just a placeholder in case theres stuff to initialize before the loop
	}
	/**
	 * implement smoother movements
	 * through decreasing delta and remembering a target to reach
	 */
	public void queueObjects(){
		game.setDirtyQueue(new ArrayList<GameObject>());
		game.setMoveQueue(new ArrayList<GameObject>());
		Iterator<GameObject> gameObjects = this.game.getGameObjects().values().iterator();
    	while(gameObjects.hasNext()){
    		GameObject gameObject = gameObjects.next();
    		
    		if(gameObject.isDestroy()){
    			game.getDeleteQueue().add(gameObject);
    		}else{
    			if(gameObject.isMoving()) game.getMoveQueue().add(gameObject);
    			if(gameObject.isDirty())  game.getDirtyQueue().add(gameObject);
    		}
    	}
	}
	
	public void moveObjects(){
		
		for(GameObject gameObject : game.getMoveQueue()){
			String name = gameObject.getName();
			boolean zreached = false;
			boolean alphareached = false;
			
			//drop ifelse cases?
			if(name.toLowerCase().contains("enemy")){
				if(gameObject.getAlphatarget() == gameObject.getRalpha()){
					gameObject.movementLogic(game.getLevel().getTube().getStepr());
				}
				// new Function for different moving beheavior
				gameObject.move();
				
				if(gameObject.getZpos() >= gameObject.getZtarget()){
					zreached = true;
    				game.destroyObject(name);
				}
				if(gameObject.getAlphatarget() == gameObject.getRalpha()){
					alphareached = true;
				}
			}
			
			else if(name.toLowerCase().contains("playerprojectile")){
				gameObject.move();
				if(gameObject.getZpos() <= gameObject.getZtarget()){
					zreached = true;
    				game.destroyObject(name);
				}
				if(gameObject.getAlphatarget() == gameObject.getRalpha()){
					alphareached = true;
				}
			}
			else if(name.toLowerCase().contains("player")){
				gameObject.move();
				zreached = true;
				if(gameObject.getAlphatarget() == gameObject.getRalpha()){
					alphareached = true;
				}
			}
			gameObject.setMoving(!(alphareached && zreached)); // false if both are true (nand)
			// delete enemies and projectiles, that reached the end
	    			
	    		
    		// check collision with player
    		if(name.toLowerCase().contains("enemy")){
    			Player player = game.getLevel().getPlayer();
    			boolean touchz = Math.abs(player.getZpos() - gameObject.getZpos()) < 22;
    			int playeralpha = player.getRalpha()%360;
    			int enemyalpha = gameObject.getRalpha()%360;
    			if (playeralpha < 0) playeralpha = 360 + playeralpha;
    			if (enemyalpha < 0) enemyalpha = 360 + enemyalpha;
    			boolean touchr = Math.abs(playeralpha - enemyalpha) <= this.game.getLevel().getTube().getStepr()/2; 
    			if (touchz && touchr){
    				gameObject.setDestroy(true);
    				this.game.sfxPlay(new Sound("sfx/Grenade-SoundBible.com-1777900486.mp3"));
    				this.playerLoseLife(player);
    				
    			}
    		// check collision with playerprojectile
    		}else if(name.toLowerCase().contains("playerprojectile")){
    			
    			int projectileAlpha = gameObject.getRalpha() % 360;
    			
    			
    			//TODO work with iterator over GameObject<> Map ?
    			Set<String> keys = game.getGameObjects().keySet();
    			
    			for(String enemysName: keys){
    				if(enemysName.contains("enemy")){
	    				if(game.getGameObjects().containsKey(enemysName)){
	    					//System.out.print("[DEBUG] Gegner in GameObject<>");
	    					GameObject enemyObject = game.getGameObjects().get(enemysName);
	    					boolean touchZ = Math.abs(enemyObject.getZpos() - gameObject.getZpos()) < 22;
	    					int enemyAlpha = enemyObject.getRalpha();
	    					
	    					if(projectileAlpha < 0) projectileAlpha = 360 + projectileAlpha;
	    					if(enemyAlpha < 0) enemyAlpha = 360 + enemyAlpha;
	    					//System.out.println("EnemyAlpha: " + enemyAlpha + "projectileAlpha: " + projectileAlpha);
	    					//System.out.println("Level Step: " + this.game.getLevel().getTube().getStepr());
	    					boolean touchR = Math.abs(projectileAlpha - enemyAlpha) <= this.game.getLevel().getTube().getStepr()/2;
	    					 if(touchZ && touchR){
	    						// System.out.println("[Debug]Zerstöre" + gameObject.getName());
	    						// System.out.println("[Debug]Zerstöre" + enemyObject.getName());
	    						 gameObject.setDestroy(true);
	    						 enemyObject.setDestroy(true);
	    						 this.game.sfxPlay(new Sound("sfx/Blast-SoundBible.com-2068539061.mp3"));
	    						 game.getLevel().setKills(game.getLevel().getKills() + 1);
	    					 }
    					 }
    				}
    			}	
    		}
    	}
	}
	
	public void spawnEnemy(){
		Level level = game.getLevel();
		Timer timer = game.getTimer();
        double now = timer.getTime();
        double spawnspeed = game.getLevel().getSpawnspeed(); // limiter for Enemy spawn speed
    	double enemyTime = game.getLevel().getEnemyTime();
    	Random random = new Random();

        if (now - enemyTime > spawnspeed) {
        	level.setEnemyTime(timer.getTime());
        	Enemy enemy = new Enemy("enemy", game);
        	level.setEnemycount(level.getEnemycount() + 1);
        	//System.out.println("Enemy Count: " + level.getEnemycount());
        	if(level.getEnemycount() % level.getSpawnCurve() == 0){
        		level.setSpawnspeed(((level.getSpawnspeed() - 0.05)));
        		//System.out.println("Spawnspeed: " + level.getSpawnspeed());
        		
        		} 
        	Player player = this.game.getLevel().getPlayer();
        	enemy.setX(player.getX());
        	enemy.setY(player.getY());
        	enemy.setZ(2.5f);
        	enemy.setRalpha((random.nextInt(360) * game.getLevel().getTube().getStepr()) % 360);
        	enemy.setAlphatarget(enemy.getRalpha()); //game.getLevel().getTube().getStepr() * (random.nextInt(360) -180)
        	enemy.setZpos(-100);
        	enemy.setZtarget(0);
        	game.addGameObject(enemy);
        	
        }
	}


	
	private void playerLoseLife(Player player) {
		// TODO Auto-generated method stub
		System.out.println("ouch!");
		if(player.loseLife() < 1) game.setState("ending");
		
	}
}
