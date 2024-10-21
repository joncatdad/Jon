import java.util.Scanner;
/**
 * Plays the game of MasterMind.
 * The game allows players to guess a 4-peg code with letters from A-F.
 * The player has 10 guesses to match the randomly generated master code.
 * 
 * @author Jonathan Chen
 * @since September 27, 2024
 */
public class MasterMind {
    private static final int PEGS_IN_CODE = 4; // Number of pegs in code
    private static final int MAX_GUESSES = 10; // Max number of guesses
    private static final int PEG_LETTERS = 6; // Number of different letters on pegs
    private int count; // counts the guesses
    private boolean reveal; // true = reveal the master combination
    private PegArray[] guesses; // array of guessed peg arrays
    private PegArray master; // the master peg array
    private Scanner scanner; // scanner for user input
    public MasterMind() { // initializing the variables
        count = 1; // Start counting guesses from 1
        reveal = false; // Set to true to reveal master code at the end
        guesses = new PegArray[MAX_GUESSES];
        master = generateMasterCode();
        scanner = new Scanner(System.in);
    }
    public static void main(String[] args){
        MasterMind game = new MasterMind();
        game.playGame();
    }
    /**
     * Prints one guess line to screen.
     * @param t the guess turn.
     */
    public void printGuess(int turn) {
        System.out.printf("|   %2d   |", (turn + 1));
        if (guesses[turn] != null) {
            for (int p = 0; p < PEGS_IN_CODE; p++) {
                char letter = guesses[turn].getPeg(p).getLetter();
                System.out.printf("   %c   |", letter);
            }
            System.out.printf("   %d      %d    |\n",
                guesses[turn].getExact(), guesses[turn].getPartial());
        }
        else {
            for (int p = 0; p < PEGS_IN_CODE; p++) {
                System.out.print("       |");
            }
            System.out.printf("   0      0    |\n");
        }
    }
    /**
     * Generates the master code as a PegArray using the Dice class.
     * Each roll of the dice corresponds to a letter from A to F.
     * @return a PegArray representing the master code.
     */
    public PegArray generateMasterCode(){
        PegArray masterCode = new PegArray(PEGS_IN_CODE);
        // 6-sided dice to represent letters A-F
        Dice dice = new Dice(PEG_LETTERS);
        for(int i = 0; i < PEGS_IN_CODE; i++){
            int roll = dice.roll();
            char letter =(char)('A' + roll - 1); // Convert 1-6 to A-F
            masterCode.getPeg(i).setLetter(letter);
        }
        return masterCode;
    }
    /**
     * Accepts user input for guesses, ensuring the input is valid.
     * @return a valid PegArray created from user input.
     */
    public PegArray acceptUserInput(){
        PegArray userGuess;
        while(true){
			System.out.println();
			System.out.println("Guess " + count);
			System.out.println();
            System.out.print("Enter the code using(A, B, C, D, E, F)."
            + " For example, ABCD or abcd: ");
            String input = scanner.nextLine();
            input = input.toUpperCase(); // Convert input to uppercase
            if(input.length() == PEGS_IN_CODE && input.matches("[A-F]+")){
				count++;
                userGuess = new PegArray(PEGS_IN_CODE);
                for(int i = 0; i < PEGS_IN_CODE; i++){
                    userGuess.getPeg(i).setLetter(input.charAt(i));
                }
                return userGuess; // Valid guess
            }
            else{
                System.out.println("ERROR: Bad input, try again.");
                count = count;
            }
        }
    }
    /**
     * Plays one turn of the game, processing user guesses.
     * @param turn the current turn number.
     */
    public void playTurn(int turn){
        guesses[turn] = acceptUserInput();
        int exactMatches = guesses[turn].getExactMatches(master);
        int partialMatches = guesses[turn].getPartialMatches(master);
        if (exactMatches == PEGS_IN_CODE){
            reveal = true; // Reveal the master code upon winning
        }
    }
    /**
     * Plays the game, managing turns and checking for win/loss conditions.
     */
    public void playGame(){
        printIntroduction();
        System.out.print("Hit the Enter key to start the game -> ");
        scanner.nextLine();
        for (int turn = 0; turn < MAX_GUESSES; turn++){
			printBoard();
            playTurn(turn);         
            if (reveal){
				printBoard();
				count --;
				System.out.println("Nice work! You found the master "
				+ "code in " + count + " guesses");
				break; // Exit loop if the master code is revealed
			}
        }
        if (!reveal){
            reveal = true; // Reveal the master code at the end
            printBoard();
            count --;
            System.out.println("Oops. You were unable to find the"
            + " solution in " + count + " guesses.");
        }
    }
    /**
     * Prints the introduction screen.
     */
    public void printIntroduction(){
        System.out.println("\n");
        System.out.println("+------------------------------------------"
        + "------------------------------------------+");
        System.out.println("| ___  ___             _              ___ "
        + " ___ _             _                       |");
        System.out.println("| |  \\/  |            | |             |  "
        + "\\/  |(_)           | |                      |");
        System.out.println("| | .  . |  __ _  ___ | |_  ___  _ __ | . "
        + " . | _  _ __    __| |                      |");
        System.out.println("| | |\\/| | / _` |/ __|| __|/ _ \\| '__|| "
        + "|\\/| || || '_ \\  / _` |                      |");
        System.out.println("| | |  | || (_| |\\__ \\| |_|  __/| |   | "
        + "|  | || || | | || (_| |                      |");
        System.out.println("| \\_|  |_/ \\__,_||___/ \\__|\\___||_|   "
        + "\\_|  |_/|_||_| |_| \\__,_|                      |");
        System.out.println("|                                          "
        + "                                          |");
        System.out.println("| WELCOME TO MONTA VISTA MASTERMIND!       "
        + "                                          |");
        System.out.println("|                                          "
        + "                                          |");
        System.out.println("| The game of MasterMind is played on a "
        + "four-peg gameboard, and six peg letters can  |");
        System.out.println("| be used.  First, the computer will choose"
        + " a random combination of four pegs, using |");
        System.out.println("| some of the six letters (A, B, C, D, E, "
        + "and F).  Repeats are allowed, so there are |");
        System.out.println("| 6 * 6 * 6 * 6 = 1296 possible combinations."
        + "  This \"master code\" is then hidden     |");
        System.out.println("| from the player, and the player starts " 
        + "making guesses at the master code.  The     |");
        System.out.println("| player has 10 turns to guess the code. "
        + " Each time the player makes a guess for     |");
        System.out.println("| the 4-peg code, the number of exact "
        + "matches and partial matches are then reported  |");
        System.out.println("| back to the user. If the player finds the"
        + " exact code, the game ends with a win.    |");
        System.out.println("| If the player does not find the master "
        + "code after 10 guesses, the game ends with   |");
        System.out.println("| a loss.                                  "
        + "                                          |");
        System.out.println("|                                          "
        + "                                          |");
        System.out.println("| LET'S PLAY SOME MASTERMIND!              "
        + "                                          |");
        System.out.println("+------------------------------------------"
        + "------------------------------------------+");
        System.out.println("\n");
    }
    /**
     * Prints the peg board to the screen.
     */
    public void printBoard(){
        System.out.print("+--------+");
        for (int a = 0; a < PEGS_IN_CODE; a++){
			System.out.print("-------+");
		}
        System.out.println("---------------+");
        System.out.print("| MASTER |");
        for (int a = 0; a < PEGS_IN_CODE; a++){
            if (reveal){
                System.out.printf("   %c   |", master.getPeg(a).getLetter());
            }
            else{
                System.out.print("  ***  |");
            }
        }
        System.out.println(" Exact Partial |");
        System.out.print("|        +");
        for (int a = 0; a < PEGS_IN_CODE; a++){
			System.out.print("-------+");
		}
        System.out.println("               |");
        // Print Guesses
        System.out.print("| GUESS  +");
        for (int a = 0; a < PEGS_IN_CODE; a++){
			System.out.print("-------+");
		}
        System.out.println("---------------|");
        for (int g = 0; g < MAX_GUESSES; g++){
            printGuess(g);
            if(g < 9){
				System.out.println("|        +-------+-------+-------+" 
				+ "-------+---------------|");
			}
			else{
				System.out.println("+--------+-------+-------+-------+" 
				+ "-------+---------------+");
			}
        }
    }
}
