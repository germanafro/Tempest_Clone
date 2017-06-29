import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import mat.Vec3;

public class GameObject {
	
	//predefines
    private float normalScale=0.1f;
	
    // identity
	private Game game;
	private Primitive geom;
	private String name;
	private int pId = 0;
	
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
  	
  	// indvidual shader data
  	private int textureID = 0;
  	
  	// each Object will have its independent transformation matrix
    private int modelMatrixLocation = 0;
  	
  	public GameObject(String name, Primitive geom, Game game){
  		this.game = game;
  		this.setpId(this.game.getRenderEngine().getpId());
		this.setName(name);
		this.setGeom(geom);
		this.setModelMatrixLocation(GL20.glGetUniformLocation(this.getpId(), "modelMatrix"));
		// assign textures id
		if (game.getTextureIds().get(geom.getTexture()) != null){
			this.setTextureID(game.getTextureIds().get(geom.getTexture()));
		} else this.setTextureID(game.getTextureIds().get("default.png"));
    	// allocate memory
    	this.setVaoId(GL30.glGenVertexArrays());
		this.setVboId(GL15.glGenBuffers());
        this.setVbocId(GL15.glGenBuffers());
        this.setVbonId(GL15.glGenBuffers());
        this.setVbotId(GL15.glGenBuffers());
		this.setVboiId(GL15.glGenBuffers());
		this.setVaoNormalLinesId(GL30.glGenVertexArrays());
        this.setVbonlId(GL15.glGenBuffers());
        this.setVbonlcId(GL15.glGenBuffers());
	}
  	/**
  	 * move the Object one step along the grid
  	 * @param x steps in x direction
  	 * @param y steps in y direction
  	 * @param z steps in z direction
  	 */
  	public void move(int x, int y, int z){
  		Vec3 origin = this.getGeom().getOrigin();
  		origin.x += x * this.getGame().getXStep();
  		origin.y += y * this.getGame().getYStep();
  		origin.z += z * this.getGame().getZStep();
  	}
  	
