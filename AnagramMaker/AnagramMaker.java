/**
 *	AnagramMaker - A program to generate anagrams from words or phrases.
 *
 *  This program reads a list of valid words from a file and creates anagrams 
 *  based on user input. Users can specify:
 *  - The number of words in the anagram
 *  - The maximum number of anagrams to display
 *
 *  It removes invalid characters, uses recursion to build anagrams, and 
 *  ensures only valid words are included in the results.
 *
 *	Requires the WordUtilities, SortMethods, Prompt, and FileUtils classes
 *
 *	@author	Jonathan Chen
 *	@since	January 15, 2024
 */
 import java.util.List;
 import java.util.ArrayList;
 import java.util.Map;
 import java.util.HashMap;
public class AnagramMaker{
	private final String FILE_NAME = "randomWords.txt";	// file containing all words
	private WordUtilities wu;	// the word utilities for building the word
								// database, sorting the database,
								// and finding all words that match
								// a string of characters
	// variables for constraining the print output of AnagramMaker
	private int numWords;		// the number of words in a phrase to print
	private int maxPhrases;		// the maximum number of phrases to print
	private int numPhrases;		// the number of phrases that have been printed
	private Map<String, List<String>> wordCache = new HashMap<>();
	/**
	 * 	Initialize the database inside WordUtilities
	 *	The database of words does NOT have to be sorted for AnagramMaker to work,
	 *	but the output will appear in order if you DO sort.
	 */
	public AnagramMaker(){
		wu = new WordUtilities();
		wu.readWordsFromFile(FILE_NAME);
		wu.sortWords();
	}
	public static void main(String[] args){
		AnagramMaker am = new AnagramMaker();
		am.run();
	}
	/**
	 * The top routine that prints the introduction and runs the anagram-maker.
	 */
	public void run(){
		printIntroduction();
		runAnagramMaker();
		System.out.println("\nThanks for using AnagramMaker!\n");
	}
	/**
	 *	Print the introduction to AnagramMaker
	 */
	public void printIntroduction(){
		System.out.println("\nWelcome to ANAGRAM MAKER");
		System.out.println("\nProvide a word, name, or phrase and out comes their anagrams.");
		System.out.println("You can choose the number of words in the anagram.");
		System.out.println("You can choose the number of anagrams shown.");
		System.out.println("\nLet's get started!");
	}
	/**
     * Handles user input and runs the anagram generation process.
     */
    public void runAnagramMaker(){
        Prompt questions = new Prompt();
        boolean keepRunning = true;
        while(keepRunning){
            String phrase = questions.getString("\nWord(s), name or phrase(q to quit)");
            if(phrase.equalsIgnoreCase("q")){
                keepRunning = false;
            }
            else{
                numWords = questions.getInt("Number of words in anagram");
                maxPhrases = questions.getInt("Maximum number of anagrams to print");
                numPhrases = 0;
                System.out.println();
                List<String> currentAnagram = new ArrayList<>();
                // Remove non-alphabet characters and keep spaces
                phrase = phrase.replaceAll("[^a-zA-Z]", "").toLowerCase();
                findAnagrams(phrase, currentAnagram);
                System.out.println("\nStopped at " + numPhrases + " anagrams.");
            }
        }
    }
    /**
	 * Recursive method to generate anagrams.
	 *
	 * @param remainingChars The remaining characters to form an anagram.
	 * @param currentAnagram The current list of words in the anagram.
	 * @param output         The StringBuilder to store results.
	 */
	public void findAnagrams(String phrase, List<String> currentAnagram) {
	    // Stop if we've reached the maximum number of anagrams to print
	    if (numPhrases >= maxPhrases) {
	        return;
	    }
	    // Base case: If the phrase is empty and we have the correct number of words, print it
	    if (phrase.isEmpty()) {
	        if (currentAnagram.size() == numWords) {
	            System.out.println(String.join(" ", currentAnagram));
	            numPhrases++;
	        }
	        return;
	    }
	    // Stop if the current anagram exceeds the desired number of words
	    if (currentAnagram.size() >= numWords) {
	        return;
	    }
	    // Retrieve valid words (cached to optimize performance)
	    List<String> validWords = wordCache.computeIfAbsent(phrase, wu::allWords);
	    for (String word : validWords) {
	        // Avoid duplicates or exceeding word count
	        currentAnagram.add(word);
	        String updatedPhrase = removeLetters(phrase, word);
	        findAnagrams(updatedPhrase, currentAnagram);
	        currentAnagram.remove(currentAnagram.size() - 1); // Backtrack
	    }
	}
    /**
     * Safely removes the letters of the word from the phrase.
     *
     * @param phrase The original phrase.
     * @param word   The word whose letters are to be removed.
     * @return The new phrase after removing the word's letters.
     */
    public String removeLetters(String phrase, String word){
	    StringBuilder sb = new StringBuilder(phrase.replace(" ", "")); // Remove spaces
	    for(char c : word.toCharArray()){
	        int index = sb.indexOf(String.valueOf(c));
	        if(index >= 0){
	            sb.deleteCharAt(index);
	        }
	    }
	    return sb.toString();
	}
}
