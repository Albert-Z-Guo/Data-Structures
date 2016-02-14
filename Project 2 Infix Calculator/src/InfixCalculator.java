// CSC 172 
// Project 2 InfixCalculator
// Student Name: Zunran Guo
// Student ID: 28279136
// Special thanks to suggestions and advice from 
// cordial computer science friends Joe Zhou and Daniel Diaz-Etchevehere
// All work is original. 30+ hours dedicated.

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class InfixCalculator implements InfixCalculatorInterface {

	// declare critical global variables
	public static int parseIndicator;
	public static MyNode<String> bottomNode;
	public static MyDoubleNode<String> headNode;
	public static ArrayList<String> arrayListGlobal = new ArrayList<String>();

	// operator precedents can be referred at:
	// https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html
	// note that "^" here is the exponentiation operator, not a bitwise exclusive OR
	// also note that the sine and cosine functions in this program take units of randians, not degrees
	public static String possibleOperators[] = { "!", "|", "&", "=", "<", ">", "sin", "cos", "-", "+", "%", "/", "*",
			"^", ")", "(" };
	public static String rightAssociativeOperators[] = { "(", "<", ">", "=", "&", "|", "!" };

	// create a stack and a queue for the InfixCalculator object
	public InfixCalculator() {
		bottomNode = null;
		headNode = null;
	}

	// check whether the stack is empty
	public static boolean isStackEmpty() {
		if (bottomNode == null) {
			return true;
		} else {
			return false;
		}
	}

	// push new a new item to the stack
	public static void push(String x) {
		// if headNode is empty, insert x
		if (isStackEmpty() == true) {
			MyNode<String> nodeAbove = new MyNode<String>();
			nodeAbove.data = x;
			bottomNode = nodeAbove;
		}

		// if headNode is not empty
		else {
			// search for the top node from the bottom
			MyNode<String> postCurrentNode = new MyNode<String>();
			postCurrentNode.above = bottomNode;

			while (postCurrentNode.above.above != null) {
				postCurrentNode.above = postCurrentNode.above.above;
			}

			// create a new node and insert x
			MyNode<String> nodeAbove = new MyNode<String>();
			nodeAbove.data = x;
			postCurrentNode.above.above = nodeAbove;
		}
	}

	// pop an item out of the stack
	public static String pop() {
		// if headNode is empty, return null
		if (isStackEmpty() == true) {
			return null;
		}

		// if there are more than one node
		else if (bottomNode.above != null) {
			MyNode<String> currentNode = new MyNode<String>();

			// search for the second top node from the bottom
			currentNode = bottomNode;
			while (currentNode.above.above != null) {
				currentNode = currentNode.above;
			}

			// store the data of the top node
			MyNode<String> temporaryNode = new MyNode<String>();
			temporaryNode.data = currentNode.above.data;

			// delete the top node and return its data
			currentNode.above = null;
			return temporaryNode.data;
		}

		// if there is only one node
		else {
			// store the data of the bottom node
			MyNode<String> temporaryNode = new MyNode<String>();
			temporaryNode.data = bottomNode.data;

			// delete the head node data and return its data
			bottomNode.data = null;
			return temporaryNode.data;
		}
	}

	// peek the top item of the stack
	public static String peekStack() {
		if (bottomNode == null) {
			return null;
		}

		MyNode<String> currentNode = new MyNode<String>();

		// search for the top node
		currentNode = bottomNode;
		while (currentNode.above != null) {
			currentNode = currentNode.above;
		}
		return currentNode.data;
	}

	// check whether the queue is empty
	public static boolean isQueueEmpty() {
		if (headNode == null) {
			return true;
		} else {
			return false;
		}
	}

	// enqueue a new item to the queue
	public static void enqueue(String x) {
		// if the queue is empty, create a node and insert x
		if (isQueueEmpty() == true) {
			MyDoubleNode<String> nodeNew = new MyDoubleNode<String>();
			nodeNew.data = x;
			headNode = nodeNew;
		}

		// if the queue is not empty
		else {
			MyDoubleNode<String> nodeAfter = new MyDoubleNode<String>();
			MyDoubleNode<String> currentNode = new MyDoubleNode<String>();

			// search for the last node from the beginning
			currentNode = headNode;
			while (currentNode.next != null) {
				currentNode = currentNode.next;
			}

			// create a new node and insert x
			nodeAfter.data = x;
			nodeAfter.prev = currentNode;
			currentNode.next = nodeAfter;
		}
	}

	// dequeue a new item from the queue
	public static String dequeue() {
		// if rearNode is empty, return null
		if (isQueueEmpty() == true) {
			return null;
		}

		// if there are more than one nodes
		else if (headNode.next != null) {
			// store the data of the head node
			MyDoubleNode<String> temporaryNode = new MyDoubleNode<String>();
			temporaryNode.data = headNode.data;

			// delete the head node and return its data
			headNode = headNode.next;
			headNode.prev = null;
			return temporaryNode.data;
		}

		// if there is only one node
		else {
			// store the data of the head node
			MyDoubleNode<String> temporaryNode = new MyDoubleNode<String>();
			temporaryNode.data = headNode.data;

			// delete the head node data and return its data
			headNode.data = null;
			return temporaryNode.data;
		}
	}

	// peek the first item of the queue
	public static String peekQueue() {
		return headNode.data;
	}

	// print the queue
	public static void printQueue() {
		MyDoubleNode<String> currentNode = new MyDoubleNode<String>();
		// start printing from the headNode
		currentNode = headNode;
		while (currentNode != null) {
			System.out.println(currentNode.data);
			currentNode = currentNode.next;
		}

	}

	// read the text file
	public void readFile(String inputFileName) {
		// The name of the file to open.
		String fileName = inputFileName;

		// This will reference one line at a time
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {

				// reset elementArrayGlobal
				ArrayList<String> arrayList = new ArrayList<String>();
				String[] elementArray;

				// split the line read by " "
				String[] tokens = line.split(" ");

				for (int i = 0; i < tokens.length; i++) {
					// reset print indicator
					parseIndicator = 0;

					// try to parse each string in the array
					String individualString = tokens[i];
					String[] parsedResult = individualString.split("");

					for (int j = 0; j < parsedResult.length; j++) {
						// if "(" or ")" appeared
						if (parsedResult[j].equals("(") || parsedResult[j].equals(")") || parsedResult[j].equals("!")) {
							parseIndicator = 1;
						}
					}

					// if "(" or ")" appeared, add the parsed result to the
					// array list
					if (parseIndicator == 1) {
						for (int j = 0; j < parsedResult.length; j++) {
							// System.out.println(parsedResult[j]);
							arrayList.add(parsedResult[j]);
						}
					}

					// if no "(" or ")" appeared, add the unparsed result to the
					// array list
					if (parseIndicator == 0) {
						// System.out.println(tokens[i]);
						arrayList.add(tokens[i]);
					}
				}

				// for each line read, do the following operations:
				// store all the parsed elements to elementArrayGlobal
				String[] elementArraySize = new String[arrayList.size()];
				elementArray = arrayList.toArray(elementArraySize);
				
				String[] trimmedArray = new String[elementArray.length];
				for (int i = 0; i < elementArray.length; i++)
				    trimmedArray[i] = elementArray[i].trim();
				
				// convert to post fix notation
				convertToPostfix(trimmedArray);

				// testing
				// System.out.println("The queue is ");
				// printQueue();

				// add postfix evaluation result to arrayListGlobal
				arrayListGlobal.add(postfixEvaluation());
			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'" + "\nInvalid input may exist.");
		}
	}

	// write the output file in .txt format
	public void writeFile(String outputFileName, String[] evaluationArray) {
		// The name of the file to open.
		String fileName = outputFileName;

		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(fileName);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// write the file line by line
			for (int i = 0; i < evaluationArray.length; i++) {
				bufferedWriter.write(String.format("%.2f", Double.parseDouble(evaluationArray[i])));
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

	// determine whether the operator is right associative
	
	// determine whether the operator is right associative
	public static int isRightAssociative(String symbol) {
			for (int i = 0; i < rightAssociativeOperators.length; i++) {
				if (symbol.equals(rightAssociativeOperators[i])) {
					return 1;
				}
			}
			return 0;
		}
	
		// method to return the precedence
		
	// determine the precedence of the operators
	public static int precedenceOrder(String symbol) {
		for (int i = 0; i < possibleOperators.length; i++) {
			if (symbol.equals(possibleOperators[i])) {
				return i;
			}
		}
		return -1;
	}

	
	// implement Shunting-Yard Algorithm
	public static void convertToPostfix(String[] elementArray) {
		// reset the stack
		bottomNode = null;

		// rest the queue
		headNode = null;

		// check the elementArray element by element
		for (int i = 0; i < elementArray.length; i++) {

			// reset the operatorIndicator
			int operatorIndicator = 0;

			// testing
			//System.out.println(elementArray[i]);

			// if the token is an operator other than parenthesis
			for (int j = 0; j < possibleOperators.length; j++) {
				if (elementArray[i].equals(possibleOperators[j])) {

					// If the token is a close-parenthesis [")"],
					if (elementArray[i].equals(")")) {

						// pop all the stack elements
						// and enqueue them one by one
						// until an open-parenthesis ["("] is found.

						// testing
						// System.out.println("top entry of the stack is " +
						// peekStack());
						while (peekStack() != null && peekStack().equals("(") != true) {
							// testing
							// System.out.println("enqueued " + peekStack() + "
							// until (");
							enqueue(pop());
						}

						// enqueue "("
						// System.out.println("enqueued (");
						enqueue(pop());

						// enqueue ")"
						// System.out.println("enqueued )");
						enqueue(elementArray[i]);
					}

					// if the operator is "="
					// pop all the entries in the stack
					// until we reach a right associative operator e.g. '('
					else if (elementArray[i].equals("=")) {
						while (peekStack() != null && isRightAssociative(peekStack()) != 1) {
							// testing
							// System.out.println("enqueued " + peekStack());
							enqueue(pop());
						}
						push(possibleOperators[j]);
					} else {
						// if the stack is not empty and the top entry of the
						// stack is not right associative
						// and the top entry of the stack has lower precedence
						// than the operator
						// we are about to stack, stop enqueue(pop())
						while (peekStack() != null && isRightAssociative(peekStack()) != 1
								&& precedenceOrder(peekStack()) + 1 >= precedenceOrder(possibleOperators[j])) {
							// testing
							// System.out.println("stack operator order is " +
							// precedenceOrder(peekStack()));
							// System.out.println("operator holding order is " +
							// precedenceOrder(possibleOperators[j]));
							// System.out.println("enqueued " + peekStack());
							enqueue(pop());
						}
						// testing
						// System.out.println("pushed " + possibleOperators[j]);
						push(possibleOperators[j]);
					}
					operatorIndicator = 1;
				}
			}

			// if the token is not an operator, i.e. an operand, enqueue it
			if (operatorIndicator == 0) {
				enqueue(elementArray[i]);
			}
		}

		// At the end of the input, pop every token that
		// remains on the stack and add them to the queue one by one.
		while (peekStack() != null) {
			enqueue(pop());
		}
	}

	// postfix evaluation
	public static String postfixEvaluation() {
		// declare two variables
		double a = 0, b = 0, c = 0;
		String d = null;

		// repeat until the queue is empty
		while (peekQueue() != null) {
			// reset the operatorIndicator
			int operatorIndicator = 0;

			for (int j = 0; j < possibleOperators.length; j++) {
				// if the token is an operator
				if (peekQueue() != null && peekQueue().equals(possibleOperators[j])) {

					operatorIndicator = 1;

					// if the operator is "+"
					if (peekQueue().equals("+")) {
						a = Double.parseDouble(pop());
						b = Double.parseDouble(pop());
						c = b + a;
					}
					// if the operator is "-"
					else if (peekQueue().equals("-")) {
						a = Double.parseDouble(pop());
						b = Double.parseDouble(pop());
						c = b - a;
					}
					// if the operator is "*"
					else if (peekQueue().equals("*")) {
						a = Double.parseDouble(pop());
						b = Double.parseDouble(pop());
						c = b * a;
					}
					// if the operator is "/"
					else if (peekQueue().equals("/")) {
						a = Double.parseDouble(pop());
						b = Double.parseDouble(pop());
						c = b / a;
					}
					// if the operator is "%"
					else if (peekQueue().equals("%")) {
						a = Double.parseDouble(pop());
						b = Double.parseDouble(pop());
						c = b % a;
					}
					// if the operator is "sin"
					else if (peekQueue().equals("sin")) {
						a = Double.parseDouble(pop());
						c = Math.sin(a);
					}
					// if the operator is "%"
					else if (peekQueue().equals("cos")) {
						a = Double.parseDouble(pop());
						c = Math.cos(a);
						;
					}
					// if the operator is "^"
					else if (peekQueue().equals("^")) {
						a = Double.parseDouble(pop());
						b = Double.parseDouble(pop());
						c = Math.pow(b, a);
					}
					// if the operator is ">"
					else if (peekQueue().equals(">")) {
						a = Double.parseDouble(pop());
						b = Double.parseDouble(pop());
						if (b > a) {
							c = 1;
						} else {
							c = 0;
						}
					}
					// if the operator is "<"
					else if (peekQueue().equals("<")) {
						a = Double.parseDouble(pop());
						b = Double.parseDouble(pop());
						if (b < a) {
							c = 1;
						} else {
							c = 0;
						}
					}
					// if the operator is "="
					else if (peekQueue().equals("=")) {
						a = Double.parseDouble(pop());
						b = Double.parseDouble(pop());
						if (b == a) {
							c = 1;
						} else {
							c = 0;
						}
					}
					// if the operator is "!"
					else if (peekQueue().equals("!")) {
						a = Double.parseDouble(pop());
						if (a == 0) {
							c = 1;
						} else {
							c = 0;
						}
					}
					// if the operator is "(" or ")"
					else if (peekQueue().equals("(") || peekQueue().equals(")")) {
						// dequeue the next operator in the queue which would be
						// ")"
						dequeue();
						c = Double.parseDouble(pop());
					}
					// if the operator is "&"
					else if (peekQueue().equals("&")) {
						a = Double.parseDouble(pop());
						b = Double.parseDouble(pop());
						if ((a == 1 && b == 1) || (a == 0) && (b == 0)) {
							c = 1;
						} else {
							c = 0;
						}
					}
					// if the operator is "|"
					else if (peekQueue().equals("|")) {
						a = Double.parseDouble(pop());
						b = Double.parseDouble(pop());
						if (a == 1 || b == 1) {
							c = 1;
						} else {
							c = 0;
						}
					}

					d = Double.toString(c);
					// push the evaluated result from the popped element(s) back
					// to stack
					push(d);

					// dequeue the used operator
					dequeue();
				}
			}

			// if the token is not an operator, i.e. an operand
			if (operatorIndicator == 0) {
				// push it into the stack
				push(dequeue());
			}

		}
		// testing
		// System.out.println("a is " + a);
		// System.out.println("b is " + b);
		// System.out.println("The operation result is " + c + "\n");
		return d;
	}

	public static void main(String[] args) {
		InfixCalculator testInstance = new InfixCalculator();
		if (args.length >= 0) {

			arrayListGlobal.clear();

			// read the file
			testInstance.readFile(args[0]);

			// store converted results to a string array elementArraySizeGlobal
			String[] elementArraySizeGlobal = new String[arrayListGlobal.size()];
			elementArraySizeGlobal = arrayListGlobal.toArray(elementArraySizeGlobal);

			// write the file
			testInstance.writeFile(args[1], elementArraySizeGlobal);
			System.out.println("Text File named " + args[1] + " is created.");
		}
	}
}
