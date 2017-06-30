import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_C;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_H;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_I;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_K;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_L;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_N;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_O;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_T;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_U;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_V;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import mat.Matrix4;
import mat.PerspectiveMatrix;
import mat.RotationMatrix;
import mat.TranslationMatrix;
import mat.Vec3;

public class RenderEngine {

	
	private Game game;
    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
    private GLFWWindowSizeCallback window_size_callback;
 
    // The window handle
    private long window;
    
    // Window size
    private int WIDTH = 800;
    private int HEIGHT = 640;
    
  	
  	// Shader variables
  	private int vsId = 0;
    private int fsId = 0;
    private int pId = 0;
	private Map<String, Integer> textureIds;

	private int vsNormalsId = 0;
    private int fsNormalsId = 0;
    private int pNormalsId = 0;
    
    // Moving variables
    private int projectionMatrixLocation = 0;
    private int viewMatrixLocation = 0;
    private int projectionMatrixLocationNormals = 0; // separate one for normals
    private int viewMatrixLocationNormals = 0;// separate one for normals
    private int modelMatrixLocationNormals = 0;// separate one for normals
    private Matrix4 projectionMatrix = null;
    private Matrix4 viewMatrix = null;
    private Vec3 modelAngle = new Vec3(0,0,0);
    private Vec3 cameraPos = new Vec3(0,0,-4);
    private float deltaRotX = 5f;
    private float deltaRotY = 5f;
    private float deltaRotZ = 5f;
    
    // toggles & interactions
    private boolean showMesh = true;
    private boolean showNormals = false;
    private boolean useBackfaceCulling = false;
    private boolean useDepthTest = false;
    private int useNormalColoring = 0;
    private int useNormalColoringLocation = 0;
    private int useTexture = 0;
    private int useTextureLocation = 0;
    
    // HUD TODO remove me
    private HUD hud;
    
    public RenderEngine(Game game){
		this.game = game;
		this.hud = this.game.getHud();
		this.setTextureIds(new HashMap<String, Integer>());
		this.hud.setVisible(true);
	}
  	/**
  	 * noteworthy changes: hooked hud to destroy when the window is closed
  	 */
    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
 
