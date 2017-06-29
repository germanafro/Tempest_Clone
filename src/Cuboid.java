import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * A cuboid. creates geometric data for triangle slice representation in opqnGL.
 * @author Andreas Berger
 *
 */
public class Cuboid extends Primitive {
	
	private int z;
	
	public Cuboid(int x, int y, int z,  int scale) {
		super(x, y, scale);
		// TODO Auto-generated constructor stub
		this.type = "cuboid";
		this.z = z;
		this.setTexturecoords(new float[1]);
	}

	@Override
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
	
	/**
	 * produce array of indices for our vertices
	 */
	private void createIndices() {
		//1,3,0,2,2,6,6,11,5,10,4,9,3,8,2,7,7,8,8,13,7,12
		int[] indices = {1,3,0,2,2,6,6,11,5,10,4,9,3,8,2,7,7,8,8,13,7,12/*3, 7, 2, 6, 1, 5, 0, 4, 4, 6, 6, 7, 5, 4, 4, 7, 7, 3, 4, 0, 0, 3, 3, 2, 0, 1*/};
		this.setIndices(indices);
	}
	/**
	 *  fill in the normals for each vertex
	 */
	private void createNormals() {
		int size = this.getVertices().length;
		float[] vertices = this.getVertices();
		float[] normals = new float[size];
		for (int i = 0 ; i < size-2; i+= 3){
			normals[i] = vertices[i];
			normals[i+1] = vertices[i+1];
			normals[i+2] = vertices[i+2];
		}
		this.setNormals(normals);
		
	}
	/**
	 * create vertices in a regular pattern x,y,z scales the cuboid relative x y z expansion, scale scales overall size %
	 */
	private void createVertices(){
// ================================== 1. Define vertices ==================================
		float x = new Float(this.getX());
		float y = new Float(this.getY());
		float z = new Float(this.getZ());
		float scale = new Float (this.getScale())/100f;
		float left = -0.5f * x *  scale;
		float right = 0.5f * x * scale;
		float bottom = -0.5f * y * scale;
		float top = 0.5f * y * scale;
		float front = 0.5f * z * scale;
		float back = -0.5f * z * scale;
		
		
		float[] vertices = {
			left, bottom, back,
			right, bottom, back,
			left, bottom, front,
			right, bottom, front,
			right, bottom, back,
			left, bottom, back,
			left, bottom, front, // back to origin
			left, top, front,
			right, top, front,
			right, top, back,
			left, top, back,
			left, top, front, // back to origin
			left, top, back,
			right, top, back,
		};
		this.setVertices(vertices);
	}
	
	private void createTextureMap(boolean streched) {
		float[] texturecoords ={
				0f, 1f,
				1f, 1f,
				0f, 0f,
				1f, 0f,
				0f, 1f,
				1f, 1f,
				0f, 0f,
				1f, 0f,
				0f, 1f,
				1f, 1f,
				0f, 0f,
				1f, 0f,
				0f, 1f,
				1f, 1f,
				0f, 0f,
				1f, 0f,
				0f, 1f,
				1f, 1f,
				0f, 0f,
				1f, 0f,
				0f, 1f,
				1f, 1f,
				0f, 0f,
				1f, 0f,
				0f, 1f,
				1f, 1f,
				0f, 0f,
				1f, 0f,
				0f, 1f,
				1f, 1f,
				0f, 0f,
				1f, 0f
		}; /*new float[this.getVertices().length*2]; 
		int x = 3 ;
		int y = 4;
		int k = 0;
		if(streched){
			for(int i = 0; i < y; i++){
				for(int j = 0; j < x; j++){
					System.out.println("i,j: " + i + ", " + j);
					texturecoords[k] = (float)j/(float)(x-1);
					texturecoords[k+1] = (float)(y-i-1)/(float)(y-1);
					k += 2;
				}
			}
		}else{
			for(int i = 0; i < y; i++){
				for(int j = 0; j < x; j++){
					System.out.println("i,j: " + i + ", " + j);
					texturecoords[k] = (float)j;
					texturecoords[k+1] = (float)(y-i-1);
					k += 2;
				}
			}
		}*/
		this.setTexturecoords(texturecoords);
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
}
