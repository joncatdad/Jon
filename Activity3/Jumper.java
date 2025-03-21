import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.Random;

public class Jumper extends Bug{
    private int jumpDistance;
    private Random rand;
    public Jumper(){
        setColor(Color.BLUE);
        jumpDistance = 2; // Default jump distance
        rand = new Random();
    }
    public Jumper(int distance){
        setColor(Color.BLUE);
        jumpDistance = distance;
        rand = new Random();
    }
    public void act(){
        if(canJump()){
            jump();
        }
        else if(canMove()){
            move();  // If jump isn't possible, move 1 step
        }
        else{
            turn();
        }
    }
    public boolean canJump(){
        Grid grid = getGrid();
        if(grid == null) return false;
        // First step forward
        Location stepOne = getLocation().getAdjacentLocation(getDirection());
        // Second step forward(actual jump)
        Location stepTwo = stepOne.getAdjacentLocation(getDirection());
        return grid.isValid(stepTwo) && grid.get(stepTwo) == null;
    }
    public void jump(){
        Grid grid = getGrid();
        if(grid == null) return;
        Location currentLoc = getLocation();
        Location stepOne = currentLoc.getAdjacentLocation(getDirection());
        Location stepTwo = stepOne.getAdjacentLocation(getDirection());
        if(grid.isValid(stepTwo) && grid.get(stepTwo) == null){
            moveTo(stepTwo); // Jump two spaces
            dropBlossom(currentLoc);
        }
    }
    public void dropBlossom(Location prevLoc){
        int lifetime = rand.nextInt(10) + 1;
        Blossom blossom = new Blossom(lifetime);
        Grid grid = getGrid();
        if(grid != null && grid.isValid(prevLoc) && grid.get(prevLoc) == null){
            blossom.putSelfInGrid(grid, prevLoc);
        }
    }
}
