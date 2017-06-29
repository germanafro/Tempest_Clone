
public class Player extends GameObject {

	public Player(String name, Primitive geom, Game game) {
		super(name, geom, game);
		// TODO Auto-generated constructor stub
	}
	public Player(String name, Game game) {
		super(name, new Cuboid(2, 1, 4, 10), game);
		// TODO Auto-generated constructor stub
	}

}
