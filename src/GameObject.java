import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import mat.Matrix4;
import mat.RotationMatrix;
import mat.TranslationMatrix;
import mat.Vec3;

public abstract class GameObject {
	
	
    // identity
	protected Game game;
	private List<Primitive> geom;
	private String name;
	
	
	//geom
	protected float offset= 0f;
	protected float xoffset = 0f;
	protected float yoffset = 0f;
	protected float zoffset = 0f;
	protected int xScale = 100;
	protected int yScale = 100;
	protected int zScale = 100;
	protected int rScale = 50;
	protected int scale = 100;
	protected int p = 1;
	protected int q = 1;
	protected float x = 0f;
	protected float y = 0f;
	protected float z = 0f;
	private String projectileSound = "sfx/laser4.mp3";
	private Sound spawnSound = new Sound("sfx/phaserDown2.mp3");
	
	private int alphatarget = 0;
	private int ralpha = 0;
	private int ztarget = 0;
	protected int zpos = 0;
	protected Matrix4[] matrices = {
			new RotationMatrix(0, mat.Axis.X),  // rotate object
			new TranslationMatrix(new Vec3(0,0,0)), //initial individual offset to determine position relative to other geoms in this object
			new TranslationMatrix(new Vec3(x,y,z)), // shared offset for all geoms for synchronous movement and propper placement in tube
			new RotationMatrix(ralpha, mat.Axis.Z) // now rotate along z axis to move left ~ right along tube
			};
	private boolean dirty = true;
	private boolean moving = false;
	private boolean destroy = false;

  	
  	public GameObject(String name, Game game){
  		this.game = game;
  		this.geom = new ArrayList<Primitive>();
		this.setName(name);
		this.addGeom(new Rectangle(100,100,100,game));
	}
  	
  	public void move() { //Alte Parameter, nur zur Sicherheit: int alpha, int z
		// z axis rotation
  		/*
		this.setRalpha(this.getRalpha() + alpha);
		this.setZpos(this.getZpos() + z);
		this.setDirty(true);*/
			}
	public void update(){
		//shared
		this.xoffset = offset * new Float(this.getxScale())/100f * new Float(this.getScale())/100f;
		this.yoffset = offset * new Float(this.getyScale())/100f * new Float(this.getScale())/100f;
		this.zoffset = offset * new Float(this.getzScale())/100f * new Float(this.getScale())/100f;
		Matrix4[] matrices = this.getMatrices();
		matrices[0] = new RotationMatrix(0, mat.Axis.X); // individual part2: then is rotated to its proper orientation 
		matrices[1] = new TranslationMatrix(new Vec3(0,0,0)); // individual part1: each rectangle uses different z value depending on orientation
		matrices[2] = new TranslationMatrix(new Vec3(x,y,z + zpos * game.getLevel().getTube().getStepz())); // shared: offset to properly sit on tube
		matrices[3] = new RotationMatrix(getRalpha(), mat.Axis.Z); //shared: this will be the players movement option across  the tube
		for(Primitive obj : this.getGeom()){
			obj.setMatrices(matrices);
			obj.matricesTomodelMatrix();
		}
		this.buffer();
	}
  	
  	public void buffer(){
  		for(Primitive obj : this.getGeom()){
  			obj.buffer();
  		}
  		this.setDirty(false);
  	}
  	
  	
  	public void movementLogic(int a){}
	public List<Primitive> getGeom() {
		return geom;
	}
	public void setGeom(List<Primitive> geom) {
		this.geom = geom;
	}
	public void addGeom(Primitive geom) {
		this.geom.add(geom);
	}
	public void removeGeom(Primitive geom) {
		this.geom.remove(geom);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public int getxScale() {
		return xScale;
	}
	public void setxScale(int xScale) {
		this.xScale = xScale;
		for(Primitive obj : this.getGeom()){
  			obj.setX(xScale);
  		}
	}
	public int getyScale() {
		return yScale;
	}
	public void setyScale(int yScale) {
		this.yScale = yScale;
		for(Primitive obj : this.getGeom()){
  			obj.setY(yScale);
  		}
	}
	public int getzScale() {
		return zScale;
	}
	public void setzScale(int zScale) {
		this.zScale = zScale;
		for(Primitive obj : this.getGeom()){
  			obj.setZ(zScale);
  		}
	}
	public int getrScale() {
		return rScale;
	}
	public void setrScale(int rScale) {
		this.rScale = rScale;
		for(Primitive obj : this.getGeom()){
  			obj.setR(rScale);
  		}
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
		for(Primitive obj : this.getGeom()){
  			obj.setScale(scale);
  		}
	}
	public int getP() {
		return p;
	}
	public void setP(int p) {
		this.p = p;
		for(Primitive obj : this.getGeom()){
  			obj.setP(p);
  		}
	}
	public int getQ() {
		return q;
	}
	public void setQ(int q) {
		this.q = q;
		for(Primitive obj : this.getGeom()){
  			obj.setQ(q);
  		}
	}
	//TODO add Translationmatrix to move all
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getZ() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}
	public boolean isDirty() {
		return dirty;
	}
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	public int getAlphatarget() {
		return alphatarget;
	}
	public void setAlphatarget(int alphatarget) {
		this.alphatarget = alphatarget;
		this.setMoving(true);
		this.setDirty(true);
	}
	public int getRalpha() {
		return ralpha;
	}
	public void setRalpha(int ralpha) {
		this.ralpha = ralpha;
	}
	public int getZpos() {
		return zpos;
	}
	public void setZpos(int zpos) {
		this.zpos = zpos;
	}
	public int getZtarget() {
		return ztarget;
	}
	public void setZtarget(int ztarget) {
		this.ztarget = ztarget;
		this.setMoving(true);
		this.setDirty(true);
	}
	public Matrix4[] getMatrices() {
		return matrices;
	}
	public void setMatrices(Matrix4[] matrices) {
		this.matrices = matrices;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public boolean isDestroy() {
		return destroy;
	}

	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}

	public String getProjectileSound() {
		return projectileSound;
	}

	public void setProjectileSound(String sound) {
		this.projectileSound = sound;
	}

	public Sound getSpawnSound() {
		return spawnSound;
	}

	public void setSpawnSound(String sound) {
		this.spawnSound = new Sound(sound);
	}
	

}
