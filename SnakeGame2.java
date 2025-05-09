import java.util.Random;
import java.util.Scanner;
import java.io.PrintWriter;
/**
 *	Snake Game - Controls the entire game: initializing the board,
 *  handling user input(w/a/s/d), moving the snake,
 *  placing targets, detecting collisions, tracking score,
 *  and saving/loading the game state.
 * 
 *	@author	Jonathan Chen
 *	@since	May 5, 2025
 */
public class SnakeGame{
	private Snake snake;
	private SnakeBoard board;
	private Coordinate target;
	private int score;
	/* Constructor */
	public SnakeGame(){
		board = new SnakeBoard(20, 26);
		snake = new Snake(5, 8);
		score = 0;
		placeNewTarget();
	}
	public static void main(String[] args){
		SnakeGame game = new SnakeGame();
		game.printIntroduction();
		boolean playing = true;
		while(playing){
			game.board.printBoard(game.snake, game.target);
			String input = Prompt.getString("Score: " + game.score 
			+ " (w - North, s - South, d - East, a - West, h - Help) ").toLowerCase();
			switch(input){
				case "w": case "a": case "s": case "d":
					playing = game.moveSnake(input);
					break;
				case "h":
					game.helpMenu();
					break;
				case "f":
					game.saveGame("snakeGameSave.txt");
					break;
				case "r":
					game.loadGame("snakeGameSave.txt");
					break;
				case "q":
					String answer = Prompt.getString("Do you really want to quit? (y or n) ").toLowerCase();
					if(answer.equals("y")){
						System.out.println("Thanks for playing SnakeGame!!!");
						playing = false;
					}
					else{
						playing = true;
					}
					break;
				default:
					System.out.println("Invalid command.");
			}
		}
		System.out.println("Game Over! Final Score: " + game.score);
	}
	/** Move the snake and return false if game over */
	public boolean moveSnake(String direction){
		Coordinate head = snake.get(0).getValue();
		int row = head.getRow();
		int col = head.getCol();
		switch(direction){
			case "w": row--; break;
			case "s": row++; break;
			case "a": col--; break;
			case "d": col++; break;
			default:
				System.out.println("Invalid direction!");
				return true;
		}
		Coordinate newHead = new Coordinate(row, col);
		// Check wall collision
		if(row < 0 || row >= board.getHeight() || col < 0 || col >= board.getWidth()){
			System.out.println("You hit a wall!");
			return false;
		}
		// Check self collision
		if(snake.contains(newHead)){
			System.out.println("You ran into yourself!");
			return false;
		}
		snake.addFirst(newHead);
		if(newHead.equals(target)){
			score++;
			placeNewTarget();
		}
		else{
			snake.remove(snake.size() - 1);
		}
		return true;
	}
	/** Places a new target not on the snake */
	public void placeNewTarget(){
		Random rand = new Random();
		boolean tOrF = true;
		while(tOrF){
			int row = rand.nextInt(board.getHeight());
			int col = rand.nextInt(board.getWidth());
			Coordinate t = new Coordinate(row, col);
			if(!snake.contains(t)){
				target = t;
				tOrF = false;
			}
		}
	}
	/** Save the game to file */
	public void saveGame(String filename){
		PrintWriter out = FileUtils.openToWrite(filename);
		out.println(score);
		out.println(snake.size());
		for(int i = 0; i < snake.size(); i++){
			Coordinate c = snake.get(i).getValue();
			out.println(c.getRow() + " " + c.getCol());
		}
		out.println(target.getRow() + " " + target.getCol());
		out.close();
		System.out.println("Game saved to " + filename);
	}
	/** Load game from file */
	public void loadGame(String filename){
		Scanner in = FileUtils.openToRead(filename);
		score = Integer.parseInt(in.nextLine());
		int size = Integer.parseInt(in.nextLine());
		snake.clear();
		for(int i = 0; i < size; i++){
			int row = in.nextInt();
			int col = in.nextInt();
			snake.add(new Coordinate(row, col));
		}
		int tRow = in.nextInt();
		int tCol = in.nextInt();
		target = new Coordinate(tRow, tCol);
		in.close();
		System.out.println("Game loaded from " + filename);
	}
	/**	Print the game introduction	*/
	public void printIntroduction(){
		System.out.println("  _________              __            ________");
		System.out.println(" /   _____/ ____ _____  |  | __ ____  /  _____/_____    _____   ____");
		System.out.println(" \\_____  \\ /    \\\\__  \\ |  |/ // __ \\/   \\  ___\\__  \\  /     \\_/ __ \\");
		System.out.println(" /        \\   |  \\/ __ \\|    <\\  ___/\\    \\_\\  \\/ __ \\|  Y Y  \\  ___/");
		System.out.println("/_______  /___| (____  /__|_ \\\\___  >\\______ (____  /__|_|  /\\___  >");
		System.out.println("        \\/     \\/     \\/     \\/    \\/        \\/     \\/      \\/     \\/");
		System.out.println("\nWelcome to SnakeGame!");
		System.out.println("\nA snake @****** moves around a board " +
							"eating targets \"+\".");
		System.out.println("Each time the snake eats the target it grows " +
							"another * longer.");
		System.out.println("The objective is to grow the longest it can " +
							"without moving into");
		System.out.println("itself or the wall.");
		System.out.println("\n");
	}
	/** Print help menu */
	public void helpMenu(){
		System.out.println("\nCommands:");
		System.out.println("  w - move north");
		System.out.println("  s - move south");
		System.out.println("  d - move east");
		System.out.println("  a - move west");
		System.out.println("  h - help");
		System.out.println("  f - save game");
		System.out.println("  r - restore game");
		System.out.println("  q - quit");
		Prompt.getString("Press enter to continue");
	}
}
/**
 *	SnakeBoard - Manages the visual grid of the game using a 2D char[][] array,
 *  where the snake and the target are rendered as ASCII characters.
 * 
 *	@author Jonathan Chen
 *	@since May 5, 2025
 */
