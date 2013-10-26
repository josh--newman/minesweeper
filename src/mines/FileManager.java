package mines;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.sql.Time;

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
		
		int[] field = {};
		String diff = fileReader.nextLine();
		if (diff.equals("easy")) {
			field = new int[NUM_EASY_CELLS];
		} else if (diff.equals("medium")) {
			field = new int[NUM_MEDIUM_CELLS];
		} else if (diff.equals("hard")) {
			field = new int[NUM_HARD_CELLS];
		}
		
		int[] numMines = new int[1];
		numMines[0] = fileReader.nextInt();
		
		
		int i = 0;
		while (fileReader.hasNextInt()) {
			field[i] = fileReader.nextInt();
			i++;
		}
		
		int[][] items = {numMines, field};
		return items;
	}
	
	public static void saveScore(String file, String name, int time, Date date) throws IOException {
		//print score to file
		PrintWriter saveScore = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		
		time = CurrentGame.getCurrentGame().getBoard().getTimeElapsed();
		date = new Date();
		saveScore.println(name + " " + time + " " + date);
		
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
}
