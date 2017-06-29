import java.util.ArrayList;

import mat.Matrix4;
import mat.RotationMatrix;
import mat.TranslationMatrix;
import mat.Vec3;

public class Player extends GameObject {
	

	float offset= 0f;
	float xoffset = 0f;
	float yoffset = 0f;
	float zoffset = 0f;
	public Player(String name, Primitive geom, Game game) {
		super(name, game);
		this.setGeom(new ArrayList<Primitive>());
		this.addGeom(geom);
	}
	public Player(String name, Game game) {
		super(name, game);
		this.update();
		this.setxScale(10);
		this.setyScale(5);
		this.setzScale(20);
	}
	
	private void update(){
		this.setGeom(new ArrayList<Primitive>());
		this.offset = 0.5f* new Float(this.getScale())/100f;
		System.out.println("offset: " + this.offset);
		this.xoffset = offset * new Float(this.getxScale())/100f;
		this.yoffset = offset * new Float(this.getyScale())/100f;
		this.zoffset = offset * new Float(this.getzScale())/100f;
		this.x = 0f;
		this.z = 2.8f;
		this.y = -0.9f;
		Matrix4[] matrices = {
				new TranslationMatrix(new Vec3(0,0,0)), 
				new RotationMatrix(0, mat.Axis.X),
				new TranslationMatrix(new Vec3(x,y,z))
				};
		
		matrices[0] = new TranslationMatrix(new Vec3(0,0,zoffset));
		matrices[1] = new RotationMatrix(0, mat.Axis.X);
		Rectangle front = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "player_jet.png");
		front.setMatrices(matrices);
		front.matricesTomodelMatrix();
		matrices[0] = new TranslationMatrix(new Vec3(0,0,zoffset));
		matrices[1] = new RotationMatrix(180, mat.Axis.X);
		Rectangle back = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "player_jet.png");
		back.setMatrices(matrices);
		back.matricesTomodelMatrix();
		matrices[0] = new TranslationMatrix(new Vec3(0,0,xoffset));
		matrices[1] = new RotationMatrix(90, mat.Axis.Y);
		Rectangle left = new Rectangle(this.getzScale(),this.getyScale(),this.getScale(), this.getGame(), "player_jet.png");
		left.setMatrices(matrices);
		left.matricesTomodelMatrix();
		matrices[0] = new TranslationMatrix(new Vec3(0,0,xoffset));
		matrices[1] = new RotationMatrix(-90, mat.Axis.Y);
		Rectangle right = new Rectangle(this.getzScale(),this.getyScale(),this.getScale(), this.getGame(), "player_jet.png");
		right.setMatrices(matrices);
		right.matricesTomodelMatrix();
		matrices[0] = new TranslationMatrix(new Vec3(0,0,yoffset));
		matrices[1] = new RotationMatrix(90, mat.Axis.X);
		Rectangle top = new Rectangle(this.getxScale(),this.getzScale(),this.getScale(), this.getGame(), "player_jet.png");
		top.setMatrices(matrices);
		top.matricesTomodelMatrix();
		matrices[0] = new TranslationMatrix(new Vec3(0,0,yoffset));
		matrices[1] = new RotationMatrix(-90, mat.Axis.X);
		Rectangle bottom = new Rectangle(this.getxScale(),this.getzScale(),this.getScale(), this.getGame(), "player_jet.png");
		bottom.setMatrices(matrices);
		bottom.matricesTomodelMatrix();
		this.addGeom(front);
		this.addGeom(back);
		this.addGeom(left);
		this.addGeom(right);
		this.addGeom(top);
		this.addGeom(bottom);
	}
	
	@Override
	public void setxScale(int x){
		this.xScale = x;
		this.getGeom().get(0).setX(x);
		this.getGeom().get(1).setX(x);
		this.getGeom().get(4).setX(x);
		this.getGeom().get(5).setX(x);
		this.xoffset = offset * new Float(this.getxScale())/100f;
		System.out.println("xoffset: " + this.xoffset);
		//this.update();
		
		Matrix4[] matrices = {
				new TranslationMatrix(new Vec3(0,0,0)), 
				new RotationMatrix(0, mat.Axis.X),
				new TranslationMatrix(new Vec3(this.x,this.y,this.z))
				};
		matrices[0] = new TranslationMatrix(new Vec3(0,0,this.xoffset));
		matrices[1] = new RotationMatrix(90, mat.Axis.Y);
		this.getGeom().get(2).setMatrices(matrices);
		this.getGeom().get(2).matricesTomodelMatrix();
		matrices[0] = new TranslationMatrix(new Vec3(0,0,this.xoffset));
		matrices[1] = new RotationMatrix(-90, mat.Axis.Y);
		this.getGeom().get(3).setMatrices(matrices);
		this.getGeom().get(3).matricesTomodelMatrix();
		
	}
	@Override
	public void setyScale(int y){
		this.yScale = y;
		this.getGeom().get(0).setY(y);
		this.getGeom().get(1).setY(y);
		this.getGeom().get(2).setY(y);
		this.getGeom().get(3).setY(y);
		this.yoffset = offset * new Float(this.getyScale())/100f;
		System.out.println("yoffset: " + this.yoffset);
		//this.update();
		
		Matrix4[] matrices = {
				new TranslationMatrix(new Vec3(0,0,0)), 
				new RotationMatrix(0, mat.Axis.X),
				new TranslationMatrix(new Vec3(this.x,this.y,this.z))
				}; 
		matrices[0] = new TranslationMatrix(new Vec3(0,0,this.yoffset));
		matrices[1] = new RotationMatrix(90, mat.Axis.X);
		this.getGeom().get(4).setMatrices(matrices);
		this.getGeom().get(4).matricesTomodelMatrix();
		matrices[0] = new TranslationMatrix(new Vec3(0,0,this.yoffset));
		matrices[1] = new RotationMatrix(-90, mat.Axis.X);
		this.getGeom().get(5).setMatrices(matrices);
		this.getGeom().get(5).matricesTomodelMatrix();
		
	}
	@Override
	public void setzScale(int z){
		this.zScale = z;
		this.getGeom().get(2).setX(z);
		this.getGeom().get(3).setX(z);
		this.getGeom().get(4).setY(z);
		this.getGeom().get(5).setY(z);
		this.zoffset = offset * new Float(this.getzScale())/100f;
		System.out.println("zoffset: " + this.zoffset);
		//this.update();
		
		Matrix4[] matrices = {
				new TranslationMatrix(new Vec3(0,0,0)), 
				new RotationMatrix(0, mat.Axis.X),
				new TranslationMatrix(new Vec3(this.x,this.y,this.z))
				};
		matrices[0] = new TranslationMatrix(new Vec3(0,0,this.zoffset));
		matrices[1] = new RotationMatrix(0, mat.Axis.X);
		this.getGeom().get(0).setMatrices(matrices);
		this.getGeom().get(0).matricesTomodelMatrix();
		matrices[0] = new TranslationMatrix(new Vec3(0,0,this.zoffset));
		matrices[1] = new RotationMatrix(180, mat.Axis.X);
		this.getGeom().get(1).setMatrices(matrices);
		this.getGeom().get(1).matricesTomodelMatrix();
		
	}
	@Override
	public void setScale(int scale){
		this.scale = scale;
		for (Primitive obj : this.getGeom()){
			obj.setScale(scale);
		}
		this.offset = 0.5f* new Float(this.getScale())/100f;
		
		//this.update();
		
	}

}
