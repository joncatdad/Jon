import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import java.awt.Color;

public class ZBugRunner{
    public static void main(String[] args){
        ActorWorld world = new ActorWorld();
        ZBug bug = new ZBug(4); // Creates a Z of size 4
        bug.setColor(Color.RED);
        world.add(new Location(5, 5), bug);
        world.show();
    }
}
