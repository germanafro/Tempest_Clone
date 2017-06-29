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
		this.setGeom(new ArrayList<Primitive>());
		Cylinder geom = new Cylinder(100, 100, 100, 25 , 400, game, "tube_neon.png");
		this.addGeom(geom);
		// TODO Auto-generated constructor stub
	}

}
