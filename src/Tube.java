import java.util.ArrayList;

public class Tube extends GameObject {

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
		this.setScale(400);
		this.setGeom(new ArrayList<Primitive>());
		Cylinder geom = new Cylinder(this.getxScale(), this.getyScale(), this.getzScale(), this.getrScale() , this.getScale(), game, "tube_neon.png");
		this.addGeom(geom);
		// TODO Auto-generated constructor stub
	}
	@Override
	void update() {
		// TODO Auto-generated method stub
		
	}

}
