import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * A Torus and Torus-Knot representation. Creates geometrical data for triangle slice representation in opqnGL
 * changing P or Q above 1 will trigger the Torus-knot code.
 * @author Andreas Berger
 *
 */
public class Torus extends Primitive {
	
	private int r;
	private int P;
	private int Q;

	public Torus(int x, int y, int scale, int r, int P, int Q, Game game) {
		super(x, y, scale, game);
		// TODO Auto-generated constructor stub
		this.r = r;
		this.P = P;
		this.Q = Q;
		this.type = "torus";
		this.setTexturecoords(new float[1]);
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getP() {
		return P;
	}

	public void setP(int p) {
		P = p;
	}

	public int getQ() {
		return Q;
	}

	public void setQ(int q) {
		Q = q;
	}

	@Override
	/**
	 * first create vertices and the respective normals and then set indices for openGL processing
	 */
	void create() {
		//create vertices + normals
		this.createVertices();
		
		// now the indices
		this.createIndices();
	}
	
	
	/**
	 * creates the vertices for either a trivial Torus or a Torus Knot depending on P and Q
	 */
	private void createVertices(){
// ================================== 1. Define vertices ==================================
    	
    	// Vertices, the order is now important.
		//coords des normalenrechtecks
				//-0.5f, -0.5f, 0f,	// left bottom		
				//0.5f, -0.5f, 0f,	// right bottom		
				//-0.5f,  0.5f, 0f,	// left top
				//0.5f, -0.5f, 0f,	// right top
		
		// because it is easier to create normals this way I shall directly integrate them into the calculation
    	List<Float> v = new ArrayList<Float>();
    	List<Float> n = new ArrayList<Float>();
		float[] vertices;
		float[] normals;
		double x = (double) this.getX();
		double y = (double) this.getY();
		double r = (double) this.getR()/100d;
		double R = (double) this.getScale()/100d;
		double P = (double) this.getP();
		double Q = (double) this.getQ();
		double stept = 360d/x; // num of rings
		double stepp = 360d/y; // num of sides
		System.out.println("step t: " + stept + ", step p: " + stepp);

		//Torus - Knot
		if (this.getP() > 1 || this.getQ() > 1){
			System.out.println("Torus-Knot");
			for (int i = 0; i < x; i ++){
				for (int j = 0; j < y; j ++){
					double t =  Math.toRadians(i*stept);
					double p =  Math.toRadians(360f - j*stepp);
					System.out.println("t: " + t);
					// splice normal information
					n.add((float) ((r*Math.cos(t)*Math.cos(p))));
					n.add((float) ((r*Math.sin(t)*Math.cos(p))));
					n.add((float) (r* Math.sin(p)));
					// add radius
					v.add((float) ((r*Math.cos(t)*Math.cos(p))+R*(2d + Math.cos(P*t))*Math.cos(Q*t)));
					v.add((float) ((r*Math.sin(t)*Math.cos(p))+R*(2d + Math.cos(P*t))*Math.sin(Q*t)));
					v.add((float) ((r* Math.sin(p))+(R*Math.sin(P*t))));
				}
			}
		// Trivial Torus
		}else{
			for (int i = 0; i < x; i ++){
				for (int j = 0; j < y; j ++){
					double t =  Math.toRadians(i*stept);
					double p =  Math.toRadians(360f - j*stepp);
					System.out.println("t: " + t + ", p: " + p);
					// splice normal information
					n.add((float) ((r*Math.cos(p))*Math.cos(t)));
					n.add((float) ((r*Math.cos(p))*Math.sin(t)));
					n.add((float) (r* Math.sin(p)));
					// add radius
					v.add((float) ((R+r*Math.cos(p))*Math.cos(t)));
					v.add((float) ((R+r*Math.cos(p))*Math.sin(t)));
					v.add((float) (r* Math.sin(p)));
				}
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
		System.out.println("[DEBUG] vertices created: " + Arrays.toString(vertices));
		this.setVertices(vertices);
		this.setNormals(normals);
	}
	
	private void createIndices() {
		// I created a mathematical pattern to wander the indices accordingly
		// start: x-1
		//travserse from right to left with x-1 steps: +x, -(x+1) 
		//to advance from left to right once: +x, +0 ,+(x-1),+0 
		int x = this.getyTiles();
		int y = this.getxTiles();
		int current = x-1;
		//int end = (x*(y-2) + 1 - x);
		List<Integer> pattern = new ArrayList<Integer>();
		// advancement right to left counter clockwise
		for (int j = 1; j < y ; j++){
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
		for (int i = 0; i < (x-1); i++){
			pattern.add((y-1)*-x); // back to origin
			pattern.add((y-1)*x-1); // up to last row
		}
		pattern.add((y-1)*-x); // 0
		pattern.add(y*x-1); // last
		pattern.add((y-1)*-x); // start
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

}
