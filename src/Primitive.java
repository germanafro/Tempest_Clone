/**
 * base class for geometrical objects
 * @author Andreas Berger
 *
 */
public abstract class Primitive {
	private int x;
	private int y;
	private int scale;
	private float[] vertices;
	private float[] texturecoords;
	private float[] normals;
	private int[] indices;
	private boolean streched;
	protected String type; 
	
	
	public String getType() {
		return type;
	}
	/**
	 * stores basic geometrical data
	 * @param x
	 * @param y
	 * @param scale
	 */
	public Primitive(int x , int y, int scale){
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.type = "primitive";
	}
	/**
	 * non trivial every child needs to specify it's own create method
	 */
	abstract void create();

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

}