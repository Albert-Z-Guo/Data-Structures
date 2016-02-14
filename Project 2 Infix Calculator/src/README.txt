CSC 172
Project 2 InfixCalculator

Student Name: Zunran Guo
Student ID: 28279136

File Description:
public static boolean isStackEmpty() checks the whether stack is empty
public static void push(String x) pushes a new item into the stack
public static String pop() pops an item out of the stack
public static String peekStack() peeks the top item of the stack
public static boolean isQueueEmpty() checks whether the queue is empty
public static void enqueue(String x) enqueues a new item to the queue
public static String dequeue() dequeues an item form the queue
public static String peekQueue() peeks the first item of the queue
public static String convertStringArrayToString(String[] tokens) converts string array to string
public void readFile(String inputFileName) reads the text file
public void writeFile(String outputFileName, String[] evaluationArray) writes the text file
public static void printQueue() prints the queue
public static int isRightAssociative(String symbol) determines whether the operator is right associative
public static int precedenceOrder(String symbol) determines the precedence order of the operator
public static void convertToPostfix(String[] elementArray) implements the Shunting-Yard Algorithm which converts infix notation to postfix notation
public static String postfixEvaluation() evaluates the postfix notation

Integrated Development Environment:
Eclipse

Summary:
For this project, the Java program reads in a series of infix expressions from a plain text file, converts the expressions to postfix notation, evaluates the postfix expressions, and saves the resulting answers to a new text file.

Note that:
The locations of the input and output files will be supplied to your program via the command line. The first command line argument will be the location of the input file (containing infix expressions), and the second argument will be the location where your postfix evaluations should be stored. For example, if your main method were in a class called InfixCalculator, your program should be run as:java InfixCalculator infix_expr_short.txt my_eval.txt
