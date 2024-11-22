/**
 *	Describe the scorecard here.
 *
 *	@author	Jonathan Chen 
 *	@since	October 23, 2024
 */
public class YahtzeeScoreCard{
	private int[]totalScores; 
	private int[]count; 
	private int numRolled; 
	private final int THREE_OF_KIND = 6; 
	private final int FOUR_OF_KIND = 7; 
	private final int FULL_HOUSE = 8; 
	private final int SMALL_STRAIGHT = 9; 
	private final int LARGE_STRAIGHT = 10; 
	private final int CHANCE = 11; 
	private final int YAHTZEE = 12; 
	private final int FULL_HOUSE_SCORE = 25; 
	private final int SMALL_STRAIGHT_SCORE = 30; 
	private final int LARGE_STRAIGHT_SCORE = 40; 
	private final int YAHTZEE_SCORE = 50; 
	public YahtzeeScoreCard(){
		totalScores = new int[13];
		for(int a = 0; a < totalScores.length; a++){
			totalScores[a] = -1;
		}
		count = new int[6];
		for(int a = 0; a < count.length; a++){
			count[a] = 0;
		}
		numRolled = 0;
	}
	/**
	 *	Get a category score on the score card.
	 *	@param category		the category number (1 to 13)
	 *	@return				the score of that category
	 */
	public int getScore(int i){
		int result = 0;
		result = totalScores[i - 1];
		return result;
	}
	/**
	 *  Print the scorecard header
	 */
	public void printCardHeader(){
		System.out.println("\n");
		System.out.printf("\t\t\t\t\t       3of  4of  Fll Smll Lrg\n");
		System.out.printf("  NAME\t\t  1    2    3    4    5    6   "
		+ "Knd  Knd  Hse Strt Strt Chnc Ytz!\n");
		System.out.printf("+------------------------------------------"
						+ "-------------------------------------+\n");
	}
	/**
	 *  Prints the player's score
	 */
	public void printPlayerScore(YahtzeePlayer player){
		System.out.printf("| %-12s |", player.getName());
		for (int i = 1; i < 14; i++){
			if (getScore(i) > -1){
				System.out.printf(" %2d |", getScore(i));
			}
			else{
				System.out.printf("    |");
			}
		}
		System.out.println();
		System.out.printf("+-------------------------------------------"
							+ "------------------------------------+\n");
	}
	public void printScoreChoices(){
		System.out.println("\t\t  1    2    3    4    5    6   " +
				"7    8    9    10   11   12   13\n");
	}
	public int getTotalScore(){
		int finalScore = 0;
		for(int a = 0; a < totalScores.length; a++){
			if(totalScores[a] == -1){
				totalScores[a] = 0;
			}
			finalScore = finalScore + totalScores[a];
		}
		return finalScore;
	}
	/**
	 *  Change the scorecard based on the category choice 1-13.
	 *
	 *  @param choice The choice of the player 1 to 13
	 *  @param dg  The DiceGroup to score
	 *  @return  true if change succeeded. Returns false if choice already taken.
	 */
	public boolean changeScore(int choice, DiceGroup dg){
		if(totalScores[choice - 1] != -1 || choice < 1 || choice > 13)
			return false;
		else
			return true;
	}
	
