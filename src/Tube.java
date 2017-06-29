
public class Tube extends GameObject {

	public Tube(String name, Primitive geom, Game game) {
		super(name, geom, game);
		// TODO Auto-generated constructor stub
	}
	public Tube(String name, Game game) {
		super(name, new Cylinder(2, 10, 20, 400), game);
		// TODO Auto-generated constructor stub
	}

}
