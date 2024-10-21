import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
/**
 *	Wordle.java
 *
 *	The Wordle.java program is a word puzzle game where players have
 *  six chances to guess a secret five-letter word. After each guess,
 *  the game provides feedback by color-coding the letters: green if the
 *  letter is in the correct position, yellow if the letter is in the
 *  word but the wrong position, and gray if the letter is not in the
 *  word. The objective is to use these clues to guess the word within
 *  the six attempts. Wordle encourages logic, vocabulary, and strategy
 *  to solve the puzzle.
 *
 *	@author	Scott DeRuiter and David Greenstein and Jonathan Chen
 *	@version	1.0
 *	@since	October 7, 2024
 */ 
public class Wordle{
	/**	A String to store the word that the player is trying to find. */
	private String word;
	/**	An array of String to store the guesses that have been made. */
	private String [] wordGuess;
	/**	A String to store the letters in the current guess. 
	 * Can have from 0 to 5 chars*/
	private String letters;
	/**	File that contains 5-letter words to find. */
	private final String WORDS5 = "words5.txt";
	/**	File that contains 5-letter words allowed for user guesses. 
	 *(bigger file) */
	private final String WORDS5_ALLOWED = "words5allowed.txt";
	/**	A variety of boolean variables to turn things on and off. 
	 * These include:
	 *	isshow				-	when true, will print the current word
	 * 							to the terminal
	 *	readyForKeyInput    -	when true, will accept keyboard input,
	 * 							when false,
	 *							will not accept keyboard input.
	 *	readyForMouseInput  -	when true, will accept mouse input,
	 * 							when false, will not accept mouse input.
	 *	isactiveGame          -	when false, will only accept action on
	 * 							the RESET button.
	 */
	private boolean isshow, readyForKeyInput, readyForMouseInput, isactiveGame;
	/**  An array to determine how to color the keyboard at the bottom
	 *  of the gameboard. 0 for not checked yet, 1 for no match, 2 for
	 *  partial, 3 for exact
	 */
	private int [] keyBoardColors;						
	/** 
	 *	Creates a Wordle object. A constructor. Initializes all of the
	 *  variables by calling the method initAll.
	 *	@param testWord		if this String is found in words5allowed.txt,
	 * 						it will be used to set word.
	 *	This method is complete.
	 */
	public Wordle(String showIt, String testWord){
		if(showIt.equalsIgnoreCase("show")){
			isshow = true;
		}
		else{
			isshow = false;
		}
		initAll(testWord);
	}
	/** 
	 *	Initializes all fields.  Calls openFileAndChooseWord to choose
	 *  the word. Sets all of the keyboard colors to light gray to start.
	 *	@param testWord		if this String is found in words5allowed.txt,
	 * 						it will be used to set word.
	 *	This method is complete.
	 */
	public void initAll(String testWord){
		wordGuess = new String[6];
		for(int i = 0; i < wordGuess.length; i++){
			wordGuess[i] = new String("");
		}
		letters = "";
		readyForKeyInput = true;
		isactiveGame = true;
		readyForMouseInput = false;
		keyBoardColors = new int[29];
		word = openFileAndChooseWord(WORDS5, testWord);		
	}
	/**
	 *	The main method, to run the program. The constructor is called,
	 *  so that all of the fields are initialized. The canvas is set up,
	 *  and the GUI(the game of Wordle) runs.
	 *	This method is complete.
	 */
	public static void main(String[] args){
		String testWord = "";
		String showIt = "";
		// Determines if args[0] and args[1] are set
		// args[0] is "show" which means to show the word chosen
		// args[1] is a word which is used as the chosen word
		// Check if args has elements
		if(args.length > 0){
			// args[0] is "show" if the word should be shown
			showIt = args[0];
		}
		if(args.length > 1){
			// args[1] is a word to be used as the chosen word
			testWord = args[1];
		}
		Wordle run = new Wordle(showIt, testWord);
		run.setUpCanvas();
		run.playGame();
	}
	/**
	 *	Sets up the canvas.  Enables double buffering so that the
	 *  gameboard is drawn offscreen first, then drawn to the gameboard
	 *  when everything is ready(with the show method).
	 *	This method is complete.
	 */
	public void setUpCanvas(){
		StdDraw.setCanvasSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		StdDraw.setXscale(0, Constants.SCREEN_WIDTH);
		StdDraw.setYscale(0, Constants.SCREEN_HEIGHT);
		StdDraw.enableDoubleBuffering();
	}
	/**
	 *	Runs the game.  An endless loop is created, constantly cycling
	 *  and looking for user input.
	 *	This method is complete.
	 */
	public void playGame(){
		boolean keepGoing = true;
		while(keepGoing){
			if(isactiveGame){
				drawPanel();
			}
			update();
		}
	}
	/** 
	 *	If the testWord is valid, it is used as the "goal word".  If it
	 *  is not, then the text file is opened, and a word is chosen at
	 *  random from the list to be the "goal word".  If the field
	 *  variable show is true, it will print the chosen word to the
	 *  terminal window. Be sure to close file when you are done.
	 *	@param inFileName		this file is to be opened, and a random
	 * 							word is to be chosen from it.
	 *	@param testWord			if this String is found in words5allowed.txt
	 * 							, it will be used to set word.
	 *	@return					the word chosen as the "goal word".
	 *	This method is complete.
	 */
	public String openFileAndChooseWord(String inFileName, String testWord){
        String result = testWord;
        List<String> words = new ArrayList<>();
        // Use FileUtils to open the file
        Scanner reader = FileUtils.openToRead(inFileName);
        // If the file could not be opened, return a default value
        //(or handle it in another way)
        if(reader == null){
            System.err.println("ERROR: File could not be opened.");
            return "ERROR"; // Return a default or error value
        }
        // Load all words into the list and check for the testWord
        boolean isValid = false;
        while(reader.hasNextLine()){
            String line = reader.nextLine().trim();
            words.add(line);
            if(line.equalsIgnoreCase(testWord)){
                isValid = true;
            }
        }
        // If testWord is not valid, choose a random word from the list
        if(!isValid){
            Random rand = new Random();
            result = words.get(rand.nextInt(words.size()));
        }
        // show the chosen word if isshow is true
        if(isshow){
            System.out.println("Chosen word: " + result);
        }
        // Close the Scanner to release file resources
        reader.close();
        return result;
    }
	/** 
	 *	Checks to see if the word in the parameter list is found in the
	 *  text file words5allowed.txt
	 *	Returns true if the word is in the file, false otherwise.
	 *	@param possibleWord       the word to looked for in words5allowed.txt
	 *	@return                   true if the word is in the text file,
	 * 							  false otherwise
	 *	This method is complete
	 */
	public boolean inAllowedWordFile(String possibleWord){
		Scanner fileScanner = null;
		boolean isOpened = false;
	    // Convert possibleWord to uppercase for making matching more easier
	    possibleWord = possibleWord.toUpperCase();
	    // Open the file using FileUtils
	    fileScanner = FileUtils.openToRead(WORDS5_ALLOWED);
	    if(fileScanner == null){
	        // Return false if the file couldn't be opened
	        return isOpened;
	    }
	    // Check each line in the file
	    while(fileScanner.hasNextLine()){
	        String wordInFile = fileScanner.nextLine().trim().toUpperCase();
	        if(wordInFile.equals(possibleWord)){
	            fileScanner.close();  // Close the file before returning
	            isOpened = true;
	            return isOpened;
	        }
	    }
	    // Close the file if no match is found
	    fileScanner.close();
	    return isOpened;
	}
	/** 
	 *	Processes the guess made by the user.  This method will only be
	 *  called if the field variable letters has length 5. The guess in
	 *  letters will need to be checked against the words in
	 *  words5allowed.txt. The method inAllowedWordFile will be called
	 *  for this task.  If the guess in letters does not exist in the
	 *  text file, a message is displayed to the user in the form of a
	 *  JOptionPane with JDialog.
	 *	This method is complete
	 */
	public void processGuess(){
	    // Convert guess to uppercase
	    letters = letters.toUpperCase();
	    // Check if the guess is in words5allowed.txt using inAllowedWordFile
	    if(inAllowedWordFile(letters)){
	        // Find the next empty slot in wordGuess to store the valid guess
	        int guessNumber = -1;
	        for(int i = 0; i < wordGuess.length; i++){
	            if(wordGuess[i] == null || wordGuess[i].isEmpty()){
	                guessNumber = i;
	                i = wordGuess.length;
	            }
	        }
	        // If there's an empty slot, store the guess
	        if(guessNumber != -1){
	            wordGuess[guessNumber] = letters;
	            // Determine colors for each letter in the guess
	            determineLetterColors(guessNumber);
	            letters = "";
	        }
	        else{
	            System.out.println("No more space for guesses.");
	        }
	    }
	    else{
	        // show error dialog if the guess is not in words5allowed.txt
	        JOptionPane.showMessageDialog(null, letters + " is not on the"
	        + " word list", "INVALID INPUT", JOptionPane.ERROR_MESSAGE);
	    }
	}
	/**
	 * This method evaluates the guessed word, assigns a color code to
	 *  each letter based on its accuracy, and updates keyboard display
	 *  colors to guide the player visually through the guessing process.
	 */
	public void determineLetterColors(int guessIndex){
	    String guess = wordGuess[guessIndex];
	    char[] guessChars = guess.toCharArray();
	    char[] goalChars = word.toCharArray();
	    // 0: default, 1: no match, 2: partial, 3: exact
	    int[] colors = new int[5];
	    // First pass: Check for exact matches
	    for(int i = 0; i < 5; i++){
	        if(guessChars[i] == goalChars[i]){
	            colors[i] = 3; // Green for exact match
	            goalChars[i] = '*'; // Mark letter in goal word as used
	            guessChars[i] = '#'; // Mark guess letter as processed
	        }
	    }
	    // Second pass: Check for partial matches
	    for(int i = 0; i < 5; i++){
	        if(colors[i] != 3){ // Skip if already an exact match
	            for(int j = 0; j < 5; j++){
	                if(guessChars[i] == goalChars[j]){
						// Yellow for partial match
	                    colors[i] = 2;
	                    // Mark letter in goal word as used
	                    goalChars[j] = '*';
	                    j = 5;
	                }
	            }
	            // If no partial match found, set to gray
	            if(colors[i] == 0){
	                colors[i] = 1; // Gray
	            }
	        }
	    }
	    // Update keyboard colors based on the highest status
	    for(int i = 0; i < 5; i++){
	        char letter = guess.charAt(i);
	        int currentColor = keyBoardColors[letter - 'A'];
	        if(colors[i] > currentColor){
	            keyBoardColors[letter - 'A'] = colors[i];
	        }
	    }
	}
	/** 
	 *	Draws the entire game panel. This includes the guessed words,
	 *  the current word being guessed, and all of the letters in the
	 *  "keyboard" at the bottom of the gameboard. The correct colors
	 *  will need to be chosen for every letter.
	 *	This method is complete
	 */
	public void drawPanel(){
	    StdDraw.clear(StdDraw.WHITE);
	    // Draw guessed letter backgrounds
	    for(int row = 0; row < 6; row++){
	        String inputword = wordGuess[row].toLowerCase();
	        for(int col = 0; col < 5; col++){
	            if(inputword.length() > 0){
	                int status = getLetterStatus(inputword, col);
	                String frameColor;
	                if(status == 3){
						// Exact match
	                    frameColor = "letterFrameGreen.png";
	                }
	                else if(status == 2){
						// Partial match
	                    frameColor = "letterFrameYellow.png";
	                }
	                else if(status == 1){
						// No match
	                    frameColor = "letterFrameDarkGray.png";
	                }
	                else{
						frameColor = "letterFrameGray.png";
					}
	                StdDraw.picture(209 + col * 68, 650 - row * 68,
									frameColor);
	            }
	            else{
	                StdDraw.picture(209 + col * 68, 650 - row * 68,
									"letterFrame.png");
	            }
	        }
	    }
	    // Draw the Wordle board
	    Font font = new Font("Arial", Font.BOLD, 12);
	    StdDraw.setFont(font);
	    StdDraw.picture(Constants.SCREEN_WIDTH / 2,
						Constants.SCREEN_HEIGHT - 30, "wordle.png");
	    // Keyboard color and drawing logic
	    // Track letter color status for keyboard display
	    int[] letterStatus = new int[26];
	    // Update letterStatus based on each guess
	    for(int row = 0; row < 6; row++){
	        String inputword = wordGuess[row].toLowerCase();
	        if(inputword.length() > 0){
	            for (int i = 0; i < inputword.length(); i++) {
				    char guessedLetter = inputword.charAt(i);
				    // Convert char to index (0-25)
				    int letterIndex = guessedLetter - 'a';
				    int currentStatus = getLetterStatus(inputword, i);
				    // Update the status only if the current status is greater
				    if (currentStatus > letterStatus[letterIndex]) {
				        letterStatus[letterIndex] = currentStatus;
				    }
				}
	        }
	    }
	    // Draw keyboard with colors based on letterStatus
	    for(int place = 0; place < Constants.KEYPLACEMENT.length; place++){
	        int[] pair = Constants.KEYPLACEMENT[place];
	        int letterIndex = Constants.KEYBOARD[place].toUpperCase().charAt(0) - 'A';
	        String keyBackground = "";
	        if(place == 19 || place == 27 || place == 28){
				keyBackground = "keyBackgroundBig.png";        
		    }
		    else if(letterStatus[letterIndex] == 3){
	            keyBackground = "keyBackgroundGreen.png";
	        }
	        else if(letterStatus[letterIndex] == 2){
	            keyBackground = "keyBackgroundYellow.png";
	        }
	        else if(letterStatus[letterIndex] == 1){
	            keyBackground = "keyBackgroundDarkGray.png";
	        }
		    else{
		        keyBackground = "keyBackground.png";
		    }
	        StdDraw.picture(pair[0], pair[1], keyBackground);
	        StdDraw.setPenColor(StdDraw.BLACK);
	        StdDraw.text(pair[0], pair[1], Constants.KEYBOARD[place]);
	    }
	    // Draw all letters guessed so far and refresh the display
	    drawAllLettersGuessed();
	    StdDraw.show();
	    StdDraw.pause(Constants.DRAW_DELAY);
	    // Check if won or lost
	    checkIfWonOrLost();
	}
	/**
	 * Helper function to get the color status of each letter.
	 * 3 - Exact match(green), 2 - Partial match(yellow),
	 * 1 - Not used(gray) or not in the word(dark gray)
	 */
	public int getLetterStatus(String guess, int pos){
	    if(guess.charAt(pos) == word.charAt(pos)){
	        return 3; // Exact match
	    }
	    else if(word.indexOf(guess.charAt(pos)) != -1){
	        return 2; // Partial match
	    }
	    else{
	        return 1; // No match or not in the target word
	    }
	}
	/** 
	 *	This method is called by drawPanel, and draws all of the letters
	 *  in the guesses made by the user.
	 *	This method is complete.
	 */
	public void drawAllLettersGuessed(){	
		// Draw guessed letters
		Font font = new Font("Arial", Font.BOLD, 34);
		StdDraw.setFont(font);
		int guessNumber = 0;
		for(int i = 0; i < wordGuess.length; i++){
			if(wordGuess[i].length() > 0){
				for(int j = 0; j < wordGuess[i].length(); j++){
					StdDraw.text(209 + j * 68, 644 - i * 68,
					"" + wordGuess[i].charAt(j));
				}
			}
			if(wordGuess[i].length() == 5){
				guessNumber = i + 1;
			}
		}
		for(int i = 0; i < letters.length(); i++){
			StdDraw.text(209 + i * 68, 644 - guessNumber * 68,
			"" + letters.substring(i, i+1));
		}
	}
	/** 
	 *	Checks to see if the game has been won or lost. The game is won
	 *  if the user enters the correct word with a guess. The game is
	 *  lost when the user does not enter the correct word with the last
	 *  (6th) guess. An appropriate message is displayed to the user in
	 *  the form of a JOptionPane with JDialog for a win or a loss.
	 *	This method is complete.
	 */
	public void checkIfWonOrLost(){
	    // Check if the last guess is correct
	    for(int i = 0; i < wordGuess.length; i++){
	        if(wordGuess[i].equalsIgnoreCase(word)){
	            isactiveGame = false;
	            word = word.toUpperCase();
	            JOptionPane pane = new JOptionPane(word + " is the word!"
	            + " Press RESET to begin again");
	            JDialog d = pane.createDialog(null, "CONGRATULATIONS!");
	            d.setLocation(365, 250);
	            d.setVisible(true);
	        }
	    }
	    // Check if all guesses are filled and none matched
	    if(wordGuess[5].length() == 5 && !wordGuess[5].equals(word)){
	        isactiveGame = false;
	        word = word.toUpperCase();
	        JOptionPane pane = new JOptionPane(word + " was the word! " 
	        + "Press RESET to begin again");
	        JDialog d = pane.createDialog(null, "LOSER!");
	        d.setLocation(365, 250);
	        d.setVisible(true);
	    }
	}
	/** 
	 *	This method is constantly looking for keyboard or mouse input
	 *  from the user, and reacting to this input.
	 *	This method is complete.
	 */
	public void update(){
		if(isactiveGame){
			respondToKeys();
		}
		respondToMouse();
	}
	/** 
	 *	Responds to input from the keyboard.  Will call the method
	 *  processGuess when the user has entered a word to guess.
	 *	This method is complete.
	 */
	public void respondToKeys(){
		if(readyForKeyInput && StdDraw.hasNextKeyTyped()
		&& StdDraw.isKeyPressed(KeyEvent.VK_BACK_SPACE)
		&& letters.length() > 0){
			letters = letters.substring(0, letters.length() - 1);
			readyForKeyInput = false;
		}
		else if(readyForKeyInput && StdDraw.hasNextKeyTyped()
				&& StdDraw.isKeyPressed(KeyEvent.VK_ENTER)
				&& letters.length() == 5){
			processGuess();
			readyForKeyInput = false;
		}
		else if(readyForKeyInput && StdDraw.hasNextKeyTyped()
		&& letters.length() < 5){
			String letter = "" + StdDraw.nextKeyTyped();
			letter = letter.toUpperCase();
			if(letter.charAt(0) >= 'A' && letter.charAt(0) <= 'Z'){
				letters += letter;
			}
			readyForKeyInput = false;
		}
		else{
			while(StdDraw.hasNextKeyTyped()){	
				StdDraw.nextKeyTyped();
			}
			if(!StdDraw.hasNextKeyTyped()){
				readyForKeyInput = true;
			}
		}
	}
	/** 
	 *	Responds to input from the mouse, simulating the typing of keys
	 *  on the "keyboard" at the bottom of the game panel. Will call the
	 *  method processGuess when the user has entered a word to guess.
	 *	This method is complete.
	 */
	public void respondToMouse(){
		if(readyForMouseInput && StdDraw.isMousePressed()){
			for(int i = 0; i < Constants.KEYPLACEMENT.length; i++){
				if(StdDraw.mouseX() > Constants.KEYPLACEMENT[i][0] - 22
				&& StdDraw.mouseX() < Constants.KEYPLACEMENT[i][0] + 22 
				&& StdDraw.mouseY() > Constants.KEYPLACEMENT[i][1] - 29
				&& StdDraw.mouseY() < Constants.KEYPLACEMENT[i][1] + 29){
					if(i == 28){
						initAll("");
						isactiveGame = true;
					}
					else if(isactiveGame && i == 27 && letters.length() > 0){
						letters = letters.substring(0, letters.length() - 1);
					}
					else if(isactiveGame && i == 19 && letters.length() == 5){
						processGuess();
					}
					else if(isactiveGame && i != 19 && i != 27 && i != 28
					&& letters.length() < 5){
						String letter = Constants.KEYBOARD[i].toUpperCase();
						letters += letter;
					}
				}
			}
			readyForMouseInput = false;
		}
		else if(!StdDraw.isMousePressed()){
			readyForMouseInput = true;
		}
	}
}
