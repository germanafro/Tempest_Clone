import java.util.ArrayList;

import mat.Matrix4;
import mat.RotationMatrix;
import mat.TranslationMatrix;
import mat.Vec3;

public class Player extends GameObject {
	

	Rectangle front;
	Rectangle back;
	Rectangle left;
	Rectangle right;
	Rectangle top;
	Rectangle bottom;
	
	public Player(String name, Game game) {
		super(name, game);
		this.setDeathSound("Grenade-SoundBible.com-1777900486.mp3");
		this.x = 0f;
		this.z = 0f;
		this.y = -1.1f;
		this.xScale = 40;
		this.yScale = 20;
		this.zScale = 80;
		this.zpos = 41;
		this.setZtarget(41);
		this.setRalpha(0);
		this.setAlphatarget(0); 
		front = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "shship.png");
		back = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "rship.png");
		left = new Rectangle(this.getzScale(),this.getyScale(),this.getScale(), this.getGame(), "rship.png");
		right = new Rectangle(this.getzScale(),this.getyScale(),this.getScale(), this.getGame(), "rship.png");
		top = new Rectangle(this.getxScale(),this.getzScale(),this.getScale(), this.getGame(), "rship.png");
		bottom = new Rectangle(this.getxScale(),this.getzScale(),this.getScale(), this.getGame(), "default.png");
		this.getGeom().clear();
		this.addGeom(front);
		this.addGeom(back);
		this.addGeom(left);
		this.addGeom(right);
		this.addGeom(top);
		this.addGeom(bottom);
		this.setDirty(true);
	}
	@Override
	public void move(){
		if(this.getAlphatarget() > this.getRalpha()){
			this.setRalpha(this.getRalpha() + 1);
		} else if(this.getAlphatarget() < this.getRalpha()){
			this.setRalpha(this.getRalpha() - 1);
		}

		this.setDirty(true);
	}
	@Override
	public void update(){
		//shared
		z = zpos * game.getLevel().getTube().getStepz();
		Matrix4[] matrices = this.getMatrices();
		matrices[0] = new RotationMatrix(0, mat.Axis.X); // individual part2: then is rotated to its proper orientation 
		matrices[1] = new TranslationMatrix(new Vec3(0,0,0)); // individual part1: each rectangle uses different z value depending on orientation
		matrices[2] = new TranslationMatrix(new Vec3(x,y,z)); // shared: offset to properly sit on tube
		matrices[3] = new RotationMatrix(getRalpha(), mat.Axis.Z); //shared: this will be the players movement option across  the tube
		this.offset = 0.5f* new Float(this.getScale())/100f;
		this.xoffset = offset * new Float(this.getxScale())/100f;
		this.yoffset = offset * new Float(this.getyScale())/100f;
		this.zoffset = offset * new Float(this.getzScale())/100f;
		//individual
		matrices[0] = new RotationMatrix(0, mat.Axis.X);
		matrices[1] = new TranslationMatrix(new Vec3(0,0,zoffset));
		front.setMatrices(matrices);
		front.matricesTomodelMatrix();
		
		matrices[1] = new TranslationMatrix(new Vec3(0,0,-zoffset));
		matrices[0] = new RotationMatrix(180, mat.Axis.X);
		back.setMatrices(matrices);
		back.matricesTomodelMatrix();
		
		matrices[1] = new TranslationMatrix(new Vec3(-xoffset,0,0));
		matrices[0] = new RotationMatrix(90, mat.Axis.Y);
		left.setMatrices(matrices);
		left.matricesTomodelMatrix();
		
		matrices[1] = new TranslationMatrix(new Vec3(xoffset,0,0));
		matrices[0] = new RotationMatrix(-90, mat.Axis.Y);
		right.setMatrices(matrices);
		right.matricesTomodelMatrix();
		
		matrices[1] = new TranslationMatrix(new Vec3(0,yoffset,0));
		matrices[0] = new RotationMatrix(-90, mat.Axis.X);
		top.setMatrices(matrices);
		top.matricesTomodelMatrix();
		
		matrices[1] = new TranslationMatrix(new Vec3(0,-yoffset,0));
		matrices[0] = new RotationMatrix(90, mat.Axis.X);
		bottom.setMatrices(matrices);
		bottom.matricesTomodelMatrix();
		this.buffer();
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
}
