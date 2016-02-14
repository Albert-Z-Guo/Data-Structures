// CSC 172
// Project 3 Word Puzzle
// Student Name: Zunran Guo
// Student ID: 28279136
// All work is original. 10+ hours dedicated.

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Collections;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WordFinder {
	// declare global variables
	int puzzleSize;
	Character[][] puzzleMatrix;
	ArrayList<String> wordFound = new ArrayList<String>();

	// construct a hash data structure
	Hash hash = new Hash();
	
	// method to load the dictionary
	public void loadDictionary(String dictionaryFileName) {
		String fileName = dictionaryFileName;
		
		// declare time variables
		long startTime, endTime, elapsedTime;

		// String line will reference one line at a time
		String line = null;

		// start counting time
		startTime = System.currentTimeMillis();

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				// hash each line into hashTableArray, since linux.words
				// is simply a newline-delimited list of dictionary words
				hash.insert(line);
			}

			// Always close files.
			bufferedReader.close();

		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		// end counting time
		endTime = System.currentTimeMillis();
		elapsedTime = endTime - startTime;

		// print the information about hashTableArray
		// System.out.println("\nThe unique words are printed as the following: ");
		//hash.print();
		System.out.println("Dictionary '" + dictionaryFileName + "' is loaded.");
		System.out.println("\nThe final size of the hash table is: " + hash.size());
		System.out.println("The number of unique words in the dictionary is: " + hash.capacity);
		System.out.println("The number of total words in the dictionary is: " + hash.totalItems);
		System.out.println("The number of collisions in hashing: " + hash.collision);
		System.out.println("Process time: " + elapsedTime + " milliseconds");
	}

	// method to read the puzzle
	public void readPuzzle(String puzzleFileNmae) {
		String fileName = puzzleFileNmae;

		// This will reference one line at a time
		String line = null;

		int matrixReadyIndicator = 0;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			// declare the row index of puzzleMatrix
			int i = 0;

			// read every line of the file
			while ((line = bufferedReader.readLine()) != null && i < line.length()) {

				if (matrixReadyIndicator == 0) {
					// initialize the size of puzzle
					puzzleSize = line.length();
					puzzleMatrix = new Character[puzzleSize][puzzleSize];
					matrixReadyIndicator = 1;
				}

				// split each line (word) to individual letter
				// and store each letter in the 2-D array
				for (int j = 0; j < puzzleSize; j++) {
					puzzleMatrix[i][j] = line.charAt(j);
				}

				// once one row is filled, update the row index
				i++;
			}

			// Always close files.
			bufferedReader.close();

		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	// method to print the puzzle
	public void printPuzzle() {
		System.out.println("\nThe puzzle read is: ");
		for (int i = 0; i < puzzleMatrix.length; i++) {
			for (int j = 0; j < puzzleMatrix.length; j++) {
				System.out.print(puzzleMatrix[i][j]);
			}
			System.out.print("\n");
		}
	}

	// method to print the found words in order
	public void printFoundWords() {
		// sort the found words in alphabetically order
		Collections.sort(wordFound);

		// print wordFound in alphabetically order
		System.out.println("\nThe " + wordFound.size() + " words found in the puzzle are: ");
		for (int i = 0; i < wordFound.size(); i++) {
			String word = wordFound.get(i);
			System.out.println(word);
		}
	}

	// method to convert characters in an arraylist to a string
	public String convertToString(ArrayList<Character> arrayList) {
		StringBuilder builder = new StringBuilder(arrayList.size());
		for (Character c : arrayList) {
			builder.append(c);
		}
		return builder.toString();
	}

	// method to check duplicates in found words
	public boolean checkDuplicate(String x){
		for (int i = 0; i < wordFound.size(); i++) {
			if (wordFound.get(i) != null && wordFound.get(i).equalsIgnoreCase(x))
				return true;
		}

		// if x is not in wordFound array list, return false
		return false;
	}
	
	// method to identify words in the puzzle
	public void indentifyWords() {
		// declare an arrayList
		ArrayList<Character> arrayList = new ArrayList<Character>();

		for (int i = 0; i < puzzleSize; i++) {
			for (int j = 0; j < puzzleSize; j++) {
				
				// now the search begins at index puzzleMatrix[i][j]
				// we need to check 8 directions systematically
				int fixedRowIndex = i;
				int fixedColumnIndex = j;
				
				// check the upward direction
				while (fixedRowIndex >= 0) {
					arrayList.add(puzzleMatrix[fixedRowIndex][fixedColumnIndex]);

					// String[] stringArray = new String[arrayList.size()];
					// stringArray = arrayList.toArray(stringArray);
					String wordGenerated = convertToString(arrayList);

					//System.out.println("upwordGenerated: " + wordGenerated);

					// check whether the word exists
					if (hash.lookup(wordGenerated) == true) {
						if (checkDuplicate(wordGenerated) == false){
							wordFound.add(wordGenerated);
						}
					}

					// update fixedRowIndex
					fixedRowIndex--;
				}
				// clear arrayList before next trial
				arrayList.clear();

				// check the downward direction
				fixedRowIndex = i;
				fixedColumnIndex = j;
				while (fixedRowIndex <= puzzleSize - 1) {
					arrayList.add(puzzleMatrix[fixedRowIndex][fixedColumnIndex]);
					String wordGenerated = convertToString(arrayList);

					//System.out.println("downwordGenerated: " + wordGenerated);

					// check whether the word exists
					if (hash.lookup(wordGenerated) == true) {
						if (checkDuplicate(wordGenerated) == false){
							wordFound.add(wordGenerated);
						}
					}

					// update fixedRowIndex
					fixedRowIndex++;
				}
				// clear arrayList before next trial
				arrayList.clear();

				// check the leftward direction
				fixedRowIndex = i;
				fixedColumnIndex = j;
				while (fixedColumnIndex >= 0) {
					arrayList.add(puzzleMatrix[fixedRowIndex][fixedColumnIndex]);
					String wordGenerated = convertToString(arrayList);

					//System.out.println("leftwordGenerated: " + wordGenerated);

					// check whether the word exists
					if (hash.lookup(wordGenerated) == true) {
						if (checkDuplicate(wordGenerated) == false){
							wordFound.add(wordGenerated);
						}
					}

					// update fixedColumnIndex
					fixedColumnIndex--;
				}
				// clear arrayList before next trial
				arrayList.clear();

				// check the rightward direction
				fixedRowIndex = i;
				fixedColumnIndex = j;
				while (fixedColumnIndex <= puzzleSize - 1) {
					arrayList.add(puzzleMatrix[fixedRowIndex][fixedColumnIndex]);
					String wordGenerated = convertToString(arrayList);

					//System.out.println("rightwordGenerated: " + wordGenerated);

					// check whether the word exists
					if (hash.lookup(wordGenerated) == true) {
						if (checkDuplicate(wordGenerated) == false){
							wordFound.add(wordGenerated);
						}
					}

					// update fixedColumnIndex
					fixedColumnIndex++;
				}
				// clear arrayList before next trial
				arrayList.clear();

				// check the diagonal left-upward direction
				fixedRowIndex = i;
				fixedColumnIndex = j;
				while (fixedColumnIndex >= 0 && fixedRowIndex >= 0) {
					arrayList.add(puzzleMatrix[fixedRowIndex][fixedColumnIndex]);
					String wordGenerated = convertToString(arrayList);

					//System.out.println("left-upwardwordGenerated: " + wordGenerated);

					// check whether the word exists
					if (hash.lookup(wordGenerated) == true) {
						if (checkDuplicate(wordGenerated) == false){
							wordFound.add(wordGenerated);
						}
					}

					// update fixedColumnIndex
					fixedColumnIndex--;
					fixedRowIndex--;
				}
				// clear arrayList before next trial
				arrayList.clear();

				// check the diagonal left-downward direction
				fixedRowIndex = i;
				fixedColumnIndex = j;
				while (fixedColumnIndex >= 0 && fixedRowIndex <= puzzleSize - 1) {
					arrayList.add(puzzleMatrix[fixedRowIndex][fixedColumnIndex]);
					String wordGenerated = convertToString(arrayList);

					//System.out.println("left-downwardwordGenerated: " + wordGenerated);

					// check whether the word exists
					if (hash.lookup(wordGenerated) == true) {
						if (checkDuplicate(wordGenerated) == false){
							wordFound.add(wordGenerated);
						}
					}

					// update fixedColumnIndex
					fixedColumnIndex--;
					fixedRowIndex++;
				}
				// clear arrayList before next trial
				arrayList.clear();

				// check the diagonal right-upward direction
				fixedRowIndex = i;
				fixedColumnIndex = j;
				while (fixedColumnIndex <= puzzleSize - 1 && fixedRowIndex >= 0) {
					arrayList.add(puzzleMatrix[fixedRowIndex][fixedColumnIndex]);
					String wordGenerated = convertToString(arrayList);

					//System.out.println("right-upwardwordGenerated: " + wordGenerated);

					// check whether the word exists
					if (hash.lookup(wordGenerated) == true) {
						if (checkDuplicate(wordGenerated) == false){
							wordFound.add(wordGenerated);
						}
					}

					// update fixedColumnIndex
					fixedColumnIndex++;
					fixedRowIndex--;
				}
				// clear arrayList before next trial
				arrayList.clear();

				// check the diagonal right-downward direction
				fixedRowIndex = i;
				fixedColumnIndex = j;
				while (fixedColumnIndex <= puzzleSize - 1 && fixedRowIndex <= puzzleSize - 1) {
					arrayList.add(puzzleMatrix[fixedRowIndex][fixedColumnIndex]);
					String wordGenerated = convertToString(arrayList);

					//System.out.println("right-downwardwordGenerated: " + wordGenerated);

					// check whether the word exists
					if (hash.lookup(wordGenerated) == true) {
						if (checkDuplicate(wordGenerated) == false){
							wordFound.add(wordGenerated);
						}
					}

					// update fixedColumnIndex
					fixedColumnIndex++;
					fixedRowIndex++;
				}
				// clear arrayList before next trial
				arrayList.clear();
			}
		}
	}

	// method to export the found words in text file format
	public void writeFile(String outputFileName) {
		// The name of the file to open.
		String fileName = outputFileName;

		try {
			// assume default encoding
			FileWriter fileWriter = new FileWriter(fileName);

			// always wrap FileWriter in BufferedWriter
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// write the file line by line
			for (int i = 0; i < wordFound.size(); i++) {
				bufferedWriter.write(wordFound.get(i));
				bufferedWriter.newLine();
			}

			// Always close files.
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
	}
		
	public static void main(String[] args) {
		WordFinder newPuzzle = new WordFinder();
		newPuzzle.loadDictionary(args[0]);
		newPuzzle.readPuzzle(args[1]);
		newPuzzle.printPuzzle();
		newPuzzle.indentifyWords();
		newPuzzle.printFoundWords();
		newPuzzle.writeFile(args[2]);
		System.out.println("\nThe above words are prined in the new file named " + args[2]);
	}
}
