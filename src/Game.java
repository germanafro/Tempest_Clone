import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

//TODO place holder
public class Game {
	private Level currLevel;
	private int levelNr = 1;
	private String state = "startmenu";
	private Map<String, GameObject> gameObjects;
	private List<GameObject> deleteQueue = new ArrayList<GameObject>();
	private List<GameObject> moveQueue = new ArrayList<GameObject>();
	private List<GameObject> dirtyQueue = new ArrayList<GameObject>();
	private GameEngine gameEngine;
	private RenderEngine renderEngine;
	private boolean pause;
	private HUD hud;
	private Timer timer = new Timer();
	public float shootingSpeed = 1f/2; // limiter for player shooting speed
	public double shootTime = 0f;
	public int shotsFired = 0;
	private int uniqueid = 0;
	private Level level = null; //TODO unterschied currLevel?
	
	
	public Game(){
		this.setHud(new HUD(this));
		this.setGameObjects(new HashMap<String, GameObject>());
		this.startNewGame();
		this.setState("starting"); // TODO removeme ^_^ just for testing
	}

	private void startNewGame() {
		// TODO Auto-generated method stub
		this.setState("starting");
	}

	public double getXStep() {
		// TODO Auto-generated method stub
		return 0;
	}
	public double getYStep() {
		// TODO Auto-generated method stub
		return 0;
	}
	public double getZStep() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void newGameEngine() {
		// TODO Auto-generated method stub
		this.gameEngine = new GameEngine(this);
		
	}

	public void newRenderEngine() {
		// TODO Auto-generated method stub
		this.setRenderEngine(new RenderEngine(this));
	}

	public void run() {
		// TODO Auto-generated method stub
		this.renderEngine.run();
		this.renderEngine.loadTextures(); // TODO: move back to render engine?
		this.renderEngine.initObjects();
		this.gameEngine.run();
		timer.init();
		this.loop();
		
        // Release window and window callbacks
        glfwDestroyWindow(renderEngine.getWindow());
        renderEngine.getKeyCallback().free();
    	//TODO remove me
    	hud.dispose();
        // Terminate GLFW and release the GLFWerrorfun
        glfwTerminate();
        
        if (renderEngine.getErrorCallback() != null)renderEngine.getErrorCallback().free();
	}
	
	private void loop(){
		long window = this.getRenderEngine().getWindow();
		
		while(!glfwWindowShouldClose(this.getRenderEngine().getWindow())){
			//TODO Step 1 do game calculations
			float delta = timer.getDelta();
			switch(state){
			case "starting":
				this.setLevel(Levels.Level1(this));
				this.addGameObject(level.getPlayer());
				this.addGameObject(level.getTube());
				this.setState("playing");
				
				break;
			case "playing":
				if(this.nextLevel()){
					this.setLevelNr(this.getLevelNr() + 1);
					this.setState("pause");
				}
				this.gameEngine.spawnEnemy();
				this.gameEngine.queueObjects();
				this.gameEngine.moveObjects();
				break;
			case "pause":
			case "startmenu":
			case "ending":
			default:
				break;
			
			}
			timer.updateUPS();
			
			
			
			//Step 2 render game
			try {
				this.getRenderEngine().render();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			timer.updateFPS();
            /* Update timer */
			timer.update();
			this.hud.getLabelUPS().setText("UPS: " + timer.getUps());
			this.hud.getLabelFPS().setText("FPS: " + timer.getFps());
			
			//Step3 fps cap if vsynch is off
	        //this.sync(60);
			
			//clean up
			
		}
	}
	private boolean nextLevel() {
		if(this.getLevel().isFinished())return true;
		return false;
	}

	/**
	 * limit fps and cpu usage
	 * @param fps
	 */
	public void sync(int fps) {
        double lastLoopTime = timer.getLastLoopTime();
        double now = timer.getTime();
        float targetTime = 1f / fps;

        while (now - lastLoopTime < targetTime) {
            Thread.yield();

            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            now = timer.getTime();
        }
	}

	public void addGameObject(GameObject gameObject) {
		String name = gameObject.getName();
		if (gameObjects.containsKey(name)) gameObject.setName(name + uniqueid++);
		this.gameObjects.put(gameObject.getName(), gameObject);
		this.hud.registerNewObject(gameObject.getName());
	}
	
	public void destroyObject(String name){
		JMenuItem item = hud.getObjects().get(name);
		if (item != null) {
			//System.out.println("item: " + item.getText());
			Map<String, JMenuItem> items = hud.getObjects();
			JMenu objectMenu = hud.getObjectMenu();
			items.remove(name);
			objectMenu.remove(item);
			item.removeActionListener(hud);
			hud.setCurrObject(null);
		}
		GameObject gameObject = this.getGameObjects().get(name);
		if(gameObject != null){
			gameObject.setDestroy(true);
		}
	}

	public RenderEngine getRenderEngine() {
		return renderEngine;
	}

	public void setRenderEngine(RenderEngine renderEngine) {
		this.renderEngine = renderEngine;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public Map<String, GameObject> getGameObjects() {
		return gameObjects;
	}

	public void setGameObjects(Map<String, GameObject> hashMap) {
		this.gameObjects = hashMap;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Level getCurrLevel() {
		return currLevel;
	}

	public void setCurrLevel(Level currLevel) {
		this.currLevel = currLevel;
	}

	public HUD getHud() {
		return hud;
	}

	public void setHud(HUD hud) {
		this.hud = hud;
	}

	public List<GameObject> getDeleteQueue() {
		return deleteQueue;
	}

	public void setDeleteQueue(List<GameObject> deleteQueue) {
		this.deleteQueue = deleteQueue;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public List<GameObject> getMoveQueue() {
		return moveQueue;
	}

	public void setMoveQueue(List<GameObject> moveQueue) {
		this.moveQueue = moveQueue;
	}

	public List<GameObject> getDirtyQueue() {
		return dirtyQueue;
	}

	public void setDirtyQueue(List<GameObject> dirtyQueue) {
		this.dirtyQueue = dirtyQueue;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
	public int getLevelNr() {
		return levelNr;
	}

	public void setLevelNr(int levelNr) {
		this.levelNr = levelNr;
	}

}
