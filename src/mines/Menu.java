package mines;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class Menu {
	
	public static JMenuBar create() {
		JMenuBar menuBar = new JMenuBar();
		
		// Create 'file' menu and items
		JMenu fileMenu = new JMenu("File");
		JMenuItem newGame = new JMenuItem(new NewGameAction("New Game"));
		JMenuItem saveGame = new JMenuItem(new SaveGameAction("Save Game"));
		JMenuItem loadGame = new JMenuItem(new LoadGameAction("Load Game"));
		JMenuItem highScores = new JMenuItem(new HighScoresAction("High Scores"));
		JMenuItem solveGame = new JMenuItem(new SolveGameAction("Solve"));
		
		// Create 'edit' menu and items
		JMenu editMenu = new JMenu("Edit");
		JMenuItem undo = new JMenuItem("Undo");
		JMenuItem redo = new JMenuItem("Redo");
		
		// Create 'difficulty' menu and items
		JMenu difficultyMenu = new JMenu("Difficulty");
		JMenuItem easy = new JMenuItem(new EasyDiffAction("Easy"));
		JMenuItem medium = new JMenuItem(new MediumDiffAction("Medium"));
		JMenuItem hard = new JMenuItem(new HardDiffAction("Hard"));
		
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
		// Add DIFFICULTY items to menu
		difficultyMenu.add(easy);
		difficultyMenu.add(medium);
		difficultyMenu.add(hard);
		
		// Add each menu to menuBar
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(difficultyMenu);
		
		return menuBar;
	}
	
	
}

//FILE MENU ACTIONS ***************************
class NewGameAction extends AbstractAction {
	public NewGameAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		// call create a new game method here
		System.out.println("New game item pressed");
	}
}

class LoadGameAction extends AbstractAction {
	public LoadGameAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		// call load game method here
		System.out.println("Load game item pressed");
	}
}

class SaveGameAction extends AbstractAction {
	public SaveGameAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		// call save game method here
		System.out.println("Save game item pressed");
	}
}

class HighScoresAction extends AbstractAction {
	public HighScoresAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		// call display high scores window here
	}
}

class SolveGameAction extends AbstractAction {
	public SolveGameAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		// call solve game method here
	}
}
	
// DIFFICULTY MENU ACTIONS
class EasyDiffAction extends AbstractAction {
	public EasyDiffAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		// close current frame
		// open new game with easy difficulty
		CurrentGame.getCurrentGame().newMineGame("easy");
	}
}

class MediumDiffAction extends AbstractAction {
	public MediumDiffAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		// close current frame
		
		// open new game with medium difficulty
		CurrentGame.getCurrentGame().newMineGame("medium");
	}
}
	
class HardDiffAction extends AbstractAction {
	public HardDiffAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		// close current frame
		// open new game with hard difficulty
		CurrentGame.getCurrentGame().newMineGame("hard");
	}
}	