import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_C;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_N;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_T;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_V;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_H;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_I;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_K;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_L;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_U;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_O;
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
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

/**
 * This document is mostly copied from simplePrimitives
 * a number of functions have been altered to interject the loop() function and allow for on-the-fly object change
 * additional keyboard controls have been added
 * object geometry has been moved to the respective Objects
 * a HUD has been added
 * @author Thorsten Gattinger, Andreas Berger
 *
 */
public class Engine {
	
	
	 
    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
    private GLFWWindowSizeCallback window_size_callback;
 
    // The window handle
    private long window;
    
    // Window size
    private int WIDTH = 800;
    private int HEIGHT = 640;
    
    // Buffer IDs
  	private int vaoId = 0;
  	private int vaoNormalLinesId = 0;
  	private int vboId = 0;	//vertex
  	private int vbocId = 0;	//color
  	private int vbonId = 0;	//normal
  	private int vbotId = 0;	//texture coords
  	private int vbonlId = 0;	//normal lines
  	private int vbonlcId = 0;	//normal lines color
  	private int vboiId = 0;	//index
  	private int indicesCount = 0;
  	private int verticesCount = 0;
  	
  	// Shader variables
  	private int vsId = 0;
    private int fsId = 0;
    private int pId = 0;
    private int vsNormalsId = 0;
    private int fsNormalsId = 0;
    private int pNormalsId = 0;
    private int textureID = 0;
    
    // Moving variables
    private int projectionMatrixLocation = 0;
    private int viewMatrixLocation = 0;
    private int modelMatrixLocation = 0;
    private int projectionMatrixLocationNormals = 0; // separate one for normals
    private int viewMatrixLocationNormals = 0;// separate one for normals
    private int modelMatrixLocationNormals = 0;// separate one for normals
    private Matrix4 projectionMatrix = null;
    private Matrix4 viewMatrix = null;
    private Matrix4 modelMatrix = null;
    private Vec3 modelAngle = new Vec3(0,0,0);
    private Vec3 cameraPos = new Vec3(0,0,-4);
    private float deltaRotX = 5f;
    private float deltaRotY = 5f;
    private float deltaRotZ = 5f;
    private float normalScale=0.1f;
    
    // toggles & interactions
    private boolean showMesh = true;
    private boolean showNormals = false;
    private boolean useBackfaceCulling = false;
    private int useNormalColoring = 0;
    private int useNormalColoringLocation = 0;
    private int useTexture = 0;
    private int useTextureLocation = 0;
    
    // HUD
    private HUD hud = new HUD();
    
    public Engine(){
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
            setupTextures();
            setupMatrices();
            initObject();
            loop();
 
            // Release window and window callbacks
            glfwDestroyWindow(window);
            keyCallback.free();
        } catch (Exception e){
        	e.printStackTrace();
        } finally {
        	hud.dispose();
            // Terminate GLFW and release the GLFWerrorfun
            glfwTerminate();
            errorCallback.free();
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
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
 
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
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
            	if ( key == GLFW_KEY_V && action == GLFW_PRESS ){
            		if (useNormalColoring==1)
            			useNormalColoring=0;
            		else
            			useNormalColoring=1;
            	}
            	// Andy's custom keys
            	if ( key == GLFW_KEY_R && action == GLFW_PRESS ){
            		initObject();
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
            	if ( key == GLFW_KEY_A && action == GLFW_PRESS ){
            		int val = hud.SlideX.getValue();
            		if (val > hud.SlideX.getMinimum()){
            			hud.SlideX.setValue(val-1);
            		}
            	}
            	if ( key == GLFW_KEY_D && action == GLFW_PRESS ){
            		int val = hud.SlideX.getValue();
            		if (val < hud.SlideX.getMaximum()){
            			hud.SlideX.setValue(val+1);
            		}
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
        }
    );
   
        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
            window,
            (vidmode.width() - WIDTH) / 2,
            (vidmode.height() - HEIGHT) / 2
        );
 
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
 
        // Make the window visible
        glfwShowWindow(window);
        
