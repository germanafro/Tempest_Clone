import java.util.ArrayList;

public class HalfTube extends Tube {

	public HalfTube(String name, int stepsr, Game game) {
		super(name, stepsr, game);
		// TODO Auto-generated constructor stub
		this.getGeom().clear();
		HalfCylinder geom = new HalfCylinder(this.getxScale(), this.getyScale(), this.getzScale(), this.getrScale() , this.getScale(), game, "tube_neon.png");
		geom.setyTiles(stepsr); // number of tiles
		setStepr(180/stepsr);
		setStepz(1f*(((float)this.getScale())/100f * ((float)this.getzScale())/100f) / stepsz);
		this.addGeom(geom);
		this.setDirty(true);
	}

}
