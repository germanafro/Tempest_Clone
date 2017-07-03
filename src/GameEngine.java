import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
/**
 * runs various checks and executes game calculations
 * @author Sebastian Witt
 *
 */
public class GameEngine {
	private boolean scoreUp;
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
		game.getDirtyQueue().clear();
		game.getMoveQueue().clear();
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
					alphareached = true;
					gameObject.enemyLogic(game.getLevel().getTube().getStepr());
				}
				gameObject.move();
				if(gameObject.getZpos() >= gameObject.getZtarget()){
					zreached = true;
    				game.destroyObject(name);
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
    			// size in steps = size / stepsize
    			
    			if (checkCollision(gameObject, player)){
    				this.game.sfxPlay("Grenade-SoundBible.com-1777900486.mp3");
    				gameObject.setDestroy(true);
    				this.playerLoseLife(player);
    				
    			}
    		// check collision with playerprojectile
    		}else if(name.toLowerCase().contains("playerprojectile")){
    			
    			//TODO work with iterator over GameObject<> Map ?
    			Set<String> keys = game.getGameObjects().keySet();
    			for(String enemysName: keys){
    				if(enemysName.contains("enemy")){
	    				GameObject enemyObject = game.getGameObjects().get(enemysName);
	    				if(checkCollision(gameObject, enemyObject)){
	    					// System.out.println("[Debug]Zerstöre" + gameObject.getName());
	    					// System.out.println("[Debug]Zerstöre" + enemyObject.getName());
	    					 gameObject.setDestroy(true);
	    					 this.game.sfxPlay("Blast-SoundBible.com-2068539061.mp3");
	    					 if(!enemysName.contains("enemy_rambo")){ // invincible types 
	    						enemyObject.setDestroy(true);
	    					 	if (!enemysName.contains("enemyprojectile")){ // no score types
	    					 		this.game.setScore(this.game.getScore() + 1);
	    					 		this.game.getLevel().setKills(game.getLevel().getKills() + 1);
	    					 		
	    					 		int tens = this.game.getScore()/10;
	    					 		int ones = this.game.getScore()%10;
	    					 		this.game.getDisplay().getScore().setTexture(ones + ".png"); 
	    					 		this.game.getDisplay().getScore10().setTexture(tens + ".png"); 
	    					 			// TODO display global score not just level score
	    					 	}
	    					 }
	    				}
    				}
    			}
    		}	
   		}
   	}
	
	public boolean checkCollision(GameObject obj1, GameObject obj2){
		boolean touchz = Math.abs(obj1.getZ() - obj2.getZ()) < obj1.zoffset + obj2.zoffset;
		int alpha1 = obj1.getRalpha()%360;
		int alpha2 = obj2.getRalpha()%360;
		// turn left into right orientation
		if (alpha1 < 0) alpha1 = 360 + alpha1;
		if (alpha2 < 0) alpha2 = 360 + alpha2;
		GameObject tube = game.getLevel().getTube();
		float radius = 1f* (tube.getScale()/100f) *  (float)(tube.getrScale())/100f;
		int deltaradius = Math.abs(alpha1-alpha2);
		boolean touchr = deltaradius*2*radius/360 < (obj1.xoffset + obj2.xoffset)/3;  // rough estimate but seems to be satisfying
		return touchz && touchr;
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
        	Random rnd = new Random();
        	int i = 0;
        	if(this.game.getLevel().getEnemies().size() > 1){
        		i = rnd.nextInt(this.game.getLevel().getEnemies().size());
        	}
        	Enemy enemy = null;
        	switch(i){
        	case 1:
        		 enemy = new Enemy("enemy_shooter", 1 ,
        				 "aliensh.png",
            				"enemy_projectile.png",
            				"enemy_projectile.png",
            				"enemy_projectile.png",
            				"roundysh.png",
            				"roundysh.png",
            				game);
        		break;
        	case 2:
        		 enemy = new Enemy("enemy_rambo", 2 ,
        				 "enemy_projectile.png",
            				"enemy_projectile.png",
            				"enemy_projectile.png",
            				"enemy_projectile.png",
            				"wingship.png",
            				"wingship.png",
            				game);
        		break;
        	case 3:
        		 enemy = new Enemy("enemy_undefined", 3 ,
        				 "yelship.png",
            				"yelship.png",
            				"yelship.png",
            				"yelship.png",
            				"spco.png",
            				"spco.png",
            				game);
        		break;
        	default:
       		 enemy = new Enemy("enemy_brute", 0 ,
       				"enemy_projectile.png",
       				"enemy_projectile.png",
       				"enemy_projectile.png",
       				"enemy_projectile.png",
       				"enemy_jet.png",
       				"enemy_jet.png",
       				 game);
       		break;
        	}
        	level.setEnemycount(level.getEnemycount() + 1);
        	if(level.getEnemycount() % level.getSpawnCurve() == 0){
        		level.setSpawnspeed(((level.getSpawnspeed() - 0.05)));        		
        		} 
        	
        	Player player = this.game.getLevel().getPlayer();
        	GameObject tube = this.game.getLevel().getTube();
        	enemy.setX(player.getX());
        	enemy.setY(player.getY());
        	enemy.setRalpha(((random.nextInt(tube.stepsr) - tube.stepsr/2) * tube.getStepr()));
        	enemy.setAlphatarget(enemy.getRalpha()); 
        	enemy.setZpos(-41);
        	enemy.setZtarget(41);
        	game.addGameObject(enemy);
        	
        }
	}


	
	private void playerLoseLife(Player player) {
		// TODO Auto-generated method stub
		System.out.println("ouch!");
		if(player.loseLife() < 1) game.setState("ending");
		
	}
}
