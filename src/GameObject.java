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
import mat.TranslationMatrix;
import mat.Vec3;

public class GameObject {
	
	
    // identity
	private Game game;
	private List<Primitive> geom;
	private String name;
	private int pId = 0;
	private int xScale = 100;
	private int yScale = 100;
	private int zScale = 100;
	private int rScale = 50;
	private int scale = 100;
	private int p = 1;
	private int q = 1;
	private float x = 0f;
	private float y = 0f;
	private float z = 0f;
	private boolean dirty = true;
  	
  	public GameObject(String name, Game game){
  		this.game = game;
  		this.geom = new ArrayList<Primitive>();
  		this.setpId(this.game.getRenderEngine().getpId());
		this.setName(name);
		this.addGeom(new Rectangle(2,2,100,game));
	}
  	/**
  	 * move the Object one step along the grid
  	 * @param x steps in x direction
  	 * @param y steps in y direction
  	 * @param z steps in z direction
  	 */
  	public void move(int x, int y, int z){
  		for(Primitive obj : this.getGeom()){
  			Vec3 origin = obj.getOrigin();
  			origin.x += x * this.getGame().getXStep();
  			origin.y += y * this.getGame().getYStep();
  			origin.z += z * this.getGame().getZStep();
  			obj.setOrigin(origin);
  		}
  	}
  	public void buffer(){
  		for(Primitive obj : this.getGeom()){
  			obj.buffer();
  		}
  	}
  	
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
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
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

}