public class SnakeBoard{
	private char[][] board;
	/** Constructor to initialize board */
	public SnakeBoard(int height, int width){
		board = new char[height][width];
	}
	/** Print the board with snake and target */
	public void printBoard(Snake snake, Coordinate target){
		int height = board.length;
		int width = board[0].length;
		// Step 1: Clear board
		for (int r = 0; r < height; r++){
			for (int c = 0; c < width; c++){
				board[r][c] = ' ';
			}
		}
		// Step 2: Place snake
		if (!snake.isEmpty()){
			Coordinate head = snake.get(0).getValue();
			board[head.getRow()][head.getCol()] = '@';
			for (int i = 1; i < snake.size(); i++){
				Coordinate body = snake.get(i).getValue();
				board[body.getRow()][body.getCol()] = '*';
			}
		}
		// Step 3: Place target
		board[target.getRow()][target.getCol()] = '+';
		// Step 4: Print top border
		System.out.print("+");
		for (int i = 0; i < width; i++) System.out.print("-");
		System.out.println("+");

		// Step 5: Print board rows
		for (int r = 0; r < height; r++){
			System.out.print("|");
			for (int c = 0; c < width; c++){
				System.out.print(board[r][c]);
			}
			System.out.println("|");
		}
		// Step 6: Print bottom border
		System.out.print("+");
		for (int i = 0; i < width; i++) System.out.print("-");
		System.out.println("+");
		// Optional: Print score (can move to SnakeGame if preferred)
	}
	/** Return board height */
	public int getHeight(){
		return board.length;
	}
	/** Return board width */
	public int getWidth(){
		return board[0].length;
	}
}
/**
 * Snake class - A linked list of Coordinates representing the snake.
 * The head is the front of the list.
 */
