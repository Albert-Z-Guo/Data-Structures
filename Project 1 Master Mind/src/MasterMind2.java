// CSC 172 
// Project 1 Master Mind
// Student Name: Zunran Guo
// Student ID: 28279136
// Reference: J. RECREATIONAL MATHEMATICS, Vol. 9(1), 1976-77
// Special thanks to suggestions and advice
// from cordial computer science friends Joe Zhou and Colin Zheng
// All work is original. 35+ hours dedicated.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MasterMind2 implements MasterMindInterface {

	// create the objects used in the project
	public MyDoubleNode<String[]> headNode;
	public String[] possibleColors;
	public ArrayList<String> reducedPossibleColors;
	public static int colors;
	public static int positions;
	public static int initialGuessIndicator = 0;
	public static int userMistakeIndicator = 0;
	public static int colorsRight_positionsWrong;
	public static int colorsRight_positionsRight;
	public String[] guess;
	public static int colorNumberCorrentIndicator;
	public int option;
	
	public MasterMind2() {
		possibleColors = new String[] { "RED", "GREEN", "BLUE", "YELLOW", "ORANGE", "PINK" };
		headNode = null;
		
	}
	
	// Reset the game
	public void newGame() {
		System.out.println("\nA new game is started!");

		// scan user's options
		while (true){
			System.out.println("From 'RED', 'GREEN', 'BLUE', 'YELLOW', 'ORANGE', 'PINK'"
					+ "\nHow many colors does your token have?");
			Scanner scannerOption = new Scanner(System.in);
			option = scannerOption.nextInt();
			if (option > 0 && option < 7) {
				System.out.printf("OK. ");
				// test
				// reset reducedPossibleColors for the new game
				reducedPossibleColors = new ArrayList<String>();
				// reset colorNumberCorrentIndicator for new game
				colorNumberCorrentIndicator = 0;
				while (true){
				System.out.println("Which color does your token have? Type 'done' to dinish.");
				// add user's color choice
				Scanner scannerOptionColor = new Scanner(System.in);
				String optionColor = scannerOptionColor.nextLine();
					if (optionColor.equalsIgnoreCase("RED")){
						reducedPossibleColors.add("RED");
						System.out.println("OK. RED.");
						colorNumberCorrentIndicator = colorNumberCorrentIndicator + 1;
					}
					else if (optionColor.equalsIgnoreCase("GREEN")){
						reducedPossibleColors.add("GREEN");
						System.out.println("OK. GREEN.");
						colorNumberCorrentIndicator = colorNumberCorrentIndicator + 1;
					}
					else if (optionColor.equalsIgnoreCase("BLUE")){
						reducedPossibleColors.add("BLUE");
						System.out.println("OK. BLUE.");
						colorNumberCorrentIndicator = colorNumberCorrentIndicator + 1;
					}
					else if (optionColor.equalsIgnoreCase("YELLOW")){
						reducedPossibleColors.add("YELLOW");
						System.out.println("OK. YELLOW.");
						colorNumberCorrentIndicator = colorNumberCorrentIndicator + 1;
					}
					else if (optionColor.equalsIgnoreCase("ORANGE")){
						reducedPossibleColors.add("ORANGE");
						System.out.println("OK. ORANGE.");
						colorNumberCorrentIndicator = colorNumberCorrentIndicator + 1;
					}
					else if (optionColor.equalsIgnoreCase("PINK")){
						reducedPossibleColors.add("PINK");
						System.out.println("OK. PINK.");
						colorNumberCorrentIndicator = colorNumberCorrentIndicator + 1;
					}
					else if (!optionColor.equalsIgnoreCase("DONE")) {
						System.out.println("Invalid input! Try it again.");
						colorNumberCorrentIndicator = 0;
					}
					
					if (colorNumberCorrentIndicator != 0){
						
						// convert array list to array
						String[] reducedPossibleColorsStringArray = new String[reducedPossibleColors.size()];
						reducedPossibleColorsStringArray = reducedPossibleColors.toArray(reducedPossibleColorsStringArray);
						
						// print the array list
						System.out.printf("Your current token has the following colors: ");
						
						for (String element : reducedPossibleColorsStringArray) {
							System.out.print(element + " ");
						}
						System.out.println("");
					}
					
					if (optionColor.equalsIgnoreCase("DONE")){
						System.out.printf("\nOK. Just %d colors chosen.", reducedPossibleColors.size());
						System.out.println("\nNote that the colors guessed by the program may repeat.");
						break;
					}
					
					if (colorNumberCorrentIndicator == option){
						System.out.printf("\n%d colors chosen already. You have reached the maximum colors you chose.", reducedPossibleColors.size());
						System.out.println("\nNote that the colors guessed by the program may repeat.");
						break;
					}
					
				}
				colors = option;
				break;
			} else {
				System.out.println("Sorry, the number of colors supported in thie program is from 1 to 6.");
			}
		}
		while (true){
			System.out.println("\nHow many positions would you like to have? (1-6)?");
			Scanner scannerOption2 = new Scanner(System.in);
			int Option2 = scannerOption2.nextInt();
			if (Option2 > 0 && Option2 < 7){
				positions = Option2;
				System.out.printf("\nOK. %d positions.\n", Option2);
				System.out.println("Note that if the number of positions is 5 or 6,"
						+ "\nit may take more than 12 seconds for the program to initialize and respond."
						+ "\nPlease pay close attention to the prompts if your token is long."
						+ "\nUsers make mistakes quite often when the number of positions is 5 or 6.");
				break;
			} else {
				System.out.println("Sorry, the number of positions supported in thie program is from 1 to 6.");
			}
		}	
		
		// specify the length of the guess
		guess = new String[positions];
		
		// reset the linked list data structure for the new game
		headNode = null;
		
		// convert array list to array for the new game
		String[] reducedPossibleColorsStringArray = new String[reducedPossibleColors.size()];
		reducedPossibleColorsStringArray = reducedPossibleColors.toArray(reducedPossibleColorsStringArray);

		// reset userMistakeIndicator for the new game
		userMistakeIndicator = 0;

		// reset initialGuessIndicator for the new game
		initialGuessIndicator = 0;
		
		// store all possible tokens in the linked list data structure
		int n = reducedPossibleColorsStringArray.length;
		if (positions == 1){
			for (int i = 0; i < n; i++) {
	                ArrayList<String> arrayList = new ArrayList<String>();
	                arrayList.add(reducedPossibleColorsStringArray[i]);
	
	                String[] stringArray = new String[arrayList.size()];
	                stringArray = arrayList.toArray(stringArray);
	                insert(stringArray);
	        }
		}
		
		if (positions == 2){
			for (int i = 0; i < n; i++) {
	            for (int j = 0; j < n; j++) {
	                ArrayList<String> arrayList = new ArrayList<String>();
	                arrayList.add(reducedPossibleColorsStringArray[i]);
	                arrayList.add(reducedPossibleColorsStringArray[j]);
	
	                String[] stringArray = new String[arrayList.size()];
	                stringArray = arrayList.toArray(stringArray);
	                insert(stringArray);
	            }
	        }
		}
		
		if (positions == 3){
			for (int i = 0; i < n; i++) {
	            for (int j = 0; j < n; j++) {
	                for (int k = 0; k < n; k++) {
                        ArrayList<String> arrayList = new ArrayList<String>();
                        arrayList.add(reducedPossibleColorsStringArray[i]);
                        arrayList.add(reducedPossibleColorsStringArray[j]);
                        arrayList.add(reducedPossibleColorsStringArray[k]);

                        String[] stringArray = new String[arrayList.size()];
                        stringArray = arrayList.toArray(stringArray);
                        insert(stringArray);
	                }
	            }
	        }
		}
		
		if (positions == 4){
			for (int i = 0; i < n; i++) {
	            for (int j = 0; j < n; j++) {
	                for (int k = 0; k < n; k++) {
	                    for (int l = 0; l < n; l++) {
	                        ArrayList<String> arrayList = new ArrayList<String>();
	                        arrayList.add(reducedPossibleColorsStringArray[i]);
	                        arrayList.add(reducedPossibleColorsStringArray[j]);
	                        arrayList.add(reducedPossibleColorsStringArray[k]);
	                        arrayList.add(reducedPossibleColorsStringArray[l]);

	                        String[] stringArray = new String[arrayList.size()];
	                        stringArray = arrayList.toArray(stringArray);
	                        insert(stringArray);
	                    }
	                }
	            }
	        }
		}
		
		if (positions == 5){
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					for (int k = 0; k < n; k++) {
						for (int l = 0; l < n; l++) {
							for (int m = 0; m < n; m++) {
									ArrayList<String> arrayList = new ArrayList<String>();
			                        arrayList.add(reducedPossibleColorsStringArray[i]);
			                        arrayList.add(reducedPossibleColorsStringArray[j]);
			                        arrayList.add(reducedPossibleColorsStringArray[k]);
			                        arrayList.add(reducedPossibleColorsStringArray[l]);
			                        arrayList.add(reducedPossibleColorsStringArray[m]);
			
			                        String[] stringArray = new String[arrayList.size()];
			                        stringArray = arrayList.toArray(stringArray);
			                        insert(stringArray);
							}
						}
					}
				}
			}
		}
		
		if (positions == 6){
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					for (int k = 0; k < n; k++) {
						for (int l = 0; l < n; l++) {
							for (int m = 0; m < n; m++) {
								for (int o = 0; o < n; o++) {
									ArrayList<String> arrayList = new ArrayList<String>();
			                        arrayList.add(reducedPossibleColorsStringArray[i]);
			                        arrayList.add(reducedPossibleColorsStringArray[j]);
			                        arrayList.add(reducedPossibleColorsStringArray[k]);
			                        arrayList.add(reducedPossibleColorsStringArray[l]);
			                        arrayList.add(reducedPossibleColorsStringArray[m]);
			                        arrayList.add(reducedPossibleColorsStringArray[o]);
			
			                        String[] stringArray = new String[arrayList.size()];
			                        stringArray = arrayList.toArray(stringArray);
			                        insert(stringArray);
								}
							}
						}
					}
				}
			}
		}
	}

	// return the next guess
	public String[] nextMove() {
		if (initialGuessIndicator == 1){
			// delete the impossibilities inferred from the guessed array
			Response();
	
			// return the top new guess from the remaining possibilities
			if (initialGuessIndicator == 1) guess = headNode.data;
		}
		
		// if no guess hasn't been made
		if (initialGuessIndicator == 0) {
			
			// convert array list to array
			String[] reducedPossibleColorsStringArray = new String[reducedPossibleColors.size()];
			reducedPossibleColorsStringArray = reducedPossibleColors.toArray(reducedPossibleColorsStringArray);

			// define variables used for the initial guess
			int max = reducedPossibleColorsStringArray.length - 1;
			int min = 0;
			Random rand = new Random();
			
			// check max - min > 0 
			// in case user makes a mistake (asking for elimination while nothing remains)
			// if the user selects more than 1 color, 
			if (max - min >= 0 && colors > 1){
				// change the counter when the game starts
				int initialGuessCode1 = rand.nextInt((max - min) + 1) + min;
				int initialGuessCode2 = rand.nextInt((max - min) + 1) + min;

				// the initial guess will always contains only two colors
				// to maximize possible eliminations
				// integer truncation for positions/2 is allowed
				for (int i = 0; i < positions / 2; i++) {
					guess[i] = reducedPossibleColorsStringArray[initialGuessCode1];
				}
				for (int i = positions / 2; i < positions; i++) {
					guess[i] = reducedPossibleColorsStringArray[initialGuessCode2];
				}
				
			}
			// if the user selects only 1 color
			else if (max - min >= 0 && colors == 1){
				int initialGuessCode3 = rand.nextInt((max - min) + 1) + min;
				for (int k = 0; k < positions; k++) {
					guess[k] = reducedPossibleColorsStringArray[initialGuessCode3];
				}
			}
			
			// change the initialGuessIndicator
			initialGuessIndicator = 1;
		}
		return guess;
	}

	// indicate whether to delete form the linked list
	public boolean deleteIndicator(String[] stringArrayInNode) {
		// if not a single color element match is found
		if (colorsRight_positionsWrong == 0 && colorsRight_positionsRight == 0) {

			// reset the initialGuessIndicator
			initialGuessIndicator = 0;

			// remove the impossibilities
			// if there are more than 1 color to be removed
			if (guess.length > 1){
			String colorToBeRemoved1 = guess[guess.length / 2 - 1];
			String colorToBeRemoved2 = guess[guess.length / 2];

			reducedPossibleColors.remove(colorToBeRemoved1);
			reducedPossibleColors.remove(colorToBeRemoved2);
			} 
			// if there is only one color to remove
			else {
				String colorToBeRemoved = guess[0];
				reducedPossibleColors.remove(colorToBeRemoved);
			}

			// all nodes that contain the guessed color element should be deleted
			for (String element1 : guess) {
				for (String element2 : stringArrayInNode) {
					if (element1.equals(element2)) {
						return true;
					}
				}
			}
		}

		// copy guessArray to testArray
		String[] guessArrayCopy = new String[guess.length];
		System.arraycopy(guess, 0, guessArrayCopy, 0, guess.length);
		// copy stringArrayInNode to testArray
		String[] testArray = new String[stringArrayInNode.length];
		System.arraycopy(stringArrayInNode, 0, testArray, 0, stringArrayInNode.length);

		// define a counter for colorsRight_positionsRight
		int counterBlack = 0;
		for (int k = 0; k < guessArrayCopy.length; k++) {
			if (guessArrayCopy[k].equals(testArray[k])) {
				// once the elements in the guessArrayCopy and testArray are matched, 
				// make it in such way so that it will not be matched again
				testArray[k] = "null";
				guessArrayCopy[k] = "null";
				counterBlack = counterBlack + 1;
			}
		}

		// define a counter for colorsRight_positionsWrong
		int counterWhite = 0;
		for (int i = 0; i < guessArrayCopy.length; i++) {
			for (int j = 0; j < testArray.length; j++) {
				if (guessArrayCopy[i] != "null" && guessArrayCopy[i].equals(testArray[j])) {
					// once one element in the testArray is matched
					// make it in such way so that it will not be matched again
					guessArrayCopy[i] = "null";
					testArray[j] = "null";
					counterWhite = counterWhite + 1;
				}
			}
		}

		// if a match is satisfied, keep the node
		if (counterBlack == colorsRight_positionsRight && counterWhite == colorsRight_positionsWrong) {
			return false;
		}

		// nodes in all the other cases should be deleted
		return true;
	}

	// delete the impossibilities from the linked list
	public void Response() {
		MyDoubleNode<String[]> currentNode = new MyDoubleNode<String[]>();
		currentNode = headNode;

		// eliminate impossibilities through the beginning of the list
		while (currentNode.next != null) {

			// if the headNode is deleted, search from the beginning again
			currentNode = headNode;

			// if the node to be deleted is the first node (and not the last node)
			if (deleteIndicator(currentNode.data) && currentNode.next != null) {
				headNode = headNode.next;
				headNode.prev = null;
			}

			// if the first node is kept
			else {
				while (currentNode.next != null) {
					// once a node is deleted, search from the beginning again

					// delete impossibility
					if (deleteIndicator(currentNode.data)) {
						currentNode.next.prev = currentNode.prev;
						currentNode.prev.next = currentNode.next;

						// once delete a node, go back to headNode
						currentNode = headNode;
					} else {
						// if nothing is deleted, keep searching
						currentNode = currentNode.next;
					}
				}
			}
			if (currentNode.next == null) {
				break;
			}
		}

		// if the last node (not the only node remaining in the list) is reached
		if (deleteIndicator(currentNode.data) && currentNode.prev != null) {
			currentNode.prev.next = null;
		}

		// if the last node is reached and this last node is the only node
		// remaining in the list, indicate that the user may have made a mistake
		if (currentNode.prev == null) {
			userMistakeIndicator = 1;
		}
	}
	
	public static void main(String[] args) {
		// instantiate the class
		MasterMind2 testInstance = new MasterMind2();

		System.out.println("Welcome to Mastermind!");
		System.out.println("\nIn this game, the program will be prompted to guess a token you made. "
				+ "\nFor example, if your token is "
				+ "\n'RED' 'GREEN' 'BLUE' 'YELLOW', while the program guesses "
				+ "\n'RED' 'GREEN' 'RED' 'GREEN', the correct prompts are"
				+ "\n\n 2 cases of both right color and right position (RED and GREEN) and"
				+ "\n 0 case of right color but wrong position"
				+ "\n\nNotice that in this program the third 'Red' and forth 'Green' in the second row "
				+ "\ncannot be counted as right-color-wrong-position with the 'Red' and 'Green' in the "
				+ "\nfirst row, since the 'Red' and 'Green' in the first row have aleady matched with "
				+ "\nthe first two 'Red' and 'Green' in the second row as right-color-right-position.");

		while (true) {
			System.out.println("\nThe following options are now available:\n" + "'new': start a new game\n"
					+ "'quit': quit the game");
			// scan user's option
			Scanner scanner = new Scanner(System.in);
			String userOption = scanner.nextLine();

			if (userOption.equals("new")) {
				// start a new game
				testInstance.newGame();
				// while in the game
				while (true) {
					// print the guessed token (again)
					System.out.println("\nProgram's guess:");
					for (String element : testInstance.nextMove()) {
						System.out.print(element + " ");
					}

					if (userMistakeIndicator == 1) {
						System.out
								.println("\nThe above guess is the last combinatorics possibility (before elimination) "
										+ "\nbased on your feedback. If the guess is not correct, you may have made a mistake."
										+ "\nQuit the game or try it again?");
						break;
					}

					while (true) {
						System.out.println(
								"\nPlease enter the number of cases of both correct color and correct position:");
						int numberInput2 = scanner.nextInt();
						if (numberInput2 >= 0 && numberInput2 <= positions) {
							colorsRight_positionsRight = numberInput2;
							break;
						} else {
							System.out.println("Invalid user input! Please try it again.\n");
						}
					}

					while (true) {
						System.out.println("Please enter the number of cases of correct color but wrong position:");
						int numberInput1 = scanner.nextInt();
						if (numberInput1 >= 0 && numberInput1 <= positions) {
							colorsRight_positionsWrong = numberInput1;
							break;
						} else {
							System.out.println("Invalid user input! Please try it again.\n");
						}
					}

					// if the token is right
					if (colorsRight_positionsWrong == 0 && colorsRight_positionsRight == positions) {
						System.out.println("Awesome! Glad to know the guess is right :]");
						break;
					}

				}

			} 
			// hidden testing option
			else if (userOption.equals("list")) {
				testInstance.printList();

			} else if (userOption.equals("quit")) {
				scanner.close();
				System.out.println("\nGame Over... Scanner closed.");
				break;
			} else {
				System.out.println("Invalid user input! Please try it again.\n");
			}
		}
	}

	// check whether the linked list is empty
	public boolean isEmpty() {
		if (headNode == null) {
			return true;
		} else {
			return false;
		}
	}

	// insert the item to the linked list
	public void insert(String[] stringArray) {
		// if headNode is empty, insert stringArray
		if (isEmpty() == true) {
			MyDoubleNode<String[]> nodeAfter = new MyDoubleNode<String[]>();
			nodeAfter.data = stringArray;
			nodeAfter.prev = null;
			nodeAfter.next = null;
			headNode = nodeAfter;
		}
		// if headNode is not empty
		else {
			MyDoubleNode<String[]> nodeAfter = new MyDoubleNode<String[]>();
			MyDoubleNode<String[]> currentNode = new MyDoubleNode<String[]>();

			// search for the last node from the beginning
			currentNode = headNode;
			while (currentNode.next != null) {
				currentNode = currentNode.next;
			}

			// create a new node and insert stringArray
			nodeAfter.data = stringArray;
			nodeAfter.prev = currentNode;
			currentNode.next = nodeAfter;
		}
	}

	// print the linked list from the beginning
	public void printList() {
		System.out.println("\nThe remaining token possibilities after elimination:");
		MyDoubleNode<String[]> currentNode = headNode;
		while (currentNode != null) {
			// print the array
			for (String element : currentNode.data) {
				System.out.print(element + " ");
			}
			System.out.println("");
			// move to the next node
			currentNode = currentNode.next;
		}
	}
}