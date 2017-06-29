 /**
  * simple startup procedure
  * @author Andreas Berger
  *
  */
public class Main {
	/**
	 * make the Engine and run it
	 * @param args no arguments needed
	 */
	public static void main(String[] args){
		
		if(args.length > 0){
			switch (args[0]){
			case "test":
				Engine engine = new Engine();
				engine.run();
				break;
			default:
				break;
			}
		}else{
			Game game = new Game();
			game.newGameEngine();
			game.newRenderEngine();
			game.run();
		}
    }
	
}
