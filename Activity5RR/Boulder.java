
import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;

public class Boulder extends Actor{
    private int lifetime;
    private static final int THRESHOLD = 10; // Adjust as necessary
    // Constructor with random lifetime between 1 and 200
    public Boulder(){
        setColor(null);
        lifetime =(int)(Math.random() * 200) + 1;
    }
    // Constructor with specified lifetime
    public Boulder(int lifetime){
        setColor(null);
        this.lifetime = lifetime;
    }
    // Act method to simulate countdown and explosion
    public void act(){
        lifetime--;
        // Change color to red when lifetime is below threshold
        if(lifetime < THRESHOLD){
            setColor(java.awt.Color.RED);
        }
        // Replace with Kaboom when lifetime reaches zero
        if(lifetime <= 0){
            if(getGrid() != null && getLocation() != null){
                Kaboom explosion = new Kaboom();
                explosion.putSelfInGrid(getGrid(), getLocation());
            }
        }
    }
}
