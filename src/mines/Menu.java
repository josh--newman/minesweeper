package mines;

import javax.swing.*;

public class Menu extends JMenuBar{
	
	public static JMenuBar create() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem saveGame = new JMenuItem("Save Game");
		JMenuItem loadGame = new JMenuItem("Load Game");
		JMenuItem highScores = new JMenuItem("High Scores");
		JMenuItem solveGame = new JMenuItem("Solve");
		menu.add(newGame);
		menu.add(saveGame);
		menu.add(loadGame);
		menu.addSeparator();
		menu.add(highScores);
		menu.addSeparator();
		menu.add(solveGame);
		menuBar.add(menu);
		return menuBar;
	}
}