public class Snake extends SinglyLinkedList<Coordinate>{
    /** 
     * Constructor that creates a Snake of length 5 (vertical),
     * with head at (startRow, startCol) and body growing downward.
     */
    public Snake(int startRow, int startCol){
        for (int i = 4; i >= 0; i--){
            this.addFirst(new Coordinate(startRow + i, startCol));
        }
    }
}
/**
 * A coordinate on a grid. Integer row and column values.
 * Used to represent positions on the SnakeBoard.
 */
public class Coordinate implements Comparable<Coordinate>{
	private int row, col;
	public Coordinate(int row, int col){
		this.row = row;
		this.col = col;
	}
	public int getRow(){ return row; }
	public int getCol(){ return col; }
	@Override
	public int compareTo(Coordinate other){
		if (this.row != other.row) return this.row - other.row;
		return this.col - other.col;
	}
	@Override
	public boolean equals(Object other){
		if (!(other instanceof Coordinate)) return false;
		return this.compareTo((Coordinate) other) == 0;
	}
	@Override
	public String toString(){
		return "[ " + row + ", " + col + " ]";
	}
}
import java.util.NoSuchElementException;

/**
 *	SinglyLinkedList - Implements a basic singly linked list to store
 *  elements (used for tracking the snake's body as a list
 *  of Coordinate objects).
 *
 *	@author	Jonathan Chen
 *	@since	April 29, 2025
 */
public class SinglyLinkedList<E extends Comparable<E>>{
	/* Fields */
	private ListNode<E> head, tail; // head and tail pointers to list
	/* No-args Constructors */
	public SinglyLinkedList(){}
	/** Copy constructor */
	public SinglyLinkedList(SinglyLinkedList<E> oldList){
		if(oldList.head == null){
			this.head = this.tail = null;
		}
		else{
			ListNode<E> currentOld = oldList.head;
			this.head = new ListNode<>(currentOld.getValue());
			ListNode<E> currentNew = this.head;
	
			currentOld = currentOld.getNext();
			while(currentOld != null){
				ListNode<E> newNode = new ListNode<>(currentOld.getValue());
				currentNew.setNext(newNode);
				currentNew = newNode;
				currentOld = currentOld.getNext();
			}
			this.tail = currentNew;
		}
	}
	/**	Clears the list of elements */
	public void clear(){
		head = null;
		tail = null;
	}
	/**	Add the object to the end of the list
	 *	@param obj		the object to add
	 *	@return			true if successful; false otherwise
	 */
	public boolean add(E obj){
		ListNode<E> newNode = new ListNode<>(obj);
		if(head == null){
			head = tail = newNode;
		}
		else{
			tail.setNext(newNode);
			tail = newNode;
		}
		return true;
	}
	/**	Add the object at the specified index
	 *	@param index		the index to add the object
	 *	@param obj			the object to add
	 *	@return				true if successful; false otherwise
	 *	@throws NoSuchElementException if index does not exist
	 */
	public boolean add(int index, E obj){
		if(index < 0 || index > size()){
			throw new NoSuchElementException();
		}
		ListNode<E> newNode = new ListNode<>(obj);
		if(index == 0){
			newNode.setNext(head);
			head = newNode;
			if(tail == null) tail = head;
		}
		else{
			ListNode<E> prev = get(index - 1);
			newNode.setNext(prev.getNext());
			prev.setNext(newNode);
			if(newNode.getNext() == null){
				tail = newNode;
			}
		}
		return true;
	}
	/**	@return the number of elements in this list */
	public int size(){
		int count = 0;
		ListNode<E> current = head;
		while(current != null){
			count++;
			current = current.getNext();
		}
		return count;
	}
	/**	Return the ListNode at the specified index
	 *	@param index		the index of the ListNode
	 *	@return				the ListNode at the specified index
	 *	@throws NoSuchElementException if index does not exist
	 */
	public ListNode<E> get(int index){
		if(index < 0){
			throw new NoSuchElementException();
		}
		ListNode<E> current = head;
		for(int i = 0; i < index; i++){
			if(current == null){
				throw new NoSuchElementException();
			}
			current = current.getNext();
		}
		if(current == null){
			throw new NoSuchElementException();
		}
		return current;
	}
	/**	Replace the object at the specified index
	 *	@param index		the index of the object
	 *	@param obj			the object that will replace the original
	 *	@return				the object that was replaced
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E set(int index, E obj){
		ListNode<E> target = get(index);
		E oldValue = target.getValue();
		target.setValue(obj);
		return oldValue;
	}
	/**	Remove the element at the specified index
	 *	@param index		the index of the element
	 *	@return				the object in the element that was removed
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E remove(int index){
		if(index < 0 || head == null){
			throw new NoSuchElementException();
		}
		if(index == 0){
			E value = head.getValue();
			head = head.getNext();
			if(head == null) tail = null;
			return value;
		}
		ListNode<E> prev = get(index - 1);
		ListNode<E> toRemove = prev.getNext();
		if(toRemove == null){
			throw new NoSuchElementException();
		}
		prev.setNext(toRemove.getNext());
		if(toRemove == tail){
			tail = prev;
		}
		return toRemove.getValue();
	}
	/**	@return	true if list is empty; false otherwise */
	public boolean isEmpty(){
		return head == null;
	}
	/**	Tests whether the list contains the given object
	 *	@param object		the object to test
	 *	@return				true if the object is in the list; false otherwise
	 */
	public boolean contains(E object){
		ListNode<E> current = head;
		while(current != null){
			if(current.getValue().equals(object)) return true;
			current = current.getNext();
		}
		return false;
	}
	/**	Return the first index matching the element
	 *	@param element		the element to match
	 *	@return				if found, the index of the element; otherwise returns -1
	 */
	public int indexOf(E object){
		int index = 0;
		ListNode<E> current = head;
		while(current != null){
			if(current.getValue().equals(object)) return index;
			current = current.getNext();
			index++;
		}
		return -1;
	}
	/**	Prints the list of elements */
	public void printList(){
		ListNode<E> ptr = head;
		while(ptr != null){
			System.out.print(ptr.getValue() + "; ");
			ptr = ptr.getNext();
		}
	}
	/** Add an element to the front of the list */
public void addFirst(E obj) {
	ListNode<E> newNode = new ListNode<>(obj);
	newNode.setNext(head);
	head = newNode;
	if (tail == null) {
		tail = head;
	}
}

}
/**
 *	A List Node for a singly-linked list.
 *
 *	@author	Mr Greenstein
 */
