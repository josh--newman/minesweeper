package mines;

public class CurrentGame {
	private static Mines currentGame;
	
	public static void setCurrentGame(Mines game) {
		currentGame = game;
	}
	
	public static Mines getCurrentGame() {
		return currentGame;
	}
	
}
