/**
 *	AnagramMaker - <description goes here>
 *
 *	Requires the WordUtilities, SortMethods, Prompt, and FileUtils classes
 *
 *	@author	Jonathan Chen
 *	@since	January 15, 2024
 */
 import java.util.List;
 import java.util.ArrayList;
public class AnagramMaker {
	private final String FILE_NAME = "randomWords.txt";	// file containing all words
	private WordUtilities wu;	// the word utilities for building the word
								// database, sorting the database,
								// and finding all words that match
								// a string of characters
	// variables for constraining the print output of AnagramMaker
	private int numWords;		// the number of words in a phrase to print
	private int maxPhrases;		// the maximum number of phrases to print
	private int numPhrases;		// the number of phrases that have been printed
	/*	Initialize the database inside WordUtilities
	 *	The database of words does NOT have to be sorted for AnagramMaker to work,
	 *	but the output will appear in order if you DO sort.
	 */
	public AnagramMaker() {
		wu = new WordUtilities();
		wu.readWordsFromFile(FILE_NAME);
		wu.sortWords();
	}
	public static void main(String[] args) {
		AnagramMaker am = new AnagramMaker();
		am.run();
	}
	/**
	 * The top routine that prints the introduction and runs the anagram-maker.
	 */
	public void run() {
		printIntroduction();
		runAnagramMaker();
		System.out.println("\nThanks for using AnagramMaker!\n");
	}
	/**
	 *	Print the introduction to AnagramMaker
	 */
	public void printIntroduction() {
		System.out.println("\nWelcome to ANAGRAM MAKER");
		System.out.println("\nProvide a word, name, or phrase and out comes their anagrams.");
		System.out.println("You can choose the number of words in the anagram.");
		System.out.println("You can choose the number of anagrams shown.");
		System.out.println("\nLet's get started!");
	}
	/**
	 *	Prompt the user for a phrase of characters, then create anagrams from those
	 *	characters.
	 */
	public void runAnagramMaker(){
        Prompt questions = new Prompt();
        boolean keepRunning = true;
        while(keepRunning) {
            String phrase = questions.getString("\nWord(s), name or phrase (q to quit)");
            if (phrase.equalsIgnoreCase("q")){
                keepRunning = false;
            }
            else {
                numWords = questions.getInt("Number of words in anagram");
                maxPhrases = questions.getInt("Maximum number of anagrams to print");
                System.out.println();
                numPhrases = 0;
                
                System.out.println("Stopped at " + numPhrases + " anagrams.");
            }
        }
    }
	
}
