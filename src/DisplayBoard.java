import java.util.ArrayList;
import java.util.List;

import mat.Matrix4;
import mat.TranslationMatrix;
import mat.Vec3;

/**
 * display the status bar
 * @author Sebastian Witt
 *
 */
public class DisplayBoard extends GameObject{
	private Rectangle level;
	private Rectangle score;
	private Rectangle score10;

	private List<Rectangle> lives = new ArrayList<Rectangle>();
	private int maxScore = 99;
	private int minScore = 0;
	private int maxlives = 0;
	public DisplayBoard(String name, Game game){
		super(name, game);
		this.getGeom().clear();
		level = new Rectangle(this.getxScale(),this.getyScale(),this.getScale()/2, this.getGame(), "1.png");
		score = new Rectangle(this.getxScale(),this.getyScale(),this.getScale()/2, this.getGame(), "0.png");
		score10 = new Rectangle(this.getxScale(),this.getyScale(),this.getScale()/2, this.getGame(), "0.png");
		for(int i = 0 ; i < game.getLives(); i++){
			Rectangle live = new Rectangle(this.getxScale(),this.getyScale(),this.getScale()/4, this.getGame(), "rship.png");
			lives.add(live);
			this.addGeom(live);
		}
		this.maxlives = lives.size();
		this.addGeom(level);
		this.addGeom(score);
		this.addGeom(score10);
		this.setDirty(true);
	}
	public void update(){
		Matrix4[] matrices = this.getMatrices();

		matrices[0] = new TranslationMatrix(new Vec3(-2.5,2.5,1));
		level.setMatrices(matrices);
		level.matricesTomodelMatrix();

		matrices[0] = new TranslationMatrix(new Vec3(1.9,2.5,1));
		score10.setMatrices(matrices);
		score10.matricesTomodelMatrix();
		
		matrices[0] = new TranslationMatrix(new Vec3(2.5,2.5,1));
		score.setMatrices(matrices);
		score.matricesTomodelMatrix();
		
		for(int i = 0 ; i < maxlives; i++){
			matrices[0] = new TranslationMatrix(new Vec3(-2.5 + 0.3*i, -2.5, 1));
			lives.get(i).setMatrices(matrices);
			lives.get(i).matricesTomodelMatrix();
		}

		this.buffer();
		
	}
	public Rectangle getLevel() {
		return level;
	}
	public void setLevel(Rectangle level) {
		this.level = level;
	}
	public Rectangle getScore() {
		return score;
	}
	public void setScore(Rectangle score) {
		this.score = score;
	}
	public List<Rectangle> getLives() {
		return lives;
	}
	public void resetLives() {
		for (Primitive live : this.lives){
			if (this.getGeom().contains(live)) this.getGeom().remove(live);
			this.addGeom(live);
		}
	}
	/**
	 * removes one life icon after another
	 * @return true if all lives are lost false otherwise
	 */
	public boolean loselive(){
		for (int i = maxlives ; i > 0 ; i--){
			if (this.getGeom().contains(this.lives.get(i-1))){ 
				this.getGeom().remove(this.lives.get(i-1)); // entferne Leben von rechts nach links
				this.updateScore(-10); // punishment!
				if (i == 1){
					return true;
				}
				return false; // only remove one at a time :P
			}  
		}
		return false;
	}
	/**
	 * increase score value with given value
	 * @param value increment
	 */
	public void updateScore(int value){
		// limiter so we dont overflow
		int newscore = this.game.getScore() + value;
		if (value > 0) this.game.setScore(Math.min(maxScore, newscore));
		else if (value < 0 ) this.game.setScore(Math.max(minScore , newscore));
		int tens = this.game.getScore() / 10;
		int ones = this.game.getScore() % 10;
		this.getScore().setTexture(ones + ".png");
		this.getScore10().setTexture(tens + ".png");
		// TODO display global score not just level score
	}
	public Rectangle getScore10() {
		return score10;
	}
	public void setScore10(Rectangle score10) {
		this.score10 = score10;
	}
	public int getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}
}
