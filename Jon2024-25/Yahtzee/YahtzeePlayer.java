/**
 *  This is one player actions and variables.
 *
 *  @author Mr Greenstein and Jonathan Chen
*/
public class YahtzeePlayer{
	private String name;
	private YahtzeeScoreCard scorecard;
	public YahtzeePlayer(){
		scorecard = new YahtzeeScoreCard();
	}
	public void setName(String n){
		name = n;
	}
	public String getName(){
		return name;
	}
	public YahtzeeScoreCard getScoreCard(){
		return scorecard;
	}
}
