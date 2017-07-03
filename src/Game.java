import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Stores most of the games data
 * runs gameloop
 * distributes workload
 * @author Andreas Berger
 *
 */

//TODO place holder
public class Game {
	private int levelNr = 1;
	private String state = "reset";
	private Map<String, GameObject> gameObjects;
	private List<GameObject> deleteQueue = new ArrayList<GameObject>();
	private List<GameObject> moveQueue = new ArrayList<GameObject>();
	private List<GameObject> dirtyQueue = new ArrayList<GameObject>();
	private GameEngine gameEngine;
	private RenderEngine renderEngine;
	private boolean pause;
	private boolean shouldStart = false;
	private HUD hud;
	private Timer timer = new Timer();
	public float shootingSpeed = 1f/10; // limiter for player shooting speed
	public double shootTime = 0f;
	public int shotsFired = 0;
	public int enemyFired = 0;
	public boolean isScoreUp = false;
	public int helpCounter = 0;
	private int uniqueid = 0;
	private Level level = null;
	private Sound bgm = null;
	private float bgmvolume = -10f;
	private float sfxvolume = 0f;
	public Map<String, Sound> mapSFX = null;
	public Map<String, Sound> mapBGM = null;
	
	public Game(){
		this.setHud(new HUD(this));
		this.setGameObjects(new HashMap<String, GameObject>());
		//TODO don't do that! 
		//this.startNewGame();
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
	/**
	 * initialize buffers and tools then runs the gameloop
	 */
	public void run() {
		// TODO Auto-generated method stub
		this.renderEngine.run();
		this.renderEngine.loadTextures(); // TODO: move back to render engine?
		this.loadMapSFX();
		this.loadMapBGM();
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
	/**
	 * the gameloop
	 * coordinate the workflow
	 */
	private void loop(){
		while(!glfwWindowShouldClose(this.getRenderEngine().getWindow())){
			//TODO Step 1 do game calculations
			float delta = timer.getDelta();
			switch(state){
			case "startmenu":
				if(shouldStart) {
					this.setLevelNr(1);
					this.setState("load");
					shouldStart = false;
				}
				break;
			case "starting":
				//TODO add countdown
				this.addGameObject(level.getPlayer());
				this.addGameObject(level.getTube());
				this.addGameObject(level.getBackground());
				this.addGameObject(new DisplayBoard("score" + this.helpCounter++, this)); // Sollte der Counter für die Abschüsse werden.
				this.bgm.stop();
				this.bgmLoop(level.getBgm());
				this.setState("ready");
				break;
			case "playing":
				if(this.nextLevel()){
					this.setLevelNr(this.getLevelNr() + 1);
					this.setState("load");
					break;
				}
				
				this.gameEngine.spawnEnemy();
				this.gameEngine.queueObjects();
				this.gameEngine.moveObjects();

				break;
			case "load": //Load next level!
				this.sleep(1000);
				pause = true;
				deleteQueue.clear();
				moveQueue.clear();
				dirtyQueue.clear();
				gameObjects.clear();
				this.shotsFired = 0;
				this.enemyFired = 0;
				this.setState("starting");
				switch(this.getLevelNr()){
				case 0:
					this.setLevel(Levels.StartMenu(this));
					this.addGameObject(level.getPlayer());
					this.addGameObject(level.getTube());
					this.addGameObject(level.getBackground());
					if(this.bgm != null) this.bgm.stop();
					this.bgmLoop(level.getBgm());
					shouldStart = false;
					this.setState("startmenu");
					break;
				case 1:
					this.setLevel(Levels.Level1(this));
					break;
				case 2:
					this.setLevel(Levels.Level2(this));
					break;
				case 3:
					this.setLevel(Levels.Level3(this));
					break;
				default:
					// this is the happy Ending!
					this.setLevel(Levels.Ending(this));
					this.addGameObject(level.getTube());
					this.bgm.stop();
					this.bgmLoop(level.getBgm());
					this.setState("end");
					this.sleep(1000);
					shouldStart = false;
					break;
				}
				break;
			case "reset":
				this.setLevelNr(0);
				this.setState("load");
				break;
			case "pause":
				this.bgm.stop();
				break;
			case "ending":
				this.sleep(1000);
				pause = true;
				deleteQueue.clear();
				moveQueue.clear();
				dirtyQueue.clear();
				gameObjects.clear();
				this.shotsFired = 0;
				this.enemyFired = 0;
				this.setLevel(Levels.GameOver(this));
				//this.addGameObject(level.getPlayer());
				this.addGameObject(level.getTube());
				//this.addGameObject(level.getBackground());
				this.bgm.stop();
				this.bgmLoop(level.getBgm());
				shouldStart = false;
				this.setState("end");
				break;
			case "end":
				if(shouldStart) {
					this.setState("reset");
					shouldStart = false;
				}
				break;
			case "ready":
				// TODO add ready message 
				if(shouldStart) {
					this.setState("playing");
					shouldStart = false;
				}
				break;
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
		//clean up
		if(bgm != null) {bgm.stop(); bgm.close();}
	}
	private boolean nextLevel() {
		if(this.getLevel().isFinished())return true;
		return false;
	}

	/**
	 * limit fps and cpu usage
	 * do not use with v-synch enabled !
	 * @param fps the targetet fps
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
	/**
	 * releases cpu while waiting
	 * @param ms time in ms to wait
	 */
	public void sleep(int ms) {
        double lastLoopTime = timer.getLastLoopTime();
        double now = timer.getTime();
        float targetTime = 1f/((float)(ms)/1000);

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
	/**
	 * load clip from map and begin a loop
	 * @param name name of mp3 file within assets/sounds/bgm/ to play
	 */
	public void bgmLoop(String name){
		if(bgm != null)	this.bgm.stop();
		this.bgm = mapBGM.get(name);
		this.bgm.setVolume(this.bgmvolume );
		this.bgm.loop();
		
	}
	/**
	 * load clip from map and play once
	 * multiple access to the same sound will cancel one another
	 * @param name name of mp3 file within assets/sounds/sfx/ to play
	 */
	public void sfxPlay(String name){
		Sound sound = this.mapSFX.get(name);
		sound.setVolume(this.sfxvolume );
		sound.play();
	}
	/**
	 * adds a GameObject to the map and readies it for rendering
	 * @param gameObject 
	 */
	public void addGameObject(GameObject gameObject) {
		String name = gameObject.getName();
		if (gameObjects.containsKey(name)) gameObject.setName(name + uniqueid++);
		this.gameObjects.put(gameObject.getName(), gameObject);
		this.hud.registerNewObject(gameObject.getName());
		this.sfxPlay(gameObject.getSpawnSound());
	}
	/**
	 * an attempt to clean up after destruction of an Object
	 * @param name
	 */
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
	/**
	 * loads all sfx mp3 files in assets/sounds/sfx/ into clips that are stored in a map
	 */
	private  void loadMapSFX(){
    	mapSFX = new HashMap<String, Sound>();
    	File folder = new File("assets/sounds/sfx/");
		File[] listOfFiles = folder.listFiles();
		System.out.println("loading Textures: ");
		for(File file : listOfFiles){
			String name = file.getName();
			System.out.println(name);
			if(name.toLowerCase().contains(".mp3")) {
				Sound sfx = new Sound("sfx/" + name);
				mapSFX.put(file.getName(), sfx); 
			}
        }
    }
	/**
	 * loads all bgm mp3 files in assets/sounds/bgm/ into clips that are stored in a map
	 */
	private  void loadMapBGM(){
    	mapBGM = new HashMap<String, Sound>();
    	File folder = new File("assets/sounds/bgm/");
		File[] listOfFiles = folder.listFiles();
		System.out.println("loading Textures: ");
		for(File file : listOfFiles){
			String name = file.getName();
			System.out.println(name);
			if(name.toLowerCase().contains(".mp3")) {
				Sound bgm = new Sound("bgm/" + name);
				mapBGM.put(file.getName(), bgm); 
			}
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

	public GameEngine getGameEngine() {
		return gameEngine;
	}

	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	public float getShootingSpeed() {
		return shootingSpeed;
	}

	public void setShootingSpeed(float shootingSpeed) {
		this.shootingSpeed = shootingSpeed;
	}

	public double getShootTime() {
		return shootTime;
	}

	public void setShootTime(double shootTime) {
		this.shootTime = shootTime;
	}

	public int getShotsFired() {
		return shotsFired;
	}

	public void setShotsFired(int shotsFired) {
		this.shotsFired = shotsFired;
	}

	public int getEnemyFired() {
		return enemyFired;
	}

	public void setEnemyFired(int enemyFired) {
		this.enemyFired = enemyFired;
	}

	public int getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(int uniqueid) {
		this.uniqueid = uniqueid;
	}

	public Sound getBgm() {
		return bgm;
	}

	public void setBgm(Sound bgm) {
		this.bgm = bgm;
	}

	public float getBgmvolume() {
		return bgmvolume;
	}

	public void setBgmvolume(float bgmvolume) {
		this.bgmvolume = bgmvolume;
	}

	public float getSfxvolume() {
		return sfxvolume;
	}

	public void setSfxvolume(float sfxvolume) {
		this.sfxvolume = sfxvolume;
	}

	public boolean isShouldStart() {
		return shouldStart;
	}

	public void setShouldStart(boolean shouldStart) {
		this.shouldStart = shouldStart;
	}

}
