import info.gridworld.actor.Bug;

public class SpiralBug extends Bug {
    private int steps;
    private int sideLength;
    public SpiralBug(int initialLength){
        steps = 0;
        sideLength = initialLength;
    }
    public void act(){
        if (steps < sideLength && canMove()){
            move();
            steps++;
        }
        else{
            turn();
            steps = 0;
            sideLength++;  // Increase side length to expand the spiral
        }
    }
}
