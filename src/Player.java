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
	Rectangle front;
	Rectangle back;
	Rectangle left;
	Rectangle right;
	Rectangle top;
	Rectangle bottom;
	private int zalpha;
	private float x = 0f;
	private float z = 2.8f;
	private float y = -0.9f;
	
	public Player(String name, Primitive geom, Game game) {
		super(name, game);
		this.setGeom(new ArrayList<Primitive>());
		this.addGeom(geom);
		this.setDirty(true);
	}
	public Player(String name, Game game) {
		super(name, game);
		this.xScale = 10;
		this.yScale = 5;
		this.zScale = 20;
		front = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "player_jet.png");
		back = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "player_jet.png");
		left = new Rectangle(this.getzScale(),this.getyScale(),this.getScale(), this.getGame(), "player_jet.png");
		right = new Rectangle(this.getzScale(),this.getyScale(),this.getScale(), this.getGame(), "player_jet.png");
		top = new Rectangle(this.getxScale(),this.getzScale(),this.getScale(), this.getGame(), "player_jet.png");
		bottom = new Rectangle(this.getxScale(),this.getzScale(),this.getScale(), this.getGame(), "player_jet.png");
		this.setGeom(new ArrayList<Primitive>());
		this.addGeom(front);
		this.addGeom(back);
		this.addGeom(left);
		this.addGeom(right);
		this.addGeom(top);
		this.addGeom(bottom);
	}
	@Override
	public void update(){
		//shared
		Matrix4[] matrices = {
				new TranslationMatrix(new Vec3(0,0,0)), // individual part1: each rectangle uses different z value depending on orientation
				new RotationMatrix(0, mat.Axis.X), // individual part2: then is rotated to its proper orientation 
				new TranslationMatrix(new Vec3(x,y,z)), // shared: offset to properly sit on tube
				new RotationMatrix(zalpha, mat.Axis.Z) //shared: this will be the players movement option across  the tube
				};
		this.offset = 0.5f* new Float(this.getScale())/100f;
		this.xoffset = offset * new Float(this.getxScale())/100f;
		this.yoffset = offset * new Float(this.getyScale())/100f;
		this.zoffset = offset * new Float(this.getzScale())/100f;
		//individual
		matrices[0] = new TranslationMatrix(new Vec3(0,0,zoffset));
		matrices[1] = new RotationMatrix(0, mat.Axis.X);
		front.setMatrices(matrices);
		front.matricesTomodelMatrix();
		
		matrices[0] = new TranslationMatrix(new Vec3(0,0,zoffset));
		matrices[1] = new RotationMatrix(180, mat.Axis.X);
		back.setMatrices(matrices);
		back.matricesTomodelMatrix();
		
		matrices[0] = new TranslationMatrix(new Vec3(0,0,xoffset));
		matrices[1] = new RotationMatrix(90, mat.Axis.Y);
		left.setMatrices(matrices);
		left.matricesTomodelMatrix();
		
		matrices[0] = new TranslationMatrix(new Vec3(0,0,xoffset));
		matrices[1] = new RotationMatrix(-90, mat.Axis.Y);
		right.setMatrices(matrices);
		right.matricesTomodelMatrix();
		
		matrices[0] = new TranslationMatrix(new Vec3(0,0,yoffset));
		matrices[1] = new RotationMatrix(90, mat.Axis.X);
		top.setMatrices(matrices);
		top.matricesTomodelMatrix();
		
		matrices[0] = new TranslationMatrix(new Vec3(0,0,yoffset));
		matrices[1] = new RotationMatrix(-90, mat.Axis.X);
		bottom.setMatrices(matrices);
		bottom.matricesTomodelMatrix();
		this.setDirty(false);
	}
	
	@Override
	public void setxScale(int x){
		this.xScale = x;
		front.setX(x);
		back.setX(x);
		top.setX(x);
		bottom.setX(x);
		this.setDirty(true);
		
	}
	@Override
	public void setyScale(int y){
		this.yScale = y;
		front.setY(y);
		back.setY(y);
		left.setY(y);
		right.setY(y);
		this.setDirty(true);
		
	}
	@Override
	public void setzScale(int z){
		this.zScale = z;
		left.setX(z);
		right.setX(z);
		top.setY(z);
		bottom.setY(z);
		this.setDirty(true);
		
	}
	@Override
	public void setScale(int scale){
		this.scale = scale;
		for (Primitive obj : this.getGeom()){
			obj.setScale(scale);
		}
		this.setDirty(true);
	}
	
	/**
	 * move the player character along the tube
	 * @param alpha the angle by which the player should move
	 */
	@Override
	public void move(int delta){
		//player can only move left or right along the tube
		// z axis rotation should suffice
		this.zalpha += delta;
		this.setDirty(true);
	}
	
	public int getZalpha() {
		return zalpha;
	}
	public void setZalpha(int zalpha) {
		this.zalpha = zalpha;
		this.setDirty(true);
	}

}
