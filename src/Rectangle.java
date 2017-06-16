import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * A rectangle. creates geometrical data for triangle slice representation in opqnGL.
 * @author Andreas Berger
 *
 */
public class Rectangle extends Primitive {

	public Rectangle(int x, int y, int scale) {
		super(x, y, scale);
		// TODO Auto-generated constructor stub
		this.type = "rectangle";
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
	 * produce array of indices for our vertices
	 */
	private void createIndices() {
		// I created a mathematical pattern to wander the indices accordingly
		// start: x-1
		//travserse from right to left with x-1 steps: +x, -(x+1) 
		//to advance from left to right once: +x, +0 ,+(x-1),+0 
		int current = this.getX()-1;
		int end = (this.getX()*this.getY()) -(this.getX());
		
		List<Integer> pattern = new ArrayList<Integer>();
		// advancement right to left counter clockwise
		for (int i = 0; i < (this.getX()-1); i++){
			pattern.add(this.getX());
			pattern.add(-1*	(this.getX()+1));}
		pattern.add(this.getX());
		//jump from left to the right
		pattern.add(0);
		pattern.add(this.getX()-1);
		pattern.add(0);
		List<Integer> traversal = new ArrayList<Integer>();
		traversal.add(current);
		
		int i = 0;
		while(current != end){
			current += pattern.get(i);
			traversal.add(current);
			i = (i+1)%pattern.size(); // modulo so the pattern keeps repeating
		}
		int[] indices = new int[traversal.size()];
		i = 0;
		for(int indice : traversal){
			indices[i++] = indice;
		}
		this.setIndices(indices);
	}
	/**
	 *  fill in the normals for each vertice
	 */
	private void createNormals() {
		int size = this.getVertices().length;
		float[] normals = new float[size];
		for (int i = 0 ; i < size-2; i+= 3){
			normals[i] = 0;
			normals[i+1] = 0;
			normals[i+2] = 1;
		}
		this.setNormals(normals);
		
	}
	/**
	 * create x*y vertices in a regular pattern
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
		float x = xval;
		float y = yval;
		float scale = scaleval;
		float left = -0.5f * new Float(scale/100);
		float right = 0.5f * new Float(scale/100);
		float bottom = -0.5f * new Float(scale/100);
		float top = 0.5f * new Float(scale/100);
		float stepX = (right-left)/(x-1f);
		float stepY = (top - bottom)/(y-1f);
		
		for (float i = 0; i < y; i ++){
			for (float j = 0; j < x; j ++){
				v.add(left + j *stepX);
				v.add(bottom + i *stepY);
				v.add(0f);
			}
		}
		vertices = new float[v.size()];
		int i = 0;
		for(float coord : v){
			vertices[i++] = coord;
		}
		this.setVertices(vertices);
	}

}
