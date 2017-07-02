import java.util.ArrayList;

import mat.Matrix4;
import mat.TranslationMatrix;
import mat.Vec3;

public class DisplayBoard extends GameObject{
	Rectangle front;
	
	public DisplayBoard(String name, Game game){
		super(name, game);
		//this.setName(name);
		this.setGeom(new ArrayList<Primitive>());
		if(name.equals("level1")){front = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "1.png");}
		if(name.equals("level2")){front = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "2.png");}
		if(name.equals("level3")){front = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "3.png");}
		if(game.getLevel().getKills() == 0 && name.contains("score")){front = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "0.png");}
		if(game.getLevel().getKills() == 1 && name.contains("score")){front = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "1.png");}
		if(game.getLevel().getKills() == 2 && name.contains("score")){front = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "2.png");}
		if(game.getLevel().getKills() == 3 && name.contains("score")){front = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "3.png");}
		if(game.getLevel().getKills() == 4 && name.contains("score")){front = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "4.png");}
		//if(game.getLevel().getKills() == 5){front = new Rectangle(this.getxScale(),this.getyScale(),this.getScale(), this.getGame(), "5.png");}
		this.addGeom(front);
		this.setDirty(true);
	}
	public void update(){
		Matrix4[] matrices = this.getMatrices();
		if(this.getName().contains("level")){
			matrices[0] = new TranslationMatrix(new Vec3(-2.5,2.5,1));
		}else if(this.getName().contains("score")){
			matrices[0] = new TranslationMatrix(new Vec3(2.5,2.5,1));
		}
		front.setMatrices(matrices);
		front.matricesTomodelMatrix();
		this.buffer();
		
	}
}