        // Setup a window size callback for viewport adjusting while resizing
        glfwSetWindowSizeCallback(window, window_size_callback = new GLFWWindowSizeCallback() {
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
        modelMatrixLocation = GL20.glGetUniformLocation(pId, "modelMatrix");
        
        // the switch for toggling normals as vertex colors and texture
        useNormalColoringLocation = GL20.glGetUniformLocation(pId, "useNormalColoring");
        useTextureLocation = GL20.glGetUniformLocation(pId, "useTexture");
        
        
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
    
    private void setupTextures() {
        textureID = this.loadPNGTexture("assets/gdv.png", GL13.GL_TEXTURE0);
    }
    /**
     * receive geometrical data from the Object currently saved in the hud.
     * loads the data into a buffer
     */
    private void initObject(){
    	//failsave just in case somethign goes wrong
    	hud.getCurrObject().create();
    	
    	// receive vertices from currObject
    	float[] vertices = hud.getCurrObject().getVertices();
		// Sending data to OpenGL requires the usage of (flipped) byte buffers
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
		verticesBuffer.put(vertices);
		verticesBuffer.flip();
		verticesCount = vertices.length/3;
		
		// Colors for each vertex, RGBA
		float[] colors = {
		        1f, 0f, 0f, 1f,
		        0f, 1f, 0f, 1f,
		        0f, 0f, 1f, 1f,
		        0.5f, 0.5f, 0.5f, 1f,
		        1f, 0f, 0f, 1f,
		        0f, 1f, 0f, 1f
		};
		FloatBuffer colorsBuffer = BufferUtils.createFloatBuffer(colors.length);
		colorsBuffer.put(colors);
		colorsBuffer.flip();
		
		// receive Normals for each vertex, XYZ from currObject
		float[] normals = hud.getCurrObject().getNormals();
		FloatBuffer normalsBuffer = BufferUtils.createFloatBuffer(normals.length);
		normalsBuffer.put(normals);
		normalsBuffer.flip();
		
		// Texture Coordinates for each vertex, ST
		float[] textureCoords = {
				0.0f, 1.0f,
				0.5f, 1.0f,
				1.0f, 1.0f,
				0.0f, 0.0f,
				0.5f, 0.0f,
				1.0f, 0.0f,
				};
		FloatBuffer textureCoordsBuffer = BufferUtils.createFloatBuffer(textureCoords.length);
		textureCoordsBuffer.put(textureCoords);
		textureCoordsBuffer.flip();
		
		// ================================== 2. Define indices for vertices ======================
		
		// OpenGL expects to draw the first vertices in counter clockwise order by default
		// receive indices from currObject
		int[] indices = hud.getCurrObject().getIndices();
		System.out.println("[DEBUG] Indices: " + Arrays.toString( indices));
		indicesCount = indices.length;
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indicesCount);
		indicesBuffer.put(indices);
		indicesBuffer.flip();
		
		// ============== 2.5 (optional) Define normal lines for visualization ====================
		
		// normal lines. Each line is represented by his start "vertex" and and "vertex + normalScale*normal"
		float[] normalLines = new float[vertices.length*2];
		
		int pos=0;
		
		// in each loop we set two XYZ points
		for (int i=0;i<vertices.length;i+=3){
			normalLines[pos++]=vertices[i];
			normalLines[pos++]=vertices[i+1];
			normalLines[pos++]=vertices[i+2];
			normalLines[pos++]=vertices[i]+normalScale*normals[i];
			normalLines[pos++]=vertices[i+1]+normalScale*normals[i+1];
			normalLines[pos++]=vertices[i+2]+normalScale*normals[i+2];
		}
		FloatBuffer normalLinesBuffer = BufferUtils.createFloatBuffer(normalLines.length);
		normalLinesBuffer.put(normalLines);
		normalLinesBuffer.flip();
		
		// color for normal lines. Each vertex has the same RGBA value (1,1,0,1) -> yellow
		float[] normalLinesColors = new float[(verticesCount)*8];
		
		pos=0;
		for (int i=0;i<(verticesCount*4);i+=4){
			normalLinesColors[pos++]=1f;
			normalLinesColors[pos++]=1f;
			normalLinesColors[pos++]=0f;
			normalLinesColors[pos++]=1f;
			normalLinesColors[pos++]=1f;
			normalLinesColors[pos++]=1f;
			normalLinesColors[pos++]=0f;
			normalLinesColors[pos++]=1f;
		}		
		FloatBuffer normalLinesColorsBuffer = BufferUtils.createFloatBuffer(normalLinesColors.length);
		normalLinesColorsBuffer.put(normalLinesColors);
		normalLinesColorsBuffer.flip();
				
		// ================================== 3. Make the data accessible =========================
		
		// Create a new Vertex Array Object in memory and select it (bind)
		// A VAO can have up to 16 attributes (VBO's) assigned to it by default
		vaoId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoId);
		
		// Create a new Vertex Buffer Object (VBO) in memory and select it (bind)
		// A VBO is a collection of Vectors which in this case resemble the location of each vertex.
		vboId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
		// Put the VBO in the attributes list at index 0
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		// Deselect (bind to 0) the VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		// Create a new VBO for the indices and select it (bind) - COLORS
        vbocId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbocId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorsBuffer, GL15.GL_STATIC_DRAW);
        //index 1, in 0 are the vertices stored; 4 values (RGAB) instead of 3 (XYZ)
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0); 
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        // Create a new VBO for the indices and select it (bind) - NORMALS
        vbonId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbonId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalsBuffer, GL15.GL_STATIC_DRAW);
        //index 2, 3 values (XYZ)
        GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, true, 0, 0); 
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        // Create a new VBO and select it (bind) - TEXTURE COORDS
        vbotId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbotId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureCoordsBuffer, GL15.GL_STATIC_DRAW);
        //index 3, 2 values (ST)
        GL20.glVertexAttribPointer(3, 2, GL11.GL_FLOAT, true, 0, 0); 
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		// Create a new VBO for the indices and select it (bind) - INDICES
		vboiId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
		// Deselect (bind to 0) the VBO
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		// _Second_ VAO for normal visualization (optional)
		vaoNormalLinesId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoNormalLinesId);
		
		// Create a new VBO for normal lines and select it (bind)
        vbonlId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbonlId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalLinesBuffer, GL15.GL_STATIC_DRAW);
        //index 0, new VAO; 3 values (XYZ)
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0); 
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        // Create a new VBO for normal lines and select it (bind) - COLOR
        vbonlcId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbonlcId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalLinesColorsBuffer, GL15.GL_STATIC_DRAW);
        //index 0, new VAO; 4 values (RGBA)
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0); 
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        // Deselect (bind to 0) the VAO
     	GL30.glBindVertexArray(0);
     		
    }
    /**
     * changes of notice: checks for hud dirty bit. if true reloads the geometrical Object into the buffer
     * @throws Exception when exiting the window.. lol
     */
    private void loop() throws Exception {
    	
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
        	
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            
            // =============================== Update HUD dirty bit  ====================================
            if(hud.isDirty()){
            	initObject();
            	hud.setDirty(false);
            }
            
            // =============================== Update matrices ====================================
            
            // first translate, then rotate. Remember the flipped order
            modelMatrix = new TranslationMatrix(new Vec3(0,0,1));  // translate...
            modelMatrix = (Matrix4) new RotationMatrix(modelAngle.z, mat.Axis.Z)
            		.mul((new RotationMatrix(modelAngle.y, mat.Axis.Y))
            		.mul(new RotationMatrix(modelAngle.x, mat.Axis.X))
            		.mul(modelMatrix)); // ... and rotate, multiply matrices 
            
            // Upload matrices to the uniform variables to shader program 0
            GL20.glUseProgram(pId);
             
            GL20.glUniformMatrix4fv(projectionMatrixLocation, false , toFFB(projectionMatrix));
            GL20.glUniformMatrix4fv(viewMatrixLocation, false, toFFB(viewMatrix));
            GL20.glUniformMatrix4fv(modelMatrixLocation, false, toFFB(modelMatrix));
            
            // Upload normal coloring and texture toggle
            GL20.glUniform1i(useNormalColoringLocation, useNormalColoring);
            GL20.glUniform1i(useTextureLocation, useTexture);
             
            GL20.glUseProgram(0);

            // ================================== Draw object =====================================
            
            GL20.glUseProgram(pId);

            // Bind to the VAO that has all the information about the vertices
            GL30.glBindVertexArray(vaoId);
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnableVertexAttribArray(2);
            GL20.glEnableVertexAttribArray(3); // texture coordinates
             
            // Bind to the index VBO that has all the information about the order of the vertices
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
             
            // Draw the vertices
            GL11.glDrawElements(GL11.GL_TRIANGLE_STRIP, indicesCount, GL11.GL_UNSIGNED_INT, 0);
            
            // Put everything back to default (deselect)
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
	            GL30.glBindVertexArray(vaoNormalLinesId);
	            GL20.glEnableVertexAttribArray(0);
	            GL20.glEnableVertexAttribArray(1);
	             
	            // Bind to the VBO that has all the information about the order of the vertices
	            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbonlId);
	             
	            // Draw the vertices
	            GL11.glDrawArrays(GL11.GL_LINES, 0, verticesCount*2);
	            
	            // Put everything back to default (deselect)
	            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	            GL20.glDisableVertexAttribArray(0);
	            GL20.glDisableVertexAttribArray(1);
	            GL30.glBindVertexArray(0);
	            GL20.glUseProgram(0);
            }
            
            // Swap the color buffer. We never draw directly to the screen, only in this buffer. So we need to display it
    		glfwSwapBuffers(window);
            
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
 
}