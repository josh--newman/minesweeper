package mines;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.sql.Time;

import javax.swing.JOptionPane;

public class FileManager {
	
	private static final int NUMBER_OF_HIGH_SCORES = 5;
	private static final int NUM_EASY_CELLS = 256;
	private static final int NUM_MEDIUM_CELLS = 784;
	private static final int NUM_HARD_CELLS = 2304;
	
	public static void saveGame(File file, int[] field, int numMines, String difficulty) throws IOException { 
		
		PrintWriter saveItems = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		
		// save the difficulty of this game as a string
		saveItems.println(difficulty);
		
		// save number of mines to file
		saveItems.println(numMines);
		
		//loop through field array
		for (int i = 0; i < field.length; i++)
			//save items in field array to file
		{
			saveItems.println(field[i]);
		}
		
		
		saveItems.flush();
		saveItems.close();
	}

	public static int[][] loadGame(String filename) throws IOException {
		
		File file = new File(filename);
		Scanner fileReader = new Scanner(file);
		
		//load in the difficulty of the saved game
		int[] field = {};
		String diff = fileReader.nextLine();
		if (diff.equals("easy")) {
			field = new int[NUM_EASY_CELLS];
		} else if (diff.equals("medium")) {
			field = new int[NUM_MEDIUM_CELLS];
		} else if (diff.equals("hard")) {
			field = new int[NUM_HARD_CELLS];
		}
		
		//number of mines array
		int[] numMines = new int[1];
		numMines[0] = fileReader.nextInt();
		
		//load the saved state of board 
		int i = 0;
		while (fileReader.hasNextInt()) {
			field[i] = fileReader.nextInt();
			i++;
		}
		
		//2D array of number of mines and state of board
		int[][] items = {numMines, field};
		return items;
	}
	
	public static void saveScore() throws IOException {
		String name = getNameDialog();
		//print score to file
		File file = null;
		if (CurrentGame.getCurrentGame().getDifficulty().equals("easy")) {
			file = new File("easy_scores.txt");
		} else if (CurrentGame.getCurrentGame().getDifficulty().equals("medium")) {
			file = new File("medium_scores.txt");
		} else if (CurrentGame.getCurrentGame().getDifficulty().equals("hard")) {
			file = new File("hard_scores.txt");
		}
		
		PrintWriter saveScore = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		
		//get name, current time and date(change to String DD/MM/YYYY format)
		int time = CurrentGame.getCurrentGame().getBoard().getTimeElapsed();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
		Date date = new Date();
		String dateString = sdf.format(date);
		saveScore.println(name + " " + time + " " + dateString);
		
		saveScore.flush();
		saveScore.close();
	}
	
	public static String[] loadScores(String filename) throws IOException {
		//load score from file
		File file = new File(filename);
		Scanner fileReader = new Scanner(file);
		
		String[] highScores = new String[NUMBER_OF_HIGH_SCORES];
		
		int i = 0;
		while (fileReader.hasNextLine()) {
			highScores[i] = fileReader.nextLine();
			i++;
		}
		
		return highScores;
	}
	
	//high scores save dialogue box
	private static String getNameDialog() {
		String name = (String)JOptionPane.showInputDialog(CurrentGame.getCurrentGame().getBoard(),
		"You won!\nPlease enter your name:\n",
		"Save high score",
		JOptionPane.PLAIN_MESSAGE,
		null,
		null,
		"Name");
		return name;
	}
}
