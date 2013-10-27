package mines;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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

class SaveGameAction extends AbstractAction {
	public SaveGameAction(String text) {
		super(text);
	}
	public void actionPerformed(ActionEvent e) {
		//get file chooser
		JFileChooser chooser = new JFileChooser();
		FileFilter ft = new FileNameExtensionFilter(".txt","txt");
		chooser.addChoosableFileFilter(ft);
		//open file chooser on top of current game
		int saveWindow = chooser.showSaveDialog(CurrentGame.getCurrentGame().getBoard());		

		//when saving a file name
		if (saveWindow == JFileChooser.APPROVE_OPTION) {
			//auto adds ".txt" to file names
			File fileToBeSaved = chooser.getSelectedFile();
            if (!fileToBeSaved.getAbsolutePath().endsWith(".txt")) {
            	fileToBeSaved = new File(chooser.getSelectedFile().getAbsolutePath() + ".txt");
            }
            //conditions for file name already exists
			if(chooser.getSelectedFile().exists()
					&& chooser.getDialogType() == chooser.SAVE_DIALOG) {
	            int result = JOptionPane.showConfirmDialog
	            		(null,"The file exists, overwrite?",
	            				"Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
	            switch(result) {
	                case JOptionPane.YES_OPTION:
	                    chooser.approveSelection();
	                    
	                    try {
	    					FileManager.saveGame(fileToBeSaved,
	    					CurrentGame.getCurrentGame().getBoard().getField(), 
	    					CurrentGame.getCurrentGame().getBoard().getNumMines(),
	    					CurrentGame.getCurrentGame().getDifficulty());
	    				} catch (IOException e1) {
	    					//catch exception
	    					e1.printStackTrace();
	    				}
	                    
	                    System.out.println("Game Saved: " +
                    		fileToBeSaved.getAbsolutePath());
	                    return;
	                case JOptionPane.NO_OPTION:
	                	System.out.println("User selected No to file overwrite");
	                    return;
	                case JOptionPane.CLOSED_OPTION:
	                	System.out.println("Save file overwrite dialogue box closed");
	                    return;
	                case JOptionPane.CANCEL_OPTION:
	                    chooser.cancelSelection();
	                    System.out.println("Cancelled");
	                    return;
	                default:
	                	return;
	            }
	        }
			//for brand new file names
		    chooser.approveSelection();
		    try {
				FileManager.saveGame(fileToBeSaved,
				CurrentGame.getCurrentGame().getBoard().getField(), 
				CurrentGame.getCurrentGame().getBoard().getNumMines(),
				CurrentGame.getCurrentGame().getDifficulty());
			} catch (IOException e1) {
				//catch exception
				e1.printStackTrace();
			}
		    System.out.println("Game Saved: " +
	    		fileToBeSaved.getAbsolutePath());
		}
		//if save game window is closed
		else {
			System.out.println("Save game cancelled");
		}
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
				int[][] items = FileManager.loadGame(chooser.getSelectedFile().getAbsolutePath());
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
		
	    else {
	    	System.out.println("Load game cancelled");
	    }
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