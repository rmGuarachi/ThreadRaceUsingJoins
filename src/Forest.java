import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;
import java.util.Scanner;

public class Forest {

	private final int FOREST_SIZE;
	private final char[] SET_OF_LETTERS;
	private Random random = new Random();
	private static final String FILE_NAME = "forest.txt";
	private final String maps[];
	
	Forest(int forestSize, char[] setOfWords){
		SET_OF_LETTERS = setOfWords;
		FOREST_SIZE = forestSize;
		maps = generateMaps(forestSize);
		setForest();
	}
	
	private void setForest() {
		// this will create the forest file
		String words = generateForest();
		Writer fileWriter;
		try {
			fileWriter = new FileWriter(FILE_NAME, false);
			fileWriter.write(words);
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String generateForest() {
		// this will generate the forest words string
		String words = "";
		for (int i=0; i < FOREST_SIZE; i++) {
			words += generateMagicWord() + " ";
		}
		return words;
	}
	
	private String[] generateMaps(int size) {
		// generate magicWords for players - each player has different magic words
		// Magic words = maps
		String words[] = new String[size];
		for (int i=0; i < size; i++) {
			words[i] = generateMagicWord();
		}
		return words;
	}
	
	private String generateMagicWord() {
		// makes magic words
		// TODO make it generate unique letters?
		String letter = "";
		for (int i=0; i < SET_OF_LETTERS.length; i++) {
			letter += SET_OF_LETTERS[random.nextInt(SET_OF_LETTERS.length)];
		}
		return letter;
	}
	
	public static boolean searchForest(String magicWord, Racer racer) {
		// players use this static method to search for magic word in forest file
		boolean wordIsInMap = false;
		try {
			Scanner scanner = new Scanner(new File(FILE_NAME));
			while(scanner.hasNext()) {
				racer.crossObstacle(null, 0, 70); // each racer will search at different pace
				if (scanner.next().equals(magicWord) ) {
					wordIsInMap = true;
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wordIsInMap;
	}

	public String getMap(int i) {
		// return keywords for player i
		return maps[i];
	}
}
