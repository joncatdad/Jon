import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import java.awt.Color;

public class SpiralBugRunner{
    public static void main(String[] args){
        ActorWorld world = new ActorWorld();
        SpiralBug bug = new SpiralBug(2); // Initial side length = 2
        bug.setColor(Color.RED);
        world.add(new Location(5, 5), bug);
        world.show();
    }
}
