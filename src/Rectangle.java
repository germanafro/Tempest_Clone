import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mat.Vec3;
/**
 * A rectangle. creates geometrical data for triangle slice representation in opqnGL.
 * @author Andreas Berger
 *
 */
public class Rectangle extends Primitive {

	public Rectangle(int x, int y, int scale, Game game) {
		super(x, y, scale, game);
		// TODO Auto-generated constructor stub
		this.type = "rectangle";
		this.setxTiles(2);
		this.setyTiles(2);
	}
	public Rectangle(int x, int y, int scale, Game game, String texture) {
		super(x, y, scale, game, texture);
		// TODO Auto-generated constructor stub
		this.type = "rectangle";
		this.setxTiles(2);
		this.setyTiles(2);
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
		
		// do texturemapping
		this.createTextureMap(this.isStreched());
	}
	
	private void createTextureMap(boolean streched) {
		float[] texturecoords = new float[this.getVertices().length]; 
		int x = this.getxTiles();
		int y = this.getyTiles();
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

	/**
	 * produce array of indices for our vertices
	 */
	private void createIndices() {
		// I created a mathematical pattern to wander the indices accordingly
		// start: x-1
		//travserse from right to left with x-1 steps: +x, -(x+1) 
		//to advance from left to right once: +x, +0 ,+(x-1),+0 
		int current = this.getxTiles()-1;
		int end = (this.getxTiles()*this.getyTiles()) -(this.getxTiles());
		
		List<Integer> pattern = new ArrayList<Integer>();
		// advancement right to left counter clockwise
		for (int i = 0; i < (this.getxTiles()-1); i++){
			pattern.add(this.getxTiles());
			pattern.add(-1*	(this.getxTiles()+1));}
		pattern.add(this.getxTiles());
		//jump from left to the right
		pattern.add(0);
		pattern.add(this.getxTiles()-1);
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
		int xval = this.getxTiles();
		int yval = this.getyTiles();
		int scaleval = this.getScale();
		float x = xval;
		float y = yval;
		float xscale = new Float(this.getX())/100f;
		float yscale = new Float(this.getY())/100f;
		float scale = new Float(scaleval) / 100f;
		float left = -0.5f * scale * xscale;
		float right = 0.5f * scale * xscale;
		float bottom = -0.5f * scale * yscale;
		float top = 0.5f * scale * yscale;
		float stepX = (right-left)/(x-1f);
		float stepY = (top - bottom)/(y-1f);
		Vec3 origin = this.getOrigin();
		
		for (int i = 0; i < yval; i ++){
			for (int j = 0; j < xval; j ++){
				v.add((float)origin.x + left + (float)j *stepX);
				v.add((float)origin.y + bottom + (float)i *stepY);
				v.add((float)origin.z + 0f);
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
