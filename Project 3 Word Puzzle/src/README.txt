CSC 172
Project 3 Word Puzzle

Student Name: Zunran Guo
Student ID: 28279136

File Description:
Hash.java implements the interface Hashing.java. Hash.java is the class created to realize the functionality of a hash table for strings, along with hashing function of extreme speed optimization, adapted from Lab 17 Hashing, which is also an original work written from scratch by myself.

wordFiner.java is the class created to realize the functionality of a fast puzzle solver in 8 directions (up, down, left, right, left-up, left-down, right-up, right-down). The methods this class contains are listed as the following:
public void loadDictionary(String dictionaryFileName) loads the dictionary (wordlist.txt) which is originally from linux.words library and screens the finding results
public void readPuzzle(String puzzleFileNmae) reads the puzzles from puzzle.txt
public void printPuzzle() prints the puzzle
public void printFoundWords() prints out the found words from the puzzle
public String convertToString converts characters in an arraylist to a string
public boolean checkDuplicate(String x) checks duplicates of the found words
public void indentifyWords()identifies words in the puzzle
public void writeFile(String outputFileName) exports the found words in text file format

Integrated Development Environment:
Eclipse

Summary:
For this project, the program reads in puzzle in text file format and identifies all words contained in all 8 possible directions. The identification process is to match all possible characters in different length in different directions with the English dictionary, loaded from wordlist.txt. Once a word is matched (no duplicate allowed), it is stored and all words found are sorted in alphabetical order and written in a new text file, exported in the end. 

Note that:
The locations of the input and output files will be supplied to your program via the command line. The first command line argument will be the location of the input word list file, the second argument will be the location of the puzzle file, and the third argument is where your solutions should be stored. For example, if your main method were in a class called WordFinder, your program should be run as:java WordFinder wordlist.txt puzzle.txt foundWords.txt

