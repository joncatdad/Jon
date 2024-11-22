import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 *	Provides utilities for word games:
 *	1. Finds all words in the dictionary that match a list of letters
 *	2. Prints an array of words to the screen in tabular format
 *	3. Finds the word from an array of words with the highest score
 *	4. Calculates the score of a word according to a table
 *
 *	Uses the FileUtils and Prompt classes.
 *	
 *	@author Jonathan Chen
 *	@since	October 18, 2024
 */
public class WordUtils{
	private String[] words;		// The dictionary of words
	private final String WORD_FILE = "wordList.txt";
	/* Constructor */
	public WordUtils(){
		
	}
	/** Load all of the dictionary from a file into the words array. */
	private void loadWords() {
	    List<String> wordList = new ArrayList<>();
	    try(Scanner input = FileUtils.openToRead(WORD_FILE)){
		    while(input.hasNext()){
		        wordList.add(input.next());
		    }
		}
		catch(Exception e){
		    System.err.println("Error occurred: " + e.getMessage());
		    System.exit(0);
		}
	    words = wordList.toArray(new String[0]);  // Store the words in an array
	}
	/** Find all words that can be formed by a list of letters.
	 *  @param letters String containing the list of letters
	 *  @return Array of strings with all words found
	 */
	public String[] findAllWords(String letters){
		List<String> matchingWords = new ArrayList<>();
		for(int i = 0; i < words.length; i++){
		    String word = words[i];  // Get the word at index i
		    if(isWordMatch(word, letters)){
		        matchingWords.add(word);
		    }
		}
		// Store the result in a variable
		String[] resultArray = matchingWords.toArray(new String[0]);
		// Return the result
		return resultArray;
	}
	/** Checks if a word can be formed using the given letters.
	 *  @param word The word to test
	 *  @param letters A string of letters to compare
	 *  @return True if the word matches the letters, false otherwise
	 */
	public boolean isWordMatch(String word, String letters){
		for(int i = 0; i < word.length(); i++){
		    char c = word.charAt(i);  // Get the character at index i
		    int index = letters.indexOf(c);
		    if(index > -1){
		        letters = letters.substring(0, index) + letters.substring(index + 1); // Remove letter from letters
		    }
		    else{
		        return false;
		    }
		}
		return true;
	}
	/** Print the words found to the screen.
	 *  @param wordList Array containing the words to be printed
	 */
	public void printWords(String[] wordList){
		System.out.println();
		for(int i = 0; i < wordList.length; i++){
			System.out.printf("%-8s", wordList[i]);
			if((i + 1) % 10 == 0){  // New line after every 10 words
				System.out.println();
			}
		}
		System.out.println();
	}
	/** Finds the highest scoring word according to a score table.
	 *
	 *  @param wordList An array of words to check
	 *  @param scoreTable An array of 26 integer scores in letter order
	 *  @return The word with the highest score
	 */
	public String bestWord(String[] wordList, int[] scoreTable){
		String bestWord = "";
		int highestScore = 0;
		for(int i = 0; i < wordList.length; i++){
		    String word = wordList[i];  // Get the word at index i
		    int score = getScore(word, scoreTable);
		    if(score > highestScore){
		        highestScore = score;
		        bestWord = word;
		    }
		}
		return bestWord;
	}
	/** Calculates the score of one word according to a score table.
	 *
	 *  @param word The word to score
	 *  @param scoreTable An array of 26 integer scores in letter order
	 *  @return The integer score of the word
	 */
	public int getScore(String word, int[] scoreTable){
		int score = 0;
		for(int i = 0; i < word.length(); i++){
		    char c = word.charAt(i); // Get the character at index i
		    // Assuming all letters are lowercase 'a' to 'z'
		    score += scoreTable[c - 'a'];
		}
		return score;
	}
	/***************************************************************/
	/************************** Testing ****************************/
	/***************************************************************/
	public static void main(String[] args){
		WordUtils wordutils = new WordUtils();
		wordutils.run();
	}
	public void run(){
		String letters = Prompt.getString("Please enter a list of "
				+ "letters, from 3 to 12 letters long, without spaces");
		loadWords();
		String[] wordList = findAllWords(letters);
		System.out.println();
		printWords(wordList);
		// Score table in alphabetic order according to Scrabble
		int[] scoreTable ={1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1,
							3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
		String best = bestWord(wordList, scoreTable);
		System.out.println("\nHighest scoring word: " + best + "\nScore = " 
							+ getScore(best, scoreTable) + "\n");
	}
}