  	public void buffer(){
    	
    	Primitive currObject = this.getGeom();
    	//failsave just in case something goes wrong
    	currObject.create();
    	//reload object texture
    	
    	// receive vertices from currObject
    	float[] vertices = currObject.getVertices();
		// Sending data to OpenGL requires the usage of (flipped) byte buffers
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
		verticesBuffer.put(vertices);
		verticesBuffer.flip();
		this.setVerticesCount(vertices.length/3);
		
		//TODO Remove
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
		float[] normals = currObject.getNormals();
		FloatBuffer normalsBuffer = BufferUtils.createFloatBuffer(normals.length);
		normalsBuffer.put(normals);
		normalsBuffer.flip();
		
		// Texture Coordinates for each vertex, ST
		
		
		float[] textureCoords = currObject.getTexturecoords();
		System.out.println(Arrays.toString(textureCoords));
		FloatBuffer textureCoordsBuffer = BufferUtils.createFloatBuffer(textureCoords.length);
		textureCoordsBuffer.put(textureCoords);
		textureCoordsBuffer.flip();
		
		// ================================== 2. Define indices for vertices ======================
		
		// OpenGL expects to draw the first vertices in counter clockwise order by default
		// receive indices from currObject
		int[] indices = currObject.getIndices();
		System.out.println("[DEBUG] Indices: " + Arrays.toString( indices));
		this.setIndicesCount(indices.length);
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(this.getIndicesCount());
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
		float[] normalLinesColors = new float[this.getVerticesCount()*8];
		
		pos=0;
		for (int i=0;i<(this.getVerticesCount()*4);i+=4){
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
		
		GL30.glBindVertexArray(this.getVaoId());
		
		// Create a new Vertex Buffer Object (VBO) in memory and select it (bind)
		// A VBO is a collection of Vectors which in this case resemble the location of each vertex.
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVboId());
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
		// Put the VBO in the attributes list at index 0
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		// Deselect (bind to 0) the VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		// Create a new VBO for the indices and select it (bind) - COLORS
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVbocId());
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorsBuffer, GL15.GL_STATIC_DRAW);
        //index 1, in 0 are the vertices stored; 4 values (RGAB) instead of 3 (XYZ)
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0); 
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        // Create a new VBO for the indices and select it (bind) - NORMALS
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVbonId());
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalsBuffer, GL15.GL_STATIC_DRAW);
        //index 2, 3 values (XYZ)
        GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, true, 0, 0); 
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        // Create a new VBO and select it (bind) - TEXTURE COORDS
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVbotId());
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureCoordsBuffer, GL15.GL_STATIC_DRAW);
        //index 3, 2 values (ST)
        GL20.glVertexAttribPointer(3, 2, GL11.GL_FLOAT, true, 0, 0); 
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		// Create a new VBO for the indices and select it (bind) - INDICES
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.getVboiId());
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
		// Deselect (bind to 0) the VBO
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		// _Second_ VAO for normal visualization (optional)
		GL30.glBindVertexArray(this.getVaoNormalLinesId());
		
		// Create a new VBO for normal lines and select it (bind)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVbonlId());
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalLinesBuffer, GL15.GL_STATIC_DRAW);
        //index 0, new VAO; 3 values (XYZ)
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0); 
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        // Create a new VBO for normal lines and select it (bind) - COLOR
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVbonlcId());
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalLinesColorsBuffer, GL15.GL_STATIC_DRAW);
        //index 0, new VAO; 4 values (RGBA)
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0); 
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        // Deselect (bind to 0) the VAO
     	GL30.glBindVertexArray(0);		
    }
  	
  	
	public int getVaoId() {
		return vaoId;
	}
	public void setVaoId(int vaoId) {
		this.vaoId = vaoId;
	}
	public int getVaoNormalLinesId() {
		return vaoNormalLinesId;
	}
	public void setVaoNormalLinesId(int vaoNormalLinesId) {
		this.vaoNormalLinesId = vaoNormalLinesId;
	}
	public int getVboId() {
		return vboId;
	}
	public void setVboId(int vboId) {
		this.vboId = vboId;
	}
	public int getVbocId() {
		return vbocId;
	}
	public void setVbocId(int vbocId) {
		this.vbocId = vbocId;
	}
	public int getVbonId() {
		return vbonId;
	}
	public void setVbonId(int vbonId) {
		this.vbonId = vbonId;
	}
	public int getVbotId() {
		return vbotId;
	}
	public void setVbotId(int vbotId) {
		this.vbotId = vbotId;
	}
	public int getVbonlId() {
		return vbonlId;
	}
	public void setVbonlId(int vbonlId) {
		this.vbonlId = vbonlId;
	}
	public int getVbonlcId() {
		return vbonlcId;
	}
	public void setVbonlcId(int vbonlcId) {
		this.vbonlcId = vbonlcId;
	}
	public int getVboiId() {
		return vboiId;
	}
	public void setVboiId(int vboiId) {
		this.vboiId = vboiId;
	}
	public int getIndicesCount() {
		return indicesCount;
	}
	public void setIndicesCount(int indicesCount) {
		this.indicesCount = indicesCount;
	}
	public int getVerticesCount() {
		return verticesCount;
	}
	public void setVerticesCount(int verticesCount) {
		this.verticesCount = verticesCount;
	}
	public Primitive getGeom() {
		return geom;
	}
	public void setGeom(Primitive geom) {
		this.geom = geom;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public int getModelMatrixLocation() {
		return modelMatrixLocation;
	}
	public void setModelMatrixLocation(int modelMatrixLocation) {
		this.modelMatrixLocation = modelMatrixLocation;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public int getTextureID() {
		return textureID;
	}
	public void setTextureID(int textureID) {
		this.textureID = textureID;
	} 

}
