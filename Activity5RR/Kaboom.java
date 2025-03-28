import info.gridworld.actor.Actor;

public class Kaboom extends Actor{
    private int lifetime;
    private static final int THRESHOLD = 3;
    // Constructor
    public Kaboom(){
        setColor(null);
        lifetime = THRESHOLD;
    }
    // Act method to simulate the explosion effect
    public void act(){
        lifetime--;
        if (lifetime <= 0) {
            removeSelfFromGrid();
        }
    }
}
