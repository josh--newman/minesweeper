package mines;

import javax.swing.*;

public class Menu extends JMenuBar{
	
	public static JMenuBar create() {
		JMenuBar menuBar = new JMenuBar();
		
		// Create 'file' menu and items
		JMenu fileMenu = new JMenu("File");
		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem saveGame = new JMenuItem("Save Game");
		JMenuItem loadGame = new JMenuItem("Load Game");
		JMenuItem highScores = new JMenuItem("High Scores");
		JMenuItem solveGame = new JMenuItem("Solve");
		
		// Create 'edit' menu and items
		JMenu editMenu = new JMenu("Edit");
		JMenuItem undo = new JMenuItem("Undo");
		JMenuItem redo = new JMenuItem("Redo");
		
		// Add FILE items to menu
		fileMenu.add(newGame);
		fileMenu.add(saveGame);
		fileMenu.add(loadGame);
		fileMenu.addSeparator();
		fileMenu.add(highScores);
		fileMenu.addSeparator();
		fileMenu.add(solveGame);
		// Add EDIT items to menu
		editMenu.add(undo);
		editMenu.add(redo);
		
		// Add each menu to menuBar
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		return menuBar;
	}
}