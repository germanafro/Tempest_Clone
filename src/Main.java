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
		Game game = new Game();
		game.newGameEngine();
		game.newRenderEngine();
		game.run();
    }
	
}
