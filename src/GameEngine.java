import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.util.Iterator;

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
	public void moveObjects(){
		Iterator<GameObject> gameObjects = this.game.getGameObjects().values().iterator();
    	while(gameObjects.hasNext()){
    		GameObject gameObject = gameObjects.next();
    		//System.out.println("processing moveObject:" + gameObject.getName());
    		//System.out.println("alphatarget: " + gameObject.getAlphatarget() + " alpha: " + gameObject.getRalpha());
    		//System.out.println("Ztarget: " + gameObject.getZtarget() + " Zpos: " + gameObject.getZpos());
    		if(gameObject.getAlphatarget() > gameObject.getRalpha()){
    			gameObject.move(1, 0);
    		} else if(gameObject.getAlphatarget() < gameObject.getRalpha()){
    			gameObject.move(-1, 0);
    		}
    		else{
    			//TODO stay idle for now}
    		}
    		if(gameObject.getZtarget() > gameObject.getZpos()){
    			gameObject.move(0, 1);
    		}else if(gameObject.getZtarget() < gameObject.getZpos()){
    			gameObject.move(0, -1);
    		}else{
    			String name = gameObject.getName();
    			if(name.contains("projectile")){
    				game.destroyObject(name);
    			}
    		}
    	}
	}
}
