/**
 * PigGame
 * A game where the user competes with the computer to get to 100 points.
 * The player rolls a 6-sided die 
 * and trying to aim to avoid rolling a 1 which erases their turn's score
 * and ends their turn.
 * Rolling between 2 and 6 adds the roll value to the player's turn score.
 * The player can choose to "hold" to keep their turn score and add it to
 * their total score.
 *
 * @author Jonathan Chen
 * @since September 13, 2024
 */
import java.util.Scanner;
public class PigGame{
    // The winning score is a constant set to 100
    private final int WINNING_SCORE;
    private Dice dice;
    /* Constructor initializes the winning score 
     * and creates a Dice object
    */
    public PigGame(){
        WINNING_SCORE = 100;
        dice = new Dice();
    }
    // Main method starts the game by calling the run method
    public static void main(String[] args){
        PigGame PG = new PigGame();
        PG.run();
    }
    /* Runs the game, showing the introduction 
     * and prompting the user to play or run statistics
     */
    public void run(){
        PrintIntroduction();
        PlayOrStatistics();
    }
    // Prints the introduction and rules for the Pig Game
    public void PrintIntroduction(){
        System.out.println("\n");
        System.out.println("_______          _____");
        System.out.println("| ___(_)        |  __ \\");
        System.out.println("| |_/ /_  __ _  | |  \\/ __ _ _ __ ___   ___");
        System.out.println("|  __/| |/ _` | | | __ / _` | '_ ` _ \\ / _ \\");
        System.out.println("|  |  | | (_| | | |_\\ \\ (_| | | | | | |  __/");
        System.out.println("\\__|  |_|\\__, | \\____/ \\__,_|_| |_| |_|\\___|");
        System.out.println("          __/ |");
        System.out.println("         |___/");
        System.out.println();
        System.out.println("The Pig Game is human vs computer. Each "
        + "takes a turn rolling a die, and the first to score 100 points"
        + " wins.");
        System.out.println("\tROLL:\t2 through 6: add points to turn "
        + "total, player's turn continues");
        System.out.println("\t\t1: player loses turn");
        System.out.println("\tHOLD:\tturn total is added to player's " 
        + "score, turn goes to the other player");
        System.out.println();
        System.out.println();
    }
    // Prompts the user to play the game or run a statistics
    public void PlayOrStatistics(){
        Scanner scanner = new Scanner(System.in);
        boolean isGameOrStats = true;
        while(isGameOrStats){
            System.out.print("Play game or Statistics(p or s) -> ");
            String choice = scanner.nextLine();
            if(choice.equalsIgnoreCase("p")){
                PlayGame(scanner); // Play the game
                isGameOrStats = false;
            }
            else if(choice.equalsIgnoreCase("s")){
				// Run the statistics
                runStatistics(scanner);
                isGameOrStats = false;
            }
        }
        System.out.println();
        System.out.println("Thanks for playing the Pig Game!!!");
        scanner.close();
    }
    /* Handles the main gameplay loop where both the player and the 
     * computer take turns
     */
    public void PlayGame(Scanner scanner){
        int PlayerTotalScore = 0;
        int ComputerTotalScore = 0;
        /* Loop until either the player or the computer reaches the 
         * winning score
         */
        while((PlayerTotalScore < WINNING_SCORE) 
        &&(ComputerTotalScore < WINNING_SCORE)){
            PlayerTotalScore = PlayerTurn(PlayerTotalScore, scanner);
            // Player's turn
            if(PlayerTotalScore >= WINNING_SCORE){
				System.out.println();
                System.out.println("Congratulations!!! YOU WON!!!");
            }
            else{
                ComputerTotalScore = 
                ComputerTurn(ComputerTotalScore, scanner);
                // Computer's turn
                if(ComputerTotalScore >= WINNING_SCORE){
					System.out.println();
                    System.out.println("The computer won!" 
                    + " Better luck next time.");
                }
            }
        }
    }
    // Manages the player's turn where they choose to either roll or hold
    public int PlayerTurn(int PlayerTotalScore, Scanner scanner){
        int PlayerTurnScore = 0;
        boolean isContinueRolling = true;
        // Loop until the player decides to hold or rolls a 1
        while(isContinueRolling){
			System.out.println();
            System.out.println("**** USER Turn ***");
            System.out.println();
            System.out.println("Your turn score: " + PlayerTurnScore);
            System.out.println("Your total score: " + PlayerTotalScore);
            System.out.println();
            System.out.print("(r)oll or(h)old -> ");
            String choice = scanner.nextLine();
            if(choice.equalsIgnoreCase("r")){
                // Player chooses to roll
                System.out.println();
                System.out.println("You ROLL");
                int roll = dice.roll();
                dice.printDice();
                if(roll == 1){
                    // If they roll a 1, they lose all points for this turn and the turn ends
                    System.out.println("You rolled a 1! You lose your turn.");
                    PlayerTurnScore = 0;
                    isContinueRolling = false;
                }
                else{
                    // Add the roll value to the player's turn score
                    PlayerTurnScore += roll;
                    System.out.println("Your turn score: " + PlayerTurnScore);
                }
            }
            else if(choice.equalsIgnoreCase("h")){
                // Player chooses to hold
                System.out.println();
                System.out.println("You HOLD");
                PlayerTotalScore += PlayerTurnScore;
                System.out.println();
                System.out.println("Your total score: " + PlayerTotalScore);
                isContinueRolling = false;
            }
        }
        return PlayerTotalScore;
    }
    /* Manages the computer's turn, automatically deciding
     * to hold once 20+ points are accumulated
     */
    public int ComputerTurn(int ComputerTotalScore, Scanner scanner){
        int ComputerTurnScore = 0;
        boolean isContinueRolling = true;
        System.out.println();
        System.out.println("**** COMPUTER'S Turn ***");
        System.out.println();
        // Loop until the computer holds or rolls a 1
        while(isContinueRolling){
            System.out.println("Computer's turn score: " + ComputerTurnScore);
            System.out.println("Computer's total score: " + ComputerTotalScore);
            System.out.println();
            System.out.print("Press enter for computer's roll -> ");
            scanner.nextLine();
            System.out.println();
            System.out.println("Computer Will ROLL");
            int roll = dice.roll();
            dice.printDice();
            if(roll == 1){
				/* If the computer rolls a 1
				* it loses all points for this turn and the turn ends
				*/
                ComputerTurnScore = 0;
                isContinueRolling = false;
            }
            else{
                // Add the roll value to the computer's turn score
                ComputerTurnScore += roll;
                // Computer holds if it has 20+ points or if it can win
                if(ComputerTurnScore >= 20 ||
                (ComputerTotalScore + ComputerTurnScore) >= WINNING_SCORE){
					System.out.println();
                    System.out.println("Computer will HOLD");
                    ComputerTotalScore += ComputerTurnScore;
                    System.out.println();
                    System.out.println("Computer's total score: " 
                    + ComputerTotalScore);
                    isContinueRolling = false;
                }
            }
        }
        return ComputerTotalScore;
    }
    /**
         *  Runs the statistics where the computer "holds at 20"
         * 
         *  @param scanner Scanner input of 1000 - 1000000
         *  @return   computer turn score
         */
    public void runStatistics(Scanner scanner){
		System.out.println();
        System.out.println("Run statistical analysis - \"Hold at 20\"");
        int numberOfTurns = 0;
        // Get a valid number of turns from the user(between 1000 and 1,000,000)
        while(numberOfTurns < 1000 || numberOfTurns > 1000000){
			System.out.println();
            System.out.print("Number of turns(1000 - 1000000) -> ");
            numberOfTurns = scanner.nextInt();
        }
        // Array to count scores(0 through 25)
        int[] scoreDistribution = new int[26];
        // Simulate the game for the specified number of turns
        for(int i = 0; i < numberOfTurns; i++){
            int turnScore = simulateComputerTurn();
            scoreDistribution[turnScore]++;
        }
        // Calculate and display the probabilities of each score
        System.out.println();
        System.out.println("\tEstimated");
        System.out.println("Score   Probabilities");
        for(int i = 0; i <= scoreDistribution.length - 1; i++){
            double probability =(double)scoreDistribution[i]/numberOfTurns;
            if(probability != 0.0){
				System.out.printf("%d: %.5f", i, probability);
				System.out.println();
			}
        }
    }
    /**
         *  Simulates one turn for the computer, returns the turn score
         *
         *  @return   computer turn score
         */
    public int simulateComputerTurn(){
        int ComputerTurnScore = 0;
        boolean isContinueRolling = true;
        // Loop until the computer holds or rolls a 1
        while(isContinueRolling){
            int roll = dice.roll();
            if(roll == 1){
                return 0;
                // Turn ends with 0 points
            }
            else{
                ComputerTurnScore += roll;
                if(ComputerTurnScore >= 20){
                    return ComputerTurnScore; 
                    // Return the score when 20+ points are reached
                }
            }
        }
        return ComputerTurnScore;
    }
}