public class ListNode<E extends Comparable<E>>{
	private E value;  // the element stored in this node
	private ListNode<E> next; // reference to next node in List
	/**
	 *	post: constructs a new element with object initValue,
	 *	followed by next element
	 *	@param initValue	the Object to store in this node
	 *	@param initNext		the link to the next ListNode
	 */
	public ListNode(E initValue, ListNode<E> initNext){
		value = initValue;
		next = initNext;
	}
	/**
	 *	post: constructs a new element with object initValue,
	 *	followed by a reference to null
	 *	@param initValue	the Object to store in this node
	 */
	public ListNode(E initValue){
		this(initValue, null);
	}
	
	/**
	 *	post: returns value associated with this element
	 *	@return  Object in this node
	 */
	public E getValue(){
		return value;
	}
	/**
	 *	post: returns reference to next value in list
	 *	@return  the next ListNode in the list
	 */
	public ListNode<E> getNext(){
		return next;
	}
	/**
	 *	@param theNewValue  the new Object value to store in this node
	 */
	public void setValue(E theNewValue){
		value = theNewValue;
	}
	/**
	 *	post: sets reference to new next value
	 *	@param theNewNext  the new next ListNode
	 */
	public void setNext(ListNode<E> theNewNext){
		next = theNewNext;
	}
}
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 *	Prompt.java - Uses BufferedReader.
 *	Provides utilities for user input.  This enhances the BufferedReader
 *	class so our programs can recover from "bad" input, and also provides
 *	a way to limit numerical input to a range of values.
 *
 *	The advantages of BufferedReader are speed, synchronization, and piping
 *	data in Linux.
 *
 *	@author	Jonathan Chen 
 *	@since	September 6, 2024
 */

