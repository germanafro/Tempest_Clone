import java.util.ArrayList;
/**
 * half the Tube limits freedom of movement
 * @author andreas Berger
 *
 */
public class HalfTube extends Tube {

	private int alphaMin = -90;
	private int alphaMax = 90;
	public HalfTube(String name, int stepsr, String texture, Game game) {
		super(name, stepsr, texture, game);
		// TODO Auto-generated constructor stub
		this.getGeom().clear();
		HalfCylinder geom = new HalfCylinder(this.getxScale(), this.getyScale(), this.getzScale(), this.getrScale() , this.getScale(), game, texture);
		geom.setyTiles(stepsr); // number of tiles
		setStepr(180/stepsr);
		setAlphaMin(-1 * this.getStepr() * (int) (stepsr/2));
		setAlphaMax(this.getStepr() * (int) (stepsr/2));
		setStepz(1f*(((float)this.getScale())/100f * ((float)this.getzScale())/100f) / stepsz);
		this.addGeom(geom);
		this.setDirty(true);
	}
	public int getAlphaMin() {
		return alphaMin;
	}
	public void setAlphaMin(int alphaMin) {
		this.alphaMin = alphaMin;
	}
	public int getAlphaMax() {
		return alphaMax;
	}
	public void setAlphaMax(int alphaMax) {
		this.alphaMax = alphaMax;
	}

}