	/**
	 *  Change the scorecard for a number score 1 to 6
	 *
	 *  @param choice The choice of the player 1 to 6
	 *  @param dg  The DiceGroup to score
	 */
	public void numberScore(int choice, DiceGroup dg){
		int count = 0;
		for(int a = 0; a < 5; a++){
			if(dg.getDie(a).getLastRollValue() == choice){
				count = count + 1;
			}
		}
		totalScores[choice-1] = count*choice;
	}
	public void specialComboStart(DiceGroup dg){
		for(int a = 0; a < count.length; a++){
			count[a] = 0;
		}
		for(int a = 0; a < 5; a++){
			numRolled = dg.getDie(a).getLastRollValue();
			count[numRolled - 1]++;
		}
	}	
	/**
	 *	Updates the scorecard for Three Of A Kind choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void threeOfAKind(DiceGroup dg){
		boolean changeable = false; 
		int total = 0;
		specialComboStart(dg);
		for(int a = 0; a < count.length; a++){
			if(count[a] >= 3){
				changeable = true;
			}
			if(changeable){
				total = (a + 1) * 3;
			}
			changeable = false;
		}
		if(total > 0){
			totalScores[THREE_OF_KIND] = total;
		}
		else{
			totalScores[THREE_OF_KIND] = 0;
		}
	}
	/**
	 *	Updates the scorecard for Four Of A Kind choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void fourOfAKind(DiceGroup dg){
		boolean changeable = false; 
		int total = 0;
		specialComboStart(dg);
		for(int a = 0; a < count.length; a++){
			if(count[a] >= 4){
				changeable = true;
			}
			if(changeable){
				total = (a + 1) * 4;
			}
			changeable = false;
		}
		if(total > 0){
			totalScores[FOUR_OF_KIND] = total;
		}
		else{
			totalScores[FOUR_OF_KIND] = 0;
		}
	}
	/**
	 *	Updates the scorecard for Full House choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void fullHouse(DiceGroup dg){
		boolean changeable1 = false; 
		boolean changeable2 = false; 
		specialComboStart(dg);
		for(int a = 0; a < count.length; a++){
			if(count[a] == 3 && changeable1 == false){
				changeable1 = true;
			}
			else if(count[a] == 2 && changeable2 == false){
				changeable2 = true;
			}
		}
		if(changeable1 && changeable2){
			totalScores[FULL_HOUSE] = FULL_HOUSE_SCORE;
		}
		else{
			totalScores[FULL_HOUSE] = 0;
		}
	}
	/**
	 *	Updates the scorecard for Small Straight choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void smallStraight(DiceGroup dg){
		boolean changeable = false;
		specialComboStart(dg);
		for(int a = 0; a < 3; a++){
			if(count[a] > 0 && count[a+1] > 0 && count[a+2] > 0 
				&& count[a+3] > 0){
				changeable = true;
			}
		}
		if(changeable){
			totalScores[SMALL_STRAIGHT] = SMALL_STRAIGHT_SCORE;
		}
		else{
			totalScores[SMALL_STRAIGHT] = 0;
		}
	}
	/**
	 *	Updates the scorecard for Large Straight choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void largeStraight(DiceGroup dg){
		boolean changeable = false;
		specialComboStart(dg);
		for(int a = 0; a < 2; a++){
			if(count[a] > 0 && count[a+1] > 0 && count[a+2] > 0 
				&& count[a+3] > 0 && count[a+4] > 0){
				changeable = true;
			}
		}
		if(changeable){
			totalScores[LARGE_STRAIGHT] = LARGE_STRAIGHT_SCORE;
		}
		else{
			totalScores[LARGE_STRAIGHT] = 0;
		}
	}
	/**
	 *	Updates the scorecard for Chance choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void chance(DiceGroup dg) {
		totalScores[CHANCE] = dg.getTotal();
	}
	/**
	 *	Updates the scorecard for Yahtzee choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void yahtzeeScore(DiceGroup dg) {
		boolean changeable = false;
		int firstNum = 0;
		int[]restNum = new int [4];
		firstNum = dg.getDie(0).getLastRollValue();
		for(int a = 1; a < 5 ; a++){
			restNum[a - 1] = dg.getDie(a).getLastRollValue();
		}
		for(int a = 0; a < 1; a++){		
			if((restNum[a] == firstNum) && (restNum[a + 1] == firstNum)
				&& (restNum[a + 2] == firstNum)
				&& (restNum[a + 3] == firstNum)){
				changeable = true;
			}
		}
		if(changeable){
			totalScores[YAHTZEE] = YAHTZEE_SCORE;
		}
		else{
			totalScores[YAHTZEE] = 0;
		}
	}
}
