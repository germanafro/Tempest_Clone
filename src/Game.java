import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

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
	private String state = "startmenu";
	private Map<String, GameObject> gameObjects;
	private Map<String, Integer> textureIds;
	private GameEngine gameEngine;
	private RenderEngine renderEngine;
	private boolean pause;
	
	private HUD hud;
	
	public Game(){
		this.setHud(new HUD());
		this.setGameObjects(new HashMap<String, GameObject>());
		this.setTextureIds(new HashMap<String, Integer>());
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
		this.loadTextures(); // TODO: move back to render engine?
		this.renderEngine.initObjects();
		this.gameEngine.run();
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
		while(!glfwWindowShouldClose(this.getRenderEngine().getWindow())){
			//TODO Step 1 do game calculations
			switch(state){
			case "starting":
				this.addGameObject(new Player("player1", this));
				this.setState("playing");
			case "playing":
				if(!this.isPause()){
					
				}else{
					// dont do anything while the game is paused
				}
				break;
			case "startmenu":
			case "ending":
			default:
				break;
			
			}
			
			
			//Step 2 render game
			try {
				this.getRenderEngine().render();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void addGameObject(GameObject obj) {
		// TODO Auto-generated method stub
		this.gameObjects.put(obj.getName(), obj);
	}

	public void loadObjects() {
		// TODO Auto-generated method stub
		int i = 0;
		for (Primitive obj : this.hud.getCurrObjects()){
			this.gameObjects.put(Integer.toString(i++), new GameObject(obj.getType(), obj, this));
		}
	}
	 /**
     * Uses an external class to load a PNG image and bind it as texture
     * @param filename
     * @param textureUnit
     * @return textureID
     */
    private int loadPNGTexture(String filename, int textureUnit) {
        ByteBuffer buf = null;
        int tWidth = 0;
        int tHeight = 0;
         
        try {
            // Open the PNG file as an InputStream
            InputStream in = new FileInputStream(filename);
            // Link the PNG decoder to this stream
            PNGDecoder decoder = new PNGDecoder(in);
             
            // Get the width and height of the texture
            tWidth = decoder.getWidth();
            tHeight = decoder.getHeight();
             
             
            // Decode the PNG file in a ByteBuffer
            buf = ByteBuffer.allocateDirect(
                    4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
            buf.flip();
             
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
         
        // Create a new texture object in memory and bind it
        int texId = GL11.glGenTextures();
        GL13.glActiveTexture(textureUnit);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
         
        // All RGB bytes are aligned to each other and each component is 1 byte
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
         
        // Upload the texture data and generate mip maps (for scaling)
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, tWidth, tHeight, 0, 
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
         
        // Setup the ST coordinate system
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
         
        // Setup what to do when the texture has to be scaled
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, 
                GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, 
                GL11.GL_LINEAR_MIPMAP_LINEAR);

        return texId;
    }
  	
	public void loadTextures(){
		String path = "assets/textures/";
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		System.out.println("loading Textures: ");
		for(File file : listOfFiles){
			String name = file.getName();
			System.out.println(name);
			this.getTextureIds().put(name, this.loadPNGTexture(path + name, GL13.GL_TEXTURE0));
		}
	}
	
	public void destroyObject(String name){
		//temp save obj
		GameObject obj = this.getGameObjects().get(name);
		// remove from registry
		this.getGameObjects().remove(name);
		// free memory
		GL15.glDeleteBuffers(obj.getVboId());
    	GL15.glDeleteBuffers(obj.getVaoId());
		GL15.glDeleteBuffers(obj.getVboId());
        GL15.glDeleteBuffers(obj.getVbocId());
        GL15.glDeleteBuffers(obj.getVbonId());
        GL15.glDeleteBuffers(obj.getVbotId());
		GL15.glDeleteBuffers(obj.getVboiId());
		GL15.glDeleteBuffers(obj.getVaoNormalLinesId());
        GL15.glDeleteBuffers(obj.getVbonlId());
        GL15.glDeleteBuffers(obj.getVbonlcId());
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

	public Map<String, Integer> getTextureIds() {
		return textureIds;
	}

	public void setTextureIds(Map<String, Integer> textureIds) {
		this.textureIds = textureIds;
	}

}
