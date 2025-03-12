import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.Random;

public class Jumper extends Bug {
    private int jumpDistance;
    private Random rand;

    public Jumper() {
        setColor(Color.BLUE);
        jumpDistance = 2;
        rand = new Random();
    }

    public Jumper(int distance) {
        setColor(Color.BLUE);
        jumpDistance = distance;
        rand = new Random();
    }

    public void act() {
        if (canJump()) {
            jump();
        } else if (canMove()) {
            move();
        } else {
            turn();
        }
    }

    public boolean canJump() {
        Grid grid = getGrid();
        if (grid == null) return false;

        Location nextLoc = getLocation().getAdjacentLocation(getDirection()).getAdjacentLocation(getDirection());
        return grid.isValid(nextLoc) && grid.get(nextLoc) == null;
    }

    public void jump() {
        Location newLoc = getLocation().getAdjacentLocation(getDirection()).getAdjacentLocation(getDirection());
        moveTo(newLoc);
        dropBlossom();
    }

    public void dropBlossom() {
        int lifetime = rand.nextInt(10) + 1;
        Blossom blossom = new Blossom(lifetime);
        blossom.putSelfInGrid(getGrid(), getLocation());
    }
}
