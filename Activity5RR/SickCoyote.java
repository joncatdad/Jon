
import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import java.awt.Color;

public class SickCoyote extends Actor{
    private int lifetime;
    private static final int THRESHOLD = 10;
    public SickCoyote(){
        setColor(null); // No color for SickCoyote
        lifetime = THRESHOLD;
    }
    public SickCoyote(int lifetime){
        setColor(null);
        this.lifetime = lifetime;
    }
    public void act(){
        lifetime--;
        if(lifetime <= 0 && getGrid() != null){
            Location loc = getLocation();
            getGrid().remove(loc);
            getGrid().put(loc, new Coyote());
        }
    }
}
