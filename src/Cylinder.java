import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Cylinder representation. containing geometry data for OpenGL
 * this is a special cylinder used as tube for the game Tempest thus the insides rather than the outsides are rendered, also note the inverted normals direktion
 * @author Andreas Berger
 *
 */
public class Cylinder extends Primitive {
	
	private int r;
	public Cylinder(int x, int y, int z, int r, int scale, Game game) {
		super(x, y, scale, game);
		// TODO Auto-generated constructor stub
		this.r = r;
		this.setZ(z);
		this.type = "cylinder";
		this.setxTiles(2);
		this.setyTiles(10);
	}
	public Cylinder(int x, int y, int z, int r, int scale, Game game, String texture) {
		super(x, y, scale, game, texture);
		// TODO Auto-generated constructor stub
		this.setZ(z);
		this.r = r;
		this.type = "cylinder";
		this.setxTiles(2);
		this.setyTiles(10);
	}

	@Override
	void create() {
		//create vertices and normals
		this.createVertices();	
		// now the indices
		this.createIndices();
		// do texturemapping
		this.createTextureMap(this.isStreched());
	}
	
	/**
	 * produce array of indices for our vertices
	 */
	private void createIndices() {
		int x = this.getyTiles() + 1 ;
		int y = this.getxTiles();
		int current = x-1;
		List<Integer> pattern = new ArrayList<Integer>();
		// advancement right to left counter clockwise
		for (int j = 1; j < y ; j++){
			for (int i = 0; i < (x-1); i++){
				pattern.add(x);
				pattern.add(-1*	(x+1));
				}
			pattern.add(x);
			//connect from left to the right
			pattern.add(0);
			pattern.add(x-1);
			pattern.add(0);
			
			//connect from left to the right
			//pattern.add(-1);
			//pattern.add(x);
			//pattern.add(0);
		}
		//for (int i = 0; i < (x-1); i++){
		//	pattern.add((y-1)*-x); // back to origin
		//	pattern.add((y-1)*x-1); // up to last row
		//}
		//pattern.add((y-1)*-x); // 0
		//pattern.add(y*x-1); // last
		//pattern.add((y-1)*-x); // start
		List<Integer> traversal = new ArrayList<Integer>();
		traversal.add(current);
		
		for(int i = 0; i < pattern.size(); i++){
			current += pattern.get(i);
			traversal.add(current);
		}
		int[] indices = new int[traversal.size()];
		int i = 0;
		for(int indice : traversal){
			indices[i++] = indice;
		}
		this.setIndices(indices);
	}
	/**
	 * create vertices and normals in a regular pattern
	 * scale - scales overall size %
	 * r 	 - radius scale % (base is 0.5)
	 * x 	 - segments in z direction
	 * y 	 - number of sides along rotation
	 */
	private void createVertices(){
// ================================== 1. Define vertices ==================================
		float x = new Float(this.getxTiles());
		float y = new Float(this.getyTiles());
		float scale = new Float (this.getScale())/100f;
		float r = 1f* scale * new Float(this.getR())/100f;
		float xscale = new Float(this.getX())/100f;
		float yscale = new Float(this.getY())/100f;
		float zscale = new Float(this.getZ())/100f;
		float front = 0.5f * scale * zscale;
		float back = -0.5f * scale * zscale;
		float stepz = (front - back)/x;
		double stepr = 360d/y; // num of sides
		float[] vertices;
		float[] normals;
		List<Float> v = new ArrayList<Float>();
    	List<Float> n = new ArrayList<Float>();
		
		for (int i = 0; i < this.getxTiles(); i ++){
			for (int j = 0; j < this.getyTiles() + 1; j ++){
				double rad =  Math.toRadians(360f - j*stepr);
				// splice normal information
				n.add((float) (-r*Math.cos(rad)*xscale));
				n.add((float) (-r*Math.sin(rad)*yscale));
				n.add(0f);
				// add z
				v.add((float) (r*Math.cos(rad)*xscale));
				v.add((float) (r*Math.sin(rad)*yscale));
				v.add(front - i * stepz);
			}
		}
		vertices = new float[v.size()];
		normals = new float[n.size()];
		int i = 0;
		for(float coord : v){
			vertices[i++] = coord;
		}
		i = 0;
		for(float coord : n){
			normals[i++] = coord;
		}
		//System.out.println("[DEBUG] vertices created: " + Arrays.toString(vertices));
		this.setVertices(vertices);
		this.setNormals(normals);
	}
	
	private void createTextureMap(boolean streched) {
		float[] texturecoords = new float[this.getVertices().length]; 
		int x = this.getyTiles() + 1 ;
		int y = this.getxTiles();
		int k = 0;
		if(streched){
			for(int i = 0; i < y; i++){
				for(int j = 0; j < x; j++){
					//System.out.println("i,j: " + i + ", " + j);
					texturecoords[k] = (float)j/(float)(x-1);
					texturecoords[k+1] = (float)(y-i-1)/(float)(y-1);
					k += 2;
				}
			}
		}else{
			for(int i = 0; i < y; i++){
				for(int j = 0; j < x; j++){
					//System.out.println("i,j: " + i + ", " + j);
					texturecoords[k] = (float)j;
					texturecoords[k+1] = (float)(y-i-1);
					k += 2;
				}
			}
		}
		this.setTexturecoords(texturecoords);
	}
	
	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}
}
