import java.util.ArrayList;

import mat.Matrix4;
import mat.RotationMatrix;
import mat.TranslationMatrix;
import mat.Vec3;

public class Background extends GameObject {
	Rectangle front;
	public Background(String name, Game game) {
		super(name, game);
		this.x = 0f;
		this.z = 0f;
		this.y = 0f;
		this.xScale = 1000;
		this.yScale = 1000;
		this.scale = 200;
		this.zpos = -41;
		this.setZtarget(-41);
		this.setRalpha(0);
		this.setAlphatarget(0);  
		front = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "background.png");
		this.getGeom().clear();
		this.addGeom(front);
		this.setDirty(true);
	}
	@Override
	public void move(){
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
		matrices[1] = new TranslationMatrix(new Vec3(0,0,-zoffset));
		front.setMatrices(matrices);
		front.matricesTomodelMatrix();
		this.buffer();
	}
}
