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
	
	public static void saveGame(String file, int[] field, int numMines) throws IOException { 
		//loop through array
		PrintWriter saveItems = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		//print number of mines to file
		saveItems.println(numMines);
		
		for (int i = 0; i < field.length; i++)
			//print items in field array to file
		{
			saveItems.println(field[i]);
		}
		
		
		saveItems.flush();
		saveItems.close();
	}
	
	private static int getTimer() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int loadGame(String filename, int[] field, int numMines) throws IOException {
		
		File file = new File(filename);
		Scanner fileReader = new Scanner(file);
		
		numMines = fileReader.nextInt();
		
		int i = 0;
		while (fileReader.hasNextInt()) {
			field[i] = fileReader.nextInt();
			i++;
		}
		
		return numMines;
	}
	
	public static void saveScore(String file, String name, int time, Date date) throws IOException {
		//print score to file
		PrintWriter saveScore = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		
		time = CurrentGame.getCurrentGame().getBoard().getTimeElapsed();
		date = new Date();
		saveScore.println(name + " " + time + " " + date);
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