public class Prompt
{
	// BufferedReader variables
	
	private static InputStreamReader streamReader = new InputStreamReader(System.in);
	private static BufferedReader buffReader = new BufferedReader(streamReader);

	/**
	 *	Prompts user for string of characters and returns the string.
	 *	@param ask  The prompt line
	 *	@return  	The string input
	 */
	public static String getString (String ask)
	{
		System.out.print(ask + " -> ");
		String input = "";
		try
		{
			input = buffReader.readLine();
		}
		catch(IOException e)
		{
			System.err.println("ERROR: BufferedReader could not read line.");
		}
	
		return input;
	}
	
	/**
	 *	Prompts the user for a character and returns the character.
	 *	@param ask  The prompt line
	 *	@return  	The character input
	 */
	public static char getChar (String ask)
	{
		return ' ';
	}
	
	/**
	 *	Prompts the user for an integer and returns the integer.
	 *	@param ask  The prompt line
	 *	@return  	The integer input
	 */
	public static int getInt (String ask)
	{
		int val = 0;
		boolean found = false;
		while(! found) 
		{
			String str = getString(ask);
			try
			{
				val = Integer.parseInt(str);
				found = true;
			}
			catch (NumberFormatException e)
			{
				found = false;
			}
		}
		return val;	
	}
	
	/**
	 *	Prompts the user for an integer using a range of min to max,
	 *	and returns the integer.
	 *	@param ask  The prompt line
	 *	@param min  The minimum integer accepted
	 *	@param max  The maximum integer accepted
	 *	@return  	The integer input
	 */
	public static int getInt (String ask, int min, int max)
	{
		int val = 0;
		do
		{
			val = getInt(ask + " (" + min + ", " + max + ")");
		}while(val < min || val > max);
		return val;
	}
	
	/**
	 *	Prompts the user for a double and returns the double.
	 *	@param ask  The prompt line
	 *	@return  The double input
	 */
	public static double getDouble (String ask)
	{
		double dec = 0.0;
		boolean tOrF = false;
		while(! tOrF)
		{
			String str = getString(ask);
			try
			{
				dec = Double.parseDouble(str);
				tOrF = true;
			}
			catch (NumberFormatException e)
			{
				tOrF = false;
			}
		}
		return dec;
	}
	
	/**
	 *	Prompts the user for a double and returns the double.
	 *	@param ask  The prompt line
	 *	@param min  The minimum double accepted
	 *	@param max  The maximum double accepted
	 *	@return  The double input
	 */
	public static double getDouble (String ask, double min, double max)
	{
		double dec2 = 0.0;
		do
		{
			dec2 = getDouble(ask + " (" + min + ", " + max + ")");
		}while(dec2 < min || dec2 > max);
		return dec2;
	}
}
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 *	File Utilities for reading and writing
 *
 *	@author Jonathan Chen
 *	@since	August 23, 2024
 */
public class FileUtils {
	/**
	 *	Opens a file to read using the Scanner class.
	 * 	@param fileName		name of the file to open
	 * 	@return				the Scanner object to the file
	 */
	public static java.util.Scanner openToRead(String fileName){
		Scanner input = null;
		try{
			input = new java.util.Scanner(new java.io.File(fileName));
		} 
		catch(java.io.FileNotFoundException e){
			System.err.println("ERROR: Cannot open " + fileName + "for reading.");
			System.exit(1);
		}
		return input;
	}
	/**
	 * 	Opens a file to write using the PrintWriter class.
	 * 	@param fileName		name of the file to open
	 * 	@return				the PrintWriter object to the file
	 */
	public static PrintWriter openToWrite(String fileName) {
		PrintWriter output = null;
		try{
			output = new PrintWriter(new File(fileName));
		}
		catch (FileNotFoundException e){
			System.err.println("ERROR: Cannot open " + fileName + " for writing.");
			System.exit(2);
		}
		return output;
	}
}