        try {
            init();
            setupShaders();
            setupMatrices();
            //initObjects();
 

        } catch (Exception e){
        	e.printStackTrace();
        }
    }
 
    private void init() {
    	
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
    	GLFWErrorCallback.createPrint(System.err).set();
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
 
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
		// Set OpenGL version to 3.2.0
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
 
        // Create the window
        setWindow(glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL));
        if ( getWindow() == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
 
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(getWindow(), setKeyCallback(new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
            	if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, true); // We will detect this in our rendering loop
            	if ( key == GLFW_KEY_UP )
                    modelAngle.x += deltaRotX;
            	if ( key == GLFW_KEY_DOWN )
                    modelAngle.x -= deltaRotX;
            	if ( key == GLFW_KEY_RIGHT )
                    modelAngle.y += deltaRotY;
            	if ( key == GLFW_KEY_LEFT )
                    modelAngle.y -= deltaRotY;
            	if ( key == GLFW_KEY_PAGE_UP )
                    modelAngle.z += deltaRotZ;
            	if ( key == GLFW_KEY_PAGE_DOWN )
                    modelAngle.z -= deltaRotZ;
            	if ( key == GLFW_KEY_BACKSPACE ){
            		modelAngle.x = 0;
            		modelAngle.y = 0;
            		modelAngle.z = 0;
            	}
            	if ( key == GLFW_KEY_M && action == GLFW_PRESS ) {
            		showMesh = !showMesh;
            		if (showMesh) glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
            		else glPolygonMode( GL_FRONT_AND_BACK, GL_FILL );
            	}
            	if ( key == GLFW_KEY_N && action == GLFW_PRESS )
            		showNormals = !showNormals;
            	if ( key == GLFW_KEY_T && action == GLFW_PRESS ){
            		if (useTexture==1)
            			useTexture=0;
            		else
            			useTexture=1;
            	}
            	if ( key == GLFW_KEY_C && action == GLFW_PRESS ) {
            		useBackfaceCulling = !useBackfaceCulling;
            		if (useBackfaceCulling) glEnable(GL_CULL_FACE);
            		else glDisable(GL_CULL_FACE);
            	}
            	if (key == GLFW_KEY_F && action == GLFW_PRESS){
            		useDepthTest = !useDepthTest;
            		System.out.println("depth test:" + useDepthTest);
            		if(useDepthTest)glEnable(GL11.GL_DEPTH_TEST);
            		else glDisable(GL11.GL_DEPTH_TEST);
            	}
            	if ( key == GLFW_KEY_V && action == GLFW_PRESS ){
            		if (useNormalColoring==1)
            			useNormalColoring=0;
            		else
            			useNormalColoring=1;
            	}
            	// Andy's custom keys
            	if ( key == GLFW_KEY_R && action == GLFW_PRESS ){
            		initObjects();
            	}
            	if ( key == GLFW_KEY_H && action == GLFW_PRESS ){
            		if(hud.isVisible()){
            			hud.setVisible(false);
            		}else{
            			hud.setVisible(true);
            		}
        		}
            	// w,S incrase,decrease Y - D,A increase, decrease X - E,Q increase, decrease Scale
            	if ( key == GLFW_KEY_W && action == GLFW_PRESS ){
            		int val = hud.SlideY.getValue();
            		if (val < hud.SlideY.getMaximum()){
            			hud.SlideY.setValue(val+1);
            		}
            	}
            	if ( key == GLFW_KEY_S && action == GLFW_PRESS ){
            		int val = hud.SlideY.getValue();
            		if (val > hud.SlideY.getMinimum()){
            			hud.SlideY.setValue(val-1);
            		}
            	}
            	
            	//Player controls
            	if ( key == GLFW_KEY_A && action == GLFW_PRESS ){
            		Player player = game.getPlayer();
            		Tube tube = game.getTube();
            		if(player != null && tube != null && !game.isPause()){
            			int alphatarget = player.getAlphatarget();
            			if (player.getRalpha() - alphatarget < tube.getStepr()){ // protects from overflowing
            				player.setAlphatarget(alphatarget - tube.getStepr());
            			}
            		}

            	}
            	if ( key == GLFW_KEY_D && action == GLFW_PRESS ){
            		Player player = game.getPlayer();
            		Tube tube = game.getTube();
            		if(player != null && tube != null && !game.isPause()){
            			int alphatarget = player.getAlphatarget();
            			if (alphatarget - player.getRalpha() < tube.getStepr()){
            				player.setAlphatarget(alphatarget + tube.getStepr());
            			}
            		}
            	}
            	
            	if ( key == GLFW_KEY_P && action == GLFW_PRESS){
            		game.setPause(!game.isPause());
            	}
            	if ( key == GLFW_KEY_Q){
            		int val = hud.SlideScale.getValue();
            		if (val > hud.SlideScale.getMinimum()){
            			hud.SlideScale.setValue(val-1);
            		}
            	}
            	if ( key == GLFW_KEY_E){
            		int val = hud.SlideScale.getValue();
            		if (val < hud.SlideScale.getMaximum()){
            			hud.SlideScale.setValue(val+1);
            		}
            	}
            	// I,K incrase,decrease Q - L,J increase, decrease P - O,U increase, decrease Inner Radius
            	if ( key == GLFW_KEY_I && action == GLFW_PRESS ){
            		int val = hud.SlideQ.getValue();
            		if (val < hud.SlideQ.getMaximum()){
            			hud.SlideQ.setValue(val+1);
            		}
            	}
            	if ( key == GLFW_KEY_K && action == GLFW_PRESS ){
            		int val = hud.SlideQ.getValue();
            		if (val > hud.SlideQ.getMinimum()){
            			hud.SlideQ.setValue(val-1);
            		}
            	}
            	if ( key == GLFW_KEY_J && action == GLFW_PRESS ){
            		int val = hud.SlideP.getValue();
            		if (val > hud.SlideP.getMinimum()){
            			hud.SlideP.setValue(val-1);
            		}
            	}
            	if ( key == GLFW_KEY_L && action == GLFW_PRESS ){
            		int val = hud.SlideP.getValue();
            		if (val < hud.SlideP.getMaximum()){
            			hud.SlideP.setValue(val+1);
            		}
            	}
            	if ( key == GLFW_KEY_U){
            		int val = hud.SlideR.getValue();
            		if (val > hud.SlideR.getMinimum()){
            			hud.SlideR.setValue(val-1);
            		}
            	}
            	if ( key == GLFW_KEY_O){
            		int val = hud.SlideR.getValue();
            		if (val < hud.SlideR.getMaximum()){
            			hud.SlideR.setValue(val+1);
            		}
            	}
            	
            }
        })
    );
   
        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
            getWindow(),
            (vidmode.width() - WIDTH) / 2,
            (vidmode.height() - HEIGHT) / 2
        );
 
        // Make the OpenGL context current
        glfwMakeContextCurrent(getWindow());
        // Enable v-sync
        glfwSwapInterval(1);
 
        // Make the window visible
        glfwShowWindow(getWindow());
        
        // Setup a window size callback for viewport adjusting while resizing
        glfwSetWindowSizeCallback(getWindow(), window_size_callback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				// Viewport: Use full display size
		        GL11.glViewport(0, 0, width, height);
			}
        });
        
        
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        
        // Debug: We need version 3.2 or newer
        System.out.println("We need OpenGL version 3.2.0. You use " + GL11.glGetString(GL11.GL_VERSION));
        
        // Viewport: Use full display size
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        
        // Set the clear color - gray
        glClearColor(0.3f, 0.3f, 0.3f, 0.0f);
        
        // Switch to wireframe
        glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
        // -> back to solid faces: glPolygonMode( GL_FRONT_AND_BACK, GL_FILL );
 
        // Backface culling: Shows, if the triangles are correctly defined
        glDisable(GL_CULL_FACE);
        
        // Draw thicker lines
        GL11.glLineWidth(2);
        
    }
    
    
    private void setupMatrices() {
     	
    	// Setup projection and view matrix
    	projectionMatrix = new PerspectiveMatrix(-1,1,-1,1, 1f,11f);
    	viewMatrix = new TranslationMatrix(cameraPos);
    }
    
    public int loadShader(String filename, int type) {
        StringBuilder shaderSource = new StringBuilder();
        int shaderID = 0;
         
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }
         
        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
         
        return shaderID;
    }
    
    private void setupShaders() {
        int errorCheckValue = GL11.glGetError();
         
        // ============================= 1. Shader: For vertices ==================================
        // Load the vertex shader
        vsId = this.loadShader("src/simplePrimitives/vertex.glsl", GL20.GL_VERTEX_SHADER);
        // Load the fragment shader
        fsId = this.loadShader("src/simplePrimitives/fragment.glsl", GL20.GL_FRAGMENT_SHADER);
         
        // Create a new shader program that links both shaders
        pId = GL20.glCreateProgram();
        GL20.glAttachShader(pId, vsId);
        GL20.glAttachShader(pId, fsId);
 
        // Position information will be attribute 0
        GL20.glBindAttribLocation(pId, 0, "in_Position");
        // Color information will be attribute 1
        GL20.glBindAttribLocation(pId, 1, "in_Color");
        // Normal information will be attribute 2
        GL20.glBindAttribLocation(pId, 2, "in_Normal");
        // Texture coordinates information will be attribute 3
        GL20.glBindAttribLocation(pId, 3, "in_TextureCoord");
        
        GL20.glLinkProgram(pId);
        GL20.glValidateProgram(pId);
         
        errorCheckValue = GL11.glGetError();
        if (errorCheckValue != GL11.GL_NO_ERROR) {
        	//todo: error msg
            System.out.println("ERROR - Could not create the shaders:");
            System.exit(-1);
        }
        
        // Get matrices uniform locations
        projectionMatrixLocation = GL20.glGetUniformLocation(pId,"projectionMatrix");
        viewMatrixLocation = GL20.glGetUniformLocation(pId, "viewMatrix");
        
        
        // the switch for toggling normals as vertex colors and texture
        useNormalColoringLocation = GL20.glGetUniformLocation(pId, "useNormalColoring");
        
        
        // ============================= 2. Shader: For normal lines ==============================
        // Each vertex has a position and color, but no normal. So we use a shader without them
        // NVIDIA and ATI graphics cards may ignore missing values, Intel not
        // No valid shader and all values -> no shader used -> white lines
        
        
        // Load the vertex shader
        vsNormalsId = this.loadShader("src/simplePrimitives/vertexNormals.glsl", GL20.GL_VERTEX_SHADER);
        // Load the fragment shader
        fsNormalsId = this.loadShader("src/simplePrimitives/fragmentNormals.glsl", GL20.GL_FRAGMENT_SHADER);
         
        // Create a new shader program that links both shaders
        pNormalsId = GL20.glCreateProgram();
        GL20.glAttachShader(pNormalsId, vsNormalsId);
        GL20.glAttachShader(pNormalsId, fsNormalsId);
 
        // Position information will be attribute 0
        GL20.glBindAttribLocation(pNormalsId, 0, "in_Position");
        // Color information will be attribute 1
        GL20.glBindAttribLocation(pNormalsId, 1, "in_Color");
        
        GL20.glLinkProgram(pNormalsId);
        GL20.glValidateProgram(pNormalsId);
         
        errorCheckValue = GL11.glGetError();
        if (errorCheckValue != GL11.GL_NO_ERROR) {
        	//todo: error msg
            System.out.println("ERROR - Could not create the shaders:"+errorCheckValue);
            System.exit(-1);
        }
        
        // Get matrices uniform locations
        projectionMatrixLocationNormals = GL20.glGetUniformLocation(pNormalsId,"projectionMatrix");
        viewMatrixLocationNormals = GL20.glGetUniformLocation(pNormalsId, "viewMatrix");
        modelMatrixLocationNormals = GL20.glGetUniformLocation(pNormalsId, "modelMatrix");        
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
    
    /**
     * buffer all Objects
     */
    public void initObjects(){
    	Iterator<GameObject> gameObjects = this.game.getGameObjects().values().iterator();
    	while(gameObjects.hasNext()){
    		GameObject gameObject = gameObjects.next(); 
    		for(Primitive obj : gameObject.getGeom()){
    			obj.buffer();
    		}
    	}
     		
    }
    
    public void doAlloc(Primitive obj){
		// allocate memory
    	obj.setVaoId(GL30.glGenVertexArrays());
		obj.setVboId(GL15.glGenBuffers());
        obj.setVbocId(GL15.glGenBuffers());
        obj.setVbonId(GL15.glGenBuffers());
        obj.setVbotId(GL15.glGenBuffers());
		obj.setVboiId(GL15.glGenBuffers());
		obj.setVaoNormalLinesId(GL30.glGenVertexArrays());
        obj.setVbonlId(GL15.glGenBuffers());
        obj.setVbonlcId(GL15.glGenBuffers());
        obj.setAlloc(false);
	}
    public void deleteAlloc(Primitive obj){
    				// free memory
    	System.out.println("cleaning: " + obj.getType());
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
    
    /**
     * changes of notice: checks for hud dirty bit. if true reloads the geometric Object into the buffer
     * @throws Exception when exiting the window.. lol
     */
    public void render() throws Exception {
    	
        // Run once unless the user has attempted to close
        // the window or has pressed the ESCAPE key.
        if (!glfwWindowShouldClose(getWindow())) {
        	
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
          
            // =============================== Update matrices ====================================
            // Upload matrices to the uniform variables to shader program 0
			GL20.glUseProgram(pId);
            GL20.glUniformMatrix4fv(projectionMatrixLocation, false , toFFB(projectionMatrix));
            GL20.glUniformMatrix4fv(viewMatrixLocation, false, toFFB(viewMatrix));
            
            
            // Upload normal coloring and texture toggle
            GL20.glUniform1i(useNormalColoringLocation, useNormalColoring);
             
            GL20.glUseProgram(0);

            for (GameObject gameObject : this.game.getDeleteQueue()){
            	for(Primitive obj : gameObject.getGeom()){
    				GL20.glUseProgram(pId);
					this.deleteAlloc(obj);
					GL20.glUseProgram(0);
    			}
            	gameObject.setGeom(null);
            }
            this.game.setDeleteQueue(new ArrayList<GameObject>());
            
            // ================================== Draw objects =====================================
            
            Iterator<GameObject> gameObjects = this.game.getGameObjects().values().iterator();
        	while(gameObjects.hasNext()){
        		
        		GameObject gameObject = gameObjects.next(); 
        		for(Primitive obj : gameObject.getGeom()){
        			//gameObject.buffer();
        			GL20.glUseProgram(pId);
        			
        			// Step 1 allocate memory Step2 buffer data 
        			//schedule mem alloc for new geom
        			if(obj.isAlloc()){
        				
        				doAlloc(obj);
        			}
        			GL20.glUseProgram(0);
        		}
        		
        		//update dirty gameObjects
        		GL20.glUseProgram(pId);
    			if(gameObject.isDirty()){
    				gameObject.update();
    				gameObject.buffer(); // TODO encapsulate in update()
    				gameObject.setDirty(false);
    			}
    			GL20.glUseProgram(0);
        		for(Primitive obj : gameObject.getGeom()){
        			//gameObject.buffer();
        			GL20.glUseProgram(pId);
        			
        			// bind the individual texture
        			GL11.glBindTexture(GL11.GL_TEXTURE_2D, obj.getTextureID());
        			useTextureLocation = GL20.glGetUniformLocation(pId, "useTexture");
        			GL20.glUniform1i(useTextureLocation, useTexture);
        			
        			// setup individual modelmatrix
        			// first translate, then rotate. Remember the flipped order
                    // Einheitliche Rotation (zusätzliche individuelle Berechnungen finden in geom statt)
        			Matrix4 modelMatrix = obj.getModelMatrix();
                    modelMatrix = (Matrix4) new RotationMatrix(modelAngle.z, mat.Axis.Z)
                    		.mul((new RotationMatrix(modelAngle.y, mat.Axis.Y))
                    		.mul(new RotationMatrix(modelAngle.x, mat.Axis.X))
                    		.mul(modelMatrix)); // ... and rotate, multiply matrices 
                    
        			int modelMatrixLocation = GL20.glGetUniformLocation(pId, "modelMatrix");
        			GL20.glUniformMatrix4fv(modelMatrixLocation, false, toFFB(modelMatrix));
        		
        			GL30.glBindVertexArray(obj.getVaoId());
        			GL20.glEnableVertexAttribArray(0);
        			GL20.glEnableVertexAttribArray(1);
        		GL20.glEnableVertexAttribArray(2);
        		GL20.glEnableVertexAttribArray(3); // texture coordinates
             
            // Bind to the index VBO that has all the information about the order of the vertices
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, obj.getVboiId());
             
            // Draw the vertices
            GL11.glDrawElements(GL11.GL_TRIANGLE_STRIP, obj.getIndicesCount(), GL11.GL_UNSIGNED_INT, 0);
            
            // Put everything back to default (deselect)
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(2);
            GL20.glDisableVertexAttribArray(3);
            GL30.glBindVertexArray(0);
            GL20.glUseProgram(0);
            
            // ================================ Draw normal lines =================================
            
            if (showNormals){
	            
	            GL20.glUseProgram(pNormalsId);
	            
	            // Upload matrices to the uniform variables
	            GL20.glUniformMatrix4fv(projectionMatrixLocationNormals, false , toFFB(projectionMatrix));
	            GL20.glUniformMatrix4fv(viewMatrixLocationNormals, false, toFFB(viewMatrix));
	            GL20.glUniformMatrix4fv(modelMatrixLocationNormals, false, toFFB(modelMatrix));
	             
	            // Bind to the VAO that has all the information about the normal lines
	            GL30.glBindVertexArray(obj.getVaoNormalLinesId());
	            GL20.glEnableVertexAttribArray(0);
	            GL20.glEnableVertexAttribArray(1);
	             
	            // Bind to the VBO that has all the information about the order of the vertices
	            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, obj.getVbonId());
	             
	            // Draw the vertices
	            GL11.glDrawArrays(GL11.GL_LINES, 0, obj.getVerticesCount()*2);
	            
	            // Put everything back to default (deselect)
	            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	            GL20.glDisableVertexAttribArray(0);
	            GL20.glDisableVertexAttribArray(1);
	            GL30.glBindVertexArray(0);
	            GL20.glUseProgram(0);
            }
        	}//end for loop
        	}
            // Swap the color buffer. We never draw directly to the screen, only in this buffer. So we need to display it
    		glfwSwapBuffers(getWindow());
            
            // Poll for window events. The key callback above will only be invoked during this call.
            glfwPollEvents();
        	
        }
    }
    
    /**
     * Converts a Matrix4 to a flipped float buffer
     * @param m
     * @return
     */
	private FloatBuffer toFFB(Matrix4 m){
		FloatBuffer res = BufferUtils.createFloatBuffer(16);
		for (int i=0;i<4;i++){
			for (int j=0;j<4;j++){
				res.put((float) m.get(i).get(j));
			}
		}
		return (FloatBuffer) res.flip();
	}
	public long getWindow() {
		return window;
	}
	public void setWindow(long window) {
		this.window = window;
	}
	public GLFWKeyCallback getKeyCallback() {
		return keyCallback;
	}
	public GLFWKeyCallback setKeyCallback(GLFWKeyCallback keyCallback) {
		this.keyCallback = keyCallback;
		return keyCallback;
	}
	public GLFWErrorCallback getErrorCallback() {
		return errorCallback;
	}
	public void setErrorCallback(GLFWErrorCallback errorCallback) {
		this.errorCallback = errorCallback;
	}
	 public int getpId() {
			return pId;
	}
	public void setpId(int pId) {
			this.pId = pId;
	}
	public Map<String, Integer> getTextureIds() {
		return textureIds;
	}
	public void setTextureIds(Map<String, Integer> textureIds) {
		this.textureIds = textureIds;
	}
}
