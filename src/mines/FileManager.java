package mines;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class FileManager {

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
}
