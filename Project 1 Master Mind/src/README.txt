CSC 172
Project 1 MasterMind

Student Name: Zunran Guo
Student ID: 28279136

File Description:
MyDoubleNode.java contains the node structure of a doubly linked list.
MasterMindInterface.java is the interface of Mastermind.java.
MasterMind.java contains the following defined methods:
public void newGame() initializes the game
public String[] nextMove() return the next guess of the program
public boolean deleteIndicator(String[] stringArrayInNode) indicates whether to delete a possibility form the linked list
public void Response() deletes the impossibilities from the linked list
public boolean isEmpty() checks whether the linked list is empty
public void insert() inserts the item to the linked list
public void printList() prints the linked list from the beginning

Integrated Development Environment:
Eclipse

Summary:
Mastermind is a code breaker that guesses the user’s token. Based on variety of colors (1 to 6) and positions (1 to 6) given by the user, the program generates all the possibilities and store each case in a node of a linked list. Based on the updating feedback the user gives each time, the program deletes all the impossibilities in the linked list and return a remaining possibility from the beginning of the list, until it has deleted all the possibilities remaining in the list (i.e. the user may have made a mistake) or until it correctly guessed the user’s token. The algorithm of deletion is implemented in public boolean deleteIndicator(String[] stringArrayInNode). In essence the algorithm compares the cases of colorsRight_positionsWrong and colorsRight_positionsRight between the guess and all the remaining possibilities. The algorithm will keep the matched cases, one of which must be the user’s token and delete all the other impossibilities. The elimination cycle continues until all the possibilities run out or the correct token is guessed. In order to achieve a high speed of elimination, the first guessed token is always in 1 or 2 colors, based on random selection of possible colors.
