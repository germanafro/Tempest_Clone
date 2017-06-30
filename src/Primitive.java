import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import mat.Matrix4;
import mat.RotationMatrix;
import mat.TranslationMatrix;
import mat.Vec3;
import mat.Vec4;

/**
 * base class for geometrical objects
 * @author Andreas Berger
 *
 */
public abstract class Primitive {
	// x,y,z directional stretch
	private int x = 1;
	private int y = 1;
	private int z = 1;
	//x,y,z number of vertices in direction, where applicable
	private int xTiles = 3;
	private int yTiles = 3;
	private int zTiles = 3;
	private int r = 100;
	private int p = 1;
	private int q = 1;
	private int scale = 100;
	private float[] vertices;
	private float[] texturecoords;
	private float[] normals;
	private int[] indices;
	private boolean streched = false;
	private boolean alloc = true;
	private boolean update = false;
	protected String type;
	private String texture;
	private Vec3 origin;
	private Matrix4[] matrices;
    private Matrix4 modelMatrix = new TranslationMatrix(new Vec3(0,0,1));
	
	// indvidual shader data
	private int textureID = 0;
	
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
	//predefines
	 private float normalScale=0.1f;
	
	// each Object will have its independent transformation matrix
	private int modelMatrixLocation = 0;
	
	public String getType() {
		return type;
	}
	/**
	 * stores basic geometric data
	 * @param x
	 * @param y
	 * @param scale
	 */
	public Primitive(int x , int y, int scale, Game game){
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.type = "primitive";
		this.setTexture("default.png");
		this.setOrigin(new Vec3(0.0, 0.0, 0.0));
		// assign textures id
		if (game.getTextureIds().get(this.getTexture()) != null){
			this.setTextureID(game.getTextureIds().get(this.getTexture()));
		} else this.setTextureID(game.getTextureIds().get("default.png"));
	}
	
	public Primitive(int x , int y, int scale, Game game, String texture){
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.type = "primitive";
		this.setTexture(texture);
		this.setOrigin(new Vec3(0.0, 0.0, 0.0));
		// assign textures id
		if (game.getTextureIds().get(this.getTexture()) != null){
			this.setTextureID(game.getTextureIds().get(this.getTexture()));
		} else this.setTextureID(game.getTextureIds().get("default.png"));
	}
	/**
	 * non trivial every child needs to specify it's own create method
	 */
	abstract void create();
	
	public void buffer(){
		
		
    	//failsave just in case something goes wrong
		// TODO check if there were any changes
    	this.create();
    	//reload object texture
    	
    	// receive vertices from this
    	float[] vertices = this.getVertices();
		// Sending data to OpenGL requires the usage of (flipped) byte buffers
    	//System.out.println(Arrays.toString(vertices));
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
		
		// receive Normals for each vertex, XYZ from this
		float[] normals = this.getNormals();
		FloatBuffer normalsBuffer = BufferUtils.createFloatBuffer(normals.length);
		normalsBuffer.put(normals);
		normalsBuffer.flip();
		
		// Texture Coordinates for each vertex, ST
		
		
		float[] textureCoords = this.getTexturecoords();
		//System.out.println(Arrays.toString(textureCoords));
		FloatBuffer textureCoordsBuffer = BufferUtils.createFloatBuffer(textureCoords.length);
		textureCoordsBuffer.put(textureCoords);
		textureCoordsBuffer.flip();
		
		// ================================== 2. Define indices for vertices ======================
		
		// OpenGL expects to draw the first vertices in counter clockwise order by default
		// receive indices from this
		int[] indices = this.getIndices();
		//System.out.println("[DEBUG] Indices: " + Arrays.toString( indices));
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

	public void matricesTomodelMatrix(){
		Matrix4[] matrices = this.getMatrices();
		this.modelMatrix = null;
		this.modelMatrix = new TranslationMatrix(new Vec3(0,0,0));
		if (matrices != null){
			for (Matrix4 matrix : matrices){
				this.modelMatrix = (Matrix4) matrix.mul(this.modelMatrix);
			}
		}
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
	
	public float[] getVertices() {
		return vertices;
	}
	public void setVertices(float[] vertices) {
		this.vertices = vertices;
	}
	public float[] getNormals() {
		return normals;
	}
	public void setNormals(float[] normals) {
		this.normals = normals;
	}
	public int[] getIndices() {
		return indices;
	}
	public void setIndices(int[] indices) {
		this.indices = indices;
	}
	public float[] getTexturecoords() {
		return texturecoords;
	}
	public void setTexturecoords(float[] texturecoords) {
		this.texturecoords = texturecoords;
	}
	public boolean isStreched() {
		return streched;
	}
	public void setStreched(boolean streched) {
		this.streched = streched;
	}
	public String getTexture() {
		return texture;
	}
	public void setTexture(String texture) {
		this.texture = texture;
	}
	public Vec3 getOrigin() {
		return origin;
	}
	public void setOrigin(Vec3 origin) {
		this.origin = origin;
	}
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public int getQ() {
		return q;
	}
	public void setQ(int q) {
		this.q = q;
	}
	public int getP() {
		return p;
	}
	public void setP(int p) {
		this.p = p;
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
	public int getTextureID() {
		return textureID;
	}
	public void setTextureID(int textureID) {
		this.textureID = textureID;
	} 
	public int getModelMatrixLocation() {
		return modelMatrixLocation;
	}
	public void setModelMatrixLocation(int modelMatrixLocation) {
		this.modelMatrixLocation = modelMatrixLocation;
	}
	public Matrix4 getModelMatrix() {
		return modelMatrix;
	}
	public void setModelMatrix(Matrix4 modelMatrix) {
		this.modelMatrix = modelMatrix;
	}
	public Matrix4[] getMatrices() {
		return matrices;
	}
	public void setMatrices(Matrix4[] matrices) {
		this.matrices = matrices;
	}
	public int getxTiles() {
		return xTiles;
	}
	public void setxTiles(int xTiles) {
		this.xTiles = xTiles;
	}
	public int getyTiles() {
		return yTiles;
	}
	public void setyTiles(int yTiles) {
		this.yTiles = yTiles;
	}
	public int getzTiles() {
		return zTiles;
	}
	public void setzTiles(int zTiles) {
		this.zTiles = zTiles;
	}
	public boolean isAlloc() {
		return alloc;
	}
	public void setAlloc(boolean alloc) {
		this.alloc = alloc;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean update) {
		this.update = update;
	}
}
