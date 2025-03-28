import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;
import java.awt.Color;
public class Stone extends Rock{
    private int lifetime;
    private static final int THRESHOLD = 10; // Turns green when below this value
    // Constructor with random lifetime between 1 and 200
    public Stone(){
        setColor(null);
        lifetime =(int)(Math.random() * 200) + 1;
    }
    // Constructor with specified lifetime
    public Stone(int lifetime){
        setColor(null);
        this.lifetime = lifetime;
    }
    // Act method to simulate countdown and transformation
    public void act(){
        lifetime--;
        // Change color to green when lifetime is below threshold
        if(lifetime < THRESHOLD){
            setColor(Color.GREEN);
        }
        // Transform into Boulder when lifetime reaches zero
        if(lifetime <= 0){
            if(getGrid() != null){
                Boulder boulder = new Boulder();
                boulder.putSelfInGrid(getGrid(), getLocation());
            }
        }
    }
}
