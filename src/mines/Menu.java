package mines;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

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
		JMenuItem undo = new JMenuItem(new UndoAction("Undo"));
		JMenuItem redo = new JMenuItem(new RedoAction("Redo"));
		
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
		CurrentGame.getCurrentGame().newMineGame();
	}
}

class LoadGameAction extends AbstractAction {
	public LoadGameAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		// call load game method here
		JFileChooser chooser = new JFileChooser();
		FileFilter ft = new FileNameExtensionFilter(".txt","txt");
		chooser.addChoosableFileFilter(ft);
		int returnVal = chooser.showOpenDialog(CurrentGame.getCurrentGame().getBoard());
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	
	    	int[] field = {};
	    	int numMines = 0;
	    	
	    	try {
				int[][] items = FileManager.loadGame(chooser.getSelectedFile().getName());
				numMines = items[0][0];
				field = items[1];
				CurrentGame.getCurrentGame().newMineGame(field, numMines);
			} catch (IOException ex) {
				//catch exception
				ex.printStackTrace();
			}
	    
	       System.out.println("Load game: " +
	            chooser.getSelectedFile().getName());
	       System.out.println("Loaded field: " + Arrays.toString(field));
	       System.out.println("Loaded numMines: " + numMines);
	       		System.out.println("Loading game");
	       		
	    }
		
	}
}

class SaveGameAction extends AbstractAction {
	public SaveGameAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		//get file chooser
		JFileChooser chooser = new JFileChooser();
		FileFilter ft = new FileNameExtensionFilter(".txt",".txt");
		chooser.addChoosableFileFilter(ft);
		//open file chooser on top of current game
		chooser.showSaveDialog(CurrentGame.getCurrentGame().getBoard());
		//ask if its OK to overwrite same named file if applicable

		int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to override existing file?", "Confirm",
	        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    if (response == JOptionPane.NO_OPTION) {
		      System.out.println("No button clicked");
		    } else if (response == JOptionPane.YES_OPTION) {
		      System.out.println("Yes button clicked");
		    } else if (response == JOptionPane.CLOSED_OPTION) {
		      System.out.println("JOptionPane closed");
		    } 

	    //save current board state and number of mines
		try {
			FileManager.saveGame(chooser.getSelectedFile(),
			CurrentGame.getCurrentGame().getBoard().getField(), 
			CurrentGame.getCurrentGame().getBoard().getNumMines(),
			CurrentGame.getCurrentGame().getDifficulty());
		} catch (IOException e1) {
			//catch exception
			e1.printStackTrace();
		}
		
		System.out.println("Game Saved: " + 
				chooser.getSelectedFile());
	}
}

class HighScoresAction extends AbstractAction {
	public HighScoresAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		// call display high scores window here
		new HighScores().setVisible(true);
	}
}

class SolveGameAction extends AbstractAction {
	public SolveGameAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		// call solve game method here
		CurrentGame.getCurrentGame().getBoard().solveGame();
	}
}

// UNDO REDO MENU ACTIONS
class UndoAction extends AbstractAction {
	public UndoAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		// call solve game method here
		CurrentGame.getCurrentGame().getBoard().undo();
	}
}

class RedoAction extends AbstractAction {
	public RedoAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		// call solve game method here
		CurrentGame.getCurrentGame().getBoard().redo();
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
		CurrentGame.getCurrentGame().setDifficulty("easy");
		CurrentGame.getCurrentGame().newMineGame();
	}
}

class MediumDiffAction extends AbstractAction {
	public MediumDiffAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		// close current frame
		// open new game with medium difficulty
		CurrentGame.getCurrentGame().setDifficulty("medium");
		CurrentGame.getCurrentGame().newMineGame();
	}
}
	
class HardDiffAction extends AbstractAction {
	public HardDiffAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		// close current frame
		// open new game with hard difficulty
		CurrentGame.getCurrentGame().setDifficulty("hard");
		CurrentGame.getCurrentGame().newMineGame();
	}
}	