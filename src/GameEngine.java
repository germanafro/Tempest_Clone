import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
			if(name.toLowerCase().contains("enemy")){
				gameObject.move();
				if(gameObject.getZpos() >= gameObject.getZtarget()){
					zreached = true;
    				game.destroyObject(name);
				}
				if(gameObject.getAlphatarget() == gameObject.getRalpha()){
					alphareached = true;
				}
			}
			
			else if(name.toLowerCase().contains("player")){
				gameObject.move();
				}
			else if(name.toLowerCase().contains("playerprojectile")){
				gameObject.move();
			}
			gameObject.setMoving(!(alphareached && zreached)); // false if both are true (nand)
	    			
	    		
    		// check collision with player
    		if(name.toLowerCase().contains("enemy")){
    			Player player = game.getLevel().getPlayer();
    			boolean touchz = Math.abs(player.getZpos() - gameObject.getZpos()) < 20;
    			int playeralpha = player.getRalpha()%360;
    			int enemyalpha = gameObject.getRalpha()%360;
    			if (playeralpha < 0) playeralpha = 360 + playeralpha;
    			if (enemyalpha < 0) enemyalpha = 360 + enemyalpha;
    			boolean touchr = Math.abs(playeralpha - enemyalpha) < this.game.getLevel().getTube().getStepr(); //TODO convert negative degrees to positive 
    			if (touchz && touchr){
    				gameObject.setDestroy(true);
    				this.playerLoseLife(player);
    				
    			}
    		// check collision with playerprojectile
    		}/*else if(name.toLowerCase().contains("playerprojectile")){
    			
    			int projectileAlpha = gameObject.getRalpha() % 360;
    			game.getGameObjects().keySet();
    			
    			for(String enemiesName: game.getGameObjects().keySet()){
    				if(game.getGameObjects().containsKey(enemiesName)){
    					GameObject enemieObject = game.getGameObjects().get(enemiesName);
    					
    					boolean touchZ = Math.abs(enemieObject.getZpos() - gameObject.getZpos()) < 20;
    					int enemyAlpha = enemieObject.getRalpha();
    					if(projectileAlpha < 0) projectileAlpha = 360 + projectileAlpha;
    					if(enemyAlpha < 0) enemyAlpha = 360 + enemyAlpha;
    					boolean touchR = Math.abs(projectileAlpha - enemyAlpha) < this.game.getLevel().getTube().getStepr();
    					 if(touchZ && touchR){
    						 gameObject.setDestroy(true);
    					 }
    					
    				}
    			}
    			
    		}*/
    	}
	}
	
	public void spawnEnemy(){
		Timer timer = game.getTimer();
        double now = timer.getTime();
        float spawnspeed = game.getLevel().getSpawnspeed(); // limiter for Enemy spawn speed
    	double enemyTime = game.getLevel().getEnemyTime();
    	Random random = new Random();

        if (now - enemyTime > spawnspeed) {
        	game.getLevel().setEnemyTime(timer.getTime());
        	game.getLevel().setSpawnspeed(0.3f + random.nextFloat()); // randomize next enemy spawn :P 
        	Enemy enemy = new Enemy("enemy", game);
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
