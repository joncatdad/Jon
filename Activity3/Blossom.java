import info.gridworld.actor.Flower;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;

public class Blossom extends Flower {
    private int lifetime;
    public Blossom(){
        super(Color.GREEN);
        lifetime = 10; // Default lifetime
    }
    public Blossom(int lifetime){
        super(Color.GREEN);
        this.lifetime = lifetime;
    }
    public void act(){
        super.act();
        lifetime--;
        if (lifetime <= 0) {
            Grid grid = getGrid();
            if (grid != null) { // Ensure the Blossom is still in the grid
                removeSelfFromGrid();
            }
        }
    }
}
