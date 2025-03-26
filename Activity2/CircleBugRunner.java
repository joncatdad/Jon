import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import java.awt.Color;

public class CircleBugRunner{
    public static void main(String[] args){
        ActorWorld world = new ActorWorld();
        CircleBug bug1 = new CircleBug(3); // Moves in a circle of size 3
        CircleBug bug2 = new CircleBug(7);
        bug1.setColor(Color.RED);
        bug2.setColor(Color.YELLOW);
        world.add(new Location(4, 4), bug1);
        world.add(new Location(8,8), bug2);
        world.show();
    }
}
