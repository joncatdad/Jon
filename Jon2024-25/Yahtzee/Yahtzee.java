import java.util.Scanner;
/**
 *	Introduce the game here
 *
 *	@author	Jonathan Chen
 *	@since	October 23, 2024
 */
public class Yahtzee{
	private YahtzeePlayer yahPlayer1, yahPlayer2;
	private DiceGroup dicegroup;
	private YahtzeeScoreCard yahScoreCard1, yahScoreCard2;
	private Scanner in;
	private int holdNum, scoreChoice, playerOneTotal, playerTwoTotal;
	private boolean hasturnEnded, hasChanged;
	public Yahtzee(){
		yahPlayer1 = new YahtzeePlayer();
		yahPlayer2 = new YahtzeePlayer();
		dicegroup = new DiceGroup();
		yahScoreCard1 = new YahtzeeScoreCard();
		yahScoreCard2 = new YahtzeeScoreCard();
		in = new Scanner(System.in);
		hasturnEnded = false;
		hasChanged = false;
		holdNum = 0;
		scoreChoice = 0;
		playerOneTotal = 0;
		playerTwoTotal = 0;
	}
	public static void main(String[]args){
		Yahtzee yahtzee = new Yahtzee();
		yahtzee.printHeader();
		yahtzee.run();
	}
	public void printHeader(){
		System.out.println("\n");
		System.out.println("+------------------------------------------"
					+ "------------------------------------------+");
		System.out.println("| WELCOME TO MONTA VISTA YAHTZEE!      "
					+ "                                              |");
		System.out.println("|                                          "
						+ "                                          |");
		System.out.println("| There are 13 rounds in a game of Yahtzee."
						+ " In each turn, a player can roll his/her  |");
		System.out.println("| dice up to 3 times in order to get the "
					+ "desired combination. On the first roll, the |");
		System.out.println("| player rolls all five of the dice at once."
						+ " On the second and third rolls, the      |");
		System.out.println("| player can roll any number of dice he/she"
						+ " wants to, including none or all of them, |");
		System.out.println("| trying to get a good combination.       "
					+ "                                           |");
		System.out.println("| The player can choose whether he/she wants"
						+ " to roll once, twice or three times in   |");
		System.out.println("| each turn. After the three rolls in a "
					+ "turn, the player must put his/her score down |");
		System.out.println("| on the scorecard, under any one of the "
					+ "thirteen categories. The score that the     |");
		System.out.println("| player finally gets for that turn depends"
						+ " on the category/box that he/she chooses  |");
		System.out.println("| and the combination that he/she got by "
					+ "rolling the dice. But once a box is chosen  |");
		System.out.println("| on the score card, it can't be chosen "
					+ "again.                                       |");
		System.out.println("|                                          "
						+ "                                          |");
		System.out.println("| LET'S PLAY SOME YAHTZEE!                 "
						+ "                                          |");
		System.out.println("+------------------------------------------"
						+ "------------------------------------------+");
		System.out.println("\n\n");
	}
	public void run(){
		boolean hasname = true;
		System.out.print("Player 1, please enter your first name --> ");
		String playerOne = in.nextLine();
		while(hasname){
			if(playerOne.length() >= 1 && playerOne.length() <= 12){
				hasname = false;
			}
			else{
				System.out.println();
				System.out.print("Please enter another name --> ");
				playerOne = in.nextLine();
			}
		}
		hasname = true;
		yahPlayer1.setName(playerOne);
		System.out.println();
		System.out.print("Player 2, please enter your first name --> ");
		String playerTwo = in.nextLine();
		while(hasname){
			if(playerTwo.length() >= 1 && playerTwo.length() <= 12){
				hasname = false;
			}
			else{
				System.out.println();
				System.out.print("Please enter another name --> ");
				playerTwo = in.nextLine();
			}
		}
		yahPlayer2.setName(playerTwo);
		System.out.println();
		boolean hasRepeat = false;
		while(!hasRepeat){
			System.out.print("Let's see who will go first. " + playerOne + 
				", please hit enter to roll the dice --> ");
			in.nextLine();
			dicegroup.rollDice();
			dicegroup.printDice();	
			playerOneTotal = dicegroup.getTotal();
			System.out.print(playerTwo + ", it's your turn. Please hit "
				+ "enter to roll the dice --> ");
			in.nextLine();
			dicegroup.rollDice();
			dicegroup.printDice();	
			playerTwoTotal = dicegroup.getTotal();
			if(playerOneTotal > playerTwoTotal
			|| playerTwoTotal > playerOneTotal){
				System.out.println(yahPlayer1.getName() + ", you rolled "
				+ "a sum of " + playerOneTotal + ", and "
				+ yahPlayer2.getName() + ", you rolled a sum of "
				+ playerTwoTotal + ".");
				hasRepeat = true;
			}
			else{
				System.out.println("Whoops, we have a tie (both rolled " 
				+ playerOneTotal + "). Looks like we'll have to try"
				+ " that again ... ");
				hasRepeat = false;
			}
		}
		boolean isOneFirst = false;
		boolean isTwoFirst = false;
		if(playerOneTotal > playerTwoTotal){
			System.out.println(yahPlayer1.getName() + 
				", since your sum was higher, you'll roll first.");
			isOneFirst = true;
		}
		else if(playerTwoTotal > playerOneTotal){
			System.out.println(yahPlayer2.getName()
				+ ", since your sum was higher, you'll roll first.");
			isTwoFirst = true;
		}
		for(int roundCount = 1; roundCount < 15; roundCount++){
			endOfTurn(roundCount, isOneFirst, isTwoFirst);
		}
		System.out.printf("%-16s", yahPlayer1.getName());
		System.out.println("score total = " + yahScoreCard1.getTotalScore()); 
		System.out.printf("%-16s", yahPlayer2.getName());
		System.out.println("score total = " + yahScoreCard2.getTotalScore());
		if(yahScoreCard1.getTotalScore() > yahScoreCard2.getTotalScore()){
			System.out.println();
			System.out.println("Congratulation " + yahPlayer1.getName()
								+ ". YOU WON!!!");
		}
		else if(yahScoreCard2.getTotalScore() > yahScoreCard1.getTotalScore()){
			System.out.println();
			System.out.println("Congratulation " + yahPlayer2.getName()
								+ ". YOU WON!!!");
		}
		else{
			System.out.println();
			System.out.println("Congratulation " + yahPlayer2.getName()
			+ " and " + yahPlayer2.getName() + ". You BOTH TIED!!!");
		}
	}
	public void endOfTurn(int count, boolean isplayerone, boolean isplayertwo){
		if(count == 1){
			yahScoreCard1.printCardHeader();
			yahScoreCard1.printPlayerScore(yahPlayer1);
			yahScoreCard2.printPlayerScore(yahPlayer2);
			System.out.print("Round " + count + " of 13 rounds.");
			System.out.println("\n");
		}
		boolean bothTakenTurns = false;
		int bothPlayersWent = 0;
		while(!bothTakenTurns && count > 1){
			if(isplayerone){
				System.out.println();
				takeYourTurn(yahPlayer1.getName());
			}
			else if(isplayertwo){
				System.out.println();
				takeYourTurn(yahPlayer2.getName());
			}
			if(hasturnEnded){
				yahScoreCard1.printCardHeader();
				yahScoreCard1.printPlayerScore(yahPlayer1);
				yahScoreCard2.printPlayerScore(yahPlayer2);
				yahScoreCard1.printScoreChoices();
				if(isplayerone){
					System.out.print(yahPlayer1.getName());
				}
				else if(isplayertwo){
					System.out.print(yahPlayer2.getName());
				}
				System.out.print(", now you need to make a choice. "
			+ " Pick a valid integer from the list above (1 - 13) --> ");
				if(isplayerone){
					isItChangeable1();
				}
				else if(isplayertwo){
					isItChangeable2();
				}
				while(!hasChanged){
					System.out.print("\nPick a valid integer from the "
										+ "list above (1 - 13) --> ");
					if(isplayerone){
						isItChangeable1();
					}
					else if(isplayertwo){
						isItChangeable2();
					}
					System.out.println();
				}
				if(hasChanged){
					if(isplayerone){
						chooseNum1(scoreChoice);
					}
					else if(isplayertwo){
						chooseNum2(scoreChoice);
					}
				}
				yahScoreCard1.printCardHeader();
				yahScoreCard1.printPlayerScore(yahPlayer1);
				yahScoreCard2.printPlayerScore(yahPlayer2);
				bothPlayersWent++;
				if(isplayerone && !bothTakenTurns && bothPlayersWent < 2){
					isplayertwo = true;
					isplayerone = false;
				}
				else if(isplayertwo && !bothTakenTurns
						&& bothPlayersWent < 2){
					isplayertwo = false;
					isplayerone = true;
				}
				else if(bothPlayersWent == 2){
					bothTakenTurns = true;
				}
				hasturnEnded = false;
			}
		}	
		if(count > 1 && count <= 13){
			System.out.print("Round " + count + " of 13 rounds.");
			System.out.println("\n");
		}
	}
	public void takeYourTurn(String name){
		String holdChoice = "";
		int turnLimit = 0;
		System.out.print(name + ", it's your turn to play. Please hit "
			+ "enter to roll the dice --> ");
		in.nextLine();
		System.out.println();
		dicegroup.rollDice();
		dicegroup.printDice();
		while(!hasturnEnded && turnLimit <= 2){
			if(turnLimit < 2){
				System.out.print("Which di(c)e would you like to keep? "
					+ "Enter the values you'd like to 'hold' without\n" 
					+ "spaces. For examples, if you'd like to 'hold' "
					+ "die 1, 2, and 5, enter 125\n(enter -1 if "
					+ "you'd like to end the turn) --> ");
				holdChoice = in.nextLine();
				boolean hasEntered = false;
				while(!hasEntered){
				    if(holdChoice.length() >= 1){
				        if(holdChoice.equals("-1")){
				            hasEntered = true; // "-1" is a valid input
				        }
				        else{
				            for(int i = 0; i < holdChoice.length(); i++){
				                if(holdChoice.charAt(i) >= '1'
				                && holdChoice.charAt(i) <= '9'){
				                    hasEntered = true; // Found a valid digit
				                    i = holdChoice.length();
				                }
								else{
									hasEntered = true;
									i = holdChoice.length();
								}
				            }
				        }
				    }
				}
				holdNum = Integer.parseInt(holdChoice);
			}
			if(holdNum != -1 && turnLimit < 2){
				dicegroup.rollDice(holdChoice);
				dicegroup.printDice();
				System.out.println();
			}
			else if(turnLimit == 2 || holdNum == -1){
				hasturnEnded = true;
			}
			turnLimit++;
		}
	}
	public void isItChangeable1(){
		boolean isnotoverunder = false;
		scoreChoice = in.nextInt();
		while(isnotoverunder == false){
			if(scoreChoice >=1 && scoreChoice <=13){
				isnotoverunder = true;
			}
			else{
				System.out.println();
				System.out.print("Please reenter a number within 1-13 --> ");
				scoreChoice = in.nextInt();
			}
		}
		in.nextLine();
		hasChanged = yahScoreCard1.changeScore(scoreChoice, dicegroup);
	}
	public void isItChangeable2(){
		boolean isnotoverunder = false;
		scoreChoice = in.nextInt();
		while(isnotoverunder == false){
			if(scoreChoice >=1 && scoreChoice <=13){
				isnotoverunder = true;
			}
			else{
				System.out.println();
				System.out.print("Please reenter a number within 1-13 --> ");
				scoreChoice = in.nextInt();
			}
		}
		in.nextLine();
		hasChanged = yahScoreCard2.changeScore(scoreChoice, dicegroup);
	}
	public void chooseNum1(int choice){
		if(choice >= 1 && choice <= 6){
			yahScoreCard1.numberScore(choice, dicegroup);
		}
		else if(choice == 7){
			yahScoreCard1.threeOfAKind(dicegroup);
		}
		else if(choice == 8){
			yahScoreCard1.fourOfAKind(dicegroup);
		}
		else if(choice == 9){
			yahScoreCard1.fullHouse(dicegroup);
		}
		else if(choice == 10){
			yahScoreCard1.smallStraight(dicegroup);
		}
		else if(choice == 11){
			yahScoreCard1.largeStraight(dicegroup);
		}
		else if(choice == 12){
			yahScoreCard1.chance(dicegroup);
		}
		else if(choice == 13){
			yahScoreCard1.yahtzeeScore(dicegroup);
		}
	}
	public void chooseNum2(int choice){
		if(choice >= 1 && choice <= 6){
			yahScoreCard2.numberScore(choice, dicegroup);
		}
		else if(choice == 7){
			yahScoreCard2.threeOfAKind(dicegroup);
		}
		else if(choice == 8){
			yahScoreCard2.fourOfAKind(dicegroup);
		}
		else if(choice == 9){
			yahScoreCard2.fullHouse(dicegroup);
		}
		else if(choice == 10){
			yahScoreCard2.smallStraight(dicegroup);
		}
		else if(choice == 11){
			yahScoreCard2.largeStraight(dicegroup);
		}
		else if(choice == 12){
			yahScoreCard2.chance(dicegroup);
		}
		else if(choice == 13){
			yahScoreCard2.yahtzeeScore(dicegroup);
		}
	}
}
