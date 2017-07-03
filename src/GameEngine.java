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
				gameObject.enemyLogic(game.getLevel().getTube().getStepr(), game.getLevel().getTube().getStepz());
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
    				this.game.sfxPlay(player.getDeathSound());
    				gameObject.setDestroy(true);
    				this.playerLoseLife();
    				
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
	    					 this.game.sfxPlay(enemyObject.getDeathSound());
	    					 if(!enemysName.contains("invincible")){ // invincible types 
	    						if (enemyObject.isDead()) {
	    							if (enemysName.contains("boss")) this.game.getLevel().setBossDead(true);
									enemyObject.setDestroy(true);
									if (!enemysName.contains("projectile")) { // no score types
										this.game.getDisplay().updateScore(enemyObject.getScoreVal());
										this.game.getLevel().setKills(game.getLevel().getKills() + 1);
										// TODO display global score not just level score
									} 
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
	/**
	 *  spawn random enemy
	 */
	public void spawnEnemy(){
		Level level = game.getLevel();
		Timer timer = game.getTimer();
        double now = timer.getTime();
        double spawnspeed = game.getLevel().getSpawnspeed(); // limiter for Enemy spawn speed
    	double enemyTime = game.getLevel().getEnemyTime();

        if (now - enemyTime > spawnspeed) {
        	level.setEnemyTime(timer.getTime());
        	Random rnd = new Random();
        	int i = 0;
        	if(this.game.getLevel().getEnemies().size() > 1){
        		i = rnd.nextInt(this.game.getLevel().getEnemies().size());
        	}
        	this.spawnEnemy(i);
        }
        	
	}
	/**
	 * spawn enemy with id
	 * @param id enemy id 1-99 normal enemies  100+ boss types
	 */
	public void spawnEnemy(int id){
		Level level = game.getLevel();
		Random random = new Random();
        Enemy enemy = null;
        	switch(id){
        	case 1:
        		 enemy = new Enemy("enemy_shooter", 1 ,
        				 "aliensh.png",
            				"enemy_projectile.png",
            				"enemy_projectile.png",
            				"enemy_projectile.png",
            				"roundysh.png",
            				"roundysh.png",
            				game);
        		 enemy.setDeathSound("phaserDown1.mp3");
        		 enemy.setLives(1);
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
        		 enemy.setDeathSound("pepSound3.mp3");
        		 enemy.setLives(10);
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
        		 enemy.setLives(2);
        		break;
        	case 100:
        		enemy = new Enemy("enemy_boss_0", 100 ,
       				 "futurefighter.png",
           				"shship.png",
           				"shship.png",
           				"shship.png",
           				"mship1.png",
           				"mship1.png",
           				game) {
        			private double lastsalvo = 0f;
        			private double lastshot = 0f;
        			private int shotsfired = 0;
        			@Override
        			public void bossTimer(){
        				Timer timer = game.getTimer();
						double now = timer.getTime();
						
						float salvodelay = 5f;
						float shotdelay = 1f/10;
						
						if (now - lastsalvo > salvodelay) {
							//begin salvo
							if(now - lastshot > shotdelay){
								for (int i = 0; i < 3 ; i++){ // ohh its a triple!!
									Projectile proj = new Projectile("enemyprojectile" + game.enemyFired++, this.getGame(), "enemy_projectile.png");
									proj.setX(this.getX());
									proj.setY(this.getY());
									proj.setZ(this.getZ());
									proj.setRalpha(this.getRalpha() + (i-1)*this.game.getLevel().getTube().getStepr());
									proj.setAlphatarget(36000); // make it spin round and round ^_^ xD
									proj.setZpos(this.getZpos() + 5);
									proj.setZtarget(50);
									proj.setSpawnSound(this.getProjectileSound());
									game.addGameObject(proj);
								}
								lastshot = timer.getTime();
								shotsfired++;
							}
							if (shotsfired>= 10){
								shotsfired = 0;
								lastsalvo = timer.getTime(); // finish salvo
							}
						}		
					}
        		};
        		enemy.setScoreVal(10);
        		enemy.setLives(100);
        		enemy.setDeathSound("lowDown.mp3");
        		enemy.setProjectileSound("phaserUp5.mp3");
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
       		 enemy.setDeathSound("phaserDown3.mp3");
       		enemy.setLives(3);
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


	
	private void playerLoseLife() {
		// TODO Auto-generated method stub
		System.out.println("ouch!");
		if(this.game.getDisplay().loselive()) game.setState("ending");
		
	}
}
