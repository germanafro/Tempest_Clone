import mat.Vec3;

public class GameObject {
	
	private Game master;
	private Primitive geom;
	private String name;
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
  	
  	public GameObject(String name, Primitive geom){
		this.setName(name);
		this.setGeom(geom);
	}
  	/**
  	 * move the Object one step along the grid
  	 * @param x steps in x direction
  	 * @param y steps in y direction
  	 * @param z steps in z direction
  	 */
  	public void move(int x, int y, int z){
  		Vec3 origin = this.getGeom().getOrigin();
  		origin.x += x * this.getMaster().getXStep();
  		origin.y += y * this.getMaster().getYStep();
  		origin.z += z * this.getMaster().getZStep();
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
	public Game getMaster() {
		return master;
	}
	public void setMaster(Game master) {
		this.master = master;
	} 

}
