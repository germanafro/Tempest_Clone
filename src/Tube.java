import java.util.ArrayList;

public class Tube extends GameObject {
	private int stepsz = 100;
	private int stepsr = 10;
	private float stepz = 0;
	private int stepr = 0;
	public Tube(String name, Primitive geom, Game game) {
		super(name, game);
		this.setGeom(new ArrayList<Primitive>());
		this.addGeom(geom);
		// TODO Auto-generated constructor stub
	}
	public Tube(String name, Game game) {
		super(name, game);
		this.setxScale(100);
		this.setyScale(100);
		this.setzScale(100);
		this.setrScale(25);
		this.setScale(525);
		this.setGeom(new ArrayList<Primitive>());
		Cylinder geom = new Cylinder(this.getxScale(), this.getyScale(), this.getzScale(), this.getrScale() , this.getScale(), game, "tube_neon.png");
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
	public int getStepr() {
		return stepr;
	}
	public void setStepr(int stepr) {
		this.stepr = stepr;
	}
	public float getStepz() {
		return stepz;
	}
	public void setStepz(float stepz) {
		this.stepz = stepz;
	}

}
