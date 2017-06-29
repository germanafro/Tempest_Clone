import java.util.ArrayList;

import mat.Matrix4;
import mat.RotationMatrix;
import mat.TranslationMatrix;
import mat.Vec3;

public class Player extends GameObject {

	public Player(String name, Primitive geom, Game game) {
		super(name, game);
		this.setGeom(new ArrayList<Primitive>());
		this.addGeom(geom);
	}
	public Player(String name, Game game) {
		super(name, game);
		this.setGeom(new ArrayList<Primitive>());
		float offset = 0.25f;
		float z = 2.5f;
		float y = -0.5f;
		Matrix4[] matrices = {
				new TranslationMatrix(new Vec3(0,0,0)), 
				new RotationMatrix(0, mat.Axis.X),
				new TranslationMatrix(new Vec3(0,y,z))
				};
		
		matrices[0] = new TranslationMatrix(new Vec3(0,0,offset));
		matrices[1] = new RotationMatrix(0, mat.Axis.X);
		Rectangle front = new Rectangle(100,100,50, game);
		front.setMatrices(matrices);
		front.matricesTomodelMatrix();
		matrices[0] = new TranslationMatrix(new Vec3(0,0,offset));
		matrices[1] = new RotationMatrix(180, mat.Axis.X);
		Rectangle back = new Rectangle(100,100,50, game);
		back.setMatrices(matrices);
		back.matricesTomodelMatrix();
		matrices[0] = new TranslationMatrix(new Vec3(0,0,offset));
		matrices[1] = new RotationMatrix(90, mat.Axis.Y);
		Rectangle left = new Rectangle(100,100,50, game);
		left.setMatrices(matrices);
		left.matricesTomodelMatrix();
		matrices[0] = new TranslationMatrix(new Vec3(0,0,offset));
		matrices[1] = new RotationMatrix(-90, mat.Axis.Y);
		Rectangle right = new Rectangle(100,100,50, game);
		right.setMatrices(matrices);
		right.matricesTomodelMatrix();
		matrices[0] = new TranslationMatrix(new Vec3(0,0,offset));
		matrices[1] = new RotationMatrix(90, mat.Axis.X);
		Rectangle top = new Rectangle(100,100,50, game);
		top.setMatrices(matrices);
		top.matricesTomodelMatrix();
		matrices[0] = new TranslationMatrix(new Vec3(0,0,offset));
		matrices[1] = new RotationMatrix(-90, mat.Axis.X);
		Rectangle bottom = new Rectangle(100,100,50, game);
		bottom.setMatrices(matrices);
		bottom.matricesTomodelMatrix();
		this.addGeom(front);
		this.addGeom(back);
		this.addGeom(left);
		this.addGeom(right);
		this.addGeom(top);
		this.addGeom(bottom);
		// TODO Auto-generated constructor stub
		
	}

}
