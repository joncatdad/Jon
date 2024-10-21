import java.util.Scanner;
import java.io.PrintWriter;
/**
 *	MVCipher - A program that performs encryption 
 *  and decryption using the MV Cipher.
 *	Requires Prompt and FileUtils classes.
 *	
 *	@author	Jonathan Chen
 *	@since	September 20, 2024
 */
public class MVCipher{
    public static void main(String[] args){
        MVCipher mvcipher = new MVCipher();
        mvcipher.run();
    }
    // Main method for running the program
    public void run(){
        // Replace Scanner with Prompt for input gathering
        System.out.println("Welcome to the MV Cipher machine!");
        String keyword = getKeyword();
        System.out.println();
        int action = Prompt.getInt("Encrypt or decrypt? (1, 2)");
        // Get file names
        String inputFileName, outputFileName;
        if (action == 1){
			System.out.println();
            inputFileName = Prompt.getString("Name of file to encrypt");
            outputFileName = Prompt.getString("Name of output file");
        }
        else{
			System.out.println();
            inputFileName = Prompt.getString("Name of file to decrypt");
            outputFileName = Prompt.getString("Name of output file");
        }
        // Process the input file for encryption or decryption
        processFile(inputFileName, outputFileName, keyword, action == 1);
        // Display result message
        if (action == 1){
			System.out.println();
            System.out.println("The encrypted file " + outputFileName + " has been created using the keyword -> " + keyword);
        }
        else{
			System.out.println();
            System.out.println("The decrypted file " + outputFileName + " has been created using the keyword -> " + keyword);
        }
    }
    /**
         *  Method to get a valid keyword from the user
         *
         *  @return String of a key to change the txt file
         */
    public String getKeyword(){
		System.out.println();
        String keyword = Prompt.getString("Please input a word to use as key (letters only)");
        keyword = keyword.toUpperCase();
        // Validate the keyword
        while (keyword.length() < 3 || !keyword.matches("[A-Za-z]+")){
            System.out.println("ERROR: Key must be all letters and at least 3 characters long.");
            keyword = Prompt.getString("Please input a word to use as key (letters only)");
            keyword = keyword.toUpperCase();
        }
        return keyword;
    }
    /**
         *  Method to process the input file for encryption or decryption
         *
         *  @param inputFileName read in a txt file
         *  @param outputFileName write in a txt file
         *  @param keyword key to encrypt or decrypt the txt file
         *  @param encrypt tells of it is decrpyt or encrypt
         */
    public void processFile(String inputFileName, String outputFileName, String keyword, boolean encrypt){
        // Use FileUtils to open the files for reading and writing
        try (Scanner inputFile = FileUtils.openToRead(inputFileName);
            PrintWriter outputFile = FileUtils.openToWrite(outputFileName)){
            while (inputFile.hasNextLine()){
                String line = inputFile.nextLine();
                String processedLine = processText(line, keyword, encrypt);
                outputFile.println(processedLine);
            }
        }
    }
    /**
         *  Encrypts or decrypts a string based on the given keyword
         *
         *  @param inputFileName read in a txt file
         *  @param outputFileName write in a txt file
         *  @param keyword
         *  @param encrypt 
         *  @return processing txt into encrypt or decrypt txt
         */
    public String processText(String text, String keyword, boolean encrypt){
        String result = "";  // Use series of string 
        int keywordIndex = 0;
        for (int i = 0; i < text.length(); i++){
            char letter = text.charAt(i);
            if ((letter >= 'A' && letter <= 'Z') || (letter >= 'a' && letter <= 'z')){
                char keyChar = keyword.charAt(keywordIndex % keyword.length());
                int shift = keyChar - 'A';
                if (!encrypt){
					shift = -shift;
				}
                if (letter >= 'A' && letter <= 'Z'){
                    result += shiftUppercase(letter, shift);
                }
                else{
                    result += shiftLowercase(letter, shift);
                }
                keywordIndex++;
            }
            else{
				// Non-alpha characters remain unchanged
                result += letter;
            }
        }
        return result;  // Return the combined string
    }
    /**
         *  Shifts an uppercase character by a given shift value
         *
         *  @param letter getting each character
         *  @param shift shifting letters around
         *  @return each character of decrypt or encrypt
         */
    public char shiftUppercase(char letter, int shift){
		char uppercase = (char)((letter - 'A' + shift + 26) % 26 + 'A');
        return uppercase;
    }
    /**
         *  Shifts a lowercase character by a given shift value
         *
         *  @param letter getting each character
         *  @param shift shifting letters around
         *  @return each character of decrypt or encrypt
         */
    public char shiftLowercase(char letter, int shift){
		char lowercase = (char)((letter - 'a' + shift + 26) % 26 + 'a');
        return lowercase;
    }
}
