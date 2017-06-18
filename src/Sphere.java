import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * A sphere representation. Creates geometrical data for triangle slice representation in opqnGL
 * @author Andreas Berger
 *
 */
public class Sphere extends Primitive {

	public Sphere(int x, int y, int scale) {
		super(x, y, scale);
		// TODO Auto-generated constructor stub
		this.type = "sphere";
		this.setTexturecoords(new float[1]);
	}

	@Override
	/**
	 * first create vertices then the normals and then set indices for openGL processing
	 */
	void create() {
		//create vertices
		this.createVertices();
		
		//create normals
		this.createNormals();
		
		// now the indices
		this.createIndices();
	}
	
	
	/**
	 * use polarcoordinates to determine the coords of each vertice
	 */
	private void createVertices(){
// ================================== 1. Define vertices ==================================
    	
    	// Vertices, the order is now important.
		//coords des normalenrechtecks
				//-0.5f, -0.5f, 0f,	// left bottom		
				//0.5f, -0.5f, 0f,	// right bottom		
				//-0.5f,  0.5f, 0f,	// left top
				//0.5f, -0.5f, 0f,	// right top
    	List<Float> v = new ArrayList<Float>();
		float[] vertices;
		int xval = this.getX();
		int yval = this.getY();
		int scaleval = this.getScale();
		float x = new Float(xval);
		float y = new Float(yval);
		double scale = new Double(scaleval);
		double r = scale/100d;
		double stepPhi = 180d / (x-1d); // y rot
		double stepTheta = 360d/y; // z rot
		System.out.println("steptheta: " + stepTheta + ", stepphi: " + stepPhi);
		// south pole
		v.add(0f);v.add(0f);v.add(-1f*(float) r);
		
		// the main body
		for (float i = 1; i < x-1; i ++){
			for (float j = 0; j < y; j ++){
				double theta =  Math.toRadians(360d - j*stepTheta);
				double phi =  Math.toRadians(180d - i*stepPhi);
				System.out.println("theta: " + theta + ", phi: " + phi);
				v.add((float) (r*Math.cos(theta)*Math.sin(phi)));
				v.add((float) (r*Math.sin(theta)*Math.sin(phi)));
				v.add((float) (r* Math.cos(phi)));
			}
		}
		// north pole
		v.add(0f);v.add(0f);v.add(1f *(float) r);
		vertices = new float[v.size()];
		int i = 0;
		for(float coord : v){
			vertices[i++] = coord;
		}
		System.out.println("[DEBUG] vertices created: " + Arrays.toString(vertices));
		this.setVertices(vertices);
	}
	/**
	 * providex the indices to draw the sphere with a triangle strip 
	 */
	private void createIndices() {
		// I created a mathematical pattern to wander the indices accordingly
		// start: x-1
		//travserse from right to left with x-1 steps: +x, -(x+1) 
		//to advance from left to right once: +x, +0 ,+(x-1),+0 
		int x = this.getY();
		int y = this.getX();
		int current = 0;
		//int end = (x*(y-2) + 1 - x);
		List<Integer> pattern = new ArrayList<Integer>();
		
		// the "fan" partially formed through degenerated triangles 0,x,0
		
		for (int i = 0 ; i+1 < x ; i++ ){
			pattern.add(x-i);
			pattern.add(-(x-i));
		}
		pattern.add(1);
		pattern.add(-1);
		pattern.add(x);
		pattern.add(0);
		
		// advancement right to left counter clockwise
		for (int j = 3; j < y ; j++){
			for (int i = 0; i < (x-1); i++){
				pattern.add(x);
				pattern.add(-1*	(x+1));
				}
			pattern.add(x);
			//connect from left to the right
			pattern.add(-1);
			pattern.add(x);
			pattern.add(0);
		}
		// the second triangle strip fan
		pattern.add(1);
		for (int i = 2 ; i <= x ; i++ ){
			pattern.add(-i);
			pattern.add(i);
		}
		pattern.add(-1);
		List<Integer> traversal = new ArrayList<Integer>();
		//traversal.add(current);
		
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
	 * every vertice already points in the direction of a the respective normal
	 */
	private void createNormals() {
		float[] vertices = this.getVertices();
		int size = vertices.length;
		float[] normals = new float[size];
		for (int i = 0 ; i < size; i++){
			normals[i] = vertices[i];
		}
		this.setNormals(normals);
		
	}

}
