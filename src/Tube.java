import java.util.ArrayList;

public class Tube extends GameObject {
	protected int stepsz = 100;
	private float stepz = 0;
	private int stepr = 0;
	private float radius = 0f;
	public Tube(String name,int stepsr, String texture, Game game) {
		super(name, game);
		this.stepsr = stepsr;
		this.setRadius(-1.1f);
		this.setxScale(100);
		this.setyScale(100);
		this.setzScale(100);
		this.setrScale(25);
		this.setScale(525);
		this.getGeom().clear();
		Cylinder geom = new Cylinder(this.getxScale(), this.getyScale(), this.getzScale(), this.getrScale() , this.getScale(), game, texture);
		geom.setyTiles(stepsr); // number of tiles
		setStepr(360/stepsr);
		setStepz(1f*(((float)this.getScale())/100f * ((float)this.getzScale())/100f) / stepsz);
		this.addGeom(geom);
		this.setDirty(true);
		// TODO Auto-generated constructor stub
	}
	/*@Override
	public void update(){
		// TODO Auto-generated method stub
		
	}*/
	@Override
	public int getStepr() {
		return stepr;
	}
	public void setStepr(int stepr) {
		this.stepr = stepr;
	}
	@Override
	public float getStepz() {
		return stepz;
	}
	public void setStepz(float stepz) {
		this.stepz = stepz;
	}
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}

}
