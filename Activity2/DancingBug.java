import info.gridworld.actor.Bug;

public class DancingBug extends Bug{
    private int[] turns;
    private int turnIndex;

    public DancingBug(int[] turnSequence){
        this.turns = turnSequence;
        this.turnIndex = 0;
    }
    public void act(){
        for (int i = 0; i < turns[turnIndex]; i++){
            turn();
        }
        if (canMove()){
            move();
        }
        turnIndex = (turnIndex + 1) % turns.length; // Loop through the sequence
    }
}
