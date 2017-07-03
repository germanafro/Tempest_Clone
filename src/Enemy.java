import java.util.ArrayList;
import java.util.Random;

import mat.Matrix4;
import mat.RotationMatrix;
import mat.TranslationMatrix;
import mat.Vec3;
/**
 * Implementation for various enemy types
 * @author Sebastian Witt
 *
 */
public class Enemy extends GameObject {
	int speed = 1; // Movement along the z Axis
	int enemyType = 1; //1 to n(3) possible Enemytypes
	Rectangle front;
	Rectangle back;
	Rectangle left;
	Rectangle right;
	Rectangle top;
	Rectangle bottom;
	private int lives = 1;
	
	public Enemy(String name, int type, Game game) {
		super(name, game);
		this.setEnemyType(type);
		this.xScale = 40;
		this.yScale = 20;
		this.zScale = 80;
		this.getGeom().clear();
		front = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "default.png");
		back = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "enemy_jet.png");
		left = new Rectangle(this.getzScale(),this.getyScale(),this.getScale(), this.getGame(), "enemy_jet.png");
		right = new Rectangle(this.getzScale(),this.getyScale(),this.getScale(), this.getGame(), "enemy_jet.png");
		top = new Rectangle(this.getxScale(),this.getzScale(),this.getScale(), this.getGame(), "enemy_jet.png");
		bottom = new Rectangle(this.getxScale(),this.getzScale(),this.getScale(), this.getGame(), "enemy_jet.png");
		this.addGeom(front);
		this.addGeom(back);
		this.addGeom(left);
		this.addGeom(right);
		this.addGeom(top);
		this.addGeom(bottom);
		this.setDirty(true);
	}
	/**
	 * constructor to set custom textures
	 * @param name name of enemy should use format enemy_name
	 * @param type type of enemy
	 * @param front texture facing positive z
	 * @param back texture facing negative z
	 * @param left textture facing negative x
	 * @param right texture facing positiv x
	 * @param top texture facing positive y
	 * @param bottom texture facing negative y
	 * @param game the game handle
	 */
	public Enemy(String name, int type, String front, String back, String left, String right, String top, String bottom, Game game) {
		super(name, game);
		this.setEnemyType(type);
		this.setDeathSound("Blast-SoundBible.com-2068539061.mp3");
		this.setProjectileSound("laser3.mp3");
		this.xScale = 40;
		this.yScale = 20;
		this.zScale = 80;
		this.getGeom().clear();
		this.front = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), front);
		this.back = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), back);
		this.left = new Rectangle(this.getzScale(),this.getyScale(),this.getScale(), this.getGame(), left);
		this.right = new Rectangle(this.getzScale(),this.getyScale(),this.getScale(), this.getGame(), right);
		this.top = new Rectangle(this.getxScale(),this.getzScale(),this.getScale(), this.getGame(), top);
		this.bottom = new Rectangle(this.getxScale(),this.getzScale(),this.getScale(), this.getGame(), bottom);
		this.addGeom(this.front);
		this.addGeom(this.back);
		this.addGeom(this.left);
		this.addGeom(this.right);
		this.addGeom(this.top);
		this.addGeom(this.bottom);
		this.setDirty(true);
	}
	
	@Override
	public void update(){
		//shared
		Matrix4[] matrices = this.getMatrices();
		z = zpos * game.getLevel().getTube().getStepz();
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
		matrices[0] = new RotationMatrix(90, mat.Axis.X);
		top.setMatrices(matrices);
		top.matricesTomodelMatrix();
		
		matrices[1] = new TranslationMatrix(new Vec3(0,-yoffset,0));
		matrices[0] = new RotationMatrix(-90, mat.Axis.X);
		bottom.setMatrices(matrices);
		bottom.matricesTomodelMatrix();
		this.buffer();
	}
	
	//Speed default should be 1
	@Override
	public void move(){
	}
	/**
	 * randomly shoot projectiles at the player
	 */
	@Override
	public void shootingLogic(int chance){
		Random rnd = new Random();
		int i = rnd.nextInt(100);
		if(i >= 100 - chance) {
			Projectile proj = new Projectile("enemyprojectile" + game.enemyFired++, this.getGame(), "enemy_projectile.png");
			proj.setX(this.getX());
			proj.setY(this.getY());
			proj.setZ(this.getZ());
			proj.setRalpha(this.getRalpha());
			proj.setAlphatarget(this.getAlphatarget());
			proj.setZpos(this.getZpos() + 5);
			proj.setZtarget(50);
			proj.setSpawnSound(this.getProjectileSound());
			game.addGameObject(proj);
		}
	}
	
	/**
	 * on each tick target one step on rotation axis with chance % chance
	 * note: the chance should be fairly low as there are up to 60 checks / sec
	 */
	@Override
	public void moveRLogic(int chance, int step){
		Random rnd = new Random();
		int i = rnd.nextInt(100);
		if(i >= 100 - chance) {
			int target = this.getAlphatarget();
			GameObject tube = this.game.getLevel().getTube();
			if(i % 2 == 0){
				target += step;
				if(tube.getClass() == HalfTube.class && target > ((HalfTube)tube).getAlphaMax()){
					this.setAlphatarget(this.getAlphatarget() - step);
				} else this.setAlphatarget(this.getAlphatarget() + step);
				
			}else{
				target -= step;
				if(tube.getClass() == HalfTube.class && target < ((HalfTube)tube).getAlphaMin()){
					this.setAlphatarget(this.getAlphatarget() + step);
				} else this.setAlphatarget(this.getAlphatarget() - step);
			}
		}
		if(this.getAlphatarget() > this.getRalpha()){
			this.setRalpha(this.getRalpha() + speed); //TODO change ralpha to double - nein! ralpha ist eine relative Einheit zum spielfeld grid working as intended
		} else if(this.getAlphatarget() < this.getRalpha()){
			this.setRalpha(this.getRalpha() - speed); //TODO same here
		}
		this.setDirty(true);
	}
	
	/**
	 * on each tick move one step on z axis towards player with chance % chance
	 * note: the chance should be fairly low as there are up to 60 checks / sec
	 */
	@Override
	public void moveZLogic(int chance, float step){
		Random rnd = new Random();
		int i = rnd.nextInt(100);
		if(i >= 100 - chance) {
			if(this.getZtarget() > this.getZpos()){
				this.setZpos(this.getZpos() + speed);
			}else if(this.getZtarget() < this.getZpos()){
				this.setZpos(this.getZpos() - speed);
			}
			this.setDirty(true);
		}
	}
	
	
	
	/**
	 * control enemy behavior
	 */
	@Override
	public void enemyLogic(int rstep, float zstep){
		int type = this.getEnemyType();
		switch(type){
		case 3: // chaser
			this.setAlphatarget(game.getLevel().getPlayer().getRalpha()); // chase players current position not his target position - that would be mean =D
			if(this.getAlphatarget() > this.getRalpha()){
				this.setRalpha(this.getRalpha() + speed);
			} else if(this.getAlphatarget() < this.getRalpha()){
				this.setRalpha(this.getRalpha() - speed); 
			}
			this.setDirty(true);
			this.moveZLogic(75, zstep); // move slower for balance
			break;
		case 1: // shooter
			this.shootingLogic(1); 
			this.moveZLogic(5, zstep); // standard shooters prefer to stay back and shoot but will move on eventually
			this.moveRLogic(5, rstep);
			break;
		case 0: // brute
			this.moveRLogic(1, rstep);
			this.moveZLogic(100, zstep);
			break;
		case 100:
			this.moveRLogic(1, rstep);
			this.bossTimer();
		default: //rambo
			this.moveZLogic(100, zstep);
			break;
		}
	}	
	@Override
	public int getEnemyType() {
		return enemyType;
	}
	public void setEnemyType(int enemyType) {
		this.enemyType = enemyType;
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
	public int getLives() {
		return lives;
	}
	public void setLives(int lives) {
		this.lives = lives;
	}
	public boolean isdead(){
		if (lives-- == 0) return true;
		return false;
	}
	public void bossTimer() {
		// TODO Auto-generated method stub
		
	}
}