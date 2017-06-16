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
	}

	@Override
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
		
		int[] indices = {3, 7, 2, 6, 1, 5, 0, 4, 4, 6, 6, 7, 5, 4, 4, 7, 7, 3, 4, 0, 0, 3, 3, 2, 0, 1};
		this.setIndices(indices);
	}
	/**
	 *  fill in the normals for each vertex
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
			left, bottom, front,
			right, bottom, front,
			right, bottom, back,
			left, bottom, back,
			left, top, front,
			right, top, front,
			right, top, back,
			left, top, back,
		};
		this.setVertices(vertices);
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
}
